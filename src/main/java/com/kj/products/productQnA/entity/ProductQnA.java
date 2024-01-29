package com.kj.products.productQnA.entity;

import com.kj.member.entity.Member;
import com.kj.productCategory.entity.ProductCategory;
import com.kj.products.product.entity.Product;
import com.kj.products.productQnA.dto.ProductAdminQnAInputDto;
import com.kj.products.productQnA.dto.ProductQnAInputDto;
import com.kj.products.productQnACategory.entity.ProductQnACategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductQnA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productQATitle;
    private String productQAContent;
    private Long sortNum;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_QNA_CATEGORY_ID")
    private ProductQnACategory productQnACategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private ProductQnA parent;

    @OneToOne(mappedBy = "parent", fetch = FetchType.LAZY)
    private ProductQnA children;

    @Builder
    public ProductQnA(ProductQnAInputDto productQnAInputDto, ProductQnACategory findProductQnACategory, Product findProduct, Member findMember, Long sortNum) {
        this.productQATitle = productQnAInputDto.getProductQATitle();
        this.productQAContent = productQnAInputDto.getProductQAContent();
        this.createdAt = LocalDateTime.now();
        this.productQnACategory = findProductQnACategory;
        this.product = findProduct;
        this.member = findMember;
        this.sortNum = sortNum;
    }
    @Builder
    public ProductQnA(ProductAdminQnAInputDto productAdminQnAInputDto, Product product, Member member,ProductQnA productQnA) {
        this.productQAContent = productAdminQnAInputDto.getProductAdminQnAContent();
        this.createdAt = LocalDateTime.now();
        this.product = product;
        this.member = member;
        this.parent = productQnA;
        this.sortNum = productQnA.getSortNum();
    }

}
