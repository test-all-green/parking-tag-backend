package cn.tag.controller;

import cn.tag.entity.Region;
import cn.tag.respository.RegionRepository;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RegionRepository regionRepository;

    @BeforeEach
    public void before_test(){
        regionRepository.save(new Region("香洲区"));
        regionRepository.save(new Region("高新区"));
    }

    @AfterEach
    public void after_test(){
        regionRepository.deleteAll();
    }

    @Test
    public void should_return_region_list_when_query() throws Exception {
        //given
        List<Region> regionList = regionRepository.findAll();
        //when
        ResultActions resultActions = this.mockMvc.perform(get("/regions"));
        //then
        resultActions.andExpect(status().isOk());
        Assertions.assertEquals(regionList.get(0).getRegionName()
                , JSONObject.parseObject(JSONArray.parseArray(resultActions.andReturn().getResponse().getContentAsString()).get(0).toString()).getString("regionName"));
    }

}
