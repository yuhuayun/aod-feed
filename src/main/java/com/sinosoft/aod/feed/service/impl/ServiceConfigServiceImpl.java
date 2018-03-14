package com.sinosoft.aod.feed.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.sinosoft.aod.feed.dao.ServiceConfigMapper;
import com.sinosoft.aod.feed.enums.ServiceConfigStatusEnum;
import com.sinosoft.aod.feed.model.ServiceConfig;
import com.sinosoft.aod.feed.msg.Response;
import com.sinosoft.aod.feed.service.IServiceConfigService;
import com.sinosoft.aod.feed.utils.HttpClientUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * <p>
 * 服务配置实现类
 * </p>
 *
 * @author LiuJuanjuan
 * @date 2018-01-14 00:00:00
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServiceConfigServiceImpl extends ServiceImpl<ServiceConfigMapper, ServiceConfig> implements IServiceConfigService {

    private final ServiceConfigMapper serviceConfigMapper;
    /**
     * aspect平台地址
     */
    @Value("${serviceInfo.url}")
    private String url;
    /**
     * 当前用户的租户
     */
    @Value("${serviceInfo.tenantId}")
    private String tenantId;
    /**
     * 新增服务配置
     * @param serviceConfig 服务配置
     * @return Response,调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    @Override
    public Response add(ServiceConfig serviceConfig) {
        int status = ServiceConfigStatusEnum.DISABLED.ordinal();
        Long serviceId = IdWorker.getId();
        serviceConfig.setId(serviceId);
        serviceConfig.setStatus(Integer.toString(status));//默认为停用
        serviceConfig.setCreateTime(new Date());
        serviceConfig.setUpdateTime(new Date());
        serviceConfig.setCreateId(1000L);
        serviceConfig.setUpdateId(1000L);
        serviceConfigMapper.insert(serviceConfig);
        return new Response().success();
    }

    /**
     * 修改服务配置
     * @param serviceConfig 服务配置
     * @return Response,调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    @Override
    public Response update(ServiceConfig serviceConfig) {
        serviceConfig.setUpdateTime(new Date());
        serviceConfig.setUpdateId(1000L);
        serviceConfigMapper.updateById(serviceConfig);
        return new Response().success();
    }

    /**
     * 按id单个删除服务（假删除）
     *
     * @param id 主键id
     * @return Response,调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    @Override
    public Response deleteById(long id) {
        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setId(id);
        serviceConfig.setDeleteFlag("Y");
        serviceConfigMapper.updateById(serviceConfig);
        return new Response().success();
    }

    /**
     * 批量删除服务配置（假删除）
     * @param idList 主键id集合
     * @return Response,调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    @Override
    public Response deleteBatchId(List<Long> idList) {
        for (long id : idList) {
            ServiceConfig serviceConfig = new ServiceConfig();
            serviceConfig.setId(id);
            serviceConfig.setDeleteFlag("Y");
            serviceConfig.setUpdateTime(new Date());
            serviceConfig.setUpdateId(1000L);
            serviceConfigMapper.updateById(serviceConfig);
        }
        return new Response().success();
    }

    /**
     * 按id更改服务状态
     * @param id 主键id
     * @param status 状态 1：启用 2：停用
     * @return Response,调用结果，JSON 的字符串,{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    @Override
    public Response updateStatus(long id, String status) {
        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setId(id);
        serviceConfig.setStatus(status);
        serviceConfig.setUpdateTime(new Date());
        serviceConfig.setUpdateId(1000L);
        serviceConfigMapper.updateById(serviceConfig);
        return new Response().success();
    }

    /**
     * 为新增服务配置的下拉框获取aspect平台的服务信息提供选择
     * 1.当前租户下的
     * 2.未被配置的
     * @return String,服务信息的json数组字符串，[{"serviceId":"","serviceDesc":"","serviceType":""},...]
     */
    @Override
    public String getServiceInfo() {
        // 可被选择的服务
        List<Map<String, Object>> selectList = new ArrayList<>();
        // aspect平台下的所有服务
        Map<String, String> params = new HashMap<>();
        params.put("aodmethod", "getserviceids");
        String result = HttpClientUtils.doGet(url,params,"utf-8");
        List<Map<String, Object>> mapList = JSONArray.fromObject(result);
        // 配置列表中已存在的服务
        ServiceConfig serviceConfigWrapper = new ServiceConfig();
        serviceConfigWrapper.setDeleteFlag("N");
        List<ServiceConfig> serviceConfigList = serviceConfigMapper.selectList(new EntityWrapper<>(serviceConfigWrapper));
        // 将未配置的服务放入List中
        if (mapList.isEmpty()) {
            return result;
        }
        if (serviceConfigList.isEmpty()){
            for (Map item:mapList){
                String tenantId1 = item.get("serviceDesc").toString().split("_")[0];//serviceDesc格式为default_dianxiao,取“_“前的default
                if (tenantId.equals(tenantId1)) {
                    selectList.add(item);
                }
            }
            return JSONArray.fromObject(selectList).toString();
        }
        for (Map map : mapList) {
            boolean flag = true;
            for (ServiceConfig serviceConfig : serviceConfigList) {
                String serviceId1 = serviceConfig.getServiceId();
                String serviceId2 = map.get("serviceId").toString();
                String tenantId2 = map.get("serviceDesc").toString().split("_")[0];//serviceDesc格式为default_dianxiao,取“_“前的default
                if (serviceId1.equals(serviceId2) || !tenantId.equals(tenantId2)) {
                    flag = false;
                }
            }
            if (flag) {
                selectList.add(map);
            }
        }
        return JSONArray.fromObject(selectList).toString();
    }

    /**
     * 分页查询（未被删除）
     * @param page 分页
     * @param serviceConfig 服务配置
     * @return Page<ServiceConfig> 服务配置的分页对象
     */
    @Override
    public Page<ServiceConfig> list(Page page, ServiceConfig serviceConfig) {
        serviceConfig.setDeleteFlag("N");
        return this.selectPage(page, new EntityWrapper<>(serviceConfig));
    }

    /**
     * 查询所有（未被删除）
     * @return List<ServiceConfig>服务配置集合
     */
    @Override
    public List<ServiceConfig> all() {
        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setDeleteFlag("N");
        return this.selectList(new EntityWrapper<>(serviceConfig));
    }
}
