package cn.tag.controller;

import cn.tag.entity.ParkingStaff;
import cn.tag.service.ParkingStaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-staffs")
@Slf4j
public class ParkingStaffController {

    @Autowired
    ParkingStaffService parkingStaffService;

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
