package jnu.chains;

import com.citahub.cita.abi.FunctionEncoder;
import com.citahub.cita.abi.FunctionReturnDecoder;
import com.citahub.cita.abi.TypeReference;
import com.citahub.cita.abi.datatypes.Function;
import com.citahub.cita.abi.datatypes.generated.Uint256;
import com.citahub.cita.crypto.Credentials;
import com.citahub.cita.protocol.CITAj;
import com.citahub.cita.protocol.core.DefaultBlockParameterName;
import com.citahub.cita.protocol.core.methods.request.Call;
import com.citahub.cita.protocol.core.methods.request.Transaction;
import com.citahub.cita.protocol.core.methods.response.TransactionReceipt;
import java.math.BigInteger;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import com.citahub.cita.protocol.http.HttpService;
import com.jnu.jnucross.chains.Numeric;
import com.jnu.jnucross.chains.cita.CITAUtils;
import org.junit.Test;

public class AdvanceTransactionTest {

    private static CITAj service;
    private static String senderPrivateKey;
    private static Credentials credentials;
    private static int version;
    private static BigInteger chainId;
    private static long quota;
    private static String value = "0";
    private static Transaction.CryptoTx cryptoTx;

    static {
        service = CITAj.build(new HttpService("http://10.154.24.5:1337"));
        credentials = Credentials.create("0xd22bc2c10f45a48a80d2f7b7b8312d05be8866d6ba3880518316af186024b744");
        senderPrivateKey = Numeric.toHexStringWithPrefix(credentials.getEcKeyPair().getPrivateKey());
        version = TestUtil.getVersion(service);
        chainId = TestUtil.getChainId(service);
        quota = 1000000;
        cryptoTx = Transaction.CryptoTx.SECP256K1;

        System.out.println("chainID = " + chainId);
        System.out.println("version = " + version);
    }

    private Random random;
    private long validUntilBlock;
    private int sendCount;
    private int threadCount;
    private boolean isEd25519AndBlake2b;
    private String contractAddress;


    public void initAdvanceTransactionTest(int sdCount, int thdCount, boolean isEd25519AndBlake2b) {
        try {
            random = new Random(System.currentTimeMillis());
            this.validUntilBlock = TestUtil.getValidUtilBlock(service, 100).longValue();
            this.sendCount = sdCount;
            this.threadCount = thdCount;
            this.isEd25519AndBlake2b = isEd25519AndBlake2b;
            System.out.println("Initial block height: " + TestUtil.getCurrentHeight(service));
        } catch (Exception e) {
            e.printStackTrace();
            //System.exit(1);
        }
    }

    // Deploy xuperchain.xuperchain.contract, return transaction hash
    private String deployContract(boolean isEd25519AndBlake2b) throws Exception {
        // xuperchain.xuperchain.contract.bin
        String contractCode = "606060405260008055341561001357600080f"
                + "d5b60fc806100216000396000f30060606040526004361060"
                + "525763ffffffff7c010000000000000000000000000000000"
                + "00000000000000000000000006000350416634f2be91f8114"
                + "60575780636d4ce63c146069578063d826f88f14608b575b6"
                + "00080fd5b3415606157600080fd5b6067609b565b005b3415"
                + "607357600080fd5b607960b2565b604051908152602001604"
                + "05180910390f35b3415609557600080fd5b606760b8565b60"
                + "005460ad90600163ffffffff60be16565b600055565b60005"
                + "490565b60008055565b8181018281101560ca57fe5b929150"
                + "505600a165627a7a72305820870dd43666c8c38bef68662f8"
                + "4b2c0e537643ad06bd44e4fb85b76114f99d8750029";
        String nonce = String.valueOf(Math.abs(this.random.nextLong()));
        Transaction tx = Transaction.createContractTransaction(
                nonce, quota, validUntilBlock,
                version, chainId, value, contractCode);

        String rawTx = tx.sign(senderPrivateKey, cryptoTx, false);

        return service.appSendRawTransaction(rawTx)
                .send().getSendTransactionResult().getHash();
    }

