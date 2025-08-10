
package com.example.rewardapi.service;

import com.example.rewardapi.entity.TransactionEntity;
import com.example.rewardapi.model.RewardResponse;
import com.example.rewardapi.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RewardServiceTest {

    @Test
    public void calculateRewards() {
        TransactionRepository repo = Mockito.mock(TransactionRepository.class);
        Mockito.when(repo.findByCustomerId(Mockito.eq("C1")))
                .thenReturn(List.of(new TransactionEntity("T1","C1", LocalDate.of(2025,5,10), 120)));

        RewardService s = new RewardService(repo);
        RewardResponse resp = s.getRewards("C1", null, null);
        assertNotNull(resp);
        assertEquals(1, resp.getMonthlyRewards().size());
    }
}
