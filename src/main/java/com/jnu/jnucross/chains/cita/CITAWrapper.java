package com.jnu.jnucross.chains.cita;

import com.citahub.cita.protocol.core.DefaultBlockParameter;
import com.citahub.cita.protocol.core.methods.response.AppBlock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.jnu.jnucross.chains.*;


import com.citahub.cita.crypto.Credentials;
import com.citahub.cita.protocol.CITAj;
import com.citahub.cita.protocol.http.HttpService;
import com.jnu.jnucross.chains.xuperchain.xuper.api.Account;
import com.jnu.jnucross.chains.xuperchain.xuper.api.XuperClient;
import com.jnu.jnucross.chains.xuperchain.xuper.crypto.xchain.sign.ECKeyPair;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SDKany
 * @ClassName CITAWrapper
 * @Date 2023/8/18 22:45
 * @Version V1.0
 * @Description
 */
public class CITAWrapper extends ChainWrapper {
    String cita_url; // = "http://10.154.24.12:8545";
    CITAj client;// = Web3j.build(new HttpService(geth_url));
    Credentials credentials;//  = null;
    static ObjectMapper objectMapper = new ObjectMapper();

    public CITAWrapper(){
        super();
    }

    public static CITAWrapper build() {
        CITAWrapper citaWrapper = new CITAWrapper();
        citaWrapper.cita_url = "http://10.154.24.5:1337";
        citaWrapper.client = CITAj.build(new HttpService(citaWrapper.cita_url));
        try {
            citaWrapper.credentials = Credentials.create("0x2e40857e98f1da9300b4991eca62231ebb7e0f4a13fabbd2fc9a1f19bff53825");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return citaWrapper;
    }

    public CITAWrapper(String url, BigInteger privateKey){
        cita_url = url;
        client = CITAj.build(new HttpService(cita_url));
        credentials = Credentials.create(com.citahub.cita.crypto.ECKeyPair.create(privateKey));
    }

    public CITAWrapper(String url, String hexPrivateKeyString){
        cita_url = url;
        client = CITAj.build(new HttpService(cita_url));
        credentials = Credentials.create(hexPrivateKeyString);
    }

    @Override
    public void setChain(String url){
        client = CITAj.build(new HttpService(url));
    }

    @Override
    public void setAccount(String hexPrivateKeyString){
        credentials = Credentials.create(hexPrivateKeyString);
    }

    @Override
    public void setAccount(BigInteger privateKey){
        credentials = Credentials.create(com.citahub.cita.crypto.ECKeyPair.create(privateKey));
    }

    public static void main(String[] args) {
        ChainWrapper chainWrapper = CITAWrapper.build();

        System.out.println("Balance = " + chainWrapper.getBalance());

        System.out.println("BlockNumber = " + chainWrapper.getBlockNumber()); // 获取块高
        System.out.println("----------------------------------");
        System.out.println("The 105509 block is = " + chainWrapper.getBlockByNumber(105509)); // 通过块高获取块
        System.out.println("----------------------------------");
//        // 通过hash获取块
        System.out.println("The block with hash 0x1d927c4fe970cc51468f95e52a7690d8d251fbebf5a59df529ee264ea876bed3 is = " + chainWrapper.getBlockByHash("0x1d927c4fe970cc51468f95e52a7690d8d251fbebf5a59df529ee264ea876bed3"));
        System.out.println("----------------------------------");
////        // 获取transaction by hash
        System.out.println("Get Transaction by hash = " + chainWrapper.getTransaction("0xbf6b70703c95892bf42d3b1ef8cdc7538089d68eb54defb74fc08ccc8df8820f"));
        System.out.println("----------------------------------");

        //RawTransaction rawTransaction = new RawTransaction();
        //client.stop();

        System.exit(0);

    }



    @Override
    public BigInteger getBalance(){
        try {
            return client.appGetBalance(credentials.getAddress(), DefaultBlockParameter.valueOf("lasest")).send().getBalance();
        } catch (Exception e) {
            e.printStackTrace();
            return BigInteger.valueOf(0);
        }
    }

    @Override
    public long getBlockNumber() {
        try {
            return client.appBlockNumber().send().getBlockNumber().longValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Block getBlockByNumber(long blockNumber) {
        AppBlock.Block citaBlock;
        try {
            citaBlock = client.appGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(blockNumber)), true).send().getResult();
        } catch (IOException e) {
            e.printStackTrace();
            return new Block();
        }
        return covertToBlock(citaBlock);
    }

    @Override
    public Block getBlockByHash(String blockHash) {
        AppBlock.Block citaBlock = null;
        try {
            citaBlock = client.appGetBlockByHash(blockHash, true).send().getBlock();
        } catch (IOException e) {
            e.printStackTrace();
            return new Block();
        }
        return covertToBlock(citaBlock);
    }

    @Override
    public Transaction getTransaction(String transactionHash) {
        try {
            com.citahub.cita.protocol.core.methods.response.Transaction citaTransaction = client.appGetTransactionByHash(transactionHash).send().getTransaction();
            return coverToTransaction(citaTransaction);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Transaction();
    }

    public static Block covertToBlock(AppBlock.Block citaBlock) {
        Block block = new Block();
        BlockHeader blockHeader = new BlockHeader();
        blockHeader.setNumber(Numeric.toBigInt(citaBlock.getHeader().getNumber()).longValue());
        blockHeader.setHash(citaBlock.getHash());
        blockHeader.setPrevHash(citaBlock.getHeader().getPrevHash());
        blockHeader.setReceiptRoot(citaBlock.getHeader().getReceiptsRoot());
        blockHeader.setTransactionRoot(citaBlock.getHeader().getTransactionsRoot());
        blockHeader.setStateRoot(citaBlock.getHeader().getStateRoot());
        block.setBlockHeader(blockHeader);
        block.setChainType(EnumType.ChainType.CITA);
        List<AppBlock.TransactionObject> transactionResults = citaBlock.getBody().getTransactions();
        List<String> transactionsHashes = new ArrayList<>();
        for (AppBlock.TransactionObject transaction : transactionResults) {
            transactionsHashes.add((transaction.get()).getHash());
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

    public static Transaction coverToTransaction(com.citahub.cita.protocol.core.methods.response.Transaction citaTransaction) throws InvalidProtocolBufferException {
        Transaction transaction = new Transaction();
        transaction.setFrom(citaTransaction.getFrom());
        transaction.setTo(citaTransaction.decodeContent().getTo());
        transaction.setHash(citaTransaction.getHash());
        transaction.setBlockNumber(citaTransaction.getBlockNumber().longValue());
        try {
            transaction.setRawBytes(objectMapper.writeValueAsBytes(citaTransaction));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            transaction.setRawBytes(new byte[0]);
        }
        transaction.setChainType(EnumType.ChainType.CITA);
        return transaction;
    }
}