package cn.tag.service;

import cn.tag.entity.ParkingLot;
import cn.tag.respository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public List<ParkingLot> findAll() {
        return parkingLotRepository.findAll();
    }

    public ParkingLot add(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }
}
