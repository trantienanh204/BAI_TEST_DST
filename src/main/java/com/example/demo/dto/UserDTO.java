package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    @NotBlank(message = "Tên không được để trống")
    private String name;
    @NotBlank(message = "Tên người dùng không được để trống")
    private String username;
    private String password;
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String email;
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Số điện thoại không hợp lệ")
    private Integer phone;
    private String avatar;
    @NotEmpty(message = "Vai trò không được để trống")
    private String Roles;
    private Boolean status;
    private Boolean delete;
    private LocalDate ngayTao;
    private LocalDate ngaySua;
}
