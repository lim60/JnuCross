package com.finsupplychain.car.entity;

/**
 * @author jessy-js
 * @ClassName InboundFormOC
 * @Version V1.0
 * @Description
 */
public class InboundFormOC {
    private String createTime;
    private String inboundState;
    private String inboundType;
    private String inboundEntityDigest;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getInboundState() {
        return inboundState;
    }

    public void setInboundState(String inboundState) {
        this.inboundState = inboundState;
    }

    public String getInboundType() {
        return inboundType;
    }

    public void setInboundType(String inboundType) {
        this.inboundType = inboundType;
    }

    public String getInboundEntityDigest() {
        return inboundEntityDigest;
    }

    public void setInboundEntityDigest(String inboundEntityDigest) {
        this.inboundEntityDigest = inboundEntityDigest;
    }
}
