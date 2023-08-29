package com.jnu.jnucross.chains.cita;

import com.citahub.cita.abi.datatypes.Function;
import com.citahub.cita.crypto.Credentials;
import com.citahub.cita.crypto.Keys;
import com.citahub.cita.protocol.core.methods.response.AppBlock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.protobuf.InvalidProtocolBufferException;
import com.jnu.jnucross.chains.*;
import com.jnu.jnucross.chains.cita.contract.Abi;
import com.jnu.jnucross.chains.cita.contract.AbiFunctionType;
import com.jnu.jnucross.chains.cita.contract.ContractParam;
import com.jnu.jnucross.chains.cita.util.ContractUtil;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author SDKany
 * @ClassName CITAUtils
 * @Date 2023/8/24 14:59
 * @Version V1.0
 * @Description
 */
public class CITAUtils {
    static ObjectMapper objectMapper = new ObjectMapper();
    static final Random RANDOM = new Random();

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

    public static String getNonce() {
        return System.nanoTime() + String.valueOf(RANDOM.nextInt(100000) + 900000);
    }

    public static int getVersion() {
        return 2;
    }
    public static BigInteger getChainId() {
        return BigInteger.valueOf(1L);
    }
    public static String creatPrivateKey(){
        try {
            return Numeric.toHexStringWithPrefix(Keys.createEcKeyPair().getPrivateKey());
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Function convertFunction(String abiString, String name, String[] args) {
        List<Abi> abis = parseAbi(abiString);
        for (Abi abi : abis) {
            if (!abi.getName().equalsIgnoreCase(name)) {
                continue;
            }
            List<String> outs = Lists.newArrayListWithCapacity(abi.getOutputTypes().size());
            abi.getOutputTypes().forEach(type -> outs.add(type.getType()));
            List<AbiFunctionType> inputs = abi.getInputTypes();
            if (null == args || args.length == 0) {
                return ContractUtil.convertFunction(name, null, outs);
            }
            if (args.length != inputs.size()) {
                throw new RuntimeException("input args number not equals to abi");
            }
            List<ContractParam> params = Lists.newArrayListWithCapacity(inputs.size());
            for (int i = 0; i < inputs.size(); i++) {
                ContractParam param = new ContractParam();
                param.setType(inputs.get(i).getType());
                param.setValue(args[i]);
                params.add(param);
            }
            return ContractUtil.convertFunction(name, params, outs);
        }
        throw new RuntimeException("method name can't be found in abi");
    }

    public static List<Abi> parseAbi(String abi) {
        List<Abi> abis = Lists.newArrayList();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode trees = objectMapper.readTree(abi);
            for (JsonNode tree : trees) {
                String type = tree.get("type").asText();
                if (!"function".equalsIgnoreCase(type)) {
                    continue;
                }
                Abi inner = new Abi();
                inner.setName(tree.get("name").asText());
                inner.setInputTypes(makeType(tree.get("inputs")));
                inner.setOutputTypes(makeType(tree.get("outputs")));
                abis.add(inner);
            }
            return abis;
        } catch (IOException e) {
            throw new RuntimeException("parse abi failed");
        }
    }

    public static List<AbiFunctionType> makeType(JsonNode node) {
        final List<AbiFunctionType> result = Lists.newArrayListWithCapacity(node.size());
        node.forEach(
                input -> {
                    AbiFunctionType type = new AbiFunctionType();
                    type.setType(input.get("type").asText());
                    type.setName(input.get("name").asText());
                    result.add(type);
                });
        return result;
    }

    public static String hexRemove0x(String hex) {
        if (hex.contains("0x")) {
            return hex.substring(2);
        }
        return hex;
    }

    public static String hexStr2Str(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, StandardCharsets.UTF_8); // UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();
        return buffer.getLong();
    }

    public static byte[] toByteArray (Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    public static Object toObject (byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }
}
