package jnu.chains;

import com.jnu.jnucross.chains.FunctionResult;
import com.jnu.jnucross.chains.Numeric;
import com.jnu.jnucross.chains.Transaction;
import com.jnu.jnucross.chains.ethereum.EthereumUtils;
import com.jnu.jnucross.chains.ethereum.EthereumWrapper;
import com.jnu.jnucross.chains.ethereum.generated.SimpleStorage;
import com.jnu.jnucross.chains.ethereum.generated.SimpleStorage2;
import com.jnu.jnucross.chains.xuperchain.XuperChainWrapper;
import org.junit.Test;
import org.springframework.format.annotation.DateTimeFormat;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.tx.Contract;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author SDKany
 * @ClassName TransferTest
 * @Date 2023/8/24 17:47
 * @Version V1.0
 * @Description
 */
public class TransferTest {

    @Test
    public void EthTransferTest() throws IOException, InterruptedException {
        EthereumWrapper ethereumWrapper = EthereumWrapper.build();
        System.out.println("***Balance = " + ethereumWrapper.getBalance());
        String toAddress = "0x18bcd3c687d51539afd1240f639b6084d52b68c8";
        System.out.println("***0x18bcd3 Balance = " + ethereumWrapper.web3j.ethGetBalance(toAddress, DefaultBlockParameter.valueOf("latest")).send().getBalance());

        String txHash = ethereumWrapper.transferTo(toAddress, BigInteger.valueOf(2), false);

        System.out.println(txHash);
        Transaction transaction = ethereumWrapper.getTransaction(txHash);
        System.out.println("****" + transaction);

        while(transaction.getHash() == null){
            Thread.sleep(10000);
            transaction = ethereumWrapper.getTransaction(txHash);
            System.out.println("****" + transaction);
            System.out.println();
        }
        System.out.println();

       // System.out.println(ethereumWrapper.getTransaction("0xc2582ae1deec5af85028e0a089ec87286c4f8d6e"));
        System.out.println("***0x18bcd3 Balance = " + ethereumWrapper.web3j.ethGetBalance(toAddress, DefaultBlockParameter.valueOf("latest")).send().getBalance());
    }


