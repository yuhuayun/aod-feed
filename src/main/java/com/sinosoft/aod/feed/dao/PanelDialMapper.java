package com.sinosoft.aod.feed.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sinosoft.aod.feed.model.PanelDial;
import org.apache.ibatis.annotations.Mapper;
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
public interface PanelDialMapper extends BaseMapper<PanelDial> {
    // 插入外拨数据
    int insertPanelDials(List<PanelDial> panelDialList);

}