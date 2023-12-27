package com.kj.product.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@ToString
public class ProductInputDto {
    private String productName;
    private String productNumber;
    private String productPrice;
    private String gender;
    private String productSeason;
    private String productSize;
    private int productCount;
    private String productDetailImage; // ckediter에서 들어오니까
    private MultipartFile file1;
    private MultipartFile file2;
    private MultipartFile file3;
    private MultipartFile file4;
    private MultipartFile file5;

}
