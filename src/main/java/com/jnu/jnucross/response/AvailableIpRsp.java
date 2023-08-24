package com.jnu.jnucross.response;

import lombok.Data;

import java.util.List;

/**
 * 可用ip响应
 */
@Data
public class AvailableIpRsp {
    /**
     * 链类型
     */
    private String ip;

    /**
     * 是否ping通
     */
    private Integer canPing;


}
