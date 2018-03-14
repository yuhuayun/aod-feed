package com.sinosoft.aod.feed.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sinosoft.aod.feed.Contants;
import com.sinosoft.aod.feed.model.CaseInfo;
import com.sinosoft.aod.feed.model.PanelError;
import com.sinosoft.aod.feed.service.IPanelErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  案件上传失败前端控制器
 * @author LongLei
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("panelError")
public class PanelErrorController extends BaseController<IPanelErrorService, PanelError> {

    private final IPanelErrorService panelErrorService;

    /**
     * 分页查询名单上传失败列表
     * @param page  分页对象Page
     * @param caseinfo  查询模型CaseInfo
     * @return 返回分页的PanelError数据
     */
    @GetMapping("/pageAllFailure")
    public Page<PanelError> pageAllFailureList(Page page, CaseInfo caseinfo){
        PanelError panelError = new PanelError();
        panelError.setCaseId(caseinfo.getId());
        panelError.setAbnormalType(Contants.SJYC);
        return panelErrorService.selectPage(page,new EntityWrapper<>(panelError));
    }
}
