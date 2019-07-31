package cn.tag.respository;

import cn.tag.entity.ShareParkingLotLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareParkingLotLocationRepository extends JpaRepository<ShareParkingLotLocation, Integer> {
}
