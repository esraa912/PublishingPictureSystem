package com.pioneers.picturepublishingservice.controllers.user;

import com.pioneers.picturepublishingservice.models.dtos.requests.UserLogin;
import com.pioneers.picturepublishingservice.models.dtos.requests.UserSignup;
import com.pioneers.picturepublishingservice.services.user.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Contains APIs for Authentications for all users in our system.
 */
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    public final AuthService authService;

    /**
     * Register a user to our system.
     *
     * @param userSignup DTO that includes the information to sign up the user.
     */
    @PostMapping("/register")
    public void registrationApi(@RequestBody final UserSignup userSignup){
       authService.registerUser(userSignup);
       log.info("User registered successfully");
    }

    /**
     * Login a user into our system.
     *
     * @param userLogin DTO that includes the information to log in the user.
     */
    @PostMapping("/login")
    public void loginApi(@RequestBody final UserLogin userLogin){
        authService.loginUser(userLogin);
        log.info("User login successfully");
    }

    /**
     * Logout a user from our system.
     *
     * @param id The unique identifier of the user to log out.
     */
    @PostMapping("/logout")
    public void logoutApi(@RequestParam final UUID id){
         authService.logoutUser(id);
        log.info("User logout successfully");
    }
}
