package com.kj.product.repository;

import com.kj.product.dto.*;
import com.kj.product.entity.*;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static com.kj.product.entity.QProduct.*;
import static com.kj.product.entity.QProductImage.productImage;
import static com.kj.product.entity.QProductSize.productSize1;
import static com.kj.product.entity.QProductTag.productTag1;
import static com.kj.productCategory.entity.QProductCategory.productCategory;


@Slf4j
public class ProductRepositoryImpl implements ProductRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT,em);
    }

    @Override
    public int findByMaxProductId() {
        Long result = queryFactory
                .select(product.id.max())
                .from(product)
                .fetchOne();
        if(result == null){
            return 1;
        }
        return Integer.parseInt(String.valueOf(result)) + 1;
    }

    @Override
    public Optional<Product> findByProductId(int no) {
        Product result = queryFactory.selectFrom(product)
                .leftJoin(productImage)
                .on(product.id.eq(productImage.product.id)).fetchJoin()
                .leftJoin(productSize1)
                .on(product.id.eq(productSize1.product.id)).fetchJoin()
                .leftJoin(productTag1)
                .on(product.id.eq(productTag1.product.id)).fetchJoin()
                .where(product.id.eq((long) no))
                .fetchOne();
//        result ===>>Product(id=1, productName=123, productNumber=123, productPrice=123, gender=남자, productSeanson=123, clickCount=0, createdAt=2024-01-02T10:52:07.937482, productDetailImageBucket=1, productDatailImage=<p>111</p>, writer=작성자, updateedAt=null, productImages=[com.kj.product.entity.ProductImage@3338ed8a, com.kj.product.entity.ProductImage@5c30981e], productSize=[com.kj.product.entity.ProductSize@27fe2d5, com.kj.product.entity.ProductSize@69e947ce, com.kj.product.entity.ProductSize@71e379be, com.kj.product.entity.ProductSize@10da9652, com.kj.product.entity.ProductSize@6dcea7d6], productTags=[com.kj.product.entity.ProductTag@78ba7566, com.kj.product.entity.ProductTag@6448ee0d], productCategory=ProductCategory(id=1, mainProductCategoryId=100, mainProductCategoryName=상의, subProductCategoryName=반팔), productCart= [], productLike=[], productReview=[], productQnA=[])


//        List<ProductUpdateDto> result =  queryFactory.selectFrom(product)
//                .leftJoin(productImage)
//                .on(product.id.eq(productImage.product.id))
//                .leftJoin(productSize1)
//                .on(product.id.eq(productSize1.product.id))
//                .leftJoin(productTag1)
//                .on(product.id.eq(productTag1.product.id))
//                .where(product.id.eq((long) no))
//                .transform(GroupBy.groupBy(product.id).list(
//                      new QProductUpdateDto(
//                              product.id,
//                              product.productName,
//                              product.productNumber,
//                              product.productPrice,
//                              product.gender,
//                              product.productSeanson,
//                              product.writer,
//                              product.createdAt,
//                              product.updateedAt,
//                              product.productDetailImageBucket,
//                              product.productDatailImage,
//                              new QProductCategoryUpdateDto(
//                                              productCategory.id,
//                                              productCategory.mainProductCategoryId,
//                                              productCategory.mainProductCategoryName,
//                                              productCategory.subProductCategoryName
//                                      ),
//                              GroupBy.set(
//                                      new QProductSizeUpdateDto(
//                                              productSize1.id,
//                                              productSize1.productSize,
//                                              productSize1.productCount
//                                      )
//                              ),
//                              GroupBy.set(
//                                      new QProductImageUpdateDto(
//                                              productImage.id,
//                                              productImage.imageName,
//                                              productImage.thubmnail,
//                                              productImage.bucketName
//                                      )
//                              ),
//                              GroupBy.set(
//                                      new QProductTagUpdateDto(
//                                              productTag1.id,
//                                              productTag1.productTag
//                                      )
//                              )
//
//                )));

        return Optional.ofNullable(result);
    }
}
