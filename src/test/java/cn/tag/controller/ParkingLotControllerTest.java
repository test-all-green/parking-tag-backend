package cn.tag.controller;

import cn.tag.entity.ParkingLot;
import cn.tag.entity.ParkingStaff;
import cn.tag.respository.ParkingLotRepository;
import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingLotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    private ParkingStaff staff1 = new ParkingStaff("staff1", "1234567678", "236417824@qq.com", 1, null, 1, "123456");

    private List<ParkingLot> parkingLots;

    @Before
    public void setupDb(){
        parkingLots = parkingLotRepository.saveAll(Arrays.asList(
                new ParkingLot("停车场1",10,staff1),
                new ParkingLot("停车场2",15,staff1),
                new ParkingLot("停车场3",10,staff1)));
    }

    @After
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
}
