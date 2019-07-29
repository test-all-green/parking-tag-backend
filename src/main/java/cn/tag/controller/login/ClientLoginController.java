package cn.tag.controller.login;

import cn.tag.entity.Employee;
import cn.tag.exception.LoginException;
import cn.tag.service.login.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class ClientLoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("staff/login")
    public ResponseEntity login(@RequestBody Employee user) {
        if (loginService.authenticate(user)) {
            Map responseBody = new HashMap();
            responseBody.put("message", "login success");
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } else {
            throw new LoginException("Please input correct username or password");
        }
    }
}

