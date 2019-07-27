package cn.tag.respository;

import cn.tag.entity.ParkingStaff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingStaffRespository extends JpaRepository<ParkingStaff,Integer> {
}
