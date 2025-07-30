
package com.example.rewardapi.controller;

import com.example.rewardapi.model.RewardResponse;
import com.example.rewardapi.service.RewardService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping("/{customerId}")
    public RewardResponse getRewards(@PathVariable String customerId,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return rewardService.calculateRewards(customerId, start, end);
    }
}
