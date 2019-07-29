package cn.tag.respository;

import cn.tag.entity.ParkingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ParkingOrderRepository extends JpaRepository<ParkingOrder,Integer> {

    List<ParkingOrder> findByCarUserIdOrderByCreateTimeDesc(Integer userId);

    @Query(value = "select * from parking_order where car_num = ?1 and status != 'F'",nativeQuery = true)
    List<ParkingOrder> findOrdersByCarNumAndSatusNotF(String carNum);
}
