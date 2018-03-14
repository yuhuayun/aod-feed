package com.sinosoft.aod.feed.service;

import com.baomidou.mybatisplus.service.IService;
import com.sinosoft.aod.feed.model.AspectPanel;
import com.sinosoft.aod.feed.model.Panel;
import com.sinosoft.aod.feed.msg.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 名单接口类
 * </p>
 *
 * @author chenbing
 * @date ${datetime}
 */
public interface IPanelService extends IService<Panel> {
    /**
     * @param serviceId  服务id
     * @param feednumber 上传数量
     * @param panelIds   重新上传的名单ID
     * @return 返回 名单list
     */
    public List<AspectPanel> uploadPanel(String serviceId, Integer feednumber, String panelIds);

    /**

     * @param phone  电话号码
     * @param file  上传的文件
     * @return 返回处理结果及处理结果信息
     */

    Response deletePanel(String phone, MultipartFile file);
}
