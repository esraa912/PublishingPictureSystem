package com.pioneers.picturepublishingservice.controllers;

import com.pioneers.picturepublishingservice.models.dtos.requests.UserLogin;
import com.pioneers.picturepublishingservice.models.dtos.requests.UserSignup;
import com.pioneers.picturepublishingservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    public final AuthService authService;

    @PostMapping("/register")
    public void registrationApi(@RequestBody final UserSignup user){
       authService.registerUser(user);
       log.info("User registered successfully");
    }

    @GetMapping("/login")
    public void loginApi(@RequestBody final UserLogin user){
        authService.loginUser(user);
    }

    @PostMapping("/logout")
    public void loginApi(@RequestParam final UUID id){
         authService.logoutUser(id);
    }
}
