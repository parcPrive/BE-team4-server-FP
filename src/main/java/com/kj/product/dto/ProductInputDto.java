package com.kj.product.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductInputDto {
    private String productName;
    private String productNumber;
    private int productPrice;
    private String gender;
    private String productSeason;
    // 사이즈와 수량
    private String productSizeS;
    private int productCountS;
    private String productSizeM;
    private int productCountM;
    private String productSizeL;
    private int productCountL;
    private String productSizeXL;
    private int productCountXL;
    private String productSizeXXL;
    private int productCountXXL;
    // ckediter에서 들어오니까
    private Long productDetailImageBucket;
    private String productDetailImage;
    // 상품 이미지
    private MultipartFile file1;
    private MultipartFile file2;
    private MultipartFile file3;
    private MultipartFile file4;
    private MultipartFile file5;
    private Long subProductCategoryId;

}
