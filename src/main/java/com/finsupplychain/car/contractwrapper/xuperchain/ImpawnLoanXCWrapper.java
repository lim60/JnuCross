package com.finsupplychain.car.contractwrapper.xuperchain;

import com.baidu.xuper.api.AddressTrans;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jnu.jnucross.chains.FunctionResult;
import com.jnu.jnucross.chains.xuperchain.XuperChainWrapper;
import com.finsupplychain.car.entity.ImpawnLoanRequestOC;
import com.finsupplychain.car.entity.LoadExaminationOC;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jessy-js
 * @ClassName ImpawnLoanXCWrapper
 * @Version V1.0
 * @Description
 */
public class ImpawnLoanXCWrapper {
    private static String abi = "[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"orderIndex\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"bankAddr\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"requestedValue\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"clientAddr\",\"type\":\"address\"},{\"internalType\":\"string\",\"name\":\"clientDigest\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"clientAccount\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"bankDigest\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"bankAccount\",\"type\":\"string\"}],\"name\":\"createImpawnLoanRequest\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"loanIndex\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"orderState\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"taxState\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"importCertificate\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"proposal\",\"type\":\"string\"}],\"name\":\"ifImpawnLoan\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"loanIndex\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"statement\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"clientAccount\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"bankAccount\",\"type\":\"string\"}],\"name\":\"makeImpawnLoan\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"loanIndex\",\"type\":\"uint256\"}],\"name\":\"queryImpawnLoanState\",\"outputs\":[{\"internalType\":\"enum ImpawnLoan.State\",\"name\":\"\",\"type\":\"uint8\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"loanIndex\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"statement\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"clientAccount\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"bankAccount\",\"type\":\"string\"}],\"name\":\"refundImpawnLoan\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"loanIndex\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"contractSignedDigest\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"creditAgreementDigest\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"noticeDigest\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"receiptDigest\",\"type\":\"string\"}],\"name\":\"signContractForImpawnLoan\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"loanIndex\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"unsignEvidenceDigest\",\"type\":\"string\"}],\"name\":\"unsignImpawnLoan\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";
    private static String bin = "608060405234801561001057600080fd5b506118e4806100206000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c8063aa1a83b71161005b578063aa1a83b714610150578063b222e29514610180578063b2237fa3146101b0578063cb890c5a146101e357610088565b8063677a555e1461008d57806370a08231146100be5780638b20f1bf146100ee57806391c377801461011f575b600080fd5b6100a760048036038101906100a29190610dac565b610214565b6040516100b5929190610ef5565b60405180910390f35b6100d860048036038101906100d39190610f83565b61042b565b6040516100e59190610fb0565b60405180910390f35b61010860048036038101906101039190610dac565b610443565b604051610116929190610ef5565b60405180910390f35b61013960048036038101906101349190610fcb565b61065a565b604051610147929190610ef5565b60405180910390f35b61016a60048036038101906101659190610fcb565b61071a565b60405161017791906110b6565b60405180910390f35b61019a600480360381019061019591906110d8565b6108c6565b6040516101a7919061117c565b60405180910390f35b6101ca60048036038101906101c59190611197565b6108f8565b6040516101da94939291906112cc565b60405180910390f35b6101fd60048036038101906101f8919061131f565b610b59565b60405161020b929190610ef5565b60405180910390f35b60006060600060016000888152602001908152602001600020905060008160030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905060008260020160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690506000600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000209050866040516020016102d591906113b7565b60405160208183030381529060405280519060200120816001016040516020016102ff91906114c6565b6040516020818303038152906040528051906020012014610323576103226114dd565b5b6000600260008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002090508860405160200161037791906113b7565b60405160208183030381529060405280519060200120816001016040516020016103a191906114c6565b60405160208183030381529060405280519060200120146103c5576103c46114dd565b5b8985600e0190816103d691906116a3565b506103e28b6005610bd4565b8a6040518060400160405280600881526020017f526566756e64656400000000000000000000000000000000000000000000000081525096509650505050505094509492505050565b60046020528060005260406000206000915090505481565b60006060600060016000888152602001908152602001600020905060008160030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905060008260020160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690506000600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002090508660405160200161050491906113b7565b604051602081830303815290604052805190602001208160010160405160200161052e91906114c6565b6040516020818303038152906040528051906020012014610552576105516114dd565b5b6000600260008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000209050886040516020016105a691906113b7565b60405160208183030381529060405280519060200120816001016040516020016105d091906114c6565b60405160208183030381529060405280519060200120146105f4576105f36114dd565b5b8985600d01908161060591906116a3565b506106118b6004610bd4565b8a6040518060400160405280600681526020017f4c6f616e6564000000000000000000000000000000000000000000000000000081525096509650505050505094509492505050565b6000606060006001600089815260200190815260200160002090508681600801600001908161068991906116a3565b508581600801600101908161069e91906116a3565b50848160080160020190816106b391906116a3565b50838160080160030190816106c891906116a3565b506106d4886003610bd4565b876040518060400160405280600681526020017f5369676e6564000000000000000000000000000000000000000000000000000081525092509250509550959350505050565b606060006001600088815260200190815260200160002090508581600401600001908161074791906116a3565b508481600401600101908161075c91906116a3565b508381600401600201908161077191906116a3565b508281600401600301908161078691906116a3565b506060604051602001610798906117c1565b60405160208183030381529060405280519060200120846040516020016107bf91906113b7565b6040516020818303038152906040528051906020012003610822576107e5886001610bd4565b6040518060400160405280600681526020017f416772656564000000000000000000000000000000000000000000000000000081525090506108b8565b60405160200161083190611822565b604051602081830303815290604052805190602001208460405160200161085891906113b7565b60405160208183030381529060405280519060200120036108b75761087e886002610bd4565b6040518060400160405280600981526020017f446973616772656564000000000000000000000000000000000000000000000081525090505b5b809250505095945050505050565b6000806001600084815260200190815260200160002090508060020160009054906101000a900460ff16915050919050565b600080606080600080600081548092919061091290611866565b9190505590508c60016000838152602001908152602001600020600001819055508b6001600083815260200190815260200160002060030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508a600160008381526020019081526020016000206001018190555060006001600083815260200190815260200160002060020160006101000a81548160ff021916908360068111156109dd576109dc611105565b5b0217905550896001600083815260200190815260200160002060020160016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060405180604001604052808a815260200189815250600260008c73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000820151816000019081610aa091906116a3565b506020820151816001019081610ab691906116a3565b50905050604051806040016040528088815260200187815250600360008e73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000820151816000019081610b2391906116a3565b506020820151816001019081610b3991906116a3565b50905050808a8a8a94509450945094505098509850985098945050505050565b6000606060006001600086815260200190815260200160002090508381600c019081610b8591906116a3565b50610b91856006610bd4565b846040518060400160405280600881526020017f556e7369676e656400000000000000000000000000000000000000000000000081525092509250509250929050565b6000600160008481526020019081526020016000209050818160020160006101000a81548160ff02191690836006811115610c1257610c11611105565b5b0217905550505050565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b610c4381610c30565b8114610c4e57600080fd5b50565b600081359050610c6081610c3a565b92915050565b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b610cb982610c70565b810181811067ffffffffffffffff82111715610cd857610cd7610c81565b5b80604052505050565b6000610ceb610c1c565b9050610cf78282610cb0565b919050565b600067ffffffffffffffff821115610d1757610d16610c81565b5b610d2082610c70565b9050602081019050919050565b82818337600083830152505050565b6000610d4f610d4a84610cfc565b610ce1565b905082815260208101848484011115610d6b57610d6a610c6b565b5b610d76848285610d2d565b509392505050565b600082601f830112610d9357610d92610c66565b5b8135610da3848260208601610d3c565b91505092915050565b60008060008060808587031215610dc657610dc5610c26565b5b6000610dd487828801610c51565b945050602085013567ffffffffffffffff811115610df557610df4610c2b565b5b610e0187828801610d7e565b935050604085013567ffffffffffffffff811115610e2257610e21610c2b565b5b610e2e87828801610d7e565b925050606085013567ffffffffffffffff811115610e4f57610e4e610c2b565b5b610e5b87828801610d7e565b91505092959194509250565b610e7081610c30565b82525050565b600081519050919050565b600082825260208201905092915050565b60005b83811015610eb0578082015181840152602081019050610e95565b60008484015250505050565b6000610ec782610e76565b610ed18185610e81565b9350610ee1818560208601610e92565b610eea81610c70565b840191505092915050565b6000604082019050610f0a6000830185610e67565b8181036020830152610f1c8184610ebc565b90509392505050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000610f5082610f25565b9050919050565b610f6081610f45565b8114610f6b57600080fd5b50565b600081359050610f7d81610f57565b92915050565b600060208284031215610f9957610f98610c26565b5b6000610fa784828501610f6e565b91505092915050565b6000602082019050610fc56000830184610e67565b92915050565b600080600080600060a08688031215610fe757610fe6610c26565b5b6000610ff588828901610c51565b955050602086013567ffffffffffffffff81111561101657611015610c2b565b5b61102288828901610d7e565b945050604086013567ffffffffffffffff81111561104357611042610c2b565b5b61104f88828901610d7e565b935050606086013567ffffffffffffffff8111156110705761106f610c2b565b5b61107c88828901610d7e565b925050608086013567ffffffffffffffff81111561109d5761109c610c2b565b5b6110a988828901610d7e565b9150509295509295909350565b600060208201905081810360008301526110d08184610ebc565b905092915050565b6000602082840312156110ee576110ed610c26565b5b60006110fc84828501610c51565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b6007811061114557611144611105565b5b50565b600081905061115682611134565b919050565b600061116682611148565b9050919050565b6111768161115b565b82525050565b6000602082019050611191600083018461116d565b92915050565b600080600080600080600080610100898b0312156111b8576111b7610c26565b5b60006111c68b828c01610c51565b98505060206111d78b828c01610f6e565b97505060406111e88b828c01610c51565b96505060606111f98b828c01610f6e565b955050608089013567ffffffffffffffff81111561121a57611219610c2b565b5b6112268b828c01610d7e565b94505060a089013567ffffffffffffffff81111561124757611246610c2b565b5b6112538b828c01610d7e565b93505060c089013567ffffffffffffffff81111561127457611273610c2b565b5b6112808b828c01610d7e565b92505060e089013567ffffffffffffffff8111156112a1576112a0610c2b565b5b6112ad8b828c01610d7e565b9150509295985092959890939650565b6112c681610f45565b82525050565b60006080820190506112e16000830187610e67565b6112ee60208301866112bd565b81810360408301526113008185610ebc565b905081810360608301526113148184610ebc565b905095945050505050565b6000806040838503121561133657611335610c26565b5b600061134485828601610c51565b925050602083013567ffffffffffffffff81111561136557611364610c2b565b5b61137185828601610d7e565b9150509250929050565b600081905092915050565b600061139182610e76565b61139b818561137b565b93506113ab818560208601610e92565b80840191505092915050565b60006113c38284611386565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061141557607f821691505b602082108103611428576114276113ce565b5b50919050565b60008190508160005260206000209050919050565b60008154611450816113fd565b61145a818661137b565b94506001821660008114611475576001811461148a576114bd565b60ff19831686528115158202860193506114bd565b6114938561142e565b60005b838110156114b557815481890152600182019150602081019050611496565b838801955050505b50505092915050565b60006114d28284611443565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052600160045260246000fd5b60006020601f8301049050919050565b600082821b905092915050565b6000600883026115597fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8261151c565b611563868361151c565b95508019841693508086168417925050509392505050565b6000819050919050565b60006115a061159b61159684610c30565b61157b565b610c30565b9050919050565b6000819050919050565b6115ba83611585565b6115ce6115c6826115a7565b848454611529565b825550505050565b600090565b6115e36115d6565b6115ee8184846115b1565b505050565b5b81811015611612576116076000826115db565b6001810190506115f4565b5050565b601f821115611657576116288161142e565b6116318461150c565b81016020851015611640578190505b61165461164c8561150c565b8301826115f3565b50505b505050565b600082821c905092915050565b600061167a6000198460080261165c565b1980831691505092915050565b60006116938383611669565b9150826002028217905092915050565b6116ac82610e76565b67ffffffffffffffff8111156116c5576116c4610c81565b5b6116cf82546113fd565b6116da828285611616565b600060209050601f83116001811461170d57600084156116fb578287015190505b6117058582611687565b86555061176d565b601f19841661171b8661142e565b60005b828110156117435784890151825560018201915060208501945060208101905061171e565b86831015611760578489015161175c601f891682611669565b8355505b6001600288020188555050505b505050505050565b7f7965730000000000000000000000000000000000000000000000000000000000600082015250565b60006117ab60038361137b565b91506117b682611775565b600382019050919050565b60006117cc8261179e565b9150819050919050565b7f6e6f000000000000000000000000000000000000000000000000000000000000600082015250565b600061180c60028361137b565b9150611817826117d6565b600282019050919050565b600061182d826117ff565b9150819050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061187182610c30565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82036118a3576118a2611837565b5b60018201905091905056fea26469706673582212201fb013a6ae3f20cef27dac55836a65a91953b30f0a3b733c77b7a9d9eb36716164736f6c63430008120033";
    private static String contractName = "ImpawnLoan6";
    private static XuperChainWrapper xuperChainWrapper = XuperChainWrapper.build();
    /*{contractAccount=XC1234567890123456@xuper, txHash=91272168d52d2c73c2f3e47c68b791be01acba0fcbbd2408c0230e11d38c759f}*/


