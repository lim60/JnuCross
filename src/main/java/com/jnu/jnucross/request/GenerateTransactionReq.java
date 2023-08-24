package com.jnu.jnucross.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 转账事务生成请求
 */
@Data
public class GenerateTransactionReq {


    /**
     * 事务id
     */
    private String crossTransactionId;
    /**
     * 发起账户id  A1
     */
    private Long launchAccountId;
    /**
     * 发起账户名
     */
    private String launchAccountName;
    /**
     * 参与账户id
     */
    private Long participateAccountId;
    /**
     * 参与账户名
     */
    private String participateAccountName;
    /**
     * 区块链ids
     */
    private String chainIds;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 交易id
     */
    private List<Long> transactionIds;





}
