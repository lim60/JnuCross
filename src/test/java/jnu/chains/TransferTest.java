package jnu.chains;

import com.jnu.jnucross.chains.FunctionResult;
import com.jnu.jnucross.chains.Numeric;
import com.jnu.jnucross.chains.Transaction;
import com.jnu.jnucross.chains.ethereum.EthereumUtils;
import com.jnu.jnucross.chains.ethereum.EthereumWrapper;
import com.jnu.jnucross.chains.ethereum.generated.SimpleStorage;
import com.jnu.jnucross.chains.ethereum.generated.SimpleStorage2;
import org.junit.Test;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.tx.Contract;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
    @Test
    public void EthDeployAndSendTest() throws Exception {
        EthereumWrapper ethereumWrapper = EthereumWrapper.build();
        String abi = "[{\"inputs\":[],\"name\":\"get\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"retVal\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"x\",\"type\":\"uint256\"}],\"name\":\"set\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"retVal\",\"type\":\"uint256\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";
//
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
        BigInteger value = BigInteger.valueOf(99);
        Uint256 value256 = new Uint256(value);
        //args.add(TypeEncoder.encode(value256));
        args.add("0x0000000000000000000000000000000000000000000000000000000000000008");
        System.out.println("**** args = " + args);
        FunctionResult functionResult = ethereumWrapper.send(abi, "SimpleStorage2","0x67d79e16c42485f07b8c8c2a6f950dd85839b8ad", "set", args, false, null, true);
        System.out.println("----- txHash = " + functionResult.transactionHash);

        for (Object type : functionResult.result) {
            System.out.println("----- functionResult = " + ((Type) type).getValue());
        }
//
//        // end of test send
//
//
//        // Test Call
        FunctionResult result1 = ethereumWrapper.call(abi, "SimpleStorage2","0x67d79e16c42485f07b8c8c2a6f950dd85839b8ad", "get", new ArrayList<String>());
        for (Object type : result1.result) {
            System.out.println("!!!!result = " + ((Type) type).getValue());
        }
//        // end of Test Call
//
//        System.out.println("Balance : " + ethereumWrapper.getBalance());

    }
}
