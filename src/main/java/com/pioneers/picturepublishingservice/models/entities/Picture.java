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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;
import com.pioneers.picturepublishingservice.models.enums.Category;
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
    private Category category;

    @Column
    private String url;

    @Column(name = "uploaded_at", nullable = false)
    private Timestamp uploadedAt;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private int height;

    /**
     * Constructs a new {@code Picture} instance using the provided {@code PictureBuilder}.
     *
     * @param pictureBuilder the builder containing picture properties
     */
    public Picture(final PictureBuilder pictureBuilder) {
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

    /**
     * Marks the picture status as {@code ACCEPTED}.
     */
    public void acceptStatus() {
        this.status = PictureStatus.ACCEPTED;
    }

    /**
     * Marks the picture status as {@code REJECTED}.
     *
     * @throws PictureException if PictureStatus not pinding
     */
    public void rejectStatus() {
        if (!PictureStatus.PENDING.equals(status)) {
            throw new PictureException("Picture must be pending");
        }

        this.status = PictureStatus.REJECTED;
    }

    /**
     * Assigns a URL to the picture.
     *
     * @param url the URL to assign to the picture
     */
    public void assignUrl(final String url) {
        this.url = url;
    }

    /**
     * Creates a new {@code PictureBuilder} instance.
     *
     * @return a new {@code PictureBuilder} instance
     */
    public static PictureBuilder builder() {
        return new PictureBuilder();
    }

    /**
     * Builder class for constructing {@link Picture} instances.
     *
     * @author esraa
     */
    public static class PictureBuilder {
        private UUID id;
        private String description;
        private String filePath;
        private String fileType;
        private PictureStatus status;
        private UUID userId;
        private Category category;
        private String url;
        private Timestamp uploadedAt;
        private int width;
        private int height;

        /**
         * Sets the unique identifier for the picture.
         *
         * @param id the UUID of the picture
         * @return the current {@code PictureBuilder} instance
         */
        public PictureBuilder id(final UUID id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the description of the picture.
         *
         * @param description a textual description of the picture
         * @return the current {@code PictureBuilder} instance
         */
        public PictureBuilder description(final String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the file path of the picture.
         *
         * @param filePath the path where the picture is stored
         * @return the current {@code PictureBuilder} instance
         */
        public PictureBuilder filePath(final String filePath) {
            this.filePath = filePath;
            return this;
        }

        /**
         * Sets the file type of the picture (e.g., jpg, png).
         *
         * @param fileType the type of the picture file
         * @return the current {@code PictureBuilder} instance
         */
        public PictureBuilder fileType(final String fileType) {
            this.fileType = fileType;
            return this;
        }

        /**
         * Sets the status of the picture.
         *
         * @param status the {@link PictureStatus} of the picture
         * @return the current {@code PictureBuilder} instance
         */
        public PictureBuilder status(final PictureStatus status) {
            this.status = status;
            return this;
        }

        /**
         * Sets the user ID associated with the picture.
         *
         * @param userId the UUID of the user
         * @return the current {@code PictureBuilder} instance
         */
        public PictureBuilder userId(final UUID userId) {
            this.userId = userId;
            return this;
        }

        /**
         * Sets the category of the picture.
         *
         * @param category the {@link Category} of the picture
         * @return the current {@code PictureBuilder} instance
         */
        public PictureBuilder category(final Category category) {
            this.category = category;
            return this;
        }

        /**
         * Sets the URL of the picture.
         *
         * @param url the URL pointing to the picture
         * @return the current {@code PictureBuilder} instance
         */
        public PictureBuilder url(final String url) {
            this.url = url;
            return this;
        }

        /**
         * Sets the timestamp when the picture was uploaded.
         *
         * @param uploadedAt the upload timestamp
         * @return the current {@code PictureBuilder} instance
         */
        public PictureBuilder uploadedAt(final Timestamp uploadedAt) {
            this.uploadedAt = uploadedAt;
            return this;
        }

        /**
         * Sets the width of the picture.
         *
         * @param width the width in pixels
         * @return the current {@code PictureBuilder} instance
         */
        public PictureBuilder width(final int width) {
            this.width = width;
            return this;
        }

        /**
         * Sets the height of the picture.
         *
         * @param height the height in pixels
         * @return the current {@code PictureBuilder} instance
         */
        public PictureBuilder height(final int height) {
            this.height = height;
            return this;
        }

        /**
         * Builds a new {@link Picture} instance using the configured values.
         *
         * @return a new {@code Picture} object
         */
        public Picture build() {
            return new Picture(this);
        }
    }
}