    // SimpleStorage 合约0x60e58975473365047ab74e62cac24873ba460767
    // SimpleStorage2 合约0x67d79e16c42485f07b8c8c2a6f950dd85839b8ad
    // SimpleStorage3 合约0xa370a949db5b9e220f4ed034ed992ab5e136e4d7
    // SimpleStorage4 合约0xccf2458aa73043cbcd8a62531577de9f39a9ae9e
    @Test
    public void EthDeployAndSendTest() throws Exception {
        EthereumWrapper ethereumWrapper = EthereumWrapper.build();
        String abi = "[{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"string\",\"name\":\"createTime\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"onPortState\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"onPortDate\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"arriveEntityDigest\",\"type\":\"string\"}],\"name\":\"ValueArriveEntity\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"string\",\"name\":\"createTime\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"customNo\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"customFormDigest\",\"type\":\"string\"}],\"name\":\"ValueCustomFormEntity\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"string\",\"name\":\"createTime\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"inboundState\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"inboundType\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"inboundEntityDigest\",\"type\":\"string\"}],\"name\":\"ValueInboundEntity\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"orderIndex\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"orderDigest\",\"type\":\"string\"}],\"name\":\"ValueOrderEntity\",\"type\":\"event\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"ImpawnWhenLoaded\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"ReleaseWhenRefunded\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"createTime\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"onPortState\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"onPortDate\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"arriveEntityDigest\",\"type\":\"string\"},{\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"createArrival\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"createTime\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"customNo\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"customCode\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"checkState\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"importData\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"customFormDigest\",\"type\":\"string\"},{\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"createForm\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"createTime\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"inboundState\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"inboundType\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"inboundEntityDigest\",\"type\":\"string\"},{\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"createInbound\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"orderId\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"createTime\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"importType\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"contractId\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"clientName\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"loadingPort\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"destinationPort\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"businessGroupId\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"tenantId\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"orderDigest\",\"type\":\"string\"}],\"name\":\"createOrder\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"queryArrival\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"queryForm\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"queryInbound\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"queryInboundState\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"queryOrder\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"queryOrderState\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]";
        //        System.out.println("Balance : " + ethereumWrapper.getBalance());


        // Test deploy
        //EthereumUtils.deployContract(null);
        //EthereumUtils.generateClass("src/main/resources/chains-sample/ethereum/contract/SimpleStorage2.abi", "src/main/resources/chains-sample/ethereum/contract/SimpleStorage2.bin", "src/main/java/com/jnu/jnucross/chains/ethereum/generated");
//        BigInteger gasPrice = ethereumWrapper.web3j.ethGasPrice().send().getGasPrice();
//        System.out.println(gasPrice);
//        ContractGasProvider gasProvider = new StaticGasProvider(BigInteger.TEN, BigInteger.valueOf(8_000_000L));
//        BigInteger chainID = ethereumWrapper.web3j.ethChainId().send().getChainId();
//        TransactionManager transactionManager = new RawTransactionManager(ethereumWrapper.web3j, ethereumWrapper.credentials, chainID.longValue());
//        SimpleStorage2 contract = SimpleStorage2.deploy(ethereumWrapper.web3j,transactionManager,gasProvider).send();
//        System.out.println("SimpleStorage 2 address : " + contract.getContractAddress());
        // end of test deploy

        // Test send
        List<String> args = new ArrayList<String>();
//        BigInteger value = BigInteger.valueOf(100);
//        Uint256 value256 = new Uint256(value);
//        args.add(TypeEncoder.encode(value256));
//        String orderId = "22222222222222";
//        args.add(TypeEncoder.encode(new Utf8String(orderId)));
//        Date localDateTime = new Date();
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        String createTime = ft.format(localDateTime);
//        args.add(TypeEncoder.encode(new Utf8String(createTime)));
//        String importType = "保税2";
//        args.add(TypeEncoder.encode(new Utf8String(importType)));
//        String contractId = "1234567890ABCDEF2";
//        args.add(TypeEncoder.encode(new Utf8String(contractId)));
//        String clientName = "宝马2";
//        args.add(TypeEncoder.encode(new Utf8String(clientName)));
//        String loadingPort = "青岛港2";
//        args.add(TypeEncoder.encode(new Utf8String(loadingPort)));
//        String destinationPort = "南沙港2";
//        args.add(TypeEncoder.encode(new Utf8String(destinationPort)));
//        String businessGroupId = "1234562";
//        args.add(TypeEncoder.encode(new Utf8String(businessGroupId)));
//        String tenantId = "666666666662";
//        args.add(TypeEncoder.encode(new Utf8String(tenantId)));
//        String orderDigest = "0xa370a949db5b9e220e4ed034ed992ab5e136e4d9";
//        args.add(TypeEncoder.encode(new Utf8String(orderDigest)));
////
////
////        String aString = "Three";
////        Utf8String utf8String = new Utf8String(aString);
////        args.add(TypeEncoder.encode(utf8String));
//        System.out.println("**** args = " + args);
//        FunctionResult functionResult = ethereumWrapper.send(abi, "ImportOrderContract2","0x7d324e7b4b21f36ffca4c7cf62aa7d1e235aafed", "createOrder", args, false, null, true);
//        System.out.println("----- txHash = " + functionResult.transactionHash);
//
//        for (Object type : functionResult.result) {
//            System.out.println("----- functionResult = " + ((Type) type).getValue());
//        }
//
//        // end of test send
//
//
//        // Test Call
        List callArgs = new ArrayList<String>();
        BigInteger index = BigInteger.valueOf(0);
        callArgs.add(TypeEncoder.encode(new Uint256(index)));

        FunctionResult result1 = ethereumWrapper.call(abi, "","0x7d324e7b4b21f36ffca4c7cf62aa7d1e235aafed", "queryOrderState", callArgs);
        for (Object type : result1.result) {
            System.out.println("!!!!result = " + ((Type) type).getValue());
        }
//        // end of Test Call
//
//        System.out.println("Balance : " + ethereumWrapper.getBalance());

    }

