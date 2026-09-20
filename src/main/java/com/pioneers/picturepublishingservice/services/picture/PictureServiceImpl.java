package com.pioneers.picturepublishingservice.services.picture;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import jakarta.transaction.Transactional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureUrlResponse;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.enums.Category;
import com.pioneers.picturepublishingservice.models.enums.PictureStatus;
import com.pioneers.picturepublishingservice.repositories.PictureRepository;
import com.pioneers.picturepublishingservice.utils.files.FileHelper;
import com.pioneers.picturepublishingservice.utils.files.ImageHelper;
import com.pioneers.picturepublishingservice.utils.mappers.PictureMapper;
import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link PictureService} that provides core operations
 * for managing pictures in the system.
 *
 * @author esraa
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    @Override
    @Transactional
    public void uploadPicture(
            final MultipartFile file,
            final String description,
            final Category category,
            final UUID userId
    ) throws IOException {
        final String methodName = "uploadPicture()";
        log.debug("{} - Uploading picture with category = [{}]", methodName, category);

        ImageHelper.validateSize(file.getSize());
        final String extension = FileHelper.fetchExtension(Objects.requireNonNull(file.getOriginalFilename()));
        ImageHelper.validateExtension(extension);

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new org.springframework.core.io.ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });
        body.add("upload_preset", "my_preset");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.cloudinary.com/v1_1/u2gjnvar/image/upload",
                requestEntity,
                String.class
        );

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.getBody());
        String url = jsonNode.get("secure_url").asText();

        final Picture picture = Picture.builder()
                .description(description)
                .filePath(url)
                .fileType(extension)
                .status(PictureStatus.PENDING)
                .userId(userId)
                .category(category)
                .uploadedAt(TimeHelper.currentTimestamp())
                .build();

        pictureRepository.save(picture);
        log.info("{} - Picture uploaded successfully to Cloudinary with URL: {}", methodName, url);
    }

    @Override
    public PictureResponse displayPictureDetails(final UUID id) {
        final String methodName = "displayPictureDetails()";
        log.debug("{} - Fetching details for picture Id: [{}]", methodName, id);

        return pictureRepository.findById(id)
                .map(PictureMapper::toPictureResponse)
                .orElseThrow(() -> new PictureException("Picture not found", methodName));
    }

    @Override
    public List<PictureUrlResponse> displayAllAcceptedPictureUrl() {
        return pictureRepository.findByStatus(PictureStatus.ACCEPTED)
                .stream()
                .map(picture -> PictureMapper.toPictureUrlResponse(picture.getUrl()))
                .toList();
    }
}
