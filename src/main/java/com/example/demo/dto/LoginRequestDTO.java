package com.example.demo.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "Tên người dùng không được để trống")
    private String username;
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;


}
