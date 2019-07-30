package cn.tag.service;

import cn.tag.entity.ShareParkingLot;
import cn.tag.respository.ShareParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShareParkingLotService {

    @Autowired
    private ShareParkingLotRepository shareParkingLotRepository;

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
}
