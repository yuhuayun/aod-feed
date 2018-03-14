package com.sinosoft.aod.feed.model;

import lombok.Data;

/**
 * @author LongLei
 * @date ${datetime}
 */
@Data

public class AodFeed {

    private String aodServiceUrl;
    /**
     * 请求方法
     */
    private String aodMethod;
    /**
     * 请求的KEY
     */
    private String key;
    /**
     * aspect的服务ID
     */
    private String serviceid;
    /**
     * 标示一个外呼记录
     */
    private String recordid;
    /**
     * 是否预览，1 预览，0 非预览
     */
    private String previewmode;
    /*
    记录拨打的优先级 1 正常的优先级,2 更高级别 9 所有级别
     */
    private String priority;
    /**
     * 号码1
     */
    private String phone1;
    /**
     * 号码2
     */
    private String phone2;
    /**
     * 号码3
     */
    private String phone3;
    /**
     * 号码4
     */
    private String phone4;
    /**
    号码5
    */
    private String phone5;
    /**
    号码6
    */
    private String phone6;
    /**
     * 客户数据1
     */
    private String userdata1;
    /*
    客户数据2
     */
    private String userdata2;
    /**
     * 客户数据3
     */
    private String userdata3;
    /**
     * 客户数据4
     */
    private String userdata4;
    /**
     * 客户数据5
     */
    private String userdata5;
    /**
     * 客户数据6
     */
    private String userdata6;
    /**
     * 是否删除服务中的队列 1 不删除，2 删除
     */
    private String queueaction;

    /**
     * 状态:启动 1,停用 2
     */
    private String status;

}
