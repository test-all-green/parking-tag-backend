//package cn.tag.controller;
//
//
//import cn.tag.entity.Employee;
//import cn.tag.entity.PublicParkingLot;
//import cn.tag.respository.PublicParkingLotRepository;
//import com.alibaba.fastjson.JSON;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class ParkingLotControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private PublicParkingLotRepository publicParkingLotRepository;
//
//    private Employee staff1 = new Employee("staff1", "1234567678", "236417824@qq.com", 1, null, 1, "123456");
//
//    private List<PublicParkingLot> parkingLots;
//
//    @BeforeEach
//    public void setupDb(){
//        parkingLots = publicParkingLotRepository.saveAll(Arrays.asList(
//                new PublicParkingLot("停车场1",10,staff1),
//                new PublicParkingLot("停车场2",15,staff1),
//                new PublicParkingLot("停车场3",10,staff1)));
//    }
//
//    @AfterEach
//    public void CleanAll(){
//        publicParkingLotRepository.deleteAll();
//    }
//
//    @Test
//    public void should_return_all_parking_lot_when_get() throws Exception {
//        //when
//        MvcResult mvcResult = mockMvc.perform(get("/parking-lots"))
//                .andExpect(status().isOk()).andReturn();
//        JSONArray resultArray = new JSONArray(mvcResult.getResponse().getContentAsString());
//        //then
//        Assertions.assertEquals(parkingLots.size(), resultArray.length());
//    }
//
//    @Test
//    public void should_return_200_when_post() throws Exception {
//        //given
//        ParkingLot parkingLot = new ParkingLot("停车场1", 10, null);
//        //when
//        String jsonString = new ObjectMapper().writeValueAsString(parkingLot);
//        MvcResult mvcResult = mockMvc.perform(post("/parking-lots")
//                .content(jsonString)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated()).andReturn();
//        JSONObject resultObject = new JSONObject(mvcResult.getResponse().getContentAsString());
//        Assertions.assertEquals(parkingLot.getParkingLotName(), resultObject.getString("parkingLotName"));
//    }
//
//    @Test
//    public void should_return_parking_lot_by_page_when_get_by_page() throws Exception {
//        MvcResult mvcResult = mockMvc.perform(get("/parking-lots")
//                .param("page", "1").param("pageSize", "2"))
//                .andExpect(status().isOk()).andReturn();
//
//        JSONObject resultObject = new JSONObject(mvcResult.getResponse().getContentAsString());
//        Assertions.assertEquals(2,resultObject.getJSONArray("content").length());
//    }
//
//    @Test
//    public void should_update_parking_lot_when_patch() throws Exception {
//        ParkingLot parkingLot = new ParkingLot("停车场A", null, null);
//        MvcResult mvcResult = mockMvc.perform(patch("/parking-lots/" + parkingLots.get(0).getId())
//                .content(JSON.toJSONString(parkingLot)).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn();
//        JSONObject resultObject = new JSONObject(mvcResult.getResponse().getContentAsString());
//        // 修改的字段
//        Assertions.assertEquals(parkingLot.getParkingLotName(), resultObject.getString("parkingLotName"));
//        // 不修改的字段不变
//        Assertions.assertEquals(parkingLots.get(0).getParkingLotCapacity().intValue(), resultObject.getInt("parkingLotCapacity"));
//    }
//
//    @Test
//    public void should_return_filter_parking_lot_when_search() throws Exception {
//        MvcResult mvcResult = mockMvc.perform(get("/parking-lots").param("name", "2")
//                .param("page", "1").param("pageSize", "2"))
//                .andExpect(status().isOk()).andReturn();
//        JSONObject resultObject = new JSONObject(mvcResult.getResponse().getContentAsString());
//        Assertions.assertEquals(1,resultObject.getJSONArray("content").length());
//        Assertions.assertEquals("停车场2",resultObject.getJSONArray("content").getJSONObject(0).getString("parkingLotName"));
//    }
//
//    @Test
//    public void should_return_filter_parking_lot_when_search_with_capacity() throws Exception {
//        MvcResult mvcResult = mockMvc.perform(get("/parking-lots")
//                .param("min", "14").param("max","16")
//                .param("page", "1").param("pageSize", "2"))
//                .andExpect(status().isOk()).andReturn();
//        JSONObject resultObject = new JSONObject(mvcResult.getResponse().getContentAsString());
//        Assertions.assertEquals(1,resultObject.getJSONArray("content").length());
//    }
//}
