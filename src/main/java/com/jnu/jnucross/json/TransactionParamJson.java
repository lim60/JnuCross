package com.jnu.jnucross.json;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 交易参数Json
 */
@Data
public class TransactionParamJson {
    /**
     * 收款账户id
     */
    private Long receiverAccountId;
    /**
     * 金额
     */
    private BigDecimal money;

}
