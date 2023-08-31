package com.finsupplychain.car.contractwrapper.xuperchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jnu.jnucross.chains.FunctionResult;
import com.jnu.jnucross.chains.xuperchain.XuperChainWrapper;
import com.finsupplychain.car.entity.CustomFormOC;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jessy-js
 * @ClassName CustomDeclareXCWrapper
 * @Date 2023/8/28 17:47
 * @Version V1.0
 * @Description
 */
public class CustomDeclareXCWrapper {

    private static String abi = "[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"orderId\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"customNo\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"customCode\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"state\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"formDigest\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"createTime\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"certificateDigest\",\"type\":\"string\"}],\"name\":\"createCustomForm\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"formIndex\",\"type\":\"uint256\"}],\"name\":\"queryCustomForm\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"formIndex\",\"type\":\"uint256\"}],\"name\":\"queryCustomState\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"stateStr\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"formIndex\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"newOrderState\",\"type\":\"string\"}],\"name\":\"updateCustomFormState\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";
    private static String bin = "608060405234801561001057600080fd5b50612114806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c8063142b0606146100515780634d31032f1461008757806375147a5e146100b75780638c614334146100e7575b600080fd5b61006b60048036038101906100669190611282565b610117565b60405161007e979695949392919061133f565b60405180910390f35b6100a1600480360381019061009c9190611282565b6104c1565b6040516100ae91906113df565b60405180910390f35b6100d160048036038101906100cc9190611536565b610af4565b6040516100de91906115ad565b60405180910390f35b61010160048036038101906100fc91906115c8565b610b8c565b60405161010e919061173d565b60405180910390f35b6060806060806060806060600061012d896104c1565b905060008060008b815260200190815260200160002090508060000181600101826002018484600401856005018660060186805461016a90611787565b80601f016020809104026020016040519081016040528092919081815260200182805461019690611787565b80156101e35780601f106101b8576101008083540402835291602001916101e3565b820191906000526020600020905b8154815290600101906020018083116101c657829003601f168201915b505050505096508580546101f690611787565b80601f016020809104026020016040519081016040528092919081815260200182805461022290611787565b801561026f5780601f106102445761010080835404028352916020019161026f565b820191906000526020600020905b81548152906001019060200180831161025257829003601f168201915b5050505050955084805461028290611787565b80601f01602080910402602001604051908101604052809291908181526020018280546102ae90611787565b80156102fb5780601f106102d0576101008083540402835291602001916102fb565b820191906000526020600020905b8154815290600101906020018083116102de57829003601f168201915b5050505050945082805461030e90611787565b80601f016020809104026020016040519081016040528092919081815260200182805461033a90611787565b80156103875780601f1061035c57610100808354040283529160200191610387565b820191906000526020600020905b81548152906001019060200180831161036a57829003601f168201915b5050505050925081805461039a90611787565b80601f01602080910402602001604051908101604052809291908181526020018280546103c690611787565b80156104135780601f106103e857610100808354040283529160200191610413565b820191906000526020600020905b8154815290600101906020018083116103f657829003601f168201915b5050505050915080805461042690611787565b80601f016020809104026020016040519081016040528092919081815260200182805461045290611787565b801561049f5780601f106104745761010080835404028352916020019161049f565b820191906000526020600020905b81548152906001019060200180831161048257829003601f168201915b5050505050905098509850985098509850985098505050919395979092949650565b6060600080600084815260200190815260200160002060030160009054906101000a900460ff1690506000600e8111156104fe576104fd6117b8565b5b81600e811115610511576105106117b8565b5b03610553576040518060400160405280600481526020017f43465f4e000000000000000000000000000000000000000000000000000000008152509150610aee565b6001600e811115610567576105666117b8565b5b81600e81111561057a576105796117b8565b5b036105bc576040518060400160405280600481526020017f43465f55000000000000000000000000000000000000000000000000000000008152509150610aed565b6002600e8111156105d0576105cf6117b8565b5b81600e8111156105e3576105e26117b8565b5b03610625576040518060400160405280600481526020017f43465f53000000000000000000000000000000000000000000000000000000008152509150610aec565b6003600e811115610639576106386117b8565b5b81600e81111561064c5761064b6117b8565b5b0361068e576040518060400160405280600581526020017f43465f50520000000000000000000000000000000000000000000000000000008152509150610aeb565b6004600e8111156106a2576106a16117b8565b5b81600e8111156106b5576106b46117b8565b5b036106f7576040518060400160405280600481526020017f43465f52000000000000000000000000000000000000000000000000000000008152509150610aea565b6005600e81111561070b5761070a6117b8565b5b81600e81111561071e5761071d6117b8565b5b03610760576040518060400160405280600581526020017f43465f53570000000000000000000000000000000000000000000000000000008152509150610ae9565b6006600e811115610774576107736117b8565b5b81600e811115610787576107866117b8565b5b036107c9576040518060400160405280600581526020017f43465f53530000000000000000000000000000000000000000000000000000008152509150610ae8565b6007600e8111156107dd576107dc6117b8565b5b81600e8111156107f0576107ef6117b8565b5b03610832576040518060400160405280600181526020017f37000000000000000000000000000000000000000000000000000000000000008152509150610ae7565b6008600e811115610846576108456117b8565b5b81600e811115610859576108586117b8565b5b0361089b576040518060400160405280600181526020017f4c000000000000000000000000000000000000000000000000000000000000008152509150610ae6565b6009600e8111156108af576108ae6117b8565b5b81600e8111156108c2576108c16117b8565b5b03610904576040518060400160405280600181526020017f4a000000000000000000000000000000000000000000000000000000000000008152509150610ae5565b600a600e811115610918576109176117b8565b5b81600e81111561092b5761092a6117b8565b5b0361096d576040518060400160405280600181526020017f4b000000000000000000000000000000000000000000000000000000000000008152509150610ae4565b600b600e811115610981576109806117b8565b5b81600e811115610994576109936117b8565b5b036109d6576040518060400160405280600181526020017f43000000000000000000000000000000000000000000000000000000000000008152509150610ae3565b600c600e8111156109ea576109e96117b8565b5b81600e8111156109fd576109fc6117b8565b5b03610a3f576040518060400160405280600181526020017f33000000000000000000000000000000000000000000000000000000000000008152509150610ae2565b600d600e811115610a5357610a526117b8565b5b81600e811115610a6657610a656117b8565b5b03610aa8576040518060400160405280600181526020017f52000000000000000000000000000000000000000000000000000000000000008152509150610ae1565b6040518060400160405280600181526020017f500000000000000000000000000000000000000000000000000000000000000081525091505b5b5b5b5b5b5b5b5b5b5b5b5b5b50919050565b60008060008085815260200190815260200160002090506000600190506000610b1c85610cd3565b90506000600e811115610b3257610b316117b8565b5b81600e811115610b4557610b446117b8565b5b03610b535760009150610b80565b808360030160006101000a81548160ff0219169083600e811115610b7a57610b796117b8565b5b02179055505b81935050505092915050565b60008060016000815480929190610ba290611816565b9190505590506000610bb387610cd3565b90506040518060e001604052808b81526020018a815260200189815260200182600e811115610be557610be46117b8565b5b8152602001878152602001868152602001858152506000808481526020019081526020016000206000820151816000019081610c219190611a0a565b506020820151816001019081610c379190611a0a565b506040820151816002019081610c4d9190611a0a565b5060608201518160030160006101000a81548160ff0219169083600e811115610c7957610c786117b8565b5b02179055506080820151816004019081610c939190611a0a565b5060a0820151816005019081610ca99190611a0a565b5060c0820151816006019081610cbf9190611a0a565b509050508192505050979650505050505050565b600080604051602001610ce590611b33565b6040516020818303038152906040528051906020012083604051602001610d0c9190611b79565b6040516020818303038152906040528051906020012003610d30576000905061122f565b604051602001610d3f90611bdc565b6040516020818303038152906040528051906020012083604051602001610d669190611b79565b6040516020818303038152906040528051906020012003610d8a576001905061122e565b604051602001610d9990611c3d565b6040516020818303038152906040528051906020012083604051602001610dc09190611b79565b6040516020818303038152906040528051906020012003610de4576002905061122d565b604051602001610df390611c9e565b6040516020818303038152906040528051906020012083604051602001610e1a9190611b79565b6040516020818303038152906040528051906020012003610e3e576003905061122c565b604051602001610e4d90611cff565b6040516020818303038152906040528051906020012083604051602001610e749190611b79565b6040516020818303038152906040528051906020012003610e98576004905061122b565b604051602001610ea790611d60565b6040516020818303038152906040528051906020012083604051602001610ece9190611b79565b6040516020818303038152906040528051906020012003610ef2576005905061122a565b604051602001610f0190611dc1565b6040516020818303038152906040528051906020012083604051602001610f289190611b79565b6040516020818303038152906040528051906020012003610f4c5760069050611229565b604051602001610f5b90611e22565b6040516020818303038152906040528051906020012083604051602001610f829190611b79565b6040516020818303038152906040528051906020012003610fa65760079050611228565b604051602001610fb590611e83565b6040516020818303038152906040528051906020012083604051602001610fdc9190611b79565b60405160208183030381529060405280519060200120036110005760089050611227565b60405160200161100f90611ee4565b60405160208183030381529060405280519060200120836040516020016110369190611b79565b604051602081830303815290604052805190602001200361105a5760099050611226565b60405160200161106990611f45565b60405160208183030381529060405280519060200120836040516020016110909190611b79565b60405160208183030381529060405280519060200120036110b457600a9050611225565b6040516020016110c390611fa6565b60405160208183030381529060405280519060200120836040516020016110ea9190611b79565b604051602081830303815290604052805190602001200361110e57600b9050611224565b60405160200161111d90612007565b60405160208183030381529060405280519060200120836040516020016111449190611b79565b604051602081830303815290604052805190602001200361116857600c9050611223565b60405160200161117790612068565b604051602081830303815290604052805190602001208360405160200161119e9190611b79565b60405160208183030381529060405280519060200120036111c257600d9050611222565b6040516020016111d1906120c9565b60405160208183030381529060405280519060200120836040516020016111f89190611b79565b604051602081830303815290604052805190602001200361121c57600e9050611221565b600090505b5b5b5b5b5b5b5b5b5b5b5b5b5b5b80915050919050565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b61125f8161124c565b811461126a57600080fd5b50565b60008135905061127c81611256565b92915050565b60006020828403121561129857611297611242565b5b60006112a68482850161126d565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b838110156112e95780820151818401526020810190506112ce565b60008484015250505050565b6000601f19601f8301169050919050565b6000611311826112af565b61131b81856112ba565b935061132b8185602086016112cb565b611334816112f5565b840191505092915050565b600060e0820190508181036000830152611359818a611306565b9050818103602083015261136d8189611306565b905081810360408301526113818188611306565b905081810360608301526113958187611306565b905081810360808301526113a98186611306565b905081810360a08301526113bd8185611306565b905081810360c08301526113d18184611306565b905098975050505050505050565b600060208201905081810360008301526113f98184611306565b905092915050565b600080fd5b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b611443826112f5565b810181811067ffffffffffffffff821117156114625761146161140b565b5b80604052505050565b6000611475611238565b9050611481828261143a565b919050565b600067ffffffffffffffff8211156114a1576114a061140b565b5b6114aa826112f5565b9050602081019050919050565b82818337600083830152505050565b60006114d96114d484611486565b61146b565b9050828152602081018484840111156114f5576114f4611406565b5b6115008482856114b7565b509392505050565b600082601f83011261151d5761151c611401565b5b813561152d8482602086016114c6565b91505092915050565b6000806040838503121561154d5761154c611242565b5b600061155b8582860161126d565b925050602083013567ffffffffffffffff81111561157c5761157b611247565b5b61158885828601611508565b9150509250929050565b60008115159050919050565b6115a781611592565b82525050565b60006020820190506115c2600083018461159e565b92915050565b600080600080600080600060e0888a0312156115e7576115e6611242565b5b600088013567ffffffffffffffff81111561160557611604611247565b5b6116118a828b01611508565b975050602088013567ffffffffffffffff81111561163257611631611247565b5b61163e8a828b01611508565b965050604088013567ffffffffffffffff81111561165f5761165e611247565b5b61166b8a828b01611508565b955050606088013567ffffffffffffffff81111561168c5761168b611247565b5b6116988a828b01611508565b945050608088013567ffffffffffffffff8111156116b9576116b8611247565b5b6116c58a828b01611508565b93505060a088013567ffffffffffffffff8111156116e6576116e5611247565b5b6116f28a828b01611508565b92505060c088013567ffffffffffffffff81111561171357611712611247565b5b61171f8a828b01611508565b91505092959891949750929550565b6117378161124c565b82525050565b6000602082019050611752600083018461172e565b92915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061179f57607f821691505b6020821081036117b2576117b1611758565b5b50919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60006118218261124c565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203611853576118526117e7565b5b600182019050919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b6000600883026118c07fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82611883565b6118ca8683611883565b95508019841693508086168417925050509392505050565b6000819050919050565b60006119076119026118fd8461124c565b6118e2565b61124c565b9050919050565b6000819050919050565b611921836118ec565b61193561192d8261190e565b848454611890565b825550505050565b600090565b61194a61193d565b611955818484611918565b505050565b5b818110156119795761196e600082611942565b60018101905061195b565b5050565b601f8211156119be5761198f8161185e565b61199884611873565b810160208510156119a7578190505b6119bb6119b385611873565b83018261195a565b50505b505050565b600082821c905092915050565b60006119e1600019846008026119c3565b1980831691505092915050565b60006119fa83836119d0565b9150826002028217905092915050565b611a13826112af565b67ffffffffffffffff811115611a2c57611a2b61140b565b5b611a368254611787565b611a4182828561197d565b600060209050601f831160018114611a745760008415611a62578287015190505b611a6c85826119ee565b865550611ad4565b601f198416611a828661185e565b60005b82811015611aaa57848901518255600182019150602085019450602081019050611a85565b86831015611ac75784890151611ac3601f8916826119d0565b8355505b6001600288020188555050505b505050505050565b600081905092915050565b7f43465f4e00000000000000000000000000000000000000000000000000000000600082015250565b6000611b1d600483611adc565b9150611b2882611ae7565b600482019050919050565b6000611b3e82611b10565b9150819050919050565b6000611b53826112af565b611b5d8185611adc565b9350611b6d8185602086016112cb565b80840191505092915050565b6000611b858284611b48565b915081905092915050565b7f43465f5500000000000000000000000000000000000000000000000000000000600082015250565b6000611bc6600483611adc565b9150611bd182611b90565b600482019050919050565b6000611be782611bb9565b9150819050919050565b7f43465f5300000000000000000000000000000000000000000000000000000000600082015250565b6000611c27600483611adc565b9150611c3282611bf1565b600482019050919050565b6000611c4882611c1a565b9150819050919050565b7f43465f5052000000000000000000000000000000000000000000000000000000600082015250565b6000611c88600583611adc565b9150611c9382611c52565b600582019050919050565b6000611ca982611c7b565b9150819050919050565b7f43465f5200000000000000000000000000000000000000000000000000000000600082015250565b6000611ce9600483611adc565b9150611cf482611cb3565b600482019050919050565b6000611d0a82611cdc565b9150819050919050565b7f43465f5357000000000000000000000000000000000000000000000000000000600082015250565b6000611d4a600583611adc565b9150611d5582611d14565b600582019050919050565b6000611d6b82611d3d565b9150819050919050565b7f43465f5353000000000000000000000000000000000000000000000000000000600082015250565b6000611dab600583611adc565b9150611db682611d75565b600582019050919050565b6000611dcc82611d9e565b9150819050919050565b7f3700000000000000000000000000000000000000000000000000000000000000600082015250565b6000611e0c600183611adc565b9150611e1782611dd6565b600182019050919050565b6000611e2d82611dff565b9150819050919050565b7f4c00000000000000000000000000000000000000000000000000000000000000600082015250565b6000611e6d600183611adc565b9150611e7882611e37565b600182019050919050565b6000611e8e82611e60565b9150819050919050565b7f4a00000000000000000000000000000000000000000000000000000000000000600082015250565b6000611ece600183611adc565b9150611ed982611e98565b600182019050919050565b6000611eef82611ec1565b9150819050919050565b7f4b00000000000000000000000000000000000000000000000000000000000000600082015250565b6000611f2f600183611adc565b9150611f3a82611ef9565b600182019050919050565b6000611f5082611f22565b9150819050919050565b7f4300000000000000000000000000000000000000000000000000000000000000600082015250565b6000611f90600183611adc565b9150611f9b82611f5a565b600182019050919050565b6000611fb182611f83565b9150819050919050565b7f3300000000000000000000000000000000000000000000000000000000000000600082015250565b6000611ff1600183611adc565b9150611ffc82611fbb565b600182019050919050565b600061201282611fe4565b9150819050919050565b7f5200000000000000000000000000000000000000000000000000000000000000600082015250565b6000612052600183611adc565b915061205d8261201c565b600182019050919050565b600061207382612045565b9150819050919050565b7f5000000000000000000000000000000000000000000000000000000000000000600082015250565b60006120b3600183611adc565b91506120be8261207d565b600182019050919050565b60006120d4826120a6565b915081905091905056fea26469706673582212204250435f24f2f36b40cf597aced100cfb4a5e3a175bd2da883438b6f37bf5f8764736f6c63430008120033";
    private static String contractName = "CustomDeclare9";
    private static XuperChainWrapper xuperChainWrapper = XuperChainWrapper.build();
    /*{contractAccount=XC1234567890123456@xuper, txHash=58fc8f6b3d04704f50b815286bc920f7c01e148ec5961c2bb968136d701fd684} 23-08-31-- 21：39*/

