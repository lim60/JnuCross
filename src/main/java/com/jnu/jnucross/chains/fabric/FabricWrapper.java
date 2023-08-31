package com.jnu.jnucross.chains.fabric;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.jnucross.chains.*;
import com.jnu.jnucross.chains.Transaction;
import com.jnu.jnucross.chains.ethereum.EthereumWrapper;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author SDKany
 * @ClassName FabricWapper
 * @Date 2023/8/18 22:46
 * @Version V1.0
 * @Description
 */
public class FabricWrapper extends ChainWrapper {
    // TODO: 修改
    String url; // = "http://10.154.24.12:8545";
    static ObjectMapper objectMapper = new ObjectMapper();

    public FabricWrapper(){
        url = "http://10.154.24.12:8545";
        
    }

    public static void main(String[] args) throws IOException {
        // Load an existing wallet holding identities used to access the network.
//        Path walletDirectory = Paths.get("wallet");
//        Wallet wallet = Wallets.newFileSystemWallet(walletDirectory);
//
//        // Path to a common connection profile describing the network.
//        Path networkConfigFile = Paths.get("connection.json");
//
//        // Configure the gateway connection used to access the network.
//        Gateway.Builder builder = Gateway.createBuilder()
//                .identity(wallet, "user1")
//                .networkConfig(networkConfigFile);
//
//        // Create a gateway connection
//        try (Gateway gateway = builder.connect()) {
//
//            // Obtain a smart contract deployed on the network.
//            Network network = gateway.getNetwork("mychannel");
//            Contract contract = network.getContract("fabcar");
//
//            // Submit transactions that store state to the ledger.
//            byte[] createCarResult = contract.createTransaction("createCar")
//                    .submit("CAR10", "VW", "Polo", "Grey", "Mary");
//            System.out.println(new String(createCarResult, StandardCharsets.UTF_8));
//
//            // Evaluate transactions that query state from the ledger.
//            byte[] queryAllCarsResult = contract.evaluateTransaction("queryAllCars");
//            System.out.println(new String(queryAllCarsResult, StandardCharsets.UTF_8));
//
//        } catch (ContractException | TimeoutException | InterruptedException e) {
//            e.printStackTrace();
//        }
    }

//    public static void main(String[] args) {
//        ChainWrapper chainWapper = new EthereumWrapper();
//
//        System.out.println("BlockNumber = " + chainWapper.getBlockNumber()); // 获取块高
//        System.out.println("----------------------------------");
//        System.out.println("The 1st block is = " + chainWapper.getBlockByNumber(2205287)); // 通过块高获取块
//        System.out.println("----------------------------------");
////        // 通过hash获取块
//        System.out.println("The block with hash 0x9b90bb072bf7a06c1cda8f214b9a29eb3a4280b44a44f9a2aee1a4c5d6de695c is = " + chainWapper.getBlockByHash("0x9b90bb072bf7a06c1cda8f214b9a29eb3a4280b44a44f9a2aee1a4c5d6de695c"));
//        System.out.println("----------------------------------");
////        // 获取transaction by hash
//        System.out.println("Get Transaction by hash = " + chainWapper.getTransaction("0x9045253663cfd4ccaa4664e196837bd21223767e4b8e19e40dcabc158e750c45"));
//        System.out.println("----------------------------------");
//
//        //RawTransaction rawTransaction = new RawTransaction();
//        //client.stop();
//
//        System.exit(0);
//
//    }

    @Override
    public BigInteger getBalance(){
        return BigInteger.ZERO;
    }

    @Override
    public void setChain(String url) {
        // todo
    }

    @Override
    public void setAccount(String hexPrivateKeyString) {
        // todo

    }

    @Override
    public void setAccount(BigInteger privateKey) {
        // todo

    }

    @Override
    public FunctionResult call(String abi, String contractName, String contractAddress, String method, List<String> args) throws Exception {
        return null;
    }

    @Override
    public FunctionResult send(String abi, String contractName, String contractAddress, String method, List<String> args, boolean payable, BigInteger amount, boolean wait) throws Exception {
        return null;
    }

    @Override
    public long getBlockNumber() {
//        try {
//            return web3j.ethBlockNumber().send().getBlockNumber().longValue();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return -1;
    }

    @Override
    public Block getBlockByNumber(long blockNumber) {
//        try {
//            EthBlock.Block ethBlock = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(blockNumber)), true).send().getBlock();
//            return covertToBlock(ethBlock);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return new Block();
    }

    @Override
    public Block getBlockByHash(String blockHash) {
//        try {
//            EthBlock.Block ethBlock = web3j.ethGetBlockByHash(blockHash,true).send().getBlock();
//            return covertToBlock(ethBlock);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return new Block();
    }


    @Override
    public Transaction getTransaction(String transactionHash) {
//        try {
//            org.web3j.protocol.core.methods.response.Transaction ethTransaction = web3j.ethGetTransactionByHash(transactionHash).send().getTransaction().get();
//            return coverToTransaction(ethTransaction);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return new Transaction();
    }

//    public static Block covertToBlock(EthBlock.Block ethBlock){
//        Block block = new Block();
//        BlockHeader blockHeader = new BlockHeader();
//        blockHeader.setNumber(ethBlock.getNumber().longValue());
//        blockHeader.setHash(ethBlock.getHash());
//        blockHeader.setPrevHash(ethBlock.getParentHash());
//        blockHeader.setReceiptRoot(ethBlock.getReceiptsRoot());
//        blockHeader.setTransactionRoot(ethBlock.getTransactionsRoot());
//        blockHeader.setStateRoot(ethBlock.getStateRoot());
//        block.setBlockHeader(blockHeader);
//        block.setChainType(EnumType.ChainType.Ethereum);
//        List<EthBlock.TransactionResult> transactionResults = ethBlock.getTransactions();
//        List<String> transactionsHashes = new ArrayList<>();
//        for (EthBlock.TransactionResult transaction : transactionResults) {
//            transactionsHashes.add(((EthBlock.TransactionObject)transaction.get()).getHash());
//        }
//        block.setTransactionsHashes(transactionsHashes);
//        try {
//            byte[] bytes = objectMapper.writeValueAsBytes(block);
//            block.setRawBytes(bytes);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            block.setRawBytes(new byte[0]);
//        }
//        return block;
//    }
//
//    public static Transaction coverToTransaction(org.web3j.protocol.core.methods.response.Transaction ethTransaction){
//        Transaction transaction = new Transaction();
//        transaction.setFrom(ethTransaction.getFrom());
//        transaction.setTo(ethTransaction.getTo());
//        transaction.setHash(ethTransaction.getHash());
//        transaction.setBlockNumber(Numeric.toBigInt(ethTransaction.getBlockNumberRaw()).longValue());
//        try {
//            transaction.setRawBytes(objectMapper.writeValueAsBytes(ethTransaction));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            transaction.setRawBytes(new byte[0]);
//        }
//        transaction.setChainType(EnumType.ChainType.Ethereum);
//        return transaction;
//    }

    public String getAddress(String hexPrivateKeyString){
        return "";
    }
}

