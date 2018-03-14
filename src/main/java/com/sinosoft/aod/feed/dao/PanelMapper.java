package com.sinosoft.aod.feed.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sinosoft.aod.feed.model.Panel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author LongLei
 * @date ${datetime}
 */
@Mapper
@Component
public interface PanelMapper extends BaseMapper<Panel> {
    //插入名单
    int insertPanels(List<Panel> panelList);

    //上传名单
    List<Panel> uploadPanels(@Param("panel") Panel panel);

    /**
     * 单个剔除名单
     *
     * @param panel
     */
    void deleteByPhone(@Param("panel") Panel panel);
}