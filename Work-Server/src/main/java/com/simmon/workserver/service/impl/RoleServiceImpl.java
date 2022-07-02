package com.simmon.workserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simmon.workserver.domain.Role;
import com.simmon.workserver.mapper.RoleMapper;
import com.simmon.workserver.service.IRoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
