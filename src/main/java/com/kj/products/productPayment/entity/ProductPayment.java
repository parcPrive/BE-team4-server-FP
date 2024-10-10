package com.kj.products.productPayment.entity;

import com.kj.member.entity.Member;
import com.kj.products.productPayment.dto.ProductPaymentInsertDto;
import com.kj.products.productPayment.dto.ResponseGetRefundDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    // 결제에 성공했을때 저장하기위한 데이터
    @Builder
    public ProductPayment(ProductPaymentInsertDto productPaymentInsertDto, Member findMember) {
        this.impUid = productPaymentInsertDto.getImpUid();
        this.productTotalPrice = productPaymentInsertDto.getProductTotalPrice();
        this.status = productPaymentInsertDto.getPaymentStatus();
        this.createdAt = LocalDateTime.now();
        this.member = findMember;
    }


    // 내 주문내역에서 환불했을때 환불데이터를 저장하기위해서
    @Builder
    public ProductPayment(ResponseGetRefundDetail resultRefundData, Member findMember) {
        this.impUid = resultRefundData.getImp_uid();
        this.productTotalPrice = resultRefundData.getCancel_amount();
        this.status = resultRefundData.getStatus();
        this.createdAt = LocalDateTime.now();
        this.member = findMember;
    }

}
