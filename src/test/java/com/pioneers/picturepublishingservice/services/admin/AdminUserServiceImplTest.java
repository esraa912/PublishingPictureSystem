package com.pioneers.picturepublishingservice.services.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pioneers.picturepublishingservice.errors.exceptions.UserAlreadyArchivedException;
import com.pioneers.picturepublishingservice.errors.exceptions.UserNotFoundException;
import com.pioneers.picturepublishingservice.models.entities.User;
import com.pioneers.picturepublishingservice.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class AdminUserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminUserServiceImpl adminUserService;

    @Test
    void testDeleteUserWhenIdIsFoundThenDeleteTheUser() {
        //Arrange
        UUID id = UUID.randomUUID();
        final User foundUser = User.builder()
                .id(id)
                .isArchived(false)
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(foundUser));

        //Ack
        adminUserService.deleteUser(id);

        //Assert
        assertTrue(foundUser.isArchived());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(foundUser);
    }

    @Test
    void testDeleteUserWhenIdIsNotFoundThenThrowUserNotFoundException() {
        //Arrange
        UUID id = UUID.randomUUID();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        //Ack & Assert
        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> adminUserService.deleteUser(id));
        assertEquals("User not found", ex.getMessage());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void testDeleteUserWhenIdIsFoundAndIsArchivedThenThrowUserAlreadyArchivedException() {
        //Arrange
        UUID id = UUID.randomUUID();
        final User foundUser = User.builder()
                .id(id)
                .isArchived(true)
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(foundUser));

        //Ack & Assert
        UserAlreadyArchivedException ex = assertThrows(UserAlreadyArchivedException.class,
                () -> adminUserService.deleteUser(id));

        assertEquals("User is already archived", ex.getMessage());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(0)).save(any());
    }
}
