package com.sinosoft.aod.feed.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sinosoft.aod.feed.model.AodFeed;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AodFeedMapper extends BaseMapper<AodFeed> {

}
