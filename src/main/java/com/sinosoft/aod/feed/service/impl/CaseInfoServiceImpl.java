package com.sinosoft.aod.feed.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.sinosoft.aod.feed.Contants;
import com.sinosoft.aod.feed.dao.CaseInfoMapper;
import com.sinosoft.aod.feed.dao.PanelErrorMapper;
import com.sinosoft.aod.feed.dao.PanelMapper;
import com.sinosoft.aod.feed.model.CaseInfo;
import com.sinosoft.aod.feed.msg.Response;
import com.sinosoft.aod.feed.service.ICaseInfoService;
import com.sinosoft.aod.feed.thread.CaseInfoThread;
import com.sinosoft.aod.feed.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


/**
 * <p>
 * 案件service实现类
 * </p>
 *
 * @author LongLei
 * @date ${datetime}
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CaseInfoServiceImpl extends ServiceImpl<CaseInfoMapper, CaseInfo> implements ICaseInfoService {

    private final CaseInfoMapper caseInfoMapper;

    private final PanelMapper panelMapper;

    private final PanelErrorMapper panelErrorMapper;

    /**
     * 导入名单功能
     * @param file  参数对象
     * @return
     */
    @Override
    public Response insertCaseInfo(String serviceId, String serviceName, MultipartFile file) {
        Response response = new Response();
        try {
            log.info("fileSize------------------->"+file.getSize());
            Long a = System.currentTimeMillis();
            String filePath = PathUtil.getClasspath() + Contants.FILEPATHFILE;							//文件上传路径
            String uploadFileName = UUID.randomUUID().toString();                                //随机生成上传的文件名，防止重复
            String currentFileName = file.getOriginalFilename();
            String fileName =  FileUpload.fileUp(file, filePath, uploadFileName);						//执行上传
            log.info("filePath------------------->"+filePath);
            log.info("fileName------------------->"+fileName);
            List<Map<String, String>> contentList = CsvFileUtil.readConetntCsvFile(filePath, fileName);
            Long b = System.currentTimeMillis();
            log.info("读取CSV文件耗时--------------->"+(b-a)+"毫秒");
            if (!CommonUtil.isEmpityCollection(contentList)) {
                //新增名单案件
                CaseInfo caseInfo = new CaseInfo();
                caseInfo.setId(IdWorker.getId());
                caseInfo.setServiceId(serviceId);
                caseInfo.setServiceName(serviceName);
                caseInfo.setFileName(currentFileName);
                String totalCount = String.valueOf(contentList.size());
                caseInfo.setTotalCount(Long.parseLong(totalCount));
                caseInfo.setCreateId(10000L);
                caseInfo.setCreateTime(new Date());
                caseInfo.setStatus(Contants.SCZ);
                caseInfo.setDeleteFlag(Contants.N);
                caseInfoMapper.insert(caseInfo);
                //执行线程，使用多线程同时进行，每个线程跑5W条数据，降低跑批时间
                // 定义一个临时集合，用于名单信息
                int j = 1;
                List<Map<String, String>> tempList = new LinkedList<>();
                for (int i = 0; i < contentList.size(); i++) {
                    tempList.add(contentList.get(i));
                    if (i != 0 && i % 50000 == 0) {
                        this.createThread4CaseInfo(tempList, j, caseInfo);
                        j++;
                    }
                }
                this.createThread4CaseInfo(tempList, j, caseInfo);

                response.setErrCode(0);
                response.setErrMsg("导入中");
            } else {
                response.setErrCode(1);
                response.setErrMsg("导入失败，数据为空");
            }
        } catch (IOException e) {
            response.setErrCode(1);
            response.setErrMsg(e.getMessage());
        }
        return response;
    }

    /**
     * 执行名单导入线程
     * @param tempList  名单集合
     * @param i         第i个线程
     */
    public void createThread4CaseInfo(List<Map<String, String>> tempList, int i, CaseInfo caseInfo) {
        CaseInfoThread caseInfoThread = new CaseInfoThread(caseInfoMapper, panelMapper, panelErrorMapper, tempList, caseInfo);
        caseInfoThread.setName("第"+i+"个50000条线程执行");
        caseInfoThread.start();
        //清空临时集合
        tempList = new LinkedList<>();
    }

    /**
     * 实现接口:按条件查询分页
     * @param page 分页对象Page
     * @param caseInfo 参数对象CaseInfo模型
     * @return 返回分页的CaseInfo数据
     */
    @Override
    public Page<CaseInfo> selectCaseInfoPage(Page page, CaseInfo caseInfo) {
        caseInfo.setDeleteFlag("N");
        Page<CaseInfo> p;
        if (CommonUtil.isNotNull(caseInfo.getSelectStartTime()) && CommonUtil.isNotNull(caseInfo.getSelectEndTime())) {
            //页面有参数传入则按条件查询
            p = this.selectPage(page,new EntityWrapper<CaseInfo>(caseInfo).between("create_time", caseInfo.getSelectStartTime(), caseInfo.getSelectEndTime()));
        } else {
            //页面无参数传入使用默认条件,只查询当天的数据
            caseInfo.setSelectStartTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd 00:00:00"));
            caseInfo.setSelectEndTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:SS"));
            p = this.selectPage(page, new EntityWrapper<CaseInfo>(caseInfo).between("create_time", caseInfo.getSelectStartTime(), caseInfo.getSelectEndTime()));
        }
        return p;
    }
}
