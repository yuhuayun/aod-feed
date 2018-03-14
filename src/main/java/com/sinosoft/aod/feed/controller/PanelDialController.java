package com.sinosoft.aod.feed.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sinosoft.aod.feed.model.PanelDial;
import com.sinosoft.aod.feed.msg.Response;
import com.sinosoft.aod.feed.service.IPanelDialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 名单拨打控制器
 *
 * @author chenbing
 * @date ${datetime}
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("paneldials")

public class PanelDialController extends BaseController<IPanelDialService, PanelDial> {
    private final IPanelDialService panelDialService;

    /**
     * 分页查询拨打号码列表
     *
     * @param page 分页对象Page
     * @param panelDial 名单拨打对象
     * @return 返回分页的拨打数据
     */
    @GetMapping("/pageAll")
    public Page<PanelDial> pageAllList(Page page, PanelDial panelDial) {
        return panelDialService.selectPage(page, new EntityWrapper<>(panelDial));
    }

    /**
     * 发送号码
     *
     * @param panelDial 外拨拨打对象
     * @return Response, 调用结果，JSON 的字符串：{"errCode":0,"errMsg":"ok"} ,errCode为0则成功，其他为调用错误
     */
    @GetMapping("/sendPhone")
    public Response sendPhone(PanelDial panelDial){

        return panelDialService.sendPhone(panelDial.getPhone(),panelDial.getPanelId(),panelDial.getServiceId(),panelDial.getResultCode(),panelDial.getAgentId());


    }


}
