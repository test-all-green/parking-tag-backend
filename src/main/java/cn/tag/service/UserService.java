package cn.tag.service;

import cn.tag.entity.User;
import cn.tag.exception.CustomException;
import cn.tag.respository.UserRepository;
import cn.tag.util.SHA1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User register(User user) {
        List<User> userRepositoryByPhone = userRepository.findByPhone(user.getPhone());
        if(userRepositoryByPhone.size() > 0){
            throw new RuntimeException("输入的手机号码已被注册！");
        }
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
