package com.webank.wecross.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 寻址过程的ip端口和状态
 * @TableName addressing_ip_and_state
 */
@TableName(value ="addressing_ip_and_state")
@Data
public class AddressingIpAndState implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Integer id;

    /**
     * 交易id
     */
    private Long transactionId;

    /**
     * ip端口
     */
    private String ip;

    /**
     * 是否能ping通：0，未连接；1，连接成功；2，连接失败
     */
    private Integer isConnected;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}