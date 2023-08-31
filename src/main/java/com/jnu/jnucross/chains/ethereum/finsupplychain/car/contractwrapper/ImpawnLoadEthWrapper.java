package com.jnu.jnucross.chains.ethereum.finsupplychain.car.contractwrapper;

import com.jnu.jnucross.chains.FunctionResult;
import com.jnu.jnucross.chains.ethereum.EthereumUtils;
import com.jnu.jnucross.chains.ethereum.EthereumWrapper;
//import com.jnu.jnucross.chains.ethereum.generated.ImpawnLoan;
import com.jnu.jnucross.chains.ethereum.generated.ImpawnLoan;
import com.jnu.jnucross.chains.xuperchain.finsupplychain.car.entity.ImpawnLoanRequestOC;
import org.junit.Test;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ImpawnLoadEthWrapper {

    private static EthereumWrapper ethereumWrapper = EthereumWrapper.build();
    private static String abi = "[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"string\",\"name\":\"stateStr\",\"type\":\"string\"}],\"name\":\"Examine\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"loanIndex\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"ifSign\",\"type\":\"string\"}],\"name\":\"ImpawnNotice\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"loanIndex\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"clientAddr\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"clientDigest\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"clientAccount\",\"type\":\"string\"}],\"name\":\"ValueRequest\",\"type\":\"event\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"orderIndex\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"bankAddr\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"requestedValue\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"clientAddr\",\"type\":\"address\"},{\"internalType\":\"string\",\"name\":\"clientDigest\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"clientAccount\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"bankDigest\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"bankAccount\",\"type\":\"string\"}],\"name\":\"createImpawnLoanRequest\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"loanIndex\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"orderState\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"taxState\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"importCertificate\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"proposal\",\"type\":\"string\"}],\"name\":\"ifImpawnLoan\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"loanIndex\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"statement\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"clientAccount\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"bankAccount\",\"type\":\"string\"}],\"name\":\"makeImpawnLoan\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"loanIndex\",\"type\":\"uint256\"}],\"name\":\"queryImpawnLoanState\",\"outputs\":[{\"internalType\":\"enum ImpawnLoan.State\",\"name\":\"\",\"type\":\"uint8\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"loanIndex\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"statement\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"clientAccount\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"bankAccount\",\"type\":\"string\"}],\"name\":\"refundImpawnLoan\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"loanIndex\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"contractSignedDigest\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"creditAgreementDigest\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"noticeDigest\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"receiptDigest\",\"type\":\"string\"}],\"name\":\"signContractForImpawnLoan\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"loanIndex\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"unsignEvidenceDigest\",\"type\":\"string\"}],\"name\":\"unsignImpawnLoan\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";
    private static String contractAddr = "0x9889d2e13c4e5f4c2054674601193a9cb0df8abd";


    public void genImpawnLoanClass() throws IOException {
        EthereumUtils.generateClass("src/main/resources/chains-sample/ethereum/contract/supplychain/ImpawnLoan.abi", "src/main/resources/chains-sample/ethereum/contract/supplychain/ImpawnLoan.bin", "src/main/java/com/jnu/jnucross/chains/ethereum/generated");
        BigInteger gasPrice = ethereumWrapper.web3j.ethGasPrice().send().getGasPrice();
        System.out.println(gasPrice);
    }

    @Test
    public void deploy() throws Exception {
        ContractGasProvider gasProvider = new StaticGasProvider(BigInteger.TEN, BigInteger.valueOf(8_000_000L));
        BigInteger chainID = ethereumWrapper.web3j.ethChainId().send().getChainId();
        TransactionManager transactionManager = new RawTransactionManager(ethereumWrapper.web3j, ethereumWrapper.credentials, chainID.longValue());
        ImpawnLoan contract = ImpawnLoan.deploy(ethereumWrapper.web3j,transactionManager,gasProvider).send();
        System.out.println("ImpawnLoad address : " + contract.getContractAddress());
        /*0x9889d2e13c4e5f4c2054674601193a9cb0df8abd 23-08-31*/
    }


    public BigInteger uploadLoadRequestTC(ImpawnLoanRequestOC impawnLoanRequestOC) throws Exception {
        List<String> args = new ArrayList<String>();
        args.add(TypeEncoder.encode(new Uint256(impawnLoanRequestOC.getOrderIndexOnChain())));
        args.add(TypeEncoder.encode(new Address(impawnLoanRequestOC.getBankAddr())));
        args.add(TypeEncoder.encode(new Uint256(impawnLoanRequestOC.getRequestedValue())));
        args.add(TypeEncoder.encode(new Address(impawnLoanRequestOC.getClientAddr())));
        args.add(TypeEncoder.encode(new Utf8String(impawnLoanRequestOC.getClientInfoDigest())));
        args.add(TypeEncoder.encode(new Utf8String(impawnLoanRequestOC.getClientOffAccount())));
        args.add(TypeEncoder.encode(new Utf8String(impawnLoanRequestOC.getBankInfoDigest())));
        args.add(TypeEncoder.encode(new Utf8String(impawnLoanRequestOC.getBankOffAccount())));

        System.out.println("**** args = " + args);
        FunctionResult functionResult = ethereumWrapper.send(abi, "ImpawnLoad",contractAddr, "createImpawnLoanRequest", args, false, null, true);
        System.out.println("----- txHash = " + functionResult.transactionHash);

        /*for (Object type : functionResult.result) {
            System.out.println("----- functionResult = " + ((Type) type).getValue());
        }*/

        BigInteger returnLoadIndex = new BigInteger(TypeEncoder.encode((Type) functionResult.result.get(0)));
        impawnLoanRequestOC.setLoadIndexOC(returnLoadIndex);
        return returnLoadIndex;
    }

    public String saveLoadProposalOC(BigInteger loadOCIndex, String orderState, String taxState,
                                     String importCertificate, String proposal) throws Exception {

        List<String> args = new ArrayList<String>();
        args.add(TypeEncoder.encode(new Uint256(loadOCIndex)));
        args.add(TypeEncoder.encode(new Utf8String(orderState)));
        args.add(TypeEncoder.encode(new Utf8String(taxState)));
        args.add(TypeEncoder.encode(new Utf8String(importCertificate)));
        args.add(TypeEncoder.encode(new Utf8String(proposal)));

        FunctionResult functionResult = ethereumWrapper.send(abi, "ImpawnLoad",contractAddr, "ifImpawnLoan", args, false, null, true);
        System.out.println("----- txHash = " + functionResult.transactionHash);
        String proposalResult = ((Type) functionResult.result.get(0)).toString();
        System.out.println("proposalResult------" + proposalResult);

        return  proposalResult;
    }

    public String uploadSignedContractOC(BigInteger loadOCIndex, String contractSignedDigest, String creditAgreementDigest,
                                         String noticeDigest, String receiptDigest) throws Exception {

        List<String> args = new ArrayList<String>();
        args.add(TypeEncoder.encode(new Uint256(loadOCIndex)));
        args.add(TypeEncoder.encode(new Utf8String(contractSignedDigest)));
        args.add(TypeEncoder.encode(new Utf8String(creditAgreementDigest)));
        args.add(TypeEncoder.encode(new Utf8String(noticeDigest)));
        args.add(TypeEncoder.encode(new Utf8String(receiptDigest)));

        FunctionResult functionResult = ethereumWrapper.send(abi, "ImpawnLoad",contractAddr, "signContractForImpawnLoan", args, false, null, true);
        System.out.println("----- txHash = " + functionResult.transactionHash);
        String signResult = ((Type) functionResult.result.get(1)).toString();
        System.out.println("proposalResult------" + signResult);

        return  signResult;
    }

    /*TODO:check
    public void queryLodStateOC(BigInteger loadOCIndex) throws Exception {
        List callArgs = new ArrayList<String>();
        callArgs.add(TypeEncoder.encode(new Uint256(loadOCIndex)));
        FunctionResult functionResult = ethereumWrapper.call(abi, "ImpawnLoad",contractAddr, "queryImpawnLoanState", callArgs);
        System.out.println("----- txHash = " + functionResult.transactionHash);
        String proposalResult = ((Type) functionResult.result.get(0)).toString();
        System.out.println("proposalResult------" + proposalResult);
    }*/

    public String saveAllowedLoadOC(BigInteger loadOCIndex, String statement, String clientAccount,
                                     String bankAccount) throws Exception {
        List<String> args = new ArrayList<String>();
        args.add(TypeEncoder.encode(new Uint256(loadOCIndex)));
        args.add(TypeEncoder.encode(new Utf8String(statement)));
        args.add(TypeEncoder.encode(new Utf8String(clientAccount)));
        args.add(TypeEncoder.encode(new Utf8String(bankAccount)));

        FunctionResult functionResult = ethereumWrapper.send(abi, "ImpawnLoad",contractAddr, "makeImpawnLoan", args, false, null, true);
        System.out.println("----- txHash = " + functionResult.transactionHash);
        String loadResult = ((Type) functionResult.result.get(1)).toString();
        System.out.println("proposalResult------" + loadResult);
        return loadResult;
    }

    public String saveRefundedLoadOC(BigInteger loadOCIndex, String statement, String clientAccount,
                                   String bankAccount) throws Exception {
        List<String> args = new ArrayList<String>();
        args.add(TypeEncoder.encode(new Uint256(loadOCIndex)));
        args.add(TypeEncoder.encode(new Utf8String(statement)));
        args.add(TypeEncoder.encode(new Utf8String(clientAccount)));
        args.add(TypeEncoder.encode(new Utf8String(bankAccount)));

        FunctionResult functionResult = ethereumWrapper.send(abi, "ImpawnLoad",contractAddr, "refundImpawnLoan", args, false, null, true);
        System.out.println("----- txHash = " + functionResult.transactionHash);
        String loadResult = ((Type) functionResult.result.get(1)).toString();
        System.out.println("proposalResult------" + loadResult);
        return loadResult;
    }

    public String saveUnsignedLoadOC(BigInteger loadOCIndex, String unsignedDigest) throws Exception {
        List<String> args = new ArrayList<String>();
        args.add(TypeEncoder.encode(new Uint256(loadOCIndex)));
        args.add(TypeEncoder.encode(new Utf8String(unsignedDigest)));
        FunctionResult functionResult = ethereumWrapper.send(abi, "ImpawnLoad",contractAddr, "unsignImpawnLoan", args, false, null, true);
        System.out.println("----- txHash = " + functionResult.transactionHash);
        String loadResult = ((Type) functionResult.result.get(1)).toString();
        System.out.println("proposalResult------" + loadResult);
        return loadResult;
    }

    @Test
    public void test() throws Exception {
//        ImpawnLoanRequestOC impawnLoanRequestOC =  new ImpawnLoanRequestOC();
//        impawnLoanRequestOC.setOrderIndexOnChain(BigInteger.valueOf(12));
//        impawnLoanRequestOC.setBankAddr("0xc2582ae1deec5af85028e0a089ec87286c4f8d6e");
//        impawnLoanRequestOC.setRequestedValue(BigInteger.valueOf(12000000));
//        impawnLoanRequestOC.setClientAddr("0xc2582ae1deec5af85028e0a089ec87286c4f8d6e");
//        impawnLoanRequestOC.setClientInfoDigest("0xcfb58ff6ecf0df16c7ae9da4dc7e04e988801c4207a4d89dccbea958d9c6307c");
//        impawnLoanRequestOC.setClientOffAccount("10000000XA");
//        impawnLoanRequestOC.setBankInfoDigest("0xcfb58ff6ecf0df16c7ae9da4dc7e04e988801c4207a4d89dccbea958d9c6307c");
//        impawnLoanRequestOC.setBankOffAccount("10000000XB");
//        uploadLoadRequestTC(impawnLoanRequestOC);
//
//        saveLoadProposalOC(BigInteger.valueOf(0), "已入库", "完成", "0xcfb58ff6ecf0df16c7aa1da4dc7e04e988802e4207a4d89dccbea958d9c6307c", "yes");
//
//        uploadSignedContractOC(BigInteger.valueOf(0), "0xcfb58ff6ecf0df16c7aa1da4dc7e04e988802e4207a4d89dccbea958d9c6323c", "0xcfb58ff6ecf0df16c7aa1da4dc7e04e988802e4207a4d89dccbea958d9c62a31", "0xcfb58ff6ecf0df16c7aa1da4dc7e04e988802e4207a4d89dccbea958d9c645de", "0xcfb58ff6ecf0df16c7aa1da4dc7e04e988802e4207a4d89dccbea958d9c61234");

//          saveRefundedLoadOC(BigInteger.valueOf(0),"A to B", "10000000XA", "10000000XB");
          saveUnsignedLoadOC(BigInteger.valueOf(0), "0xcfb58ff6ecf0df16c7aa1da4dc7e04e988802e4207a4d89dccbea958d9c6307c");
    }
}
