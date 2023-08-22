package com.jnu.jnucross.chains.bcos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.jnucross.chains.*;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.client.ClientImpl;
import org.fisco.bcos.sdk.client.protocol.model.JsonTransactionResponse;
import org.fisco.bcos.sdk.client.protocol.response.BcosBlock;
import org.fisco.bcos.sdk.client.protocol.response.BlockNumber;
import org.fisco.bcos.sdk.config.Config;
import org.fisco.bcos.sdk.config.ConfigOption;
import org.fisco.bcos.sdk.config.exceptions.ConfigException;
import org.fisco.bcos.sdk.config.model.NetworkConfig;
import org.fisco.bcos.sdk.model.TransactionReceipt;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SDKany
 * @ClassName BCOSWrapper
 * @Date 2023/8/17 22:34
 * @Version V1.0
 * @Description
 */
public class BCOSWrapper extends ChainWrapper {
    public String configFile;
    public BcosSDK sdk;
    public Client client;

    static ObjectMapper objectMapper = new ObjectMapper();

    public BCOSWrapper(){
        super();
    }

    public static BCOSWrapper build() {
        BCOSWrapper bcosWrapper = new BCOSWrapper();
        bcosWrapper.configFile = "./src/main/resources/chains-sample/bcos/config-example.toml";
        bcosWrapper.sdk = BcosSDK.build(bcosWrapper.configFile);
        bcosWrapper.client = bcosWrapper.sdk.getClient(Integer.valueOf(1));
        return bcosWrapper;
    }

    public BCOSWrapper(String tomlPath){
        super();
        configFile = tomlPath;
        sdk = BcosSDK.build(configFile);
        client = sdk.getClient(Integer.valueOf(1));
    }

    public static void main(String[] args) throws IOException {
        ChainWrapper chainWapper = BCOSWrapper.build();
        System.out.println("BlockNumber = " + chainWapper.getBlockNumber()); // 获取块高
        System.out.println("----------------------------------");
        System.out.println("The 1st block is = " + chainWapper.getBlockByNumber(1)); // 通过块高获取块
        System.out.println("----------------------------------");
        // 通过hash获取块
        System.out.println("The block with hash 0xa5487cbf77ea93b757ddce9df3faf0c99653d97c3036c4b32441d5963d51c389 is = " + chainWapper.getBlockByHash("0xa5487cbf77ea93b757ddce9df3faf0c99653d97c3036c4b32441d5963d51c389"));
        System.out.println("----------------------------------");
        // 获取transaction by hash
        System.out.println("Get Transaction by hash = " + chainWapper.getTransaction("0x5ba2c19295185f055fda7f62bb0778510789b28caa86a63692d8708c487733d6"));
        System.out.println("----------------------------------");

        //RawTransaction rawTransaction = new RawTransaction();
        //client.stop();

        System.exit(0);
    }

    @Override
    public BigInteger getBalance(){
        return BigInteger.ZERO;
    }

    @Override
    public void setChain(String url) {
        // todo: bcos的client实例化逻辑较为复杂，且进行了较多的封装，建议生成公私钥之后，产生config-example.toml文件后载入
//        configFile = "./src/main/resources/chains-sample/bcos/config-example.toml";
//        try {
//            ConfigOption configOption = Config.load(configFile);
//            NetworkConfig networkConfig = configOption.getNetworkConfig();
//            List<String> peers = networkConfig.getPeers();
//            peers.add(url);
//            networkConfig.setPeers(peers);
//            configOption.setNetworkConfig(networkConfig);
//            sdk = new BcosSDK(configOption);
//            client = sdk.getClient(Integer.valueOf(1));
//        } catch (ConfigException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void setAccount(String hexPrivateKeyString) {
        // todo
    }

    @Override
    public void setAccount(BigInteger privateKey) {
        // todo
    }


    public Block getBlockByNumber(long blockNumber) {
        BcosBlock.Block bcosBlock = client.getBlockByNumber(BigInteger.valueOf(blockNumber), true).getResult();
        if (bcosBlock == null)
            return new Block();
        return coverToBlock(bcosBlock);
    }

    public Block getBlockByHash(String blockHash) {
        BcosBlock.Block bcosBlock = client.getBlockByHash(blockHash, true).getBlock();
        if (bcosBlock == null)
            return new Block();
        return coverToBlock(bcosBlock);
    }

    public long getBlockNumber() {
        BlockNumber blockNumber = client.getBlockNumber();
        return blockNumber.getBlockNumber().longValue();
    }

    public Transaction getTransaction(String transactionHash) {
        TransactionReceipt bcosTransactionReceipt =
                client.getTransactionReceipt(transactionHash).getResult();
        if (bcosTransactionReceipt == null)
            return new Transaction();
        return coverToTransaction(bcosTransactionReceipt);
    }

    public static Block coverToBlock(BcosBlock.Block bcosBlock){
        Block block = new Block();
        BlockHeader blockHeader = new BlockHeader();
        blockHeader.setNumber(bcosBlock.getNumber().longValue());
        blockHeader.setHash(bcosBlock.getHash());
        blockHeader.setPrevHash(bcosBlock.getParentHash());
        blockHeader.setReceiptRoot(bcosBlock.getReceiptsRoot());
        blockHeader.setTransactionRoot(bcosBlock.getTransactionsRoot());
        blockHeader.setStateRoot(bcosBlock.getStateRoot());
        block.setBlockHeader(blockHeader);
        block.setChainType(EnumType.ChainType.BCOS);
        List<BcosBlock.TransactionResult> transactionResults = bcosBlock.getTransactions();
        List<String> transactionsHashes = new ArrayList<>();
        for (BcosBlock.TransactionResult transaction : transactionResults) {
            transactionsHashes.add(((JsonTransactionResponse)transaction.get()).getHash());
        }
        block.setTransactionsHashes(transactionsHashes);
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(block);
            block.setRawBytes(bytes);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            block.setRawBytes(new byte[0]);
        }
        return block;
    }

    public static Transaction coverToTransaction(TransactionReceipt bcosTransactionReceipt){
        Transaction transaction = new Transaction();
        transaction.setFrom(bcosTransactionReceipt.getFrom());
        transaction.setTo(bcosTransactionReceipt.getTo());
        transaction.setHash(bcosTransactionReceipt.getTransactionHash());
        transaction.setBlockNumber(Numeric.toBigInt(bcosTransactionReceipt.getBlockNumber()).longValue());
        try {
            transaction.setRawBytes(objectMapper.writeValueAsBytes(bcosTransactionReceipt));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            transaction.setRawBytes(new byte[0]);
        }
        transaction.setChainType(EnumType.ChainType.BCOS);
        return transaction;
    }
}
