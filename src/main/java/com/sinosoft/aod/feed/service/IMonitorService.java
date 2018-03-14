package com.sinosoft.aod.feed.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sinosoft.aod.feed.model.Monitor;
import com.sinosoft.aod.feed.msg.Response;

/**
 * <p>
 * 监控接口类
 * </p>
 *
 * @author chenbing
 * @date ${datetime}
 */
public interface IMonitorService extends IService<Monitor> {

    /**
     * 展示监控数据
     *
     * @param page 分页对象Page
     */
    public Page<Monitor> queryMonitorData(Page page);

    /**
     * 更新服务启动状态
     * @param serviceId
     * @param status
     * @return 调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    public Response updateStatus(String serviceId, String status);
   }
