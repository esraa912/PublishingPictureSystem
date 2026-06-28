package com.pioneers.picturepublishingservice.services.admin;

import com.pioneers.picturepublishingservice.errors.exceptions.*;
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
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

//    @Override
//    @Transactional
//    public void login(UserLogin adminLogin) {
//
//        final String methodName = "loginAdmin()";
//        final User foundAdmin = userRepository.findByEmail(adminLogin.email())
//                .orElseThrow( () -> new LoginException(
//                        String.format("Admin with email %s is not found!", adminLogin.email())
//                        )
//                );
//
//        if (!adminLogin.password().matches(foundAdmin.getPassword())) {
//            log.error("{}, {}", methodName, "Password is incorrect");
//            throw new LoginException("Email or password incorrect");
//        }
//
//        if (foundAdmin.isLogin()) {
//            final String errorDetails = "Admin with email: " + adminLogin.email() + " is already login";
//            final String[] loginArgsErrorLogs = new String[]{methodName, errorDetails};
//            log.error("{}, {}", loginArgsErrorLogs);
//
//            throw new LoginException(errorDetails);
//        }
//
//        foundAdmin.setLogin(true);
//
//        userRepository.save(foundAdmin);
//        log.info("Login successfully!");
//    }

    @Override
    @Transactional
    public void logout(final UUID id){
        final String methodName = "logoutAdmin()";
        User foundAdmin = userRepository.findById(id)
                .orElseThrow(() -> new LogoutException("Admin with id: [" + id + "] is not found"));

        if(!foundAdmin.isLogin()){
            final String errorDetail = "Admin with id: [" + foundAdmin.getId() + "] is not login";
            log.error("{}, {}",  methodName, errorDetail);

            throw new LogoutException(errorDetail);
        }

        foundAdmin.setLogin(false);
        userRepository.save(foundAdmin);
    }
}