    // Contract function reset call
    private String funcResetCall(
            String contractAddress, boolean isEd25519AndBlake2b)
            throws Exception {
        Function resetFunc = new Function(
                "reset",
                Collections.emptyList(),
                Collections.emptyList()
        );

        String resetFuncData = FunctionEncoder.encode(resetFunc);

        String nonce = TestUtil.getNonce();
        Transaction tx = Transaction.createFunctionCallTransaction(
                contractAddress,
                nonce,
                this.quota,
                this.validUntilBlock,
                version,
                chainId,
                value,
                resetFuncData);
        String rawTx = tx.sign(senderPrivateKey, cryptoTx, false);

        return service.appSendRawTransaction(rawTx)
                .send().getSendTransactionResult().getHash();
    }

    // Contract function add call
    public void funcAddCall(
            String contractAddress, boolean isEd25519AndBlake2b)
            throws Exception {
        Function addFunc = new Function(
                "add",
                Collections.emptyList(),
                Collections.emptyList()
        );
        String addFuncData = FunctionEncoder.encode(addFunc);

        String nonce = String.valueOf(
                Math.abs(this.random.nextLong()));
        Transaction tx = Transaction.createFunctionCallTransaction(
                contractAddress,
                nonce,
                this.quota,
                this.validUntilBlock,
                version,
                chainId,
                value,
                addFuncData);
        String rawTx = tx.sign(senderPrivateKey, cryptoTx, false);

        service.appSendRawTransaction(rawTx).send();
    }

    // eth_call
    private String call(
            String from, String contractAddress, String callData)
            throws Exception {
        Call call = new Call(from, contractAddress, callData);
        return service.appCall(
                call, DefaultBlockParameterName.PENDING).send().getValue();
    }

    // Get transaction receipt
    private TransactionReceipt getTransactionReceipt(String txHash)
            throws Exception {
        return service.appGetTransactionReceipt(txHash)
                .send().getTransactionReceipt();
    }

