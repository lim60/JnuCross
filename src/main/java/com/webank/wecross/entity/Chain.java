package com.webank.wecross.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName chain
 */
@TableName(value ="chain")
@Data
public class Chain implements Serializable {
    /**
     * 区块链id
     */
    @TableId
    private Long id;

    /**
     * 区块链名
     */
    private String chainName;

    /**
     * 区块链类型，5种类型
     */
    private Integer chainType;

    /**
     * 区块高度
     */
    private Integer chainHeight;

    /**
     * 状态：0-待验证；1-已上线
     */
    private Integer chainState;

    /**
     * 区块域名
     */
    private String chainDomain;

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