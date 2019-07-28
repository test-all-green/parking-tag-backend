package cn.tag.service;

import cn.tag.entity.StaffCharacter;
import cn.tag.respository.StaffCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffCharacterService {

    @Autowired
    StaffCharacterRepository staffCharacterRepository;

    public List<StaffCharacter> queryStaffCharacterList() {
        return staffCharacterRepository.findAll();
    }
}
