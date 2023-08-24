package com.jnu.jnucross.constants;

//交易类型：0，余额转账；1，合约调用
public interface TransactionTypeConstant {

    /**
     * 余额转账
     */
    Integer BALANCE_TRANSFER  = 0;
    /**
     * 合约调用
     */
    Integer CONTRACT_CALL = 1;
}
