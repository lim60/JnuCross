package com.finsupplychain.car.entity;

/**
 * @author jessy-js
 * @ClassName ArrivalFormOC
 * @Version V1.0
 * @Description
 */
public class ArrivalFormOC {
    private String createTime;
    private String onPortState;
    private String onPortDate;
    private String arriveEntityDigest;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOnPortState() {
        return onPortState;
    }

    public void setOnPortState(String onPortState) {
        this.onPortState = onPortState;
    }

    public String getOnPortDate() {
        return onPortDate;
    }

    public void setOnPortDate(String onPortDate) {
        this.onPortDate = onPortDate;
    }

    public String getArriveEntityDigest() {
        return arriveEntityDigest;
    }

    public void setArriveEntityDigest(String arriveEntityDigest) {
        this.arriveEntityDigest = arriveEntityDigest;
    }
}