    @Test
    public void deploy(){
        Map<String, String> deployResult = xuperChainWrapper.deploy(bin, abi, contractName, new HashMap<String, String>());
        System.out.println(deployResult);
    }


    public FunctionResult createImpawnLoanRequestOC(ImpawnLoanRequestOC impawnLoanRequestOC) throws JsonProcessingException {
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(impawnLoanRequestOC.getOrderIndexOnChain()));
        args.add(AddressTrans.xChainToEvmAddress(impawnLoanRequestOC.getBankAddr()).getAddr());
        args.add(String.valueOf(impawnLoanRequestOC.getRequestedValue()));
        args.add(AddressTrans.xChainToEvmAddress(impawnLoanRequestOC.getClientAddr()).getAddr());
        args.add(impawnLoanRequestOC.getClientInfoDigest());
        args.add(impawnLoanRequestOC.getClientOffAccount());
        args.add(impawnLoanRequestOC.getBankInfoDigest());
        args.add(impawnLoanRequestOC.getBankOffAccount());

        FunctionResult functionResult = xuperChainWrapper.send(abi, contractName, null, "createImpawnLoanRequest", args, false, null, false);
        System.out.println("----" + functionResult.result);
        System.out.println("----" + functionResult.transactionHash);
        return functionResult;
    }

    /*
       金融机构审批客户资质
       @param LoadExaminationOC， 包括 orderState订单状态，
       taxState完税证明状态，
       importCertificate进口证明书摘要，
       proposal 金融机构审查意见，'yes' or 'no'

       @return FunctionResult，其中
       result包括1个值，金融机构审查通过"Agreed"， 或者 审查不通过"Disagreed"
       * */
    public FunctionResult examineImpawnLoanOC(BigInteger loadIndexOC, LoadExaminationOC loadExaminationOC) throws JsonProcessingException {
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(loadIndexOC));
        args.add(loadExaminationOC.getOrderState());
        args.add(loadExaminationOC.getTaxState());
        args.add(loadExaminationOC.getImportCertificate());
        args.add(loadExaminationOC.getProposal());
        FunctionResult functionResult = xuperChainWrapper.send(abi, contractName, null, "ifImpawnLoan", args, false, null, false);
        System.out.println("----" + functionResult.result);
        System.out.println("----" + functionResult.transactionHash);
        return functionResult;

    }

    /*
    * 金融机构审查资质后，签订融资合同书，触发贷款申请状态为Signed
    @param ImpawnLoanRequestOC，包括contractSignedDigest仓单质押合同，
    creditAgreementDigest仓单质押授信监管三方协议，
    noticeDigest出质通知书，
    receiptDigest出质通知书回执
    *
    *
    @return FunctionResult，其中
    result包括[贷款请求链上索引，状态"Signed"]
    * */
    public FunctionResult signImpawnLoanOC(BigInteger loadIndexOC, ImpawnLoanRequestOC impawnLoanRequestOC) throws JsonProcessingException {
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(loadIndexOC));
        args.add(impawnLoanRequestOC.getContractSignedDigest());
        args.add(impawnLoanRequestOC.getCreditAgreementDigest());
        args.add(impawnLoanRequestOC.getNoticeDigest());
        args.add(impawnLoanRequestOC.getReceiptDigest());
        FunctionResult functionResult = xuperChainWrapper.send(abi, contractName, null, "signContractForImpawnLoan", args, false, null, false);
        System.out.println("----" + functionResult.result);
        System.out.println("----" + functionResult.transactionHash);
        return functionResult;
    }


    public FunctionResult queryImpawnLoanStateOC(BigInteger loadIndexOC) throws JsonProcessingException {
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(loadIndexOC));
        FunctionResult functionResult = xuperChainWrapper.send(abi, contractName, null, "queryImpawnLoanState", args, false, null, false);
        System.out.println("----" + functionResult.result);
        System.out.println("----" + functionResult.transactionHash);
        return functionResult;
    }

    /*
    金融机构放款，金融机构调用该合约，转账给指定客户端
    */

    public FunctionResult makeImpawnLoanOC(BigInteger loadIndexOC, ImpawnLoanRequestOC impawnLoanRequestOC) throws JsonProcessingException {
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(loadIndexOC));
        args.add(impawnLoanRequestOC.getBankToClientStat());
        args.add(impawnLoanRequestOC.getClientOffAccount());
        args.add(impawnLoanRequestOC.getBankOffAccount());
        FunctionResult functionResult = xuperChainWrapper.send(abi, contractName, null, "makeImpawnLoan", args, false, null, false);
        System.out.println("----" + functionResult.result);
        System.out.println("----" + functionResult.transactionHash);
        return functionResult;

    }

    /*
    客户还款，由客户调用，转账给贷款银行
    */
    public FunctionResult refundImpawnLoanOC(BigInteger loadIndexOC, ImpawnLoanRequestOC impawnLoanRequestOC) throws JsonProcessingException {
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(loadIndexOC));
        args.add(impawnLoanRequestOC.getClientToBankStat());
        args.add(impawnLoanRequestOC.getClientOffAccount());
        args.add(impawnLoanRequestOC.getBankOffAccount());
        FunctionResult functionResult = xuperChainWrapper.send(abi, contractName, null, "refundImpawnLoan", args, false, null, false);
        System.out.println("----" + functionResult.result);
        System.out.println("----" + functionResult.transactionHash);
        return functionResult;

    }

    /*解除贷款合同*/
    public FunctionResult unsignImpawnLoanOC(BigInteger loadIndexOC, ImpawnLoanRequestOC impawnLoanRequestOC) throws JsonProcessingException {
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(loadIndexOC));
        args.add(impawnLoanRequestOC.getUnsignImpawnContractDigest());
        FunctionResult functionResult = xuperChainWrapper.send(abi, contractName, null, "unsignImpawnLoan", args, false, null, false);
        System.out.println("----" + functionResult.result);
        System.out.println("----" + functionResult.transactionHash);
        return functionResult;

    }

    }
