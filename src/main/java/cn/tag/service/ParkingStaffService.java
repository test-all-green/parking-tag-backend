package cn.tag.service;

import cn.tag.entity.ParkingStaff;
import cn.tag.respository.ParkingStaffRepository;
import cn.tag.util.SHA1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ParkingStaffService {

    @Autowired
    ParkingStaffRepository parkingStaffRespository;


    public void createAccount(ParkingStaff parkingStaff) {
        String staffPassword= SHA1.encode("123456");
        parkingStaff.setStaffPassword(staffPassword);
        parkingStaff.setStaffAccountStatus(0);
        parkingStaffRespository.save(parkingStaff);
    }

    public Page<ParkingStaff> queryParkingStaffList(Integer pageNum, Integer pageSize) {
        return parkingStaffRespository.findAll(PageRequest.of(pageNum-1,pageSize));
    }

    public ParkingStaff findParkingStaffByStaffEmail(ParkingStaff user){
        return parkingStaffRespository.findParkingStaffByStaffEmail(user.getStaffEmail());
    }
    public ParkingStaff findUserById(Integer userId) {
        return parkingStaffRespository.findParkingStaffById(userId);
    }
}
