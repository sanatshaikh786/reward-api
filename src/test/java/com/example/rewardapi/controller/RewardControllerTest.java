
package com.example.rewardapi.controller;

import com.example.rewardapi.service.RewardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RewardController.class)
public class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @Test
    public void whenMissingCustomerId_thenBadRequest() throws Exception {
        mockMvc.perform(get("/api/rewards"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenValidRequest_thenOk() throws Exception {
        Mockito.when(rewardService.getRewards(Mockito.eq("C1"), Mockito.any(), Mockito.any()))
                .thenReturn(null);
        mockMvc.perform(get("/api/rewards").param("customerId","C1"))
                .andExpect(status().isOk());
    }
}