    @Test
    public void XuperDeployAndSendTest() throws Exception{
        XuperChainWrapper xuperChainWrapper = XuperChainWrapper.build();
        String abi = "[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"get\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"x\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"dataInput\",\"type\":\"string\"}],\"name\":\"set\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";
        String bin = "608060405234801561001057600080fd5b5060008081905550610916806100276000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c8063643719771461003b5780639507d39a1461006d575b600080fd5b61005560048036038101906100509190610406565b61009e565b604051610064939291906104f0565b60405180910390f35b6100876004803603810190610082919061052e565b6101c4565b60405161009592919061055b565b60405180910390f35b600080606060008060008154809291906100b7906105ba565b919050559050856001600083815260200190815260200160002060000181905550846001600083815260200190815260200160002060010190816100fb919061080e565b508060016000838152602001908152602001600020600001546001600084815260200190815260200160002060010180805461013690610631565b80601f016020809104026020016040519081016040528092919081815260200182805461016290610631565b80156101af5780601f10610184576101008083540402835291602001916101af565b820191906000526020600020905b81548152906001019060200180831161019257829003601f168201915b50505050509050935093509350509250925092565b60006060600054600160008581526020019081526020016000206001018080546101ed90610631565b80601f016020809104026020016040519081016040528092919081815260200182805461021990610631565b80156102665780601f1061023b57610100808354040283529160200191610266565b820191906000526020600020905b81548152906001019060200180831161024957829003601f168201915b5050505050905091509150915091565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b61029d8161028a565b81146102a857600080fd5b50565b6000813590506102ba81610294565b92915050565b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b610313826102ca565b810181811067ffffffffffffffff82111715610332576103316102db565b5b80604052505050565b6000610345610276565b9050610351828261030a565b919050565b600067ffffffffffffffff821115610371576103706102db565b5b61037a826102ca565b9050602081019050919050565b82818337600083830152505050565b60006103a96103a484610356565b61033b565b9050828152602081018484840111156103c5576103c46102c5565b5b6103d0848285610387565b509392505050565b600082601f8301126103ed576103ec6102c0565b5b81356103fd848260208601610396565b91505092915050565b6000806040838503121561041d5761041c610280565b5b600061042b858286016102ab565b925050602083013567ffffffffffffffff81111561044c5761044b610285565b5b610458858286016103d8565b9150509250929050565b61046b8161028a565b82525050565b600081519050919050565b600082825260208201905092915050565b60005b838110156104ab578082015181840152602081019050610490565b60008484015250505050565b60006104c282610471565b6104cc818561047c565b93506104dc81856020860161048d565b6104e5816102ca565b840191505092915050565b60006060820190506105056000830186610462565b6105126020830185610462565b818103604083015261052481846104b7565b9050949350505050565b60006020828403121561054457610543610280565b5b6000610552848285016102ab565b91505092915050565b60006040820190506105706000830185610462565b818103602083015261058281846104b7565b90509392505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60006105c58261028a565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82036105f7576105f661058b565b5b600182019050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061064957607f821691505b60208210810361065c5761065b610602565b5b50919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b6000600883026106c47fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82610687565b6106ce8683610687565b95508019841693508086168417925050509392505050565b6000819050919050565b600061070b6107066107018461028a565b6106e6565b61028a565b9050919050565b6000819050919050565b610725836106f0565b61073961073182610712565b848454610694565b825550505050565b600090565b61074e610741565b61075981848461071c565b505050565b5b8181101561077d57610772600082610746565b60018101905061075f565b5050565b601f8211156107c25761079381610662565b61079c84610677565b810160208510156107ab578190505b6107bf6107b785610677565b83018261075e565b50505b505050565b600082821c905092915050565b60006107e5600019846008026107c7565b1980831691505092915050565b60006107fe83836107d4565b9150826002028217905092915050565b61081782610471565b67ffffffffffffffff8111156108305761082f6102db565b5b61083a8254610631565b610845828285610781565b600060209050601f8311600181146108785760008415610866578287015190505b61087085826107f2565b8655506108d8565b601f19841661088686610662565b60005b828110156108ae57848901518255600182019150602085019450602081019050610889565b868310156108cb57848901516108c7601f8916826107d4565b8355505b6001600288020188555050505b50505050505056fea264697066735822122088d41c8760ddcee4166df6e1d0da4a839354adc0e415bbb5081d6b017579dbd364736f6c63430008120033";
        String contractName = "SimpleStorage7";

        // xuperchain 部署合约 {contractAccount=XC1234567890123455@xuper, txHash=4901aaf1a5025041ab6a41bcf9c49a95026828129309368578e535b79462cad1}
//        Map<String, String> deployResult = xuperChainWrapper.deploy(bin, abi, contractName, new HashMap<String, String>());
//        System.out.println(deployResult);
        // xuperchain 完成合约部署

        // call 方法
        List<String> args = new ArrayList<>();
        args.add("345");
        args.add("Hello345");
        FunctionResult functionResult = xuperChainWrapper.send(abi, contractName, null, "set", args, false, null, false);
        System.out.println("----" + functionResult.result);
        System.out.println("----" + functionResult.transactionHash);

        // send 方法
//        List<String> args = new ArrayList<>();
//        args.add("1");
//        FunctionResult functionResult = xuperChainWrapper.call(abi, contractName, null, "get", args);
//        System.out.println("****" + functionResult.result);
//        System.out.println("****" + functionResult.transactionHash);
    }
}
