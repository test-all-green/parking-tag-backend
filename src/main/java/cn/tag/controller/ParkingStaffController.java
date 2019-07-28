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
import java.util.List;

@RestController
@RequestMapping("/parking-staffs")
@Slf4j
public class ParkingStaffController {

    public static final String LOGIN_ERROR_STAFF_NOT_EXIT = "登录失败,用户不存在";
    public static final String LOGIN_ERROR_PASSWORD_ERROR = "登录失败,密码错误";
    @Autowired
    ParkingStaffService parkingStaffService;
    @Autowired
    TokenService tokenService;

    // 登录
    @RequestMapping(value = "/login" ,method = RequestMethod.GET)
    public Object login(ParkingStaff user, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        ParkingStaff userForBase = new ParkingStaff();
        ParkingStaff parkingStaffByStaffEmail = new ParkingStaff();
        parkingStaffByStaffEmail = parkingStaffService.findParkingStaffByStaffEmail(user);
        if(parkingStaffByStaffEmail==null) {
            jsonObject.put("message", LOGIN_ERROR_STAFF_NOT_EXIT);
            return jsonObject;
        }
        userForBase.setId(parkingStaffByStaffEmail.getId());
        userForBase.setStaffEmail(parkingStaffByStaffEmail.getStaffEmail());
        userForBase.setStaffPassword(parkingStaffByStaffEmail.getStaffPassword());
        if (!userForBase.getStaffPassword().equals(user.getStaffPassword())) {
            jsonObject.put("message", LOGIN_ERROR_PASSWORD_ERROR);
            return jsonObject;
        } else {
            String token = tokenService.getToken(userForBase);
            jsonObject.put("token", token);

            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);

            return jsonObject;

        }
    }
    /***
     * 这个请求需要验证token才能访问
     */
    @UserLoginToken
    //@Apio(value = "获取信息", notes = "获取信息")
    @GetMapping(value = "/getMessage")
    public String getMessage() {
        // 取出token中带的用户id 进行操作

        return "您已通过验证";
    }
    @PostMapping
    public ResponseEntity createAccount(@RequestBody ParkingStaff parkingStaff){
        parkingStaffService.createAccount(parkingStaff);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity queryParkingStaffListByPageNumAndPageSize(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        Page<ParkingStaff> parkingStaffList = parkingStaffService.queryParkingStaffList(pageNum,pageSize);
        return ResponseEntity.ok().body(parkingStaffList);
    }

}
