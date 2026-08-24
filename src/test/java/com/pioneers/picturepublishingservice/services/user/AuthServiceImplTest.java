package com.pioneers.picturepublishingservice.services.user;

import java.util.Optional;
import java.util.UUID;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pioneers.picturepublishingservice.errors.exceptions.CredentialsException;
import com.pioneers.picturepublishingservice.errors.exceptions.LoginException;
import com.pioneers.picturepublishingservice.errors.exceptions.LogoutException;
import com.pioneers.picturepublishingservice.errors.exceptions.RegisterException;
import com.pioneers.picturepublishingservice.models.dtos.requests.UserLogin;
import com.pioneers.picturepublishingservice.models.dtos.requests.UserSignup;
import com.pioneers.picturepublishingservice.models.entities.User;
import com.pioneers.picturepublishingservice.models.enums.Role;
import com.pioneers.picturepublishingservice.repositories.UserRepository;
import com.pioneers.picturepublishingservice.utils.CredentialsHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private HttpSession httpSession;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void testLoginUserWhenEmailExistAndUserNotLoggedInAndPasswordAlignedThenLoginSuccessfully() {
        //Arrange
        final UserLogin userLogin = new UserLogin("esraa.foad@gmail.com", "Esraa123@");

        final String hashedPassword = CredentialsHelper.hashPassword(userLogin.password());

        final User foundUser = User.builder()
                .isLogin(false)
                .email(userLogin.email())
                .password(hashedPassword)
                .build();

        when(userRepository.findByEmail(userLogin.email())).thenReturn(Optional.of(foundUser));

        //Ack
        authService.loginUser(userLogin);

        //Assert
        assertTrue(foundUser.isLogin());
        verify(userRepository, times(1)).findByEmail(userLogin.email());
        verify(userRepository, times(1)).save(foundUser);
    }

    @Test
    void testLoginUserWhenEmailIsNotExistThenThrowLoginException() {
        //Arrange
        final UserLogin userLogin = new UserLogin("esraa.foad@gmail.com", "Esraa123@");

        when(userRepository.findByEmail(userLogin.email())).thenReturn(Optional.empty());

        //Ack & Assert
        assertThrows(LoginException.class, () -> authService.loginUser(userLogin));
        verify(userRepository, times(1)).findByEmail(userLogin.email());
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void testLoginUserWhenEmailExistAndUserNotLoggedInAndPasswordNotAlignedThenThrowLoginException() {
        //Arrange
        final UserLogin userLogin = new UserLogin("esraa.foad@gmail.com", "Esraa123@");

        final User foundUser = User.builder()
                .isLogin(false)
                .email(userLogin.email())
                .password("esraa123")
                .build();

        when(userRepository.findByEmail(userLogin.email())).thenReturn(Optional.of(foundUser));

        //Ack & Assert
        LoginException ex = assertThrows(LoginException.class, () -> authService.loginUser(userLogin));
        assertEquals("Email or password incorrect", ex.getMessage());
        assertFalse(foundUser.isLogin());
        verify(userRepository, times(1)).findByEmail(userLogin.email());
        verify(userRepository, times(0)).save(foundUser);
    }

    @Test
    void testLoginUserWhenUserExistAndLoggedInAndPasswordAlignedThenThrowLoginException() {
        //Arrange
        final UserLogin userLogin = new UserLogin("esraa.foad@gmail.com", "Esraa123@");

        final String hashedPassword = CredentialsHelper.hashPassword(userLogin.password());

        final User foundUser = User.builder()
                .isLogin(true)
                .email(userLogin.email())
                .password(hashedPassword)
                .build();

        when(userRepository.findByEmail(userLogin.email())).thenReturn(Optional.of(foundUser));

        //Ack & Assert
        LoginException ex = assertThrows(LoginException.class, () -> authService.loginUser(userLogin));
        assertTrue(ex.getMessage().contains("User with email:"));
        assertTrue(foundUser.isLogin());
        verify(userRepository, times(1)).findByEmail(userLogin.email());
        verify(userRepository, times(0)).save(foundUser);
    }

    @Test
    void testLoginUserWhenCredentialsHelperThrowsExceptionThenThrowCredentialsException() {
        //Arrange
        final UserLogin userLogin = new UserLogin("esraa.foad@gmail.com", "Esraa123@");

        final String hashedPassword = CredentialsHelper.hashPassword(userLogin.password());

        final User foundUser = User.builder()
                .isLogin(false)
                .email(userLogin.email())
                .password(hashedPassword)
                .build();

        when(userRepository.findByEmail(userLogin.email())).thenReturn(Optional.of(foundUser));

        try (MockedStatic<CredentialsHelper> mockedHelper = Mockito.mockStatic(CredentialsHelper.class)) {
            mockedHelper.when(() -> CredentialsHelper.verifyPassword(userLogin.password(), hashedPassword))
                    .thenThrow(new CredentialsException("Cannot hash the plain text password", "loginUser()"));

            //Ack & Assert
            LoginException ex = assertThrows(LoginException.class, () -> authService.loginUser(userLogin));
            assertEquals("Cannot hash the plain text password", ex.getMessage());

            verify(userRepository, times(1)).findByEmail(userLogin.email());
            verify(userRepository, never()).save(any());
        }
    }

    @Test
    void testRegisterUserWhenEmailNotExistThenUserRegisterSuccessfully() {
        //Arrange
        final UserSignup userSignup =
                new UserSignup("salma.mohamed@gmail.com", "salma mohamed", "Salma123@", Role.USER);

        when(userRepository.findByEmail(userSignup.email())).thenReturn(Optional.empty());

        //Ack
        authService.registerUser(userSignup);

        //Assert
        verify(userRepository, times(1)).findByEmail(userSignup.email());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void testRegisterUserWhenEmailExistThenThrowRegisterException() {
        //Arrange
        final UserSignup userSignup =
                new UserSignup("salma.mohamed@gmail.com", "salma mohamed", "Salma123@", Role.USER);

        final User foundUser = User.builder()
                .email(userSignup.email())
                .name(userSignup.name())
                .password(userSignup.password())
                .role(userSignup.role())
                .build();

        when(userRepository.findByEmail(userSignup.email())).thenReturn(Optional.of(foundUser));

        //Ack & Assert
        RegisterException ex = assertThrows(RegisterException.class, () -> authService.registerUser(userSignup));
        assertEquals("Email is already used in the system", ex.getMessage());
        verify(userRepository, times(1)).findByEmail(userSignup.email());
        verify(userRepository, times(0)).save(foundUser);
    }

    @Test
    void testLogoutUserWhenIdIsFoundThenLogoutSuccessfully() {
        //Arrange
        UUID id = UUID.randomUUID();
        final User foundUser = User.builder()
                .id(id)
                .isLogin(true)
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(foundUser));

        //Ack
        authService.logoutUser(id);

        //Assert
        assertFalse(foundUser.isLogin());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(foundUser);
    }

    @Test
    void testLogoutUserWhenIdIsNotFoundThenThrowLogoutException() {
        //Arrange
        UUID id = UUID.randomUUID();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        //Ack & Assert
        assertThrows(LogoutException.class, () -> authService.logoutUser(id));
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void testLogoutUserWhenIdIsFoundAndUserIsNotLoginThenThrowLogoutException() {
        //Arrange
        UUID id = UUID.randomUUID();
        final User foundUser = User.builder()
                .id(id)
                .isLogin(false)
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(foundUser));

        //Ack & Assert
        assertThrows(LogoutException.class, () -> authService.logoutUser(id));
        assertFalse(foundUser.isLogin());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(0)).save(any());
    }
}
