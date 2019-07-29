package cn.tag.controller;

import cn.tag.Interceptor.EmployeeToken;
import cn.tag.Interceptor.UserLoginToken;
import cn.tag.entity.ParkingOrder;
import cn.tag.service.ParkingOrderService;
import cn.tag.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/parking-orders")
@Slf4j
public class ParkingOrderController {
    @Autowired
    private ParkingOrderService parkingOrderService;

    @GetMapping
    public ResponseEntity findAll(){
        return ResponseEntity.ok(parkingOrderService.findAll());
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity findByPage(@RequestParam(name = "page",defaultValue = "1") Integer page,
                                     @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok(parkingOrderService.findByPage(page,pageSize));
    }

    @EmployeeToken
    @PostMapping
    public ResponseEntity add(@RequestBody ParkingOrder parkingOrder){
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingOrderService.add(parkingOrder));
    }
    @EmployeeToken
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Integer id,@RequestBody ParkingOrder parkingOrder){
        return ResponseEntity.ok(parkingOrderService.update(id,parkingOrder));
    }

    @EmployeeToken
    @GetMapping("/history")
    public ResponseEntity findByUserId(){
        String tokenUserId = TokenUtil.getTokenUserId();
        return ResponseEntity.ok(parkingOrderService.findOrderOfUser(Integer.valueOf(tokenUserId)));
    }
}
