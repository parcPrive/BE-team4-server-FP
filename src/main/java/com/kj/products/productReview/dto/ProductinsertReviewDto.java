package com.kj.products.productReview.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
@Data
public class ProductinsertReviewDto {
    private Long productId;
    private String userName;
    private String productReview;
    private int star;
    private List<MultipartFile> productReviewImages;

}
