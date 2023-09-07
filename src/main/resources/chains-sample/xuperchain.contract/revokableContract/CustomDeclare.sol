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

    // event ValueIndex(uint formIndex);

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

    mapping (uint => CustomForm) customForms;
    uint numForm;

    uint numCertificate;

    constructor(){
    }


    //创建海关申报单
    function createCustomForm(string memory orderId,
        string memory customNo,
        string memory customCode,
        string memory state,
        string memory formDigest,
        string memory createTime,
        string memory certificateDigest) public returns (uint){

        uint formIndex = numForm++;

        State s = compareStateStrs(state);

        customForms[formIndex] = CustomForm(orderId, customNo, customCode, s, formDigest, createTime, certificateDigest);

        // emit ValueIndex(formIndex);
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
    function updateCustomFormState(uint formIndex, string memory newOrderState) public returns(bool){
        CustomForm storage customForm = customForms[formIndex];
        bool updateState = true;

        State s = compareStateStrs(newOrderState);

        if(s == State.Created){
            updateState = false;
        }else{
            customForm.checkState = s;
        }

        return updateState;

    }

    function compareStateStrs(string memory newOrderState) internal pure returns(State){
        State returnState;

        if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("CF_N"))){
            returnState = State.Created;
        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("CF_U"))){
            returnState = State.Updated;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("CF_S"))){
            returnState = State.Submitted;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("CF_PR"))){
            returnState = State.Preliminary_Checked;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("CF_R"))){
            returnState = State.Repeat_Checked;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("CF_SW"))){
            returnState = State.Single_Window_Submitted;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("CF_SS"))){
            returnState = State.Saved;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("7"))){
            returnState = State.Directly_Passed;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("L"))){
            returnState = State.Warehoused;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("J"))){
            returnState = State.Request_Accepted;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("K"))){
            returnState = State.Guarantee_Releasing;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("C"))){
            returnState = State.Port_Inspected;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("3"))){
            returnState = State.Released;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("R"))){
            returnState = State.Closed;

        }else if(keccak256(abi.encodePacked(newOrderState)) == keccak256(abi.encodePacked("P"))){
            returnState = State.Document_Released;

        }else{
            returnState = State.Created;
        }
        return returnState;
    }


}
