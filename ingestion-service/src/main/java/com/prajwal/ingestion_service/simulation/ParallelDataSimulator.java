package com.prajwal.ingestion_service.simulation;

import com.prajwal.ingestion_service.dto.EnergyUsesDto;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Component
@Slf4j
public class ParallelDataSimulator implements CommandLineRunner {

    private  final RestClient restClient=RestClient.create();
    private final Random random= new Random();
    private ExecutorService executorService;

    @Value("${simulation.parallel-threads}")
    private int parallelThreads;

    @Value("${simulation.requests-per-interval}")
    private int requestsPerInterval;

    @Value("${simulation.endpoint}")
    private String ingestionEndpoint;

    @Override
    public void run(String... args) throws Exception {
        log.info("Parrallel data simulator is starrted");
        ((ThreadPoolExecutor)executorService).setCorePoolSize(parallelThreads);
    }
    @Scheduled(fixedRateString = "${simulation.interval-ms}")
    public void sendMockData() {
        int batchSize = requestsPerInterval / parallelThreads;
        int remainder = requestsPerInterval % parallelThreads;

        for (int i = 0; i < parallelThreads; i++) {
            int requestsForThread = batchSize + (i < remainder ? 1 : 0);
            executorService.submit(() -> {
                for (int j = 0; j < requestsForThread; j++) {
               EnergyUsesDto energyUsesDto = EnergyUsesDto.builder()
                            .deviceId(random.nextLong(1, 200))
                            .energyConsumed(Math.round(random.nextDouble(0.0, 2.0) * 100.0) / 100.0)
                            .timestamp(LocalDateTime.now()
                                    .atZone(ZoneId.systemDefault()).toInstant())
                            .build();
                    try {
                        restClient.post()
                                .uri(ingestionEndpoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(energyUsesDto)
                                .retrieve()
                                .toBodilessEntity();
                        log.info("sent mock data :{}",energyUsesDto);
                    } catch (Exception e) {
                        log.error("Failed to send data: " + e.getMessage());
                    }
                }
            });
        }
    }
    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
        log.info("ParallelDataSimulator shut down.");
    }

}
