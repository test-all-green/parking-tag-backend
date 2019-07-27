package cn.tag.controller;

import cn.tag.entity.ParkingLot;
import cn.tag.service.ParkingLotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-lots")
@Slf4j
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping
    public ResponseEntity findAll(){
        List<ParkingLot> parkingLots = parkingLotService.findAll();
//        return ResponseEntity.ok().body(parkingLots);
        return ResponseEntity.ok(parkingLots);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody ParkingLot parkingLot){
        ParkingLot add = parkingLotService.add(parkingLot);
        log.info("==========================="+add.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(add);

    }
}
