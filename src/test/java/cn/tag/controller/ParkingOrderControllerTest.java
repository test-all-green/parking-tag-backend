package cn.tag.controller;

import cn.tag.entity.ParkingLot;
import cn.tag.entity.ParkingOrder;
import cn.tag.respository.ParkingOrderRepository;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingOrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    private List<ParkingOrder> parkingOrderList;
    @BeforeEach
    public void setupDb(){
        parkingOrderList=parkingOrderRepository.saveAll(Arrays.asList(
                new ParkingOrder("12345",1,1,new Date(),new Date(),1,1),
                new ParkingOrder("23456",2,2,new Date(),new Date(),2,2),
                new ParkingOrder("34567",3,3,new Date(),new Date(),3,3)
                ));
    }
    @AfterEach
    public void cleanAll(){
        parkingOrderRepository.deleteAll();
    }
    @Test
    public void should_return_all_parking_order_when_get() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(get("/parking-orders"))
                .andExpect(status().isOk()).andReturn();
        JSONArray resultArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        //then
        Assertions.assertEquals(parkingOrderList.size(), resultArray.length());
    }
    @Test
    public void should_return_200_when_post() throws Exception {
        //given
        ParkingOrder parkingOrder = new ParkingOrder("12345",1,1,new Date(),new Date(),1,1);
        //when
        String jsonString = new ObjectMapper().writeValueAsString(parkingOrder);
        MvcResult mvcResult = mockMvc.perform(post("/parking-orders")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        JSONObject resultObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        Assertions.assertEquals(parkingOrder.getCarNumber(), resultObject.getString("carNumber"));
    }

    @Test
    public void should_return_parking_order_by_page_when_get_by_page() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/parking-orders")
                .param("page", "1").param("pageSize", "2"))
                .andExpect(status().isOk()).andReturn();

        JSONObject resultObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        Assertions.assertEquals(2,resultObject.getJSONArray("content").length());
    }

    @Test
    public void should_update_parking_order_when_put() throws Exception {
        ParkingOrder parkingOrder = new ParkingOrder("88888",1,1,new Date(),new Date(),1,1);

        MvcResult mvcResult = mockMvc.perform(put("/parking-orders/" + parkingOrderList.get(0).getId())
                .content(JSON.toJSONString(parkingOrder)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        JSONObject resultObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        // 修改的字段
        Assertions.assertEquals(parkingOrder.getCarNumber(), resultObject.getString("carNumber"));
    }
}
