package cn.tag.controller;

import cn.tag.entity.StaffCharacter;
import cn.tag.respository.StaffCharacterRepository;
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
public class StaffCharacterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StaffCharacterRepository staffCharacterRepository;

    @BeforeEach
    public void before_test(){
        staffCharacterRepository.save(new StaffCharacter("泊车仔"));
        staffCharacterRepository.save(new StaffCharacter("经理"));
    }

    @AfterEach
    public void after_test(){
        staffCharacterRepository.deleteAll();
    }

    @Test
    public void should_return_staff_character_list_when_query() throws Exception {
        //given
        List<StaffCharacter> staffCharacterList = staffCharacterRepository.findAll();
        //when
        ResultActions resultActions = this.mockMvc.perform(get("/staff-characters"));
        //then
        resultActions.andExpect(status().isOk());
        Assertions.assertEquals(staffCharacterList.get(0).getCharacterName()
            , JSONObject.parseObject(JSONArray.parseArray(resultActions.andReturn().getResponse().getContentAsString()).get(0).toString()).getString("characterName"));
    }

}
