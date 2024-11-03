package com.dailycodework.dreamshops.service.image;

import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.exceptions.NotFoundException;
import com.dailycodework.dreamshops.model.Image;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.repository.ImageRepository;
import com.dailycodework.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements  IImageService{

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Image not found"));
    }

    @Override
    public List<ImageDto> addImages(List<MultipartFile> files, Long productId) {
        Product product= productService.getProductById(productId);

        List<ImageDto> savedImageDto= new ArrayList<>();

        for (MultipartFile file: files){
            try {
                Image image= new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String baseUrl= "/api/v1/images/image/download/";
                String downloadUrl= baseUrl+image.getId();
                image.setDownloadUrl(downloadUrl);

                Image savedImage= imageRepository.save(image);
                savedImage.setDownloadUrl(baseUrl+savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto= new ImageDto();

                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long id) {
        Image image= getImageById(id);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException| SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,()->{
            throw new NotFoundException("Image not found");
        });
    }
}
