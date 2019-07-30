package cn.tag.controller;

import cn.tag.Interceptor.EmployeeToken;
import cn.tag.entity.ShareParkingLot;
import cn.tag.service.ShareParkingLotService;
import cn.tag.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/share-parking-lots")
public class ShareParkingLotController {

    @Autowired
    private ShareParkingLotService shareParkingLotService;

    @GetMapping
    @EmployeeToken
    public ResponseEntity findByUserId(){
        Integer tokenUserId = Integer.valueOf(TokenUtil.getTokenUserId());
        List<ShareParkingLot> shareParkingLots = shareParkingLotService.findByUserId(tokenUserId);
        return ResponseEntity.ok().body(shareParkingLots);
    }

    /**
     * 新增一条未发布的shareParkingLot
     *
     * @param shareParkingLot
     * @return
     */
    @PostMapping
    @EmployeeToken
    public ResponseEntity add(@RequestBody ShareParkingLot shareParkingLot){
        Integer tokenUserId = Integer.valueOf(TokenUtil.getTokenUserId());
        shareParkingLot.setUserId(tokenUserId);
        ShareParkingLot shareParkingLotAdd = shareParkingLotService.add(shareParkingLot);
        return ResponseEntity.status(HttpStatus.CREATED).body(shareParkingLotAdd);
    }

    /**
     * 发布共享车位
     *
     * @param id
     * @param shareParkingLot 包括共享开始时间和结束时间
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity publishShareParkingLot(@PathVariable Integer id, @RequestBody ShareParkingLot shareParkingLot){
        ShareParkingLot publish = shareParkingLotService.publish(id, shareParkingLot);
        return ResponseEntity.ok().body(publish);
    }



}