package com.finsupplychain.car.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finsupplychain.car.common.ChainResult;
import com.finsupplychain.car.contractwrapper.xuperchain.CustomDeclareXCWrapper;
import com.finsupplychain.car.contractwrapper.xuperchain.EvidenceXCWrapper;
import com.finsupplychain.car.contractwrapper.xuperchain.ImpawnLoanXCWrapper;
import com.finsupplychain.car.contractwrapper.xuperchain.ImportOrderXCWrapper;
import com.finsupplychain.car.entity.*;
import com.jnu.jnucross.chains.FunctionResult;
import com.jnu.jnucross.chains.xuperchain.XuperChainWrapper;

import java.math.BigInteger;

public class XCChainDataController {
    XuperChainWrapper xuperChainWrapper = XuperChainWrapper.build();
    CustomDeclareXCWrapper customDeclareXCWrapper = new CustomDeclareXCWrapper();
    EvidenceXCWrapper evidenceXCWrapper = new EvidenceXCWrapper();
    ImpawnLoanXCWrapper impawnLoanXCWrapper = new ImpawnLoanXCWrapper();
    ImportOrderXCWrapper importOrderXCWrapper = new ImportOrderXCWrapper();


    /*上传报关单关键信息
     * @param importOrderId 进口订单ID
     * @param customNo 报关单号
     * @param customCode 海关编码
     * @param checkState 审核状态 参见CustomDeclareState类
     * @param formDigest 报关单内容摘要
     * @param createTime 创建时间
     * @param certificateDigest 完税证明回执
     * @return chainResult
     * */
    public ChainResult customChainUpload(String importOrderId, String customNo, String customCode,
                                         String checkState, String formDigest, String createTime,
                                         String certificateDigest) {

        CustomFormOC customFormOC = new CustomFormOC();
        customFormOC.setOrderId(importOrderId);
        customFormOC.setCustomNo(customNo);
        customFormOC.setCustomCode(customCode);
        customFormOC.setCheckState(checkState);
        customFormOC.setFormDigest(formDigest);
        customFormOC.setCreateTime(createTime);
        customFormOC.setCertificateDigest(certificateDigest);
        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = customDeclareXCWrapper.uploadCustomFormTC(customFormOC);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }

    /*
     *报关单审核状态更新
     * @param onChainIndex 报关单链上索引
     * @param newState 新的审核状态 参见CustomDeclareState类
     * @return chainResult
     * */
    public ChainResult customChainStateUpdate(BigInteger onChainIndex, String newState) {

        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = customDeclareXCWrapper.updateOCCustomFormState(onChainIndex, newState);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }

    /*
     *报关单查询
     * @param onChainIndex 报关单链上索引
     * @return chainResult
     * */
    public ChainResult customChainQuery(BigInteger onChainIndex) {

        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = customDeclareXCWrapper.queryOCCustomForm(onChainIndex);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }

    /*
     *报关单审核状态查询
     * @param onChainIndex 报关单链上索引
     * @return chainResult
     * */
    public ChainResult customChainStateQuery(BigInteger onChainIndex) {

        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = customDeclareXCWrapper.queryOCCustomFormState(onChainIndex);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }

    /*
   新建存证
    * @param orderIndex 订单在链上的唯一索引，用于关联订单实体
    * @param evidenceDigest 首次创建，"进口订单"的证据

    * @return chainResult
    */
    public ChainResult evidenceChainUpload(BigInteger orderIndex, String evidenceDigest) {
        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = evidenceXCWrapper.newEvidenceTC(orderIndex, evidenceDigest);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return chainResult;
    }

    /*
    依据业务所处不同阶段，可由中海龙、客户、银行调用该函数
    * @param onChainIndex 证据索引
    * @param newState 新加入的证据状态，前端传入参数CustomCertificated，TaxCollected等
    * @param evidenceDigest 新加入的证据摘要


    * @return chainResult
    新增证据，包括 报关核验、完税、进口、到港、入库、放款、合同签订、还款、出库
    */
    public ChainResult evidenceChainUpdate(BigInteger evidenceIndex, String newState, String evidenceDigest) {
        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = evidenceXCWrapper.updateOCEvidence(evidenceIndex, newState, evidenceDigest);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return chainResult;
    }

