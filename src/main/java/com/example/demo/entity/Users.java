package com.example.demo.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
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

    @NotBlank(message = "Tên không được để trống")
    private String name;

    @NotBlank(message = "Tên người dùng không được để trống")
    @Indexed(unique = true)
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    @Indexed(unique = true)
    private String email;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    private String avatar;

    @NotEmpty(message = "Vai trò không được để trống")
    private List<String> roles = List.of("USER");

    private Boolean status = true;
    private Boolean delete = false;

    @CreatedDate
    private LocalDate ngayTao = LocalDate.now();
    @LastModifiedDate
    private LocalDate ngaySua;
}