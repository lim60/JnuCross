package com.jnu.jnucross.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 开启事务请求
 */
@Data
public class StartTransactionReq {


    /**
     * 事务ID
     */
    private String transactionId;

    /**
     * 资源路径
     */
    private List<String> paths;







}
/*
事务id；
资源路径Set；
交易List：
{id;
}
 */