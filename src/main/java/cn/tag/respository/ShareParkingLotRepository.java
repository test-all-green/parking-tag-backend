package cn.tag.respository;

import cn.tag.entity.ShareParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareParkingLotRepository extends JpaRepository<ShareParkingLot, Integer> {
    List<ShareParkingLot> findByUserId(Integer userId);
}
