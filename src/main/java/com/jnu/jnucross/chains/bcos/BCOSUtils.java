package com.jnu.jnucross.chains.bcos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.jnucross.chains.*;
import org.fisco.bcos.sdk.client.protocol.model.JsonTransactionResponse;
import org.fisco.bcos.sdk.client.protocol.response.BcosBlock;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.crypto.keypair.ECDSAKeyPair;
import org.fisco.bcos.sdk.crypto.keystore.KeyTool;
import org.fisco.bcos.sdk.model.TransactionReceipt;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SDKany
 * @ClassName BCOSUtils
 * @Date 2023/8/24 14:59
 * @Version V1.0
 * @Description
 */
public class BCOSUtils {

    static ObjectMapper objectMapper = new ObjectMapper();


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

    public static List<String> getAddressAndPublicKey(String hexPrivateKeyString){
        List<String> result = new ArrayList<String>();
        KeyPair keyPair = KeyTool.convertHexedStringToKeyPair(hexPrivateKeyString, CryptoKeyPair.ECDSA_CURVE_NAME);
        CryptoKeyPair cryptoKeyPair = new ECDSAKeyPair(keyPair);
        result.add(cryptoKeyPair.getAddress());
        result.add(cryptoKeyPair.getHexPublicKey());
        return result;
    }

    public static String getAddress(String hexPrivateKeyString){
        KeyPair keyPair = KeyTool.convertHexedStringToKeyPair(hexPrivateKeyString, CryptoKeyPair.ECDSA_CURVE_NAME);
        CryptoKeyPair cryptoKeyPair = new ECDSAKeyPair(keyPair);
        return cryptoKeyPair.getAddress();
    }

    public static String getPublicKey(String hexPrivateKeyString){
        KeyPair keyPair = KeyTool.convertHexedStringToKeyPair(hexPrivateKeyString, CryptoKeyPair.ECDSA_CURVE_NAME);
        CryptoKeyPair cryptoKeyPair = new ECDSAKeyPair(keyPair);
        return cryptoKeyPair.getHexPublicKey();
    }

    public static String creatPrivateKey(){
        CryptoKeyPair cryptoKeyPair = new ECDSAKeyPair();
        return cryptoKeyPair.getHexPrivateKey();
    }
}
