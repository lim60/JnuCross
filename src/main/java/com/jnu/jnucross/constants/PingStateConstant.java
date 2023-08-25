package com.jnu.jnucross.constants;

//是否能ping通：0，未连接；1，连接成功；2，连接失败
public interface PingStateConstant {

    /**
     * 未连接
     */
    Integer UN_CONNECTED  = 0;
    /**
     * 连接成功
     */
    Integer SUCCESS_CONNECT = 1;
    /**
     * 连接失败
     */
    Integer FAIL_CONNECT = 2;
}
