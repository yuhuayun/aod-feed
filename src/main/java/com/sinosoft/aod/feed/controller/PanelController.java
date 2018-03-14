package com.sinosoft.aod.feed.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sinosoft.aod.feed.Contants;
import com.sinosoft.aod.feed.model.AspectPanel;
import com.sinosoft.aod.feed.model.CaseInfo;
import com.sinosoft.aod.feed.model.Panel;
import com.sinosoft.aod.feed.msg.Response;
import com.sinosoft.aod.feed.service.IPanelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * 名单管理控制器
 *
 * @author chenbing
 * @date ${datetime}
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("panel")
public class PanelController extends BaseController<IPanelService, Panel> {

    private final IPanelService panelService;

    /**
     * 分页查询名单上传成功列表
     *
     * @param page     分页对象Page
     * @param caseinfo 查询模型CaseInfo 使用caseInfo.id关键字做查询
     * @return 返回分页的Panel数据
     */
    @GetMapping("/pageAllScuess")
    public Page<Panel> pageAllScuessList(Page page, CaseInfo caseinfo) {
        Panel panel = new Panel();
        panel.setCaseId(caseinfo.getId());
        panel.setDeleteFlag(Contants.N);
        return panelService.selectPage(page, new EntityWrapper<>(panel));
    }

    /**
     * 上传名单到aspect平台
     * @param serviceId     服务ID
     * @param feednumber    上传数量
     * @param panelIds      名单ID集合
     * @return  返回名单集合
     */
    @GetMapping("/upload")
    public List<AspectPanel> uploadPanel(@PathParam("serviceId") String serviceId, @PathParam("feednumber") Integer feednumber, @PathParam("panelIds") String panelIds) {
        return panelService.uploadPanel(serviceId, feednumber, panelIds);
    }

    /**
     * 根据单个号码剔除名单
     * @param phone 电话号码
     * @param file 上传的文件
     * @return 响应内容
     */
    @PostMapping("/deletePanel")
    public Response deletePanel( @RequestParam(value = "phone")String phone, @RequestParam(value = "file")MultipartFile file) {
        return panelService.deletePanel( phone, file);
    }
}
