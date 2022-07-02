package com.simmon.workserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simmon.workserver.domain.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Simmon
 * @since 2021-11-21
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getMenusByAdminId();

    List<Menu> getMenusWithRole();

    List<Menu> getAllMenus();
}
