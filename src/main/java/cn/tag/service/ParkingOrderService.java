package cn.tag.service;

import cn.tag.entity.ParkingOrder;
import cn.tag.respository.ParkingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingOrderService {
    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    public List<ParkingOrder> findAll(){
        return parkingOrderRepository.findAll();
    }

    public ParkingOrder add(ParkingOrder parkingOrder){
        return parkingOrderRepository.save(parkingOrder);
    }

    public Page<ParkingOrder> findByPage(Integer page,Integer pageSize){
        return parkingOrderRepository.findAll(PageRequest.of(page-1,pageSize));
    }

    public ParkingOrder update(Integer id,ParkingOrder parkingOrder){
        parkingOrder.setId(id);
        return parkingOrderRepository.save(parkingOrder);
    }
}
