package com.jnu.jnucross.adapter;

// by yanlin
import com.webank.wecross.stub.TransactionResponse;

import java.time.ZonedDateTime;
import java.util.*;


// add negihbor information to each transaction, so that we can construct the DAG.
public class TransactionInfo {
    private int transactionID;
    private List<String> paths;   // path of resource
    private List<TransactionInfo> neighbors; // the next transactions going to be executed
    private int chain_id;   // id of the chain: [BCOS, FABRIC]
    private int initiate_account_id;
    private int smart_contract_id;
    private String method; // the method to invocation:[get, .....]
    private List<String> args; // the arguments of method [Alice, Oscar]
    private long seq;    // the execution sequence
    //private String username;
    private String type; // contractInvocation or transaction
    private TransactionResponse response;
    private int status = 1;    // the status of a transaction
    private String XaTransactionID;
    private String startTimestamp; // 时间
    private ZonedDateTime EndTimestamp;
    private String rollbackTimestamp;
    private String ErrorMessage;
    private String ipAddr;
    private Map<String, Object> options = new HashMap<String, Object>();


    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public String getErrorMessage(){
        return ErrorMessage;
    }

    public void setXaTransactionID(String xaTransactionID) {
        XaTransactionID = xaTransactionID;
    }

    public String getXaTransactionID() {
        return XaTransactionID;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public void setResponse(TransactionResponse response){
        this.response = response;
    }

    public TransactionResponse getResponse(){
        return this.response;
    }

    public List<TransactionInfo> getNeighbors()
    {
        return neighbors;
    }

    // This is the constructor for transactions that require arguments
    public  TransactionInfo (int id, List<String> paths,String method, List<String> args,long seq) {
        this.response = new TransactionResponse();
        this.transactionID = id;
        //this.chain_id = chainId;
        this.paths = paths;
        this.method = method;
        this.args = args;
        this.neighbors = new ArrayList<>();
        this.seq = seq;
    }

    public TransactionInfo(long seq){
        this.seq = seq;
        this.neighbors = new ArrayList<>();
    }

    public void addNeighbor(TransactionInfo neighbor) {
        neighbors.add(neighbor);
    }

    public TransactionInfo(int transactionID, List<String> paths) {
        this.transactionID = transactionID;
        this.paths = paths;
    }

    public TransactionInfo() {}
    //end
    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public  List<String> getPaths() {
        return paths;
    }

    public  String getMethod() {
        return method;
    }

    public  List<String> getArgs() {
        return args;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    //public void setStatus(String status){this.response.setMessage(status);}
    public void setStatus(int status){this.status = status;}

    public int getStatus(){
        return this.status;
    }

    public void setBlockNumber(int blockNumer){this.response.setBlockNumber(blockNumer);}
    public int getBlockNumber(){return (int) this.response.getBlockNumber();}


    public String getType(){return type;}  // the type: contractInvocation or Transaction
    public void setType(String type){this.type = type;}

    public String[] getResult(){
        return this.response.getResult();
    }

    public void setChainId(int chain_id) {
        this.chain_id = chain_id;
    }

    public int getInitiate_account_id() {
        return initiate_account_id;
    }

    public void setInitiate_account_id(int initiate_account_id) {
        this.initiate_account_id = initiate_account_id;
    }

    public int getSmart_contract_id() {
        return smart_contract_id;
    }

    public void setSmart_contract_id(int smart_contract_id) {
        this.smart_contract_id = smart_contract_id;
    }

    public String toResultString(){
        StringBuilder sb = new StringBuilder();
        for (String item : this.getResult()) {
            sb.append(item).append(" ");
        }
        return sb.toString();
    }
    public void setResult(String[] result){this.response.setResult(result);}

    public String getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(String startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public ZonedDateTime getEndTimestamp() {
        return EndTimestamp;
    }

    public void setEndTimestamp(ZonedDateTime EndTimestamp) {
        this.EndTimestamp = EndTimestamp;
    }

    public String getRollbackTimestamp() {
        return rollbackTimestamp;
    }

    public void setRollbackTimestamp(String rollbackTimestamp) {
        this.rollbackTimestamp = rollbackTimestamp;
    }

    public int getChainId(){return chain_id;}

    //public void setUsername(String username){this.username = username;}
    public long getSeq()
    {
        return seq;
    }
    public String getNeighbor(){
        StringBuilder sb = new StringBuilder();
        for (TransactionInfo neighbor : neighbors) {
            sb.append(neighbor.transactionID).append(" ");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(transactionID).append(" ");
        for (String path : paths) {
            sb.append(path).append(" ");
        }
        return sb.toString();
    }

    public String toPathString() {
        StringBuilder sb = new StringBuilder();
        for (String path : paths) {
            sb.append(path).append(" ");
        }
        return sb.toString();
    }

    public String toArgsString() {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }
        return sb.toString();
    }

    public String getTxHash(){
        return this.response.getHash();
    }

    public void setTxHash(String txHash) {
        this.response.setHash(txHash);
    }

}
