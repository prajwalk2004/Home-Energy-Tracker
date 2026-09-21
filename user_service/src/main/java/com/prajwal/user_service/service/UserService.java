package com.prajwal.user_service.service;

import com.prajwal.user_service.dto.userDto;
import com.prajwal.user_service.entity.Users;
import com.prajwal.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public userDto createuser(userDto UserDto){
      final   Users create_user= Users.builder()
              .name(UserDto.getName())
              .email(UserDto.getEmail())
              .address(UserDto.getAddress())
              .alerting(UserDto.isAlerting())
              .energyAlertingThreshold(UserDto.getEnergyAlertingThreshold())
              .build();

      Users save=userRepository.save(create_user);
      return toDto(save);
    }

    public userDto getUserById(Long id){
        return userRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }
    public void updateUser(Long id, userDto dto) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setAlerting(dto.isAlerting());
        user.setEnergyAlertingThreshold(dto.getEnergyAlertingThreshold());

        userRepository.save(user);
    }
    public void deleteUserById(Long id){
        Users user=userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("no user found by this id"));
        userRepository.delete(user);
    }

    private userDto toDto(Users user) {
        return userDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .address(user.getAddress())
                .alerting(user.isAlerting())
                .energyAlertingThreshold(user.getEnergyAlertingThreshold())
                .build();
    }
}
