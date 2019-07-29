package cn.tag.respository;


import cn.tag.entity.PublicParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicParkingLotRepository extends JpaRepository<PublicParkingLot, Integer>, JpaSpecificationExecutor<PublicParkingLot> {
}