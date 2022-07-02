package com.simmon.workserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simmon.workserver.domain.Oplog;
import com.simmon.workserver.mapper.OplogMapper;
import com.simmon.workserver.service.IOplogService;
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
public class OplogServiceImpl extends ServiceImpl<OplogMapper, Oplog> implements IOplogService {

}
