package cn.tag.service.login;

import cn.tag.entity.Employee;
import cn.tag.exception.LoginException;
import cn.tag.respository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean authenticate(Employee inputUser) {
        Employee staffByName = employeeRepository.findByEmployeeName(inputUser.getEmployeeName());
        if (staffByName == null) {
            throw new LoginException("No exist User");
        }
        if (!staffByName.getEmployeePassword().equals(inputUser.getEmployeePassword())) {
            throw new LoginException("Password invalid");
        }
        return true;
    }
}
