
package com.sinosoft.aod.feed.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.sinosoft.aod.feed.model.ServiceConfig;
import com.sinosoft.aod.feed.msg.Response;
import com.sinosoft.aod.feed.service.IServiceConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 服务配置控制器
 * @author LiuJuanjuan
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("serviceConfig")
public class ServiceConfigController extends BaseController<IServiceConfigService, ServiceConfig> {

    private final IServiceConfigService serviceConfigService;

    /**
     * 分页查询未被删除的服务配置列表
     * @param page          分页
     * @param serviceConfig 服务配置
     * @return Page<ServiceConfig> 服务配置的分页对象
     */
    @Override
    public Page<ServiceConfig> list(Page page, ServiceConfig serviceConfig) {
        return serviceConfigService.list(page, serviceConfig);
    }

    /**
     * 获取所有的未被删除的服务配置列表
     * @return List<ServiceConfig>,服务配置集合
     */
    @Override
    public List<ServiceConfig> all() {
        return serviceConfigService.all();

    }

    /**
     * 新增服务配置
     * @param serviceConfig 服务配置
     * @return Response,调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    @Override
    @PostMapping("/insert")
    public Response add(@RequestBody ServiceConfig serviceConfig) {
        return serviceConfigService.add(serviceConfig);
    }

    /**
     * 修改服务配置
     * @param serviceConfig 服务配置
     * @return Response,调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    @Override
    @PostMapping("/update")
    public Response update(@RequestBody ServiceConfig serviceConfig) {
        return serviceConfigService.update(serviceConfig);
    }

    /**
     * 按id单个删除服务（假删除）
     * @param id 主键id
     * @return Response,调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    @PostMapping(value = "deleteById/{id}")
    public Response deleteById(@PathVariable long id) {
        return serviceConfigService.deleteById(id);
    }

    /**
     * 批量删除服务配置（假删除）
     * @param idList 主键id集合
     * @return Response,调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    @PostMapping("/deleteBatchIds")
    public Response deleteBatchId(@RequestParam("idList") List<Long> idList) {
        return serviceConfigService.deleteBatchId(idList);
    }

    /**
     * 新增服务配置时，获取当前租户下未被配置的服务列表以供选择
     * @return String,服务信息的json数组字符串，[{"serviceId":"","serviceDesc":"","serviceType":""},...]
     */
    @GetMapping("/serviceInfo")
    public String serviceInfo() {
        return serviceConfigService.getServiceInfo();
    }

}
