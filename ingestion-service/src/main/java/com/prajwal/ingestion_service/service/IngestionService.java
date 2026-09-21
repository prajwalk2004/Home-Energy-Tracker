package com.prajwal.ingestion_service.service;


import com.prajwal.ingestion_service.dto.EnergyUsesDto;
import com.prajwal.ingestion_service.kafka.event.EnergyUsageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class IngestionService {
    private final KafkaTemplate<String, EnergyUsageEvent> kafkaTemplate;

    public void ingestEnergyUsage(EnergyUsesDto input){
        EnergyUsageEvent energyUsageEvent=EnergyUsageEvent.builder()
                .deviceId(input.deviceId())
                .energyConsumed(input.energyConsumed())
                .timestamp(input.timestamp())
                .build();
        kafkaTemplate.send("energy-usage",energyUsageEvent);
        log.info("ingesed Energy usage event",energyUsageEvent);
    }
}
