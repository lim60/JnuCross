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
    public static final String BINARY = "608060405234801561001057600080fd5b506120d3806100206000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c8063aa1a83b71161005b578063aa1a83b714610150578063b222e29514610180578063b2237fa3146101b0578063cb890c5a146101e357610088565b8063677a555e1461008d57806370a08231146100be5780638b20f1bf146100ee57806391c377801461011f575b600080fd5b6100a760048036038101906100a291906113b8565b610214565b6040516100b5929190611501565b60405180910390f35b6100d860048036038101906100d3919061158f565b61042b565b6040516100e591906115bc565b60405180910390f35b610108600480360381019061010391906113b8565b610443565b604051610116929190611501565b60405180910390f35b610139600480360381019061013491906115d7565b610807565b604051610147929190611501565b60405180910390f35b61016a600480360381019061016591906115d7565b6108fe565b60405161017791906116c2565b60405180910390f35b61019a600480360381019061019591906116e4565b610b14565b6040516101a791906116c2565b60405180910390f35b6101ca60048036038101906101c59190611711565b610e90565b6040516101da9493929190611846565b60405180910390f35b6101fd60048036038101906101f89190611899565b61112e565b60405161020b929190611501565b60405180910390f35b60006060600060016000888152602001908152602001600020905060008160030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905060008260020160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690506000600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000209050866040516020016102d59190611931565b60405160208183030381529060405280519060200120816001016040516020016102ff9190611a40565b604051602081830303815290604052805190602001201461032357610322611a57565b5b6000600260008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000209050886040516020016103779190611931565b60405160208183030381529060405280519060200120816001016040516020016103a19190611a40565b60405160208183030381529060405280519060200120146103c5576103c4611a57565b5b8985600e0190816103d69190611c1d565b506103e28b60056111e0565b8a6040518060400160405280600881526020017f526566756e64656400000000000000000000000000000000000000000000000081525096509650505050505094509492505050565b60046020528060005260406000206000915090505481565b60006060600060016000888152602001908152602001600020905060008160030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905060008260020160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050600360068111156104c4576104c3611cef565b5b8360020160009054906101000a900460ff1660068111156104e8576104e7611cef565b5b146104f6576104f5611a57565b5b6000600260008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060405180604001604052908160008201805461055290611977565b80601f016020809104026020016040519081016040528092919081815260200182805461057e90611977565b80156105cb5780601f106105a0576101008083540402835291602001916105cb565b820191906000526020600020905b8154815290600101906020018083116105ae57829003601f168201915b505050505081526020016001820180546105e490611977565b80601f016020809104026020016040519081016040528092919081815260200182805461061090611977565b801561065d5780601f106106325761010080835404028352916020019161065d565b820191906000526020600020905b81548152906001019060200180831161064057829003601f168201915b5050505050815250509050876040516020016106799190611931565b6040516020818303038152906040528051906020012081602001516040516020016106a49190611931565b60405160208183030381529060405280519060200120146106c8576106c7611a57565b5b6000600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002090508760405160200161071c9190611931565b60405160208183030381529060405280519060200120816001016040516020016107469190611a40565b604051602081830303815290604052805190602001201461076a57610769611a57565b5b8985600d01908161077b9190611c1d565b506107878b60046111e0565b7f2cafa1203a9f98d4e110483541bbc97520f21e3cf88efc8ff61bdd12c8a3630e8b6040516107b69190611d6a565b60405180910390a18a6040518060400160405280600681526020017f4c6f616e6564000000000000000000000000000000000000000000000000000081525096509650505050505094509492505050565b600060606000600160008981526020019081526020016000209050868160080160000190816108369190611c1d565b508581600801600101908161084b9190611c1d565b50848160080160020190816108609190611c1d565b50838160080160030190816108759190611c1d565b506108818860036111e0565b7f2cafa1203a9f98d4e110483541bbc97520f21e3cf88efc8ff61bdd12c8a3630e886040516108b09190611de4565b60405180910390a1876040518060400160405280600681526020017f5369676e6564000000000000000000000000000000000000000000000000000081525092509250509550959350505050565b606060006001600088815260200190815260200160002090508581600401600001908161092b9190611c1d565b50848160040160010190816109409190611c1d565b50838160040160020190816109559190611c1d565b508281600401600301908161096a9190611c1d565b50606060405160200161097c90611e5e565b60405160208183030381529060405280519060200120846040516020016109a39190611931565b6040516020818303038152906040528051906020012003610a3b576109c98860016111e0565b6040518060400160405280600681526020017f416772656564000000000000000000000000000000000000000000000000000081525090507f84450bb130d0092bf6e71fac4c20699120ef78eefe657d6f261de46adae9162f604051610a2e90611ebf565b60405180910390a1610b06565b604051602001610a4a90611f2b565b6040516020818303038152906040528051906020012084604051602001610a719190611931565b6040516020818303038152906040528051906020012003610b0557610a978860026111e0565b6040518060400160405280600981526020017f446973616772656564000000000000000000000000000000000000000000000081525090507f84450bb130d0092bf6e71fac4c20699120ef78eefe657d6f261de46adae9162f604051610afc90611f8c565b60405180910390a15b5b809250505095945050505050565b60606000600160008481526020019081526020016000209050606060006006811115610b4357610b42611cef565b5b8260020160009054906101000a900460ff166006811115610b6757610b66611cef565b5b03610ba9576040518060400160405280600781526020017f43726561746564000000000000000000000000000000000000000000000000008152509050610e86565b60016006811115610bbd57610bbc611cef565b5b8260020160009054906101000a900460ff166006811115610be157610be0611cef565b5b03610c23576040518060400160405280600681526020017f41677265656400000000000000000000000000000000000000000000000000008152509050610e85565b60026006811115610c3757610c36611cef565b5b8260020160009054906101000a900460ff166006811115610c5b57610c5a611cef565b5b03610c9d576040518060400160405280600981526020017f44697361677265656400000000000000000000000000000000000000000000008152509050610e84565b60036006811115610cb157610cb0611cef565b5b8260020160009054906101000a900460ff166006811115610cd557610cd4611cef565b5b03610d17576040518060400160405280600681526020017f5369676e656400000000000000000000000000000000000000000000000000008152509050610e83565b60046006811115610d2b57610d2a611cef565b5b8260020160009054906101000a900460ff166006811115610d4f57610d4e611cef565b5b03610d91576040518060400160405280600681526020017f4c6f616e656400000000000000000000000000000000000000000000000000008152509050610e82565b60056006811115610da557610da4611cef565b5b8260020160009054906101000a900460ff166006811115610dc957610dc8611cef565b5b03610e0b576040518060400160405280600881526020017f526566756e6465640000000000000000000000000000000000000000000000008152509050610e81565b600680811115610e1e57610e1d611cef565b5b8260020160009054906101000a900460ff166006811115610e4257610e41611cef565b5b03610e80576040518060400160405280600881526020017f556e7369676e656400000000000000000000000000000000000000000000000081525090505b5b5b5b5b5b5b8092505050919050565b6000806060806000806000815480929190610eaa90611fdb565b9190505590508c60016000838152602001908152602001600020600001819055508b6001600083815260200190815260200160002060030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508a600160008381526020019081526020016000206001018190555060006001600083815260200190815260200160002060020160006101000a81548160ff02191690836006811115610f7557610f74611cef565b5b0217905550896001600083815260200190815260200160002060020160016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060405180604001604052808a815260200189815250600260008c73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000190816110389190611c1d565b50602082015181600101908161104e9190611c1d565b50905050604051806040016040528088815260200187815250600360008e73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000190816110bb9190611c1d565b5060208201518160010190816110d19190611c1d565b509050507f3f310b9eeec8c136743f6820027d2fcdcc0da968127acfa23f991124b04922bd818b8b8b60405161110a9493929190611846565b60405180910390a1808a8a8a94509450945094505098509850985098945050505050565b6000606060006001600086815260200190815260200160002090508381600c01908161115a9190611c1d565b506111668560066111e0565b7f2cafa1203a9f98d4e110483541bbc97520f21e3cf88efc8ff61bdd12c8a3630e85604051611195919061206f565b60405180910390a1846040518060400160405280600881526020017f556e7369676e656400000000000000000000000000000000000000000000000081525092509250509250929050565b6000600160008481526020019081526020016000209050818160020160006101000a81548160ff0219169083600681111561121e5761121d611cef565b5b0217905550505050565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b61124f8161123c565b811461125a57600080fd5b50565b60008135905061126c81611246565b92915050565b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6112c58261127c565b810181811067ffffffffffffffff821117156112e4576112e361128d565b5b80604052505050565b60006112f7611228565b905061130382826112bc565b919050565b600067ffffffffffffffff8211156113235761132261128d565b5b61132c8261127c565b9050602081019050919050565b82818337600083830152505050565b600061135b61135684611308565b6112ed565b90508281526020810184848401111561137757611376611277565b5b611382848285611339565b509392505050565b600082601f83011261139f5761139e611272565b5b81356113af848260208601611348565b91505092915050565b600080600080608085870312156113d2576113d1611232565b5b60006113e08782880161125d565b945050602085013567ffffffffffffffff81111561140157611400611237565b5b61140d8782880161138a565b935050604085013567ffffffffffffffff81111561142e5761142d611237565b5b61143a8782880161138a565b925050606085013567ffffffffffffffff81111561145b5761145a611237565b5b6114678782880161138a565b91505092959194509250565b61147c8161123c565b82525050565b600081519050919050565b600082825260208201905092915050565b60005b838110156114bc5780820151818401526020810190506114a1565b60008484015250505050565b60006114d382611482565b6114dd818561148d565b93506114ed81856020860161149e565b6114f68161127c565b840191505092915050565b60006040820190506115166000830185611473565b818103602083015261152881846114c8565b90509392505050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061155c82611531565b9050919050565b61156c81611551565b811461157757600080fd5b50565b60008135905061158981611563565b92915050565b6000602082840312156115a5576115a4611232565b5b60006115b38482850161157a565b91505092915050565b60006020820190506115d16000830184611473565b92915050565b600080600080600060a086880312156115f3576115f2611232565b5b60006116018882890161125d565b955050602086013567ffffffffffffffff81111561162257611621611237565b5b61162e8882890161138a565b945050604086013567ffffffffffffffff81111561164f5761164e611237565b5b61165b8882890161138a565b935050606086013567ffffffffffffffff81111561167c5761167b611237565b5b6116888882890161138a565b925050608086013567ffffffffffffffff8111156116a9576116a8611237565b5b6116b58882890161138a565b9150509295509295909350565b600060208201905081810360008301526116dc81846114c8565b905092915050565b6000602082840312156116fa576116f9611232565b5b60006117088482850161125d565b91505092915050565b600080600080600080600080610100898b03121561173257611731611232565b5b60006117408b828c0161125d565b98505060206117518b828c0161157a565b97505060406117628b828c0161125d565b96505060606117738b828c0161157a565b955050608089013567ffffffffffffffff81111561179457611793611237565b5b6117a08b828c0161138a565b94505060a089013567ffffffffffffffff8111156117c1576117c0611237565b5b6117cd8b828c0161138a565b93505060c089013567ffffffffffffffff8111156117ee576117ed611237565b5b6117fa8b828c0161138a565b92505060e089013567ffffffffffffffff81111561181b5761181a611237565b5b6118278b828c0161138a565b9150509295985092959890939650565b61184081611551565b82525050565b600060808201905061185b6000830187611473565b6118686020830186611837565b818103604083015261187a81856114c8565b9050818103606083015261188e81846114c8565b905095945050505050565b600080604083850312156118b0576118af611232565b5b60006118be8582860161125d565b925050602083013567ffffffffffffffff8111156118df576118de611237565b5b6118eb8582860161138a565b9150509250929050565b600081905092915050565b600061190b82611482565b61191581856118f5565b935061192581856020860161149e565b80840191505092915050565b600061193d8284611900565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061198f57607f821691505b6020821081036119a2576119a1611948565b5b50919050565b60008190508160005260206000209050919050565b600081546119ca81611977565b6119d481866118f5565b945060018216600081146119ef5760018114611a0457611a37565b60ff1983168652811515820286019350611a37565b611a0d856119a8565b60005b83811015611a2f57815481890152600182019150602081019050611a10565b838801955050505b50505092915050565b6000611a4c82846119bd565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052600160045260246000fd5b60006020601f8301049050919050565b600082821b905092915050565b600060088302611ad37fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82611a96565b611add8683611a96565b95508019841693508086168417925050509392505050565b6000819050919050565b6000611b1a611b15611b108461123c565b611af5565b61123c565b9050919050565b6000819050919050565b611b3483611aff565b611b48611b4082611b21565b848454611aa3565b825550505050565b600090565b611b5d611b50565b611b68818484611b2b565b505050565b5b81811015611b8c57611b81600082611b55565b600181019050611b6e565b5050565b601f821115611bd157611ba2816119a8565b611bab84611a86565b81016020851015611bba578190505b611bce611bc685611a86565b830182611b6d565b50505b505050565b600082821c905092915050565b6000611bf460001984600802611bd6565b1980831691505092915050565b6000611c0d8383611be3565b9150826002028217905092915050565b611c2682611482565b67ffffffffffffffff811115611c3f57611c3e61128d565b5b611c498254611977565b611c54828285611b90565b600060209050601f831160018114611c875760008415611c75578287015190505b611c7f8582611c01565b865550611ce7565b601f198416611c95866119a8565b60005b82811015611cbd57848901518255600182019150602085019450602081019050611c98565b86831015611cda5784890151611cd6601f891682611be3565b8355505b6001600288020188555050505b505050505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b7f4c6f616e65640000000000000000000000000000000000000000000000000000600082015250565b6000611d5460068361148d565b9150611d5f82611d1e565b602082019050919050565b6000604082019050611d7f6000830184611473565b8181036020830152611d9081611d47565b905092915050565b7f5369676e65640000000000000000000000000000000000000000000000000000600082015250565b6000611dce60068361148d565b9150611dd982611d98565b602082019050919050565b6000604082019050611df96000830184611473565b8181036020830152611e0a81611dc1565b905092915050565b7f7965730000000000000000000000000000000000000000000000000000000000600082015250565b6000611e486003836118f5565b9150611e5382611e12565b600382019050919050565b6000611e6982611e3b565b9150819050919050565b7f4167726565640000000000000000000000000000000000000000000000000000600082015250565b6000611ea960068361148d565b9150611eb482611e73565b602082019050919050565b60006020820190508181036000830152611ed881611e9c565b9050919050565b7f6e6f000000000000000000000000000000000000000000000000000000000000600082015250565b6000611f156002836118f5565b9150611f2082611edf565b600282019050919050565b6000611f3682611f08565b9150819050919050565b7f4469736167726565640000000000000000000000000000000000000000000000600082015250565b6000611f7660098361148d565b9150611f8182611f40565b602082019050919050565b60006020820190508181036000830152611fa581611f69565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000611fe68261123c565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff820361201857612017611fac565b5b600182019050919050565b7f556e7369676e6564000000000000000000000000000000000000000000000000600082015250565b600061205960088361148d565b915061206482612023565b602082019050919050565b60006040820190506120846000830184611473565b81810360208301526120958161204c565b90509291505056fea2646970667358221220cd83a8abd2cc24086a35a7faeb45f53bf14495d0caccca46e31c6f1dbfae358864736f6c63430008120033";

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

    public RemoteFunctionCall<String> queryImpawnLoanState(BigInteger loanIndex) {
        final Function function = new Function(FUNC_QUERYIMPAWNLOANSTATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(loanIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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
