package com.fick.luckyfick.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * http工具类
 */
public class HttpUtils {
    /**
     * 获取真实的ip
     * @param request
     * @return
     * @throws UnknownHostException
     */
    public static String getRealIp(HttpServletRequest request){
        String ip;
        // 有的user可能使用代理，为处理用户使用代理的情况，使用x-forwarded-for
        if  (request.getHeader("x-real-ip") == null)  {
            ip = request.getRemoteAddr();
        }  else  {
            ip = request.getHeader("x-real-ip");
        }
        if  ("127.0.0.1".equals(ip))  {
            try {
                // 获取本机真正的ip地址
                ip = InetAddress.getLocalHost().getHostAddress();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return ip;
    }
}
