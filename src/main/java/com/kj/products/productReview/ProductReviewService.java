package com.kj.products.productReview;



import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kj.config.S3Config;
import com.kj.member.entity.Member;
import com.kj.member.repository.MemberRepository;
import com.kj.products.product.entity.Product;
import com.kj.products.product.repository.ProductRepository;
import com.kj.products.productReview.dto.ProductinsertReviewDto;
import com.kj.products.productReview.entity.ProductReview;
import com.kj.products.productReview.repository.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductReviewService {
    private final ProductReviewRepository productReviewRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    private final S3Config s3Config;
    @Value("${file.path}")
    private String myLocalFolder;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public ProductReview InsertProductReview(ProductinsertReviewDto productinsertReviewDto) throws IOException {
        Optional<Product> findProduct =  productRepository.findByProductId(productinsertReviewDto.getProductId());
        Optional<Member> findMember = memberRepository.findByNickName(productinsertReviewDto.getUserName());
        if(!findProduct.isEmpty() || !findMember.isEmpty()){
            new RuntimeException("없는 상품 이거나 없는 회원입니다. 다시 조회해주세여.");
        }
        List<String> productReviewImages = S3ProductReviewImageUpload(productinsertReviewDto.getProductReviewImages());
        ProductReview insertProductReview = new ProductReview(productinsertReviewDto, productReviewImages,findProduct.get(),findMember.get());
        return productReviewRepository.save(insertProductReview);
    }

    private List<String> S3ProductReviewImageUpload(List<MultipartFile> productReviewImages) throws IOException {
        String localLocation = myLocalFolder + UUID.randomUUID() + "/";
        File folder = new File(localLocation);
        List<String> productReviewImageList = new ArrayList<>();
        for(MultipartFile inputProductReviewImage : productReviewImages){
            if(inputProductReviewImage.isEmpty()){
                log.info("이미지가 없다면??");
                productReviewImageList.add("no");
            }else{
                String productReviewImageName = inputProductReviewImage.getOriginalFilename();
                productReviewImageList.add(UUID.randomUUID() + productReviewImageName.substring(productReviewImageName.indexOf(".")));
            }
            log.info("이미지 ==>>> {}", productReviewImageList);
        }
        if(!folder.exists()){
            folder.mkdir();
        }else{
            new RuntimeException("폴더생성 실패");
        }
        String S3FolderName = String.valueOf(UUID.randomUUID());
        List<String>  productReviewImageUrl = new ArrayList<>();
        for(int i = 0; i < productReviewImageList.size(); i++){
            if(productReviewImageList.equals("no")){
                productReviewImageUrl.add("https://s3.ap-northeast-2.amazonaws.com/mok-s3/productdetailimage/9c34234b-dfda-446f-9e87-391cff3098df/1a19b245-4c65-4e0e-9a7a-b97d249ffe35.jpg");
            }else{
                String localPath = localLocation + productReviewImageList.get(i);
                File localFile = new File(localPath);
                productReviewImages.get(i).transferTo(localFile);
                s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket + "/productReviewImage/" + S3FolderName, productReviewImageList.get(i),localFile).withCannedAcl(CannedAccessControlList.PublicRead));
                String s3Uril = s3Config.amazonS3Client().getUrl(bucket + "/productReviewImage/" + S3FolderName, productReviewImageList.get(i)).toString();
                productReviewImageUrl.add(s3Uril);
            }
        }
        return productReviewImageUrl;
    }
}
