package com.finsupplychain.car.common;
import com.jnu.jnucross.chains.Transaction;

import java.util.List;

public class ChainResult {

    private List conResult;
    private Transaction txResult;


    public List getConResult() {
        return conResult;
    }

    public void setConResult(List conResult) {
        this.conResult = conResult;
    }

    public Transaction getTxResult() {
        return txResult;
    }

    public void setTxResult(Transaction txResult) {
        this.txResult = txResult;
    }
}
