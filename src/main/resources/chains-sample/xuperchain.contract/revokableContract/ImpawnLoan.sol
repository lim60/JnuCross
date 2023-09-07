// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

contract ImpawnLoan {
    /*
    流程：1. 客户申请质押贷款，创建贷款申请单结构体，其内容会包括营业执照、组织机构代码证、法定代表人证明书
            ==》生成贷款申请单结构体
         2. 金融机构根据loanIndex查询贷款申请单结构体，并审查贷款申请
            ==》查询贷款申请单
            ==》触发跨链平台的注册代理函数
            ==》触发跨链平台的状态查询函数，向海关链查询发票、完税证明、进口证明书，向中海龙业务链查询入库单
            ==》返回审查结果
         3. 金融机构向客户放款，更新状态Loaned
         4. 中海龙链上的入库订单车辆 状态为抵押状态
         5. 客户向金融机构还款后，更新状态Refunded
         6. 中海龙链上的入库订单车辆 状态为赎回状态
    */

    //TODO：货物已抵押、货物已赎回 ==》关联中海龙链上的合约 (Done)
    //TODO: 链下转账，线上存证 ，定向贷款，流水证明

    //贷款申请单状态：创建未审查、审查通过、审查不通过、合同已签订、发放贷款、还款、合同解除
    enum State { Created, Agreed, Disagreed, Signed, Loaned, Refunded, Unsigned }

    struct ImpawnLoanExamination {
        string orderState;
        string taxState;
        string importCertificate;
        string proposal;
    }

    struct ImpawnLoanContractSigned {
        string contractSignedDigest; //仓单质押合同
        string creditAgreementDigest; //仓单质押授信监管三方协议
        string noticeDigest; //出质通知书
        string receiptDigest; //出质通知书回执
    }

    //客户三证信息结构体
    struct ClientInfo{
        string clientInfoDigest; //营业执照+组织机构代码证+法定代表人证明书 摘要
        string clientOffchainAccount; //客户链下账户
    }

    //银行信息
    struct BankInfo{
        string bankInfoDigest; //所申请贷款的银行名称 + 营业执照+组织机构代码证+法定代表人证明书 摘要
        string bankOffchainAccount; //银行链下账户
    }


    //贷款申请单结构体 (汽车进口)
    struct ImpawnLoanRequest {
        uint orderIndex;//用于关联<--->中海龙业务链的进口订单
        uint256 requestedValue;//申请贷款金额
        State loanState;//贷款申请单状态
        address clientAddr;//客户链上账户地址
        address bankAddr; //银行链上账户地址
        ImpawnLoanExamination loanExamination; //贷款申请审核结构体
        ImpawnLoanContractSigned loanContractSigned;//贷款合同签订结构体
        string unsignImpawnContractDigest; //贷款合同解除证明
        string bankToClientStat; //银行转账给客户的流水
        string clientToBankStat; //客户转账给银行的流水
    }

    // event ValueRequest(uint loanIndex, address clientAddr, string clientDigest, string clientAccount);
    // event ImpawnNotice(uint loanIndex, string ifSign);

    uint numloans;
    mapping (uint => ImpawnLoanRequest) loans;
    mapping (address => ClientInfo) clientInfos;//客户链上账户地址 映射 客户三证信息
    mapping (address => BankInfo) bankInfos;//银行链上账户地址 映射 银行信息

    // 该转账事件会通知所有区块链用户
    // event Transfer(address indexed from, address indexed to, uint256 value);

    // 贷款申请审批通过事件会通知
    // event Examine(string stateStr);

    //账户地址对应的余额
    mapping (address => uint256) public balanceOf;
    constructor(){

    }

    /*
    新建质押贷款申请，由客户调用
    @param orderIndex 用于关联客户订单
    @param bankAddr 申请贷款的银行链上账户地址
    @param requestedValue 贷款金额
    @param clientAddr 客户的链上账户地址

    @param clientDigest 客户三证信息摘要
    @param clientAccount 客户链下银行账户
    @param bankDigest 银行三证信息摘要
    @param bankAccount 银行链下银行账户
    @return loanIndex 贷款申请单索引，客户的链上账户地址，客户三证信息摘要，客户链下银行账户
    */
    function createImpawnLoanRequest(uint orderIndex, address bankAddr, uint256 requestedValue,
        address clientAddr, string memory clientDigest, string memory clientAccount,
        string memory bankDigest, string memory bankAccount)
    public returns(uint, address, string memory, string memory){

        uint loanIndex = numloans++;

        loans[loanIndex].orderIndex = orderIndex;
        loans[loanIndex].bankAddr = bankAddr;
        loans[loanIndex].requestedValue = requestedValue;
        loans[loanIndex].loanState = State.Created;
        loans[loanIndex].clientAddr = clientAddr;

        clientInfos[clientAddr] = ClientInfo(clientDigest, clientAccount);
        bankInfos[bankAddr] = BankInfo(bankDigest, bankAccount);

        // emit ValueRequest(loanIndex, clientAddr, clientDigest, clientAccount);

        return (loanIndex, clientAddr, clientDigest, clientAccount);
    }


    /*
    金融机构审批客户资质（跨链获得 入库单 UWB状态、完税证明状态、进口证明书），并核验贷款申请信息，返回审批结果
    @param loanIndex 贷款申请单索引
    @param orderState 订单状态
    @param taxState 完税证明状态
    @param importCertificate 进口证明书
    @param proposal 金融机构审查意见，'yes' or 'no'

    @return bool true表示审查通过，false表示审查不通过
    */
    function ifImpawnLoan(uint loanIndex,
        string memory orderState,
        string memory taxState,
        string memory importCertificate,
        string memory proposal) public returns(string memory){
        //查询Loan
        ImpawnLoanRequest storage loan = loans[loanIndex];

        loan.loanExamination.orderState = orderState;
        loan.loanExamination.taxState = taxState;
        loan.loanExamination.importCertificate = importCertificate;
        loan.loanExamination.proposal = proposal;



        string memory ifloan;

        if(keccak256(abi.encodePacked(proposal)) == keccak256(abi.encodePacked("yes"))){
            updateImpawnLoanState(loanIndex, State.Agreed);
            ifloan = "Agreed";
            // emit Examine("Agreed");
        }else if(keccak256(abi.encodePacked(proposal)) == keccak256(abi.encodePacked("no"))){
            updateImpawnLoanState(loanIndex, State.Disagreed);
            ifloan = "Disagreed";
            // emit Examine("Disagreed");
        }
        return ifloan;
    }

    /*
    金融机构审查资质后，签订融资合同书，触发贷款申请状态为Signed
    @param loanIndex
    @param contractSignedDigest 仓单质押合同
    @param creditAgreementDigest 仓单质押授信监管三方协议
    @param noticeDigest 出质通知书
    @param receiptDigest 出质通知书回执
    */
    function signContractForImpawnLoan(uint loanIndex,
        string memory contractSignedDigest,
        string memory creditAgreementDigest,
        string memory noticeDigest,
        string memory receiptDigest) public returns (uint, string memory){
        //查询Loan
        ImpawnLoanRequest storage loan = loans[loanIndex];

        loan.loanContractSigned.contractSignedDigest = contractSignedDigest;
        loan.loanContractSigned.creditAgreementDigest = creditAgreementDigest;
        loan.loanContractSigned.noticeDigest = noticeDigest;
        loan.loanContractSigned.receiptDigest = receiptDigest;

        updateImpawnLoanState(loanIndex, State.Signed);
        // emit ImpawnNotice(loanIndex, "Signed");
        return (loanIndex, "Signed");

    }

    /*
    根据新传入的state更新loanState，由本合约更新
    @param laodIndex
    @param newState
    */
    function updateImpawnLoanState(uint loanIndex, State newState) internal{
        ImpawnLoanRequest storage loan = loans[loanIndex];
        loan.loanState = newState;
    }

    /*
    查询贷款申请状态
    */
    function queryImpawnLoanState(uint loanIndex) public view returns(State){
        ImpawnLoanRequest storage loan = loans[loanIndex];
        return loan.loanState;
    }

    /*
    金融机构放款，金融机构调用该合约，转账给指定客户端
    @param loanIndex
    */
    function makeImpawnLoan(uint loanIndex,
        string memory statement,
        string memory clientAccount,
        string memory bankAccount
    ) public returns (uint, string memory) {

        ImpawnLoanRequest storage loan = loans[loanIndex];

        address bankAddr = loan.bankAddr;
        address clientAddr = loan.clientAddr;

        assert(loan.loanState == State.Signed);

        //检查客户链上账户和链下账户关联性
        ClientInfo memory client = clientInfos[clientAddr];
        assert(keccak256(abi.encodePacked(client.clientOffchainAccount)) == keccak256(abi.encodePacked(clientAccount)));

        //检查银行链上账户和链下账户关联性
        BankInfo storage bank = bankInfos[bankAddr];
        assert(keccak256(abi.encodePacked(bank.bankOffchainAccount)) == keccak256(abi.encodePacked(bankAccount)));

        //存储银行流水
        loan.bankToClientStat = statement;

        updateImpawnLoanState(loanIndex, State.Loaned);

        // emit ImpawnNotice(loanIndex, "Loaned");
        return (loanIndex, "Loaned");
    }

    /*
    客户还款，由客户调用，转账给贷款银行
    */
    function refundImpawnLoan(uint loanIndex,
        string memory statement,
        string memory clientAccount,
        string memory bankAccount
    ) public returns (uint, string memory) {

        ImpawnLoanRequest storage loan = loans[loanIndex];
        address bankAddress = loan.bankAddr;
        address clientAddress = loan.clientAddr;

        BankInfo storage bank = bankInfos[bankAddress];
        assert(keccak256(abi.encodePacked(bank.bankOffchainAccount)) == keccak256(abi.encodePacked(bankAccount)));

        ClientInfo storage client = clientInfos[clientAddress];
        assert(keccak256(abi.encodePacked(client.clientOffchainAccount)) == keccak256(abi.encodePacked(clientAccount)));

        loan.clientToBankStat = statement;

        updateImpawnLoanState(loanIndex, State.Refunded);

        // emit ImpawnNotice(loanIndex, "Refunded");
        return (loanIndex, "Refunded");
    }

    /*解除贷款合同*/
    function unsignImpawnLoan(uint loanIndex, string memory unsignEvidenceDigest) public  returns (uint, string memory) {
        ImpawnLoanRequest storage loan = loans[loanIndex];
        loan.unsignImpawnContractDigest = unsignEvidenceDigest;
        updateImpawnLoanState(loanIndex, State.Unsigned);

        // emit ImpawnNotice(loanIndex, "Unsigned");
        return (loanIndex, "Unsigned");
    }
}
