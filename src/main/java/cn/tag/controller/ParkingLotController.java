package cn.tag.controller;

import cn.tag.entity.ParkingLot;
import cn.tag.service.ParkingLotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@CrossOrigin
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
        return ResponseEntity.status(HttpStatus.CREATED).body(add);
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity findByPage(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        Page<ParkingLot> parkingLotPage = parkingLotService.findByPage(page, pageSize);
        return ResponseEntity.ok().body(parkingLotPage);
    }

    @PatchMapping("/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody ParkingLot parkingLot){
        ParkingLot update = parkingLotService.update(id, parkingLot);
        return ResponseEntity.ok().body(update);
    }

}
