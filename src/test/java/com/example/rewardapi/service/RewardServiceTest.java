
package com.example.rewardapi.service;

import com.example.rewardapi.entity.TransactionEntity;
import com.example.rewardapi.model.RewardResponse;
import com.example.rewardapi.model.TransactionDetail;
import com.example.rewardapi.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class RewardServiceTest {


    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardService rewardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRewardsByCustomerId() {
        // Given
        String customerId = "CUST001";
        TransactionEntity txn1 = new TransactionEntity("TXN1", customerId,  LocalDate.of(2025, 7, 15),120.0);
        TransactionEntity txn2 = new TransactionEntity("TXN2", customerId,  LocalDate.of(2025, 7, 20),80.0);

        when(transactionRepository.findByCustomerId(customerId))
                .thenReturn(Arrays.asList(txn1, txn2));

        // When
        RewardResponse response = rewardService.getRewards(customerId,null,null);

        // Check monthly reward details
        assertEquals(1, response.getMonthlyRewards().size());
        TransactionDetail julyReward = response.getMonthlyRewards().get(0);
        assertEquals("Jul 2025", julyReward.getMonth());

        int expectedTxn1Points = (2 * 20) + 50; // $120 → (120-100)*2 + (100-50)*1 = 40 + 50 = 90
        int expectedTxn2Points = 30;            // $80 → (80-50) = 30
        int expectedTotalPoints = expectedTxn1Points + expectedTxn2Points;

        assertEquals(expectedTxn1Points + expectedTxn2Points, julyReward.getPoints()); // should be 120
        assertEquals(expectedTotalPoints, response.getTotalPoints());
    }
    @Test
    void testGetRewardsByCustomerId_NoTransactions() {
        String customerId = "CUST002";
        when(transactionRepository.findByCustomerId(customerId)).thenReturn(Collections.emptyList());

        RewardResponse response = rewardService.getRewards(customerId, null, null);

        assertEquals(customerId, response.getCustomerId());
        assertEquals(0, response.getTotalPoints());
        assertTrue(response.getMonthlyRewards().isEmpty());
    }

    @Test
    void testTransactionExactly50_NoPoints() {
        String customerId = "CUST003";
        TransactionEntity txn = new TransactionEntity("TXN3", customerId, LocalDate.of(2025, 8, 1), 50.0);

        when(transactionRepository.findByCustomerId(customerId)).thenReturn(List.of(txn));

        RewardResponse response = rewardService.getRewards(customerId, null, null);

        assertEquals(0, response.getTotalPoints());
        assertEquals("Aug 2025", response.getMonthlyRewards().get(0).getMonth());
        assertEquals(0, response.getMonthlyRewards().get(0).getPoints());
    }
    @Test
    void testTransactionExactly100_50Points() {
        String customerId = "CUST004";
        TransactionEntity txn = new TransactionEntity("TXN4", customerId, LocalDate.of(2025, 6, 10), 100.0);

        when(transactionRepository.findByCustomerId(customerId)).thenReturn(List.of(txn));

        RewardResponse response = rewardService.getRewards(customerId, null, null);

        assertEquals(50, response.getTotalPoints());
        assertEquals("Jun 2025", response.getMonthlyRewards().get(0).getMonth());
        assertEquals(50, response.getMonthlyRewards().get(0).getPoints());
    }

    @Test
    void testTransactionsAcrossMultipleMonths() {
        String customerId = "CUST005";
        TransactionEntity txn1 = new TransactionEntity("TXN5", customerId, LocalDate.of(2025, 6, 15), 120.0); // 90 points
        TransactionEntity txn2 = new TransactionEntity("TXN6", customerId, LocalDate.of(2025, 7, 10), 90.0);  // 40 points
        TransactionEntity txn3 = new TransactionEntity("TXN7", customerId, LocalDate.of(2025, 8, 5), 55.0);   // 5 points

        when(transactionRepository.findByCustomerId(customerId)).thenReturn(Arrays.asList(txn1, txn2, txn3));

        RewardResponse response = rewardService.getRewards(customerId, null, null);

        assertEquals(3, response.getMonthlyRewards().size());
        assertEquals(135, response.getTotalPoints());

        Map<String, Integer> expected = Map.of(
                "Jun 2025", 90,
                "Jul 2025", 40,
                "Aug 2025", 5
        );

        for (TransactionDetail detail : response.getMonthlyRewards()) {
            assertEquals(expected.get(detail.getMonth()), detail.getPoints());
        }
    }

}
