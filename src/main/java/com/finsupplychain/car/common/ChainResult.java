package com.finsupplychain.car.common;
import com.jnu.jnucross.chains.Transaction;

import java.util.List;

public class ChainResult {

    private List conResult;
    private Transaction traResult;


    public List getConResult() {
        return conResult;
    }

    public void setConResult(List conResult) {
        this.conResult = conResult;
    }

    public Transaction getTraResult() {
        return traResult;
    }

    public void setTraResult(Transaction traResult) {
        this.traResult = traResult;
    }
}
