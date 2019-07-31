package cn.tag.respository;

import cn.tag.entity.ParkingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ParkingOrderRepository extends JpaRepository<ParkingOrder, Integer> {

    List<ParkingOrder> findByCarUserIdOrderByCreateTimeDesc(Integer userId);

    @Query(value = "select * from parking_order where car_num = ?1 and status != 'FW' and status !='F'", nativeQuery = true)
    List<ParkingOrder> findOrdersByCarNumAndStatusNotF(String carNum);

    List<ParkingOrder> findByCarUserId(Integer carUserId);

    @Query(value = "select * from parking_order where status = ?1", nativeQuery = true)
    List<ParkingOrder> getOrdersWithStatus(String status);

    @Query(value = "select * from parking_order where parking_boy_id = ?1 order by create_time desc", nativeQuery = true)
    List<ParkingOrder> findByEmployeeIdOrderByCreateTime(Integer employeeId);

    @Query(value = "select * from parking_order where car_user_id =?1 and type =0 and status ='FW' order by end_time desc limit 1", nativeQuery = true)
    ParkingOrder getOrderWithStyleIsZeroAndStatusIsF(Integer orderId);

    @Query(value = "select * from parking_order where region_id = ?1 and status ='WP'", nativeQuery = true)
    List<ParkingOrder> findByUserLocation(Integer employeeId);

    @Query(value = "select * from parking_order where car_num =?1 order by create_time desc limit 2", nativeQuery = true)
    List<ParkingOrder> findOrdersByCarNum(String carNum);

    @Query(value = "select * from parking_order where previous_order_id =?1", nativeQuery = true)
    ParkingOrder findOrderByPreOrderId(Integer previousOrderId);
}
