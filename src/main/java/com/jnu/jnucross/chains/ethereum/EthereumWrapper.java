package com.jnu.jnucross.chains.ethereum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.jnucross.chains.*;
import com.jnu.jnucross.chains.ethereum.generated.SimpleStorage;
import org.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;


import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

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
    static ObjectMapper objectMapper = new ObjectMapper();

    public EthereumWrapper(){
        geth_url = "http://10.154.24.12:8545";
        web3j = Web3j.build(new HttpService(geth_url));
        try {
            credentials = WalletUtils.loadJsonCredentials("", "{\"address\":\"07c65a8e7aea175283057afa9fb6ac28d43cb69d\",\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"7284312eb0495fac8962de86aaf0960a4e8a047180d5a3d51ff02c7e6f3bea08\",\"cipherparams\":{\"iv\":\"df390e190df78bf2f002ce6704ffe1b3\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":4096,\"p\":6,\"r\":8,\"salt\":\"6eaafa73bf89c18816dd6172fb17cefe77711903a83ee144ff8d9bad2159d907\"},\"mac\":\"bb70c6736b21e6a145ec23cc0a968c11e6f39e1b50fccf9cc28f19c6c5ebf22d\"},\"id\":\"ca3cf343-8d2b-4a81-b037-f5e2046bf4db\",\"version\":3}");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EthereumWrapper chainWrapper = new EthereumWrapper();

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

        SimpleStorage contract = null;
        try {
            BigInteger gasPrice = chainWrapper.web3j.ethGasPrice().send().getGasPrice();
            System.out.println(gasPrice);
            ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, BigInteger.valueOf(8_000_000));
            TransactionManager transactionManager = new RawTransactionManager(chainWrapper.web3j, chainWrapper.credentials, 1337);
            contract = SimpleStorage.deploy(chainWrapper.web3j,transactionManager,gasProvider).send();
            System.out.println(contract.getContractAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void deployContract(String bin, String abi){
        //RawTransaction.createContractTransaction();
    }

    public static Block covertToBlock(EthBlock.Block ethBlock){
        Block block = new Block();
        BlockHeader blockHeader = new BlockHeader();
        blockHeader.setNumber(ethBlock.getNumber().longValue());
        blockHeader.setHash(ethBlock.getHash());
        blockHeader.setPrevHash(ethBlock.getParentHash());
        blockHeader.setReceiptRoot(ethBlock.getReceiptsRoot());
        blockHeader.setTransactionRoot(ethBlock.getTransactionsRoot());
        blockHeader.setStateRoot(ethBlock.getStateRoot());
        block.setBlockHeader(blockHeader);
        block.setChainType(EnumType.ChainType.Ethereum);
        List<EthBlock.TransactionResult> transactionResults = ethBlock.getTransactions();
        List<String> transactionsHashes = new ArrayList<>();
        for (EthBlock.TransactionResult transaction : transactionResults) {
            transactionsHashes.add(((EthBlock.TransactionObject)transaction.get()).getHash());
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

    public static Transaction coverToTransaction(org.web3j.protocol.core.methods.response.Transaction ethTransaction){
        Transaction transaction = new Transaction();
        transaction.setFrom(ethTransaction.getFrom());
        transaction.setTo(ethTransaction.getTo());
        transaction.setHash(ethTransaction.getHash());
        transaction.setBlockNumber(Numeric.toBigInt(ethTransaction.getBlockNumberRaw()).longValue());
        try {
            transaction.setRawBytes(objectMapper.writeValueAsBytes(ethTransaction));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            transaction.setRawBytes(new byte[0]);
        }
        transaction.setChainType(EnumType.ChainType.Ethereum);
        return transaction;
    }

    // 给定abi文件和bin文件，生成合约的java文件
    public static void generateClass(String abiFile,String binFile,String generateFile){
        String[] args = Arrays.asList(
                "-a",abiFile,
                "-b",binFile,
                "-p","",
                "-o",generateFile
        ).toArray(new String[0]);
        Stream.of(args).forEach(System.out::println);
        SolidityFunctionWrapperGenerator.main(args);
    }

}
