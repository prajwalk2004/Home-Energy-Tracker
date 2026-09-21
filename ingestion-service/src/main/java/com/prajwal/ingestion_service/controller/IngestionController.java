package com.prajwal.ingestion_service.controller;

import com.prajwal.ingestion_service.dto.EnergyUsesDto;
import com.prajwal.ingestion_service.service.IngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingestion")
@RequiredArgsConstructor
public class IngestionController {
  private final IngestionService ingestionService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void ingestData(@RequestBody EnergyUsesDto energyUsesDto){
      ingestionService.ingestEnergyUsage(energyUsesDto);
  }
}
