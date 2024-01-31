package com.kj.products.productOder.entity;

import com.kj.member.entity.Member;
import com.kj.products.product.entity.ProductSize;
import com.kj.products.productCart.entity.ProductCart;
import com.kj.products.productOder.dto.ProductInsertOrderDto;
import com.kj.products.productPayment.entity.ProductPayment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ORDER_DETAIL_ID")
    private Long id;

    private String deliveryNumber;

    private int price;

    private int productCount;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ORDER_ID")
    private ProductOrderInfo productOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_SIZE_ID")
    private ProductSize productSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_PAYMENT_ID")
    private ProductPayment productPayment;

    public ProductOrderProductDetail(String deliveryNumber,ProductInsertOrderDto productInsertOrderDto, ProductSize productSizeAndProduct, ProductCart productCart, ProductOrderInfo productOrder, Member member, ProductPayment productPayment) {
        this.deliveryNumber = deliveryNumber;
        this.price = productSizeAndProduct.getProduct().getProductPrice();
        this.productCount = productCart.getProductCount();
        this.productOrder = productOrder;
        this.productSize = productSizeAndProduct;
        this.member = member;
        this.productPayment = productPayment;
    }

    public void setProductPayment(ProductPayment productPayment){
        this.productPayment = productPayment;
    }
}
