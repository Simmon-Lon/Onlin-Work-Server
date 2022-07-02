package com.simmon.workserver.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simmon.workserver.domain.Employee;
import com.simmon.workserver.domain.EmployeeEc;
import com.simmon.workserver.domain.RespPageBean;
import com.simmon.workserver.mapper.EmployeeEcMapper;
import com.simmon.workserver.mapper.EmployeeMapper;
import com.simmon.workserver.service.IEmployeeEcService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Simmon
 * @since 2021-11-21
 */
@Service
public class EmployeeEcServiceImpl extends ServiceImpl<EmployeeEcMapper, EmployeeEc> implements IEmployeeEcService {
}
