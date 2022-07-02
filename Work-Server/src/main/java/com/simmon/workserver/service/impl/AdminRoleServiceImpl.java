package com.simmon.workserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simmon.workserver.domain.AdminRole;
import com.simmon.workserver.mapper.AdminRoleMapper;
import com.simmon.workserver.service.IAdminRoleService;
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
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements IAdminRoleService {

}
