package com.sinosoft.aod.feed.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sinosoft.aod.feed.model.CaseInfo;
import com.sinosoft.aod.feed.msg.Response;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author LongLei
 * @date ${datetime}
 */
public interface ICaseInfoService extends IService<CaseInfo> {

    //上传名单
    public Response insertCaseInfo(String serviceId, String serviceName, MultipartFile file);

    public Page<CaseInfo> selectCaseInfoPage(Page page, CaseInfo caseInfo);


}
