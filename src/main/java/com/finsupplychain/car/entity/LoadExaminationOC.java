package com.finsupplychain.car.entity;

/**
 * @author jessy-js
 * @ClassName LoadExaminationOC
 * @Version V1.0
 * @Description
 */
public class LoadExaminationOC {
    private String orderState;
    private String taxState;
    private String importCertificate;
    private String proposal;

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getTaxState() {
        return taxState;
    }

    public void setTaxState(String taxState) {
        this.taxState = taxState;
    }

    public String getImportCertificate() {
        return importCertificate;
    }

    public void setImportCertificate(String importCertificate) {
        this.importCertificate = importCertificate;
    }

    public String getProposal() {
        return proposal;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }
}
