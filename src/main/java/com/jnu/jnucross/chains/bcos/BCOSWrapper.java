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
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.crypto.keypair.ECDSAKeyPair;
import org.fisco.bcos.sdk.crypto.keystore.KeyTool;
import org.fisco.bcos.sdk.model.TransactionReceipt;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

import static com.jnu.jnucross.chains.bcos.BCOSUtils.coverToBlock;
import static com.jnu.jnucross.chains.bcos.BCOSUtils.coverToTransaction;

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
        BCOSWrapper chainWapper = BCOSWrapper.build();
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
        System.out.println("old address");
        System.out.println(chainWapper.client.getCryptoSuite().getCryptoKeyPair().getAddress());
        ECDSAKeyPair ecdsaKeyPair = new ECDSAKeyPair();
        chainWapper.setAccount(ecdsaKeyPair.getHexPrivateKey());
        System.out.println("new address");
        System.out.println(chainWapper.client.getCryptoSuite().getCryptoKeyPair().getAddress());





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
        KeyPair keyPair = KeyTool.convertHexedStringToKeyPair(hexPrivateKeyString, CryptoKeyPair.ECDSA_CURVE_NAME);
        CryptoKeyPair cryptoKeyPair = new ECDSAKeyPair(keyPair);
        client.getCryptoSuite().setCryptoKeyPair(cryptoKeyPair);
    }

    @Override
    public void setAccount(BigInteger privateKey) {
        KeyPair keyPair = KeyTool.convertPrivateKeyToKeyPair(privateKey, CryptoKeyPair.ECDSA_CURVE_NAME);
        CryptoKeyPair cryptoKeyPair = new ECDSAKeyPair(keyPair);
        client.getCryptoSuite().setCryptoKeyPair(cryptoKeyPair);
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

}
