package com.jnu.jnucross.chains.xuperchain.finsupplychain.car.entity;

import java.math.BigInteger;

public class EvidenceOC {

    BigInteger orderIndex; //关联order's on-chain ID
    String evidenceState; //证据最新状态From State
    String[] evidenceDigests;//关于不同状态下的证据摘要
    String[] signatures;
    BigInteger evidenceIndexOnChain;

    public BigInteger getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(BigInteger orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getEvidenceState() {
        return evidenceState;
    }

    public void setEvidenceState(String evidenceState) {
        this.evidenceState = evidenceState;
    }

    public String[] getEvidenceDigests() {
        return evidenceDigests;
    }

    public void setEvidenceDigests(String[] evidenceDigests) {
        this.evidenceDigests = evidenceDigests;
    }

    public String[] getSignatures() {
        return signatures;
    }

    public void setSignatures(String[] signatures) {
        this.signatures = signatures;
    }

    public BigInteger getEvidenceIndexOnChain() {
        return evidenceIndexOnChain;
    }

    public void setEvidenceIndexOnChain(BigInteger evidenceIndexOnChain) {
        this.evidenceIndexOnChain = evidenceIndexOnChain;
    }
}
