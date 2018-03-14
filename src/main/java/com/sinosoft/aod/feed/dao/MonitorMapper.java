package com.sinosoft.aod.feed.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sinosoft.aod.feed.model.Monitor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MonitorMapper extends BaseMapper<Monitor> {
    //插入监控数据
    List<Monitor> queryMonitorData();

}