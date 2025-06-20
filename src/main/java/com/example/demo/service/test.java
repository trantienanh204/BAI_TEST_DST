package com.example.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class test {


        public static void main(String[] args) {
            // Tạo một instance của BCryptPasswordEncoder
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // Mật khẩu gốc cần mã hóa
            String rawPassword = "123";

            // Mã hóa mật khẩu
            String encodedPassword = passwordEncoder.encode(rawPassword);

            // In ra mật khẩu đã mã hóa
            System.out.println("Mật khẩu gốc: " + rawPassword);
            System.out.println("Mật khẩu đã mã hóa: " + encodedPassword);

            // Kiểm tra tính hợp lệ (so sánh mật khẩu gốc với mật khẩu đã mã hóa)
            boolean isMatch = passwordEncoder.matches(rawPassword, encodedPassword);
            System.out.println("Mật khẩu khớp: " + isMatch);
        }
    }

