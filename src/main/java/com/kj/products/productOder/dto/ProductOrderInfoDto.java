package com.kj.products.productOder.dto;

import com.kj.member.entity.Member;
import com.kj.products.product.entity.ProductSize;
import com.kj.products.productCart.entity.ProductCart;
import lombok.Builder;
import lombok.Data;
// 오더 정보를 보여 주는 dto(결제하기 마지막 단계)
// 배송지와 결제할 상품들
@Data
public class ProductOrderInfoDto {
    private Long productId;
    private Long productCartId;
    private String userName;
    private String userPhoneNum;
    private String userPostCode;
    private String userAddress;
    private String userDetailAddress;
    private String productName;
    private ProductSize productSize;
    private int productPrice;
    private int productCount;
    private String productImage;
    @Builder
    public ProductOrderInfoDto(ProductCart findProductOrderList) {
        this.productId = findProductOrderList.getProductSize().getProduct().getId();
        this.productCartId = findProductOrderList.getId();
        this.userName = findProductOrderList.getMember().getUserName();
        this.userPhoneNum = findProductOrderList.getMember().getPhone();
        this.userPostCode = findProductOrderList.getMember().getPostCode();
        this.userAddress = findProductOrderList.getMember().getAddress();
        this.userDetailAddress = findProductOrderList.getMember().getAddressDetail();
        this.productName = findProductOrderList.getProductSize().getProduct().getProductName();
        this.productSize = findProductOrderList.getProductSize();
        this.productPrice = findProductOrderList.getProductCount() * findProductOrderList.getProductSize().getProduct().getProductPrice();
        this.productCount = findProductOrderList.getProductCount();
        this.productImage = findProductOrderList.getProductSize().getProduct().getProductImages().get(0).getImageName();
    }


}
