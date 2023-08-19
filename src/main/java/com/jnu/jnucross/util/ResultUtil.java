package com.jnu.jnucross.util;

import cn.hutool.core.util.NumberUtil;
import com.webank.wecross.restserver.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * 结果返回封装类
 */
public class ResultUtil {
    private static final Logger logger = LoggerFactory.getLogger(ResultUtil.class);

    public static  RestResponse success() {
        RestResponse result = new RestResponse<>();
        result.setMessage("success");
        return result;
    }

    public static <T> RestResponse<T> success(T data) {
        RestResponse<T> result = new RestResponse<>();
        result.setData(data);
        result.setMessage("success");
        return result;
    }

    public static RestResponse error() {
        return error("error",-1);
    }

    public static RestResponse error(String message,Integer errorCode) {
        RestResponse result = new RestResponse<>();
        result.setErrorCode(errorCode);
        result.setMessage(message);
        return result;
    }
}