package com.jnu.jnucross.chains.xuperchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.jnucross.chains.*;
import com.jnu.jnucross.chains.xuperchain.xuper.api.Account;
import com.jnu.jnucross.chains.xuperchain.xuper.api.XuperClient;
import com.jnu.jnucross.chains.xuperchain.xuper.crypto.xchain.sign.ECKeyPair;
import com.jnu.jnucross.chains.xuperchain.xuper.pb.XchainOuterClass;


import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SDKany
 * @ClassName XuperChainWrapper
 * @Date 2023/8/18 22:47
 * @Version V1.0
 * @Description
 */
public class XuperChainWrapper extends ChainWrapper {
    String xuperChain_url; // = "http://10.154.24.12:8545";
    XuperClient client;// = Web3j.build(new HttpService(geth_url));
    Account account;//  = null;
    static ObjectMapper objectMapper = new ObjectMapper();

    public XuperChainWrapper(){
        xuperChain_url = "10.154.24.12:37101";
        client = new XuperClient(xuperChain_url);
        ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger("111497060296999106528800133634901141644446751975433315540300236500052690483486"));
        account = Account.create(ecKeyPair);
    }

    public static void main(String[] args) {
        ChainWrapper chainWapper = new XuperChainWrapper();

        System.out.println("BlockNumber = " + chainWapper.getBlockNumber()); // 获取块高
        System.out.println("----------------------------------");
        System.out.println("The 1st block is = " + chainWapper.getBlockByNumber(2)); // 通过块高获取块
        System.out.println("----------------------------------");
        // 通过hash获取块
        System.out.println("The block with hash 2a11f1aebb9c3ff173bbe8e1cdbf679ce72b5ca35aed50f99d3a4f6b90670d61 is = " + chainWapper.getBlockByHash("2a11f1aebb9c3ff173bbe8e1cdbf679ce72b5ca35aed50f99d3a4f6b90670d61"));
        System.out.println("----------------------------------");
//        // 获取transaction by hash
        System.out.println("Get Transaction by hash = " + chainWapper.getTransaction("5939f2423d45b7512d9af6ac9f56553b21d5d03f0057da407f1133dbdc4a8c86"));
        System.out.println("----------------------------------");

        //RawTransaction rawTransaction = new RawTransaction();
        //client.stop();

        System.exit(0);

    }

    @Override
    public long getBlockNumber() {
        return client.getHeight();
    }

    @Override
    public Block getBlockByNumber(long blockNumber) {
        XchainOuterClass.InternalBlock xuperBlock = client.queryBlockByHeight(blockNumber);
        if (xuperBlock == null)
            return new Block();
        return covertToBlock(xuperBlock);
    }

    @Override
    public Block getBlockByHash(String blockHash) {
        XchainOuterClass.InternalBlock xuperBlock = client.queryBlock(blockHash);
        if (xuperBlock == null)
            return new Block();
        return covertToBlock(xuperBlock);
    }


    @Override
    public Transaction getTransaction(String transactionHash) {
        XchainOuterClass.Transaction xuperChainTransaction = client.queryTx(transactionHash);
        if(xuperChainTransaction == null)
            return new Transaction();
        return coverToTransaction(xuperChainTransaction);
    }

    public static Block covertToBlock(XchainOuterClass.InternalBlock xuperBlock){
        Block block = new Block();
        BlockHeader blockHeader = new BlockHeader();
        blockHeader.setNumber(xuperBlock.getHeight());
        blockHeader.setHash(Numeric.toHexStringNoPrefix(xuperBlock.getBlockid().toByteArray()));
        blockHeader.setPrevHash(Numeric.toHexStringNoPrefix(xuperBlock.getPreHash().toByteArray()));
        blockHeader.setReceiptRoot(Numeric.toHexStringNoPrefix(xuperBlock.getMerkleRoot().toByteArray()));
        blockHeader.setTransactionRoot(""); // xuper chain block似乎不存在这样的数据
        blockHeader.setStateRoot(""); // xuper chain block似乎不存在这样的数据
        block.setBlockHeader(blockHeader);
        block.setChainType(EnumType.ChainType.XuperChain);
        List<XchainOuterClass.Transaction> transactionResults = xuperBlock.getTransactionsList();
        List<String> transactionsHashes = new ArrayList<>();
        for (XchainOuterClass.Transaction transaction : transactionResults) {
            transactionsHashes.add(Numeric.toHexStringNoPrefix(transaction.getTxid().toByteArray()));
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

    public static Transaction coverToTransaction(XchainOuterClass.Transaction xuperChainTransaction){
        Transaction transaction = new Transaction();
        transaction.setFrom(xuperChainTransaction.getInitiator());
        try {
            transaction.setTo(Numeric.toHexStringNoPrefix(xuperChainTransaction.getTxOutputs(0).getToAddr().toByteArray()));
        }catch (Exception e){
            transaction.setTo("");
            e.printStackTrace();
        }
        transaction.setHash(Numeric.toHexStringNoPrefix(xuperChainTransaction.getTxid().toByteArray()));
        transaction.setBlockNumber(Numeric.toBigInt(xuperChainTransaction.getBlockid().toByteArray()).longValue());
        try {
            transaction.setRawBytes(xuperChainTransaction.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            transaction.setRawBytes(new byte[0]);
        }
        transaction.setChainType(EnumType.ChainType.XuperChain);
        return transaction;
    }
}
