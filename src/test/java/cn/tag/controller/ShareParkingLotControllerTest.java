package cn.tag.controller;

import cn.tag.entity.ShareParkingLot;
import cn.tag.respository.ShareParkingLotRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShareParkingLotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShareParkingLotRepository shareParkingLotRepository;

    private List<ShareParkingLot> shareParkingLots;

    private String token;

    @BeforeEach
    public void initDb() throws Exception {
        shareParkingLots = shareParkingLotRepository.saveAll(Arrays.asList(
                new ShareParkingLot(null, "parkinglot1", null, null, 0,1,1,"location1"),
                new ShareParkingLot(null, "parkinglot2", new Date().getTime(), new Date().getTime(), 1,1,1,"location2"),
                new ShareParkingLot(null, "parkinglot3", null, null, 0,1,1,"location3")
        ));
        MvcResult mvcResult = mockMvc.perform(get("/users/login")).andReturn();
        JSONObject resultObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        token = resultObject.getString("token");
    }

    @AfterEach
    public void cleanDb(){
        shareParkingLotRepository.deleteAll();
    }



}
