package com.juancarsg.reviews.backend.service.impl;

import com.juancarsg.reviews.backend.entity.Commerce;
import com.juancarsg.reviews.backend.entity.CommerceStat;
import com.juancarsg.reviews.backend.repository.CommerceRepository;
import com.juancarsg.reviews.backend.repository.CommerceStatRepository;
import com.juancarsg.reviews.backend.repository.ReviewRepository;
import com.juancarsg.reviews.backend.service.CommerceStatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Service
public class CommerceStatsServiceImpl implements CommerceStatsService {

    private final CommerceRepository commerceRepository;
    private final CommerceStatRepository commerceStatsRepository;
    private final ReviewRepository reviewRepository;

    public CommerceStatsServiceImpl(CommerceRepository commerceRepository, CommerceStatRepository commerceStatsRepository, ReviewRepository reviewRepository) {
        this.commerceRepository = commerceRepository;
        this.commerceStatsRepository = commerceStatsRepository;
        this.reviewRepository = reviewRepository;
    }

    public static final Logger LOGGER = LoggerFactory.getLogger(CommerceStatsServiceImpl.class);
    public static final int BATCH_SIZE = 100;
    public static final int SEMAPHORE_SIZE = 2;

    public void recalculateCommerceStats() {
        long init = System.currentTimeMillis();

        int batchSize = BATCH_SIZE;
        int page = 0;

        LocalDateTime now = LocalDateTime.now();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Semaphore semaphore = new Semaphore(SEMAPHORE_SIZE);

            while(true) {
                List<Commerce> commerces = commerceRepository.findAll(PageRequest.of(page, batchSize)).getContent();
                if (commerces.isEmpty()) {
                    break;
                }

                List<CompletableFuture<Void>> futures = new ArrayList<>();
                for (Commerce commerce : commerces) {
                    futures.add(CompletableFuture.runAsync(() -> {
                        try {
                            semaphore.acquire();
                            commerce.getCommerceStat().setAvgRating(reviewRepository.avgRatingByCommerceId(commerce.getId()));
                            commerce.getCommerceStat().setCountReviews(reviewRepository.countByCommerceId(commerce.getId()));
                            commerce.getCommerceStat().setLastUpdated(now);
                        } catch (InterruptedException e) {
                            LOGGER.error("Thread was interrupted while processing commerce {} stats.", commerce.getId());
                            Thread.currentThread().interrupt();
                        } catch (Exception e) {
                            LOGGER.error("An error has occurred calculating commerce {} stats.", commerce.getId());
                            Thread.currentThread().interrupt();
                        }
                        semaphore.release();
                    }, executor));
                }
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

                List<CommerceStat> commercesStats = commerces.stream().map(Commerce::getCommerceStat).toList();
                try {
                    commerceStatsRepository.saveAll(commercesStats);
                } catch (Exception e) {
                    LOGGER.error("An error has occurred updating stats. Error: {}", e.getMessage());
                }

                page++;
            }
        }

        long end = System.currentTimeMillis();
        LOGGER.info("Recalculated commerces stats with in {} ms.", end - init);
    }

}

