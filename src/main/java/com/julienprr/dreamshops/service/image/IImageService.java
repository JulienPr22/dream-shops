package com.julienprr.dreamshops.service.image;

import com.julienprr.dreamshops.dto.ImageDto;
import com.julienprr.dreamshops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long producId);
    void updateImage(MultipartFile file, Long id);
}
