package com.pioneers.picturepublishingservice.services;

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

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Transactional
    @Override
    public String registerUser(UserSignup userSignup)  throws CredentialsException {

        final String methodName = "registerUser()";
        userRepository.findByEmail(userSignup.email())
                .ifPresent(user -> throwRegisterException(methodName, "Email is already used in system"));

        final User user = UserMapper.toNewUser(userSignup);
        log.debug("Converted userSignup to User entity");

        userRepository.save(user);
        log.debug("Successfully save user with id: {}", user.getId());
        return "User registered successfully";
    }

    private static void throwRegisterException(final String methodName, final String errorMessage) {
        Object[] args = new Object[]{methodName, errorMessage};
        log.error("{}, {}", args);
        throw new RegisterException(errorMessage);
    }

    @Transactional
    @Override
    public String loginUser(UserLogin userLogin) {

        final String methodName = "loginUser()";
        final User foundUser = userRepository.findByEmail(userLogin.email())
                .orElseThrow(() -> new LoginException(
                        String.format("User with email %s is not found",userLogin.email()))
    );

        try {
            final boolean isPasswordMatched =
                    CredentialsHelper.verifyPassword(userLogin.password(), foundUser.getPassword());
            log.debug(String.valueOf(isPasswordMatched));

            if (!isPasswordMatched) {
                final String[] passwordArgsErrorLogs = new String[]{methodName, "Password is incorrect"};
                log.error("{}, {}", passwordArgsErrorLogs);
                throw new LoginException("Email or password incorrect");
            }
        } catch (CredentialsException e) {
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
        return "Login successful";
    }

    @Transactional
    @Override
    public void logoutUser(final UUID id) {
        final String methodName = "logoutStudent()";
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new LogoutException("User with id: [" + id + "is not found"));

        if (!foundUser.isLogin()) {
            final String errorDetail = "User with id: [" + foundUser.getId() + "] is not login";
            log.error("{}, {}",  methodName, errorDetail);

            throw new LogoutException(errorDetail);
        }

        foundUser.setLogin(false);
        userRepository.save(foundUser);
    }
}
