package com.sinosoft.aod.feed.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP处理工具类
 * Created by LONGLEI on 2018/2/7.
 */
public class PublicUtil {

    public static String getPorjectPath(){
        String nowpath = "";
        nowpath=System.getProperty("user.dir")+"/";

        return nowpath;
    }

    /**
     * 获取本机访问地址
     * @return
     */
    public static String getIp(){
        String ip = "";
        try {
            InetAddress inet = InetAddress.getLocalHost();
            ip = inet.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }
}
