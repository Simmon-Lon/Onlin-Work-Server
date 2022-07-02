package com.simmon.workserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simmon.workserver.domain.Department;
import com.simmon.workserver.domain.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Simmon
 * @since 2021-11-21
 */
public interface IDepartmentService extends IService<Department> {

    List<Department> getAllDepartments();

    RespBean addDep(Department department);

    RespBean deleteDep(Integer id);
}
