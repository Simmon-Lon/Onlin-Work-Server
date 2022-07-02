package com.simmon.workserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simmon.workserver.domain.Department;
import com.simmon.workserver.domain.RespBean;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Simmon
 * @since 2021-11-21
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * <p>获取所有部门</p>
     * @return Department for list
     */
    List<Department> getAllDepartments(Integer parentId);

    void addDep(Department department);

    void deleteDep(Department department);
}
