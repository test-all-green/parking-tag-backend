package cn.tag.service;

import cn.tag.entity.Employee;
import cn.tag.enums.WorkStatusEnum;
import cn.tag.respository.EmployeeRepository;
import cn.tag.respository.RoleRepository;
import cn.tag.util.SHA1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    public void createAccount(Employee employee) {
        String staffPassword= SHA1.encode("123456");
//        employee.setEmployeeId("EM"+(int)Math.random()*100000);
        employee.setEmployeePassword(staffPassword);
        employee.setStatus(0);
        employee.setWorkStatus(WorkStatusEnum.IDLE.getKey());
        employeeRepository.save(employee);
    }

    public Page<Employee> queryEmployeeList(Integer pageNum, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(pageNum-1,pageSize));
    }

    public Employee findParkingStaffByStaffEmail(String loginMethod){
        return employeeRepository.findEmployeeByEmail(loginMethod);
    }
    public Employee findUserById(Integer userId) {
        return employeeRepository.findById(userId).get();
    }

    public void updateAccount(Integer id, Employee employee) {

        employee.setId(id);
        employeeRepository.save(employee);
    }

    public Employee register(Employee employee) {
        employee.setEmployeePassword(SHA1.encode(employee.getEmployeePassword()));
        return employeeRepository.save(employee);
    }
}
