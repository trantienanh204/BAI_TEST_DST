package com.example.demo.Service;

import com.example.demo.DTO.userDTO;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<userDTO> timkiem() {
        List<Users> users = userRepository.findAll();

        List<userDTO> dtoList = users.stream().map(user -> {
            userDTO dto = new userDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setPassword(user.getPassword());
            return dto;
        }).collect(Collectors.toList());

        return dtoList;
    }


}
