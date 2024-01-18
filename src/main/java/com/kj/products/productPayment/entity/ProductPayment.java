package com.kj.products.productPayment.entity;

import com.kj.member.entity.Member;
import com.kj.products.productPayment.dto.ProductPaymentInsertDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_PAYMENT_ID")
    private Long id;

    private String impUid;
    private int productTotalPrice;

    private String status;

    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public ProductPayment(ProductPaymentInsertDto productPaymentInsertDto, Member findMember) {
        this.impUid = productPaymentInsertDto.getImpUid();
        this.productTotalPrice = productPaymentInsertDto.getProductTotalPrice();
        this.status = productPaymentInsertDto.getPaymentStatus();
//        this.createdAt = LocalDate.now();
        this.member = findMember;
    }
}
