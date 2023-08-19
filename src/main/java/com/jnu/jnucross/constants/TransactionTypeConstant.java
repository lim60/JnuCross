package com.jnu.jnucross.constants;

//事务类型：0，跨链转账；1，合约调用；2，原子交换；
public interface TransactionTypeConstant {

    /**
     * 跨链转账
     */
    Integer CROSS_CHAIN_TRANSFER = 0;
    /**
     * 合约调用
     */
    Integer CONTRACT_CALL = 1;
    /**
     * 原子交换
     */
    Integer ATOMIC_EXCHANGE = 2;
}
