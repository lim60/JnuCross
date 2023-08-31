package com.jnu.jnucross.chains.ethereum.generated;

import io.reactivex.Flowable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.9.8.
 */
@SuppressWarnings("rawtypes")
public class EvidenceContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50611826806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806318b0986b146100515780638169c61114610081578063fad36822146100b3578063fcdaad0f146100e5575b600080fd5b61006b60048036038101906100669190610cfc565b610116565b6040516100789190610db9565b60405180910390f35b61009b60048036038101906100969190610f10565b61059d565b6040516100aa93929190610faa565b60405180910390f35b6100cd60048036038101906100c89190610fef565b610a8a565b6040516100dc93929190610faa565b60405180910390f35b6100ff60048036038101906100fa9190610f10565b610bb7565b60405161010d92919061104b565b60405180910390f35b606080600080600085815260200190815260200160002090506001600981111561014357610142611082565b5b8160010160009054906101000a900460ff16600981111561016757610166611082565b5b036101a9576040518060400160405280601281526020017f437573746f6d43657274696669636174656400000000000000000000000000008152509150610593565b600260098111156101bd576101bc611082565b5b8160010160009054906101000a900460ff1660098111156101e1576101e0611082565b5b03610223576040518060400160405280600c81526020017f546178436f6c6c656374656400000000000000000000000000000000000000008152509150610592565b6003600981111561023757610236611082565b5b8160010160009054906101000a900460ff16600981111561025b5761025a611082565b5b0361029d576040518060400160405280600881526020017f496d706f727465640000000000000000000000000000000000000000000000008152509150610591565b600460098111156102b1576102b0611082565b5b8160010160009054906101000a900460ff1660098111156102d5576102d4611082565b5b03610317576040518060400160405280600681526020017f6f6e506f727400000000000000000000000000000000000000000000000000008152509150610590565b6005600981111561032b5761032a611082565b5b8160010160009054906101000a900460ff16600981111561034f5761034e611082565b5b03610391576040518060400160405280600a81526020017f57617265686f7573656400000000000000000000000000000000000000000000815250915061058f565b600660098111156103a5576103a4611082565b5b8160010160009054906101000a900460ff1660098111156103c9576103c8611082565b5b0361040b576040518060400160405280600681526020017f4c6f616465640000000000000000000000000000000000000000000000000000815250915061058e565b6007600981111561041f5761041e611082565b5b8160010160009054906101000a900460ff16600981111561044357610442611082565b5b03610485576040518060400160405280600681526020017f5369676e65640000000000000000000000000000000000000000000000000000815250915061058d565b6008600981111561049957610498611082565b5b8160010160009054906101000a900460ff1660098111156104bd576104bc611082565b5b036104ff576040518060400160405280600881526020017f526566756e646564000000000000000000000000000000000000000000000000815250915061058c565b60098081111561051257610511611082565b5b8160010160009054906101000a900460ff16600981111561053657610535611082565b5b03610578576040518060400160405280600a81526020017f44656c6976657269656400000000000000000000000000000000000000000000815250915061058b565b6040518060200160405280600081525091505b5b5b5b5b5b5b5b5b8192505050919050565b60006060806000806000888152602001908152602001600020905080600301859080600181540180825580915050600190039060005260206000200160009091909190915090816105ee91906112bd565b506040516020016105fe906113e6565b6040516020818303038152906040528051906020012086604051602001610625919061142c565b60405160208183030381529060405280519060200120036106725760018160010160006101000a81548160ff0219169083600981111561066857610667611082565b5b0217905550610a3c565b6040516020016106819061148f565b60405160208183030381529060405280519060200120866040516020016106a8919061142c565b60405160208183030381529060405280519060200120036106f55760028160010160006101000a81548160ff021916908360098111156106eb576106ea611082565b5b0217905550610a3b565b604051602001610704906114f0565b604051602081830303815290604052805190602001208660405160200161072b919061142c565b60405160208183030381529060405280519060200120036107785760038160010160006101000a81548160ff0219169083600981111561076e5761076d611082565b5b0217905550610a3a565b60405160200161078790611551565b60405160208183030381529060405280519060200120866040516020016107ae919061142c565b60405160208183030381529060405280519060200120036107fb5760048160010160006101000a81548160ff021916908360098111156107f1576107f0611082565b5b0217905550610a39565b60405160200161080a906115b2565b6040516020818303038152906040528051906020012086604051602001610831919061142c565b604051602081830303815290604052805190602001200361087e5760058160010160006101000a81548160ff0219169083600981111561087457610873611082565b5b0217905550610a38565b60405160200161088d90611613565b60405160208183030381529060405280519060200120866040516020016108b4919061142c565b60405160208183030381529060405280519060200120036109015760068160010160006101000a81548160ff021916908360098111156108f7576108f6611082565b5b0217905550610a37565b60405160200161091090611674565b6040516020818303038152906040528051906020012086604051602001610937919061142c565b60405160208183030381529060405280519060200120036109845760078160010160006101000a81548160ff0219169083600981111561097a57610979611082565b5b0217905550610a36565b604051602001610993906116d5565b60405160208183030381529060405280519060200120866040516020016109ba919061142c565b6040516020818303038152906040528051906020012003610a075760088160010160006101000a81548160ff021916908360098111156109fd576109fc611082565b5b0217905550610a35565b60098160010160006101000a81548160ff02191690836009811115610a2f57610a2e611082565b5b02179055505b5b5b5b5b5b5b5b7fe4da14be1e4ae43c7a253e762076df11a83efd2c3cf24dc44183f6305a39795a878787604051610a6f93929190610faa565b60405180910390a18686869350935093505093509350939050565b6000606080600060016000815480929190610aa490611719565b9190505590506000806000838152602001908152602001600020905086816000018190555060008160010160006101000a81548160ff02191690836009811115610af157610af0611082565b5b02179055508060030186908060018154018082558091505060019003906000526020600020016000909190919091509081610b2c91906112bd565b504281600201819055507fe4da14be1e4ae43c7a253e762076df11a83efd2c3cf24dc44183f6305a39795a8287604051610b679291906117ad565b60405180910390a181866040518060400160405280600781526020017f43726561746564000000000000000000000000000000000000000000000000008152509094509450945050509250925092565b606080600080600087815260200190815260200160002090508060040185908060018154018082558091505060019003906000526020600020016000909190919091509081610c0691906112bd565b508060050184908060018154018082558091505060019003906000526020600020016000909190919091509081610c3d91906112bd565b5083600286604051610c4f919061142c565b90815260200160405180910390209081610c6991906112bd565b507f09158bf9f4d68e3cd2b04fd3f9aa98bd319034e09a8c94e1982bcdd678ae86c98585604051610c9b92919061104b565b60405180910390a184849250925050935093915050565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b610cd981610cc6565b8114610ce457600080fd5b50565b600081359050610cf681610cd0565b92915050565b600060208284031215610d1257610d11610cbc565b5b6000610d2084828501610ce7565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b83811015610d63578082015181840152602081019050610d48565b60008484015250505050565b6000601f19601f8301169050919050565b6000610d8b82610d29565b610d958185610d34565b9350610da5818560208601610d45565b610dae81610d6f565b840191505092915050565b60006020820190508181036000830152610dd38184610d80565b905092915050565b600080fd5b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b610e1d82610d6f565b810181811067ffffffffffffffff82111715610e3c57610e3b610de5565b5b80604052505050565b6000610e4f610cb2565b9050610e5b8282610e14565b919050565b600067ffffffffffffffff821115610e7b57610e7a610de5565b5b610e8482610d6f565b9050602081019050919050565b82818337600083830152505050565b6000610eb3610eae84610e60565b610e45565b905082815260208101848484011115610ecf57610ece610de0565b5b610eda848285610e91565b509392505050565b600082601f830112610ef757610ef6610ddb565b5b8135610f07848260208601610ea0565b91505092915050565b600080600060608486031215610f2957610f28610cbc565b5b6000610f3786828701610ce7565b935050602084013567ffffffffffffffff811115610f5857610f57610cc1565b5b610f6486828701610ee2565b925050604084013567ffffffffffffffff811115610f8557610f84610cc1565b5b610f9186828701610ee2565b9150509250925092565b610fa481610cc6565b82525050565b6000606082019050610fbf6000830186610f9b565b8181036020830152610fd18185610d80565b90508181036040830152610fe58184610d80565b9050949350505050565b6000806040838503121561100657611005610cbc565b5b600061101485828601610ce7565b925050602083013567ffffffffffffffff81111561103557611034610cc1565b5b61104185828601610ee2565b9150509250929050565b600060408201905081810360008301526110658185610d80565b905081810360208301526110798184610d80565b90509392505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b600060028204905060018216806110f857607f821691505b60208210810361110b5761110a6110b1565b5b50919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b6000600883026111737fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82611136565b61117d8683611136565b95508019841693508086168417925050509392505050565b6000819050919050565b60006111ba6111b56111b084610cc6565b611195565b610cc6565b9050919050565b6000819050919050565b6111d48361119f565b6111e86111e0826111c1565b848454611143565b825550505050565b600090565b6111fd6111f0565b6112088184846111cb565b505050565b5b8181101561122c576112216000826111f5565b60018101905061120e565b5050565b601f8211156112715761124281611111565b61124b84611126565b8101602085101561125a578190505b61126e61126685611126565b83018261120d565b50505b505050565b600082821c905092915050565b600061129460001984600802611276565b1980831691505092915050565b60006112ad8383611283565b9150826002028217905092915050565b6112c682610d29565b67ffffffffffffffff8111156112df576112de610de5565b5b6112e982546110e0565b6112f4828285611230565b600060209050601f8311600181146113275760008415611315578287015190505b61131f85826112a1565b865550611387565b601f19841661133586611111565b60005b8281101561135d57848901518255600182019150602085019450602081019050611338565b8683101561137a5784890151611376601f891682611283565b8355505b6001600288020188555050505b505050505050565b600081905092915050565b7f437573746f6d4365727469666963617465640000000000000000000000000000600082015250565b60006113d060128361138f565b91506113db8261139a565b601282019050919050565b60006113f1826113c3565b9150819050919050565b600061140682610d29565b611410818561138f565b9350611420818560208601610d45565b80840191505092915050565b600061143882846113fb565b915081905092915050565b7f546178436f6c6c65637465640000000000000000000000000000000000000000600082015250565b6000611479600c8361138f565b915061148482611443565b600c82019050919050565b600061149a8261146c565b9150819050919050565b7f496d706f72746564000000000000000000000000000000000000000000000000600082015250565b60006114da60088361138f565b91506114e5826114a4565b600882019050919050565b60006114fb826114cd565b9150819050919050565b7f6f6e506f72740000000000000000000000000000000000000000000000000000600082015250565b600061153b60068361138f565b915061154682611505565b600682019050919050565b600061155c8261152e565b9150819050919050565b7f57617265686f7573656400000000000000000000000000000000000000000000600082015250565b600061159c600a8361138f565b91506115a782611566565b600a82019050919050565b60006115bd8261158f565b9150819050919050565b7f4c6f616465640000000000000000000000000000000000000000000000000000600082015250565b60006115fd60068361138f565b9150611608826115c7565b600682019050919050565b600061161e826115f0565b9150819050919050565b7f5369676e65640000000000000000000000000000000000000000000000000000600082015250565b600061165e60068361138f565b915061166982611628565b600682019050919050565b600061167f82611651565b9150819050919050565b7f526566756e646564000000000000000000000000000000000000000000000000600082015250565b60006116bf60088361138f565b91506116ca82611689565b600882019050919050565b60006116e0826116b2565b9150819050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061172482610cc6565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203611756576117556116ea565b5b600182019050919050565b7f4372656174656400000000000000000000000000000000000000000000000000600082015250565b6000611797600783610d34565b91506117a282611761565b602082019050919050565b60006060820190506117c26000830185610f9b565b81810360208301526117d38161178a565b905081810360408301526117e78184610d80565b9050939250505056fea2646970667358221220a9d9e56e95e89d7e62a74e2cb46cc271e1695b9a22845944eccf0d49cbe3215464736f6c63430008120033";

    public static final String FUNC_ADDSIGNATURES = "addSignatures";

    public static final String FUNC_NEWEVIDENCE = "newEvidence";

    public static final String FUNC_QUERYEVIDENCESTATE = "queryEvidenceState";

    public static final String FUNC_UPDATEEVIDENCE = "updateEvidence";

    public static final Event VALUEEVIDENCE_EVENT = new Event("ValueEvidence", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event VALUENEWEVISIGNATURE_EVENT = new Event("ValueNewEviSignature", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected EvidenceContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EvidenceContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EvidenceContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EvidenceContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ValueEvidenceEventResponse> getValueEvidenceEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VALUEEVIDENCE_EVENT, transactionReceipt);
        ArrayList<ValueEvidenceEventResponse> responses = new ArrayList<ValueEvidenceEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ValueEvidenceEventResponse typedResponse = new ValueEvidenceEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.evidenceIndex = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.state = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.evidenceDigest = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ValueEvidenceEventResponse getValueEvidenceEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VALUEEVIDENCE_EVENT, log);
        ValueEvidenceEventResponse typedResponse = new ValueEvidenceEventResponse();
        typedResponse.log = log;
        typedResponse.evidenceIndex = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.state = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.evidenceDigest = (String) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<ValueEvidenceEventResponse> valueEvidenceEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getValueEvidenceEventFromLog(log));
    }

    public Flowable<ValueEvidenceEventResponse> valueEvidenceEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VALUEEVIDENCE_EVENT));
        return valueEvidenceEventFlowable(filter);
    }

    public static List<ValueNewEviSignatureEventResponse> getValueNewEviSignatureEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VALUENEWEVISIGNATURE_EVENT, transactionReceipt);
        ArrayList<ValueNewEviSignatureEventResponse> responses = new ArrayList<ValueNewEviSignatureEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ValueNewEviSignatureEventResponse typedResponse = new ValueNewEviSignatureEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.signer = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.signature = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ValueNewEviSignatureEventResponse getValueNewEviSignatureEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VALUENEWEVISIGNATURE_EVENT, log);
        ValueNewEviSignatureEventResponse typedResponse = new ValueNewEviSignatureEventResponse();
        typedResponse.log = log;
        typedResponse.signer = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.signature = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<ValueNewEviSignatureEventResponse> valueNewEviSignatureEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getValueNewEviSignatureEventFromLog(log));
    }

    public Flowable<ValueNewEviSignatureEventResponse> valueNewEviSignatureEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VALUENEWEVISIGNATURE_EVENT));
        return valueNewEviSignatureEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addSignatures(BigInteger evidenceIndex, String publicKey, String indiviSignature) {
        final Function function = new Function(
                FUNC_ADDSIGNATURES, 
                Arrays.<Type>asList(new Uint256(evidenceIndex),
                new Utf8String(publicKey),
                new Utf8String(indiviSignature)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> newEvidence(BigInteger orderIndex, String evidenceDigest) {
        final Function function = new Function(
                FUNC_NEWEVIDENCE, 
                Arrays.<Type>asList(new Uint256(orderIndex),
                new Utf8String(evidenceDigest)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> queryEvidenceState(BigInteger evidenceIndex) {
        final Function function = new Function(FUNC_QUERYEVIDENCESTATE, 
                Arrays.<Type>asList(new Uint256(evidenceIndex)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> updateEvidence(BigInteger evidenceIndex, String newState, String evidenceDigest) {
        final Function function = new Function(
                FUNC_UPDATEEVIDENCE, 
                Arrays.<Type>asList(new Uint256(evidenceIndex),
                new Utf8String(newState),
                new Utf8String(evidenceDigest)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static EvidenceContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EvidenceContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EvidenceContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EvidenceContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EvidenceContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EvidenceContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EvidenceContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EvidenceContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EvidenceContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EvidenceContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EvidenceContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EvidenceContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<EvidenceContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EvidenceContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EvidenceContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EvidenceContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ValueEvidenceEventResponse extends BaseEventResponse {
        public BigInteger evidenceIndex;

        public String state;

        public String evidenceDigest;
    }

    public static class ValueNewEviSignatureEventResponse extends BaseEventResponse {
        public String signer;

        public String signature;
    }


}
