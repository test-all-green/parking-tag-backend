package cn.tag.service;

import cn.tag.entity.ParkingOrder;
import cn.tag.enums.OrderStatusEnum;
import cn.tag.respository.ParkingOrderRepository;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Service
public class ParkingOrderService {
    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    public List<ParkingOrder> findAll(){
        return parkingOrderRepository.findAll();
    }

    public ParkingOrder add(ParkingOrder parkingOrder){
        parkingOrder.setCreateTime(System.currentTimeMillis());
        parkingOrder.setStatus(OrderStatusEnum.PARKING_WAIT.getKey());
        return parkingOrderRepository.save(parkingOrder);
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
}
