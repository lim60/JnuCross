package com.jnu.jnucross.chains.cita;

import com.citahub.cita.abi.FunctionEncoder;
import com.citahub.cita.abi.FunctionReturnDecoder;
import com.citahub.cita.protocol.account.Account;
import com.citahub.cita.protocol.core.DefaultBlockParameterName;
import com.citahub.cita.protocol.core.methods.request.Call;
import com.citahub.cita.abi.datatypes.Function;
import com.citahub.cita.abi.datatypes.Type;
import com.citahub.cita.abi.datatypes.generated.AbiTypes;
import com.citahub.cita.crypto.RawTransaction;
import com.citahub.cita.crypto.Sign;
import com.citahub.cita.crypto.TransactionEncoder;
import com.citahub.cita.protocol.core.DefaultBlockParameter;
import com.citahub.cita.protocol.core.methods.response.*;
import com.citahub.cita.protocol.exceptions.TransactionException;
import com.citahub.cita.tx.RawTransactionManager;
import com.citahub.cita.tx.TransactionManager;
import com.citahub.cita.tx.response.PollingTransactionReceiptProcessor;
import com.citahub.cita.tx.response.TransactionReceiptProcessor;
import com.jnu.jnucross.chains.*;


import com.citahub.cita.crypto.Credentials;
import com.citahub.cita.protocol.CITAj;
import com.citahub.cita.protocol.http.HttpService;
import com.jnu.jnucross.chains.Transaction;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.jnu.jnucross.chains.cita.CITAUtils.*;

/**
 * @author SDKany
 * @ClassName CITAWrapper
 * @Date 2023/8/18 22:45
 * @Version V1.0
 * @Description
 */
public class CITAWrapper extends ChainWrapper {
    public String cita_url; // = "http://10.154.24.12:8545";
    public CITAj client;// = Web3j.build(new HttpService(geth_url));
    public Credentials credentials;//  = null;

    public CITAWrapper(){
        super();
    }

    // "address": "0x288e32f040e37c964d3bf7b417106e4269c3e27f",
    //  "private": "0xce02da6f0f2135ad9c0f07e270273c93999c7e246bfd0297938727ee09d2604f",
    //  "public": "0x167249595463631ec0a0d678bcbafb442519070438eb252baef265d590df6f88d9e8fad26fb05bf92e64944b1f1be8d3099de21582abdcdd24036f251bb413a7"

