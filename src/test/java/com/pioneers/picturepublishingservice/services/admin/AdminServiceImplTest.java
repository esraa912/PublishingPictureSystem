package com.pioneers.picturepublishingservice.services.admin;

import com.pioneers.picturepublishingservice.errors.exceptions.LoginException;
import com.pioneers.picturepublishingservice.errors.exceptions.LogoutException;
import com.pioneers.picturepublishingservice.models.dtos.requests.UserLogin;
import com.pioneers.picturepublishingservice.models.entities.User;
import com.pioneers.picturepublishingservice.repositories.UserRepository;
import com.pioneers.picturepublishingservice.utils.CredentialsHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    void testLoginAdmin_WhenEmailExistAndAdminNotLoggedInAndPasswordAligned_ThenAdminLoggedInSuccessfully() {
        //Arrange
        final UserLogin adminLogin = new UserLogin("esraa.foad@gmail.com", "Esraa123@");

        final User foundAdmin = User.builder()
                .isLogin(false)
                .email(adminLogin.email())
                .password(adminLogin.password())
                .build();

        when(userRepository.findByEmail(adminLogin.email())).thenReturn(Optional.of(foundAdmin));

        //Ack
        adminService.login(adminLogin);

        //Assert
        assertTrue(foundAdmin.isLogin());
        verify(userRepository, times(1)).findByEmail(adminLogin.email());
        verify(userRepository, times(1)).save(foundAdmin);
    }

    @Test
    void testLoginAdmin_WhenEmailIsNotExist_ThenThrowLoginException() {
        //Arrange
        final UserLogin adminLogin = new UserLogin("esraa.foad@gmail.com", "Esraa123@");

        when(userRepository.findByEmail(adminLogin.email())).thenReturn(Optional.empty());

        //Ack & Assert
        assertThrows(LoginException.class, () -> adminService.login(adminLogin));
        verify(userRepository, times(1)).findByEmail(adminLogin.email());
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void testLoginAdmin_WhenEmailExistAndAdminNotLoggedInAndPasswordNotAligned_ThenThrowLoginException() {
        //Arrange
        final UserLogin adminLogin = new UserLogin("esraa.foad@gmail.com", "Esraa123@");

        final User foundAdmin = User.builder()
                .isLogin(false)
                .email(adminLogin.email())
                .password("esraa123")
                .build();

        when(userRepository.findByEmail(adminLogin.email())).thenReturn(Optional.of(foundAdmin));

        //Ack & Assert
        LoginException ex = assertThrows(LoginException.class, () -> adminService.login(adminLogin));
        assertEquals("Email or password incorrect", ex.getMessage());
        assertFalse(foundAdmin.isLogin());
        verify(userRepository, times(1)).findByEmail(adminLogin.email());
        verify(userRepository, times(0)).save(foundAdmin);
    }

    @Test
    void testLoginAdmin_WhenEmailExistAndAdminLoggedInAndPasswordAligned_ThenThrowLoginException() {
        //Arrange
        final UserLogin adminLogin = new UserLogin("esraa.foad@gmail.com", "Esraa123@");

        final User foundAdmin = User.builder()
                .isLogin(true)
                .email(adminLogin.email())
                .password(adminLogin.password())
                .build();

        when(userRepository.findByEmail(adminLogin.email())).thenReturn(Optional.of(foundAdmin));

        //Ack & Assert
        LoginException ex = assertThrows(LoginException.class, () -> adminService.login(adminLogin));
        assertTrue(ex.getMessage().contains("Admin with email:"));
        assertTrue(foundAdmin.isLogin());
        verify(userRepository, times(1)).findByEmail(adminLogin.email());
        verify(userRepository, times(0)).save(foundAdmin);
    }

    @Test
    void testLoginAdmin_WhenCredentialsHelperThrowsException_ThenThrowLoginException() {
        //Arrange
        final UserLogin adminLogin = new UserLogin("esraa.foad@gmail.com", "Esraa123@");

        final User foundAdmin = User.builder()
                .isLogin(false)
                .email(adminLogin.email())
                .password(adminLogin.password())
                .build();

        when(userRepository.findByEmail(adminLogin.email())).thenReturn(Optional.of(foundAdmin));

        try (MockedStatic<CredentialsHelper> mockedHelper = Mockito.mockStatic(CredentialsHelper.class)) {
            mockedHelper.when(() -> CredentialsHelper.hashPassword(anyString()))
                    .thenThrow(new LoginException("Cannot hash the plain text password"));

            //Ack & Assert
            LoginException ex = assertThrows(LoginException.class, () -> adminService.login(adminLogin));
            assertEquals("Cannot hash the plain text password", ex.getMessage());

            verify(userRepository, times(1)).findByEmail(adminLogin.email());
            verify(userRepository, never()).save(any());
        }
    }

    @Test
    void testLogoutAdmin_WhenIdIsFound_ThenLogoutSuccessfully(){
        //Arrange
        UUID id = UUID.randomUUID();
        final User foundAdmin = User.builder()
                .id(id)
                .isLogin(true)
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(foundAdmin));

        //Ack
        adminService.logout(id);

        //Assert
        assertFalse(foundAdmin.isLogin());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(foundAdmin);
    }

    @Test
    void testLogoutAdmin_WhenIdIsNotFound_ThenThrowLogoutException(){
        //Arrange
        UUID id = UUID.randomUUID();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        //Ack & Assert
        assertThrows(LogoutException.class, () -> adminService.logout(id));
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void testLogoutAdmin_WhenIdIsFoundAndAdminIsNotLogin_ThenThrowLogoutException(){
        //Arrange
        UUID id = UUID.randomUUID();
        final User foundAdmin = User.builder()
                .id(id)
                .isLogin(false)
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(foundAdmin));

        //Ack & Assert
        assertThrows(LogoutException.class, () -> adminService.logout(id));
        assertFalse(foundAdmin.isLogin());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(0)).save(any());
    }
}
