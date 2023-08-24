package com.jnu.jnucross.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 查询事务响应
 */
@Data
public class GetCrossTransactionRSp {


    /**
     * 事务ID
     */
    private String transactionId;

    /**
     * 事务类型
     */
    private Integer type;

    /**
     * 创建用户
     */
    private String creator;
    /**
     * 跨链目标
     */
    private List<String> crossChainTarget;
    /**
     * 发起时间
     */
    private String startTime;
    /**
     * 状态
     */
    private Integer state;








}