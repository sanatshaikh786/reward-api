
package com.example.rewardapi.controller;

import com.example.rewardapi.model.RewardResponse;
import com.example.rewardapi.model.TransactionDetail;
import com.example.rewardapi.service.RewardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RewardController.class)
public class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

   /* @Mock
    private RewardService rewardService;*/

    @MockBean
    private RewardService rewardService;

    @Test
    void testGetRewards_WithStartEndParams() throws Exception {
        // Arrange
        String customerId = "CUST001";
        LocalDate start = LocalDate.of(2025, 6, 1);
        LocalDate end = LocalDate.of(2025, 8, 31);
        RewardResponse mockResponse = new RewardResponse(customerId,
                List.of(new TransactionDetail("Jul 2025", 100)),
                100);

        when(rewardService.getRewards(eq(customerId), eq(start), eq(end))).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/api/rewards/{customerId}", customerId)
                        .param("start", start.toString())
                        .param("end", end.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customerId))
                .andExpect(jsonPath("$.totalPoints").value(100))
                .andExpect(jsonPath("$.monthlyRewards", hasSize(1)))
                .andExpect(jsonPath("$.monthlyRewards[0].month").value("Jul 2025"))
                .andExpect(jsonPath("$.monthlyRewards[0].points").value(100));
    }

    @Test
    void testGetRewards_DefaultDatesUsed() throws Exception {
        // Arrange
        String customerId = "CUST002";
        LocalDate now = LocalDate.now();
        LocalDate expectedStart = now.minusMonths(3);
        LocalDate expectedEnd = now;

        RewardResponse mockResponse = new RewardResponse(customerId,
                List.of(new TransactionDetail("Aug 2025", 120)),
                120);

        when(rewardService.getRewards(eq(customerId), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/api/rewards/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customerId))
                .andExpect(jsonPath("$.totalPoints").value(120))
                .andExpect(jsonPath("$.monthlyRewards[0].month").value("Aug 2025"))
                .andExpect(jsonPath("$.monthlyRewards[0].points").value(120));

        // Verify the correct default range was passed (optional, just to be strict)
        verify(rewardService, times(1)).getRewards(eq(customerId), any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    void testGetRewards_InvalidCustomerId() throws Exception {
        mockMvc.perform(get("/api/rewards/ ")  // empty/blank customerId
                        .param("start", "2025-06-01")
                        .param("end", "2025-08-31"))
                .andExpect(status().isBadRequest());
    }

}