    public static CITAWrapper build() {
        CITAWrapper citaWrapper = new CITAWrapper();
        citaWrapper.cita_url = "http://10.154.24.5:1337"; //"http://81.71.46.41:1337";//
        citaWrapper.client = CITAj.build(new HttpService(citaWrapper.cita_url));
        try {
            citaWrapper.credentials = Credentials.create("0xd22bc2c10f45a48a80d2f7b7b8312d05be8866d6ba3880518316af186024b744"); // 0x2e40857e98f1da9300b4991eca62231ebb7e0f4a13fabbd2fc9a1f19bff53825
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

    public static void main(String[] args) throws IOException {
        CITAWrapper chainWrapper = CITAWrapper.build();

        System.out.println("Balance = " + chainWrapper.getBalance());

        System.out.println("Address = " + chainWrapper.credentials.getAddress());

        System.out.println("BlockNumber = " + chainWrapper.getBlockNumber()); // 获取块高
        System.out.println("----------------------------------");
        System.out.println("The 10 block is = " + chainWrapper.getBlockByNumber(10)); // 通过块高获取块
        System.out.println("----------------------------------");
//        // 通过hash获取块
//        System.out.println("The block with hash 0x535b6629769783578c49c118d474710b392d997db767311792c4b8f93d7a53cb is = " + chainWrapper.getBlockByHash("0x535b6629769783578c49c118d474710b392d997db767311792c4b8f93d7a53cb"));
        System.out.println("----------------------------------");
////        // 获取transaction by hash
//        System.out.println("Get Transaction by hash = " + chainWrapper.getTransaction("0xbf6b70703c95892bf42d3b1ef8cdc7538089d68eb54defb74fc08ccc8df8820f"));
//        System.out.println("----------------------------------");

        //RawTransaction rawTransaction = new RawTransaction();
        //client.stop();

        String toAddress = "0xc7a05c3a60a0548c8a0733e641f551c85d60b511";
        System.out.println("Balance = " + chainWrapper.getBalance());
        System.out.println("Balance1 = " + chainWrapper.getBalance(toAddress));
        chainWrapper.transferTo(toAddress, BigInteger.valueOf(10002L), true);
        System.out.println("Balance = " + chainWrapper.getBalance());
        System.out.println("Balance1 = " + chainWrapper.getBalance(toAddress));

        System.exit(0);

    }

    @Override
    public BigInteger getBalance(){
        try {
            return client.appGetBalance(credentials.getAddress(), DefaultBlockParameter.valueOf("latest")).send().getBalance();
        } catch (Exception e) {
            e.printStackTrace();
            return BigInteger.valueOf(0);
        }
    }

    public BigInteger getBalance(String address){
        try {
            return client.appGetBalance(address, DefaultBlockParameter.valueOf("latest")).send().getBalance();
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

    // amount为本区块链中的最小单位WEI，1 Ether=10^18 Wei
    public String transferTo(String toAddress, BigInteger amount, boolean wait) throws IOException {
        long blockNumber = getBlockNumber();
        com.citahub.cita.protocol.core.methods.request.Transaction tx =
                new com.citahub.cita.protocol.core.methods.request.Transaction(
                        toAddress,
                        CITAUtils.getNonce(),
                        1000000L,
                        blockNumber + 88,
                        CITAUtils.getVersion(),
                        CITAUtils.getChainId(),
                        amount.toString(), "");
        byte[] bsTx = tx.serializeRawTransaction(false);
        Sign.SignatureData signatureData = Sign.signMessage(bsTx, credentials.getEcKeyPair());
        String raw_tx = tx.serializeUnverifiedTransaction(signatureData.get_signature(), bsTx);

        AppSendTransaction appSendTransaction =
                client.appSendRawTransaction(raw_tx).send();

        if (wait){
            TransactionReceipt receipt = waitForPolling(appSendTransaction.getSendTransactionResult().getHash());
            System.out.println("receipt.getErrorMessage() = " + receipt.getErrorMessage());
        }
        return appSendTransaction.getSendTransactionResult().getHash();
    }

    public TransactionReceipt waitForPolling(String txHash){
        TransactionReceiptProcessor transactionReceiptProcessor = new PollingTransactionReceiptProcessor(client, TransactionManager.DEFAULT_POLLING_FREQUENCY, TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);
        TransactionReceipt transactionReceipt = null;
        try {
            transactionReceipt = transactionReceiptProcessor.waitForTransactionReceipt(txHash);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (TransactionException e) {
            e.printStackTrace();
        }
        System.out.println(transactionReceipt);
        return transactionReceipt;
    }

    // 如果wait的话，返回合约的地址，否则返回transaction的hash
    public String deploy(String bin, String abi, boolean wait) throws IOException {
        int version = getVersion();
        BigInteger chainId = getChainId();
        long currentHeight = getBlockNumber();
        long validUntilBlock = currentHeight + 80;
        String nonce = getNonce();
        long quota = 1000000;
        com.citahub.cita.protocol.core.methods.request.Transaction tx = com.citahub.cita.protocol.core.methods.request.Transaction.createContractTransaction(nonce, quota, validUntilBlock, version, chainId, "0", bin);

        String rawTx = tx.sign(credentials);
        AppSendTransaction result = client.appSendRawTransaction(rawTx).send();

        if (wait){
            TransactionReceipt a = waitForPolling(result.getSendTransactionResult().getHash());
            System.out.println("ContractAddress = " + a.getContractAddress());
            System.out.println("txHash = " + a.getTransactionHash());
            return a.getContractAddress();
        }
        return result.getSendTransactionResult().getHash();
    }

    public FunctionResult send(String abi, String contractName, String contractAddress, String method, List<String> args, boolean payable, BigInteger amount, boolean wait) throws Exception {
        Function function = convertFunction(abi, method, args.toArray(new String[0]));
        String encodeFunction = FunctionEncoder.encode(function);
        try {
            int version = getVersion();
            BigInteger chainId = getChainId();
            long currentHeight = getBlockNumber();
            long validUntilBlock = currentHeight + 80;
            String nonce = getNonce();
            long quota = 1000000;

            com.citahub.cita.protocol.core.methods.request.Transaction tx = com.citahub.cita.protocol.core.methods.request.Transaction.createFunctionCallTransaction(
                    contractAddress,
                    nonce,
                    quota,
                    validUntilBlock,
                    version,
                    chainId,
                    "0",
                    encodeFunction);
            String rawTx = tx.sign(credentials);

            AppSendTransaction.SendTransactionResult result = client.appSendRawTransaction(rawTx)
                    .send().getSendTransactionResult();

            System.out.println("------ result hash = " + result.getHash());
            System.out.println("------ result status = " + result.getStatus());

            Account account = new Account(Numeric.toHexStringWithPrefix(credentials.getEcKeyPair().getPrivateKey()), client);

            List<Type> list = null;
            if (wait){
                list = new ArrayList<>();
                TransactionReceipt transactionReceipt = waitForPolling(result.getHash());
                List<Log> logs = transactionReceipt.getLogs();
                if ((logs != null) && (!logs.isEmpty())){
                    list = FunctionReturnDecoder.decode(logs.get(0).getData(), function.getOutputParameters());
                }
            }
            FunctionResult functionResult = new FunctionResult();
            functionResult.transactionHash = result.getHash();
            functionResult.result = list;
            System.out.println(functionResult.result);
            System.out.println(result);
            return functionResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public FunctionResult send2(String abi, String contractName, String contractAddress, String method, Object[] args, boolean payable, BigInteger amount, boolean wait) throws Exception {
//        try {
//            int version = getVersion();
//            BigInteger chainId = getChainId();
//            long currentHeight = getBlockNumber();
//            long validUntilBlock = currentHeight + 80;
//            String nonce = getNonce();
//            long quota = 1000000;
//
//            Account account = new Account(Numeric.toHexStringWithPrefix(credentials.getEcKeyPair().getPrivateKey()), client);
//            Object object = account.callContract(contractAddress, method,
//                    nonce, quota, version,
//            chainId, "0", args);
//            System.out.println(object);
//
////
////            List<Type> list = null;
////            if (wait){
////                list = new ArrayList<>();
////                TransactionReceipt transactionReceipt = waitForPolling(result.getHash());
////                List<Log> logs = transactionReceipt.getLogs();
////                if ((logs != null) && (!logs.isEmpty())){
////                    list = FunctionReturnDecoder.decode(logs.get(0).getData(), function.getOutputParameters());
////                }
////            }
////            FunctionResult functionResult = new FunctionResult();
////            functionResult.transactionHash = result.getHash();
////            functionResult.result = list;
////            System.out.println(functionResult.result);
////            System.out.println(result);
//            return null;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public FunctionResult call(String abi, String contractName, String contractAddress, String method, List<String> args) throws IOException {
        Function function = convertFunction(abi, method, args.toArray(new String[0]));
        String encodeFunction = FunctionEncoder.encode(function);
        Call call = new Call(credentials.getAddress(), contractAddress, encodeFunction);
        String result = client.appCall(call, DefaultBlockParameterName.PENDING)
                .send().getValue();
        List<Type> list = FunctionReturnDecoder.decode(result, function.getOutputParameters());

        for (Type type : list){
            System.out.println("---- value = " + type.getValue());
            System.out.println("     type  = " + type.getTypeAsString());
        }

        return null;
    }

}