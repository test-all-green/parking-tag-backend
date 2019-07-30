package cn.tag.service;

import cn.tag.entity.PublicParkingLot;
import cn.tag.entity.ShareParkingLot;
import cn.tag.respository.ShareParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShareParkingLotService {
    @Autowired
    private ShareParkingLotRepository shareParkingLotRepository;

    public List<ShareParkingLot> findAll(){return shareParkingLotRepository.findAll();}

    public ShareParkingLot add(ShareParkingLot shareParkingLot){
        shareParkingLot.setReleaseStatus(false);
        return shareParkingLotRepository.save(shareParkingLot);
    }

    public Page<ShareParkingLot> findByPage(Integer page,Integer pageSize){
        return shareParkingLotRepository.findAll(PageRequest.of(page-1,pageSize));
    }

    public List<ShareParkingLot> findByUserId(Integer userId){
        return shareParkingLotRepository.findAllByUserId(userId);
    }

    public ShareParkingLot update(Integer id,ShareParkingLot shareParkingLot){
        return shareParkingLotRepository.save(shareParkingLot);
    }

    public ShareParkingLot findById(Integer id){
        return shareParkingLotRepository.findById(id).get();
    }

//    public Page<ShareParkingLot> findByPage(Integer page, Integer pageSize, String name, Integer min, Integer max) {
//        Specification<ShareParkingLot> specification = new Specification<ShareParkingLot>() {
//
//        };
//
//    }
}
