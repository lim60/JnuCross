package com.jnu.jnucross.adapter;

// by yanlin
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.webank.wecross.account.UniversalAccount;
import com.webank.wecross.common.NetworkQueryStatus;
import com.webank.wecross.exception.WeCrossException;
import com.webank.wecross.host.WeCrossHost;
import com.webank.wecross.network.rpc.authentication.RemoteAuthFilter;
import com.webank.wecross.network.rpc.handler.URIHandler;
import com.webank.wecross.resource.Resource;
import com.webank.wecross.restserver.RestRequest;
import com.webank.wecross.restserver.RestResponse;
import com.webank.wecross.routine.htlc.HTLCManager;
import com.webank.wecross.routine.xa.XAResponse;
import com.webank.wecross.routine.xa.XATransactionManager;
import com.webank.wecross.stub.*;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** POST/GET /xa/method/ **/

public class Adapter {
    private static final Logger logger = LoggerFactory.getLogger(Adapter.class);
    private static final ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** possible status for a raw transaction **/
    public enum TransactionStatus {
        created,
        waiting,
        executing,
        success,
        fail,
        rollbacking,
        rollbacked
    }
    /** possible status for a cross chain transaction **/
    public enum XATransactionStatus {
        executing,
        success,
        fail,
        rollbacked
    }

    /* supported transactions */
    public enum CrossTransactionType{
        cross_chain_transfer,
        contract_invocation,
        atomic_swap,
    }

    /* supported blockchains */


    /* may not be used */
    public static class TransactionErrorCode {
        public static final int SUCCESS = 0;
        public static final int REMOTE_QUERY_FAILED = StubQueryStatus.REMOTE_QUERY_FAILED; // 100
        public static final int TIMEOUT = StubQueryStatus.TIMEOUT; // 200
        public static final int ACCOUNT_ERRPR = 300; // 300
        public static final int INTERNAL_ERROR = 1001;
    }

    /** received cross chain transaction data from the client **/
    public static  class XATransactionRequest {

        // added by yanlin
        private  int type;    // type of the cross chain transaction
        //end
        private String xaTransactionID;   //ID of cross chain transaction
        private Set<String> paths;   // all paths needed in the cross chain transaction
        // added by yanlin
        private List<TransactionRequestClient> transactionRequests; // information for each raw transaction
        //end

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public String getXaTransactionID() {
            return xaTransactionID;
        }

        public void setXaTransactionID(String xaTransactionID) {
            this.xaTransactionID = xaTransactionID;
        }

        public Set<String> getPaths() {
            return paths;
        }

        public List<TransactionRequestClient> getTransactionRequests(){
            return transactionRequests;
        }

        public void setPaths(Set<String> paths) {
            this.paths = paths;
        }
    }

    /* may not be used */
    public static  class ListXATransactionsRequest {
        private int size;
        private Map<String, Long> offsets = Collections.synchronizedMap(new HashMap<>());

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public Map<String, Long> getOffsets() {
            return offsets;
        }

        public void setOffsets(Map<String, Long> offsets) {
            this.offsets = offsets;
        }
    }

    /* obtain the resource object according to the path */
    private static Resource getResource(Path path,WeCrossHost host) {
        Resource resourceObj;
        try {
            resourceObj = host.getZoneManager().fetchResource(path);
        } catch (Exception e) {
            logger.error("getResource error", e);
            return null;
        }
        if (resourceObj == null) {
            logger.error("Unable to find resource: {}", path);
        } else {
            HTLCManager htlcManager = host.getRoutineManager().getHtlcManager();
            resourceObj = htlcManager.filterHTLCResource(host.getZoneManager(), path, resourceObj);
        }

        return resourceObj;
    }

    public static Path obtain_path(String Txpath){
        Path path = new Path();
        String[] splits = Txpath.split("/");
        logger.info("splits "+splits);
        for (String i: splits){
            logger.info("each splits "+i);
        }
        logger.info("path information: "+splits[1]+"/"+splits[2]+"/"+splits[3]);
        path.setZone(splits[1]);
        path.setChain(splits[2]);
        if (splits.length >= 4) {
            path.setResource(splits[3]);
        }
        return path;
    }

