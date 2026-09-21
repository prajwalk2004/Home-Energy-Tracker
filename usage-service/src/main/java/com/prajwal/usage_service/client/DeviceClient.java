package com.prajwal.usage_service.client;

import com.prajwal.usage_service.dto.DeviceDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class DeviceClient {
    private  RestClient restClient;

    public DeviceClient(@Value("${device.service.url}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public DeviceDto getDeviceById(Long deviceId){
        return restClient.get()
                .uri("{/deviceId}",deviceId)
                .retrieve()
                .body(DeviceDto.class);

    }

    public List<DeviceDto> geAllDeviceForUser(Long UserId){
        DeviceDto[] devices=restClient.get()
                .uri("/user/{userId}",UserId)
                .retrieve()
                .body(DeviceDto[].class);
        return devices == null ? List.of() : Arrays.asList(devices);
    }
}
