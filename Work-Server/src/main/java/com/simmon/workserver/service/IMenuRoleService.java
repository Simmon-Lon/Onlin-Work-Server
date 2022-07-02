package com.simmon.workserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simmon.workserver.domain.MenuRole;
import com.simmon.workserver.domain.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Simmon
 * @since 2021-11-21
 */
public interface IMenuRoleService extends IService<MenuRole> {

    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
