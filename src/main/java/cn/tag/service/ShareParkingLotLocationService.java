package cn.tag.service;

import cn.tag.entity.ShareParkingLotLocation;
import cn.tag.respository.ShareParkingLotLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShareParkingLotLocationService {

    @Autowired
    private ShareParkingLotLocationRepository shareParkingLotLocationRepository;

    public List<ShareParkingLotLocation> findAll() {
        return shareParkingLotLocationRepository.findAll();
    }
}
