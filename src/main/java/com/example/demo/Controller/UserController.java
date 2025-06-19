package com.example.demo.Controller;

import com.example.demo.DTO.userDTO;
import com.example.demo.Service.UserService;
import com.example.demo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping()
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/test")
    public List<userDTO> addtest(){

    return userService.timkiem();
    }

}
