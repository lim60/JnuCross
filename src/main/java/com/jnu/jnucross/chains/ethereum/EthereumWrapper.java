package com.jnu.jnucross.chains.ethereum;

import com.jnu.jnucross.chains.*;
import com.jnu.jnucross.chains.Transaction;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.AbiTypes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.jnu.jnucross.chains.ethereum.EthereumUtils.*;

/**
 * @author SDKany
 * @ClassName EthereumWrapper
 * @Date 2023/8/18 22:46
 * @Version V1.0
 * @Description
 */
public class EthereumWrapper extends ChainWrapper {
    public String geth_url; // = "http://10.154.24.12:8545";
    public Web3j web3j;// = Web3j.build(new HttpService(geth_url));
    public Credentials credentials;//  = null;

    protected EthereumWrapper(){
        super();
    }

    public EthereumWrapper(String url, BigInteger privateKey){
        super();
        geth_url = url;
        web3j = Web3j.build(new HttpService(geth_url));
        try {
            credentials = Credentials.create(ECKeyPair.create(privateKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EthereumWrapper(String url, String hexPrivateKeyString){
        super();
        geth_url = url;
        web3j = Web3j.build(new HttpService(geth_url));
        try {
            credentials = Credentials.create(hexPrivateKeyString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EthereumWrapper build(){
        EthereumWrapper ethereumWrapper = new EthereumWrapper();
        ethereumWrapper.geth_url = "http://10.154.24.12:8545";
        ethereumWrapper.web3j = Web3j.build(new HttpService(ethereumWrapper.geth_url));
        try {
            ethereumWrapper.credentials = WalletUtils.loadJsonCredentials("", "{\"address\":\"c2582ae1deec5af85028e0a089ec87286c4f8d6e\",\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"7d2b1b3fb0d1e1758b90a1419bdb52d4b3a421c68d8b57dcee8544f51b7c9e5e\",\"cipherparams\":{\"iv\":\"853a3bc72ca80013dd4577f58aca4ea5\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":4096,\"p\":6,\"r\":8,\"salt\":\"0a63e01bc2df1d8f15357faedd6a46e8e184789e90c251e65d289ce130cc04ae\"},\"mac\":\"d15a6a86df4aea6dc0e730d5ed1ae473e5549531e9eb0aa547c8f75d5a9a9f5c\"},\"id\":\"f1d8f10c-e153-4274-8697-d80ccc4c4069\",\"version\":3}");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        return ethereumWrapper;
    }

    @Override
    public void setChain(String url) {
        geth_url = url;
        web3j = Web3j.build(new HttpService(geth_url));
    }

    @Override
    public void setAccount(String hexPrivateKeyString) {
        try {
            credentials = Credentials.create(hexPrivateKeyString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAccount(BigInteger privateKey) {
        try {
            credentials = Credentials.create(ECKeyPair.create(privateKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EthereumWrapper chainWrapper = EthereumWrapper.build();

        System.out.println(Numeric.toHexStringWithPrefix(chainWrapper.credentials.getEcKeyPair().getPrivateKey()));

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

    public BigInteger getBalance(String address){
        try {
            return web3j.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send().getBalance();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Transaction();
    }

    // amount为本区块链中的最小单位WEI，1 Ether=10^18 Wei
    public String transferTo(String toAddress, BigInteger amount, boolean wait){
        TransactionManager transactionManager = new RawTransactionManager(this.web3j, this.credentials, 111);
        try {
            BigInteger gasPrice = this.web3j.ethGasPrice().send().getGasPrice();
            System.out.println("*** gasPrice = " + gasPrice);
            EthSendTransaction ethSendTransaction = transactionManager.sendTransaction(gasPrice.multiply(BigInteger.TEN), BigInteger.valueOf(8_000_000L), toAddress, "", amount);
            if (wait){
                TransactionReceipt transactionReceipt = this.waitForPolling(ethSendTransaction.getTransactionHash());
                return transactionReceipt.getTransactionHash();
            }
            return ethSendTransaction.getTransactionHash();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public TransactionReceipt waitForPolling(String txHash){
        TransactionReceiptProcessor transactionReceiptProcessor = new PollingTransactionReceiptProcessor(web3j, TransactionManager.DEFAULT_POLLING_FREQUENCY, TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);
        TransactionReceipt transactionReceipt = null;
        try {
            transactionReceipt = transactionReceiptProcessor.waitForTransactionReceipt(txHash);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
        System.out.println(transactionReceipt);
        return transactionReceipt;
    }

    @Override
    public FunctionResult send(String abi, String contractName, String contractAddress, String method, List<String> args, boolean payable, BigInteger amount, boolean wait) throws Exception {
        AbiDefinition[] abiDefinitions = objectMapper.readValue(abi, AbiDefinition[].class);
        AbiDefinition abiDefinition = null;
        for (int i = 0; i < abiDefinitions.length; i++){
            abiDefinition = abiDefinitions[i];
            if(abiDefinition.getName() == null){
                continue;
            }
            if(abiDefinition.getName().equals(method)){
                break;
            }
        }
        if (abiDefinition == null){
            throw new Exception("no such method in this contract! method = " + method);
        }

        List<AbiDefinition.NamedType> inputs = abiDefinition.getInputs();
        List<AbiDefinition.NamedType> outputs = abiDefinition.getOutputs();

        System.out.println("abiDefinition = " + abiDefinition.getOutputs());
        System.out.println("abiDefinition = " + abiDefinition.getName());
        System.out.println("abiDefinition = " + abiDefinition.getInputs());
        System.out.println("abiDefinition = " + abiDefinition.getStateMutability());

        List<Type> inputParas = new ArrayList<>();
        List<TypeReference<?>> outputParas = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            System.out.println("---- inputs i = " + i);
            AbiDefinition.NamedType namedType = inputs.get(i);
            System.out.println("    namedType.getName() = " + namedType.getName());
            System.out.println("    namedType.getType() = " + namedType.getType());
            String arg = args.get(i);
            System.out.println("    args_i = " + arg);
            inputParas.add(TypeDecoder.decode(arg, AbiTypes.getType(namedType.getType())));
        }
        for (int i = 0; i < outputs.size(); i++) {
            AbiDefinition.NamedType namedType = outputs.get(i);
            outputParas.add(TypeReference.makeTypeReference(namedType.getType()));
        }

        Function function = new Function(
                method,
                inputParas,
                outputParas);
        String encodeFunction = FunctionEncoder.encode(function);
        BigInteger gasPrice = this.web3j.ethGasPrice().send().getGasPrice();
//        org.web3j.protocol.core.methods.request.Transaction transaction = org.web3j.protocol.core.methods.request.Transaction.createFunctionCallTransaction(
//                this.credentials.getAddress(),
//                null,
//                gasPrice.multiply(BigInteger.TEN),
//                BigInteger.valueOf(8_000_000L),
//                contractAddress,
//                amount,
//                encodeFunction);
        //EthSendTransaction ethCall;
        try {
//            ethCall = web3j.ethSendTransaction(transaction).sendAsync().get();
//            System.out.println("ethCall = " + ethCall);
//            String txHash = ethCall.getTransactionHash();
//            System.out.println("hash = " + txHash);

            BigInteger nonce = web3j.ethGetTransactionCount(
                    credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getTransactionCount();
            RawTransaction rawTransaction = null;
            if(payable){
                rawTransaction = RawTransaction.createTransaction(nonce, gasPrice.multiply(BigInteger.TEN), BigInteger.valueOf(8_000_000L), contractAddress, amount, encodeFunction);
            }else{
                rawTransaction = RawTransaction.createTransaction(nonce, gasPrice.multiply(BigInteger.TEN), BigInteger.valueOf(8_000_000L), contractAddress, encodeFunction);
            }
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, 111, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction response = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
            List<Type> result = null;
            if (wait){
                result = new ArrayList<>();
                TransactionReceipt transactionReceipt = waitForPolling(response.getTransactionHash());
                List<Log> logs = transactionReceipt.getLogs();
                if ((logs != null) && (!logs.isEmpty())){
                    result = FunctionReturnDecoder.decode(logs.get(0).getData(), function.getOutputParameters());
                }
            }
            FunctionResult functionResult = new FunctionResult();
            functionResult.transactionHash = response.getTransactionHash();
            functionResult.result = result;
            System.out.println(functionResult.result);
            System.out.println(result);
            return functionResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public FunctionResult call(String abi, String contractName, String contractAddress, String method, List<String> args) throws Exception {
        AbiDefinition[] abiDefinitions = objectMapper.readValue(abi, AbiDefinition[].class);
        AbiDefinition abiDefinition = null;
        for (int i = 0; i < abiDefinitions.length; i++){
            abiDefinition = abiDefinitions[i];
            if(abiDefinition.getName().equals(method)){
                break;
            }
        }
        if (abiDefinition == null){
            throw new Exception("no such method in this contract! method = " + method);
        }

        List<AbiDefinition.NamedType> inputs = abiDefinition.getInputs();
        List<AbiDefinition.NamedType> outputs = abiDefinition.getOutputs();

        List<Type> inputParas = new ArrayList<>();
        List<TypeReference<?>> outputParas = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            AbiDefinition.NamedType namedType = inputs.get(i);
            inputParas.add(TypeDecoder.decode(args.get(i), AbiTypes.getType(namedType.getType())));
        }
        for (int i = 0; i < outputs.size(); i++) {
            AbiDefinition.NamedType namedType = outputs.get(i);
            outputParas.add(TypeReference.makeTypeReference(namedType.getType()));
        }

//        System.out.println("******");
//        for (int i = 0; i < outputParas.size(); i++){
//            System.out.println("i: " + outputParas.get(i) + " - " + outputs.get(i).getType());
//        }

        Function function = new Function(
                method,
                inputParas,
                outputParas);
        String encodeFunction = FunctionEncoder.encode(function);
        //
        org.web3j.protocol.core.methods.request.Transaction transaction = org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(this.credentials.getAddress(), contractAddress, encodeFunction);
        EthCall ethCall;
        try {
            ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            System.out.println("ethCall.getValue() = " + ethCall.getValue());
            List<Type> result = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());

            FunctionResult functionResult = new FunctionResult();

            functionResult.result = result;

            return functionResult;
        } catch (Exception e) {
            e.printStackTrace();
        }

//        TransactionManager transactionManager = new RawTransactionManager(this.web3j, this.credentials, 111);
//        String result = transactionManager.sendCall(contractAddress, encodeFunction, DefaultBlockParameter.valueOf("latest"));
//        System.out.println("result = " + result);
//        System.out.println("List = " + FunctionReturnDecoder.decode(result, function.getOutputParameters()));
        return null;
    }
}
