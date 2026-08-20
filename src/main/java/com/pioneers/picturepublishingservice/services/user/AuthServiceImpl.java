package com.pioneers.picturepublishingservice.services.user;

import java.util.UUID;
import java.util.function.Function;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pioneers.picturepublishingservice.errors.exceptions.CredentialsException;
import com.pioneers.picturepublishingservice.errors.exceptions.LoginException;
import com.pioneers.picturepublishingservice.errors.exceptions.LogoutException;
import com.pioneers.picturepublishingservice.errors.exceptions.RegisterException;
import com.pioneers.picturepublishingservice.models.dtos.requests.UserLogin;
import com.pioneers.picturepublishingservice.models.dtos.requests.UserSignup;
import com.pioneers.picturepublishingservice.models.entities.User;
import com.pioneers.picturepublishingservice.repositories.UserRepository;
import com.pioneers.picturepublishingservice.utils.CredentialsHelper;
import com.pioneers.picturepublishingservice.utils.mappers.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link AuthService} that provides authentication
 * operations for user management.
 *
 * @author esraa
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final HttpSession httpSession;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void registerUser(UserSignup userSignup) {
        final String methodName = "registerUser()";
        log.debug("{} - Attempting to register user with email: {}", methodName, userSignup.email());

        userRepository.findByEmail(userSignup.email())
                .ifPresent(user ->
                        logAndThrow(methodName, "Email is already used in system", RegisterException::new)
                );

        final User user = UserMapper.toUser(userSignup);
        log.debug("{} - Converted userSignup to User entity", methodName);

        userRepository.save(user);
        log.debug("{} - User registered successfully with email: {}", methodName, user.getEmail());
    }

    @Transactional
    @Override
    public void loginUser(UserLogin userLogin) {
        final String methodName = "loginUser()";
        log.debug("{} - Attempting to login user with email: {}", methodName, userLogin.email());

        final User foundUser = userRepository.findByEmail(userLogin.email())
                .orElseThrow(() -> {
                    log.error("{} - User not found with email: {}", methodName, userLogin.email());
                    return new LoginException(String.format("User with email %s is not found", userLogin.email()));
                });

        try {
            final boolean isPasswordMatched =
                    CredentialsHelper.verifyPassword(userLogin.password(), foundUser.getPassword());
            log.debug("{} - Password matched: {}", methodName, isPasswordMatched);

            if (!isPasswordMatched) {
                logAndThrow(methodName, "Password is incorrect", LoginException::new);
            }
        } catch (CredentialsException e) {
            logAndThrow(methodName, "Cannot hash the plain text password", LoginException::new);
        }

        if (foundUser.isLogin()) {
            logAndThrow(methodName,
                    "User with email: " + userLogin.email() + " is already login", LoginException::new);
        }

        foundUser.login();
        httpSession.setAttribute("user_id", foundUser.getId());
        userRepository.save(foundUser);

        log.info("{} - User login successful with email: {}", methodName, userLogin.email());
    }

    @Override
    public UUID getCurrentUserId() {
        return (UUID) httpSession.getAttribute("user_id");
    }

    @Transactional
    @Override
    public void logoutUser(final UUID id) {
        final String methodName = "logoutUser()";
        log.debug("{} - Attempting to logout user", methodName);

        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new LogoutException("User with id: [" + id + "] is not found"));

        if (!foundUser.isLogin()) {
            logAndThrow(methodName,
                    "User with id: [" + foundUser.getId() + "] is not login", LogoutException::new);
        }

        foundUser.logout();
        userRepository.save(foundUser);

        log.info("{} - User logout successful", methodName);
    }

    private static <T extends RuntimeException> void logAndThrow(
            final String methodName,
            final String errorMessage,
            final Function<String, T> exceptionSupplier) {

        log.error("{} - {}", methodName, errorMessage);
        throw exceptionSupplier.apply(errorMessage);
    }
}
