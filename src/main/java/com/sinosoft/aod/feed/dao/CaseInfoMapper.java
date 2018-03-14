package com.sinosoft.aod.feed.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sinosoft.aod.feed.model.CaseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

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
public interface CaseInfoMapper extends BaseMapper<CaseInfo> {

}