package com.finsupplychain.car.entity;

import java.math.BigInteger;

/**
 * @author jessy-js
 * @ClassName ImportOrderOC
 * @Version V1.0
 * @Description
 */
public class ImportOrderOC {
    private String orderId;
    private String createTime;
    private String importType;
    private String contractId;
    private String clientName;
    private String loadingPort;
    private String destinationPort;
    private String businessGroupId;
    private String tenantId;
    private String orderDigest;
    private BigInteger orderIndexOnChain;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getImportType() {
        return importType;
    }

    public void setImportType(String importType) {
        this.importType = importType;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getLoadingPort() {
        return loadingPort;
    }

    public void setLoadingPort(String loadingPort) {
        this.loadingPort = loadingPort;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
    }

    public String getBusinessGroupId() {
        return businessGroupId;
    }

    public void setBusinessGroupId(String businessGroupId) {
        this.businessGroupId = businessGroupId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getOrderDigest() {
        return orderDigest;
    }

    public void setOrderDigest(String orderDigest) {
        this.orderDigest = orderDigest;
    }

    public BigInteger getOrderIndexOnChain() {
        return orderIndexOnChain;
    }

    public void setOrderIndexOnChain(BigInteger orderIndexOnChain) {
        this.orderIndexOnChain = orderIndexOnChain;
    }
}
