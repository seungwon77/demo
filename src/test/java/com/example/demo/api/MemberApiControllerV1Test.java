package com.example.demo.api;

import com.example.demo.domain.ApiResponseV1;
import com.example.demo.domain.MemberV1;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberApiControllerV1Test {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private MemberApiControllerV1 memberApiControllerV1;

    @Autowired
    private ObjectMapper objectMapper;

    private Gson gson = new Gson();

    @Test
    void findMemberTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/v1/member/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();
        String resultJson = result.getResponse().getContentAsString();
        ApiResponseV1<MemberV1> responseV1 = objectMapper.readValue(resultJson, new TypeReference<ApiResponseV1<MemberV1>>() {
        });
        Assertions.assertEquals(1, responseV1.getData().getMemberNo());
    }

    @Test
    void createMemberTest() throws Exception {
        MemberV1 memberV1 = this.getMemberV1();
        String jsonMember = gson.toJson(memberV1);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/v1/member")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonMember)
                ).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();
        String resultJson = result.getResponse().getContentAsString();
        ApiResponseV1 responseV1 = objectMapper.readValue(resultJson, new TypeReference<ApiResponseV1>() {
        });
        Assertions.assertEquals("200", responseV1.getCode());
    }


    private MemberV1 getMemberV1() {
        return MemberV1.builder()
                .memberId("test001")
                .password("password1!")
                .email("test@test.com")
                .cellNo("010-1234-4321")
                .build();
    }
}
