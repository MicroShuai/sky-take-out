package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.vo.EmployeeLoginVO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EmployeeMapper {

    @Select("SELECT * from employee where username = #{username} and password = #{password}")
    Employee queryEmployeeByUsernameAndPassword(String username, String password);

    Employee getByUsername(String username);

    Page<Employee> queryPageEmployee(String name);

    void updatePassword(PasswordEditDTO passwordEditDTO);

    @Insert("INSERT INTO employee values (null,#{name},#{username},#{password},#{phone},#{sex},#{idNumber},1,#{createTime},#{updateTime},0,0 )")
    void addEmployee(Employee employee);

    @Select("select * from employee where id = #{id}")
    Employee getEmployeesById(Long id);

    void updateEmployeeInfo(Employee employee);


    @Update("update employee set status = #{status} where id = #{id}")
    void updateEmployeeStatus(String status,Long id );
}
