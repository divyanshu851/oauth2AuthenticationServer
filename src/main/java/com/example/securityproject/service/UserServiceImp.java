package com.example.securityproject.service;

import com.example.securityproject.dto.LoginRequestDto;
import com.example.securityproject.dto.SignUpRequestDto;
import com.example.securityproject.dto.UserDto;
import com.example.securityproject.entities.Role;
import com.example.securityproject.entities.User;
import com.example.securityproject.exception.InvalidCredentials;
import com.example.securityproject.exception.UserNotFoundException;
import com.example.securityproject.repository.RoleRepository;
import com.example.securityproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.securityproject.exception.RoleNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImp {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public UserDto signup(SignUpRequestDto signUpRequestDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setPassword(encoder.encode(signUpRequestDto.getPassword()));
        user.setEmail(signUpRequestDto.getEmail());
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }

    public UserDto login(LoginRequestDto loginRequestDto) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User savedUser = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(()->new UserNotFoundException("User with this gmailid is not found in the repository"));
        if(bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), savedUser.getPassword())){
            String userData  = savedUser.getEmail() + savedUser.getPassword() + LocalDate.now();
            String token = bCryptPasswordEncoder.encode(userData);
        }else{
            throw new InvalidCredentials("Password is not correct");
        }
        savedUser = userRepository.save(savedUser);
        return UserDto.from(savedUser);
    }

    public List<User> getUser(){
        return userRepository.findAll();
    }

//    public boolean validateToken(String token) {
//        User savedUser = userRepository.findByToken(token).orElseThrow(()->new InvalidCredentials("token is not valid"));
//        return true;
//    }
//
//    public boolean logout(String token) {
//        User saveedUser = userRepository.findByToken(token).orElseThrow(()->new InvalidCredentials("Token is not valid"));
//        userRepository.save(saveedUser);
//        return true;
//    }
}
