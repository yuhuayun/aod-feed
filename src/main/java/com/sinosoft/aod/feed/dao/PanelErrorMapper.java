package com.sinosoft.aod.feed.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sinosoft.aod.feed.model.PanelError;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LongLei
 * @date   ${datetime}
 */
@Mapper
@Component
public interface PanelErrorMapper extends BaseMapper<PanelError> {

    int insertPanelErrors(List<PanelError> panelErrorList);
}