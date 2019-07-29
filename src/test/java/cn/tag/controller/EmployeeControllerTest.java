package cn.tag.controller;


import cn.tag.entity.Role;
import cn.tag.entity.Employee;
import cn.tag.enums.EmployeeStatusEnum;
import cn.tag.enums.WorkStatusEnum;
import cn.tag.respository.EmployeeRepository;
import cn.tag.respository.RoleRepository;
import cn.tag.util.SHA1;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @BeforeEach
    public void before_test() {
        roleRepository.save(new Role("泊车仔"));
        roleRepository.save(new Role("经理"));
        Role role = roleRepository.findAll().get(0);
        employeeRepository.save(new Employee("EM532736","张三","635653@qq.com",
                SHA1.encode("123456"),"13514155874", WorkStatusEnum.IDLE.getKey(), EmployeeStatusEnum.NORMAL.getKey(),1));
        employeeRepository.save(new Employee("EM538774","李四","123456789@qq.com",
                SHA1.encode("123456"),"13515458746", WorkStatusEnum.IDLE.getKey(), EmployeeStatusEnum.NORMAL.getKey(),1));

    }

    @AfterEach
    public void after_test() {
        employeeRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void should_created_status_when_create_account() throws Exception {
        //given
        Role role = roleRepository.findAll().get(0);
        Employee employee = new Employee("EM532886","赵武","635654353@qq.com",
                SHA1.encode("123456"),"13514155875", WorkStatusEnum.IDLE.getKey(), EmployeeStatusEnum.NORMAL.getKey(),1);
        int currentSize = employeeRepository.findAll().size();
        //when
        ResultActions resultActions = this.mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8).content(JSON.toJSONString(employee)));
        //then
        resultActions.andExpect(status().isCreated());
        Assertions.assertEquals(employee.getEmployeeName(), employeeRepository.findAll().get(currentSize).getEmployeeName());
    }

    @Test
    public void should_return_parking_staff_list_when_get_by_pageNum_and_pageSize() throws Exception {
        //given
        List<Employee> employeeList = employeeRepository.findAll();
        //when
        ResultActions resultActions = this.mockMvc.perform(get("/employees")
                .param("page","1").param("pageSize","5"));
        //then
        resultActions.andExpect(status().isOk());
        JSONObject jsonObject = JSONObject.parseObject(resultActions.andReturn().getResponse().getContentAsString());
        List<Employee> currentParkingStaffList = JSONObject.parseArray(jsonObject.get("content").toString(), Employee.class);
        Assertions.assertEquals(employeeList.get(0).getEmployeeName(), currentParkingStaffList.get(0).getEmployeeName());
    }

    @Test
    public void should_return_ok_status_when_modify_staff() throws Exception {
        //given
        Employee employee = employeeRepository.findAll().get(0);
        Integer id = roleRepository.findAll().get(0).getId();
        employee.setRoleId(2);
        //when
        ResultActions resultActions = this.mockMvc.perform(put("/employees/{id}",employee.getId().toString())
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8).content(JSON.toJSONString(employee)));
        //then
        resultActions.andExpect(status().isOk());
        Assertions.assertNotEquals(id, employeeRepository.findById(employee.getId()).get().getId());
    }

}
