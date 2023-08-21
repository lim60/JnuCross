package com.jnu.jnucross.chains;

/**
 * @author SDKany
 * @ClassName ChainWrapper
 * @Date 2023/8/19 11:34
 * @Version V1.0
 * @Description 用于封装5条链
 */
public abstract class ChainWrapper {
    public abstract Block getBlockByNumber(long blockNumber);

    public abstract Block getBlockByHash(String blockHash);

    public abstract long getBlockNumber();

    public abstract Transaction getTransaction(String transactionHash);

//    public abstract void call();
//
//    public abstract void send();

    // TODO: call 查询， send 发送，
}
