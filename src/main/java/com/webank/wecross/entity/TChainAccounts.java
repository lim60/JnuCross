package com.webank.wecross.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_chain_accounts
 */
@TableName(value ="t_chain_accounts")
@Data
public class TChainAccounts implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String ext0;

    /**
     * 
     */
    private String ext1;

    /**
     * 
     */
    private String ext2;

    /**
     * 
     */
    private String ext3;

    /**
     * 
     */
    private String identity;

    /**
     * 
     */
    private String identityDetail;

    /**
     * 
     */
    private Boolean isDefault;

    /**
     * 
     */
    private Integer keyid;

    /**
     * 
     */
    private String pub;

    /**
     * 
     */
    private String sec;

    /**
     * 
     */
    private String type;

    /**
     * 
     */
    private String username;

    /**
     * 区块链id
     */
    private Integer chainId;

    /**
     * 账户名
     */
    private String accountName;

    /**
     * 地址
     */
    private String address;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}