package jnu.chains;

import com.citahub.cita.abi.TypeEncoder;
import com.citahub.cita.abi.datatypes.Type;
import com.citahub.cita.abi.datatypes.Utf8String;
import com.citahub.cita.abi.datatypes.generated.Uint256;
import com.citahub.cita.protocol.core.DefaultBlockParameterName;
import com.jnu.jnucross.chains.FunctionResult;
import com.jnu.jnucross.chains.cita.CITAWrapper;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SDKany
 * @ClassName CITATest
 * @Date 2023/8/27 21:15
 * @Version V1.0
 * @Description
 */
public class CITATest {
    // SimpleStorage 0x409070269a6ce46a61e49f29838103b09d17fe1b 没有event
    // SimpleStorage3 0xfd229428e811bef38812890b40e845f077c75a19 有event

    @Test
    public void CITADeployAndSendTest() throws Exception {
        CITAWrapper citaWrapper = CITAWrapper.build();
        String bin = "6080604052600560005534801561001557600080fd5b506101a6806100256000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c806360fe47b11461003b5780636d4ce63c1461006b575b600080fd5b61005560048036038101906100509190610119565b610089565b6040516100629190610155565b60405180910390f35b6100736100d5565b6040516100809190610155565b60405180910390f35b6000816000819055507fc5a46ee641f0e0790ea3ed69ad8bed13888878711d6797fba0ad6834d3f809f16000546040516100c39190610155565b60405180910390a16000549050919050565b60008054905090565b600080fd5b6000819050919050565b6100f6816100e3565b811461010157600080fd5b50565b600081359050610113816100ed565b92915050565b60006020828403121561012f5761012e6100de565b5b600061013d84828501610104565b91505092915050565b61014f816100e3565b82525050565b600060208201905061016a6000830184610146565b9291505056fea264697066735822122072a0d2eb87f18e6083696446e5db71d650efa6972d6659229dbb24f9e14c8f2864736f6c63430008120033";
        String abi = "[{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"storedData\",\"type\":\"uint256\"}],\"name\":\"value\",\"type\":\"event\"},{\"inputs\":[],\"name\":\"get\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"retVal\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"x\",\"type\":\"uint256\"}],\"name\":\"set\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";

//////        // cita 合约部署
        /*String txHash = citaWrapper.deploy(bin,abi,true);
        System.out.println("txHash or contractAddress = " + txHash);*/

        String contractAddress = "0xfd229428e811bef38812890b40e845f077c75a19";
//
//        txHash = 0xaacfe4152bbba582c05f30d093439ec3246134881b527ff7f4a9bf91dfea2051
//        ContractAddress = 0xedf5a3ad8acb26fffbb7672e7127fe822c249662


        // cita send合约调用
        List<String> args = new ArrayList<String>();
        args.add("456");
        FunctionResult functionResult = citaWrapper.send(abi, "SimpleStorage",contractAddress, "set", args, false, null, true);
        System.out.println("----- txHash = " + functionResult.transactionHash);

        for (Object type : functionResult.result) {
            System.out.println("----- functionResult = " + ((Type) type).getValue());
        }


//        Object[] args = new Object[2];
//        args[0] = BigInteger.valueOf(0);
//        args[1] = "00000000";
//        BigInteger value = BigInteger.valueOf(0);
//        Uint256 value256 = new Uint256(value);
//        args.add(TypeEncoder.encode(value256));
//        String orderId = "00000000";
//        args.add(TypeEncoder.encode(new Utf8String(orderId)));


//        FunctionResult functionResult = citaWrapper.send2(abi, "SimpleStorage5",contractAddress, "set", args, false, null, true);
//        System.out.println("----- txHash = " + functionResult.transactionHash);
//
//        for (Object type : functionResult.result) {
//            System.out.println("----- functionResult = " + ((Type) type).getValue());
//        }

//        String abi2 = citaWrapper.client.appGetAbi(
//                "0xcae0d2ffeff0826adbb83b568aba5342a287123f", DefaultBlockParameterName.PENDING).send().getAbi();
//        System.out.println(abi2);

        // cita call合约调用

//        List<String> args2 = new ArrayList<String>();
//        FunctionResult functionResult2 = citaWrapper.call(abi, "SimpleStorage",contractAddress, "get", args2);

    }
}
