package cn.tag.respository;

import cn.tag.entity.ParkingStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingStaffRepository extends JpaRepository<ParkingStaff,Integer> {
    @Query(value = "select * from parking_staff p where p.staff_email =?1",nativeQuery = true)
    ParkingStaff findParkingStaffByStaffEmail(String staffEmail);

    ParkingStaff findParkingStaffById(Integer id);
}
