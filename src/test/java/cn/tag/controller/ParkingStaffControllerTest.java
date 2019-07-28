package cn.tag.controller;


import cn.tag.entity.ParkingStaff;
import cn.tag.entity.StaffCharacter;
import cn.tag.respository.ParkingStaffRepository;
import cn.tag.respository.StaffCharacterRepository;
import cn.tag.util.SHA1;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingStaffControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ParkingStaffRepository parkingStaffRespository;

    @Autowired
    StaffCharacterRepository staffCharacterRepository;

    @BeforeEach
    public void before_test() {
        staffCharacterRepository.save(new StaffCharacter("泊车仔"));
        staffCharacterRepository.save(new StaffCharacter("经理"));
        StaffCharacter staffCharacter = staffCharacterRepository.findAll().get(0);
        parkingStaffRespository.save(new ParkingStaff("张三", "13234565456", "sanzhang@parkinglot.com", 0, staffCharacter, 0, SHA1.encode("123456")));
        parkingStaffRespository.save(new ParkingStaff("李四", "12637878767", "sili@parkinglot.com", 0, staffCharacter, 0,SHA1.encode("123456")));
        parkingStaffRespository.save(new ParkingStaff("王五", "13765689098", "wuwang@parkinglot.com", 0, staffCharacter, 0,SHA1.encode("123456")));
        parkingStaffRespository.save(new ParkingStaff("刘六", "13678798098", "liuliu@parkinglot.com", 0, staffCharacter, 0,SHA1.encode("123456")));
        parkingStaffRespository.save(new ParkingStaff("赵甲", "13526767878", "jiazhao@parkinglot.com", 0, staffCharacter, 0,SHA1.encode("123456")));
    }

    @AfterEach
    public void after_test() {
//        parkingStaffRespository.deleteAll();
    }

    @Test
    public void should_created_status_when_create_account() throws Exception {
        //given
        StaffCharacter staffCharacter = staffCharacterRepository.findAll().get(0);
        ParkingStaff parkingStaff = new ParkingStaff("张小三", "12382736475", "hello@parkinglot.com", 0, staffCharacter);
        int currentSize = parkingStaffRespository.findAll().size();
        //when
        System.out.println(JSON.toJSONString(parkingStaff));
        ResultActions resultActions = this.mockMvc.perform(post("/parking-staffs")
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8).content(JSON.toJSONString(parkingStaff)));
        //then
        resultActions.andExpect(status().isCreated());
        Assertions.assertEquals(parkingStaff.getStaffName(),parkingStaffRespository.findAll().get(currentSize).getStaffName());
    }

    @Test
    public void should_return_parking_staff_list_when_get_by_pageNum_and_pageSize() throws Exception {
        //given
        List<ParkingStaff> parkingStaffList = parkingStaffRespository.findAll();
        //when
        ResultActions resultActions = this.mockMvc.perform(get("/parking-staffs")
                .param("pageNum","1").param("pageSize","5"));
        //then
        resultActions.andExpect(status().isOk());
        JSONObject jsonObject = JSONObject.parseObject(resultActions.andReturn().getResponse().getContentAsString());
        List<ParkingStaff> currentParkingStaffList = JSONObject.parseArray(jsonObject.get("content").toString(), ParkingStaff.class);
        Assertions.assertEquals(parkingStaffList.get(0).getStaffName(), currentParkingStaffList.get(0).getStaffName());
    }

}
