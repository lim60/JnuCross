//// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract CustomDeclare {
    /*
   流程：1. 存证客户的海关申报单
        2. 存证客户的完税证明
   */

    enum State { Created, Updated, Submitted, //已创建、已修改、已提交
        Preliminary_Checked, Repeat_Checked, //已初审、已复审、
        Single_Window_Submitted, Saved, //提交到海关单一窗口、暂存成功
        Directly_Passed, Warehoused, //直接申报成功、入库成功!
        Request_Accepted, Guarantee_Releasing, //海关已接受申报、报关单担保放行
        Port_Inspected, Released, Closed, Document_Released //口岸检查、已放行、报关单已结关、单证放行
    }

    event ValueIndex(uint formIndex);

    //报关单
    struct CustomForm{
        string orderId;
        string customNo; //报关单号
        string customCode; //海关编码
        State checkState;//审核状态
        string formDigest; //报关单内容摘要
        string createTime;
        string certificateDigest;//完税证明回执
    }
    //完税证明
    struct TaxCertificate{
        string receiptDigest; //发票 from 进口订单
        string importCertificateDigest; //进口证明书 from 报关单
        CustomForm customform;
    }

    mapping (uint => CustomForm) customForms;
    uint numForm;

    mapping (uint => TaxCertificate) taxCertificates;
    uint numCertificate;

    constructor(){
    }


    //创建海关申报单
    function createCustomForm(string memory orderId,
        string memory customNo,
        string memory customCode,
        string memory formDigest,
        string memory createTime,
        string memory certificateDigest) public returns (uint){

        uint formIndex = numForm++;

        customForms[formIndex] = CustomForm(orderId, customNo, customCode, State.Created, formDigest, createTime, certificateDigest);

        emit ValueIndex(formIndex);
        return formIndex;
    }

    //查询海关申报单
    function queryCustomForm(uint formIndex) public view returns(string memory, string memory, string memory, string memory, string memory, string memory, string memory){

        string memory checkState = queryCustomState(formIndex);
        CustomForm storage customForm = customForms[formIndex];

        return (customForm.orderId,
        customForm.customNo,
        customForm.customCode,
        checkState,
        customForm.formDigest,
        customForm.createTime,
        customForm.certificateDigest);
    }

    //查询海关单状态
    function queryCustomState(uint formIndex) public view returns(string memory stateStr){
        State checkState_ = customForms[formIndex].checkState;

        if(checkState_ == State.Created){
            stateStr = "CF_N";
        }else if(checkState_ == State.Updated){
            stateStr = "CF_U";
        }else if(checkState_ == State.Submitted){
            stateStr = "CF_S";
        }else if(checkState_ == State.Preliminary_Checked){
            stateStr = "CF_PR";
        }else if(checkState_ == State.Repeat_Checked){
            stateStr = "CF_R";
        }else if(checkState_ == State.Single_Window_Submitted){
            stateStr = "CF_SW";
        }else if(checkState_ == State.Saved){
            stateStr = "CF_SS";
        }else if(checkState_ == State.Directly_Passed){
            stateStr = "7";
        }else if(checkState_ == State.Warehoused){
            stateStr = "L";
        }else if(checkState_ == State.Request_Accepted){
            stateStr = "J";
        }else if(checkState_ == State.Guarantee_Releasing){
            stateStr = "K";
        }else if(checkState_ == State.Port_Inspected){
            stateStr = "C";
        }else if(checkState_ == State.Released){
            stateStr = "3";
        }else if(checkState_ == State.Closed){
            stateStr = "R";
        }else
            stateStr = "P";

        return stateStr;
    }

    //更新海关申报单状态
    function updateCustomFormState(uint formIndex, string memory newOrderState) public {
        CustomForm storage customForm = customForms[formIndex];

        if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("CF_N"))){
            customForm.checkState = State.Created;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("CF_U"))){
            customForm.checkState = State.Updated;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("CF_S"))){
            customForm.checkState = State.Submitted;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("CF_PR"))){
            customForm.checkState = State.Preliminary_Checked;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("CF_R"))){
            customForm.checkState = State.Repeat_Checked;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("CF_SW"))){
            customForm.checkState = State.Single_Window_Submitted;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("CF_SS"))){
            customForm.checkState = State.Saved;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("7"))){
            customForm.checkState = State.Directly_Passed;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("L"))){
            customForm.checkState = State.Warehoused;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("J"))){
            customForm.checkState = State.Request_Accepted;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("K"))){
            customForm.checkState = State.Guarantee_Releasing;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("C"))){
            customForm.checkState = State.Port_Inspected;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("3"))){
            customForm.checkState = State.Released;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("R"))){
            customForm.checkState = State.Closed;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("P"))){
            customForm.checkState = State.Document_Released;

        }else{
            customForm.checkState = customForm.checkState;


        }
    }

    // //上传完税证明和发票
    // function saveTaxCertificate(uint formIndex, string memory receiptDigest, string memory importCertificateDigest) public returns(bool){
    //     CustomForm storage customForm = customForms[formIndex];
    //     uint certificateIndex = numCertificate++;
    //     if (customForm.checkState == State.Certificated){
    //         taxCertificates[certificateIndex] = TaxCertificate(receiptDigest, importCertificateDigest, customForm);
    //         return true;
    //     }
    //     else return false;
    // }

}
