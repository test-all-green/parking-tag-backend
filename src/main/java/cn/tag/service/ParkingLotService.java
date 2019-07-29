package cn.tag.service;

import cn.tag.entity.PublicParkingLot;
import cn.tag.respository.PublicParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private PublicParkingLotRepository publicParkingLotRepository;

    public List<PublicParkingLot> findAll() {
        return publicParkingLotRepository.findAll();
    }

    public PublicParkingLot add(PublicParkingLot parkingLot) {
        return publicParkingLotRepository.save(parkingLot);
    }

    public Page<PublicParkingLot> findByPage(Integer page, Integer pageSize) {
        return publicParkingLotRepository.findAll(PageRequest.of(page-1, pageSize));
    }

    public PublicParkingLot update(Integer id, PublicParkingLot parkingLot) {
        PublicParkingLot lot = publicParkingLotRepository.findById(id).get();
        if(parkingLot.getParkingLotName() != null){
            lot.setParkingLotName(parkingLot.getParkingLotName());
        }
        if(parkingLot.getParkingLotCapacity() != null){
            lot.setParkingLotCapacity(parkingLot.getParkingLotCapacity());
        }
        if(parkingLot.getStatus() != null){
            lot.setStatus(parkingLot.getStatus());
        }

        return publicParkingLotRepository.save(lot);
    }

    public Page<PublicParkingLot> findByPage(Integer page, Integer pageSize, String name, Integer min, Integer max) {

        Specification<PublicParkingLot> specification = new Specification<PublicParkingLot>() {

            @Override
            public Predicate toPredicate(Root<PublicParkingLot> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
        return publicParkingLotRepository.findAll(specification, PageRequest.of(page-1, pageSize));
    }
}
