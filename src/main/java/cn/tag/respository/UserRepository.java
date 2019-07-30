package cn.tag.respository;

import cn.tag.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select * from user p where p.email =?1 or p.phone =?1",nativeQuery = true)
    User findEmployeeByEmail(String staffEmail);
}