    /** obtain all raw transaction from the request of client *
     * We also construct the transaction graph according to the seq
     **/
    public static List<TransactionInfo> obtainAllTransactions(RestRequest<XATransactionRequest> Requests) throws ExecutionException, InterruptedException{
        String XATransactionID =  Requests.getData().getXaTransactionID();
        List<TransactionInfo> allTransactions = TransactionRequestClient.obtainTransactionList(
                Requests.getData().getTransactionRequests(),XATransactionID);

        TransactionGraph.constructTransactionGraph(allTransactions);
        List<TransactionInfo> bfsResult = TransactionGraph.bfsSearch(allTransactions.get(0));
        for (TransactionInfo tx : bfsResult) {
            logger.info("bfsResult: " + tx.getTransactionID());
        }
        return bfsResult;
    }

    public static Set<Path> filterAndSortChainPaths(Set<String> paths) throws WeCrossException {
        Set<String> tempPaths = new HashSet<>();
        for (String path : paths) {
            try {
                String[] splits = path.split("\\.");
                tempPaths.add(splits[0] + "." + splits[1]);
            } catch (Exception e) {
                throw new WeCrossException(
                        WeCrossException.ErrorCode.POST_DATA_ERROR, "Invalid path found");
            }
        }
        Set<Path> sortSet = new TreeSet<>(Comparator.comparing(Path::toString));
        sortSet.addAll(decodePathSet(tempPaths));
        return sortSet;
    }

    private static Set<Path> decodePathSet(Set<String> paths) throws WeCrossException {
        Set<Path> res =
                paths.parallelStream()
                        .map(
                                (s) -> {
                                    try {
                                        return Path.decode(s);
                                    } catch (Exception e) {
                                        logger.error("Decode path error: ", e);
                                        return null;
                                    }
                                })
                        .collect(Collectors.toSet());

        if (res.isEmpty() || res.size() < paths.size()) {
            throw new WeCrossException(
                    WeCrossException.ErrorCode.POST_DATA_ERROR, "Invalid path found");
        }

        return res;
    }

    /* may not be used */
    /*public static void executeHTLCTransactions(UniversalAccount ua,
                                               List<TransactionInfo> bfsResult,
                                               int index,
                                               TransactionResponse previousResponse,
                                               RestRequest<XATransactionRequest> xaRequest,
                                               WeCrossHost host){
        if (index >= bfsResult.size()) {
            logger.info("All transactions executed.");
            return;
        }

        TransactionInfo item = bfsResult.get(index);
        List<String> txpaths = item.getPaths();
        logger.info("txpaths " + txpaths.get(0));

        Path path = obtain_path(txpaths.get(0));
        String tx_method = item.getMethod();
        List<String> method_args = item.getArgs();

        Map<String, Object> options = item.getOptions();
        logger.info("before calling getResource");
        Resource resourceObj = getResource(path,host);
        logger.info("resourceobj" + resourceObj);
        for (Map.Entry<String, Object> entry : options.entrySet()) {
            logger.info("options: " + entry.getKey());
            logger.info("options: " + entry.getValue());
        }
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setMethod(tx_method);
        transactionRequest.setOptions(options);

        if (Objects.equals(tx_method, "newProposal")){
            transactionRequest.setArgs(method_args.toArray(new String[0]));
        }
        if (Objects.equals(tx_method, "setNewProposalTxInfo")){
            List<String>final_args = new ArrayList<>();
            String hash = method_args.get(0);
            String txHash = previousResponse.getHash();
            String blockNumber = String.valueOf(previousResponse.getBlockNumber());
            final_args.add(hash);
            final_args.add(txHash);
            final_args.add(blockNumber);
            transactionRequest.setArgs(final_args.toArray(new String[0]));
        }
        //只有交易发起方需要执行setSecret交易
        if (Objects.equals(tx_method, "setSecret")){
            transactionRequest.setArgs(method_args.toArray(new String[0]));
        }
        assert resourceObj != null;
        resourceObj.asyncSendTransaction(transactionRequest, ua, (transactionException, transactionResponse) -> {
            logger.info(item.getTransactionID() + " TransactionResponse: {}, TransactionException, ", transactionResponse, transactionException);
            item.setResponse(transactionResponse);
            //DatabaseUtil.writeNewTransactionToMySql(item);

            if (transactionException != null) {
                //TODO:处理交易异常
            }
            else {
                // execute next transaction
                if (transactionResponse.getErrorCode() !=0){
                    //rollback
                }
                else {
                    int nextIndex = index + 1;
                    executeHTLCTransactions(ua, bfsResult, nextIndex, transactionResponse, xaRequest,host);
                }
            }
        });
    }*/


