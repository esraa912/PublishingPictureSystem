package com.pioneers.picturepublishingservice.controllers.auth;

import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pioneers.picturepublishingservice.models.dtos.requests.UserLogin;
import com.pioneers.picturepublishingservice.models.dtos.requests.UserSignup;
import com.pioneers.picturepublishingservice.services.user.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Contains APIs for Authentications for all users in our system.
 *
 * @author esraa
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    public final AuthService authService;

    /**
     * Register a user to our system.
     *
     * @param userSignup DTO that includes the information to sign up the user.
     */
    @PostMapping("/register")
    public void registrationApi(@Valid @RequestBody final UserSignup userSignup) {
        final String methodName = "registrationApi()";
        log.debug("{} - Registering user with email= {}", methodName, userSignup.email());

        authService.registerUser(userSignup);

        log.info("{} - User registered successfully with email= {}", methodName, userSignup.email());
    }

    /**
     * Login a user into our system.
     *
     * @param userLogin DTO that includes the information to log in the user.
     */
    @PostMapping("/login")
    public void loginApi(@RequestBody final UserLogin userLogin) {
        final String methodName = "loginApi()";
        log.debug("{} - Attempting login for user: {}", methodName, userLogin.email());

        authService.loginUser(userLogin);

        log.info("{} - User login successfully with username: {}", methodName, userLogin.email());
    }

    /**
     * Logout a user from our system.
     *
     * @param id The unique identifier of the user to log out.
     */
    @PostMapping("/logout")
    public void logoutApi(@RequestParam final UUID id) {
        final String methodName = "logoutApi()";
        log.debug("{} - Logging out user with id: {}", methodName, id);

        authService.logoutUser(id);

        log.info("{} - User logout successfully with id: {}", methodName, id);
    }
}
