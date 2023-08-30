package com.jnu.jnucross.chains.xuperchain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.xuper.crypto.gm.hash.Hash;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.jnucross.chains.*;
import com.baidu.xuper.api.Account;
import com.baidu.xuper.api.XuperClient;
import com.baidu.xuper.crypto.xchain.sign.ECKeyPair;
import com.baidu.xuper.pb.XchainOuterClass;
import org.web3j.abi.TypeDecoder;
import org.web3j.abi.datatypes.AbiTypes;
import org.web3j.protocol.core.methods.response.AbiDefinition;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jnu.jnucross.chains.xuperchain.XuperChainUtils.*;

/**
 * @author SDKany
 * @ClassName XuperChainWrapper
 * @Date 2023/8/18 22:47
 * @Version V1.0
 * @Description
 */
public class XuperChainWrapper extends ChainWrapper {
    public String xuperChain_url; // = "http://10.154.24.12:8545";
    public XuperClient client;// = Web3j.build(new HttpService(geth_url));
    public Account account;//  = null;

    public XuperChainWrapper(){
        super();
    }

    public XuperChainWrapper(String url, BigInteger privateKey){
        xuperChain_url = url;
        client = new XuperClient(xuperChain_url);
        ECKeyPair ecKeyPair = ECKeyPair.create(privateKey);
        account = Account.create(ecKeyPair);
    }

