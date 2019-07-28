package cn.tag.service;

import cn.tag.entity.ParkingLot;
import cn.tag.respository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<ParkingLot> findByPage(Integer page, Integer pageSize) {
        return parkingLotRepository.findAll(PageRequest.of(page-1, pageSize));
    }
}
