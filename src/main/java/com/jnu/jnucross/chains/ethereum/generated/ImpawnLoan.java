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
    public static final String BINARY = "608060405234801561001057600080fd5b50612184806100206000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c8063aa1a83b71161005b578063aa1a83b714610150578063b222e29514610180578063b2237fa3146101b0578063cb890c5a146101e357610088565b8063677a555e1461008d57806370a08231146100be5780638b20f1bf146100ee57806391c377801461011f575b600080fd5b6100a760048036038101906100a291906113ef565b610214565b6040516100b5929190611538565b60405180910390f35b6100d860048036038101906100d391906115c6565b610462565b6040516100e591906115f3565b60405180910390f35b610108600480360381019061010391906113ef565b61047a565b604051610116929190611538565b60405180910390f35b6101396004803603810190610134919061160e565b61083e565b604051610147929190611538565b60405180910390f35b61016a6004803603810190610165919061160e565b610935565b60405161017791906116f9565b60405180910390f35b61019a6004803603810190610195919061171b565b610b4b565b6040516101a791906116f9565b60405180910390f35b6101ca60048036038101906101c59190611748565b610ec7565b6040516101da949392919061187d565b60405180910390f35b6101fd60048036038101906101f891906118d0565b611165565b60405161020b929190611538565b60405180910390f35b60006060600060016000888152602001908152602001600020905060008160030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905060008260020160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690506000600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000209050866040516020016102d59190611968565b60405160208183030381529060405280519060200120816001016040516020016102ff9190611a77565b604051602081830303815290604052805190602001201461032357610322611a8e565b5b6000600260008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000209050886040516020016103779190611968565b60405160208183030381529060405280519060200120816001016040516020016103a19190611a77565b60405160208183030381529060405280519060200120146103c5576103c4611a8e565b5b8985600e0190816103d69190611c54565b506103e28b6005611217565b7f2cafa1203a9f98d4e110483541bbc97520f21e3cf88efc8ff61bdd12c8a3630e8b6040516104119190611d72565b60405180910390a18a6040518060400160405280600881526020017f526566756e64656400000000000000000000000000000000000000000000000081525096509650505050505094509492505050565b60046020528060005260406000206000915090505481565b60006060600060016000888152602001908152602001600020905060008160030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905060008260020160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050600360068111156104fb576104fa611da0565b5b8360020160009054906101000a900460ff16600681111561051f5761051e611da0565b5b1461052d5761052c611a8e565b5b6000600260008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020604051806040016040529081600082018054610589906119ae565b80601f01602080910402602001604051908101604052809291908181526020018280546105b5906119ae565b80156106025780601f106105d757610100808354040283529160200191610602565b820191906000526020600020905b8154815290600101906020018083116105e557829003601f168201915b5050505050815260200160018201805461061b906119ae565b80601f0160208091040260200160405190810160405280929190818152602001828054610647906119ae565b80156106945780601f1061066957610100808354040283529160200191610694565b820191906000526020600020905b81548152906001019060200180831161067757829003601f168201915b5050505050815250509050876040516020016106b09190611968565b6040516020818303038152906040528051906020012081602001516040516020016106db9190611968565b60405160208183030381529060405280519060200120146106ff576106fe611a8e565b5b6000600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000209050876040516020016107539190611968565b604051602081830303815290604052805190602001208160010160405160200161077d9190611a77565b60405160208183030381529060405280519060200120146107a1576107a0611a8e565b5b8985600d0190816107b29190611c54565b506107be8b6004611217565b7f2cafa1203a9f98d4e110483541bbc97520f21e3cf88efc8ff61bdd12c8a3630e8b6040516107ed9190611e1b565b60405180910390a18a6040518060400160405280600681526020017f4c6f616e6564000000000000000000000000000000000000000000000000000081525096509650505050505094509492505050565b6000606060006001600089815260200190815260200160002090508681600801600001908161086d9190611c54565b50858160080160010190816108829190611c54565b50848160080160020190816108979190611c54565b50838160080160030190816108ac9190611c54565b506108b8886003611217565b7f2cafa1203a9f98d4e110483541bbc97520f21e3cf88efc8ff61bdd12c8a3630e886040516108e79190611e95565b60405180910390a1876040518060400160405280600681526020017f5369676e6564000000000000000000000000000000000000000000000000000081525092509250509550959350505050565b60606000600160008881526020019081526020016000209050858160040160000190816109629190611c54565b50848160040160010190816109779190611c54565b508381600401600201908161098c9190611c54565b50828160040160030190816109a19190611c54565b5060606040516020016109b390611f0f565b60405160208183030381529060405280519060200120846040516020016109da9190611968565b6040516020818303038152906040528051906020012003610a7257610a00886001611217565b6040518060400160405280600681526020017f416772656564000000000000000000000000000000000000000000000000000081525090507f84450bb130d0092bf6e71fac4c20699120ef78eefe657d6f261de46adae9162f604051610a6590611f70565b60405180910390a1610b3d565b604051602001610a8190611fdc565b6040516020818303038152906040528051906020012084604051602001610aa89190611968565b6040516020818303038152906040528051906020012003610b3c57610ace886002611217565b6040518060400160405280600981526020017f446973616772656564000000000000000000000000000000000000000000000081525090507f84450bb130d0092bf6e71fac4c20699120ef78eefe657d6f261de46adae9162f604051610b339061203d565b60405180910390a15b5b809250505095945050505050565b60606000600160008481526020019081526020016000209050606060006006811115610b7a57610b79611da0565b5b8260020160009054906101000a900460ff166006811115610b9e57610b9d611da0565b5b03610be0576040518060400160405280600781526020017f43726561746564000000000000000000000000000000000000000000000000008152509050610ebd565b60016006811115610bf457610bf3611da0565b5b8260020160009054906101000a900460ff166006811115610c1857610c17611da0565b5b03610c5a576040518060400160405280600681526020017f41677265656400000000000000000000000000000000000000000000000000008152509050610ebc565b60026006811115610c6e57610c6d611da0565b5b8260020160009054906101000a900460ff166006811115610c9257610c91611da0565b5b03610cd4576040518060400160405280600981526020017f44697361677265656400000000000000000000000000000000000000000000008152509050610ebb565b60036006811115610ce857610ce7611da0565b5b8260020160009054906101000a900460ff166006811115610d0c57610d0b611da0565b5b03610d4e576040518060400160405280600681526020017f5369676e656400000000000000000000000000000000000000000000000000008152509050610eba565b60046006811115610d6257610d61611da0565b5b8260020160009054906101000a900460ff166006811115610d8657610d85611da0565b5b03610dc8576040518060400160405280600681526020017f4c6f616e656400000000000000000000000000000000000000000000000000008152509050610eb9565b60056006811115610ddc57610ddb611da0565b5b8260020160009054906101000a900460ff166006811115610e0057610dff611da0565b5b03610e42576040518060400160405280600881526020017f526566756e6465640000000000000000000000000000000000000000000000008152509050610eb8565b600680811115610e5557610e54611da0565b5b8260020160009054906101000a900460ff166006811115610e7957610e78611da0565b5b03610eb7576040518060400160405280600881526020017f556e7369676e656400000000000000000000000000000000000000000000000081525090505b5b5b5b5b5b5b8092505050919050565b6000806060806000806000815480929190610ee19061208c565b9190505590508c60016000838152602001908152602001600020600001819055508b6001600083815260200190815260200160002060030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508a600160008381526020019081526020016000206001018190555060006001600083815260200190815260200160002060020160006101000a81548160ff02191690836006811115610fac57610fab611da0565b5b0217905550896001600083815260200190815260200160002060020160016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060405180604001604052808a815260200189815250600260008c73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082015181600001908161106f9190611c54565b5060208201518160010190816110859190611c54565b50905050604051806040016040528088815260200187815250600360008e73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000190816110f29190611c54565b5060208201518160010190816111089190611c54565b509050507f3f310b9eeec8c136743f6820027d2fcdcc0da968127acfa23f991124b04922bd818b8b8b604051611141949392919061187d565b60405180910390a1808a8a8a94509450945094505098509850985098945050505050565b6000606060006001600086815260200190815260200160002090508381600c0190816111919190611c54565b5061119d856006611217565b7f2cafa1203a9f98d4e110483541bbc97520f21e3cf88efc8ff61bdd12c8a3630e856040516111cc9190612120565b60405180910390a1846040518060400160405280600881526020017f556e7369676e656400000000000000000000000000000000000000000000000081525092509250509250929050565b6000600160008481526020019081526020016000209050818160020160006101000a81548160ff0219169083600681111561125557611254611da0565b5b0217905550505050565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b61128681611273565b811461129157600080fd5b50565b6000813590506112a38161127d565b92915050565b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6112fc826112b3565b810181811067ffffffffffffffff8211171561131b5761131a6112c4565b5b80604052505050565b600061132e61125f565b905061133a82826112f3565b919050565b600067ffffffffffffffff82111561135a576113596112c4565b5b611363826112b3565b9050602081019050919050565b82818337600083830152505050565b600061139261138d8461133f565b611324565b9050828152602081018484840111156113ae576113ad6112ae565b5b6113b9848285611370565b509392505050565b600082601f8301126113d6576113d56112a9565b5b81356113e684826020860161137f565b91505092915050565b6000806000806080858703121561140957611408611269565b5b600061141787828801611294565b945050602085013567ffffffffffffffff8111156114385761143761126e565b5b611444878288016113c1565b935050604085013567ffffffffffffffff8111156114655761146461126e565b5b611471878288016113c1565b925050606085013567ffffffffffffffff8111156114925761149161126e565b5b61149e878288016113c1565b91505092959194509250565b6114b381611273565b82525050565b600081519050919050565b600082825260208201905092915050565b60005b838110156114f35780820151818401526020810190506114d8565b60008484015250505050565b600061150a826114b9565b61151481856114c4565b93506115248185602086016114d5565b61152d816112b3565b840191505092915050565b600060408201905061154d60008301856114aa565b818103602083015261155f81846114ff565b90509392505050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061159382611568565b9050919050565b6115a381611588565b81146115ae57600080fd5b50565b6000813590506115c08161159a565b92915050565b6000602082840312156115dc576115db611269565b5b60006115ea848285016115b1565b91505092915050565b600060208201905061160860008301846114aa565b92915050565b600080600080600060a0868803121561162a57611629611269565b5b600061163888828901611294565b955050602086013567ffffffffffffffff8111156116595761165861126e565b5b611665888289016113c1565b945050604086013567ffffffffffffffff8111156116865761168561126e565b5b611692888289016113c1565b935050606086013567ffffffffffffffff8111156116b3576116b261126e565b5b6116bf888289016113c1565b925050608086013567ffffffffffffffff8111156116e0576116df61126e565b5b6116ec888289016113c1565b9150509295509295909350565b6000602082019050818103600083015261171381846114ff565b905092915050565b60006020828403121561173157611730611269565b5b600061173f84828501611294565b91505092915050565b600080600080600080600080610100898b03121561176957611768611269565b5b60006117778b828c01611294565b98505060206117888b828c016115b1565b97505060406117998b828c01611294565b96505060606117aa8b828c016115b1565b955050608089013567ffffffffffffffff8111156117cb576117ca61126e565b5b6117d78b828c016113c1565b94505060a089013567ffffffffffffffff8111156117f8576117f761126e565b5b6118048b828c016113c1565b93505060c089013567ffffffffffffffff8111156118255761182461126e565b5b6118318b828c016113c1565b92505060e089013567ffffffffffffffff8111156118525761185161126e565b5b61185e8b828c016113c1565b9150509295985092959890939650565b61187781611588565b82525050565b600060808201905061189260008301876114aa565b61189f602083018661186e565b81810360408301526118b181856114ff565b905081810360608301526118c581846114ff565b905095945050505050565b600080604083850312156118e7576118e6611269565b5b60006118f585828601611294565b925050602083013567ffffffffffffffff8111156119165761191561126e565b5b611922858286016113c1565b9150509250929050565b600081905092915050565b6000611942826114b9565b61194c818561192c565b935061195c8185602086016114d5565b80840191505092915050565b60006119748284611937565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b600060028204905060018216806119c657607f821691505b6020821081036119d9576119d861197f565b5b50919050565b60008190508160005260206000209050919050565b60008154611a01816119ae565b611a0b818661192c565b94506001821660008114611a265760018114611a3b57611a6e565b60ff1983168652811515820286019350611a6e565b611a44856119df565b60005b83811015611a6657815481890152600182019150602081019050611a47565b838801955050505b50505092915050565b6000611a8382846119f4565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052600160045260246000fd5b60006020601f8301049050919050565b600082821b905092915050565b600060088302611b0a7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82611acd565b611b148683611acd565b95508019841693508086168417925050509392505050565b6000819050919050565b6000611b51611b4c611b4784611273565b611b2c565b611273565b9050919050565b6000819050919050565b611b6b83611b36565b611b7f611b7782611b58565b848454611ada565b825550505050565b600090565b611b94611b87565b611b9f818484611b62565b505050565b5b81811015611bc357611bb8600082611b8c565b600181019050611ba5565b5050565b601f821115611c0857611bd9816119df565b611be284611abd565b81016020851015611bf1578190505b611c05611bfd85611abd565b830182611ba4565b50505b505050565b600082821c905092915050565b6000611c2b60001984600802611c0d565b1980831691505092915050565b6000611c448383611c1a565b9150826002028217905092915050565b611c5d826114b9565b67ffffffffffffffff811115611c7657611c756112c4565b5b611c8082546119ae565b611c8b828285611bc7565b600060209050601f831160018114611cbe5760008415611cac578287015190505b611cb68582611c38565b865550611d1e565b601f198416611ccc866119df565b60005b82811015611cf457848901518255600182019150602085019450602081019050611ccf565b86831015611d115784890151611d0d601f891682611c1a565b8355505b6001600288020188555050505b505050505050565b7f526566756e646564000000000000000000000000000000000000000000000000600082015250565b6000611d5c6008836114c4565b9150611d6782611d26565b602082019050919050565b6000604082019050611d8760008301846114aa565b8181036020830152611d9881611d4f565b905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b7f4c6f616e65640000000000000000000000000000000000000000000000000000600082015250565b6000611e056006836114c4565b9150611e1082611dcf565b602082019050919050565b6000604082019050611e3060008301846114aa565b8181036020830152611e4181611df8565b905092915050565b7f5369676e65640000000000000000000000000000000000000000000000000000600082015250565b6000611e7f6006836114c4565b9150611e8a82611e49565b602082019050919050565b6000604082019050611eaa60008301846114aa565b8181036020830152611ebb81611e72565b905092915050565b7f7965730000000000000000000000000000000000000000000000000000000000600082015250565b6000611ef960038361192c565b9150611f0482611ec3565b600382019050919050565b6000611f1a82611eec565b9150819050919050565b7f4167726565640000000000000000000000000000000000000000000000000000600082015250565b6000611f5a6006836114c4565b9150611f6582611f24565b602082019050919050565b60006020820190508181036000830152611f8981611f4d565b9050919050565b7f6e6f000000000000000000000000000000000000000000000000000000000000600082015250565b6000611fc660028361192c565b9150611fd182611f90565b600282019050919050565b6000611fe782611fb9565b9150819050919050565b7f4469736167726565640000000000000000000000000000000000000000000000600082015250565b60006120276009836114c4565b915061203282611ff1565b602082019050919050565b600060208201905081810360008301526120568161201a565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061209782611273565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82036120c9576120c861205d565b5b600182019050919050565b7f556e7369676e6564000000000000000000000000000000000000000000000000600082015250565b600061210a6008836114c4565b9150612115826120d4565b602082019050919050565b600060408201905061213560008301846114aa565b8181036020830152612146816120fd565b90509291505056fea2646970667358221220fb1205c2f8c784013db8dca3feac2dd1b516bc7b430fab83da34f27b64ca779364736f6c63430008120033";

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

    @Deprecated
    public static RemoteCall<ImpawnLoan> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ImpawnLoan.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ImpawnLoan> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ImpawnLoan.class, web3j, transactionManager, contractGasProvider, BINARY, "");
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
