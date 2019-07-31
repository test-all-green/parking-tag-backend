package cn.tag.controller;

import cn.tag.entity.ShareParkingLotLocation;
import cn.tag.service.ShareParkingLotLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/share-parking-lot-locations")
public class ShareParkingLotLocationController {

    @Autowired
    private ShareParkingLotLocationService shareParkingLotLocationService;

    @GetMapping
    public ResponseEntity findAll(){
        List<ShareParkingLotLocation> lotLocations = shareParkingLotLocationService.findAll();
        return ResponseEntity.ok(lotLocations);
    }
}
