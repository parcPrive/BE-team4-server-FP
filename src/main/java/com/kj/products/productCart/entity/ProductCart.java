package com.kj.products.productCart.entity;

import com.kj.member.entity.Member;
import com.kj.products.productCart.dto.ProductCartInsertDto;
import com.kj.products.product.entity.ProductSize;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
public class ProductCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_CART_ID")
    private Long id;

    private int productCount;

//    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCTSIZE_ID")
    private ProductSize productSize;


    public ProductCart(ProductCartInsertDto productCartInsertDto, Member member, ProductSize productSize) {
        this.productCount = productCartInsertDto.getProductTotalCount();
        this.member = member;
        this.productSize = productSize;
//        this.createdAt = LocalDateTime.now();
    }

}
