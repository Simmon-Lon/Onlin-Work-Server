package com.simmon.workserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.simmon.workserver.domain.Department;
import com.simmon.workserver.domain.RespBean;
import com.simmon.workserver.mapper.DepartmentMapper;
import com.simmon.workserver.service.IDepartmentService;
import org.springframework.stereotype.Service;

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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDepartments() {

        return departmentMapper.getAllDepartments(-1);
    }

    @Override
    public RespBean addDep(Department department) {
        department.setEnabled(true);
        departmentMapper.addDep(department);
        if (1==department.getResult()){
            return RespBean.success("添加成功",department);
        }
        return RespBean.error("添加失败");
    }

    @Override
    public RespBean deleteDep(Integer id) {
        Department department=new Department();
        department.setId(id);
        departmentMapper.deleteDep(department);
        if (-2==department.getResult()){
            return RespBean.error("该部门下有子部门,删除失败");
        }
        else if(-1==department.getResult()){
            return RespBean.error("该部门下还有员工,删除失败");
        }
        else if (1==department.getResult()){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
