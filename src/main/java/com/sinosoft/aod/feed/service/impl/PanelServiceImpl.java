package com.sinosoft.aod.feed.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sinosoft.aod.feed.Contants;
import com.sinosoft.aod.feed.dao.PanelMapper;
import com.sinosoft.aod.feed.model.AspectPanel;
import com.sinosoft.aod.feed.model.Panel;
import com.sinosoft.aod.feed.msg.Response;
import com.sinosoft.aod.feed.service.IPanelService;
import com.sinosoft.aod.feed.utils.CommonUtil;
import com.sinosoft.aod.feed.utils.FileUpload;
import com.sinosoft.aod.feed.utils.PathUtil;
import com.sinosoft.aod.feed.utils.ReadExcellUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * 名单服务实现类
 *
 * @author chenbing
 * @date ${datetime}
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PanelServiceImpl extends ServiceImpl<PanelMapper, Panel> implements IPanelService {

    private final PanelMapper panelMapper;

    /**
     * 导入名单到aspect接口
     *
     * @param serviceId  服务ID
     * @param feednumber 导入条数
     * @param panelIds   指定导入名单ID，用","隔开
     * @return
     */
    @Override
    public List<AspectPanel> uploadPanel(String serviceId, Integer feednumber, String panelIds) {
        List<AspectPanel> aspectPanelList = new ArrayList<>();

        Panel panel = new Panel();
        panel.setServiceId(serviceId);
        panel.setFeednumber(feednumber);
        if (!CommonUtil.isEmptyStr(panelIds)) {
            String[] strs = panelIds.split(",");
            List list = Arrays.asList(strs);
            panel.setPanelIds(list);
        }
        List<Panel> panelList = panelMapper.uploadPanels(panel);
        if (!CommonUtil.isEmpityCollection(panelList)) {
            for (Panel panel1 : panelList) {
                AspectPanel aspectPanel = new AspectPanel();
                aspectPanel.setPanelId(panel1.getId());
                aspectPanel.setServiceId(panel1.getServiceId());
                aspectPanel.setPriority(panel1.getPriority());
                aspectPanel.setPhone_1(panel1.getPhone1());
                aspectPanel.setPhone_2(panel1.getPhone2());
                aspectPanel.setPhone_3(panel1.getPhone3());
                aspectPanel.setPhone_4(panel1.getPhone4());
                aspectPanel.setPhone_5(panel1.getPhone5());
                aspectPanel.setPhone_6(panel1.getPhone6());
                aspectPanel.setUserdata_1(panel1.getReserve16());
                aspectPanel.setUserdata_2(panel1.getReserve17());
                aspectPanel.setUserdata_3(panel1.getReserve18());
                aspectPanel.setUserdata_4(panel1.getReserve19());
                aspectPanel.setUserdata_5(panel1.getReserve20());
                aspectPanel.setUserdata_6(panel1.getReserve21());
                aspectPanel.setUserdata_7(panel1.getReserve22());
                aspectPanel.setUserdata_8(panel1.getReserve23());
                aspectPanel.setUserdata_9(panel1.getReserve24());
                aspectPanel.setUserdata_10(panel1.getReserve25());
                aspectPanel.setUserdata_11(panel1.getReserve25());
                aspectPanel.setUserdata_12(panel1.getReserve27());
                aspectPanel.setUserdata_13(panel1.getReserve28());
                aspectPanel.setUserdata_14(panel1.getReserve29());
                aspectPanel.setUserdata_15(panel1.getReserve30());
                aspectPanel.setUserdata_16(panel1.getReserve31());
                aspectPanel.setUserdata_17(panel1.getReserve32());
                aspectPanel.setUserdata_18(panel1.getReserve33());
                aspectPanel.setUserdata_19(panel1.getReserve34());
                aspectPanel.setUserdata_20(panel1.getReserve35());
                aspectPanelList.add(aspectPanel);

                //修改名单状态
                Panel currentPanel = new Panel();
                currentPanel.setId(panel1.getId());
                currentPanel.setStatus("2");
                this.updateById(currentPanel);
            }
        }
        return aspectPanelList;
    }


    /**
     * 根据单个号码剔除名单
     *
     * @param phone 电话号码
     * @param file  上传的文件
     * @return 返回处理结果及处理结果信息
     */
    @Override
    public Response deletePanel(String phone, MultipartFile file) {
        Panel panel = new Panel();
        if (phone != null && !"".equals(phone)) {
            panel.setPhone(phone);
            deletePhone(panel);
        } else if (file != null && !"".equals(file)) {

            //文件上传路径
            String filePath = PathUtil.getClasspath() + Contants.FILEPATHFILE;
            //随机生成上传的文件名，防止重复
            String uploadFileName = UUID.randomUUID().toString();
            panel.setFilePath(filePath);
            String fileName = FileUpload.fileUp(file, filePath, uploadFileName);
            panel.setFileName(fileName);
            List phoneList = ReadExcellUtil.readExcel(filePath, fileName);
            for (Object item : phoneList) {
                String tel = item.toString();
                if (tel != null && !"".equals(tel)) {
                    panel.setPhone(tel);
                    log.info("电话号码:" + tel);
                    deletePhone(panel);
                }
            }
        }

        return new Response().success();
    }

    /**
     * 逻辑删除号码
     *
     * @param panel 数据对象
     */
    private void deletePhone(Panel panel) {
        panelMapper.deleteByPhone(panel);
    }
}
