package com.jnu.jnucross.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 获取交易详情响应
 */
@Data
public class GetTransactionInfoRsp {
    /**
     * 交易哈希
     */
    private String hash;

    /**
     * 链名
     */
    private String chainName;

    /**
     * 区块高度
     */
    private Integer blockHeight;

    /**
     * 发起账户名
     */
    private String initiateAccountName;

    /**
     * 合约名
     */
    private String contractName;

    /**
     * 调用方法
     */
    private String function;

    /**
     * 调用参数
     */
    private List<String> parameter;

    /**
     * 交易时间
     */
    private LocalDateTime transactTime;

    /**
     * 状态
     */
    private Integer state;

    //额外
    /**
     * 交易id
     */
    private Long transactionId;

    /**
     * 交易类型
     */
    private Integer transactionType;

    /**
     * 链类型
     */
    private Integer chainType;

}
