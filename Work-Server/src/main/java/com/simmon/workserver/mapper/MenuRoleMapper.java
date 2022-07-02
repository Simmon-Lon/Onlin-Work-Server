package com.simmon.workserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simmon.workserver.domain.MenuRole;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Simmon
 * @since 2021-11-21
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
