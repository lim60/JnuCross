package com.jnu.jnucross.json;

import lombok.Data;

/**
 * 可用ip响应
 */
@Data
public class AvailableIpJson {
    /**
     * 链类型
     */
    private String ip;

    /**
     * 是否ping通
     */
    private Integer canPing;


}
