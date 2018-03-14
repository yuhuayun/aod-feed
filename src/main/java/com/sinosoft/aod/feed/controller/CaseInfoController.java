package com.sinosoft.aod.feed.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sinosoft.aod.feed.model.CaseInfo;
import com.sinosoft.aod.feed.msg.Response;
import com.sinosoft.aod.feed.service.ICaseInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 案件控制器
 * </p>
 *
 * @author LongLei
 * @date
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("caseInfo")
public class CaseInfoController extends BaseController<ICaseInfoService, CaseInfo> {

    private final ICaseInfoService caseInfoService;

    /**
     * 上传名单
     * @param serviceId 服务ID
     * @param serviceName   服务名称
     * @param file  上传的文件
     * @return  返回处理结果及处理结果信息
     */
    @PostMapping(value = "/insert")
    public Response insertCaseInfo(@RequestParam(value = "serviceId") String serviceId, @RequestParam(value = "serviceName") String serviceName, @RequestParam(value="file") MultipartFile file) {
        return caseInfoService.insertCaseInfo(serviceId, serviceName, file);
    }

    /**
     * 分页查询名单上传的列表
     * @param page 分页对象Page
     * @param caseInfo 查询模型CaseInfo 使用caseInfo.id关键字做查询
     * @return 返回分页的CaseInfo数据
     */
    @GetMapping("/pageAll")
    public Page<CaseInfo> pageAllList(Page page, CaseInfo caseInfo) {
        return caseInfoService.selectPage(page, new EntityWrapper<>(caseInfo));
    }
}
