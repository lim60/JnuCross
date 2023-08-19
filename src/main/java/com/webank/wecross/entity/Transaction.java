package com.webank.wecross.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 交易表
 * @TableName transaction
 */
@TableName(value ="transaction")
@Data
public class Transaction implements Serializable {
    /**
     * 交易id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 交易哈希值
     */
    private String hash;

    /**
     * 所属事务id
     */
    private String crossTransactionId;

    /**
     * 链id
     */
    private Long chainId;

    /**
     * 区块高度
     */
    private Integer chainHeight;

    /**
     * 发起账户id
     */
    private Long initiateAccountId;

    /**
     * 智能合约id
     */
    private Long smartContractId;

    /**
     * 调用方法
     */
    private String function;

    /**
     * 调用参数：{par1:xx; par2:xxx;...}
     */
    private String parameter;

    /**
     * 交易状态：0，新建，1，待执行；2，执行中；3，成功；4，失败；5，回滚中；6，已回滚
     */
    private Integer state;

    /**
     * 交易时间
     */
    private LocalDateTime transactionTime;

    /**
     * 寻址过程中的ip-状态列表：{ip1:false,ip2:true,...}
     */
    private Integer addressingIpAndState;

    /**
     * 是否成功寻址：0，寻址失败；1，寻址成功；2，未开始寻址
     */
    private Integer isAddressed;

    /**
     * 交易ip地址
     */
    private String transactionIp;

    /**
     * 寻址用时，秒
     */
    private Double addressingTime;

    /**
     * 在事务中的执行顺序，如果是跨链转账与合约调用，则是按0,1,2,...的顺序递增；如果是原子交换，则交易顺序都为0
     */
    private Integer executionOrder;

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