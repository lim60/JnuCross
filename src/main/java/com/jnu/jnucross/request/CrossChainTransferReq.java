package com.jnu.jnucross.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 跨链转账请求
 */
@Data
public class CrossChainTransferReq {


    /**
     * 事务idid-uuid
     */
    private String transactionId;
    /**
     * 发起账户id  A1
     */
    private Long launchAccountId;
    /**
     * 参与账户id
     */
    private Long participateAccountId;
    /**
     * 寻址策略
     */
    private Integer addressingStrategy;
    /**
     * 区块链ids
     */
    private String chainIds;

    /**
     * 金额
     */
    private BigDecimal money;



}
