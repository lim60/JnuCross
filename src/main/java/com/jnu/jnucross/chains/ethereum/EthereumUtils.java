package com.jnu.jnucross.chains.ethereum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.jnucross.chains.*;
import com.jnu.jnucross.chains.ethereum.generated.SimpleStorage;
import org.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.tx.Contract;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author SDKany
 * @ClassName EthereumUtils
 * @Date 2023/8/24 14:59
 * @Version V1.0
 * @Description
 */
public class EthereumUtils {
    static ObjectMapper objectMapper = new ObjectMapper();

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

    // 部署合约接口，需要先生成合约的java文件，然后私用合约Java类的deploy方法调用
    public static void deployContract(Contract contractClass){
        EthereumWrapper ethereumWrapper = EthereumWrapper.build();
        Contract contract = null;
        try {
            BigInteger gasPrice = ethereumWrapper.web3j.ethGasPrice().send().getGasPrice();
            System.out.println(gasPrice);
            ContractGasProvider gasProvider = new DefaultGasProvider();
            BigInteger chainID = ethereumWrapper.web3j.ethChainId().send().getChainId();
            TransactionManager transactionManager = new RawTransactionManager(ethereumWrapper.web3j, ethereumWrapper.credentials, chainID.longValue());
            contract = SimpleStorage.deploy(ethereumWrapper.web3j,transactionManager,gasProvider).send();
            System.out.println(contract.getContractAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    // 返回0x开头的公钥Hex String
    public static String getPublicKey(String hexPrivateKeyString){
        Credentials credentials = Credentials.create(hexPrivateKeyString);
        return Numeric.toHexStringWithPrefix(credentials.getEcKeyPair().getPublicKey());
    }
}
