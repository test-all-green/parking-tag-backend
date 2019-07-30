package cn.tag.service;

import cn.tag.entity.ParkingOrder;
import cn.tag.entity.ShareParkingLot;
import cn.tag.exception.CustomException;
import cn.tag.respository.ShareParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShareParkingLotService {

    @Autowired
    private ShareParkingLotRepository shareParkingLotRepository;

    @Autowired
    private ParkingOrderService parkingOrderService;
    public List<ShareParkingLot> findAll(){return shareParkingLotRepository.findAll();}

    public Page<ShareParkingLot> findByPage(Integer page, Integer pageSize){
        return shareParkingLotRepository.findAll(PageRequest.of(page-1,pageSize));
    }
    public List<ShareParkingLot> findByUserId(Integer userId) {
        return shareParkingLotRepository.findByUserId(userId);
    }

    public ShareParkingLot add(ShareParkingLot shareParkingLot) {
        shareParkingLot.setStatus(0); //未发布状态
        return shareParkingLotRepository.save(shareParkingLot);
    }

    public ShareParkingLot publish(Integer id, ShareParkingLot shareParkingLot) {
        ShareParkingLot lot = shareParkingLotRepository.findById(id).get();
        lot.setBeginTime(shareParkingLot.getBeginTime());
        lot.setEndTime(shareParkingLot.getEndTime());
        lot.setStatus(1); //状态为已发布状态
        return shareParkingLotRepository.save(lot);
    }
    public ShareParkingLot findById(Integer id){
        return shareParkingLotRepository.findById(id).get();
    }
    public Boolean deleteByID(Integer id){
        try{
            shareParkingLotRepository.deleteById(id);
            return true;
        }catch (Exception e){
            throw new CustomException("105","删除失败");
        }
    }
    public List<ShareParkingLot> findListByOrderId(Integer orderId){
        ParkingOrder orderByOrderId = parkingOrderService.findOrderByOrderId(orderId);
        return shareParkingLotRepository.findByRegionId(orderByOrderId.getRegionId());
    }
}
