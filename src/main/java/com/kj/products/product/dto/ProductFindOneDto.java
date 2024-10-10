package com.kj.products.product.dto;

import com.kj.products.product.entity.Product;
import com.kj.products.product.entity.ProductImage;
import com.kj.products.product.entity.ProductSize;
import com.kj.products.product.entity.ProductTag;
import com.kj.products.productQnA.dto.ProductQnAfind;
import com.kj.products.productQnA.entity.ProductQnA;
import com.kj.products.productReview.entity.ProductReview;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
@Data
public class ProductFindOneDto {
    private Long id;

    private String productName;

    private String productNumber;

    private int productPrice;

    private String gender;

    private String productSeason;

    private int clickCount;
    private String productDatailImage;
    private Long productLike;

    private List<ProductImage> productImages;
    private List<ProductSize> productSize;
    private List<ProductTag> productTags;
    private PageImpl<ProductReview> productReviews;
    private PageImpl<ProductQnA> productQnAPage;


    @Builder
    public ProductFindOneDto(Product product, List<ProductSize> findProductSize, PageImpl<ProductReview> productReviews, List<ProductTag> findProductTags, Long producLike, PageImpl<ProductQnA> productQnAPage) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.productNumber = product.getProductNumber();
        this.productPrice = product.getProductPrice();
        this.gender = product.getGender();
        this.productSeason = product.getProductSeason();
        this.clickCount = product.getClickCount();
        this.productDatailImage = product.getProductDatailImage();
        this.productImages = product.getProductImages();
        this.productSize = findProductSize;
        this.productTags = findProductTags;
        this.productLike = producLike;
        this.productReviews = productReviews;
        this.productQnAPage = productQnAPage;
    }
}
