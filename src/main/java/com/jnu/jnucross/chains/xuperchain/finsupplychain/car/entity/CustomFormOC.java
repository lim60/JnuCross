package com.jnu.jnucross.chains.xuperchain.finsupplychain.car.entity;

import java.math.BigInteger;

public class CustomFormOC {

    private String orderId;
    private String customNo; //报关单号
    private String customCode; //海关编码
    private String checkState;//审核状态
    private String formDigest; //报关单内容摘要
    private String createTime;
    private String certificateDigest;//完税证明回执
    private BigInteger formIndexOnChain;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomNo() {
        return customNo;
    }

    public void setCustomNo(String customNo) {
        this.customNo = customNo;
    }

    public String getCustomCode() {
        return customCode;
    }

    public void setCustomCode(String customCode) {
        this.customCode = customCode;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getFormDigest() {
        return formDigest;
    }

    public void setFormDigest(String formDigest) {
        this.formDigest = formDigest;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCertificateDigest() {
        return certificateDigest;
    }

    public void setCertificateDigest(String certificateDigest) {
        this.certificateDigest = certificateDigest;
    }

    public BigInteger getFormIndexOnChain() {
        return formIndexOnChain;
    }

    public void setFormIndexOnChain(BigInteger formIndexOnChain) {
        this.formIndexOnChain = formIndexOnChain;
    }
}
