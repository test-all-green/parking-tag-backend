package cn.tag.service;

import cn.tag.entity.Role;
import cn.tag.respository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public List<Role> queryCharacterList() {
        return roleRepository.findAll();
    }
}
