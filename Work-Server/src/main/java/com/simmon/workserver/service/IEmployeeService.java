package com.simmon.workserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simmon.workserver.domain.Employee;
import com.simmon.workserver.domain.RespBean;
import com.simmon.workserver.domain.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Simmon
 * @since 2021-11-21
 */
public interface IEmployeeService extends IService<Employee> {
    RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    RespBean maxWorkID();

    RespBean addEmp(Employee employee);

    List<Employee> getEmployee(Integer id);

    RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size);
}