    public static void deploy(){
        Map<String, String> deployResult = xuperChainWrapper.deploy(bin, abi, contractName, new HashMap<String, String>());
        System.out.println(deployResult);
    }

    public static BigInteger uploadCustomFormTC(CustomFormOC customForm) throws JsonProcessingException {
        List<String> args = new ArrayList<>();
        args.add(customForm.getOrderId());
        args.add(customForm.getCustomNo());
        args.add(customForm.getCustomCode());
        args.add(customForm.getCheckState());
        args.add(customForm.getFormDigest());
        args.add(customForm.getCreateTime());
        args.add(customForm.getCertificateDigest());
        FunctionResult functionResult = xuperChainWrapper.send(abi, contractName, null, "createCustomForm", args, false, null, false);
        String returnIndex = functionResult.result.get(0).toString();
        customForm.setFormIndexOnChain(new BigInteger(returnIndex));
        System.out.println("----" + functionResult.result.get(0));
        System.out.println("----" + functionResult.transactionHash);
        return new BigInteger(returnIndex);
    }

    public static boolean updateOCCustomFormState(BigInteger onChainIndex, String newState) throws JsonProcessingException {
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(onChainIndex));
        args.add(newState);
        FunctionResult functionResult = xuperChainWrapper.send(abi, contractName, null, "updateCustomFormState", args, false, null, false);
        System.out.println("----" + functionResult.result);
        System.out.println("----" + functionResult.transactionHash);
        return true;
    }

    public static List queryOCCustomForm(BigInteger onChainIndex) throws JsonProcessingException {
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(onChainIndex));
        FunctionResult functionResult = xuperChainWrapper.send(abi, contractName, null, "queryCustomForm", args, false, null, false);
        System.out.println("----" + functionResult.result);
        System.out.println("----" + functionResult.transactionHash);
        return functionResult.result;
    }

    public static List  queryOCCustomFormState(BigInteger onChainIndex) throws JsonProcessingException {
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(onChainIndex));
        FunctionResult functionResult = xuperChainWrapper.send(abi, contractName, null, "queryCustomState", args, false, null, false);
        System.out.println("----" + functionResult.result);
        System.out.println("----" + functionResult.transactionHash);
        return functionResult.result;
    }
}
