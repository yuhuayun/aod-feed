package com.sinosoft.aod.feed.controller;

import com.sinosoft.aod.feed.model.AodFeed;
import com.sinosoft.aod.feed.model.Panel;
import com.sinosoft.aod.feed.service.IAodFeedService;
import com.sinosoft.aod.feed.service.IPanelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AodFeed控制器
 *
 * @author chenbing
 * @date ${datetime}
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("aodFeed")
public class AodFeedController extends BaseController<IPanelService, Panel> {

    private final IAodFeedService aodFeedService;
    /**
     * 获取aodFeed服务
     *
     * @param aodFeed 用于请求AODFeed服务时传递数据
     * @return 接收服务结果
     */
    @GetMapping("/doGetAodFeed")
    public String doGetAodFeed(@RequestBody AodFeed aodFeed) {
        return aodFeedService.doGetAodFeed(aodFeed);
    }


}