    /* roll back the raw transactions that have been executed after a raw transaction failed */
    public static void rollbackPreviousTransactions(List<TransactionInfo> bfsResult, int index,
                                                    RestRequest<XATransactionRequest> xaRequest,
                                                    UniversalAccount ua,
                                                    XATransactionManager xaTransactionManager){

        try {
            logger.info("now try to rollback the executed transactions");
            xaTransactionManager.asyncRollbackXATransaction(
                    xaRequest.getData().getXaTransactionID(),
                    ua,
                    filterAndSortChainPaths(xaRequest.getData().getPaths()),
                    (response) -> {
                        if (logger.isDebugEnabled()) {
                            logger.debug(
                                    "rollbackXATransaction, final response: {}",
                                    response);
                        }
                        /* Setting the status of a transaction to be rollbacked */
                        for (int i = index-1; i >= 0; i--){
                            TransactionInfo previous_transaction = bfsResult.get(i);

                            previous_transaction.setStatus(TransactionStatus.rollbacking.ordinal());
                            DatabaseUtil.updateTransaction(previous_transaction,"status");

                            previous_transaction.setStatus(TransactionStatus.rollbacked.ordinal());
                            ZonedDateTime rollbackTime =  ZonedDateTime.now();
                            previous_transaction.setEndTimestamp(rollbackTime);
                            logger.info("status after rollback "+ previous_transaction.getTransactionID()
                                    +" "+previous_transaction.getStatus());

                            DatabaseUtil.updateTransaction(previous_transaction,"status");
                        }
                    });
        }catch (WeCrossException e) {
            logger.error("Error while processing xa: ", e);
        } catch (Exception e) {
            logger.error("Error while processing xa: ", e);
        }
    }

    /* may not be used */
    public static void setTransactionExceptionMessage(Integer transactionExceptionCode, TransactionInfo item){
        switch(transactionExceptionCode) {
            case TransactionErrorCode.INTERNAL_ERROR:
                item.setErrorMessage("internal error");
            case TransactionErrorCode.ACCOUNT_ERRPR:
                item.setErrorMessage("account error");
            case TransactionErrorCode.REMOTE_QUERY_FAILED:
                item.setErrorMessage("remote query failed");
            case TransactionErrorCode.TIMEOUT:
                item.setErrorMessage("timeout");
        }
    }

    /* may not be used */
    public static String createErrorMessage(TransactionInfo transaction){
        String message = "ErrorMessage:{"
                + "Chain ="
                + transaction.getChainId()
                + '\''
                + "Path ="
                + transaction.toPathString()
                + '\''
                + ", Method ='"
                + transaction.getMethod()
                + '\''
                + ", Args ='"
                + transaction.toArgsString()
                + '}';

        return message;
    }

