package cn.tag.controller;

import cn.tag.Interceptor.EmployeeToken;
import cn.tag.Interceptor.PassToken;
import cn.tag.Interceptor.UserLoginToken;
import cn.tag.entity.ParkingOrder;
import cn.tag.entity.PublicParkingLot;
import cn.tag.entity.ShareParkingLot;
import cn.tag.enums.OrderStatusEnum;
import cn.tag.service.ParkingOrderService;
import cn.tag.service.PublicParkingLotService;
import cn.tag.service.ShareParkingLotService;
import cn.tag.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/parking-orders")
@Slf4j
public class ParkingOrderController {
    @Autowired
    private ParkingOrderService parkingOrderService;
    @Autowired
    private PublicParkingLotService parkingLotService;
    @Autowired
    private ShareParkingLotService shareParkingLotService;

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok(parkingOrderService.findAll());
    }

    @GetMapping("/locations")
    public ResponseEntity findByUserLocation() {
        return ResponseEntity.ok(parkingOrderService.findByUserLocation());
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity findByPage(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(parkingOrderService.findByPage(page, pageSize));
    }

    @EmployeeToken
    @GetMapping("/{carUserId}")
    public ResponseEntity findOrderByUserId(@PathVariable Integer carUserId) {
        System.out.println("carUserId:" + carUserId);
        return ResponseEntity.ok(parkingOrderService.findOrderByCarUserId(carUserId));
    }

    @EmployeeToken
    @PostMapping
    public ResponseEntity add(@RequestBody ParkingOrder parkingOrder) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingOrderService.add(parkingOrder));
    }

    @EmployeeToken
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody ParkingOrder parkingOrder) {
        return ResponseEntity.ok(parkingOrderService.update(id, parkingOrder));
    }

    @EmployeeToken
    @GetMapping("/history")
    public ResponseEntity findByUserId() {
        String tokenUserId = TokenUtil.getTokenUserId();
        return ResponseEntity.ok(parkingOrderService.findOrderOfUser(Integer.valueOf(tokenUserId)));
    }

    @PassToken
    @GetMapping("/historys-employees")
    public ResponseEntity findByEmployeeId() {
        String tokenUserId = TokenUtil.getTokenUserId();
        System.out.println("tokenUserId:" + tokenUserId);
        return ResponseEntity.ok(parkingOrderService.findByEmployeeIdOrderByCreateTime(Integer.valueOf(tokenUserId)));
    }

    @PutMapping("/saveParkBoyId/{orderId}")
    public ResponseEntity saveParkBoyId(@PathVariable Integer orderId) {
        ParkingOrder parkingOrder = parkingOrderService.findOrderByOrderId(orderId);
        String tokenUserId = TokenUtil.getTokenUserId();
        parkingOrder.setParkingBoyId(Integer.valueOf(tokenUserId));
        parkingOrder.setStatus(OrderStatusEnum.WAIT_TO.getKey());
        parkingOrderService.update(Integer.valueOf(orderId), parkingOrder);
        Map map = new HashMap();
        map.put("code", "200");
        map.put("message", "抢单成功");
        return ResponseEntity.ok(map);
    }

    @PutMapping("/grabOrder")
    public ResponseEntity grabOrder(@RequestBody Map<String, String> request) throws ObjectOptimisticLockingFailureException {
        Map map = new HashMap();
        String parkingLotType = request.get("parkingLotType");
        String parkingLotId = request.get("parkingLotId");
        String orderId = request.get("orderId");
        ParkingOrder parkingOrder = parkingOrderService.findOrderByOrderId(Integer.valueOf(orderId));
        if (parkingOrder.getStatus() != OrderStatusEnum.PARKING_WAIT.toString()) {
            map.put("message", "订单已被抢");
        }
        parkingOrder.setParkingLotId(Integer.valueOf(parkingLotId));
        setParkingOrder(parkingOrder, parkingLotId, parkingLotType);
        parkingOrderService.update(Integer.valueOf(orderId), parkingOrder);

        map.put("code", "200");
        map.put("message", "完成操作");
        return ResponseEntity.ok(map);
    }

    @PassToken
    @PutMapping("/finishedOrder/{orderId}")
    public ResponseEntity finishdeOrder(@PathVariable Integer orderId) throws ObjectOptimisticLockingFailureException, ParseException {
        ParkingOrder parkingOrder = parkingOrderService.findOrderByOrderId(Integer.valueOf(orderId));
        Map map = new HashMap();
        setParkingOrderfinished(parkingOrder);
        parkingOrderService.update(Integer.valueOf(orderId), parkingOrder);
        map.put("code", "200");
        map.put("message", "完成");
        return ResponseEntity.ok(map);
    }

    @GetMapping(params = {"status"})
    public ResponseEntity getOrdersWithStatus(@RequestParam(name = "status", defaultValue = "PW") String status) {
        return ResponseEntity.ok(parkingOrderService.getOrdersWithStatus(status));
    }

    @GetMapping("/getFinishedOrder")
    public ResponseEntity getOrderBySytleIsZeroAndStatusIsF() {
        Integer orderId = Integer.valueOf(TokenUtil.getTokenUserId());
        return ResponseEntity.ok(parkingOrderService.getOrderWithStyleIsZeroAndStatusIsF(orderId));
    }

    private void setParkingOrder(ParkingOrder parkingOrder, String parkingLotId, String parkingLotType) {
        // todo 根据parking lot类型获取parking location
        // 停车场剩余容量减 1
        if (Integer.valueOf(parkingLotType) == 1) {
            PublicParkingLot parkingLot = parkingLotService.findById(Integer.valueOf(parkingLotId));
            parkingLot.setRemain(parkingLot.getRemain() - 1);
            parkingLotService.update(parkingLot.getId(), parkingLot);
            parkingOrder.setParkingLocation(parkingLot.getLocation());
            parkingOrder.setParkingCreateTime(System.currentTimeMillis());
        }
        parkingOrder.setStatus(OrderStatusEnum.PARKING_ING.getKey());
        parkingOrder.setType(0);

    }

    private void setParkingOrderfinished(ParkingOrder parkingOrder) throws ParseException {
        // todo 根据parking lot类型获取parking location
        // 停车场剩余容量加 1
        if (parkingOrder.getParkingLotType() == 1) {
            PublicParkingLot parkingLot = parkingLotService.findById(parkingOrder.getParkingLotId());
            parkingLot.setRemain(parkingLot.getRemain() - 1);
            parkingLotService.update(parkingLot.getId(), parkingLot);
        } else {
            ShareParkingLot shareParkingLot = shareParkingLotService.findById(parkingOrder.getParkingLotId());
            shareParkingLot.setStatus(1);
        }
        if (parkingOrder.getType() == 0) {
            parkingOrder.setStatus(OrderStatusEnum.FETCH_WAIT.getKey());
        } else {
            parkingOrder.setStatus(OrderStatusEnum.FINISH.getKey());
            setParkTime(parkingOrder);
        }
        parkingOrder.setEndTime(System.currentTimeMillis());

    }

    private long setParkTime(ParkingOrder parkingOrder) throws ParseException {
        long parkTime;
        double price = 0.0;
        if (parkingOrder.getParkingLotType() == 1) {
            price = parkingLotService.findById(parkingOrder.getParkingLotId()).getPrice();
        } else {
            price = shareParkingLotService.findById(parkingOrder.getParkingLotId()).getPrice();
        }
        Date time1 = getTimeStr(parkingOrder.getEndTime());
        Date time2 = getTimeStr(parkingOrder.getParkingCreateTime());
        long diff = time1.getTime() - time2.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        long diffHours = diff / (60 * 60 * 1000) % 24 + 1;
        parkTime = diffDays * 24 + diffHours;
        //System.out.println("parkTime"+parkTime);
        parkingOrder.setMoney(parkTime * price);
        return parkTime;
    }

    private Date getTimeStr(long timeLong) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(sdf.format(new Date(Long.parseLong(String.valueOf(timeLong)))));
    }
//    private double countParkMoney(parkingOrder)
}
