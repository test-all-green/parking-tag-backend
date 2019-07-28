package cn.tag.controller;

import cn.tag.entity.ParkingLot;
import cn.tag.entity.ParkingStaff;
import cn.tag.respository.ParkingLotRepository;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingLotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    private ParkingStaff staff1 = new ParkingStaff("staff1", "1234567678", "236417824@qq.com", 1, null, 1, "123456");

    private List<ParkingLot> parkingLots;

    @BeforeEach
    public void setupDb(){
        parkingLots = parkingLotRepository.saveAll(Arrays.asList(
                new ParkingLot("停车场1",10,staff1),
                new ParkingLot("停车场2",15,staff1),
                new ParkingLot("停车场3",10,staff1)));
    }

    @AfterEach
    public void CleanAll(){
        parkingLotRepository.deleteAll();
    }

    @Test
    public void should_return_all_parking_lot_when_get() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(get("/parking-lots"))
                .andExpect(status().isOk()).andReturn();
        JSONArray resultArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        //then
        Assertions.assertEquals(parkingLots.size(), resultArray.length());
    }

    @Test
    public void should_return_200_when_post() throws Exception {
        //given
        ParkingLot parkingLot = new ParkingLot("停车场1", 10, null);
        //when
        String jsonString = new ObjectMapper().writeValueAsString(parkingLot);
        MvcResult mvcResult = mockMvc.perform(post("/parking-lots")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        JSONObject resultObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        Assertions.assertEquals(parkingLot.getParkingName(), resultObject.getString("parkingName"));
    }

    @Test
    public void should_return_parking_lot_by_page_when_get_by_page() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/parking-lots")
                .param("page", "0").param("pageSize", "2"))
                .andExpect(status().isOk()).andReturn();

        JSONObject resultObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        Assertions.assertEquals(2,resultObject.getJSONArray("content").length());
    }
}
