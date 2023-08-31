package com.finsupplychain.car.entity;

import java.math.BigInteger;

/**
 * @author jessy-js
 * @ClassName ImpawnLoanRequestOC
 * @Version V1.0
 * @Description
 */
public class ImpawnLoanRequestOC {

    private BigInteger orderIndexOnChain;//用于关联<--->中海龙业务链的进口订单
    private BigInteger  requestedValue;//申请贷款金额
    private String loanState;//贷款申请单状态
    private String clientAddr;//客户链上账户地址
    private String bankAddr; //银行链上账户地址

    private String clientInfoDigest;
    private String bankInfoDigest;
    private String clientOffAccount;
    private String bankOffAccount;

    private String contractSignedDigest; //仓单质押合同
    private String creditAgreementDigest; //仓单质押授信监管三方协议
    private String noticeDigest; //出质通知书
    private String receiptDigest; //出质通知书回执

    private String unsignImpawnContractDigest; //贷款合同解除证明
    private String bankToClientStat; //银行转账给客户的流水
    private String clientToBankStat; //客户转账给银行的流水

    private Boolean ifAllowed;


    public Boolean getIfAllowed() {
        return ifAllowed;
    }

    public void setIfAllowed(Boolean ifAllowed) {
        this.ifAllowed = ifAllowed;
    }

    public BigInteger getLoadIndexOC() {
        return loadIndexOC;
    }

    public void setLoadIndexOC(BigInteger loadIndexOC) {
        this.loadIndexOC = loadIndexOC;
    }
    private BigInteger loadIndexOC;


    public String getClientOffAccount() {
        return clientOffAccount;
    }

    public void setClientOffAccount(String clientOffAccount) {
        this.clientOffAccount = clientOffAccount;
    }

    public String getBankOffAccount() {
        return bankOffAccount;
    }

    public void setBankOffAccount(String bankOffAccount) {
        this.bankOffAccount = bankOffAccount;
    }

    public BigInteger getOrderIndexOnChain() {
        return orderIndexOnChain;
    }

    public String getClientInfoDigest() {
        return clientInfoDigest;
    }

    public void setClientInfoDigest(String clientInfoDigest) {
        this.clientInfoDigest = clientInfoDigest;
    }

    public String getBankInfoDigest() {
        return bankInfoDigest;
    }

    public void setBankInfoDigest(String bankInfoDigest) {
        this.bankInfoDigest = bankInfoDigest;
    }

    public void setOrderIndexOnChain(BigInteger orderIndexOnChain) {
        this.orderIndexOnChain = orderIndexOnChain;
    }

    public BigInteger getRequestedValue() {
        return requestedValue;
    }

    public void setRequestedValue(BigInteger requestedValue) {
        this.requestedValue = requestedValue;
    }

    public String getLoanState() {
        return loanState;
    }

    public void setLoanState(String loanState) {
        this.loanState = loanState;
    }

    public String getClientAddr() {
        return clientAddr;
    }

    public void setClientAddr(String clientAddr) {
        this.clientAddr = clientAddr;
    }

    public String getBankAddr() {
        return bankAddr;
    }

    public void setBankAddr(String bankAddr) {
        this.bankAddr = bankAddr;
    }

    public String getContractSignedDigest() {
        return contractSignedDigest;
    }

    public void setContractSignedDigest(String contractSignedDigest) {
        this.contractSignedDigest = contractSignedDigest;
    }

    public String getCreditAgreementDigest() {
        return creditAgreementDigest;
    }

    public void setCreditAgreementDigest(String creditAgreementDigest) {
        this.creditAgreementDigest = creditAgreementDigest;
    }

    public String getNoticeDigest() {
        return noticeDigest;
    }

    public void setNoticeDigest(String noticeDigest) {
        this.noticeDigest = noticeDigest;
    }

    public String getReceiptDigest() {
        return receiptDigest;
    }

    public void setReceiptDigest(String receiptDigest) {
        this.receiptDigest = receiptDigest;
    }

    public String getUnsignImpawnContractDigest() {
        return unsignImpawnContractDigest;
    }

    public void setUnsignImpawnContractDigest(String unsignImpawnContractDigest) {
        this.unsignImpawnContractDigest = unsignImpawnContractDigest;
    }

    public String getBankToClientStat() {
        return bankToClientStat;
    }

    public void setBankToClientStat(String bankToClientStat) {
        this.bankToClientStat = bankToClientStat;
    }

    public String getClientToBankStat() {
        return clientToBankStat;
    }

    public void setClientToBankStat(String clientToBankStat) {
        this.clientToBankStat = clientToBankStat;
    }
}
