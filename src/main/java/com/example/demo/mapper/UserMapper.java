package com.example.demo.mapper;


import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Users;
import com.example.demo.service.UserService;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    @Mapping(target = "id", source = "id")
//    @Mapping(target = "name", source = "name")
//    @Mapping(target = "username", source = "username")
//    @Mapping(target = "email", source = "email")
//    @Mapping(target = "phone", source = "phone")
//    @Mapping(target = "avatar", source = "avatar")
//    @Mapping(target = "roles", source = "roles")
//    @Mapping(target = "status", source = "status")
//    @Mapping(target = "delete", source = "delete")
//    @Mapping(target = "ngayTao", source = "ngayTao")
//    @Mapping(target = "ngaySua", source = "ngaySua")
    UserDTO toDto(Users user);

    Users userRegister (RegisterRequestDTO registerRequestDTO);
}

