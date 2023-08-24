package com.jnu.jnucross.chains.xuperchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.jnucross.chains.*;
import com.jnu.jnucross.chains.xuperchain.xuper.api.Account;
import com.jnu.jnucross.chains.xuperchain.xuper.crypto.xchain.sign.ECKeyPair;
import com.jnu.jnucross.chains.xuperchain.xuper.pb.XchainOuterClass;
import org.bouncycastle.math.ec.ECPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SDKany
 * @ClassName XuperChainUtils
 * @Date 2023/8/24 15:00
 * @Version V1.0
 * @Description
 */
public class XuperChainUtils {
    static ObjectMapper objectMapper = new ObjectMapper();

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


    public static List<String> getAddressAndPublicKey(String hexPrivateKeyString){
        List<String> result = new ArrayList<String>();
        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(hexPrivateKeyString));
        Account account = Account.create(ecKeyPair);

        result.add(account.getAddress());
        result.add(account.getKeyPair().getJSONPublicKey());
        return result;
    }

    public static String getAddress(String hexPrivateKeyString){
        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(hexPrivateKeyString));
        Account account = Account.create(ecKeyPair);
        String address = account.getAddress();
        return address;
    }

    public static String getPublicKey(String hexPrivateKeyString){
        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(hexPrivateKeyString));
        // 暂时先返回JSON格式的public key，因为单独的public key 没法直接使用，只能用来验证签名之类的
        String publicKeyJSON = ecKeyPair.getJSONPublicKey();
        ECPoint p = ecKeyPair.getPublicKey();
        return publicKeyJSON;
    }

    public static String creatPrivateKey(){
        ECKeyPair ecKeyPair = ECKeyPair.create();
        return Numeric.toHexStringWithPrefix(ecKeyPair.privateKey);
    }
}
