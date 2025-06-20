package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    private String id;
    private String name;
    private String username;
    private String password;
    private String email;
    private Integer phone;
    private String avatar;
    private String roles;
    private Boolean status;
    private Boolean delete;
    private LocalDate ngayTao;
    private LocalDate ngaySua;
}
