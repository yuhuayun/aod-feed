package com.sinosoft.aod.feed.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sinosoft.aod.feed.dao.PanelDialMapper;
import com.sinosoft.aod.feed.model.PanelDial;
import com.sinosoft.aod.feed.msg.Response;
import com.sinosoft.aod.feed.service.IPanelDialService;
import com.sinosoft.aod.feed.utils.PatternTools;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * 名单拨打服务实现类
 *
 * @author chenbing
 * @date ${datetime}
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PanelDialServiceImpl extends ServiceImpl<PanelDialMapper, PanelDial> implements IPanelDialService {

    private final PanelDialMapper panelDialMapper;

    /**
     * @param phone 电话号码
     * @param panelId 名单ID
     * @param serviceId 服务id
     * @param resultCode 结果
     * @param agentId 坐席工号id
     * @return Response,调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    @Override
    public Response sendPhone(String phone, Long panelId, String serviceId,String resultCode,String agentId) {
        PanelDial panelDial = new PanelDial();
        String localPhone = PatternTools.isLocal(phone);
        panelDial.setPhone(localPhone);
        panelDial.setServiceId(serviceId);
        panelDial.setPanelId(panelId);
        panelDial.setResultCode(resultCode);
        panelDial.setCreateTime(new Date());
        panelDial.setCreateId(12L);
        panelDial.setUpdateTime(new Date());
        panelDial.setUpdateId(12L);
        panelDial.setAgentId(agentId);
        panelDial.setResultCode(resultCode);

        panelDialMapper.insert(panelDial);
        return new Response().success();
    }




}
