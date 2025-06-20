package com.example.demo.mapper;


import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Users;
import com.example.demo.service.UserService;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper  {
    UserDTO toDto (Users user);
}
