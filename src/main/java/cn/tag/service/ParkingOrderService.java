package cn.tag.service;

import cn.tag.entity.ParkingOrder;
import cn.tag.enums.OrderStatusEnum;
import cn.tag.exception.CustomException;
import cn.tag.respository.ParkingOrderRepository;
import cn.tag.util.TokenUtil;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class ParkingOrderService {
    public static final String CODE_401 = "401";
    public static final String ORDER_ERROR = "您的车有未完成订单";
    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    public List<ParkingOrder> findAll(){
        return parkingOrderRepository.findAll();
    }

    public ParkingOrder add(ParkingOrder parkingOrder){
        String carNum = parkingOrder.getCarNum();
        if(parkingOrderRepository.findOrdersByCarNumAndSatusNotF(carNum).size()==0) {
            parkingOrder.setCarUserId(Integer.valueOf(TokenUtil.getTokenUserId()));
            parkingOrder.setCreateTime(Long.valueOf(getStringDate()));
            parkingOrder.setStatus(OrderStatusEnum.PARKING_WAIT.getKey());
            return parkingOrderRepository.save(parkingOrder);
        }else{
            throw new CustomException(CODE_401, ORDER_ERROR);
        }
    }
    private String getStringDate(){
        Date current = new Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "yyyyMMddHHmmss");
        String time = sdf.format(current);
        return time;
    }
    public Page<ParkingOrder> findByPage(Integer page,Integer pageSize){
        return parkingOrderRepository.findAll(PageRequest.of(page-1,pageSize));
    }

    public ParkingOrder update(Integer id,ParkingOrder parkingOrder){
        parkingOrder.setId(id);
        return parkingOrderRepository.save(parkingOrder);
    }

    public List<ParkingOrder> findOrderOfUser(Integer userId) {
        return parkingOrderRepository.findByCarUserIdOrderByCreateTimeDesc(userId);
    }
    public ParkingOrder findOrderByOrderId(Integer orderId){
        return parkingOrderRepository.findById(orderId).get();
    }

}
