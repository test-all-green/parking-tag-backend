package cn.tag.respository;

import cn.tag.entity.ParkingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingOrderRepository extends JpaRepository<ParkingOrder,Integer> {

    List<ParkingOrder> findByCarUserIdOrderByCreateTimeDesc(Integer userId);
}
