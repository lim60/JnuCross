package com.jnu.jnucross.response;

import com.webank.wecross.zone.ChainInfo;
import lombok.Data;

import java.util.List;

/**
 * 获取寻址信息接口响应 交易所在链、可用ip（查json），最后使用ip，得排序
 */
@Data
public class GetAddressPageInfoRsp {
    /**
     * 链名
     */
    private String chainName;

    /**
     * 调用合约名称
     */
    private String contractName;

    /**
     * 可用ip列表
     */
    private List<AvailableIpRsp> availableIps;

    /**
     * 调用方法
     */
    private String callMethod;

    /**
     * 最终确定ip
     */
    private String finalIp;

    /**
     * 交易的顺序
     */
    private Integer order;

    /**
     * 链的域名
     */
    private String domain;

    /**
     * 事务类型
     */
    private Integer crossTransactionType;

    /**
     * 交易id
     */
    private Long transactionId;


}
