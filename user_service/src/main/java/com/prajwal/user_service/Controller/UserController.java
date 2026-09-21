package com.prajwal.user_service.Controller;

import com.prajwal.user_service.dto.userDto;
import com.prajwal.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<userDto> createUsers(@RequestBody userDto UserDto){
          userDto created=userService.createuser(UserDto);
          return new ResponseEntity<userDto>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<userDto> getUserById(@PathVariable Long user_id ){
        userDto UserDto =userService.getUserById(user_id);
        if(UserDto==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(UserDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,
                                             @RequestBody userDto UserDto) {
        try {
            userService.updateUser(id, UserDto);
            return ResponseEntity.ok("User updated successfully");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteUserById(@PathVariable Long user_id){
        try{
            userService.deleteUserById(user_id);
            return ResponseEntity.noContent().build();
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
