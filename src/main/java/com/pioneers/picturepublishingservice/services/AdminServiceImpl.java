package com.pioneers.picturepublishingservice.services;

import com.pioneers.picturepublishingservice.errors.exceptions.*;
import com.pioneers.picturepublishingservice.models.dtos.requests.UserLogin;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.entities.User;
import com.pioneers.picturepublishingservice.models.enums.PICTURE_STATUS;
import com.pioneers.picturepublishingservice.repositories.PictureRepository;
import com.pioneers.picturepublishingservice.repositories.UserRepository;
import com.pioneers.picturepublishingservice.utils.CredentialsHelper;
import com.pioneers.picturepublishingservice.utils.mappers.PictureMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final PictureRepository pictureRepository;
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

    @Override
    public List<PictureResponse> getPendingPictures() {
        return pictureRepository.findByStatus(PICTURE_STATUS.PENDING)
                .stream()
                .map(PictureMapper::toPictureResponse)
                .toList();
    }

    @Override
    public void approvePicture(final UUID id) {
        final Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new PictureNotFoundException("Picture not found with id: " + id));

        picture.setStatus(PICTURE_STATUS.ACCEPTED);
        pictureRepository.save(picture);
    }

    @Override
    public void rejectPicture(final UUID id) {
        final Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new PictureNotFoundException("Picture not found with id: " + id));

        picture.setStatus(PICTURE_STATUS.REJECTED);
        pictureRepository.save(picture);
    }

    @Override
    public void deleteUser(UUID id) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setArchived(true);
        userRepository.save(user);
    }
}
