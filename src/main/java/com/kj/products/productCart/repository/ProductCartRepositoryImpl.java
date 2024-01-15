package com.kj.products.productCart.repository;

import com.kj.member.entity.QMember;
import com.kj.products.product.entity.QProduct;
import com.kj.products.product.entity.QProductImage;
import com.kj.products.product.entity.QProductSize;
import com.kj.products.productCart.dto.ProductCartListDto;
import com.kj.products.productCart.entity.ProductCart;
import com.kj.products.productCart.entity.QProductCart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.kj.member.entity.QMember.*;
import static com.kj.products.product.entity.QProduct.*;
import static com.kj.products.product.entity.QProductImage.*;
import static com.kj.products.product.entity.QProductSize.productSize1;
import static com.kj.products.productCart.entity.QProductCart.*;
@Slf4j
public class ProductCartRepositoryImpl implements ProductCartRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ProductCartRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Long countByUserId(String userId) {
        return queryFactory.select(productCart.count())
                .from(productCart)
                .where(productCart.member.userId.eq(userId))
                .fetchOne();
    }

    @Override
    public List<ProductCartListDto> findByUserId(String userId) {
        List<ProductCart> findProductCart = queryFactory.selectFrom(productCart)
                .join(productCart.member, member).fetchJoin()
                .join(productCart.productSize, productSize1).fetchJoin()
                .join(productCart.productSize.product,product).fetchJoin()
                .join(productCart.productSize.product.productImages, productImage).fetchJoin()
                .where(productCart.member.userId.eq(userId), productImage.thubmnail.eq(1))
                .fetch();
        List<ProductCartListDto> productCartList = new ArrayList<>();
        for(ProductCart findProductCartList : findProductCart){
            productCartList.add(new ProductCartListDto(findProductCartList));
        }
        log.info("productCartList ===>>> {}",productCartList);
        return productCartList;
    }
}