    public static void processTransactionError(TransactionInfo item, int index,
                                                   RestRequest<XATransactionRequest> xaRequest,
                                                   List<TransactionInfo> bfsResult,
                                                   XATransactionManager xaTransactionManager,
                                                   UniversalAccount ua)
    {
        /* update the status of a raw transaction to be executing */
        item.setStatus(TransactionStatus.fail.ordinal());
        //setTransactionExceptionMessage(transactionException.getErrorCode(), item);
        //item.setErrorMessage(transactionException.getMessage());
        DatabaseUtil.updateTransaction(item, "fail");
        /* update the status of the cross chain transaction to fail */
        DatabaseUtil.updateXATransaction(XATransactionStatus.fail.ordinal(), item.getXaTransactionID());
        /* once a raw transaction failed, roll back all previously executed raw transactions */
        rollbackPreviousTransactions(bfsResult, index, xaRequest, ua, xaTransactionManager);

        /* update the status of the cross chain transaction to rollbacked */
        ZonedDateTime XAendTime = ZonedDateTime.now();
        DatabaseUtil.updateXATransactionFinish(XATransactionStatus.rollbacked.ordinal(),
                xaRequest.getData().getXaTransactionID(),
                XAendTime);
    }

    /** Execute each raw transaction recursively **/
    public static void executeAllTransactions(UniversalAccount ua, List<TransactionInfo> bfsResult,
                                              int index, RestRequest<XATransactionRequest> xaRequest,
                                              XATransactionManager xaTransactionManager, WeCrossHost host) {

        /** all transactions are executed,
         update the status of the cross chain transaction to success
         **/
        if (index >= bfsResult.size()) {
            logger.info("All transactions executed.");
            ZonedDateTime endTime = ZonedDateTime.now();
            DatabaseUtil.updateXATransactionFinish(XATransactionStatus.success.ordinal(),
                    xaRequest.getData().getXaTransactionID(),
                    endTime);
            return;
        }

        TransactionInfo item = bfsResult.get(index);

        List<String> txpaths = item.getPaths();
        Path path = obtain_path(txpaths.get(0));
        String tx_method = item.getMethod();
        List<String> method_args = item.getArgs();

        Map<String, Object> options = item.getOptions();
        logger.info("before calling getResource");
        Resource resourceObj = getResource(path,host);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setMethod(tx_method);
        transactionRequest.setArgs(method_args.toArray(new String[0]));
        transactionRequest.setOptions(options);

        int chain_type = item.getChain_type();

        switch(chain_type){
            case (ChainType.cita_chain):
            {
                break;
            }
            case (ChainType.ethereum_chain) :
            {
                break;
            }
            case (ChainType.xuperChain):
            {
                break;
            }
            case(ChainType.bcos_chain):
            case(ChainType.farbic_chain): {
                if (Objects.equals(item.getType(), "transaction")) {
                    String startTime = ZonedDateTime.now().format(formatter);
                    item.setStartTimestamp(startTime);
                    /* update the status of a raw transaction to be executing */
                    item.setStatus(TransactionStatus.executing.ordinal());
                    DatabaseUtil.updateTransaction(item, "status");
                    assert resourceObj != null;
                    /* send transaction */
                    resourceObj.asyncSendTransaction(transactionRequest, ua, (transactionException, transactionResponse) -> {
                        ZonedDateTime endTime = ZonedDateTime.now();
                        item.setEndTimestamp(endTime);
                        item.setResponse(transactionResponse);
                        logger.info(item.getTransactionID() + " TransactionResponse: {}, TransactionException, ", transactionResponse, transactionException);

                        if (transactionException != null) {
                            /* update the status of the raw transaction to fail */
                            processTransactionError(item,
                                                        index,
                                                        xaRequest,
                                                        bfsResult,
                                                        xaTransactionManager,
                                                        ua);
                        } else {
                            if (transactionResponse.getErrorCode() != 0) {
                                /* update the status of the raw transaction to fail */
                                processTransactionError(item,
                                        index,
                                        xaRequest,
                                        bfsResult,
                                        xaTransactionManager,
                                        ua);
                            } else {
                                /* raw transaction executed successfully, update its status to success */
                                item.setStatus(TransactionStatus.success.ordinal());
                                DatabaseUtil.updateTransaction(item, "success");
                                int nextIndex = index + 1;
                                // Execute next transaction
                                executeAllTransactions(ua, bfsResult, nextIndex, xaRequest, xaTransactionManager, host);
                            }
                        }
                    });
                } else if (Objects.equals(item.getType(), "call")) {
                    String startTime = ZonedDateTime.now().format(formatter);
                    item.setStartTimestamp(startTime);
                    item.setStatus(TransactionStatus.executing.ordinal());
                    DatabaseUtil.updateTransaction(item, "status");
                    assert resourceObj != null;
                    resourceObj.asyncCall(transactionRequest, ua, (transactionException, transactionResponse) -> {
                        ZonedDateTime endTime = ZonedDateTime.now();
                        item.setEndTimestamp(endTime);
                        item.setResponse(transactionResponse);

                        logger.info(item.getTransactionID() + " TransactionResponse: {}, TransactionException, ", transactionResponse, transactionException);

                        if (transactionException != null) {
                            processTransactionError(item,
                                    index,
                                    xaRequest,
                                    bfsResult,
                                    xaTransactionManager,
                                    ua);
                        } else {
                            if (transactionResponse.getErrorCode() != 0) {
                                processTransactionError(item,
                                        index,
                                        xaRequest,
                                        bfsResult,
                                        xaTransactionManager,
                                        ua);
                            } else {
                                item.setStatus(TransactionStatus.success.ordinal());
                                DatabaseUtil.updateTransaction(item, "success");
                                int nextIndex = index + 1;
                                // execute next transaction
                                executeAllTransactions( ua,
                                                        bfsResult,
                                                        nextIndex,
                                                        xaRequest,
                                                        xaTransactionManager,
                                                        host);
                            }
                        }
                    });
                }
                break;
            }
            default:
                logger.info("unsupported chains");
                new RuntimeException();
                break;
        }
    }

