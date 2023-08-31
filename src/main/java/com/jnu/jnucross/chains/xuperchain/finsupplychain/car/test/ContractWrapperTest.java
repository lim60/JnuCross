package com.jnu.jnucross.chains.xuperchain.finsupplychain.car.test;

import com.jnu.jnucross.chains.xuperchain.finsupplychain.car.common.CustomDeclareState;
import com.jnu.jnucross.chains.xuperchain.finsupplychain.car.contractwrapper.CustomDeclareWrapper;
import com.jnu.jnucross.chains.xuperchain.finsupplychain.car.contractwrapper.EvidenceWrapper;
import com.jnu.jnucross.chains.xuperchain.finsupplychain.car.contractwrapper.ImportOrderWrapper;
import com.jnu.jnucross.chains.xuperchain.finsupplychain.car.entity.ArrivalFormOC;
import com.jnu.jnucross.chains.xuperchain.finsupplychain.car.entity.CustomFormOC;
import com.jnu.jnucross.chains.xuperchain.finsupplychain.car.entity.ImportOrderOC;
import com.jnu.jnucross.chains.xuperchain.finsupplychain.car.entity.InboundFormOC;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;

public class ContractWrapperTest {


    @Test
    public void testCustomDeclareWrapper() throws Exception {

        /*CustomDeclareWrapper wrapper = new CustomDeclareWrapper();
        wrapper.deploy();*/

        /*CustomFormOC customFormOC = new CustomFormOC();
        customFormOC.setOrderId("100000000001");
        customFormOC.setCustomNo("01");
        customFormOC.setCustomCode("123");
        customFormOC.setCheckState("CF_N");
        customFormOC.setFormDigest("0xcfb58ff6ecf0df16c7ae9da4dc7e04e988801c4207a4d89dccbea958d9c6307c");
        customFormOC.setCreateTime("230829");
        customFormOC.setCertificateDigest("0xcfb58ad6ecf0df16c7ae9da4dc7e04e988801c4202a4d89dccbea128d9c6307c");

        BigInteger result = wrapper.uploadCustomFormTC(customFormOC);
        System.out.println("create result: " + result);

        wrapper.updateOCCustomFormState(result, CustomDeclareState.UPDATED);

        wrapper.queryOCCustomForm(result);

        wrapper.queryOCCustomFormState(result);*/


        /*----------------ImportOrderWrapper------------------------*/
        ImportOrderWrapper importOrderWrapper = new ImportOrderWrapper();
        ImportOrderOC importOrderOC = new ImportOrderOC();

        importOrderOC.setOrderId("0000001");
        importOrderOC.setCreateTime("230829");
        importOrderOC.setImportType("1");
        importOrderOC.setContractId("0000000000a");
        importOrderOC.setClientName("zhonghailong");
        importOrderOC.setLoadingPort("广州港");
        importOrderOC.setDestinationPort("南沙港");
        importOrderOC.setBusinessGroupId("02");
        importOrderOC.setTenantId("01");
        importOrderOC.setOrderDigest("0xcfb58ff6ecf0df16c7ae9da4dc7e04e988801c4207a4d89dccbea958d9c6307c");

        importOrderWrapper.createOrderOC(importOrderOC);

        importOrderWrapper.queryOrderOC(BigInteger.valueOf(0));

        importOrderWrapper.queryOrderStateOC(BigInteger.valueOf(0));

        CustomFormOC customFormOC = new CustomFormOC();
        customFormOC.setCreateTime("230829");
        customFormOC.setCustomNo("01");
        customFormOC.setCustomCode("123");
        customFormOC.setCheckState("CF_N");
        customFormOC.setCertificateDigest("0xcfb58ff6ecf0df16c7ae9da4dc7e04e988801c4207a4d89dccbea958d9c6307c");

        importOrderWrapper.createOrderCustomFormOC(BigInteger.valueOf(0), customFormOC);

        importOrderWrapper.queryOrderCustomFormOC(BigInteger.valueOf(0));


        ArrivalFormOC arrivalFormOnChain = new ArrivalFormOC();
        arrivalFormOnChain.setCreateTime("230831");
        arrivalFormOnChain.setOnPortState("已到港");
        arrivalFormOnChain.setOnPortDate("240101");
        arrivalFormOnChain.setArriveEntityDigest("0xcfb58ff6ecf0df16c7ae9da4dc7e04e988801c4207a4d89dccbea958d9c6307c");

        importOrderWrapper.createOrderArrivalOC(BigInteger.valueOf(0), arrivalFormOnChain);

        importOrderWrapper.queryOrderArrivalOC(BigInteger.valueOf(0));

        InboundFormOC inboundFormOnChain = new InboundFormOC();
        inboundFormOnChain.setCreateTime("230231");
        inboundFormOnChain.setInboundType("入库类型");
        inboundFormOnChain.setInboundState("01");
        inboundFormOnChain.setInboundEntityDigest("0xcfb58ff6ecf0df16c7ae9da4dc7e04e988801c4207a4d89dccbea958d9c6307c");
        importOrderWrapper.createOrderInboundOC(BigInteger.valueOf(0), inboundFormOnChain);

        importOrderWrapper.queryOrderInboundOC(BigInteger.valueOf(0));

        importOrderWrapper.queryInboundStateOC(BigInteger.valueOf(0));

        importOrderWrapper.updateInboundStateWhenLoaded(BigInteger.valueOf(0));

        importOrderWrapper.updateInboundStateWhenRefunded(BigInteger.valueOf(0));


        /*EvidenceWrapper*/
        /*EvidenceWrapper evidenceWrapper = new EvidenceWrapper();
        evidenceWrapper.deploy();*/

    }




}
