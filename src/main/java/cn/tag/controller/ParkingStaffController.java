package cn.tag.controller;

import cn.tag.Interceptor.UserLoginToken;
import cn.tag.entity.ParkingStaff;
import cn.tag.service.ParkingStaffService;
import cn.tag.service.TokenService;
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

@CrossOrigin
@RestController
@RequestMapping("/parking-staffs")
@Slf4j
public class ParkingStaffController {

    private static final String LOGIN_ERROR_STAFF_NOT_EXIT = "登录失败,用户不存在";
    private static final String LOGIN_ERROR_PASSWORD_ERROR = "登录失败,密码错误";
    public static final String MESSAGE = "message";
    public static final String LOGIN_METHOD = "loginMethod";
    public static final String TOKEN = "token";
    public static final String STAFF_PASSWORD = "staffPassword";
    @Autowired
    private ParkingStaffService parkingStaffService;
    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/login")
    public Object login(@RequestBody Map<String,Object> params,
                        HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        ParkingStaff userForBase = new ParkingStaff();
        ParkingStaff parkingStaffByStaffEmail = parkingStaffService.findParkingStaffByStaffEmail(params.get(LOGIN_METHOD).toString());
        if (parkingStaffByStaffEmail == null) {
            jsonObject.put(MESSAGE, LOGIN_ERROR_STAFF_NOT_EXIT);
            return jsonObject;
        }
        setParkingStaff(userForBase, parkingStaffByStaffEmail);
        if (!userForBase.getStaffPassword().equals(params.get(STAFF_PASSWORD).toString())) {
            jsonObject.put(MESSAGE, LOGIN_ERROR_PASSWORD_ERROR);
            return jsonObject;
        }
        return getObjectWhenPasswordEquals(response, jsonObject, userForBase);
    }

    private void setParkingStaff(ParkingStaff userForBase, ParkingStaff parkingStaffByStaffEmail) {
        userForBase.setId(parkingStaffByStaffEmail.getId());
        userForBase.setStaffEmail(parkingStaffByStaffEmail.getStaffEmail());
        userForBase.setStaffPassword(parkingStaffByStaffEmail.getStaffPassword());
    }

    private Object getObjectWhenPasswordEquals(HttpServletResponse response, JSONObject jsonObject, ParkingStaff userForBase) {
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
    @UserLoginToken
    //@Apio(value = "获取信息", notes = "获取信息")
    @GetMapping(value = "/getMessage")
    public String getMessage() {
        // 取出token中带的用户id 进行操作
        System.out.println(TokenUtil.getTokenUserId());
        return "您已通过验证";
    }

    @PostMapping
    public ResponseEntity createAccount(@RequestBody ParkingStaff parkingStaff) {
        parkingStaffService.createAccount(parkingStaff);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity queryParkingStaffListByPageNumAndPageSize(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Page<ParkingStaff> parkingStaffList = parkingStaffService.queryParkingStaffList(pageNum, pageSize);
        return ResponseEntity.ok().body(parkingStaffList);
    }

}
