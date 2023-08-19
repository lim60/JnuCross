package com.webank.wecross.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_universal_accounts
 */
@TableName(value ="t_universal_accounts")
@Data
public class TUniversalAccounts implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private String ext;

    /**
     * 
     */
    private Integer latestKeyid;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private String pub;

    /**
     * 
     */
    private String role;

    /**
     * 
     */
    private String salt;

    /**
     * 
     */
    private String sec;

    /**
     * 
     */
    private String tokenSec;

    /**
     * 
     */
    private String uaid;

    /**
     * 
     */
    private Long updateTimestamp;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private Long version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}