    public void runContract() throws Exception {
        boolean isEd25519AndBlake2b = this.isEd25519AndBlake2b;
        long dealWithCount = 0;
        String ethCallResult;
        String from = credentials.getAddress();
        long startBlock = TestUtil.getCurrentHeight(service).longValue();
        long oldBlock = startBlock;
        long currentBlock = startBlock;

        // deploy xuperchain.xuperchain.contract
        String deployContractTxHash = deployContract(isEd25519AndBlake2b);
        System.out.println("wait to deploy xuperchain.xuperchain.contract , txHash: "
                + deployContractTxHash);

        // wait for xuperchain.xuperchain.contract deployment.
        // don't wait for 3 more blocks
        // because block height increase might stop or even decrease in some case.
        System.out.println("Wait for xuperchain.xuperchain.contract deployment. ");
        Thread.sleep(10000);

        // get xuperchain.xuperchain.contract address from receipt
        int countForContractDeployment = 0;
        while (true) {
            TransactionReceipt receipt = service
                    .appGetTransactionReceipt(deployContractTxHash)
                    .send().getTransactionReceipt();
            if (receipt != null) {
                if (receipt.getErrorMessage() == null) {
                    this.contractAddress = receipt.getContractAddress();
                    System.out.println("Contract is deployed successfully.");
                    break;
                } else {
                    System.out.println("Failed to deploy smart xuperchain.xuperchain.contract. Error: "
                            + receipt.getErrorMessage());
                    //System.exit(1);
                }
            } else {
                System.out.println("Waiting for xuperchain.xuperchain.contract deployment....");
                Thread.sleep(3000);
                if (countForContractDeployment++ > 3) {
                    System.out.println("Timeout, failed to deploy xuperchain.xuperchain.contract.");
                    //System.exit(1);
                }
            }
        }

        String resetTxHash = funcResetCall(this.contractAddress, isEd25519AndBlake2b);

        //call smart xuperchain.xuperchain.contract function and wait for receipt.
        int countForResetFunctionCall = 0;
        while (true) {
            TransactionReceipt receipt = service
                    .appGetTransactionReceipt(resetTxHash)
                    .send().getTransactionReceipt();
            if (receipt != null) {
                if (receipt.getErrorMessage() == null) {
                    System.out.println("Count is reset successfully.");
                    System.out.println("reset transaction hash = " + receipt.getTransactionHash());
                    break;
                } else {
                    System.out.println("Failed to reset count. ");
                    System.out.println("error :" + receipt.getErrorMessage());
                    System.exit(1);
                }
            } else {
                System.out.println("Waiting to reset count....");
                Thread.sleep(3000);
                if (countForResetFunctionCall++ > 3) {
                    System.out.println("Timeout, failed to reset count.");
                    System.exit(1);
                }
            }
        }


        System.out.println("Contract address: " + this.contractAddress + ", start call add...");
        long startTime = System.currentTimeMillis();
        //start thread
        Thread[] threads = new Thread[this.threadCount];
        int countPerThread = this.sendCount;
        for (int i = 0; i < this.threadCount; i++) {
            Thread t = new Thread(
                    () -> {
                        try {
                            int j = 0;
                            while (j < countPerThread) {
                                funcAddCall(contractAddress, isEd25519AndBlake2b);
                                j++;
                            }
                        } catch (Exception e) {
                            System.out.println("Failed to call xuperchain.xuperchain.contract function.");
                            e.printStackTrace();
                            //System.exit(1);
                        }
                    });
            t.start();
            threads[i] = t;
        }

        for (int k = 0; k < this.threadCount; k++) {
            threads[k].join();
        }
        System.out.println("send tx use "
                + (System.currentTimeMillis() - startTime) + " ms");

        //get result
        while (true) {
            Thread.sleep(2000);
            currentBlock = TestUtil.getCurrentHeight(service).longValue();
            if (currentBlock < oldBlock) {
                System.out.println("Error : Height is decreasing");
            }
            oldBlock = currentBlock;
            if (currentBlock >= this.validUntilBlock) {
                break;
            }

            Function getCall = new Function(
                    "get",
                    Collections.emptyList(),
                    Arrays.asList(new TypeReference<Uint256>() {
                    })
            );

            String getCallData = FunctionEncoder.encode(getCall);
            ethCallResult = call(from, contractAddress, getCallData);
            String ethCallResultReadable = FunctionReturnDecoder.decode(
                            ethCallResult, getCall.getOutputParameters())
                    .get(0).toString();
            System.out.println("ethCallResult: " + ethCallResultReadable);
            if (ethCallResult == null || ethCallResult.length() < 3) {
                continue;
            }
            dealWithCount = Long.valueOf(ethCallResult.substring(2), 16);
            System.out.println("eth_call result: " + dealWithCount);
            if (dealWithCount == this.sendCount * this.threadCount) {
                break;
            }
        }
        long endTime = System.currentTimeMillis();

        System.out.println("complete input count : " + this.sendCount * this.threadCount
                + ", actual count: " + dealWithCount);
        System.out.println("performance(transactioncount/s):");
        System.out.println("start time: " + startTime
                + " ms\r\n" + "end   time: " + endTime + " ms");
        double tps = dealWithCount / ((endTime - startTime) / 1000.0);
        DecimalFormat df = new DecimalFormat("0.00000000");
        String outStr = "performance - send tx tese case ...... success , result : "
                + df.format(tps) + " TPS";
        System.out.println(outStr);
    }

    @Test
    public void testAdvanceTransaction() throws Exception {

        int sendcount = 20;
        int threadcount = 1;
        boolean isEd25519AndBlake2b = false;

        System.out.println(
                "sendCount: " + sendcount + " threadCount: " + threadcount
                        + " isEd25519AndBlake2b: " + isEd25519AndBlake2b);
        initAdvanceTransactionTest(sendcount, threadcount, isEd25519AndBlake2b);
        runContract();
        System.out.println("Performance - test case complete");
    }

}