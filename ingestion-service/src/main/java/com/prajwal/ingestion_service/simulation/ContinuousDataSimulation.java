package com.prajwal.ingestion_service.simulation;

import com.prajwal.ingestion_service.dto.EnergyUsesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

@Slf4j
@Controller
public class ContinuousDataSimulation implements CommandLineRunner {

    private final Random random=new Random();
    private final RestClient restClient=RestClient.create();

   @Value("${simulation.requests-per-interval}")
    private int requestPerInterval ;
   @Value("${simulation.endpoint}")
   private String ingestionEndpoint;

    @Override
    public void run(String... args) throws Exception {
     log.info("Continuous datasimulator Started");
    }

    @Scheduled(fixedRateString = "${simulation.interval-ms}")
    public void sendMockData(){
        for(int i=0;i<requestPerInterval;i++){
            EnergyUsesDto energyUsesDto=EnergyUsesDto.builder()
                    .deviceId(random.nextLong(1,9))
                    .energyConsumed(Math.round(random.nextDouble(0.0, 2.0) * 100.0) / 100.0)
                    .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
                    .build();
            try{
                restClient.post()
                        .uri(ingestionEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(energyUsesDto)
                        .retrieve()
                        .toBodilessEntity();
                log.info("sent mock data :{}",energyUsesDto);
            }catch(Exception e){
                log.error("Failed to sent data",e);
            }
        }
    }
}
