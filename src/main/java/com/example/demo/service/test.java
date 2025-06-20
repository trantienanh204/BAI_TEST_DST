package com.example.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class test {


        public static void main(String[] args) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String rawPassword = "123";
            String encodedPassword = passwordEncoder.encode(rawPassword);
            System.out.println("Mật khẩu đã mã hóa: " + encodedPassword);
        }
    }

