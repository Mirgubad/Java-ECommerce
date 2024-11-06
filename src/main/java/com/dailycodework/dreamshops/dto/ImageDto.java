package com.dailycodework.dreamshops.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDto {
    private Long id;
    private String fileName;
    private String downloadUrl;

    public ImageDto() {
    }
    public ImageDto(Long id, String fileName, String downloadUrl) {
        this.id = id;
        this.fileName = fileName;
        this.downloadUrl = downloadUrl;
    }
}
