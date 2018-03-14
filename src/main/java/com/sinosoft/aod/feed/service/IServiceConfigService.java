package com.sinosoft.aod.feed.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sinosoft.aod.feed.model.ServiceConfig;
import com.sinosoft.aod.feed.msg.Response;

import java.util.List;

/**
 * <p>
 *  服务配置接口类
 * </p>
 *
 * @author LiuJuanjuan
 * @date 2018-01-14 00:00:00
 */
public interface IServiceConfigService extends IService<ServiceConfig> {
    /**
     * 新增服务配置
     * @param serviceConfig 服务配置
     * @return Response,调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    public Response add(ServiceConfig serviceConfig);

    /**
     * 修改服务配置
     * @param serviceConfig 服务配置
     * @return Response,调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    public Response update(ServiceConfig serviceConfig);

    /**
     * 按id单个删除服务（假删除）
     *
     * @param id 主键id
     * @return Response,调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    public Response deleteById(long id);

    /**
     * 批量删除服务配置（假删除）
     * @param idList 主键id集合
     * @return Response,调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    public Response deleteBatchId(List<Long> idList);

    /**
     * 按id更改服务状态
     * @param id 主键id
     * @param status 状态 1：启用 2：停用
     * @return Response,调用结果，JSON 的字符串,{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    public Response updateStatus(long id, String status);

    /**
     * 为新增服务配置的下拉框获取aspect平台的服务信息提供选择
     * 1.当前租户下的
     * 2.未被配置的
     * @return String,服务信息的json数组字符串，[{"serviceId":"","serviceDesc":"","serviceType":""},...]
     */
    public String getServiceInfo();

    /**
     * 分页查询（未被删除）
     * @param page 分页
     * @param serviceConfig 服务配置
     * @return Page<ServiceConfig> 服务配置的分页对象
     */
    public Page<ServiceConfig> list(Page page, ServiceConfig serviceConfig);

    /**
     * 查询所有（未被删除）
     * @return List<ServiceConfig>服务配置集合
     */
    public List<ServiceConfig> all();

}
