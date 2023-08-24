package com.webank.wecross.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 合约表
 * @TableName smart_contract
 */
@TableName(value ="smart_contract")
@Data
public class SmartContract implements Serializable {
    /**
     * 合约id
     */
    @TableId
    private Long id;

    /**
     * 合约名称
     */
    private String contractName;

    /**
     * 合约路径
     */
    private String path;

    /**
     * 合约地址
     */
    private String address;

    /**
     * 版本号
     */
    private String version;

    /**
     * 合约语言
     */
    private String language;

    /**
     * 链id
     */
    private Long chainId;

    /**
     * 创建用户id
     */
    private Long createorId;

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