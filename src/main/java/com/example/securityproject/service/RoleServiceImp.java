package com.example.securityproject.service;

import com.example.securityproject.entities.Role;
import com.example.securityproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp{
    @Autowired
    private RoleRepository roleRepository;


    public Role createRole(String name) {
        Role role = new Role();
        role.setName(name);
        return roleRepository.save(role);
    }
}
