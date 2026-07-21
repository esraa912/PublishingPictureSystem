package com.pioneers.picturepublishingservice.models.entities;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.pioneers.picturepublishingservice.models.enums.CATEGORY;
import com.pioneers.picturepublishingservice.models.enums.PictureStatus;

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
@Table(name = "pictures")
public class Picture {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String description;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "picture_status")
    private PictureStatus status;

    @JoinColumn(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "category")
    private CATEGORY category;

    @Column
    private String url;

    @Column(name = "uploaded_at", nullable = false)
    private Timestamp uploadedAt;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private int height;

    public Picture(PictureBuilder pictureBuilder) {
        this.id = pictureBuilder.id;
        this.description = pictureBuilder.description;
        this.filePath = pictureBuilder.filePath;
        this.fileType = pictureBuilder.fileType;
        this.status = pictureBuilder.status;
        this.userId = pictureBuilder.userId;
        this.category = pictureBuilder.category;
        this.url = pictureBuilder.url;
        this.uploadedAt = pictureBuilder.uploadedAt;
        this.width = pictureBuilder.width;
        this.height = pictureBuilder.height;
    }

    public static PictureBuilder builder() {
        return new PictureBuilder();
    }

    public static class PictureBuilder {
        private UUID id;
        private String description;
        private String filePath;
        private String fileType;
        private PictureStatus status;
        private UUID userId;
        private CATEGORY category;
        private String url;
        private Timestamp uploadedAt;
        private int width;
        private int height;

        public PictureBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public PictureBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PictureBuilder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public PictureBuilder fileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public PictureBuilder status(PictureStatus status) {
            this.status = status;
            return this;
        }

        public PictureBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public PictureBuilder category(CATEGORY category) {
            this.category = category;
            return this;
        }

        public PictureBuilder url(String url) {
            this.url = url;
            return this;
        }

        public PictureBuilder uploadedAt(Timestamp uploadedAt) {
            this.uploadedAt = uploadedAt;
            return this;
        }

        public PictureBuilder width(int width) {
            this.width = width;
            return this;
        }

        public PictureBuilder height(int height) {
            this.height = height;
            return this;
        }

        public Picture build() {
            return new Picture(this);
        }
    }
}
