package com.example.securityproject.controller;


import com.example.securityproject.dto.LoginRequestDto;
import com.example.securityproject.dto.SignUpRequestDto;
import com.example.securityproject.dto.UserDto;
import com.example.securityproject.entities.User;
import com.example.securityproject.service.UserServiceImp;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImp userService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto requestDto){
        UserDto responseDto = userService.login(requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getUser(){
        return ResponseEntity.ok(userService.getUser());
    }

//    @GetMapping("/logout")
//    public ResponseEntity<Boolean> logout(@RequestHeader("Authorisation") String authToken){
//        return ResponseEntity.ok(userService.logout(authToken));
//    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpRequestDto signUpRequestDto){
        UserDto responseDto = userService.signup(signUpRequestDto);
        return ResponseEntity.ok(responseDto);
    }

//    @GetMapping("/validate")
//    public ResponseEntity<Boolean> validate(@RequestHeader("auth_token") String authToken){
//        return ResponseEntity.ok(userService.validateToken(authToken));
//   }
}
