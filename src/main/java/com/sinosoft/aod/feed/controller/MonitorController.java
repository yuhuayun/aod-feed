package com.sinosoft.aod.feed.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.sinosoft.aod.feed.model.Monitor;
import com.sinosoft.aod.feed.msg.Response;
import com.sinosoft.aod.feed.service.IMonitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 服务监控控制器
 *
 * @author chenbing
 * @date 2018-01-21
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("monitor")
public class MonitorController extends BaseController<IMonitorService, Monitor> {

    private final IMonitorService monitorService;

    /**
     * 分页展示监控数据
     *
     * @param page 分页对象
     * @return 分页监控对象数据
     */
    @GetMapping("/queryMonitorData")
    public Page<Monitor> queryMonitorData(Page page) {
        return monitorService.queryMonitorData(page);
    }

    /**
     * 服务启停
     *
     * @param serviceId 服务id
     * @param status    状态  ENABLED:1,启用;DISABLED:2,禁用
     * @return 调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    @PostMapping("/updateStatus/{serviceId}/{status}")
    public Response updateStatus(@PathVariable String serviceId, @PathVariable String status) {

        return monitorService.updateStatus(serviceId, status);
    }
}
