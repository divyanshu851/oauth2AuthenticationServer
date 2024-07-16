package com.example.securityproject.service;


import com.example.securityproject.dto.UserDto;
import com.example.securityproject.entities.Session;
import com.example.securityproject.entities.SessionStatus;
import com.example.securityproject.entities.User;
import com.example.securityproject.exception.UserAlreadyExistsException;
import com.example.securityproject.exception.UserNotFoundException;
import com.example.securityproject.repository.SessionRepository;
import com.example.securityproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;


    public ResponseEntity<UserDto> login(String email, String password) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with email: " + email + " doesn't exist.");
        }

        User user = userOptional.get();

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String token = RandomStringUtils.randomAscii(20);
        MultiValueMapAdapter<String, String> headers =  new MultiValueMapAdapter<>(new HashMap<>());
        headers.add("AUTH_TOKEN", token);

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);

        UserDto userDto = UserDto.from(user);
        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, headers, HttpStatus.OK);
        return response;
    }

    public ResponseEntity<Void> logout(String token, Long userId){
        Optional<Session> session = sessionRepository.findByTokenAndUser_Id(token, userId);
        if(session.isEmpty()){
           return null;
        }

        Session session1 = session.get();
        session1.setSessionStatus(SessionStatus.LOGGED_OUT);
        sessionRepository.save(session1);
        return ResponseEntity.ok().build();
    }

    public UserDto signUp(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserAlreadyExistsException("User with email: " + email + " already exists.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }

    public Optional<UserDto> validate(String token, Long userId) {
        Optional<Session> session = sessionRepository.findByTokenAndUser_Id(token, userId);
        if(session.isEmpty()){
            return Optional.empty();
        }
        Session session1 = session.get();

        if(!session1.getSessionStatus().equals(SessionStatus.ACTIVE)){
            return Optional.empty();
        }

        User user = userRepository.findById(userId).get();
        UserDto userDto = UserDto.from(user);
        return Optional.of(userDto);
    }




}
