package com.simmon.workserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simmon.workserver.domain.Admin;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Simmon
 * @since 2021-11-21
 */
public interface AdminMapper extends BaseMapper<Admin> {
    List<Admin>  getAllAdmins(Integer id, String keywords);
}