    /* start the cross chain transaction
     * (1): call asyncStartXATransaction to start a XA
     * (2): when the response of asyncStartXATransaction is success, then execute each raw transaction recursively
     */
    public static void startXATransaction(String content,
                                          UniversalAccount ua, RestResponse<XAResponse> restResponse,
                                          XATransactionManager xaTransactionManager,
                                          WeCrossHost host,
                                          URIHandler.Callback callback) {


        try {
            RestRequest<XATransactionRequest> Requests =
                    objectMapper.readValue(
                            content,
                            new TypeReference<RestRequest<XATransactionRequest>>() {
                            });

            ua.getAccessControlFilter()
                    .checkPermissions(
                            Requests.getData().getPaths().toArray(new String[0]));

            List<TransactionInfo> bfsResult = obtainAllTransactions(Requests);
            DatabaseUtil.updateXATransaction(XATransactionStatus.executing.ordinal(), Requests.getData().getXaTransactionID());
            int transactionType = Requests.getData().getType();
            xaTransactionManager.asyncStartXATransaction(
                    Requests.getData().getXaTransactionID(),
                    ua,
                    Requests.getData().getPaths(),
                    (response) -> {
                        //if (logger.isDebugEnabled())
                        {
                            logger.info(
                                    "startXATransaction, final response: {}", response);
                        }
                        restResponse.setData(response);
                        callback.onResponse(restResponse);
                        //DatabaseUtil.updateXATransaction(XATransactionStatus.executing.ordinal(), Requests.getData().getXaTransactionID());
                        /*if (Objects.equals(transactionType, "htlc")) {
                            executeHTLCTransactions(ua, bfsResult, 0, null,Requests,host);
                        } else */
                        if (response.getStatus() == 0)
                        {
                            executeAllTransactions(ua, bfsResult, 0,Requests,xaTransactionManager,host);
                        }
                        else{
                            DatabaseUtil.updateXATransaction(XATransactionStatus.fail.ordinal(), Requests.getData().getXaTransactionID());
                        }
                    });
        }catch (WeCrossException e) {
            logger.error("Error while processing xa: ", e);
            restResponse.setErrorCode(NetworkQueryStatus.XA_ERROR + e.getErrorCode());
            restResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            logger.error("Error while processing xa: ", e);
            restResponse.setErrorCode(NetworkQueryStatus.INTERNAL_ERROR);
            restResponse.setMessage("Undefined error: " + e.getMessage());
        }
    }

