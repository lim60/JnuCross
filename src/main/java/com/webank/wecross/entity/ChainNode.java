package com.webank.wecross.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 区块链节点表
 * @TableName chain_node
 */
@TableName(value ="chain_node")
@Data
public class ChainNode implements Serializable {
    /**
     * 节点id
     */
    @TableId
    private Long id;

    /**
     * ip端口
     */
    private String ip;

    /**
     * 所属区块链id
     */
    private Long chainId;

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