package com.pioneers.picturepublishingservice.services.admin;

import java.util.UUID;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pioneers.picturepublishingservice.errors.exceptions.UserAlreadyArchivedException;
import com.pioneers.picturepublishingservice.errors.exceptions.UserNotFoundException;
import com.pioneers.picturepublishingservice.models.entities.User;
import com.pioneers.picturepublishingservice.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link AdminUserService} that provides
 * administrative operations for managing users.
 *
 * @author esraa
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void deleteUser(final UUID id) {
        final String methodName = "deleteUser()";
        log.debug("{} - Attempting to delete user with id: {}", methodName, id);

        final User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.isArchived()) {
            throw new UserAlreadyArchivedException("User is already archived");
        }

        if (user.isLogin()) {
            user.logout();
            log.debug("{} - User with id: {} is currently logged out", methodName, id);
        }

        user.markAsArchived();
        userRepository.save(user);
        log.info("{} - User archived successfully with id: {}", methodName, id);
    }
}
