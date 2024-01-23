package com.kj.products.product.repository;

import com.kj.products.product.dto.ProductFindOneDto;
import com.kj.products.product.dto.ProductListDto;
import com.kj.products.product.dto.ProductSearchCondotion;
import com.kj.products.product.entity.Product;
import com.kj.products.product.entity.ProductSize;
import com.kj.products.product.entity.ProductTag;

import com.kj.products.productReview.entity.ProductReview;
import com.kj.products.productReview.entity.QProductReview;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static com.kj.productCategory.entity.QProductCategory.productCategory;
import static com.kj.products.product.entity.QProduct.*;
import static com.kj.products.product.entity.QProductImage.productImage;
import static com.kj.products.product.entity.QProductLike.productLike;
import static com.kj.products.product.entity.QProductSize.productSize1;
import static com.kj.products.product.entity.QProductTag.productTag1;
import static com.kj.products.productReview.entity.QProductReview.productReview1;
import static org.springframework.util.StringUtils.hasText;


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
    public Optional<Product> findByProductId(long no) {
        Product result = queryFactory.selectFrom(product)
                .leftJoin(productImage)
                .on(product.id.eq(productImage.product.id)).fetchJoin()
                .leftJoin(productSize1)
                .on(product.id.eq(productSize1.product.id)).fetchJoin()
                .leftJoin(productTag1)
                .on(product.id.eq(productTag1.product.id)).fetchJoin()
                .where(product.id.eq( no))
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

    @Override
    public PageImpl<ProductListDto> findListProducPage(Pageable pageable, ProductSearchCondotion productSearchCondotion) {
            QueryResults<Long> productIds = queryFactory
                    .select(product.id)
                    .from(product)
                    .where(
                            categoryEq(productSearchCondotion.getCategory()),
                            productSearchWord(productSearchCondotion.getSearchWord())
                    )
                    .orderBy(product.id.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetchResults();

        List<Product> findListProducts = queryFactory
                    .selectFrom(product)
                    .join(product.productImages, productImage)
                    .fetchJoin()
                    .where(product.id.in(productIds.getResults()))
                    .orderBy(product.id.desc())
                    .fetch();
        List<ProductListDto> productListDtos = new ArrayList<>();
        for(Product result : findListProducts){
            productListDtos.add(new ProductListDto(result));
            }
            PageImpl<ProductListDto> pageList = new PageImpl<>(productListDtos, pageable, productIds.getTotal());
            return pageList;
    }

    @Override
    public ProductFindOneDto findByProductId1(int productId) {
        Product findOneProduct = queryFactory.selectFrom(product)
                .join(product.productImages, productImage).fetchJoin()
                .where(product.id.eq((long) productId))
                .fetchOne();
        List<ProductSize> findProductSize = queryFactory.selectFrom(productSize1)
                        .where(productSize1.product.id.eq((long) productId))
                        .fetch();
        List<ProductReview> findProductReview = queryFactory.selectFrom(productReview1)
                .where(productReview1.product.id.eq((long) productId))
                .orderBy(productReview1.id.desc())
                .fetch();
        List<ProductTag> findProductTags = queryFactory.selectFrom(productTag1)
                    .where(productTag1.product.id.eq((long) productId))
                    .fetch();
         Long producLike = queryFactory.select(productLike.product.count())
                         .from(productLike)
                                 .where(productLike.product.id.eq((long) productId))
                                         .fetchOne();

        log.info("프로덕트 아이디가 없다고??? ===>>> {}", findOneProduct);
        ProductFindOneDto result = new ProductFindOneDto(findOneProduct, findProductSize, findProductReview, findProductTags,producLike);
        log.info("====================================================={}", result);
        return result;

        /*.leftJoin(productTag1)
                .on(product.id.eq(productTag1.product.id)).fetchJoin()*/
    }

    private BooleanExpression categoryEq(String category){
        if(!hasText(category)) return null;
        else if(category.equals("all")) return null;
        else return productCategory.mainProductCategoryName.eq(category);
    }

    private BooleanExpression productSearchWord(String productSearchWord){
        return hasText(productSearchWord) ? product.productName.like("%" + productSearchWord + "%") : null;
    }
}
