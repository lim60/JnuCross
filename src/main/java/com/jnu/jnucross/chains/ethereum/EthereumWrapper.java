package com.jnu.jnucross.chains.ethereum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.jnucross.chains.*;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SDKany
 * @ClassName EthereumWapper
 * @Date 2023/8/18 22:46
 * @Version V1.0
 * @Description
 */
public class EthereumWapper extends ChainWapper {
    String geth_url; // = "http://10.154.24.12:8545";
    Web3j web3j;// = Web3j.build(new HttpService(geth_url));
    Credentials credentials;//  = null;
    static ObjectMapper objectMapper = new ObjectMapper();

    public EthereumWapper(){
        geth_url = "http://10.154.24.12:8545";
        web3j = Web3j.build(new HttpService(geth_url));
        try {
            credentials = WalletUtils.loadJsonCredentials("", "{\"address\":\"da1ade291d7fe704398d13aeb60d2a291a355e89\",\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"84fe123345c08d5a04d0fc09e0fbb3fc30b2a42c5ee5c7d713c2d3c68e2931e1\",\"cipherparams\":{\"iv\":\"e81115433acd9e203064ebf00873b8c1\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":4096,\"p\":6,\"r\":8,\"salt\":\"e08aa20123995a48af91f248154ef1fb2ef84363bbdd0a8116890046f2ed60c7\"},\"mac\":\"ae42574572f0a4d1b30a31b8612bf8507b0d23a050f171998777e23c39fe4d0c\"},\"id\":\"0880c2c2-4fdd-4a3a-b2ee-5ad6e29b5cd1\",\"version\":3}");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChainWapper chainWapper = new EthereumWapper();

        System.out.println("BlockNumber = " + chainWapper.getBlockNumber()); // 获取块高
        System.out.println("----------------------------------");
        System.out.println("The 1st block is = " + chainWapper.getBlockByNumber(2205287)); // 通过块高获取块
        System.out.println("----------------------------------");
//        // 通过hash获取块
        System.out.println("The block with hash 0x9b90bb072bf7a06c1cda8f214b9a29eb3a4280b44a44f9a2aee1a4c5d6de695c is = " + chainWapper.getBlockByHash("0x9b90bb072bf7a06c1cda8f214b9a29eb3a4280b44a44f9a2aee1a4c5d6de695c"));
        System.out.println("----------------------------------");
//        // 获取transaction by hash
        System.out.println("Get Transaction by hash = " + chainWapper.getTransaction("0x9045253663cfd4ccaa4664e196837bd21223767e4b8e19e40dcabc158e750c45"));
        System.out.println("----------------------------------");

        //RawTransaction rawTransaction = new RawTransaction();
        //client.stop();

        System.exit(0);

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

        return null;
    }

    @Override
    public Block getBlockByHash(String blockHash) {
        try {
            EthBlock.Block ethBlock = web3j.ethGetBlockByHash(blockHash,true).send().getBlock();
            return covertToBlock(ethBlock);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Transaction getTransaction(String transactionHash) {
        try {
            org.web3j.protocol.core.methods.response.Transaction ethTransaction = web3j.ethGetTransactionByHash(transactionHash).send().getTransaction().get();
            return coverToTransaction(ethTransaction);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        block.setChainType(EnumType.ChainType.BCOS);
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
        transaction.setChainType(EnumType.ChainType.BCOS);
        return transaction;
    }
}
