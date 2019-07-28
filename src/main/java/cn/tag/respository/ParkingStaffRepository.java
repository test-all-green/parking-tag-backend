package cn.tag.respository;

import cn.tag.entity.ParkingStaff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingStaffRepository extends JpaRepository<ParkingStaff,Integer> {
}
