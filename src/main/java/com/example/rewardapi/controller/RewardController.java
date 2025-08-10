
package com.example.rewardapi.controller;

import com.example.rewardapi.model.RewardResponse;
import com.example.rewardapi.service.RewardService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<RewardResponse> getRewards(
            @RequestParam @NotBlank(message = "customerId is required") String customerId,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        // default last 3 months if start/end not provided
        if (start == null || end == null) {
            end = LocalDate.now();
            start = end.minus(3, ChronoUnit.MONTHS);
        }

        RewardResponse resp = rewardService.getRewards(customerId, start, end);
        return ResponseEntity.ok(resp);

    }
}
