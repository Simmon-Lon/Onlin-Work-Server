package com.simmon.workserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.simmon.workserver.domain.PoliticsStatus;
import com.simmon.workserver.mapper.PoliticsStatusMapper;
import com.simmon.workserver.service.IPoliticsStatusService;
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
public class PoliticsStatusServiceImpl extends ServiceImpl<PoliticsStatusMapper, PoliticsStatus> implements IPoliticsStatusService {

}
