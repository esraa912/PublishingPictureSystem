package com.pioneers.picturepublishingservice.utils.mappers;

import com.pioneers.picturepublishingservice.errors.exceptions.CredentialsException;
import com.pioneers.picturepublishingservice.models.dtos.requests.UserSignup;
import com.pioneers.picturepublishingservice.models.entities.User;
import com.pioneers.picturepublishingservice.models.enums.ROLE;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

import static com.pioneers.picturepublishingservice.utils.CredentialsHelper.hashPassword;
import static com.pioneers.picturepublishingservice.utils.time.TimeHelper.currentTimestamp;

/**
 * Mapper class to transform to/from User.
 */
@Slf4j
public class UserMapper {

    private UserMapper() {
        throw new AssertionError("Cannot instantiate the UserMapper");
    }

    /**
     * Transfer the UserSignup to a User object.
     *
     * @param userSignup is the target request need to transform from it.
     * @return a new user object from the request.
     * @throws CredentialsException is the returned exception during the hashing password process
     */
  public static User toNewUser(final UserSignup userSignup) throws CredentialsException {
      final String methodName = "toNewUser()";
      final String hashedPassword = hashPassword(userSignup.password());
      final Timestamp currentTime = currentTimestamp();

      final User user = User.builder()
              .name(userSignup.name())
              .email(userSignup.email())
              .password(hashedPassword)
              .isLogin(false)
              .role(ROLE.USER)
              .createdAt(currentTime)
              .isArchived(false)
              .build();
      log.debug("{}, Mapped to new student with email: [{}]", methodName, user.getEmail());
      return user;
  }
}
