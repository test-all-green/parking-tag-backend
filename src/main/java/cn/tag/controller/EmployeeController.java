package cn.tag.controller;

import cn.tag.Interceptor.*;
//import cn.tag.Interceptor.UserLoginToken;
import cn.tag.entity.Employee;
import cn.tag.Interceptor.AuthToken;
import cn.tag.service.EmployeeService;
import cn.tag.service.TokenService;
import cn.tag.util.SHA1;
import cn.tag.util.TokenUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {

    private static final String LOGIN_ERROR_STAFF_NOT_EXIT = "登录失败,用户不存在";
    private static final String LOGIN_ERROR_PASSWORD_ERROR = "登录失败,密码错误";
    public static final String MESSAGE = "message";
    public static final String LOGIN_METHOD = "loginMethod";
    public static final String TOKEN = "token";
    public static final String STAFF_PASSWORD = "staffPassword";
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/login")
    public Object login(@RequestBody Map<String,Object> params,
                        HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        Employee userForBase = new Employee();
        Employee parkingStaffByStaffEmail = employeeService.findParkingStaffByStaffEmail(params.get(LOGIN_METHOD).toString());
        if (parkingStaffByStaffEmail == null) {
            jsonObject.put(MESSAGE, LOGIN_ERROR_STAFF_NOT_EXIT);
            return jsonObject;
        }
        setEmployee(userForBase, parkingStaffByStaffEmail);
        if (!userForBase.getEmployeePassword().equals(SHA1.encode(params.get(STAFF_PASSWORD).toString()))) {
            jsonObject.put(MESSAGE, LOGIN_ERROR_PASSWORD_ERROR);
            return jsonObject;
        }
        return getObjectWhenPasswordEquals(response, jsonObject, userForBase);
    }

    private void setEmployee(Employee userForBase, Employee employeeEmail) {
        userForBase.setId(employeeEmail.getId());
        userForBase.setEmail(employeeEmail.getEmail());
        userForBase.setEmployeePassword(employeeEmail.getEmployeePassword());
        userForBase.setRoleId(employeeEmail.getRoleId());
    }

    private Object getObjectWhenPasswordEquals(HttpServletResponse response, JSONObject jsonObject, Employee userForBase) {
        String token = tokenService.getToken(userForBase);
        jsonObject.put(TOKEN, token);
        Cookie cookie = new Cookie(TOKEN, token);
        cookie.setPath("/");
        response.addCookie(cookie);
        return jsonObject;
    }

    /***
     * 这个请求需要验证token才能访问
     */
    @AuthToken(role_name = "1")
    @UserLoginToken
    //@Apio(value = "获取信息", notes = "获取信息")
    @GetMapping(value = "/getMessage")
    public String getMessage() {
        // 取出token中带的用户id 进行操作
        System.out.println(TokenUtil.getTokenUserId());
        return "您已通过验证";
    }

    @PostMapping
    public ResponseEntity createAccount(@RequestBody Employee employee) {
        employeeService.createAccount(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity updateAccount(@PathVariable Integer id,@RequestBody Employee employee) {
        employeeService.updateAccount(id,employee);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity queryEmployeeList(@RequestParam Integer page, @RequestParam Integer pageSize) {
        Page<Employee> parkingStaffList = employeeService.queryEmployeeList(page, pageSize);
        return ResponseEntity.ok().body(parkingStaffList);
    }

}
