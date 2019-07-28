package cn.tag.service;

import cn.tag.entity.ParkingLot;
import cn.tag.respository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    public ParkingLot update(Integer id, ParkingLot parkingLot) {
        ParkingLot lot = parkingLotRepository.findById(id).get();
        if(parkingLot.getParkingLotName() != null){
            lot.setParkingLotName(parkingLot.getParkingLotName());
        }
        if(parkingLot.getParkingLotCapacity() != null){
            lot.setParkingLotCapacity(parkingLot.getParkingLotCapacity());
        }
        if(parkingLot.getParkingLotStatus() != null){
            lot.setParkingLotStatus(parkingLot.getParkingLotStatus());
        }
        if(parkingLot.getParkingStaff() != null){
            lot.setParkingStaff(parkingLot.getParkingStaff());
        }
        return parkingLotRepository.save(lot);
    }

    public Page<ParkingLot> findByPage(Integer page, Integer pageSize, String name, Integer min, Integer max) {

        Specification<ParkingLot> specification = new Specification<ParkingLot>() {

            @Override
            public Predicate toPredicate(Root<ParkingLot> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate parkingLotName = null;
                Predicate parkingLotCapacity = null;
                if(name != null){
//                    query.where(criteriaBuilder.and(criteriaBuilder.like(root.get("parkingLotName").as(String.class), "%'"+name+"'%")));
                    parkingLotName = criteriaBuilder.like(root.get("parkingLotName").as(String.class), "%" + name + "%");
                }
                if(min < max){
//                    query.where(criteriaBuilder.and(criteriaBuilder.between(root.get("parkingLotCapacity"),Math.max(1,min), Math.min(9999,max))));
                    parkingLotCapacity = criteriaBuilder.between(root.get("parkingLotCapacity"), Math.max(1, min), Math.min(9999, max));
                }
                if(parkingLotName != null && parkingLotCapacity != null){
                    query.where(criteriaBuilder.and(parkingLotName,parkingLotCapacity));
                } else if(parkingLotName != null){
                    query.where(criteriaBuilder.and(parkingLotName));
                } else {
                    query.where(criteriaBuilder.and(parkingLotCapacity));
                }
                return null;
            }
        };
        return parkingLotRepository.findAll(specification, PageRequest.of(page-1, pageSize));
    }
}
