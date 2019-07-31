package cn.tag.service;

import cn.tag.entity.ParkingOrder;
import cn.tag.entity.ShareParkingLot;
import cn.tag.exception.CustomException;
import cn.tag.respository.ShareParkingLotLocationRepository;
import cn.tag.respository.ShareParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShareParkingLotService {

    @Autowired
    private ShareParkingLotRepository shareParkingLotRepository;

    @Autowired
    private ShareParkingLotLocationRepository shareParkingLotLocationRepository;

    @Autowired
    private ParkingOrderService parkingOrderService;
    public List<ShareParkingLot> findAll(){return shareParkingLotRepository.findAll();}

    public Page<ShareParkingLot> findByPage(Integer page, Integer pageSize){
        return shareParkingLotRepository.findAll(PageRequest.of(page-1,pageSize));
    }
    public List<ShareParkingLot> findByUserId(Integer userId) {
        return shareParkingLotRepository.findByUserId(userId);
    }

    public ShareParkingLot add(ShareParkingLot shareParkingLot, Integer userId) {

        List<ShareParkingLot> byUserId = shareParkingLotRepository.findByUserId(userId);
        if(byUserId.size() >= 10){
            throw new RuntimeException("发布的的共享车位信息不能超过10条！");
        }
        for(ShareParkingLot sp : byUserId){
            if(sp.getParkingLotName().equals(shareParkingLot.getParkingLotName())){
                throw new RuntimeException("输入的共享停车位名称已存在!");
            }
        }
        shareParkingLot.setLocationName(shareParkingLotLocationRepository.findById(shareParkingLot.getLocationId()).get().getName());
        shareParkingLot.setStatus(0); //未发布状态
        return shareParkingLotRepository.save(shareParkingLot);
    }

    public ShareParkingLot publish(Integer id, ShareParkingLot shareParkingLot) {
        ShareParkingLot lot = shareParkingLotRepository.findById(id).get();
        lot.setBeginTime(shareParkingLot.getBeginTime());
        lot.setEndTime(shareParkingLot.getEndTime());
        if(shareParkingLot.getParkingLotName() != null){
            lot.setLocationName(shareParkingLot.getLocationName());
        }
        if(shareParkingLot.getLocationId() != null){
            lot.setLocationId(shareParkingLot.getLocationId());
            lot.setLocationName(shareParkingLot.getLocationName());
        }
        if(shareParkingLot.getPrice() != null){
            lot.setPrice(shareParkingLot.getPrice());
        }
        if(shareParkingLot.getRegionId() != null){
            lot.setRegionId(shareParkingLot.getRegionId());
        }

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
    public List<ShareParkingLot> findListByOrderId(Integer orderId) throws ParseException {
        ParkingOrder parkingOrder = parkingOrderService.findOrderByOrderId(orderId);
        List<ShareParkingLot> shareParkingLotList = shareParkingLotRepository.findByRegionId(parkingOrder.getRegionId());
        shareParkingLotList=shareParkingLotList.stream().filter(es->es.getStatus()==1).collect(Collectors.toList());
        Long scheduledParkingTime = parkingOrder.getScheduledParkingTime();
        String scheduledParkingArriveTime = parkingOrder.getScheduledParkingArriveTime();
        //
        scheduledParkingTime=scheduledParkingTime*60*60*1000;
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        //
        long tempScheduledParkingTime = df.parse(scheduledParkingArriveTime + ":00").getTime();
        List<ShareParkingLot> currentShareParkingLot=new ArrayList<>();
        for(int i=0;i<shareParkingLotList.size();i++){
            Long endTime = shareParkingLotList.get(i).getEndTime();
            SimpleDateFormat lsdFormat = new SimpleDateFormat("HH:mm:ss");
            Date lDate = new Date(endTime*1000);
            String lStrDate = lsdFormat.format(lDate);
            try {
                long tempEndTime = df.parse(lStrDate).getTime();
                if(tempEndTime>=(scheduledParkingTime+tempScheduledParkingTime)){
                    currentShareParkingLot.add(shareParkingLotList.get(i));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return currentShareParkingLot;
    }
    public ShareParkingLot cancelPublish(Integer id) {
        ShareParkingLot lot = shareParkingLotRepository.findById(id).get();
        if(lot.getStatus() == 2){
            // 使用中
            throw new RuntimeException("该车位已被使用，无法取消");
        }
        lot.setBeginTime(null);
        lot.setEndTime(null);
        lot.setStatus(0); //状态为已发布状态
        return shareParkingLotRepository.save(lot);
    }
}
