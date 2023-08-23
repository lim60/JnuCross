package com.jnu.jnucross.adapter;
// by yanlin
import com.webank.wecross.network.rpc.handler.XATransactionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* request received from the client */
public class TransactionRequestClient {

    // they are information received from the client
    private int txID;
    private List<String> paths;   // path of resource
    private String method; // the method to invocation:[get, set, transfer.....]
    private List<String> args; // the arguments of method [e.g., Alice, Oscar]
    private int chain_id;
    private int initiate_account_id;
    private int smart_contract_id;
    private int seq;    // the execution sequence
    private String type; // call or sendTransaction
    private  String transaction_ip; //交易地址
    private int chain_type;

    /** format of option:
      * options: {
      *  'XA_TRANSACTION_ID': this.transactionForm.transactionID,
      *  'XA_TRANSACTION_SEQ': Date.now()+2
      *  }
      **/
    private Map<String, Object> options = new HashMap<String, Object>();

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    //end
    public TransactionRequestClient() {}
    private static Logger logger = LoggerFactory.getLogger(XATransactionHandler.class);
    public int gettxID() {
        return txID;
    }
    public void settxID(int txID) {
        this.txID = txID;
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
    public String getType(){return type;}  // the type: contractInvocation or Transaction
    public void setType(String type){this.type = type;}
    public int getChain() {return chain_id;}
    public int getChain_type() {
        return chain_type;
    }

    public int getInitiate_account_id() {
        return initiate_account_id;
    }

    public int getSmart_contract_id() {
        return smart_contract_id;
    }
    public long getSeq()
    {
        return seq;
    }

    // Obtain all transaction information from the client
    public static List<TransactionInfo> obtainTransactionList(List<TransactionRequestClient> transactionRequests, String XATransactionID){
        List<TransactionInfo>alltransactions = new ArrayList<>();

        for (TransactionRequestClient transactionRequest: transactionRequests){

            logger.info("transactionID: "+transactionRequest.gettxID());
            TransactionInfo node
                    = new TransactionInfo(
                    transactionRequest.gettxID(),
                    transactionRequest.getPaths(),
                    transactionRequest.getMethod(),
                    transactionRequest.getArgs(),
                    transactionRequest.getSeq()
            );
            node.setType(transactionRequest.getType());
            //node.setUsername(transactionRequest.getUserName());
            node.setOptions(transactionRequest.getOptions());
            node.setXaTransactionID(XATransactionID);
            node.setStatus(1);
            node.setChain_type(transactionRequest.getChain_type());
            DatabaseUtil.updateTransaction(node,"insert");
            alltransactions.add(node);
        }

        return alltransactions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(txID).append(" ");
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
}

