package com.pioneers.picturepublishingservice.services.admin;

import com.pioneers.picturepublishingservice.errors.exceptions.UserAlreadyArchivedException;
import com.pioneers.picturepublishingservice.errors.exceptions.UserNotFoundException;
import com.pioneers.picturepublishingservice.models.entities.User;
import com.pioneers.picturepublishingservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminUserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminUserServiceImpl adminUserService;

    @Test
    void testDeleteUser_WhenIdIsFound_ThenDeleteTheUser(){
        //Arrange
        UUID id = UUID.randomUUID();
        final User foundUser = User.builder()
                .id(id)
                .isArchived(false)
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(foundUser));

        //Act
        adminUserService.deleteUser(id);

        //Assert
        assertTrue(foundUser.isArchived());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(foundUser);
    }

    @Test
    void testDeleteUser_WhenIdIsNotFound_ThenThrowUserNotFoundException(){
        //Arrange
        UUID id = UUID.randomUUID();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        //Act & Assert
        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> adminUserService.deleteUser(id));
        assertEquals("User not found", ex.getMessage());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void testDeleteUser_WhenIdIsFoundAndIsArchived_ThenThrowUserAlreadyArchivedException(){
        //Arrange
        UUID id = UUID.randomUUID();
        final User foundUser = User.builder()
                .id(id)
                .isArchived(true)
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(foundUser));

        //Act & Assert
        UserAlreadyArchivedException ex = assertThrows(UserAlreadyArchivedException.class,
                () -> adminUserService.deleteUser(id));

        assertEquals("User is already archived", ex.getMessage());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(0)).save(any());
    }
}
