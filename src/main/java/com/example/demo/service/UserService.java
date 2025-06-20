package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.Users;
import com.example.demo.Repository.UserRepository;
import com.example.demo.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        log.info("Đăng nhập: {}", loginRequestDTO.getUsername());
        Users user = userRepository.findByUsernameAndDeleteFalse(loginRequestDTO.getUsername());
        if (user == null || !passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Tên người dùng hoặc mật khẩu không đúng");
        }
        List<String> userRoles = user.getRoles() != null ? user.getRoles() : Collections.singletonList("USER");
        String token = jwtUtil.generateToken(user.getUsername(), userRoles);
        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        response.setUsername(user.getUsername());
        response.setRoles(userRoles);
        return response;
    }

    public void updateUser(String id, UserDTO userDTO) {
        log.info("Cập nhật người dùng với id: {}", id);
        Users user = userRepository.findById(id)
                .filter(u -> !u.getDelete())
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng với id: " + id));

        if (userDTO.getName() == null || userDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên không được để trống");
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setAvatar(userDTO.getAvatar());
        user.setRoles(userDTO.getRoles() != null ? userDTO.getRoles() : Collections.emptyList());
        user.setStatus(userDTO.getStatus() != null ? userDTO.getStatus() : true);
        user.setNgaySua(LocalDate.now());

        userRepository.save(user);
    }

    public void addUser(RegisterRequestDTO registerRequestDTO) {
        log.info("Thêm người dùng mới qua đăng ký: {}", registerRequestDTO.getUsername());

        if (registerRequestDTO.getUsername() == null || registerRequestDTO.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên người dùng không được để trống");
        }
        if (registerRequestDTO.getPassword() == null || registerRequestDTO.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống");
        }
        if (registerRequestDTO.getEmail() == null || registerRequestDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }

        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Email đã được sử dụng: " + registerRequestDTO.getEmail());
        }
        if (userRepository.existsByUsername(registerRequestDTO.getUsername())) {
            throw new IllegalArgumentException("Tên người dùng đã được sử dụng: " + registerRequestDTO.getUsername());
        }
        if (registerRequestDTO.getId() != null && !registerRequestDTO.getId().isEmpty() &&
                userRepository.existsById(registerRequestDTO.getId())) {
            throw new IllegalArgumentException("ID đã được sử dụng: " + registerRequestDTO.getId());
        }

        Users users = new Users();
        if (registerRequestDTO.getId() != null && !registerRequestDTO.getId().isEmpty()) {
            users.setId(registerRequestDTO.getId());
        }
        users.setName(registerRequestDTO.getName());
        users.setDelete(false);
        users.setUsername(registerRequestDTO.getUsername());
        users.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        users.setEmail(registerRequestDTO.getEmail());
        users.setPhone(registerRequestDTO.getPhone());
        users.setAvatar(registerRequestDTO.getAvatar());
        users.setRoles(registerRequestDTO.getRoles() != null ? registerRequestDTO.getRoles() : Collections.singletonList("USER"));
        users.setStatus(true);
        users.setNgayTao(LocalDate.now());
        userRepository.save(users);
    }

    public List<UserDTO> getAllUsers() {
        log.info("Lấy danh sách tất cả người dùng");
        return userRepository.findAll().stream()
                .filter(user -> !user.getDelete())
                .map(users -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(users.getId());
                    userDTO.setName(users.getName());
                    userDTO.setAvatar(users.getAvatar());
                    userDTO.setDelete(users.getDelete());
                    userDTO.setEmail(users.getEmail());
                    userDTO.setNgayTao(users.getNgayTao());
                    userDTO.setPhone(users.getPhone());
                    userDTO.setRoles(users.getRoles() != null ? users.getRoles() : Collections.emptyList());
                    userDTO.setStatus(users.getStatus());
                    userDTO.setUsername(users.getUsername());
                    userDTO.setNgaySua(users.getNgaySua());
                    return userDTO;
                })
                .collect(Collectors.toList());
    }

    public void deleteUser(String id) {
        log.info("Xóa mềm người dùng với id: {}", id);
        Users user = userRepository.findById(id)
                .filter(u -> !u.getDelete())
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng với id: " + id));
        user.setDelete(true);
        userRepository.save(user);
    }
}