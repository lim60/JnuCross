package com.jnu.jnucross.chains.cita;

import com.citahub.cita.crypto.Credentials;
import com.citahub.cita.protocol.core.methods.response.AppBlock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.jnu.jnucross.chains.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SDKany
 * @ClassName CITAUtils
 * @Date 2023/8/24 14:59
 * @Version V1.0
 * @Description
 */
public class CITAUtils {
    static ObjectMapper objectMapper = new ObjectMapper();

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


    public static List<String> getAddressAndPublicKey(String hexPrivateKeyString){
        List<String> result = new ArrayList<String>();
        Credentials credentials = Credentials.create(hexPrivateKeyString);
        result.add(credentials.getAddress());
        result.add(Numeric.toHexStringWithPrefix(credentials.getEcKeyPair().getPublicKey()));
        return result;
    }

    public static String getAddress(String hexPrivateKeyString){
        Credentials credentials = Credentials.create(hexPrivateKeyString);
        return credentials.getAddress();
    }

    public static String getPublicKey(String hexPrivateKeyString){
        Credentials credentials = Credentials.create(hexPrivateKeyString);
        return Numeric.toHexStringWithPrefix(credentials.getEcKeyPair().getPublicKey());
    }
}
