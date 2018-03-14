package com.sinosoft.aod.feed.service;


import com.baomidou.mybatisplus.service.IService;
import com.sinosoft.aod.feed.model.AodFeed;

/**
 * <p>
 * aodFeed接口类
 * </p>
 *
 * @author chenbing
 * @date ${datetime}
 */
public interface IAodFeedService extends IService<AodFeed> {

    /**
     * @param aodFeed 用于请求AODFeed服务时传递数据
     * @return 接收服务结果
     */
    public String doGetAodFeed(AodFeed aodFeed);
}