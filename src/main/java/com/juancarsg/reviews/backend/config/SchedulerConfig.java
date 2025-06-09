package com.juancarsg.reviews.backend.config;

import com.juancarsg.reviews.backend.service.CommerceStatsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerConfig {

    private final CommerceStatsService commerceStatsService;

    public SchedulerConfig(CommerceStatsService commerceStatsService) {
        this.commerceStatsService = commerceStatsService;
    }

    @Scheduled(initialDelay = 1 * 10 * 1000, fixedDelay = 1 * 60 * 1000)
    public void updateCommerceStats() {
        commerceStatsService.recalculateCommerceStats();
    }

}
