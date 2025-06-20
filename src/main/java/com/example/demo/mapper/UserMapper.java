package com.example.demo.mapper;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(Users user);

    @Mapping(target = "password", ignore = true)
    Users toEntity(UserDTO userDTO);

    @Mapping(target = "roles", constant = "USER")
    @Mapping(target = "status", expression = "java(true)")
    @Mapping(target = "delete", expression = "java(false)")
    @Mapping(target = "ngayTao", expression = "java(java.time.LocalDate.now())")
    Users toEntity(RegisterRequestDTO registerRequestDTO);
}