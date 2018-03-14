package com.sinosoft.aod.feed.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sinosoft.aod.feed.dao.AodFeedMapper;
import com.sinosoft.aod.feed.model.AodFeed;
import com.sinosoft.aod.feed.service.IAodFeedService;
import com.sinosoft.aod.feed.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * aodFeed 表数据服务层接口实现类
 *
 * @author chenbing
 * @date ${datetime}
 */
@Service
public class AodFeedImpl extends ServiceImpl<AodFeedMapper, AodFeed> implements IAodFeedService {

    /**
     * aspect平台地址
     */
    @Value("${serviceInfo.url}")
    private String url;

    /**
     * 实现获取aodFeed的方法
     *
     * @param aodFeed 用于请求AODFeed服务时传递数据
     * @return
     */
    @Override
    public String doGetAodFeed(AodFeed aodFeed) {

        String aodServiceUrl = url;
        String method = aodFeed.getAodMethod();  //调用的方法
        Map<String, String> paras = new HashMap<>();
        paras.put("aodmethod", aodFeed.getAodMethod());
        paras.put("key", aodFeed.getKey());
        if ("getserviceids".equals(method)) {//获取服务ID
            String result = HttpClientUtils.doGet(aodServiceUrl, paras, "UTF-8");
            return result;
        } else if ("startservice".equals(method)) { //开始外呼服务
            paras.put("serviceid", aodFeed.getServiceid());
            String result = HttpClientUtils.doGet(aodServiceUrl, paras, "UTF-8");
            return result;
        } else if ("stopservice".equals(method)) { //停止外呼
            paras.put("serviceid", aodFeed.getServiceid());
            paras.put("queueaction", aodFeed.getQueueaction());
            String result = HttpClientUtils.doGet(aodServiceUrl, paras, "UTF-8");
            return result;
        } else if ("addrecordtodial".equals(method)) {//增加一条数据到外拔表
            paras.put("serviceid", aodFeed.getServiceid());
            paras.put("recordid", aodFeed.getRecordid());
            paras.put("previewmode", aodFeed.getPreviewmode());
            paras.put("priority", aodFeed.getPriority());
            paras.put("phone1", aodFeed.getPhone1());
            paras.put("phone2", aodFeed.getPhone2());
            paras.put("phone3", aodFeed.getPhone3());
            paras.put("phone4", aodFeed.getPhone4());
            paras.put("phone5", aodFeed.getPhone5());
            paras.put("phone6", aodFeed.getPhone6());
            paras.put("userdata1", aodFeed.getUserdata1());
            paras.put("userdata2", aodFeed.getUserdata2());
            paras.put("userdata3", aodFeed.getUserdata3());
            paras.put("userdata4", aodFeed.getUserdata4());
            paras.put("userdata5", aodFeed.getUserdata5());
            paras.put("userdata6", aodFeed.getUserdata6());
            String s = HttpClientUtils.doGet(aodServiceUrl, paras, "utf-8");
            return s;
        } else if ("deleterecord".equals(method)) {//删除一条外拔表数据
            paras.put("serviceid", aodFeed.getServiceid());
            paras.put("recordid", aodFeed.getRecordid());
            paras.put("priority", aodFeed.getPriority());
            String result = HttpClientUtils.doGet(aodServiceUrl, paras, "utf-8");
            return result;
        } else if ("getqueuecount".equals(method)) {//获取服务中队列数量
            paras.put("serviceid", aodFeed.getServiceid());
            paras.put("priority", aodFeed.getPriority());
            String result = HttpClientUtils.doGet(aodServiceUrl, paras, "utf-8");
            return result;
        } else if ("getservicestatus".equals(method)) {//获取服务状态
            paras.put("serviceid", aodFeed.getServiceid());
            String result = HttpClientUtils.doGet(aodServiceUrl, paras, "utf-8");
            return result;
        }
        return null;
    }
}