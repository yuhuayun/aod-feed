package com.sinosoft.aod.feed.thread;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.sinosoft.aod.feed.Contants;
import com.sinosoft.aod.feed.dao.CaseInfoMapper;
import com.sinosoft.aod.feed.dao.PanelErrorMapper;
import com.sinosoft.aod.feed.dao.PanelMapper;
import com.sinosoft.aod.feed.model.CaseInfo;
import com.sinosoft.aod.feed.model.Panel;
import com.sinosoft.aod.feed.model.PanelError;
import com.sinosoft.aod.feed.utils.BeanRefUtil;
import com.sinosoft.aod.feed.utils.CommonUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 名单导入线程类
 * Created by LONGLEI on 2018/1/23.
 */
public class CaseInfoThread extends Thread {

    private CaseInfoMapper caseInfoMapper;
    private PanelMapper panelMapper;
    private PanelErrorMapper panelErrorMapper;
    private List<Map<String, String>> contentList;
    private CaseInfo caseInfo;

    public CaseInfoThread(CaseInfoMapper caseInfoMapper, PanelMapper panelMapper, PanelErrorMapper panelErrorMapper, List<Map<String, String>> contentList, CaseInfo caseInfo) {
        this.caseInfoMapper = caseInfoMapper;
        this.panelMapper = panelMapper;
        this.panelErrorMapper = panelErrorMapper;
        this.contentList = contentList;
        this.caseInfo = caseInfo;
    }

    /**
     * 导入名单线程执行
     */
    @Override
    public void run() {
        System.out.println("当前线程---------------->"+Thread.currentThread().getName());
        //循环遍历名单,防止频繁与数据库交互，采用每次2000条入库一次
        int successCount = 0;
        int total = contentList.size();
        int z = 0, count = 2000;
        int number = total%count==0 ? total/count : total/count+1;
        System.out.println("number------------->"+number);
        for (int i= 1; i<=number; i++) {
            if (i == number) {
                count = total;
            } else {
                count = count * i;
            }
            List<Panel> panelList = new ArrayList<>();
            List<PanelError> panelErrorList = new ArrayList<>();
            for (int j = z; j < count; j++) {
                try {
                    Panel panel = new Panel();
                    panel.setCaseId(caseInfo.getId());
                    Map<String, String> map = contentList.get(j);

                    //将数据返回到实体中
                    BeanRefUtil.setFieldValue(panel, map);
                    //过滤错误号码  异常类别 :1表示号码异常,2表示文件异常
                    if (!CommonUtil.isEmptyStr(panel.getPhone1()) && !isMobile(panel.getPhone1())) {
                        PanelError panelError = panelErrorInfo(Contants.HMYC, panel.getPhone1(), "");
                        panelErrorList.add(panelError);
                        panel.setPhone1("");
                    }
                    if (!CommonUtil.isEmptyStr(panel.getPhone2()) && !isMobile(panel.getPhone2())) {
                        PanelError panelError = panelErrorInfo(Contants.HMYC, panel.getPhone2(), "");
                        panelErrorList.add(panelError);
                        panel.setPhone2("");
                    }
                    if (!CommonUtil.isEmptyStr(panel.getPhone3()) && !isMobile(panel.getPhone3())) {
                        PanelError panelError = panelErrorInfo(Contants.HMYC, panel.getPhone3(), "");
                        panelErrorList.add(panelError);
                        panel.setPhone3("");
                    }
                    if (!CommonUtil.isEmptyStr(panel.getPhone4()) && !isMobile(panel.getPhone4())) {
                        PanelError panelError = panelErrorInfo(Contants.HMYC, panel.getPhone4(), "");
                        panelErrorList.add(panelError);
                        panel.setPhone4("");
                    }
                    if (!CommonUtil.isEmptyStr(panel.getPhone5()) && !isMobile(panel.getPhone5())) {
                        PanelError panelError = panelErrorInfo(Contants.HMYC, panel.getPhone5(), "");
                        panelErrorList.add(panelError);
                        panel.setPhone5("");
                    }
                    if (!CommonUtil.isEmptyStr(panel.getPhone6()) && !isMobile(panel.getPhone6())) {
                        PanelError panelError = panelErrorInfo(Contants.HMYC, panel.getPhone6(), "");
                        panelErrorList.add(panelError);
                        panel.setPhone6("");
                    }
                    panel.setStatus("1");
                    panel.setDeleteFlag("N");
                    //放入到list中去
                    panelList.add(panel);
                    //成功数量+1
                    successCount++;
                } catch (Exception e) {
                    String errorType = e.toString().split(":")[0];
                    String errorInfo = "";
                    StackTraceElement[] trace = e.getStackTrace();
                    for (StackTraceElement s : trace) {
                        errorInfo += "\tat" + s + "\r\n";
                    }
                    PanelError panelError = panelErrorInfo(Contants.SJYC, errorType, errorInfo);
                    //错误行数
                    String line = String.valueOf(j);
                    panelError.setErrorLine(Long.parseLong(line));
                    panelErrorList.add(panelError);

                    continue;
                }
            }
            //批量插入名单数据
            panelMapper.insertPanels(panelList);
            //批量插入异常数据
            if (!CommonUtil.isEmpityCollection(panelErrorList)) {
                panelErrorMapper.insertPanelErrors(panelErrorList);
            }
            System.out.println("新增第" + i + "个2000条数据");
            z = count;
        }
        //修改名单案件的状态
        caseInfo.setUpdateId(10000L);
        caseInfo.setUpdateTime(new Date());
        caseInfo.setStatus(Contants.YSC);
        caseInfo.setSuccessCount(Long.parseLong(String.valueOf(successCount)));
        caseInfoMapper.updateById(caseInfo);
    }

    /**
     * 组装panelError对象
     * @param abnormalType
     * @param errorType
     * @param errorInfo
     * @return
     */
    public PanelError panelErrorInfo(String abnormalType, String errorType, String errorInfo) {
        PanelError panelError = new PanelError();
        panelError.setId(IdWorker.getId());
        panelError.setCaseId(caseInfo.getId());
        panelError.setAbnormalType(abnormalType);
        panelError.setErrorType(errorType);
        panelError.setErrorInfo(errorInfo);

        return panelError;
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        if (mobile.startsWith("0")) {
            mobile = mobile.substring(1, mobile.length());
        }
        String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

}
