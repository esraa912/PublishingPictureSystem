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
                .orElseThrow(() -> {
                    log.error("{} - User not found with id={}", methodName, id);
                    return new UserNotFoundException("User not found");
                });

        if (user.isArchived()) {
            log.error("{} - User with id: {} is already archived", methodName, id);
            throw new UserAlreadyArchivedException("User is already archived");
        }

        if (user.isLogin()) {
            log.debug("{} - User with id: {} is currently logged in", methodName, id);
            user.setLogin(false);
        }

        user.setArchived(true);
        userRepository.save(user);
        log.info("{} - User archived successfully with id: {}", methodName, id);
    }
}
