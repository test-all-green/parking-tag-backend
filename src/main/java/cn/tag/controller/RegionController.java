package cn.tag.controller;

import cn.tag.service.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable(name = "id") Integer id){
        return ResponseEntity.ok().body(regionService.findById(id));
    }

}
