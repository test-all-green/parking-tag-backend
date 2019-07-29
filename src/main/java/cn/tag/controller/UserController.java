package cn.tag.controller;

import cn.tag.Interceptor.EmployeeToken;
import cn.tag.Interceptor.UserLoginToken;
import cn.tag.entity.User;
import cn.tag.service.TokenService;
import cn.tag.service.UserService;
import cn.tag.util.SHA1;
import cn.tag.util.TokenUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private static final String LOGIN_ERROR_STAFF_NOT_EXIT = "登录失败,用户不存在";
    private static final String LOGIN_ERROR_PASSWORD_ERROR = "登录失败,密码错误";
    public static final String MESSAGE = "message";
    public static final String LOGIN_METHOD = "loginMethod";
    public static final String TOKEN = "token";
    public static final String STAFF_PASSWORD = "staffPassword";
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody User user){
        userService.register(user);
        return ResponseEntity.ok(getRegisterSuccessMap());
    }
    public Map getRegisterSuccessMap(){
        Map map = new HashMap();
        map.put("code","200");
        map.put("success","恭喜你注册成功");
        return map;
    }
    @PostMapping(value = "/login")
    public Object login(@RequestBody Map<String,Object> params,
                        HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        User userForBase = new User();
        User parkingStaffByStaffEmail = userService.findParkingStaffByStaffEmail(params.get(LOGIN_METHOD).toString());
        if (parkingStaffByStaffEmail == null) {
            jsonObject.put(MESSAGE, LOGIN_ERROR_STAFF_NOT_EXIT);
            return jsonObject;
        }
        setUser(userForBase, parkingStaffByStaffEmail);
        if (!userForBase.getUserPassword().equals(SHA1.encode(params.get(STAFF_PASSWORD).toString()))) {
            jsonObject.put(MESSAGE, LOGIN_ERROR_PASSWORD_ERROR);
            return jsonObject;
        }
        return getObjectWhenPasswordEquals(response, jsonObject, userForBase);
    }

    private void setUser(User userForBase, User employeeEmail) {
        userForBase.setId(employeeEmail.getId());
        userForBase.setEmail(employeeEmail.getEmail());
        userForBase.setUserPassword(employeeEmail.getUserPassword());
    }

    private Object getObjectWhenPasswordEquals(HttpServletResponse response, JSONObject jsonObject, User userForBase) {
        String token = tokenService.getToken(userForBase);
        jsonObject.put(TOKEN, token);
        Cookie cookie = new Cookie(TOKEN, token);
        cookie.setPath("/");
        response.addCookie(cookie);
        return jsonObject;
    }

    @EmployeeToken
    //@Apio(value = "获取信息", notes = "获取信息")
    @GetMapping(value = "/getMessage")
    public String getMessage() {
        // 取出token中带的用户id 进行操作
        System.out.println(TokenUtil.getTokenUserId());
        return "您已通过验证";
    }
}
