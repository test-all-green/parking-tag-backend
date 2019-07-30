package cn.tag.service;

import cn.tag.entity.ParkingOrder;
import cn.tag.entity.ShareParkingLot;
import cn.tag.exception.CustomException;
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
                if(tempEndTime<=(scheduledParkingTime+tempScheduledParkingTime)){
                    currentShareParkingLot.add(shareParkingLotList.get(i));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return currentShareParkingLot;
}
}
