package com.example.demo.Controller;

import com.example.demo.dto.*;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.info("Yêu cầu lấy danh sách người dùng");
        List<UserDTO> users = userService.getAllUsers();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        log.info("Yêu cầu đăng nhập cho người dùng: {}", loginRequestDTO.getUsername());
        try {
            LoginResponseDTO response = userService.login(loginRequestDTO);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Đăng nhập thất bại: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/users")
    public ResponseEntity<String> addUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        log.info("Yêu cầu thêm người dùng mới: {}", registerRequestDTO.getUsername());
        try {
            userService.addUser(registerRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Thêm người dùng thành công");
        } catch (IllegalArgumentException e) {
            log.error("Thêm người dùng thất bại: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @Valid @RequestBody UserDTO userDTO) {
        log.info("Yêu cầu cập nhật người dùng với id: {}", id);
        try {
            userService.updateUser(id, userDTO);
            return ResponseEntity.ok("Cập nhật người dùng thành công");
        } catch (Exception e) {
            log.error("Cập nhật thất bại: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        log.info("Yêu cầu xóa người dùng với id: {}", id);
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Xóa người dùng thành công");
        } catch (Exception e) {
            log.error("Không tìm thấy người dùng: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
//test quyền
    @GetMapping("/users/admin/test")
     @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminTest() {
        return ResponseEntity.ok("Chỉ admin mới thấy được!");
    }
    @GetMapping("/users/test")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> UserTest() {
        return ResponseEntity.ok("Chỉ admin mới thấy được!");
    }
}