    public XuperChainWrapper(String url, String hexPrivateKeyString){
        xuperChain_url = url;
        client = new XuperClient(xuperChain_url);
        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(hexPrivateKeyString));
        account = Account.create(ecKeyPair);
    }

    public static XuperChainWrapper build(){
        XuperChainWrapper xuperChainWrapper = new XuperChainWrapper();
        xuperChainWrapper.xuperChain_url = "10.154.24.12:37101";
        xuperChainWrapper.client = new XuperClient(xuperChainWrapper.xuperChain_url);
        ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger("111497060296999106528800133634901141644446751975433315540300236500052690483486"));
        xuperChainWrapper.account = Account.create(ecKeyPair);
        String contractAccount = "XC1234567890123456@xuper";//creatContractAccount();
        xuperChainWrapper.account.setContractAccount(contractAccount);
        return xuperChainWrapper;
    }

    @Override
    public void setChain(String url){
        client = new XuperClient(url);
    }

    @Override
    public void setAccount(String hexPrivateKeyString){
        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(hexPrivateKeyString));
        account = Account.create(ecKeyPair);
    }

    @Override
    public void setAccount(BigInteger privateKey){
        ECKeyPair ecKeyPair = ECKeyPair.create(privateKey);
        account = Account.create(ecKeyPair);
    }

    public static void main(String[] args) throws Exception {
        XuperChainWrapper chainWrapper = XuperChainWrapper.build();
//
        System.out.println("Balance = " + chainWrapper.getBalance());

        System.out.println(chainWrapper.account.getAKAddress());


//
        System.out.println("BlockNumber = " + chainWrapper.getBlockNumber()); // 获取块高
        System.out.println("----------------------------------");
        System.out.println("The 1st block is = " + chainWrapper.getBlockByNumber(2)); // 通过块高获取块
//        System.out.println("----------------------------------");
//        // 通过hash获取块
//        System.out.println("The block with hash 2a11f1aebb9c3ff173bbe8e1cdbf679ce72b5ca35aed50f99d3a4f6b90670d61 is = " + chainWrapper.getBlockByHash("2a11f1aebb9c3ff173bbe8e1cdbf679ce72b5ca35aed50f99d3a4f6b90670d61"));
//        System.out.println("----------------------------------");
////        // 获取transaction by hash
//        System.out.println("Get Transaction by hash = " + chainWrapper.getTransaction("5939f2423d45b7512d9af6ac9f56553b21d5d03f0057da407f1133dbdc4a8c86"));
        System.out.println("----------------------------------");
//
        String abi = "[{\"constant\":false,\"inputs\":[{\"name\":\"x\",\"type\":\"uint256\"}],\"name\":\"set\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"get\",\"outputs\":[{\"name\":\"retVal\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"}]";
        String bin = "608060405234801561001057600080fd5b50600560005560bf806100246000396000f30060806040526004361060485763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166360fe47b18114604d5780636d4ce63c146064575b600080fd5b348015605857600080fd5b5060626004356088565b005b348015606f57600080fd5b506076608d565b60408051918252519081900360200190f35b600055565b600054905600a165627a7a72305820419b352168794764ac1d5d6d3460eaffedc13c00bcbb4d2ff772148d2f0670fc0029";
        String contractName = "SimpleStorage2";

        Map<String, String> args2 = new HashMap<>();

        com.baidu.xuper.api.Transaction t = chainWrapper.client.deployEVMContract(chainWrapper.account, bin.getBytes(), abi.getBytes(), contractName, args2);
        System.out.println("txID:" + t.getTxid());

        System.exit(0);

    }

    @Override
    public BigInteger getBalance(){
        try {
            return client.getBalance(account.getAKAddress(),false);
        } catch (Exception e) {
            e.printStackTrace();
            return BigInteger.valueOf(0);
        }
    }

    public BigInteger getBalance(String address){
        try {
            return client.getBalance(address,false);
        } catch (Exception e) {
            e.printStackTrace();
            return BigInteger.valueOf(0);
        }
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

    // 返回合约账户
    public Map<String, String> deploy(String bin, String abi, String contractName, Map<String, String> args){
        com.baidu.xuper.api.Transaction t = client.deployEVMContract(account, bin.getBytes(), abi.getBytes(), contractName, args);
        Map<String, String> result = new HashMap<>();
        result.put("contractAccount", account.getContractAccount());
        result.put("txHash", t.getTxid());
        return result;
    }

    public Transaction transfer(String to, BigInteger amount){
        com.baidu.xuper.api.Transaction t =  client.transfer(account, to, amount, "1");
        return coverToTransaction(t.getRawTx());
    }

    // query不会发生交易，只请求合约上的函数
    @Override
    public FunctionResult call(String abi, String contractName, String contractAddress, String method, List<String> args) throws Exception{
        com.baidu.xuper.api.Transaction t = client.queryEVMContract(account, contractName, method, parseABI(abi, method, args));
//        System.out.println("-------------");
//        System.out.println(t.getRawTx());
//        System.out.println("-------------");

        FunctionResult functionResult = new FunctionResult();
        functionResult.transactionHash = null;
        String resultString = t.getContractResponse().getBodyStr();
        //System.out.println("resultString = " + resultString);
        functionResult.result = parseStr(resultString);
        return functionResult;
    }

    public static Map parseABI(String abi, String method, List<String> args) throws JsonProcessingException {
        Map<String, String> argsMap = new HashMap<>();
        AbiDefinition[] abiDefinitions = XuperChainUtils.objectMapper.readValue(abi, AbiDefinition[].class);
        AbiDefinition abiDefinition = null;
        for (int i = 0; i < abiDefinitions.length; i++){
            abiDefinition = abiDefinitions[i];
            if (abiDefinition.getName() == null){
                continue;
            }
            if(abiDefinition.getName().equals(method)){
                break;
            }
        }
        List<AbiDefinition.NamedType> inputs = abiDefinition.getInputs();
        for (int i = 0; i < inputs.size(); i++) {
            argsMap.put(inputs.get(i).getName(), args.get(i));
        }
        return argsMap;
    }

    public static List parseStr(String str){
        List<String> result = new ArrayList<>();
        ArrayList jsonObject = JSON.parseObject(str, ArrayList.class);
        for (Object a: jsonObject) {
            //System.out.println(a);
            HashMap h = JSON.parseObject(a.toString(), HashMap.class);
            for (Object key: h.keySet()){
                //System.out.println(h.get(key));
                result.add(h.get(key).toString());
            }
        }
        return result;
    }

    // invoke会产生交易
    @Override
    public FunctionResult send(String abi, String contractName, String contractAddress, String method, List<String> args, boolean payable, BigInteger amount, boolean wait) throws JsonProcessingException {
        com.baidu.xuper.api.Transaction t = client.invokeEVMContract(account, contractName, method, parseABI(abi, method, args), amount);

        FunctionResult functionResult = new FunctionResult();
        functionResult.transactionHash = t.getTxid();
        functionResult.result = parseStr(t.getContractResponse().getBodyStr());
        return functionResult;
    }
}
