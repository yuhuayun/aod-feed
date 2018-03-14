package com.sinosoft.aod.feed.service;

import com.baomidou.mybatisplus.service.IService;
import com.sinosoft.aod.feed.model.PanelDial;
import com.sinosoft.aod.feed.msg.Response;

/**
 * <p>
 * 名单拨打接口类
 * </p>
 *
 * @author chenbing
 * @date ${datetime}
 */
public interface IPanelDialService extends IService<PanelDial> {



    /**
     *
     * @param phone 电话号码
     * @param panelId 名单ID
     * @param serviceId 服务id
     * @param resultCode 结果
     * @param agentId 坐席工号id
     * @return Response,调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    Response sendPhone(String phone, Long panelId, String serviceId,String resultCode, String agentId);
}
