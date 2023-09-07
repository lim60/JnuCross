//// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract EvidenceContract {

    /*
    流程：1. 中海龙在法院链上新建证据，关联orderId，新建 "进口订单"证据
         3. 在新建 "进口订单"证据之上 添加"报关核验"证据
         4. 添加"完税"证据
         5. 添加"进口"证据
         6. 添加"到港"证据
         7. 添加"入库"证据
         8. 添加"放款"证据
         9. 法院实体可在以上不同阶段添加签名

    函数：newEvidence，updateEvidence，queryEvidenceState，addSignatures，getSigners，getSignersSize
    以及内部函数
    */

    /*证据状态包括
      订单创建、报关核验、完税、进口、到港、入库、放款、合同签订、还款、出库
    */
    enum State { Created, CustomCertificated, TaxCollected, Imported, onPort, Warehoused, Loaded, Signed, Refunded, Deliveried }

    struct Evidence{
        uint orderIndex; //关联ID
        State evidenceState; //证据最新状态From State
        uint createTime; //当前块时间戳
        string[] evidenceDigests;//关于不同状态下的证据摘要
        string[] addedSigners;//已签名实体
        string[] signatures;
    }

    // string[] initSigners; //法院链上的法院实体公钥，比如广州法院、北京法院；负责对新建evidence进行签名
    mapping (uint => Evidence) evidences;
    uint numEvidence;

    mapping (string => string) signatures;//表示不同法院实体的签名，签名是ecdsa签名

    // event ValueEvidence(uint evidenceIndex, string state, string evidenceDigest);
    // event ValueNewEviSignature(string signer, string signature);

    /*
    合约构造函数
    初始化维护证据的法院参与方地址. ["地址"]
    */
    constructor(){
    }


    /*
    * @param orderIndex 订单在链上的唯一索引，用于关联订单实体
    * @param evidenceDigest 首次创建，"进口订单"的证据
    * @return evidenceIndex 证据索引
    */
    function newEvidence(uint orderIndex, string memory evidenceDigest) public returns(uint, string memory, string memory){
        uint evidenceIndex = numEvidence++;
        Evidence storage evidence = evidences[evidenceIndex];
        evidence.orderIndex = orderIndex;
        evidence.evidenceState = State.Created;
        evidence.evidenceDigests.push(evidenceDigest);
        evidence.createTime = block.timestamp;

        // emit ValueEvidence(evidenceIndex, "Created", evidenceDigest);

        return (evidenceIndex, "Created", evidenceDigest);
    }


    /*
    依据业务所处不同阶段，可由中海龙、客户、银行调用该函数
    * @param evidenceIndex 证据索引
    * @param newState 新加入的证据状态，前端传入参数CustomCertificated，TaxCollected等
    * @param evidenceDigest 新加入的证据摘要
    * @return evidenceIndex 更新后的证据索引
    新增证据，包括 报关核验、完税、进口、到港、入库、放款、合同签订、还款、出库
    */
    function updateEvidence(uint evidenceIndex, string memory newState, string memory evidenceDigest)
    public returns(uint, string memory, string memory){
        Evidence storage e = evidences[evidenceIndex];
        e.evidenceDigests.push(evidenceDigest);

        if(keccak256(abi.encodePacked(newState)) == keccak256(abi.encodePacked("CustomCertificated"))){
            e.evidenceState = State.CustomCertificated;
        }else if(keccak256(abi.encodePacked(newState)) == keccak256(abi.encodePacked("TaxCollected"))){
            e.evidenceState = State.TaxCollected;
        }else if(keccak256(abi.encodePacked(newState)) == keccak256(abi.encodePacked("Imported"))){
            e.evidenceState = State.Imported;
        }else if(keccak256(abi.encodePacked(newState)) == keccak256(abi.encodePacked("onPort"))){
            e.evidenceState = State.onPort;
        }else if(keccak256(abi.encodePacked(newState)) == keccak256(abi.encodePacked("Warehoused"))){
            e.evidenceState = State.Warehoused;
        }else if(keccak256(abi.encodePacked(newState)) == keccak256(abi.encodePacked("Loaded"))){
            e.evidenceState = State.Loaded;
        }else if(keccak256(abi.encodePacked(newState)) == keccak256(abi.encodePacked("Signed"))){
            e.evidenceState = State.Signed;
        }else if(keccak256(abi.encodePacked(newState)) == keccak256(abi.encodePacked("Refunded"))){
            e.evidenceState = State.Refunded;
        }else{
            e.evidenceState = State.Deliveried;
        }

        // emit ValueEvidence(evidenceIndex, newState, evidenceDigest);

        return (evidenceIndex, newState, evidenceDigest);
    }


    function queryEvidenceState(uint evidenceIndex) public view returns(string memory){

        string memory eviState;
        Evidence storage e = evidences[evidenceIndex];
        if(e.evidenceState == State.CustomCertificated){
            eviState = "CustomCertificated";

        }else if(e.evidenceState == State.TaxCollected){
            eviState = "TaxCollected";

        }else if(e.evidenceState == State.Imported){
            eviState = "Imported";

        }else if(e.evidenceState == State.onPort){
            eviState = "onPort";

        }else if(e.evidenceState == State.Warehoused){
            eviState = "Warehoused";

        }else if(e.evidenceState == State.Loaded){
            eviState = "Loaded";

        }else if(e.evidenceState == State.Signed){
            eviState = "Signed";

        }else if(e.evidenceState == State.Refunded){
            eviState = "Refunded";

        }else if(e.evidenceState == State.Deliveried){
            eviState = "Deliveried";

        }else{
            eviState = "";
        }

        return eviState;
    }

    /*
    法院实体对关联evidenceIndex的当前证据添加签名
    @param evidenceIndex 证据链上唯一索引
    @param signerAddress 法院账户地址
    */
    function addSignatures(uint evidenceIndex, string memory publicKey, string memory indiviSignature) public returns (string memory, string memory){
        Evidence storage evidence = evidences[evidenceIndex];
        evidence.addedSigners.push(publicKey);
        evidence.signatures.push(indiviSignature);
        signatures[publicKey] = indiviSignature;
        // emit ValueNewEviSignature(publicKey, indiviSignature);
        return (publicKey, indiviSignature);
    }

    /*
    获取已经签名的实体
    */
    // function getSigners(uint evidenceIndex) public view returns(string[] memory){
    //     Evidence storage evidence = evidences[evidenceIndex];
    //     return evidence.addedSigners;
    // }

    // function getSignersSize(uint evidenceIndex) public view returns(uint){
    //     Evidence storage evidence = evidences[evidenceIndex];
    //     return evidence.addedSigners.length;
    // }


}