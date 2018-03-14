package com.sinosoft.aod.feed.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sinosoft.aod.feed.dao.PanelErrorMapper;
import com.sinosoft.aod.feed.model.PanelError;
import com.sinosoft.aod.feed.service.IPanelErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  案件上传失败服务实现类
 * @author LongLei
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PanelErrorServiceImpl extends ServiceImpl<PanelErrorMapper, PanelError> implements IPanelErrorService {

}
