package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.exception.LoginFailedException;
import com.sky.exception.PasswordEditFailedException;
import com.sky.exception.PasswordErrorException;
import com.sky.exception.UsernameExisted;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageBean;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    public final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    @Override
    public void updatePassword(PasswordEditDTO passwordEditDTO) {
        String oldPassword = passwordEditDTO.getOldPassword();
        String nowPassword = employeeMapper.getEmployeesById(passwordEditDTO.getEmpId()).getPassword();

        if (oldPassword == null || oldPassword.isEmpty()) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (!Objects.equals(oldPassword, nowPassword)) {
            throw new PasswordEditFailedException(MessageConstant.PASSWORD_EDIT_FAILED + "," + MessageConstant.PASSWORD_ERROR);
        }

        employeeMapper.updatePassword(passwordEditDTO);

    }

    @Override
    public EmployeeLoginVO login(EmployeeLoginDTO employeeLoginDTO) {
        //接受前端用户名和密码

        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();


        //mapper获取employee 对象
      // Employee employee = employeeMapper.queryEmployeeByUsernameAndPassword(username, password);
        Employee employee = employeeMapper.getByUsername(username);

        //获取属性
        Long id = employee.getId();
        String name = employee.getName();
        String employeeUsername = employee.getUsername();
        String employeePassword = employee.getPassword();
      //  log.error(employeePassword);

        if (id == null) {
            throw new LoginFailedException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
       boolean checkpw = BCrypt.checkpw(password, employeePassword);
        //log.error(String.valueOf(checkpw));
        if (!checkpw) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        return EmployeeLoginVO.builder().
                name(name).
                userName(employeeUsername).
                id(id).
                token(null).
                build();
    }

    @Override
    public PageBean queryPageEmployee(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());

        Page<Employee> employee = employeeMapper.queryPageEmployee(employeePageQueryDTO.getName());

        return new PageBean(employee.getTotal(), employee.getResult());
    }

    @Override
    public void addEmployee(Employee employee) {
//
//        Employee employee1 = employeeMapper.getByUsername(employee.getUsername());
//        if(employee1 != null){
//          throw new UsernameExisted("用户已存在");
//
//        }

        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder password = new StringBuilder();
//        Random random = new Random();
//
//        // 生成 6 位密码
        for (int i = 1; i <= 6; i++) {
          //  int index = random.nextInt(characters.length());
            password.append(i);
        }
        String hashpw = BCrypt.hashpw(password.toString(), BCrypt.gensalt());
        employee.setPassword(hashpw);

        employeeMapper.addEmployee(employee);
    }

    @Override
    public Employee getEmployeesById(Long id) {
        Employee employee = employeeMapper.getEmployeesById(id);
        employee.setPassword("********");
        return employee;
    }

    @Override
    public void updateEmployeeInfo(Employee employee) {
        employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.updateEmployeeInfo(employee);
    }

    @Override
    public void updateEmployeeStatus(String status,Long id) {
        employeeMapper.updateEmployeeStatus(status,id);
    }


}
