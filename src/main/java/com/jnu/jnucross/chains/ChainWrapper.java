package com.jnu.jnucross.chains;

import org.fisco.bcos.sdk.client.protocol.response.BcosBlock;
import org.fisco.bcos.sdk.client.protocol.response.BcosTransactionReceipt;
import org.fisco.bcos.sdk.client.protocol.response.BlockNumber;
import org.fisco.bcos.sdk.model.TransactionReceipt;

import java.io.IOException;
import java.math.BigInteger;

/**
 * @author SDKany
 * @ClassName ChainWapper
 * @Date 2023/8/19 11:34
 * @Version V1.0
 * @Description 用于封装5条链
 */
public abstract class ChainWapper {
    public abstract Block getBlockByNumber(long blockNumber);

    public abstract Block getBlockByHash(String blockHash);

    public abstract long getBlockNumber();

    public abstract Transaction getTransaction(String transactionHash);
}
