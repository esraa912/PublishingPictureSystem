package com.pioneers.picturepublishingservice.services.admin;

import com.pioneers.picturepublishingservice.errors.exceptions.UserAlreadyArchivedException;
import com.pioneers.picturepublishingservice.errors.exceptions.UserNotFoundException;
import com.pioneers.picturepublishingservice.models.entities.User;
import com.pioneers.picturepublishingservice.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService{

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void deleteUser(final UUID id) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.isArchived()) {
            throw new UserAlreadyArchivedException("User is already archived");
        }

        if (user.isLogin()) {
            user.setLogin(false);
        }

        user.setArchived(true);
        userRepository.save(user);
    }
}
