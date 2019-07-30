package cn.tag.controller;

import cn.tag.Interceptor.EmployeeToken;
import cn.tag.entity.ShareParkingLot;
import cn.tag.service.ShareParkingLotService;
import cn.tag.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/share-parking-lots")
public class ShareParkingLotController {

    @Autowired
    private ShareParkingLotService shareParkingLotService;

    @GetMapping(params = {"page", "pageSize"})
    @EmployeeToken
    public ResponseEntity findByPage(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        Page<ShareParkingLot> shareParkingLots = shareParkingLotService.findByPage(page,pageSize);
        return ResponseEntity.ok().body(shareParkingLots);
    }

    @GetMapping(params = {"orderId"})
    public ResponseEntity findByOrderId(@RequestParam(name = "orderId") Integer orderId) throws ParseException {
        List<ShareParkingLot> shareParkingLotList = shareParkingLotService.findListByOrderId(orderId);
        return ResponseEntity.ok(shareParkingLotList);
    }

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
    @EmployeeToken
    public ResponseEntity publishShareParkingLot(@PathVariable Integer id, @RequestBody ShareParkingLot shareParkingLot){
        ShareParkingLot publish = shareParkingLotService.publish(id, shareParkingLot);
        return ResponseEntity.ok().body(publish);
    }

    @DeleteMapping("/{id}")
    @EmployeeToken
    public ResponseEntity delete(@PathVariable Integer id) {
        return shareParkingLotService.deleteByID(id)?ResponseEntity.ok().build():ResponseEntity.badRequest().build();
    }

}
