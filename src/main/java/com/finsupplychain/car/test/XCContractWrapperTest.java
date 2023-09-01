package com.finsupplychain.car.test;

import com.finsupplychain.car.controller.XCChainDataController;
import org.junit.Test;

import java.math.BigInteger;

/**
 * @author jessy-js
 * @ClassName ContractWrapperTest
 * @Version V1.0
 * @Description
 */
public class XCContractWrapperTest {


    @Test
    public void testCustomDeclareWrapper() throws Exception {

        /*----------------CustomDeclareXCWrapper------------------------*/
        XCChainDataController controller = new XCChainDataController();
//
//        controller.customChainUpload("100000000001", "01", "123", "CF_N",
//                "0xcfb58ff6ecf0df16c7ae9da4dc7e04e988801c4207a4d89dccbea958d9c6307c", "230829",
//                "0xcfb58ad6ecf0df16c7ae9da4dc7e04e988801c4202a4d89dccbea128d9c6307c");
//
//        controller.customChainStateUpdate(BigInteger.valueOf(0), CustomDeclareState.UPDATED);
//
//        controller.customChainQuery(BigInteger.valueOf(0));
//
//        controller.customChainStateQuery(BigInteger.valueOf(0));


        /*----------------ImportOrderXCWrapper------------------------*/




//        controller.bussChainImportOrderUpload("0000001","230829","1",
//                "0000000000a","zhonghailong","广州港","南沙港",
//                "02", "01", "0xcfb58ff6ecf0df16c7ae9da4dc7e04e988801c4207a4d89dccbea958d9c6307c");


//        controller.bussChainImportOrderQuery(BigInteger.valueOf(0));
//
//          controller.bussChainImportOrderStateQuery(BigInteger.valueOf(0));
//
//        controller.bussChainCustomeFormUpload(BigInteger.valueOf(0), "230829",
//        "01", "123", "CF_N", "0xcfb58ff6ecf0df16c7ae9da4dc7e04e988801c4207a4d89dccbea958d9c6307c"
//        );
//
        controller.bussChainCustomeFormQuery(BigInteger.valueOf(0));
//
//
//        ArrivalFormOC arrivalFormOnChain = new ArrivalFormOC();
//        arrivalFormOnChain.setCreateTime("230831");
//        arrivalFormOnChain.setOnPortState("已到港");
//        arrivalFormOnChain.setOnPortDate("240101");
//        arrivalFormOnChain.setArriveEntityDigest("0xcfb58ff6ecf0df16c7ae9da4dc7e04e988801c4207a4d89dccbea958d9c6307c");
//
//        importOrderWrapper.createOrderArrivalOC(BigInteger.valueOf(0), arrivalFormOnChain);
//
//        importOrderWrapper.queryOrderArrivalOC(BigInteger.valueOf(0));
//
//        InboundFormOC inboundFormOnChain = new InboundFormOC();
//        inboundFormOnChain.setCreateTime("230231");
//        inboundFormOnChain.setInboundType("入库类型");
//        inboundFormOnChain.setInboundState("01");
//        inboundFormOnChain.setInboundEntityDigest("0xcfb58ff6ecf0df16c7ae9da4dc7e04e988801c4207a4d89dccbea958d9c6307c");
//        importOrderWrapper.createOrderInboundOC(BigInteger.valueOf(0), inboundFormOnChain);
//
//        importOrderWrapper.queryOrderInboundOC(BigInteger.valueOf(0));
//
//        importOrderWrapper.queryInboundStateOC(BigInteger.valueOf(0));
//
//        importOrderWrapper.updateInboundStateWhenLoaded(BigInteger.valueOf(0));
//
//        importOrderWrapper.updateInboundStateWhenRefunded(BigInteger.valueOf(0));


        /*----------------EvidenceXCWrapper------------------------*/
        /*EvidenceXCWrapper evidenceWrapper = new EvidenceXCWrapper();
        evidenceWrapper.newEvidenceTC(BigInteger.valueOf(0), "0xcfb58ff6ecf0df16c7ae9da4dc7e04e988801c4207a4d89dccbea958d9c6307c");

        evidenceWrapper.updateOCEvidence(BigInteger.valueOf(0), "OnPort", "0xcfb58ff6ecf0df16c7ae9da4dc7e04e988801c4207a4d89dccbea958d9c621a1");
        evidenceWrapper.queryOCEvidenceState(BigInteger.valueOf(0));
        evidenceWrapper.addSignatureOC(BigInteger.valueOf(0),
                "0x6ddc3c77d36def1064c1c1d6baefb93e80d4c1644d6c47fc9d071f1ac2784150f7d9cf5b688a7b578697f994c9efdf64cf69158555c0dc4d558ad447828f835a",
                "0x1070b038f9497358c4b9d344ae137143592ce9fb8f9497358c4b9d344ae");*/


        /*----------------ImpawnLoanXCWrapper------------------------*/

//        ImpawnLoanXCWrapper impawnLoanXCWrapper = new ImpawnLoanXCWrapper();
//
//        ImpawnLoanRequestOC impawnLoanRequestOC =  new ImpawnLoanRequestOC();
//        impawnLoanRequestOC.setOrderIndexOnChain(BigInteger.valueOf(12));
//        impawnLoanRequestOC.setBankAddr("XC1234567890123456@xuper");
//        impawnLoanRequestOC.setRequestedValue(BigInteger.valueOf(12000000));
//        impawnLoanRequestOC.setClientAddr("XC0000007890123456@xuper");
//        impawnLoanRequestOC.setClientInfoDigest("0xcfb58ff6ecf0df16c7ae9da4dc7e04e988801c4207a4d89dccbea958d9c6307c");
//        impawnLoanRequestOC.setClientOffAccount("10000000XA");
//        impawnLoanRequestOC.setBankInfoDigest("0xcfb58ff6ecf0df16c7ae9da4dc7e04e988801c4207a4d89dccbea958d9c6307c");
//        impawnLoanRequestOC.setBankOffAccount("10000000XB");
//        impawnLoanXCWrapper.createImpawnLoanRequestOC(impawnLoanRequestOC);
//
//
//        LoadExaminationOC loanExamOC = new LoadExaminationOC();
//        loanExamOC.setOrderState("已入库");
//        loanExamOC.setTaxState("完成");
//        loanExamOC.setProposal("yes");
//        loanExamOC.setImportCertificate("0xcfb58ff6ecf0df16c7aa1da4dc7e04e988802e4207a4d89dccbea958d9c6307c");
//        impawnLoanXCWrapper.examineImpawnLoanOC(BigInteger.valueOf(0), loanExamOC);
//
//        impawnLoanRequestOC.setContractSignedDigest("0xcfb58ff6ecf0df16c7aa1da4dc7e04e988802e4207a4d89dccbea958d9c6323c");
//        impawnLoanRequestOC.setCreditAgreementDigest("0xcfb58ff6ecf0df16c7aa1da4dc7e04e988802e4207a4d89dccbea958d9c62a31");
//        impawnLoanRequestOC.setNoticeDigest("0xcfb58ff6ecf0df16c7aa1da4dc7e04e988802e4207a4d89dccbea958d9c645de");
//        impawnLoanRequestOC.setReceiptDigest("0xcfb58ff6ecf0df16c7aa1da4dc7e04e988802e4207a4d89dccbea958d9c61234");
//
//        impawnLoanXCWrapper.signImpawnLoanOC(BigInteger.valueOf(0),impawnLoanRequestOC);
//
//        impawnLoanXCWrapper.queryImpawnLoanStateOC(BigInteger.valueOf(0));
//
//        impawnLoanRequestOC.setBankToClientStat("A to B");
//        impawnLoanRequestOC.setClientOffAccount("10000000XA");
//        impawnLoanRequestOC.setBankOffAccount("10000000XB");
//
//        impawnLoanXCWrapper.makeImpawnLoanOC(BigInteger.valueOf(0),impawnLoanRequestOC);
//
//        impawnLoanRequestOC.setClientToBankStat("B to A");
//        impawnLoanXCWrapper.refundImpawnLoanOC(BigInteger.valueOf(0),impawnLoanRequestOC);
//
//        impawnLoanRequestOC.setUnsignImpawnContractDigest("0xcfb58ff6ecf0df16c7aa1da4dc7e04e988802e4207a4d89dccbea958d9c6307c");
//        impawnLoanXCWrapper.unsignImpawnLoanOC(BigInteger.valueOf(0), impawnLoanRequestOC);

    }




}
