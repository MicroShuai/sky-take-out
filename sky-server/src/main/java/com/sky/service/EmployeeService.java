package com.sky.service;

import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.result.PageBean;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.models.auth.In;

public interface EmployeeService {

    void updatePassword(PasswordEditDTO passwordEditDTO);

    EmployeeLoginVO login(EmployeeLoginDTO employeeLoginDTO);

    PageBean queryPageEmployee(EmployeePageQueryDTO employeePageQueryDTO);

    void addEmployee(Employee employee);

    Employee getEmployeesById(Long id);

    void updateEmployeeInfo(Employee employee);

    void updateEmployeeStatus(String status,Long id);

}