    /*
    * 入参：链上证据唯一索引
    *
    * */
    public ChainResult evidenceChainStateQuery(BigInteger evidenceIndex) {
        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = evidenceXCWrapper.queryOCEvidenceState(evidenceIndex);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return chainResult;
    }

     /*
     TODO
   法院实体对关联evidenceIndex的当前证据添加签名
   @param onChainIndex 证据链上唯一索引
   @param pubKey 法院链上账户公钥
   @param signature 由法院用上述公钥对应的私钥签名生成

   @return FunctionResult，其中，result包括pubKey和signature
   */
    /* public ChainResult evidenceChainAddSignature(BigInteger evidenceIndex) {
     *//*String pubKey, String signature*//*
         ChainResult chainResult = new ChainResult();
         return chainResult;
     }
     */

    /*
    保存质押贷款申请
    @param importOrderChainIndex 客户的链上进口订单索引
    申请贷款的银行链上账户地址
    requestedValue 贷款金额，
    clientAddr 客户的链上账户地址，
    clientDigest 客户三证信息摘要
    clientAccount 客户链下银行账户，
    bankDigest 银行三证信息摘要
    bankAccount 银行链下银行账户

    @return ChainResult，其中
    conResult包括[ 贷款申请单索引，客户的链上账户地址，客户三证信息摘要，客户链下银行账户]
    txResult 包括[from， to, blockNumber等]
    * */
    public ChainResult financeChainUpload(BigInteger importOrderChainIndex, String bankChainAddr, Integer requestValue,
                                          String clientChainAddr, String clientInfoDigest, String clientOffAccount,
                                          String bankInfoDigest, String bankOffAccount) {

        ImpawnLoanRequestOC impawnLoanRequestOC = new ImpawnLoanRequestOC();
        impawnLoanRequestOC.setOrderIndexOnChain(importOrderChainIndex);
        impawnLoanRequestOC.setBankAddr(bankChainAddr);
        impawnLoanRequestOC.setRequestedValue(BigInteger.valueOf(requestValue));
        impawnLoanRequestOC.setClientAddr(clientChainAddr);
        impawnLoanRequestOC.setClientInfoDigest(clientInfoDigest);
        impawnLoanRequestOC.setClientOffAccount(clientOffAccount);
        impawnLoanRequestOC.setBankInfoDigest(bankInfoDigest);
        impawnLoanRequestOC.setBankOffAccount(bankOffAccount);

        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = impawnLoanXCWrapper.createImpawnLoanRequestOC(impawnLoanRequestOC);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }


    /*
    *金融链
       金融机构审批客户资质
       @param LoadExaminationOC， 包括 orderState订单状态，
       taxState完税证明状态，
       importCertificate进口证明书摘要，
       proposal 金融机构审查意见，'yes' or 'no'
    *
    * */
    public ChainResult financeChainExamine(BigInteger loanOnChainIndex, String orderState, String taxState,
                                           String importCertificate, String proposal) {
        LoadExaminationOC loadExaminationOC = new LoadExaminationOC();
        loadExaminationOC.setOrderState(orderState);
        loadExaminationOC.setTaxState(taxState);
        loadExaminationOC.setImportCertificate(importCertificate);
        loadExaminationOC.setProposal(proposal);

        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = impawnLoanXCWrapper.examineImpawnLoanOC(loanOnChainIndex, loadExaminationOC);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }

