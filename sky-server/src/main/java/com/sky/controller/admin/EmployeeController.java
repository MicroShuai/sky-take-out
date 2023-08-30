package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageBean;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Shuai
 */
@RestController
@RequestMapping("/admin/employee")
@Api(tags = "员工相关接口")
@Slf4j
public class EmployeeController {

    public final JwtProperties jwtProperties;
    public final EmployeeService employeeService;

    @Autowired
    public HttpServletRequest httpServletRequest;

    public EmployeeController(EmployeeService employeeService, JwtProperties jwtProperties) {
        this.employeeService = employeeService;
        this.jwtProperties = jwtProperties;
    }

    @PutMapping("/editPassword")
    public Result<T> updatePassword(@RequestBody PasswordEditDTO passwordEditDTO) {
        //获取 TODO 有待优化
        String token = httpServletRequest.getHeader("token");

        System.out.println(token);


        Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
        //toString
        Long empId =Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
        passwordEditDTO.setEmpId(empId);

        employeeService.updatePassword(passwordEditDTO);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        EmployeeLoginVO employeeLoginVO = employeeService.login(employeeLoginDTO);

        HashMap<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employeeLoginVO.getId());

        //生成jwt token 1. configuration 把 对象的字段 和 配置文件中对应的值绑定 2.@Bean
        String token = JwtUtil.createJWT(jwtProperties.getAdminSecretKey(), jwtProperties.getAdminTtl(), claims);

        employeeLoginVO.setToken(token);

        return Result.success(employeeLoginVO);
    }

    @GetMapping("/page")
    public Result<PageBean> queryPageEmployee(@ModelAttribute EmployeePageQueryDTO employeePageQueryDTO) {
        return Result.success(employeeService.queryPageEmployee(employeePageQueryDTO));
    }

    @PostMapping
    public Result<T> addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
        return Result.success();
    }

    //根据id查询员工
    @GetMapping("/{id}")
    public Result<Employee> getEmployeesById(@PathVariable Long id) {
        return Result.success(employeeService.getEmployeesById(id));
    }

    //编辑员工信息
    @PutMapping
    public Result<T> updateEmployeeInfo(@RequestBody Employee employee){
        employeeService.updateEmployeeInfo(employee);
        return Result.success();
    }
    //启用、禁用员工账号

    @PostMapping("/status/{status}")
    public Result<T> updateEmployeeStatus(@PathVariable String status,Long id){
        employeeService.updateEmployeeStatus(status,id);
        return Result.success();
    }

    @PostMapping("/logout")
    public Result<T> logout(){
        return Result.success();
    }

}
