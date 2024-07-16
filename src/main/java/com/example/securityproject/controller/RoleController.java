package com.example.securityproject.controller;

import com.example.securityproject.dto.CreateRoleRequestDto;
import com.example.securityproject.entities.Role;
import com.example.securityproject.service.RoleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleServiceImp roleService;

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDto requestDto){
        Role responseDto = roleService.createRole(requestDto.getName());
        return ResponseEntity.ok(responseDto);
    }
}
