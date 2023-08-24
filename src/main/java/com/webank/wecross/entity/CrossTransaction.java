package com.webank.wecross.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 跨链事务表
 * @TableName cross_transaction
 */
@TableName(value ="cross_transaction")
@Data
public class CrossTransaction implements Serializable {
    /**
     * 事务id,uuid
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 事务类型：0，跨链转账；1，合约调用；2，原子交换；
     */
    private Integer type;

    /**
     * 涉及区块链ids：{0,1,...}
     */
    private String chains;

    /**
     * 事务状态：0，执行中；1，已完成；2，已中止；3，已回滚；
     */
    private Integer state;

    /**
     * 发起账户
     */
    private Long initiateAccount;

    /**
     * 参与账户ids：{0,1,...}
     */
    private String participateAccounts;

    /**
     * 所使用的合约ids：{0,1,...}
     */
    private String smartContracts;

    /**
     * 寻址策略：0，默认策略；
     */
    private Integer addressingStrategy;

    /**
     * 创建用户id
     */
    private Long creatorId;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

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