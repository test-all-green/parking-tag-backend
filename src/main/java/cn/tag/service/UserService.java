package cn.tag.service;

import cn.tag.entity.User;
import cn.tag.respository.UserRepository;
import cn.tag.util.SHA1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User register(User user) {
        user.setStatus(0);
        user.setUserPassword(SHA1.encode(user.getUserPassword()));
        return userRepository.saveAndFlush(user);
    }

    public User findUserById(int userId) {
        return userRepository.findById(userId).get();
    }
    public User findParkingStaffByStaffEmail(String loginMethod){
        return userRepository.findEmployeeByEmail(loginMethod);
    }
}
