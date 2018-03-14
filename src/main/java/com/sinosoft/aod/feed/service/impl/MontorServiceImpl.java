package com.sinosoft.aod.feed.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sinosoft.aod.feed.dao.MonitorMapper;
import com.sinosoft.aod.feed.dao.ServiceConfigMapper;
import com.sinosoft.aod.feed.enums.ServiceConfigStatusEnum;
import com.sinosoft.aod.feed.model.Monitor;
import com.sinosoft.aod.feed.model.ServiceConfig;
import com.sinosoft.aod.feed.msg.Response;
import com.sinosoft.aod.feed.service.IMonitorService;
import com.sinosoft.aod.feed.utils.HttpClientUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监控实现类
 *
 * @author LongLei
 * @date ${datetime}
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MontorServiceImpl extends ServiceImpl<MonitorMapper, Monitor> implements IMonitorService {

    private final MonitorMapper monitorMapper;

    private final ServiceConfigMapper serviceConfigMapper;
    /**
     * aspect平台地址
     */
    @Value("${serviceInfo.url}")
    private String url;

    /**
     * 实现查询监控数据的方法
     *
     * @param page 分页对象Page
     * @return 返回分页的监控数据
     */
    @Override
    public Page<Monitor> queryMonitorData(Page page) {
        List<Monitor> list = monitorMapper.queryMonitorData();
        page.setRecords(list);
        return page;
    }

    /**
     * @param serviceId 服务id
     * @param status    服务状态
     * @return 调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    @Override
    public Response updateStatus(String serviceId, String status) {
        int sta = ServiceConfigStatusEnum.DISABLED.ordinal();
        Map<String, String> params = new HashMap<>();
        if (status.equals(Integer.toString(sta))) {
            params.put("aodmethod", "stopservice");
            params.put("serviceid", serviceId);
            params.put("queueaction", "1");
            params.put("key", "");
        } else {
            params.put("aodmethod", "startservice");
            params.put("serviceid", serviceId);
            params.put("key", "");
        }
        HttpClientUtils.doGet(url, params, "utf-8");
        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setStatus(status);
        serviceConfigMapper.update(serviceConfig, new EntityWrapper<ServiceConfig>().eq("service_id",serviceId));
        return new Response().success(status);
    }

}
