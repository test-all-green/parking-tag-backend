package cn.tag.controller;

import cn.tag.service.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/regions")
@Slf4j
public class RegionController {

    @Autowired
    RegionService regionService;

    @GetMapping
    public ResponseEntity queryRegionList(){
        return ResponseEntity.ok().body(regionService.queryRegionList());
    }

}
