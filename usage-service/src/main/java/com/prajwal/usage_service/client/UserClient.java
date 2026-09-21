package com.prajwal.usage_service.client;

import com.prajwal.usage_service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class UserClient {
    private  RestClient restClient;
    public UserClient(@Value("${user.service.url}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public UserDto getUserById(Long userId){
        UserDto user= restClient.get()
                .uri("/{userId}")
                .retrieve()
                .body(UserDto.class);
        return user;
    }

}
