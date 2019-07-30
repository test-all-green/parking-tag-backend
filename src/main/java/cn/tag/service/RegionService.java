package cn.tag.service;

import cn.tag.entity.Region;
import cn.tag.respository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    @Autowired
    RegionRepository regionRepository;

    public List<Region> queryRegionList() {
        return regionRepository.findAll();
    }

    public Region findById(Integer id){
        return regionRepository.findById(id).get();
    }
}