    /*
    * 金融机构审查资质后，签订融资合同书，触发贷款申请状态为Signed
    @param ImpawnLoanRequestOC，包括contractSignedDigest仓单质押合同，
    creditAgreementDigest仓单质押授信监管三方协议，
    noticeDigest出质通知书，
    receiptDigest出质通知书回执
    *
    */
    public ChainResult financeChainSign(BigInteger loanOnChainIndex, String contractSignedDigest, String creditAgreementDigest,
                                        String noticeDigest, String receiptDigest) {

        ImpawnLoanRequestOC impawnLoanRequestOC = new ImpawnLoanRequestOC();
        impawnLoanRequestOC.setContractSignedDigest(contractSignedDigest);
        impawnLoanRequestOC.setCreditAgreementDigest(creditAgreementDigest);
        impawnLoanRequestOC.setNoticeDigest(noticeDigest);
        impawnLoanRequestOC.setReceiptDigest(receiptDigest);

        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = impawnLoanXCWrapper.signImpawnLoanOC(loanOnChainIndex, impawnLoanRequestOC);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }


    public ChainResult financeChainQuery(BigInteger loanOnChainIndex) {
        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = impawnLoanXCWrapper.queryImpawnLoanStateOC(loanOnChainIndex);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }

    /*
    金融机构放款，金融机构调用该合约，转账给指定客户端
    @param bankToClientStat 银行to客户转账流水
    @param clientOffAccount 客户链下账户
    @param bankOffAccount 银行链上账户
    */
    public ChainResult financeChainMake(BigInteger loanOnChainIndex, String bankToClientStat, String clientOffAccount,
                                        String bankOffAccount) {

        ImpawnLoanRequestOC impawnLoanRequestOC = new ImpawnLoanRequestOC();
        impawnLoanRequestOC.setBankToClientStat(bankToClientStat);
        impawnLoanRequestOC.setClientOffAccount(clientOffAccount);
        impawnLoanRequestOC.setBankOffAccount(bankOffAccount);

        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = impawnLoanXCWrapper.makeImpawnLoanOC(loanOnChainIndex, impawnLoanRequestOC);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }

    /*
    客户还款，由客户调用，转账给贷款银行
    @param clientToBankStat 客户to银行转账流水
    @param clientOffAccount 客户链下账户
    @param bankOffAccount 银行链上账户
    */
    public ChainResult financeChainRefund(BigInteger loanOnChainIndex, String clientToBankStat, String clientOffAccount,
                                          String bankOffAccount) {

        ImpawnLoanRequestOC impawnLoanRequestOC = new ImpawnLoanRequestOC();
        impawnLoanRequestOC.setBankToClientStat(clientToBankStat);
        impawnLoanRequestOC.setClientOffAccount(clientOffAccount);
        impawnLoanRequestOC.setBankOffAccount(bankOffAccount);

        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = impawnLoanXCWrapper.refundImpawnLoanOC(loanOnChainIndex, impawnLoanRequestOC);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }


    /*解除贷款合同
    *
    * @param unsignImpawnContractDigest 解除贷款合同证明摘要
    * */
    public ChainResult financeChainUnsign(BigInteger loanOnChainIndex, String unsignImpawnContractDigest) {

        ImpawnLoanRequestOC impawnLoanRequestOC = new ImpawnLoanRequestOC();
        impawnLoanRequestOC.setUnsignImpawnContractDigest(unsignImpawnContractDigest);

        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = impawnLoanXCWrapper.unsignImpawnLoanOC(loanOnChainIndex, impawnLoanRequestOC);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }


     /*
    保存进口订单
    *@param ImportOrderOC 包含
    *orderId 进口订单号， createTime 创建时间，importType 进口类型，
    *contractId 合同编号，clientName 客户名称，loadingPort 装货港，destinationPort 目的港，
    * businessGroupId 业务组id，tenantId 租户id，orderDigest 订单摘要
    */
    public ChainResult bussChainImportOrderUpload(String importOrderId, String createTime, String importType,
                                                  String contractId, String clientName, String loadingPort,
                                                  String destinationPort, String businessGroupId, String tenantId, String orderDigest) {

        ImportOrderOC importOrderOC = new ImportOrderOC();
        importOrderOC.setOrderId(importOrderId);
        importOrderOC.setCreateTime(createTime);
        importOrderOC.setImportType(importType);
        importOrderOC.setContractId(contractId);
        importOrderOC.setClientName(clientName);
        importOrderOC.setLoadingPort(loadingPort);
        importOrderOC.setDestinationPort(destinationPort);
        importOrderOC.setBusinessGroupId(businessGroupId);
        importOrderOC.setTenantId(tenantId);
        importOrderOC.setOrderDigest(orderDigest);

        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = importOrderXCWrapper.createOrderOC(importOrderOC);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }


    public ChainResult bussChainImportOrderQuery(BigInteger importOrderOnchainIndex) {
        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = importOrderXCWrapper.queryOrderOC(importOrderOnchainIndex);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }

    public ChainResult bussChainImportOrderStateQuery(BigInteger importOrderOnchainIndex) {
        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = importOrderXCWrapper.queryOrderStateOC(importOrderOnchainIndex);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }

    /*
    保存报关单
    *@param CustomFormOC 包含
    *createTime 申报时间，customNo报关单号, customCode 海关编码，checkState 审核状态
    *customFormDigest 报关单内容摘要，onchainOrderIndex 链上报关单索引
    */
    public ChainResult bussChainCustomeFormUpload(BigInteger importOrderOnchainIndex, String createTime, String customNo, String customCode,
                                                  String checkStat, String formDigest) {

        CustomFormOC customFormOnChain = new CustomFormOC();
        customFormOnChain.setCreateTime(createTime);
        customFormOnChain.setCustomNo(customNo);
        customFormOnChain.setCustomCode(customCode);
        customFormOnChain.setCheckState(checkStat);
        customFormOnChain.setFormDigest(formDigest);

        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = importOrderXCWrapper.createOrderCustomFormOC(importOrderOnchainIndex, customFormOnChain);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }

    public ChainResult bussChainCustomeFormQuery(BigInteger importOrderOnchainIndex) {
        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = importOrderXCWrapper.queryOrderCustomFormOC(importOrderOnchainIndex);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }

     /*
    保存到港订单
    *@param ArrivalFormOC, 包括createTime更新时间，
    *onPortState 到港状态，onPortDate 到港日期，arriveEntityDigest 到港订单摘要
    */
    public ChainResult bussChainArrivalUpload(BigInteger importOrderOnchainIndex, String createTime, String onPortState, String onPortDate,
                                                  String arriveEntityDigest){

        ArrivalFormOC arrivalFormOnChain = new ArrivalFormOC();
        arrivalFormOnChain.setCreateTime(createTime);
        arrivalFormOnChain.setOnPortState(onPortState);
        arrivalFormOnChain.setOnPortDate(onPortDate);
        arrivalFormOnChain.setArriveEntityDigest(arriveEntityDigest);
        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = importOrderXCWrapper.createOrderArrivalOC(importOrderOnchainIndex, arrivalFormOnChain);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }

    public ChainResult bussChainArrivalQuery(BigInteger importOrderOnchainIndex) {
        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = importOrderXCWrapper.queryOrderArrivalOC(importOrderOnchainIndex);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }

    /*
    保存入库订单
    *@param InboundFormOC, 包括createTime入库时间, inboundState入库状态 01:新增 02:已生成波次单 03:完成
    *inboundType入库类型, inboundEntityDigest入库订单摘要
    */
    public ChainResult bussChainInboundUpload(BigInteger importOrderOnchainIndex, String createTime, String inboundState,
                                              String inboundType,
                                              String inboundEntityDigest){

        InboundFormOC inboundFormOnChain = new InboundFormOC();
        inboundFormOnChain.setCreateTime(createTime);
        inboundFormOnChain.setInboundState(inboundState);
        inboundFormOnChain.setInboundType(inboundType);
        inboundFormOnChain.setInboundEntityDigest(inboundEntityDigest);

        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = importOrderXCWrapper.createOrderInboundOC(importOrderOnchainIndex, inboundFormOnChain);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }

    public ChainResult bussChainInboundQuery(BigInteger importOrderOnchainIndex) {
        ChainResult chainResult = new ChainResult();
        try {
            FunctionResult functionResult = importOrderXCWrapper.queryOrderInboundOC(importOrderOnchainIndex);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }

}
