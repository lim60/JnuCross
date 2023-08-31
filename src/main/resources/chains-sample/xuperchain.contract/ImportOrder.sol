//// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract ImportOrderContract {


    /*证据状态
     */
    enum State {
        Created,  //订单已创建
        CustomsCertificated, //报关已核验
        TaxCollected, //已完税
        Imported, //进口
        onPort, //已到港
        Warehoused, //已入库
        Loaded, //已融资
        Signed, //已合同签订
        Refunded, //已还款
        Deliveried //已出库
    }

    /*入库状态
    未入库、入库、质押、解押
    */
    enum ImportState { NotImport, Imported, Impawned, Released }


    /*进口订单*/
    //TODO 添加 【client_Name 客户名称】 【Loading_Port 装货港】
    //TODO 添加 【Destination_Port 目的港】【business_group_id 业务组id】【tenant_id 租户id】
    struct OrderEntity{
        string orderId; //关联ID  进口订单id
        string createTime;  //   创建时间create_time
        string importType;  //   进口类型import_type
        string contractId;  //   合同编号contract_no
        string clientName; // 客户名称client_Name
        string loadingPort; // 装货港Loading_Port
        string destinationPort; // 目的港Destination_Port
        string businessGroupId; // 业务组id
        string tenantId; //租户id
        string orderDigest; //   订单内容摘要
        State orderState;
        CustomForm customForm;
        ArriveEntity arriveEntity;
        InboundEntity inboundEntity; //根据租户id找inboundEntity
    }

    // event ValueOrderEntity(uint orderIndex, string orderDigest);



    //报关单
    struct CustomForm{
        string createTime; //申报时间 declare_time
        string customNo; //报关单号
        string customCode; //海关编码
        string checkState;//审核状态
        string customFormDigest;
    }

    // event ValueCustomFormEntity(string createTime, string customNo, string customFormDigest);

    //到港订单
    struct ArriveEntity{
        string createTime; //更新时间
        string onPortState; //到港状态
        string onPortDate; //到港日期
        string arriveEntityDigest;
    }

    // event ValueArriveEntity(string createTime, string onPortState, string onPortDate, string arriveEntityDigest);

    //入库订单
    //TODO 添加入库时间、入库状态、入库类型
    struct InboundEntity{
        string createTime; //入库时间
        string inboundState; //入库状态 01:新增 02:已生成波次单 03:完成
        string inboundType; //入库类型
        string inboundEntityDigest;
        ImportState importState;    //记录入库UWB状态 【Storage_Status 库位状态】
    }

    // event ValueInboundEntity(string createTime, string inboundState, string inboundType, string inboundEntityDigest);


    uint numOrdereEtity;
    mapping (uint => OrderEntity) Entity;
    uint onchainOrderIndex;

    constructor(){

    }

    /*
    创建进口订单， 默认设置orderState=Created， 成功返回true
    @param orderId 进口订单号
    @param createTime 创建时间
    @param importType 进口类型
    @param contractId 合同编号
    @param clientName 客户名称
    @param loadingPort 装货港
    @param destinationPort 目的港
    @param businessGroupId 业务组id
    @param tenantId 租户id, 用于关联入库订单信息
    @param orderDigest 订单摘要
    @return 进口订单索引
    */
    function createOrder(string memory orderId,
        string memory createTime,
        string memory importType,
        string memory contractId,
        string memory clientName,
        string memory loadingPort,
        string memory destinationPort,
        string memory businessGroupId,
        string memory tenantId,
        string memory orderDigest) public returns(uint, string memory){
        onchainOrderIndex = numOrdereEtity++;

        Entity[onchainOrderIndex].orderId = orderId;
        Entity[onchainOrderIndex].createTime = createTime;
        Entity[onchainOrderIndex].importType = importType;
        Entity[onchainOrderIndex].contractId = contractId;
        Entity[onchainOrderIndex].clientName = clientName;
        Entity[onchainOrderIndex].loadingPort = loadingPort;
        Entity[onchainOrderIndex].destinationPort = destinationPort;
        Entity[onchainOrderIndex].businessGroupId = businessGroupId;
        Entity[onchainOrderIndex].tenantId = tenantId;
        Entity[onchainOrderIndex].orderDigest = orderDigest;
        Entity[onchainOrderIndex].orderState = State.Created;

        // emit ValueOrderEntity(onchainOrderIndex, Entity[onchainOrderIndex].orderDigest);
        return (onchainOrderIndex, Entity[onchainOrderIndex].orderDigest);

    }

    /*根据Index 查询进口订单
    @param onchainOrderIndex 链上进口订单索引
    @return OrderEntity 进口订单
    */
    function queryOrder(uint index) public view returns(string memory, string memory){
        OrderEntity storage order = Entity[index];
        return (order.orderId, order.orderDigest);
    }


    /*根据 Index 查询进口订单状态
    @param onchainOrderIndex 链上进口订单索引
    @return State 进口订单状态
    */
    function queryOrderState(uint index) public view returns(string memory){
        OrderEntity storage order = Entity[index];
        string memory returnState;

        if(order.orderState == State.Created){
            returnState = "Created";
        }else if(order.orderState == State.CustomsCertificated){
            returnState = "CustomsCertificated";
        }else if(order.orderState == State.TaxCollected){
            returnState = "TaxCollected";
        }else if(order.orderState == State.Imported){
            returnState = "Imported";
        }else if(order.orderState == State.onPort){
            returnState = "onPort";
        }else if(order.orderState == State.Warehoused){
            returnState = "Warehoused";
        }else if(order.orderState == State.Loaded){
            returnState = "Loaded";
        }else if(order.orderState == State.Signed){
            returnState = "Signed";
        }else if(order.orderState == State.Refunded){
            returnState = "Refunded";
        }else if(order.orderState == State.Deliveried){
            returnState = "Deliveried";
        }else{
            returnState = "";
        }

        return returnState;

    }


    /*根据 Index 更新进口订单状态
    只能本合约调用该函数
    */
    function updateOrderState(uint index, State newOrderState) internal {
        OrderEntity storage order = Entity[index];
        order.orderState = newOrderState;
    }

    /*创建报关单
    @param createTime 申报时间
    @param customCode 海关编码
    @param checkState 审核状态
    @param customFormDigest 报关单内容摘要
    @param onchainOrderIndex 链上报关单索引
    @return CustomForm 报关单
    */
    function createForm(string memory createTime,
        string memory customNo,
        string memory customCode,
        string memory checkState,
        string memory customFormDigest,
        uint index) public returns(string memory, string memory, string memory){

        OrderEntity storage order = Entity[index];
        CustomForm memory tmp = CustomForm(createTime, customNo, customCode, checkState, customFormDigest);
        updateOrderState(index, State.TaxCollected);
        order.customForm = tmp;
        // emit ValueCustomFormEntity(tmp.createTime,
        //                         tmp.customNo,
        //                         tmp.customFormDigest);
        return (tmp.createTime, tmp.customNo, tmp.customFormDigest);
    }

    //查询报关单
    function queryForm(uint index) public view returns(string memory, string memory, string memory,
        string memory, string memory){
        OrderEntity storage order = Entity[index];
        return (order.customForm.createTime, order.customForm.customNo, order.customForm.customCode,
        order.customForm.checkState, order.customForm.customFormDigest);
    }



    /*创建到港单，确认上传为完税状态后再更新
    @param createTime
    @param onPortState 到港状态
    @param onPortDate 到港日期
    @param arriveEntityDigest 到港订单摘要
    @param onchainOrderIndex
    */
    function createArrival(string memory createTime,
        string memory onPortState,
        string memory onPortDate,
        string memory arriveEntityDigest,
        uint index) public returns(string memory, string memory, string memory, string memory){

        OrderEntity storage order = Entity[index];
        ArriveEntity memory tmp = ArriveEntity(createTime, onPortState, onPortDate, arriveEntityDigest);
        updateOrderState(index, State.onPort);
        order.arriveEntity = tmp;

        // emit ValueArriveEntity(tmp.createTime,
        //                   tmp.onPortState,
        //                   tmp.onPortDate,
        //                   tmp.arriveEntityDigest);

        return (tmp.createTime,
        tmp.onPortState,
        tmp.onPortDate,
        tmp.arriveEntityDigest);
    }

    //查询到港单
    function queryArrival(uint index) public view returns(string memory, string memory, string memory, string memory){
        OrderEntity storage order = Entity[index];
        return (order.arriveEntity.createTime, order.arriveEntity.onPortState, order.arriveEntity.onPortDate, order.arriveEntity.arriveEntityDigest);
    }


    /*创建入库单
    @param createTime 入库时间
    @param inboundState 入库状态 01:新增 02:已生成波次单 03:完成
    @param inboundType 入库类型
    @param inboundEntityDigest 入库订单摘要
    @param onchainOrderIndex
    @return 入库单
    */
    function createInbound(string memory createTime,
        string memory inboundState,
        string memory inboundType,
        string memory inboundEntityDigest,
        uint index) public returns(string memory, string memory, string memory, string memory){
        OrderEntity storage order = Entity[index];
        InboundEntity memory tmp = InboundEntity(createTime, inboundState, inboundType, inboundEntityDigest, ImportState.NotImport);
        order.inboundEntity=tmp;
        updateInboundState(index, ImportState.Imported);

        // emit ValueInboundEntity(createTime, inboundState, inboundType, inboundEntityDigest);
        return (createTime, inboundState, inboundType, inboundEntityDigest);
    }

    function queryInbound(uint index) public view returns(string memory, string memory, string memory, string memory){
        OrderEntity storage order = Entity[index];
        return (order.inboundEntity.createTime, order.inboundEntity.inboundState, order.inboundEntity.inboundType, order.inboundEntity.inboundEntityDigest);
    }

    //查询入库单状态
    function queryInboundState(uint index) public view returns(string memory){
        OrderEntity storage order = Entity[index];
        string memory returnImportState;
        if(order.inboundEntity.importState == ImportState.NotImport){
            returnImportState = "NotImport";
        }else if(order.inboundEntity.importState == ImportState.Imported){
            returnImportState = "Imported";
        }else if(order.inboundEntity.importState == ImportState.Impawned){
            returnImportState = "Impawned";
        }else if(order.inboundEntity.importState == ImportState.Released){
            returnImportState = "Released";
        }else{
            returnImportState = "";
        }

        return returnImportState;
    }


    //更新入库单的状态为入库，同时更新进口订单状态
    function updateInboundState(uint index, ImportState newState) internal{
        OrderEntity storage order = Entity[index];
        order.inboundEntity.importState = newState;
    }

    //客户贷款后，入库车辆更改为质押状态
    function ImpawnWhenLoaded(uint index) public returns(bool){
        updateInboundState(index, ImportState.Impawned);
        return true;
    }


    //客户还款后，入库车辆更改为解押状态
    function ReleaseWhenRefunded(uint index) public returns(bool){
        updateInboundState(index, ImportState.Released);
        return true;
    }
}
