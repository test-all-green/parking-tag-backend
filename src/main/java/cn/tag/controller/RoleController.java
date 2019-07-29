package cn.tag.controller;


import cn.tag.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/roles")
@Slf4j
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping
    public ResponseEntity queryCharacterList(){
        return ResponseEntity.ok().body(roleService.queryCharacterList());
    }
}
