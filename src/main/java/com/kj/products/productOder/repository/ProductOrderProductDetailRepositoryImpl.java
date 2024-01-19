package com.kj.products.productOder.repository;

import com.kj.products.product.entity.QProduct;
import com.kj.products.product.entity.QProductImage;
import com.kj.products.product.entity.QProductSize;
import com.kj.products.productOder.dto.ProductOrderSuccessDto;
import com.kj.products.productOder.entity.MyProductOrderDto;
import com.kj.products.productOder.entity.ProductOrderProductDetail;
import com.kj.products.productOder.entity.QProductOrderInfo;
import com.kj.products.productOder.entity.QProductOrderProductDetail;
import com.kj.products.productPayment.entity.QProductPayment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.kj.products.product.entity.QProduct.product;
import static com.kj.products.product.entity.QProductImage.productImage;
import static com.kj.products.product.entity.QProductSize.productSize1;
import static com.kj.products.productOder.entity.QProductOrderInfo.productOrderInfo;
import static com.kj.products.productOder.entity.QProductOrderProductDetail.productOrderProductDetail;
import static com.kj.products.productPayment.entity.QProductPayment.productPayment;

@Slf4j
public class ProductOrderProductDetailRepositoryImpl implements ProductOrderProductDetailRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ProductOrderProductDetailRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<ProductOrderSuccessDto> findProductOrdersByOrderId(List<Long> orderIds) {
        List<ProductOrderProductDetail> findOrders = queryFactory.selectFrom(productOrderProductDetail)
                .join(productOrderProductDetail.productSize,productSize1).fetchJoin()
                .join(productSize1.product, product).fetchJoin()
                .join(product.productImages, productImage).fetchJoin()
                .join(productOrderProductDetail.productOrder, productOrderInfo).fetchJoin()
                .where(productOrderProductDetail.id.in(orderIds), productImage.thubmnail.eq(1))
                .fetch();
        List<ProductOrderSuccessDto> productOrderSuccessDtoList = new ArrayList<>();
        for(ProductOrderProductDetail findOrder : findOrders){
            productOrderSuccessDtoList.add(new ProductOrderSuccessDto(findOrder));
        }
        return productOrderSuccessDtoList;

    }

    @Override
    public List<MyProductOrderDto> findProductOrdersByUserNickName(String userNickName) {
        List<ProductOrderProductDetail> findMyOrers = queryFactory.selectFrom(productOrderProductDetail)
                .join(productOrderProductDetail.productSize,productSize1).fetchJoin()
                .join(productSize1.product, product).fetchJoin()
                .join(product.productImages, productImage).fetchJoin()
                .join(productOrderProductDetail.productPayment, productPayment).fetchJoin()
                .where(productOrderProductDetail.member.nickName.eq(userNickName),productImage.thubmnail.eq(1))
                .orderBy(productPayment.createdAt.desc())
                .fetch();
        List<MyProductOrderDto> myProductOrders = new ArrayList<>();
        for(ProductOrderProductDetail findMyOrder : findMyOrers){
            myProductOrders.add(new MyProductOrderDto(findMyOrder));
        }
        return myProductOrders;


    }

    @Override
    public ProductOrderProductDetail findProductPriceNimpUidByProductOrderId(Long productOrderId) {
        return queryFactory.selectFrom(productOrderProductDetail)
                .join(productOrderProductDetail.productPayment, productPayment).fetchJoin()
                .where(productOrderProductDetail.id.eq(productOrderId))
                .fetchOne();


    }
}
