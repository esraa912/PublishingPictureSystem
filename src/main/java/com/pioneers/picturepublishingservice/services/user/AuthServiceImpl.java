package com.pioneers.picturepublishingservice.services.user;

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
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
                .ifPresent(user -> throwRegisterException(methodName, "Email is already used in system"));

        final User user = UserMapper.toNewUser(userSignup);
        log.debug("{} - Converted userSignup to User entity", methodName);

        userRepository.save(user);
        log.debug("{} - User registered successfully with email: {}", methodName, user.getEmail());
    }

    private static void throwRegisterException(final String methodName, final String errorMessage) {
        Object[] args = new Object[]{methodName, errorMessage};
        log.error("{}, {}", args);
        throw new RegisterException(errorMessage);
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
                final String[] passwordArgsErrorLogs = new String[]{methodName, "Password is incorrect"};
                log.error("{}, {}", passwordArgsErrorLogs);
                throw new LoginException("Email or password incorrect");
            }
        } catch (CredentialsException e) {
            log.error("{} - Cannot hash/verify password for email: {}", methodName, userLogin.email());
            throw new LoginException("Cannot hash the plain text password");
        }

        if (foundUser.isLogin()) {
            final String errorDetails = "User with email: " + userLogin.email() + " is already login";
            final String[] loginArgsErrorLogs = new String[]{methodName, errorDetails};
            log.error("{}, {}", loginArgsErrorLogs);

            throw new LoginException(errorDetails);
        }

        foundUser.setLogin(true);
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
                .orElseThrow(() -> new LogoutException("User with id: [" + id + "is not found"));

        if (!foundUser.isLogin()) {
            final String errorDetail = "User with id: [" + foundUser.getId() + "] is not login";
            log.error("{}, {}", methodName, errorDetail);

            throw new LogoutException(errorDetail);
        }

        foundUser.setLogin(false);
        userRepository.save(foundUser);

        log.info("{} - User logout successful{}", methodName);
    }
}
