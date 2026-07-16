package com.pioneers.picturepublishingservice.models.entities;

import com.pioneers.picturepublishingservice.models.enums.ROLE;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Entity class representing a user in the system.
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

    @Column(nullable = false, unique=true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_login", nullable = false)
    private boolean isLogin;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "role")
    private ROLE role;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "is_archived", nullable = false)
    private boolean isArchived;

    public User(UserBuilder userBuilder){
        this.id = userBuilder.id;
        this.name = userBuilder.name;
        this.email = userBuilder.email;
        this.password = userBuilder.password;
        this.isLogin = userBuilder.isLogin;
        this.role = userBuilder.role;
        this.createdAt = userBuilder.createdAt;
        this.isArchived = userBuilder.isArchived;
    }

    public static UserBuilder builder(){
        return new UserBuilder();
    }

    public static class UserBuilder{
        private UUID id;
        private String name;
        private String email;
        private String password;
        private boolean isLogin;
        private ROLE role;
        private Timestamp createdAt;
        private boolean isArchived;

        public UserBuilder id(UUID id){
            this.id = id;
            return this;
        }

        public UserBuilder name(String name){
            this.name = name;
            return this;
        }

        public UserBuilder email(String email){
            this.email = email;
            return this;
        }

        public UserBuilder password(String password){
            this.password = password;
            return this;
        }

        public UserBuilder isLogin(Boolean isLogin){
            this.isLogin = isLogin;
            return this;
        }

        public UserBuilder role(ROLE role){
            this.role = role;
            return this;
        }

        public UserBuilder createdAt(Timestamp createdAt){
            this.createdAt = createdAt;
            return this;
        }

        public UserBuilder isArchived(boolean isArchived){
            this.isArchived = isArchived;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }
}