    /* commit the cross chain transaction */
    public static void commitTransaction(String content,
                                         UniversalAccount ua, RestResponse<Object> restResponse,
                                         XATransactionManager xaTransactionManager,
                                         URIHandler.Callback callback){
        try {
            logger.info("now commitTransaction");
            RestRequest<XATransactionRequest> xaRequest =
                    objectMapper.readValue(
                            content,
                            new TypeReference<RestRequest<XATransactionRequest>>() {
                            });

            ua.getAccessControlFilter()
                    .checkPermissions(
                            xaRequest.getData().getPaths().toArray(new String[0]));

            xaTransactionManager.asyncCommitXATransaction(
                    xaRequest.getData().getXaTransactionID(),
                    ua,
                    filterAndSortChainPaths(xaRequest.getData().getPaths()),
                    (response) -> {
                        if (logger.isDebugEnabled()) {
                            logger.debug(
                                    "commitXATransaction, final response: {}",
                                    response);
                        }
                        restResponse.setData(response);
                        callback.onResponse(restResponse);
                        if (response.getStatus() ==0){
                            DatabaseUtil.updateXATransaction(XATransactionStatus.success.ordinal(), xaRequest.getData().getXaTransactionID());
                        }
                        else{
                            DatabaseUtil.updateXATransaction(XATransactionStatus.fail.ordinal(),xaRequest.getData().getXaTransactionID());
                        }
                    });
        }catch (WeCrossException e) {
            logger.error("Error while processing xa: ", e);
            restResponse.setErrorCode(NetworkQueryStatus.XA_ERROR + e.getErrorCode());
            restResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            logger.error("Error while processing xa: ", e);
            restResponse.setErrorCode(NetworkQueryStatus.INTERNAL_ERROR);
            restResponse.setMessage("Undefined error: " + e.getMessage());
        }
    }

    /* rollback the cross chain transaction */
    public static void rollbackTransaction(String content,
                                           UniversalAccount ua, RestResponse<Object> restResponse,
                                           XATransactionManager xaTransactionManager,
                                           URIHandler.Callback callback){

        try{
            RestRequest<XATransactionRequest> xaRequest =
                    objectMapper.readValue(
                            content,
                            new TypeReference<RestRequest<XATransactionRequest>>() {
                            });

            ua.getAccessControlFilter()
                    .checkPermissions(
                            xaRequest.getData().getPaths().toArray(new String[0]));

            List<Integer> transactionIds;
            transactionIds = DatabaseUtil.findTransactionFromXA(xaRequest.getData().getXaTransactionID());

            xaTransactionManager.asyncRollbackXATransaction(
                    xaRequest.getData().getXaTransactionID(),
                    ua,
                    filterAndSortChainPaths(xaRequest.getData().getPaths()),
                    (response) -> {
                        if (logger.isDebugEnabled())
                        {
                            logger.debug(
                                    "rollbackXATransaction, final response: {}",
                                    response);
                        }
                        restResponse.setData(response);
                        callback.onResponse(restResponse);
                        /* update the status of each raw transaction and the cross chain transaction */
                        if (response.getStatus() == 0)
                        {
                            logger.info("Try to rollback each transaction");
                            Map<Integer, ZonedDateTime> rollbackTimes = new HashMap<>();
                            for (int i = transactionIds.size() - 1; i >= 0; i--) {
                                int transactionID =  transactionIds.get(i);
                                logger.info("transction id found from xaID "+transactionID);
                                ZonedDateTime endTime = ZonedDateTime.now();
                                rollbackTimes.put(transactionID,endTime);
                            }
                            logger.info("now rollback all transactions");
                            DatabaseUtil.updateTransactionsRollback(transactionIds,rollbackTimes);
                            // update the status and endTime for XATransaction
                            ZonedDateTime endTime = ZonedDateTime.now();
                            DatabaseUtil.updateXATransactionFinish(XATransactionStatus.rollbacked.ordinal(),
                                    xaRequest.getData().getXaTransactionID(),
                                    endTime);

                        }
                    });
        }catch (WeCrossException e) {
            logger.error("Error while processing xa: ", e);
            restResponse.setErrorCode(NetworkQueryStatus.XA_ERROR + e.getErrorCode());
            restResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            logger.error("Error while processing xa: ", e);
            restResponse.setErrorCode(NetworkQueryStatus.INTERNAL_ERROR);
            restResponse.setMessage("Undefined error: " + e.getMessage());
        }
    }

