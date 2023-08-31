package com.jnu.jnucross.chains.ethereum.generated;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
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
public class ImpawnLoan extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50611c8a806100206000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c8063aa1a83b71161005b578063aa1a83b714610150578063b222e29514610180578063b2237fa3146101b0578063cb890c5a146101e357610088565b8063677a555e1461008d57806370a08231146100be5780638b20f1bf146100ee57806391c377801461011f575b600080fd5b6100a760048036038101906100a29190611000565b610214565b6040516100b5929190611149565b60405180910390f35b6100d860048036038101906100d391906111d7565b61042b565b6040516100e59190611204565b60405180910390f35b61010860048036038101906101039190611000565b610443565b604051610116929190611149565b60405180910390f35b6101396004803603810190610134919061121f565b6107d0565b604051610147929190611149565b60405180910390f35b61016a6004803603810190610165919061121f565b6108c7565b604051610177919061130a565b60405180910390f35b61019a6004803603810190610195919061132c565b610add565b6040516101a791906113d0565b60405180910390f35b6101ca60048036038101906101c591906113eb565b610b0f565b6040516101da9493929190611520565b60405180910390f35b6101fd60048036038101906101f89190611573565b610dad565b60405161020b929190611149565b60405180910390f35b60006060600060016000888152602001908152602001600020905060008160030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905060008260020160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690506000600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000209050866040516020016102d5919061160b565b60405160208183030381529060405280519060200120816001016040516020016102ff919061171a565b604051602081830303815290604052805190602001201461032357610322611731565b5b6000600260008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020905088604051602001610377919061160b565b60405160208183030381529060405280519060200120816001016040516020016103a1919061171a565b60405160208183030381529060405280519060200120146103c5576103c4611731565b5b8985600e0190816103d691906118f7565b506103e28b6005610e28565b8a6040518060400160405280600881526020017f526566756e64656400000000000000000000000000000000000000000000000081525096509650505050505094509492505050565b60046020528060005260406000206000915090505481565b60006060600060016000888152602001908152602001600020905060008160030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905060008260020160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050600360068111156104c4576104c3611359565b5b8360020160009054906101000a900460ff1660068111156104e8576104e7611359565b5b146104f6576104f5611731565b5b6000600260008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060405180604001604052908160008201805461055290611651565b80601f016020809104026020016040519081016040528092919081815260200182805461057e90611651565b80156105cb5780601f106105a0576101008083540402835291602001916105cb565b820191906000526020600020905b8154815290600101906020018083116105ae57829003601f168201915b505050505081526020016001820180546105e490611651565b80601f016020809104026020016040519081016040528092919081815260200182805461061090611651565b801561065d5780601f106106325761010080835404028352916020019161065d565b820191906000526020600020905b81548152906001019060200180831161064057829003601f168201915b505050505081525050905087604051602001610679919061160b565b6040516020818303038152906040528051906020012081602001516040516020016106a4919061160b565b60405160208183030381529060405280519060200120146106c8576106c7611731565b5b6000600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002090508760405160200161071c919061160b565b6040516020818303038152906040528051906020012081600101604051602001610746919061171a565b604051602081830303815290604052805190602001201461076a57610769611731565b5b8985600d01908161077b91906118f7565b506107878b6004610e28565b8a6040518060400160405280600681526020017f4c6f616e6564000000000000000000000000000000000000000000000000000081525096509650505050505094509492505050565b600060606000600160008981526020019081526020016000209050868160080160000190816107ff91906118f7565b508581600801600101908161081491906118f7565b508481600801600201908161082991906118f7565b508381600801600301908161083e91906118f7565b5061084a886003610e28565b7f2cafa1203a9f98d4e110483541bbc97520f21e3cf88efc8ff61bdd12c8a3630e886040516108799190611a15565b60405180910390a1876040518060400160405280600681526020017f5369676e6564000000000000000000000000000000000000000000000000000081525092509250509550959350505050565b60606000600160008881526020019081526020016000209050858160040160000190816108f491906118f7565b508481600401600101908161090991906118f7565b508381600401600201908161091e91906118f7565b508281600401600301908161093391906118f7565b50606060405160200161094590611a8f565b604051602081830303815290604052805190602001208460405160200161096c919061160b565b6040516020818303038152906040528051906020012003610a0457610992886001610e28565b6040518060400160405280600681526020017f416772656564000000000000000000000000000000000000000000000000000081525090507f84450bb130d0092bf6e71fac4c20699120ef78eefe657d6f261de46adae9162f6040516109f790611af0565b60405180910390a1610acf565b604051602001610a1390611b5c565b6040516020818303038152906040528051906020012084604051602001610a3a919061160b565b6040516020818303038152906040528051906020012003610ace57610a60886002610e28565b6040518060400160405280600981526020017f446973616772656564000000000000000000000000000000000000000000000081525090507f84450bb130d0092bf6e71fac4c20699120ef78eefe657d6f261de46adae9162f604051610ac590611bbd565b60405180910390a15b5b809250505095945050505050565b6000806001600084815260200190815260200160002090508060020160009054906101000a900460ff16915050919050565b6000806060806000806000815480929190610b2990611c0c565b9190505590508c60016000838152602001908152602001600020600001819055508b6001600083815260200190815260200160002060030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508a600160008381526020019081526020016000206001018190555060006001600083815260200190815260200160002060020160006101000a81548160ff02191690836006811115610bf457610bf3611359565b5b0217905550896001600083815260200190815260200160002060020160016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060405180604001604052808a815260200189815250600260008c73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000820151816000019081610cb791906118f7565b506020820151816001019081610ccd91906118f7565b50905050604051806040016040528088815260200187815250600360008e73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000820151816000019081610d3a91906118f7565b506020820151816001019081610d5091906118f7565b509050507f3f310b9eeec8c136743f6820027d2fcdcc0da968127acfa23f991124b04922bd818b8b8b604051610d899493929190611520565b60405180910390a1808a8a8a94509450945094505098509850985098945050505050565b6000606060006001600086815260200190815260200160002090508381600c019081610dd991906118f7565b50610de5856006610e28565b846040518060400160405280600881526020017f556e7369676e656400000000000000000000000000000000000000000000000081525092509250509250929050565b6000600160008481526020019081526020016000209050818160020160006101000a81548160ff02191690836006811115610e6657610e65611359565b5b0217905550505050565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b610e9781610e84565b8114610ea257600080fd5b50565b600081359050610eb481610e8e565b92915050565b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b610f0d82610ec4565b810181811067ffffffffffffffff82111715610f2c57610f2b610ed5565b5b80604052505050565b6000610f3f610e70565b9050610f4b8282610f04565b919050565b600067ffffffffffffffff821115610f6b57610f6a610ed5565b5b610f7482610ec4565b9050602081019050919050565b82818337600083830152505050565b6000610fa3610f9e84610f50565b610f35565b905082815260208101848484011115610fbf57610fbe610ebf565b5b610fca848285610f81565b509392505050565b600082601f830112610fe757610fe6610eba565b5b8135610ff7848260208601610f90565b91505092915050565b6000806000806080858703121561101a57611019610e7a565b5b600061102887828801610ea5565b945050602085013567ffffffffffffffff81111561104957611048610e7f565b5b61105587828801610fd2565b935050604085013567ffffffffffffffff81111561107657611075610e7f565b5b61108287828801610fd2565b925050606085013567ffffffffffffffff8111156110a3576110a2610e7f565b5b6110af87828801610fd2565b91505092959194509250565b6110c481610e84565b82525050565b600081519050919050565b600082825260208201905092915050565b60005b838110156111045780820151818401526020810190506110e9565b60008484015250505050565b600061111b826110ca565b61112581856110d5565b93506111358185602086016110e6565b61113e81610ec4565b840191505092915050565b600060408201905061115e60008301856110bb565b81810360208301526111708184611110565b90509392505050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006111a482611179565b9050919050565b6111b481611199565b81146111bf57600080fd5b50565b6000813590506111d1816111ab565b92915050565b6000602082840312156111ed576111ec610e7a565b5b60006111fb848285016111c2565b91505092915050565b600060208201905061121960008301846110bb565b92915050565b600080600080600060a0868803121561123b5761123a610e7a565b5b600061124988828901610ea5565b955050602086013567ffffffffffffffff81111561126a57611269610e7f565b5b61127688828901610fd2565b945050604086013567ffffffffffffffff81111561129757611296610e7f565b5b6112a388828901610fd2565b935050606086013567ffffffffffffffff8111156112c4576112c3610e7f565b5b6112d088828901610fd2565b925050608086013567ffffffffffffffff8111156112f1576112f0610e7f565b5b6112fd88828901610fd2565b9150509295509295909350565b600060208201905081810360008301526113248184611110565b905092915050565b60006020828403121561134257611341610e7a565b5b600061135084828501610ea5565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b6007811061139957611398611359565b5b50565b60008190506113aa82611388565b919050565b60006113ba8261139c565b9050919050565b6113ca816113af565b82525050565b60006020820190506113e560008301846113c1565b92915050565b600080600080600080600080610100898b03121561140c5761140b610e7a565b5b600061141a8b828c01610ea5565b985050602061142b8b828c016111c2565b975050604061143c8b828c01610ea5565b965050606061144d8b828c016111c2565b955050608089013567ffffffffffffffff81111561146e5761146d610e7f565b5b61147a8b828c01610fd2565b94505060a089013567ffffffffffffffff81111561149b5761149a610e7f565b5b6114a78b828c01610fd2565b93505060c089013567ffffffffffffffff8111156114c8576114c7610e7f565b5b6114d48b828c01610fd2565b92505060e089013567ffffffffffffffff8111156114f5576114f4610e7f565b5b6115018b828c01610fd2565b9150509295985092959890939650565b61151a81611199565b82525050565b600060808201905061153560008301876110bb565b6115426020830186611511565b81810360408301526115548185611110565b905081810360608301526115688184611110565b905095945050505050565b6000806040838503121561158a57611589610e7a565b5b600061159885828601610ea5565b925050602083013567ffffffffffffffff8111156115b9576115b8610e7f565b5b6115c585828601610fd2565b9150509250929050565b600081905092915050565b60006115e5826110ca565b6115ef81856115cf565b93506115ff8185602086016110e6565b80840191505092915050565b600061161782846115da565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061166957607f821691505b60208210810361167c5761167b611622565b5b50919050565b60008190508160005260206000209050919050565b600081546116a481611651565b6116ae81866115cf565b945060018216600081146116c957600181146116de57611711565b60ff1983168652811515820286019350611711565b6116e785611682565b60005b83811015611709578154818901526001820191506020810190506116ea565b838801955050505b50505092915050565b60006117268284611697565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052600160045260246000fd5b60006020601f8301049050919050565b600082821b905092915050565b6000600883026117ad7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82611770565b6117b78683611770565b95508019841693508086168417925050509392505050565b6000819050919050565b60006117f46117ef6117ea84610e84565b6117cf565b610e84565b9050919050565b6000819050919050565b61180e836117d9565b61182261181a826117fb565b84845461177d565b825550505050565b600090565b61183761182a565b611842818484611805565b505050565b5b818110156118665761185b60008261182f565b600181019050611848565b5050565b601f8211156118ab5761187c81611682565b61188584611760565b81016020851015611894578190505b6118a86118a085611760565b830182611847565b50505b505050565b600082821c905092915050565b60006118ce600019846008026118b0565b1980831691505092915050565b60006118e783836118bd565b9150826002028217905092915050565b611900826110ca565b67ffffffffffffffff81111561191957611918610ed5565b5b6119238254611651565b61192e82828561186a565b600060209050601f831160018114611961576000841561194f578287015190505b61195985826118db565b8655506119c1565b601f19841661196f86611682565b60005b8281101561199757848901518255600182019150602085019450602081019050611972565b868310156119b457848901516119b0601f8916826118bd565b8355505b6001600288020188555050505b505050505050565b7f5369676e65640000000000000000000000000000000000000000000000000000600082015250565b60006119ff6006836110d5565b9150611a0a826119c9565b602082019050919050565b6000604082019050611a2a60008301846110bb565b8181036020830152611a3b816119f2565b905092915050565b7f7965730000000000000000000000000000000000000000000000000000000000600082015250565b6000611a796003836115cf565b9150611a8482611a43565b600382019050919050565b6000611a9a82611a6c565b9150819050919050565b7f4167726565640000000000000000000000000000000000000000000000000000600082015250565b6000611ada6006836110d5565b9150611ae582611aa4565b602082019050919050565b60006020820190508181036000830152611b0981611acd565b9050919050565b7f6e6f000000000000000000000000000000000000000000000000000000000000600082015250565b6000611b466002836115cf565b9150611b5182611b10565b600282019050919050565b6000611b6782611b39565b9150819050919050565b7f4469736167726565640000000000000000000000000000000000000000000000600082015250565b6000611ba76009836110d5565b9150611bb282611b71565b602082019050919050565b60006020820190508181036000830152611bd681611b9a565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000611c1782610e84565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203611c4957611c48611bdd565b5b60018201905091905056fea264697066735822122096c73ca1239fb88cea3ad0bb55fc698a956890de9ae949574240d58c7934325c64736f6c63430008120033";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_CREATEIMPAWNLOANREQUEST = "createImpawnLoanRequest";

    public static final String FUNC_IFIMPAWNLOAN = "ifImpawnLoan";

    public static final String FUNC_MAKEIMPAWNLOAN = "makeImpawnLoan";

    public static final String FUNC_QUERYIMPAWNLOANSTATE = "queryImpawnLoanState";

    public static final String FUNC_REFUNDIMPAWNLOAN = "refundImpawnLoan";

    public static final String FUNC_SIGNCONTRACTFORIMPAWNLOAN = "signContractForImpawnLoan";

    public static final String FUNC_UNSIGNIMPAWNLOAN = "unsignImpawnLoan";

    public static final Event EXAMINE_EVENT = new Event("Examine", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event IMPAWNNOTICE_EVENT = new Event("ImpawnNotice", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event VALUEREQUEST_EVENT = new Event("ValueRequest", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected ImpawnLoan(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ImpawnLoan(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ImpawnLoan(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ImpawnLoan(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ExamineEventResponse> getExamineEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(EXAMINE_EVENT, transactionReceipt);
        ArrayList<ExamineEventResponse> responses = new ArrayList<ExamineEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ExamineEventResponse typedResponse = new ExamineEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.stateStr = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ExamineEventResponse getExamineEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(EXAMINE_EVENT, log);
        ExamineEventResponse typedResponse = new ExamineEventResponse();
        typedResponse.log = log;
        typedResponse.stateStr = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ExamineEventResponse> examineEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getExamineEventFromLog(log));
    }

    public Flowable<ExamineEventResponse> examineEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EXAMINE_EVENT));
        return examineEventFlowable(filter);
    }

    public static List<ImpawnNoticeEventResponse> getImpawnNoticeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(IMPAWNNOTICE_EVENT, transactionReceipt);
        ArrayList<ImpawnNoticeEventResponse> responses = new ArrayList<ImpawnNoticeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ImpawnNoticeEventResponse typedResponse = new ImpawnNoticeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.loanIndex = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.ifSign = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ImpawnNoticeEventResponse getImpawnNoticeEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(IMPAWNNOTICE_EVENT, log);
        ImpawnNoticeEventResponse typedResponse = new ImpawnNoticeEventResponse();
        typedResponse.log = log;
        typedResponse.loanIndex = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.ifSign = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<ImpawnNoticeEventResponse> impawnNoticeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getImpawnNoticeEventFromLog(log));
    }

    public Flowable<ImpawnNoticeEventResponse> impawnNoticeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(IMPAWNNOTICE_EVENT));
        return impawnNoticeEventFlowable(filter);
    }

    public static List<ValueRequestEventResponse> getValueRequestEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VALUEREQUEST_EVENT, transactionReceipt);
        ArrayList<ValueRequestEventResponse> responses = new ArrayList<ValueRequestEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ValueRequestEventResponse typedResponse = new ValueRequestEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.loanIndex = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.clientAddr = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.clientDigest = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.clientAccount = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ValueRequestEventResponse getValueRequestEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VALUEREQUEST_EVENT, log);
        ValueRequestEventResponse typedResponse = new ValueRequestEventResponse();
        typedResponse.log = log;
        typedResponse.loanIndex = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.clientAddr = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.clientDigest = (String) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.clientAccount = (String) eventValues.getNonIndexedValues().get(3).getValue();
        return typedResponse;
    }

    public Flowable<ValueRequestEventResponse> valueRequestEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getValueRequestEventFromLog(log));
    }

    public Flowable<ValueRequestEventResponse> valueRequestEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VALUEREQUEST_EVENT));
        return valueRequestEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String param0) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> createImpawnLoanRequest(BigInteger orderIndex, String bankAddr, BigInteger requestedValue, String clientAddr, String clientDigest, String clientAccount, String bankDigest, String bankAccount) {
        final Function function = new Function(
                FUNC_CREATEIMPAWNLOANREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(orderIndex), 
                new org.web3j.abi.datatypes.Address(160, bankAddr), 
                new org.web3j.abi.datatypes.generated.Uint256(requestedValue), 
                new org.web3j.abi.datatypes.Address(160, clientAddr), 
                new org.web3j.abi.datatypes.Utf8String(clientDigest), 
                new org.web3j.abi.datatypes.Utf8String(clientAccount), 
                new org.web3j.abi.datatypes.Utf8String(bankDigest), 
                new org.web3j.abi.datatypes.Utf8String(bankAccount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> ifImpawnLoan(BigInteger loanIndex, String orderState, String taxState, String importCertificate, String proposal) {
        final Function function = new Function(
                FUNC_IFIMPAWNLOAN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(loanIndex), 
                new org.web3j.abi.datatypes.Utf8String(orderState), 
                new org.web3j.abi.datatypes.Utf8String(taxState), 
                new org.web3j.abi.datatypes.Utf8String(importCertificate), 
                new org.web3j.abi.datatypes.Utf8String(proposal)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> makeImpawnLoan(BigInteger loanIndex, String statement, String clientAccount, String bankAccount) {
        final Function function = new Function(
                FUNC_MAKEIMPAWNLOAN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(loanIndex), 
                new org.web3j.abi.datatypes.Utf8String(statement), 
                new org.web3j.abi.datatypes.Utf8String(clientAccount), 
                new org.web3j.abi.datatypes.Utf8String(bankAccount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> queryImpawnLoanState(BigInteger loanIndex) {
        final Function function = new Function(FUNC_QUERYIMPAWNLOANSTATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(loanIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> refundImpawnLoan(BigInteger loanIndex, String statement, String clientAccount, String bankAccount) {
        final Function function = new Function(
                FUNC_REFUNDIMPAWNLOAN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(loanIndex), 
                new org.web3j.abi.datatypes.Utf8String(statement), 
                new org.web3j.abi.datatypes.Utf8String(clientAccount), 
                new org.web3j.abi.datatypes.Utf8String(bankAccount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> signContractForImpawnLoan(BigInteger loanIndex, String contractSignedDigest, String creditAgreementDigest, String noticeDigest, String receiptDigest) {
        final Function function = new Function(
                FUNC_SIGNCONTRACTFORIMPAWNLOAN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(loanIndex), 
                new org.web3j.abi.datatypes.Utf8String(contractSignedDigest), 
                new org.web3j.abi.datatypes.Utf8String(creditAgreementDigest), 
                new org.web3j.abi.datatypes.Utf8String(noticeDigest), 
                new org.web3j.abi.datatypes.Utf8String(receiptDigest)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unsignImpawnLoan(BigInteger loanIndex, String unsignEvidenceDigest) {
        final Function function = new Function(
                FUNC_UNSIGNIMPAWNLOAN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(loanIndex), 
                new org.web3j.abi.datatypes.Utf8String(unsignEvidenceDigest)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static ImpawnLoan load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ImpawnLoan(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ImpawnLoan load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ImpawnLoan(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ImpawnLoan load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ImpawnLoan(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ImpawnLoan load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ImpawnLoan(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ImpawnLoan> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ImpawnLoan.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<ImpawnLoan> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ImpawnLoan.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ImpawnLoan> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ImpawnLoan.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ImpawnLoan> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ImpawnLoan.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ExamineEventResponse extends BaseEventResponse {
        public String stateStr;
    }

    public static class ImpawnNoticeEventResponse extends BaseEventResponse {
        public BigInteger loanIndex;

        public String ifSign;
    }

    public static class ValueRequestEventResponse extends BaseEventResponse {
        public BigInteger loanIndex;

        public String clientAddr;

        public String clientDigest;

        public String clientAccount;
    }
}
