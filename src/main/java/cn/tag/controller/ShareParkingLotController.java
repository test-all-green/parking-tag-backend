package cn.tag.controller;

import cn.tag.entity.PublicParkingLot;
import cn.tag.entity.ShareParkingLot;
import cn.tag.service.ShareParkingLotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/share-parking-lots")
@Slf4j
public class ShareParkingLotController {
    @Autowired
    private ShareParkingLotService shareParkingLotService;

    @GetMapping
    public ResponseEntity findAll(){
        return ResponseEntity.ok(shareParkingLotService.findAll());
    }

    @PostMapping
    public ResponseEntity add(@RequestBody ShareParkingLot parkingLot){
        return ResponseEntity.status(HttpStatus.CREATED).body(shareParkingLotService.add(parkingLot));
    }

    @GetMapping(params = {"page","pageSize"})
    public ResponseEntity findByPage(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        Page<ShareParkingLot> parkingLotPage = shareParkingLotService.findByPage(page, pageSize);
        return ResponseEntity.ok(parkingLotPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Integer id,@RequestBody ShareParkingLot shareParkingLot){
        ShareParkingLot update=shareParkingLotService.update(id, shareParkingLot);
        return ResponseEntity.ok(update);
    }
}
