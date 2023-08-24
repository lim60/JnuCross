package com.webank.wecross.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 网关表
 * @TableName gateway
 */
@TableName(value ="gateway")
@Data
public class Gateway implements Serializable {
    /**
     * 网关id
     */
    @TableId
    private Long id;

    /**
     * 所属区块链id
     */
    private Integer chainId;

    /**
     * 事务类型：001，跨链转账；010，合约调用；100，原子交换；
     */
    private Integer transactionType;

    /**
     * ip端口
     */
    private String ip;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 相邻网关：{ip端口1,ip端口2,...}
     */
    private String connectedList;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}