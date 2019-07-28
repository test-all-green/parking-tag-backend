package cn.tag.service;

import cn.tag.entity.ParkingStaff;
import cn.tag.entity.StaffCharacter;
import cn.tag.respository.ParkingStaffRepository;
import cn.tag.respository.StaffCharacterRepository;
import cn.tag.util.SHA1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ParkingStaffService {

    @Autowired
    ParkingStaffRepository parkingStaffRespository;

    @Autowired
    StaffCharacterRepository staffCharacterRepository;

    public void createAccount(ParkingStaff parkingStaff) {
        String staffPassword= SHA1.encode("123456");
        parkingStaff.setStaffPassword(staffPassword);
        parkingStaff.setStaffAccountStatus(0);
        parkingStaff.setStaffStatus(0);
        parkingStaffRespository.save(parkingStaff);
    }

    public Page<ParkingStaff> queryParkingStaffList(Integer pageNum, Integer pageSize) {
        return parkingStaffRespository.findAll(PageRequest.of(pageNum-1,pageSize));
    }

    public ParkingStaff findParkingStaffByStaffEmail(String loginMethod){
        return parkingStaffRespository.findParkingStaffByStaffEmail(loginMethod);
    }
    public ParkingStaff findUserById(Integer userId) {
        return parkingStaffRespository.findParkingStaffById(userId);
    }

    public void updateAccount(Integer id, ParkingStaff parkingStaff) {

        parkingStaff.setId(id);
        parkingStaff.setStaffCharacter(staffCharacterRepository.findById(parkingStaff.getStaffCharacter().getId()).get());
        parkingStaffRespository.save(parkingStaff);
    }
}
