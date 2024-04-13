package com.common.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    private PasswordEncoder passwordEncoder;

    public String encodePassword(String password){
        return passwordEncoder.encode(password);
    }
}
