package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.Users;
import com.example.demo.mapper.UserMapper;
import com.example.demo.Repository.UserRepository;
import com.example.demo.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final List<String> VALID_ROLES = Arrays.asList("USER", "ADMIN");

    @Autowired
    private UserRepository userRepository;


    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public UserDTO addUser(RegisterRequestDTO registerRequestDTO) {
        logger.info("Thêm người dùng mới qua đăng ký: {}", registerRequestDTO.getUsername());

        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Email đã được sử dụng: " + registerRequestDTO.getEmail());
        }
        if (userRepository.existsByUsername(registerRequestDTO.getUsername())) {
            throw new IllegalArgumentException("Tên người dùng đã được sử dụng: " + registerRequestDTO.getUsername());
        }

        Users user = userMapper.toEntity(registerRequestDTO);
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        Users savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public UserDTO addUser(UserDTO userDTO) {
        logger.info("Thêm người dùng mới qua API: {}", userDTO.getUsername());

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email đã được sử dụng: " + userDTO.getEmail());
        }
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Tên người dùng đã được sử dụng: " + userDTO.getUsername());
        }
        if (!VALID_ROLES.contains(userDTO.getRoles())) { // Sửa từ getRole thành getRoles
            throw new IllegalArgumentException("Vai trò không hợp lệ: " + userDTO.getRoles());
        }

        Users user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword() != null ? userDTO.getPassword() : "defaultPassword123"));
        user.setStatus(userDTO.getStatus() != null ? userDTO.getStatus() : true);
        user.setDelete(userDTO.getDelete() != null ? userDTO.getDelete() : false);
        user.setNgayTao(LocalDate.now());

        Users savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        logger.info("Đăng nhập: {}", loginRequestDTO.getUsername());
        Users user = userRepository.findByUsernameAndDeleteFalse(loginRequestDTO.getUsername());
        if (user == null || !passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Tên người dùng hoặc mật khẩu không đúng");
        }
        String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());
        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        response.setUsername(user.getUsername());
        response.setRoles(user.getRoles());
        return response;
    }

    public List<UserDTO> getAllUsers() {
        logger.info("Lấy danh sách tất cả người dùng");
        return userRepository.findAll().stream()
                .filter(user -> !user.getDelete())
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(String id, UserDTO userDTO) {
        logger.info("Cập nhật người dùng với id: {}", id);
        Users user = userRepository.findById(id)
                .filter(u -> !u.getDelete())
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng với id: " + id));

        if (!VALID_ROLES.contains(userDTO.getRoles())) { // Sửa từ getRole thành getRoles
            throw new IllegalArgumentException("Vai trò không hợp lệ: " + userDTO.getRoles());
        }

        user.setName(userDTO.getName()); // Thêm cập nhật name
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setAvatar(userDTO.getAvatar());
        user.setRoles(userDTO.getRoles());
        user.setStatus(userDTO.getStatus() != null ? userDTO.getStatus() : true);
        user.setNgaySua(LocalDate.now());

        Users updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    public void deleteUser(String id) {
        logger.info("Xóa mềm người dùng với id: {}", id);
        Users user = userRepository.findById(id)
                .filter(u -> !u.getDelete())
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng với id: " + id));
        user.setDelete(true);
        userRepository.save(user);
    }
}