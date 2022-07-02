package com.simmon.workserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.simmon.workserver.domain.SysMsg;
import com.simmon.workserver.mapper.SysMsgMapper;
import com.simmon.workserver.service.ISysMsgService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Simmon
 * @since 2021-11-21
 */
@Service
public class SysMsgServiceImpl extends ServiceImpl<SysMsgMapper, SysMsg> implements ISysMsgService {

}
