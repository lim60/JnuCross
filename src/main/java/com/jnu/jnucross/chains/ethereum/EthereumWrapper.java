package com.jnu.jnucross.chains.ethereum;

import com.jnu.jnucross.chains.*;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

import static com.jnu.jnucross.chains.ethereum.EthereumUtils.coverToTransaction;
import static com.jnu.jnucross.chains.ethereum.EthereumUtils.covertToBlock;

/**
 * @author SDKany
 * @ClassName EthereumWrapper
 * @Date 2023/8/18 22:46
 * @Version V1.0
 * @Description
 */
public class EthereumWrapper extends ChainWrapper {
    String geth_url; // = "http://10.154.24.12:8545";
    Web3j web3j;// = Web3j.build(new HttpService(geth_url));
    Credentials credentials;//  = null;

    public EthereumWrapper(){
        super();
    }

    public EthereumWrapper(String url, BigInteger privateKey){
        super();
        geth_url = url;
        web3j = Web3j.build(new HttpService(geth_url));
        try {
            credentials = Credentials.create(ECKeyPair.create(privateKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EthereumWrapper(String url, String hexPrivateKeyString){
        super();
        geth_url = url;
        web3j = Web3j.build(new HttpService(geth_url));
        try {
            credentials = Credentials.create(hexPrivateKeyString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EthereumWrapper build(){
        EthereumWrapper ethereumWrapper = new EthereumWrapper();
        ethereumWrapper.geth_url = "http://10.154.24.12:8545";
        ethereumWrapper.web3j = Web3j.build(new HttpService(ethereumWrapper.geth_url));
        try {
            ethereumWrapper.credentials = WalletUtils.loadJsonCredentials("", "{\"address\":\"c2582ae1deec5af85028e0a089ec87286c4f8d6e\",\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"7d2b1b3fb0d1e1758b90a1419bdb52d4b3a421c68d8b57dcee8544f51b7c9e5e\",\"cipherparams\":{\"iv\":\"853a3bc72ca80013dd4577f58aca4ea5\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":4096,\"p\":6,\"r\":8,\"salt\":\"0a63e01bc2df1d8f15357faedd6a46e8e184789e90c251e65d289ce130cc04ae\"},\"mac\":\"d15a6a86df4aea6dc0e730d5ed1ae473e5549531e9eb0aa547c8f75d5a9a9f5c\"},\"id\":\"f1d8f10c-e153-4274-8697-d80ccc4c4069\",\"version\":3}");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        return ethereumWrapper;
    }

    @Override
    public void setChain(String url) {
        geth_url = url;
        web3j = Web3j.build(new HttpService(geth_url));
    }

    @Override
    public void setAccount(String hexPrivateKeyString) {
        try {
            credentials = Credentials.create(hexPrivateKeyString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAccount(BigInteger privateKey) {
        try {
            credentials = Credentials.create(ECKeyPair.create(privateKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EthereumWrapper chainWrapper = EthereumWrapper.build();

        System.out.println("Balance = " + chainWrapper.getBalance());

        System.out.println("BlockNumber = " + chainWrapper.getBlockNumber()); // 获取块高
        System.out.println("----------------------------------");
        System.out.println("The 1st block is = " + chainWrapper.getBlockByNumber(600)); // 通过块高获取块
        System.out.println("----------------------------------");
//        // 通过hash获取块
//        System.out.println("The block with hash 0x9b90bb072bf7a06c1cda8f214b9a29eb3a4280b44a44f9a2aee1a4c5d6de695c is = " + chainWapper.getBlockByHash("0x9b90bb072bf7a06c1cda8f214b9a29eb3a4280b44a44f9a2aee1a4c5d6de695c"));
//        System.out.println("----------------------------------");
////        // 获取transaction by hash
//        System.out.println("Get Transaction by hash = " + chainWapper.getTransaction("0x9045253663cfd4ccaa4664e196837bd21223767e4b8e19e40dcabc158e750c45"));
//        System.out.println("----------------------------------");

        //RawTransaction rawTransaction = new RawTransaction();
        //client.stop();
        // 生成合约java代码
        //generateClass("src/main/resources/chains-sample/ethereum/contract/SimpleStorage.abi", "src/main/resources/chains-sample/ethereum/contract/SimpleStorage.bin", "src/main/java/com/jnu/jnucross/chains/ethereum/generated");


        //System.out.println(contract.getContractAddress());

//        contract = SimpleStorage.load("0xE3720A6D1dA0b27aCd735aA5Bc121d7AbD55Ff68",chainWapper.web3j,chainWapper.credentials,
//                GAS_PRICE,GAS_LIMIT);

        System.exit(0);

    }


    @Override
    public BigInteger getBalance(){
        try {
            return web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameter.valueOf("latest")).send().getBalance();
        } catch (IOException e) {
            e.printStackTrace();
            return BigInteger.valueOf(0);
        }
    }

    @Override
    public long getBlockNumber() {
        try {
            return web3j.ethBlockNumber().send().getBlockNumber().longValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Block getBlockByNumber(long blockNumber) {
        try {
            EthBlock.Block ethBlock = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(blockNumber)), true).send().getBlock();
            return covertToBlock(ethBlock);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Block();
    }

    @Override
    public Block getBlockByHash(String blockHash) {
        try {
            EthBlock.Block ethBlock = web3j.ethGetBlockByHash(blockHash,true).send().getBlock();
            return covertToBlock(ethBlock);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Block();
    }


    @Override
    public Transaction getTransaction(String transactionHash) {
        try {
            org.web3j.protocol.core.methods.response.Transaction ethTransaction = web3j.ethGetTransactionByHash(transactionHash).send().getTransaction().get();
            return coverToTransaction(ethTransaction);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Transaction();
    }

}