    /* may not use */
    public static void listXATransactions(String content,
                                          UniversalAccount ua, RestResponse<Object> restResponse,
                                          XATransactionManager xaTransactionManager,
                                          WeCrossHost host,
                                          URIHandler.Callback callback){

        try{
            RestRequest<ListXATransactionsRequest> xaRequest =
                    objectMapper.readValue(
                            content,
                            new TypeReference<
                                    RestRequest<ListXATransactionsRequest>>() {
                            });

            xaTransactionManager.asyncListXATransactions(
                    host.getAccountManager().getAdminUA(),
                    xaRequest.getData().getOffsets(),
                    xaRequest.getData().getSize(),
                    (exception, xaTransactionListResponse) -> {
                        if (logger.isDebugEnabled()) {
                            logger.debug(
                                    "listXATransactions, final response: {}, error: ",
                                    xaTransactionListResponse,
                                    exception);
                        }
                        if (Objects.nonNull(exception)) {
                            restResponse.setErrorCode(
                                    NetworkQueryStatus.XA_ERROR
                                            + exception.getErrorCode());
                            restResponse.setMessage(exception.getMessage());
                            callback.onResponse(restResponse);
                            return;
                        }

                        restResponse.setData(xaTransactionListResponse);
                        callback.onResponse(restResponse);
                    });
        }catch (WeCrossException e) {
            logger.error("Error while processing xa: ", e);
            restResponse.setErrorCode(NetworkQueryStatus.XA_ERROR + e.getErrorCode());
            restResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            logger.error("Error while processing xa: ", e);
            restResponse.setErrorCode(NetworkQueryStatus.INTERNAL_ERROR);
            restResponse.setMessage("Undefined error: " + e.getMessage());
        }
    }

    /* may not use */
    public static void getXATransaction(String content,
                                        UniversalAccount ua, RestResponse<Object> restResponse,
                                        XATransactionManager xaTransactionManager,
                                        WeCrossHost host,
                                        URIHandler.Callback callback){
        try{
            RestRequest<XATransactionRequest> xaRequest =
                    objectMapper.readValue(
                            content,
                            new TypeReference<RestRequest<XATransactionRequest>>() {
                            });

            ua.getAccessControlFilter()
                    .checkPermissions(
                            xaRequest.getData().getPaths().toArray(new String[0]));
            xaTransactionManager.asyncGetXATransaction(
                    xaRequest.getData().getXaTransactionID(),
                    host.getAccountManager().getAdminUA(),
                    filterAndSortChainPaths(xaRequest.getData().getPaths()),
                    (xaTransactionResponse) -> {
                        if (logger.isDebugEnabled()) {
                            logger.debug(
                                    "getXATransaction, final response: {}",
                                    xaTransactionResponse);
                        }
                        restResponse.setData(xaTransactionResponse);
                        callback.onResponse(restResponse);
                    });
        }catch (WeCrossException e) {
            logger.error("Error while processing xa: ", e);
            restResponse.setErrorCode(NetworkQueryStatus.XA_ERROR + e.getErrorCode());
            restResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            logger.error("Error while processing xa: ", e);
            restResponse.setErrorCode(NetworkQueryStatus.INTERNAL_ERROR);
            restResponse.setMessage("Undefined error: " + e.getMessage());
        }

    }
}
