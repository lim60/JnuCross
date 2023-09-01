package jnu.chains;

import com.jnu.jnucross.chains.Numeric;
import com.baidu.xuper.api.Account;
import com.baidu.xuper.api.Transaction;
import com.baidu.xuper.api.XuperClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.baidu.xuper.crypto.xchain.sign.ECKeyPair;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author SDKany
 * @ClassName XuperChainContractTest
 * @Date 2023/8/4 21:39
 * @Version V1.0
 * @Description
 */
public class XuperChainContractTest {
    public static XuperClient client = new XuperClient("10.154.24.12:37101");
    static String abi = "[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"storedData\",\"type\":\"uint256\"}],\"name\":\"value\",\"type\":\"event\"},{\"inputs\":[],\"name\":\"get\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"retVal\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"x\",\"type\":\"uint256\"}],\"name\":\"set\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";
    static String bin = "6080604052600560005534801561001557600080fd5b506101a6806100256000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c806360fe47b11461003b5780636d4ce63c1461006b575b600080fd5b61005560048036038101906100509190610119565b610089565b6040516100629190610155565b60405180910390f35b6100736100d5565b6040516100809190610155565b60405180910390f35b6000816000819055507fc5a46ee641f0e0790ea3ed69ad8bed13888878711d6797fba0ad6834d3f809f16000546040516100c39190610155565b60405180910390a16000549050919050565b60008054905090565b600080fd5b6000819050919050565b6100f6816100e3565b811461010157600080fd5b50565b600081359050610113816100ed565b92915050565b60006020828403121561012f5761012e6100de565b5b600061013d84828501610104565b91505092915050565b61014f816100e3565b82525050565b600060208201905061016a6000830184610146565b9291505056fea26469706673582212209adcf49c10e34e40f98bb3869cb1a8699b4acb0ef40693326fbbdc55b11de19364736f6c63430008120033";
    static String contractName = "SimpleStorage";
    static Account account;
    static Map<String, String> args = new HashMap<>();
    static{
        ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger("79833914188285032734510047008692369036830969561638683117894543716557611565261"));
        account = Account.create(ecKeyPair);
        account.setContractAccount("XC1234567890123455@xuper");
    }
    @Test
    public void EVMContractTest(){
        //deploy();
        //invoke();
        invoke2();
    }

    public static void deploy(){
        System.out.println("0000");
        System.out.println(account.getKeyPair().getPrivateKey());
        Transaction t = client.deployEVMContract(account, bin.getBytes(), abi.getBytes(), contractName, args);
        System.out.println("txID:" + t.getTxid());
        // txID:f0a067f26bbd0bcda8fa2b873ac866305eb70a4b0fe0da8b651262e7fa414496
    }

    public static void invoke(){
        // storagepay is a payable method. Amount param can be NULL if there is no need to transfer to the xuperchain.xuperchain.contract.
        args.put("x", "1234");
        System.out.println(args);
        Transaction t1 = client.invokeEVMContract(account, contractName, "set", args, null);
        System.out.println("txID:" + t1.getTxid());
        System.out.println("tx gas:" + t1.getGasUsed());
        System.out.println("*************");
        System.out.println(t1.getRawTx());
        System.out.println("*************");
        System.out.println(t1);
        System.out.println("*************");
        // txID:85671a3352aac1ffb4d3562da361736b4a785e321b9b0c4ec477df68fadf2502
    }

    public static void invoke2(){
        Transaction t2 = client.queryEVMContract(account, contractName, "get", null);
        System.out.println("tx res getMessage:" + t2.getContractResponse().getMessage());
        System.out.println("tx res getBodyStr:" + t2.getContractResponse().getBodyStr());
        System.out.println("tx res getStatus:" + t2.getContractResponse().getStatus());
    }

    @Test
    public void ABIParse(){
        String[] args = new String[]{"1234"};
        System.out.println(parseAbi(abi, "set", args));
    }

    public static Map<String, String> parseAbi(String abi, String method, String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> result = new TreeMap<>();
        try {
            JsonNode trees = objectMapper.readTree(abi);
            for (JsonNode tree : trees) {
                String type = tree.get("type").asText();
                if (!"function".equalsIgnoreCase(type)) {
                    continue;
                }
                String name = tree.get("name").asText();
                if (name.equalsIgnoreCase(method)) {
                    JsonNode inputNode = tree.get("inputs");
                    if (inputNode.size() != args.length)
                        throw new IllegalArgumentException("args size and the method input size are not equaled! method = " + method + ", args = " + Arrays.toString(args) + ", inputNode = " + inputNode);
                    System.out.println(inputNode.size());
                    for(int i = 0; i < inputNode.size(); i ++){
                        result.put(inputNode.get(i).get("name").asText(), args[i]);
                    }
                }
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException("parse abi failed");
        }
    }
//
//    public static List<AbiFunctionType> makeType(JsonNode node) {
//        final List<AbiFunctionType> result = Lists.newArrayListWithCapacity(node.size());
//        node.forEach(
//                input -> {
//                    AbiFunctionType type = new AbiFunctionType();
//                    type.setType(input.get("type").asText());
//                    type.setName(input.get("name").asText());
//                    result.add(type);
//                });
//        return result;
//    }
}
