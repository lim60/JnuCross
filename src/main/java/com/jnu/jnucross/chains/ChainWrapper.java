package com.jnu.jnucross.chains;

import com.citahub.cita.crypto.Credentials;
import com.citahub.cita.protocol.CITAj;
import com.citahub.cita.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * @author SDKany
 * @ClassName ChainWrapper
 * @Date 2023/8/19 11:34
 * @Version V1.0
 * @Description 用于封装5条链
 */
public abstract class ChainWrapper {
    public abstract Block getBlockByNumber(long blockNumber) throws IOException;

    public abstract Block getBlockByHash(String blockHash) throws IOException;

    public abstract long getBlockNumber() throws IOException;

    public abstract Transaction getTransaction(String transactionHash) throws IOException;

    public abstract BigInteger getBalance() throws IOException;

    public abstract void setChain(String url);

    public abstract void setAccount(String hexPrivateKeyString);

    public abstract void setAccount(BigInteger privateKey);

//    public abstract void deploy();

    public abstract FunctionResult call(String abi, String contractName, String contractAddress, String method, List<String> args) throws Exception;

    public abstract FunctionResult send(String abi, String contractName, String contractAddress, String method, List<String> args, boolean payable, BigInteger amount, boolean wait) throws Exception;

}
