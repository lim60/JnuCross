package com.finsupplychain.car.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finsupplychain.car.common.ChainResult;
import com.finsupplychain.car.contractwrapper.xuperchain.CustomDeclareXCWrapper;
import com.finsupplychain.car.entity.CustomFormOC;
import com.jnu.jnucross.chains.ChainWrapper;
import com.jnu.jnucross.chains.FunctionResult;
import com.jnu.jnucross.chains.xuperchain.XuperChainUtils;
import com.jnu.jnucross.chains.xuperchain.XuperChainWrapper;
import org.junit.Test;

import java.math.BigInteger;

public class ChainDataController {
    XuperChainWrapper xuperChainWrapper = new XuperChainWrapper();
    CustomDeclareXCWrapper customDeclareXCWrapper = new CustomDeclareXCWrapper();


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
                                  String certificateDigest){

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
    public ChainResult customChainStateUpdate(BigInteger onChainIndex, String newState){

        FunctionResult functionResult = null;
        ChainResult chainResult = new ChainResult();
        try {
            functionResult = customDeclareXCWrapper.updateOCCustomFormState(onChainIndex, newState);
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
    public ChainResult customChainQuery(BigInteger onChainIndex){

        FunctionResult functionResult = null;
        ChainResult chainResult = new ChainResult();
        try {
            functionResult = customDeclareXCWrapper.queryOCCustomForm(onChainIndex);
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
    public ChainResult customChainStateQuery(BigInteger onChainIndex){

        FunctionResult functionResult = null;
        ChainResult chainResult = new ChainResult();
        try {
            functionResult = customDeclareXCWrapper.queryOCCustomFormState(onChainIndex);
            chainResult.setConResult(functionResult.result);
            chainResult.setTxResult(xuperChainWrapper.getTransaction(functionResult.transactionHash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainResult;
    }

}
