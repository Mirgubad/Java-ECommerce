package com.dailycodework.dreamshops.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ImageDto {
    private Long id;
    private String imageName;
    private String downloadUrl;

    public ImageDto(Long id, String fileName, String downloadUrl) {
        this.id = id;
        this.imageName = fileName;
        this.downloadUrl = downloadUrl;
    }

    public ImageDto() {

    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }


}
