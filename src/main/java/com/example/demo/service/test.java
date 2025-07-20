//package com.example.demo.service;
//
//import com.example.demo.dto.UserDTO;
//import com.example.demo.entity.Users;
//import com.example.demo.mapper.UserMapper;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//public class test {
//    @Autowired
//    private UserMapper userMapper;
//
//        public static void main(String[] args) {
//            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            String rawPassword = "123";
//            String encodedPassword = passwordEncoder.encode(rawPassword);
//            System.out.println("Mật khẩu đã mã hóa: " + encodedPassword);
////
//        }
//
//
//    @PostConstruct
//    public void testMapStruct() {
//        Users test = new Users();
//        test.setId("1");
//        test.setName("Mapstruct test");
//        test.setUsername("test_user");
//        test.setEmail("test@example.com");
//
//        UserDTO dto = userMapper.toDto(test);
//        System.out.println("DTO: " + dto);
//    }
//
//}
//
