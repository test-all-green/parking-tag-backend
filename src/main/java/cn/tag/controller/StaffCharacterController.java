package cn.tag.controller;

import cn.tag.entity.StaffCharacter;
import cn.tag.service.StaffCharacterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/staff-characters")
@Slf4j
public class StaffCharacterController {

    @Autowired
    StaffCharacterService staffCharacterService;

    @GetMapping
    public ResponseEntity queryStaffCharacterList(){
        System.out.println("------------------");
        return ResponseEntity.ok().body(staffCharacterService.queryStaffCharacterList());
    }
}
