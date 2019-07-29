package cn.tag.respository;


import cn.tag.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    @Query(value = "select * from employee p where p.email =?1 or p.phone =?1",nativeQuery = true)
    Employee findEmployeeByEmail(String staffEmail);

//    Employee findParkingStaffById(Integer id);
//
    Employee findByEmployeeName(String name);

}
