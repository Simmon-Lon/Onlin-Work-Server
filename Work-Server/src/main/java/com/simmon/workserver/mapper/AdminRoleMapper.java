package com.simmon.workserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simmon.workserver.domain.AdminRole;
import com.simmon.workserver.domain.RespBean;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Simmon
 * @since 2021-11-21
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    Integer addAdminRoles(@Param("adminId")Integer adminId,@Param("rids") Integer[] rids);
}
