package com.simmon.workserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simmon.workserver.domain.Admin;
import com.simmon.workserver.domain.Menu;
import com.simmon.workserver.mapper.MenuMapper;
import com.simmon.workserver.service.IMenuService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Simmon
 * @since 2021-11-21
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public List<Menu> getMenusByAdminId() {
        Integer id = ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        List<Menu> list =(List<Menu>) valueOperations.get("menu_" + id);
        if (CollectionUtils.isEmpty(list)){
            list=menuMapper.getMenusByAdminId(id);
            valueOperations.set("menu_"+id,list);
        }
        return list;
    }

    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }
}
