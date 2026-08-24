package com.pioneers.picturepublishingservice.models.entities;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.pioneers.picturepublishingservice.models.enums.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a picture stored in the system.
 *
 * @author esraa
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_login", nullable = false)
    private boolean isLogin;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "role")
    private Role role;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "is_archived", nullable = false)
    private boolean isArchived;

    /**
     * Constructs a new {@code User} instance using the provided {@code UserBuilder}.
     *
     * @param userBuilder the builder containing user properties
     */
    public User(final UserBuilder userBuilder) {
        this.id = userBuilder.id;
        this.name = userBuilder.name;
        this.email = userBuilder.email;
        this.password = userBuilder.password;
        this.isLogin = userBuilder.isLogin;
        this.role = userBuilder.role;
        this.createdAt = userBuilder.createdAt;
        this.isArchived = userBuilder.isArchived;
    }

    /**
     * Logs out the user by setting {@code isLogin} to false.
     */
    public void logout() {
        if (isLogin) {
            this.isLogin = false;
        }
    }

    /**
     * Marks the user as archived.
     */
    public void markAsArchived() {
        this.isArchived = true;
    }

    /**
     * Logs in the user by setting {@code isLogin} to true.
     */
    public void login() {
        if (!isLogin) {
            this.isLogin = true;
        }
    }

    /**
     * Creates a new {@code UserBuilder} instance.
     *
     * @return a new {@code UserBuilder} instance
     */
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    /**
     * Builder class for constructing {@link User} instances.
     */
    public static class UserBuilder {
        private UUID id;
        private String name;
        private String email;
        private String password;
        private boolean isLogin;
        private Role role;
        private Timestamp createdAt;
        private boolean isArchived;

        /**
         * Sets the unique identifier for the user.
         *
         * @param id the UUID of the user
         * @return the current {@code UserBuilder} instance
         */
        public UserBuilder id(final UUID id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the name of the user.
         *
         * @param name the user's name
         * @return the current {@code UserBuilder} instance
         */
        public UserBuilder name(final String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the email of the user.
         *
         * @param email the user's email address
         * @return the current {@code UserBuilder} instance
         */
        public UserBuilder email(final String email) {
            this.email = email;
            return this;
        }

        /**
         * Sets the password of the user.
         *
         * @param password the user's password
         * @return the current {@code UserBuilder} instance
         */
        public UserBuilder password(final String password) {
            this.password = password;
            return this;
        }

        /**
         * Sets the login status of the user.
         *
         * @param isLogin true if the user is logged in, false otherwise
         * @return the current {@code UserBuilder} instance
         */
        public UserBuilder isLogin(final Boolean isLogin) {
            this.isLogin = isLogin;
            return this;
        }

        /**
         * Sets the role of the user.
         *
         * @param role the {@link Role} assigned to the user
         * @return the current {@code UserBuilder} instance
         */
        public UserBuilder role(final Role role) {
            this.role = role;
            return this;
        }

        /**
         * Sets the creation timestamp of the user.
         *
         * @param createdAt the timestamp when the user was created
         * @return the current {@code UserBuilder} instance
         */
        public UserBuilder createdAt(final Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Sets the archival status of the user.
         *
         * @param isArchived true if the user is archived, false otherwise
         * @return the current {@code UserBuilder} instance
         */
        public UserBuilder isArchived(final boolean isArchived) {
            this.isArchived = isArchived;
            return this;
        }

        /**
         * Builds a new {@link User} instance using the configured values.
         *
         * @return a new {@code User} object
         */
        public User build() {
            return new User(this);
        }
    }
}
