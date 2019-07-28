package cn.tag.service.login;

import cn.tag.entity.ParkingStaff;
import cn.tag.exception.LoginException;
import cn.tag.respository.ParkingStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private ParkingStaffRepository staffRespository;

    public boolean authenticate(ParkingStaff inputUser) {
        ParkingStaff staffByName = staffRespository.findByStaffName(inputUser.getStaffName());
        if (staffByName == null) {
            throw new LoginException("No exist User");
        }
        if (!staffByName.getStaffPassword().equals(inputUser.getStaffPassword())) {
            throw new LoginException("Password invalid");
        }
        return true;
    }
}
