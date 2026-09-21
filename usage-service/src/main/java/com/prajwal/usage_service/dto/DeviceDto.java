package com.prajwal.usage_service.dto;

public record DeviceDto(Long id,
                        String name,
                        String type,
                        String location,
                        Long userId,
                        Double energyConsumed) {
}
