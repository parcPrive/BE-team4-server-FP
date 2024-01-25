package com.kj.products.productQnACategory.entity;

import com.kj.products.productQnACategory.dto.ProductQnACategoryInputDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductQnACategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productQnACategoryName;

    @Builder
    public ProductQnACategory(ProductQnACategoryInputDto productQnACategoryInputDto) {
        this.productQnACategoryName = productQnACategoryInputDto.getProductQnACategoryName();
    }
}
