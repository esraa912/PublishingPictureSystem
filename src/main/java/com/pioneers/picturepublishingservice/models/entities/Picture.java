package com.pioneers.picturepublishingservice.models.entities;

import com.pioneers.picturepublishingservice.models.enums.CATEGORY;
import com.pioneers.picturepublishingservice.models.enums.PICTURE_STATUS;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

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
    @Column(name = "file_path",nullable = false)
    private String filePath;
    @Column(name = "file_type", nullable = false)
    private String fileType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PICTURE_STATUS status;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CATEGORY category;
    @Column(name = "uploaded_at", nullable = false)
    private Timestamp uploadedAt;
    @Column(nullable = false)
    private int width;
    @Column(nullable = false)
    private int height;

    public Picture(PictureBuilder pictureBuilder){
        this.id = pictureBuilder.id;
        this.description = pictureBuilder.description;
        this.filePath = pictureBuilder.filePath;
        this.fileType = pictureBuilder.fileType;
        this.status = pictureBuilder.status;
        this.user = pictureBuilder.user;
        this.category = pictureBuilder.category;
        this.uploadedAt = pictureBuilder.uploadedAt;
        this.width = pictureBuilder.width;
        this.height = pictureBuilder.height;
    }

    public static PictureBuilder builder(){
        return new PictureBuilder();
    }

    public static class PictureBuilder{
        private UUID id;
        private String description;
        private String filePath;
        private String fileType;
        private PICTURE_STATUS status;
        private User user;
        private CATEGORY category;
        private Timestamp uploadedAt;
        private int width;
        private int height;

        public PictureBuilder id(UUID id){
            this.id = id;
            return this;
        }

        public PictureBuilder description(String description){
            this.description = description;
            return this;
        }

        public PictureBuilder filePath(String filePath){
            this.filePath = filePath;
            return this;
        }

        public PictureBuilder fileType(String fileType){
            this.fileType = fileType;
            return this;
        }

        public PictureBuilder status(PICTURE_STATUS status){
            this.status = status;
            return this;
        }

        public PictureBuilder user(User user){
            this.user = user;
            return this;
        }

        public PictureBuilder category(CATEGORY category){
            this.category = category;
            return this;
        }

        public PictureBuilder uploadedAt(Timestamp uploadedAt){
            this.uploadedAt = uploadedAt;
            return this;
        }

        public PictureBuilder width(int width){
            this.width = width;
            return this;
        }

        public PictureBuilder height(int height){
            this.height = height;
            return this;
        }

        public Picture build(){
            return new Picture(this);
        }
    }
}
