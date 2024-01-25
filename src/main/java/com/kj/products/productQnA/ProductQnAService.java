package com.kj.products.productQnA;

import com.kj.member.entity.Member;
import com.kj.member.repository.MemberRepository;
import com.kj.products.product.entity.Product;
import com.kj.products.product.repository.ProductRepository;
import com.kj.products.productQnA.dto.ProductAdminQnAInputDto;
import com.kj.products.productQnA.dto.ProductAdminQnAReturnDto;
import com.kj.products.productQnA.dto.ProductQnAInputDto;

import com.kj.products.productQnA.dto.ProductQnAInsertReturnDto;
import com.kj.products.productQnA.entity.ProductQnA;
import com.kj.products.productQnA.repository.ProductQnARepository;
import com.kj.products.productQnACategory.entity.ProductQnACategory;
import com.kj.products.productQnACategory.repository.ProductQnACategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductQnAService {
    private final ProductQnARepository productQnARepository;
    private final ProductRepository productRepository;
    private final ProductQnACategoryRepository productQnACategoryRepository;
    private final MemberRepository memberRepository;

//    @Transactional
    public ProductQnAInsertReturnDto insertProductQnAUser(ProductQnAInputDto productQnAInputDto) {
        Optional<Product> findProduct = productRepository.findByProductId(productQnAInputDto.getProductId());
        Optional<ProductQnACategory> findProductQnACategory = productQnACategoryRepository.findById(productQnAInputDto.getProductQACategoryId());
        Optional<Member> findMember = memberRepository.findByNickName(productQnAInputDto.getUserName());
        if(!findProduct.isPresent() || !findProductQnACategory.isPresent() || !findMember.isPresent()) new RuntimeException("무언가가 없다.");
        ProductQnA productQnA = new ProductQnA(productQnAInputDto, findProductQnACategory.get(), findProduct.get(), findMember.get());
        ProductQnA insertProductQnA = productQnARepository.save(productQnA);
        ProductQnAInsertReturnDto returnProductQnA = new ProductQnAInsertReturnDto(insertProductQnA);
       return returnProductQnA;
    }

    public ProductAdminQnAReturnDto insertProductAdminQnA(ProductAdminQnAInputDto productAdminQnAInputDto) {
        Optional<Product> findProduct =productRepository.findById(productAdminQnAInputDto.getProductId());
        Optional<Member> findMember =memberRepository.findByNickName(productAdminQnAInputDto.getAdminId());
        Optional<ProductQnA> findParentProductQnA =productQnARepository.findById(productAdminQnAInputDto.getProductQnAId());
        if(!findProduct.isPresent() || !findMember.isPresent() || !findParentProductQnA.isPresent()) new RuntimeException("무언가가 없다.");
        ProductQnA productQnA = new ProductQnA(productAdminQnAInputDto,findProduct.get(),findMember.get(), findParentProductQnA.get());
        ProductQnA insertProductAdminQnA =productQnARepository.save(productQnA);
        ProductAdminQnAReturnDto productAdminQnAReturnDto = new ProductAdminQnAReturnDto(insertProductAdminQnA);
        return productAdminQnAReturnDto;
    }
}
