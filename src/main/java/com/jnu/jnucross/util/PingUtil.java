package com.jnu.jnucross.util;

import com.jnu.jnucross.handler.JnuCrossURIHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * ip端口测试连通工具类
 */
public class PingUtil {

    private static final Logger logger = LoggerFactory.getLogger(PingUtil.class);


    public static boolean ping(String host, int port, int timeout) {
        Socket s = new Socket();
        boolean status = false;
        try {
            s.connect(new InetSocketAddress(host, port), timeout);
            System.out.println("ip及端口访问正常");
            status = true;
        } catch (IOException e) {
            logger.info(host + ":" + port + "无法访问！");
        } finally {
            try {
                s.close();
            } catch (IOException ex) {
                logger.info("关闭socket异常" + ex);
            }
        }
        return status;

    }

}
