package com.pioneers.picturepublishingservice.services.user;

import com.pioneers.picturepublishingservice.errors.exceptions.RegisterException;
import com.pioneers.picturepublishingservice.models.dtos.requests.UserSignup;
import com.pioneers.picturepublishingservice.models.entities.User;
import com.pioneers.picturepublishingservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void testRegisterUser_WhenEmailNotExist_ThenUserRegisterSuccessfully(){
        //Arrange
        final UserSignup userSignup =
                new UserSignup("salma.mohamed@gmail.com", "salma mohamed", "Salma123@");

        when(userRepository.findByEmail(userSignup.email())).thenReturn(Optional.empty());

        //Act
        authService.registerUser(userSignup);

        //Assert
        verify(userRepository, times(1)).findByEmail(userSignup.email());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void testRegisterUser_WhenEmailExist_ThenThrowRegisterException(){
        //Arrange
        final UserSignup userSignup =
                new UserSignup("salma.mohamed@gmail.com", "salma mohamed", "Salma123@");

        final User foundUser = User.builder()
                .email(userSignup.email())
                .name(userSignup.name())
                .password(userSignup.password())
                .build();

        when(userRepository.findByEmail(userSignup.email())).thenReturn(Optional.of(foundUser));

        //Act & Assert
        RegisterException ex = assertThrows(RegisterException.class, () -> authService.registerUser(userSignup));
        assertEquals("Email is already used in system", ex.getMessage());
        verify(userRepository, times(1)).findByEmail(userSignup.email());
        verify(userRepository, times(0)).save(foundUser);
    }
}
