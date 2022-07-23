package com.example.testnetris.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CameraDataControllersTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void getAllCamerasData() throws Exception {
        this.mockMvc.perform(get("/v1/cameras"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("length()").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].urlType").value("LIVE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].videoUrl").value("rtsp://127.0.0.1/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].value").value("fa4b588e-249b-11e9-ab14-d663bd873d93"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ttl").value(120));
    }

}