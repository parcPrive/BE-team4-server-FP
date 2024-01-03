package com.kj.product;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kj.config.S3Config;
import com.kj.product.dto.ProductInputDto;
import com.kj.product.dto.ProductUpdateDto;
import com.kj.product.entity.Product;
import com.kj.product.entity.ProductImage;
import com.kj.product.entity.ProductSize;
import com.kj.product.entity.ProductTag;
import com.kj.product.repository.ProductImageRepository;
import com.kj.product.repository.ProductRepository;
import com.kj.product.repository.ProductSizeRepository;
import com.kj.product.repository.ProductTagRepository;
import com.kj.productCategory.Repository.ProductCategoryRepository;
import com.kj.productCategory.entity.ProductCategory;
import com.kj.productCategory.entity.QProductCategory;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    // repository
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductSizeRepository productSizeRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductTagRepository productTagRepository;

    private final S3Config s3Config;
    @Value("${file.path}")
    private String myLocalFolder;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;



    // =====================insert==========================================
    @Transactional
    public void insertProduct(ProductInputDto productInputDto) throws IOException {
        Optional<ProductCategory> findProductCategory = productCategoryRepository.findById(productInputDto.getSubProductCategoryId());
        if(findProductCategory.isPresent()) {
            Product insertProduct = new Product(productInputDto, findProductCategory.get());
            Product result = productRepository.save(insertProduct);

            insertProductSize(productInputDto,result);
            insertProductImage(productInputDto,result);
            insertProductTag(productInputDto,result);
        }
    }

    @Transactional
    public void insertProductImage(ProductInputDto productInputDto,Product product) throws IOException {
        String localLocation = myLocalFolder + UUID.randomUUID() + "/";
        File folder = new File(localLocation);
        MultipartFile[] productImage = {productInputDto.getFile1(), productInputDto.getFile2()};
        String file1 = productImage[0].getOriginalFilename();
        String file2 = productImage[1].getOriginalFilename();
//        String file3 = productInputDto.getFile3().getOriginalFilename();
//        String file4 = productInputDto.getFile4().getOriginalFilename();
//        String file5 = productInputDto.getFile5().getOriginalFilename();

        String fileName1 = UUID.randomUUID() + file1.substring(file1.indexOf("."));
        String fileName2 = UUID.randomUUID() + file2.substring(file2.indexOf("."));
//        String fileName3 = UUID.randomUUID() + file3.substring(file2.indexOf("."));
//        String fileName4 = UUID.randomUUID() + file4.substring(file2.indexOf("."));
//        String fileName5 = UUID.randomUUID() + file5.substring(file2.indexOf("."));
        String[] files = {fileName1, fileName2};
        log.info("이미지들!!====>>> 1 ===> {} 2 ====>> {}" , fileName1,fileName2);

        if(!folder.exists()){
            folder.mkdir();
            log.info("폴더생성");
        }else{
            log.info("폴더실패");
        }
        String bucketName = String.valueOf(UUID.randomUUID());
        String[] productImageUrl = new String[2];
        for(int i = 0; i < files.length; i++){
            String localPath = localLocation + files[i];
            File localFile = new File(localPath);
            productImage[i].transferTo(localFile);
            s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket + "/productimage/" + bucketName, files[i], localFile).withCannedAcl(CannedAccessControlList.PublicRead));
            String s3Url = s3Config.amazonS3Client().getUrl(bucket + "/productimage/" + bucketName, files[i]).toString();
            log.info("s3User ===>>> {}", s3Url);
            productImageUrl[i] = s3Url;
        }
        // product 데이터 받아오기 저장된

        ProductImage insertProductImage1 = new ProductImage(productImageUrl[0], 1, bucketName, product);
        ProductImage insertProductImage2 = new ProductImage(productImageUrl[1], 0, bucketName, product);

        productImageRepository.saveAll(Arrays.asList(insertProductImage1,insertProductImage2));
        // 로컬 폴더에있는 이미지 삭제후 폴더까지 삭제
        while(folder.exists()){
            File[] folerList = folder.listFiles();
            log.info("while문 안에서 도는중!!!");
            for(int i = 0; i < folerList.length; i++){
                folerList[i].delete();
                log.info("파일삭제!!");
            }
            if(folerList.length == 0 && folder.isDirectory()){
                folder.delete();
                log.info("폴더삭제!!");
            }
        }
    }
    @Transactional
    public void insertProductSize(ProductInputDto productInputDto, Product product){
        ProductSize productSizeS = new ProductSize(productInputDto.getProductSizeS(), productInputDto.getProductCountS(), product);
        ProductSize productSizeM = new ProductSize(productInputDto.getProductSizeM(), productInputDto.getProductCountM(), product);
        ProductSize productSizeL = new ProductSize(productInputDto.getProductSizeL(), productInputDto.getProductCountL(), product);
        ProductSize productSizeXL = new ProductSize(productInputDto.getProductSizeXL(), productInputDto.getProductCountXL(), product);
        ProductSize productSizeXXL = new ProductSize(productInputDto.getProductSizeXXL(), productInputDto.getProductCountXXL(), product);
        productSizeRepository.saveAll(Arrays.asList(productSizeS, productSizeM, productSizeL, productSizeXL, productSizeXXL));
    }
    @Transactional
    public void insertProductTag(ProductInputDto productInputDto, Product product){
        for(String productTag : productInputDto.getProductTag()){
            log.info("productTag ===>> {}", productTag );
            ProductTag insertProductTag = new ProductTag(productTag,product);
            productTagRepository.save(insertProductTag);

        }
    }

    // ======================insert===========================================

    public int findByMaxProductId(){
        int result = productRepository.findByMaxProductId();
        return result;

    }
    public String S3UploadProductDetailImage(MultipartFile productDetailImage, int no) throws IOException {
        String localLocation = myLocalFolder;

        String fileName = productDetailImage.getOriginalFilename();
        String ext = fileName.substring(fileName.indexOf("."));
        String uuidFileName = UUID.randomUUID() + ext;

        String localPath = localLocation + uuidFileName;
        File localFile = new File(localPath);
        productDetailImage.transferTo(localFile);

        s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket + "/productdetailimage/" + no ,uuidFileName,localFile).withCannedAcl(CannedAccessControlList.PublicRead));
        String s3Url = s3Config.amazonS3Client().getUrl(bucket + "/productdetailimage/" + no, uuidFileName).toString();
        log.info("s3yurl ===>> {}" , s3Url);
        localFile.delete();
        return s3Url;
    }

    @Transactional
    public ProductUpdateDto findByProductId(int no) {
        Product findProduct = productRepository.findByProductId(no);
        ProductUpdateDto result = new ProductUpdateDto(findProduct);
            log.info("productUpdateDto -=======>>>>> {}", result);

        return result;
    }

    public void updateProduct(int no){
        Product findProduct = productRepository.findByProductId(no);


    }
}
