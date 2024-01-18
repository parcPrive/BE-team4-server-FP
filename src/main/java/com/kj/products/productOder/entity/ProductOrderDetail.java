package com.kj.products.productOder.entity;

import com.kj.member.entity.Member;
import com.kj.products.product.entity.Product;
import com.kj.products.product.entity.ProductSize;
import com.kj.products.productPayment.entity.ProductPayment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ORDER_DETAIL_ID")
    private Long id;

    private String deliveryNumber;

    private int price;

    private int productCount;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ORDER_ID")
    private ProductOrder productOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_SIZE_ID")
    private ProductSize productSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_PAYMENT_ID")
    private ProductPayment productPayment;
}
