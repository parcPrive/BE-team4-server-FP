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
        List<String> productImages = new ArrayList<>();
        for(MultipartFile productImage : productInputDto.getFile()){
            String productImageFileName = productImage.getOriginalFilename();
            productImages.add(UUID.randomUUID() + productImageFileName.substring(productImageFileName.indexOf(".")));
        }
        if(!folder.exists()){
            folder.mkdir();
            log.info("폴더생성");
        }else{
            log.info("폴더실패");
        }
        String bucketName = String.valueOf(UUID.randomUUID());
        List<String> productImageUrl = new ArrayList<>();
        for(int i = 0; i < productImages.size(); i++){
            String localPath = localLocation + productImages.get(i);
            File localFile = new File(localPath);
            productInputDto.getFile().get(i).transferTo(localFile);
            s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket + "/productimage/" + bucketName, productImages.get(i), localFile).withCannedAcl(CannedAccessControlList.PublicRead));
            String s3Url = s3Config.amazonS3Client().getUrl(bucket + "/productimage/" + bucketName, productImages.get(i)).toString();
            log.info("s3User ===>>> {}", s3Url);
            productImageUrl.add(s3Url);
        }
        // product 데이터 받아오기 저장된
        List<ProductImage> insertProductImages = new ArrayList<>();
        for(int i = 0; i < productImageUrl.size(); i++){
            if(i == 0)  insertProductImages.add(new ProductImage(productImageUrl.get(i), 1, bucketName, product));
            else insertProductImages.add(new ProductImage(productImageUrl.get(i), 0, bucketName, product));
        }

        productImageRepository.saveAll(insertProductImages);
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
        String bucketName = bucket + "/productdetailimage/" + no;
        s3Config.amazonS3Client().putObject(new PutObjectRequest(bucketName, uuidFileName,localFile).withCannedAcl(CannedAccessControlList.PublicRead));
        String s3Url = s3Config.amazonS3Client().getUrl(bucketName, uuidFileName).toString();
        log.info("s3yurl ===>> {}" , s3Url);
        localFile.delete();
        return s3Url;
    }

    @Transactional
    public ProductUpdateDto findByProductId(int no) {
        Product findProduct = productRepository.findByProductId(no).orElseThrow( () -> new RuntimeException("asd"));
        ProductUpdateDto result = new ProductUpdateDto(findProduct);
            log.info("productUpdateDto -=======>>>>> {}", result);

        return result;
    }

    @Transactional
    public void updateProduct(int no, ProductInputDto productInputDto){
        Product findProduct = productRepository.findByProductId(no).orElseThrow(() -> new RuntimeException("asd"));
        log.info("findproduct ===>>>{}", findProduct);
        updateImageCheck(findProduct,productInputDto);


    }

    @Transactional
    public void updateImageCheck(Product findproduct, ProductInputDto productInputDto){
        List<ProductImage> prevImageList = findproduct.getProductImages();
        List<String> updateProductImageList = new ArrayList<>();
        for(MultipartFile updateProductImage : productInputDto.getFile()){
            if(updateProductImage == null){
//                updateProductImageList.add();
            }
            updateProductImageList.add(updateProductImage.getOriginalFilename());
        }
        for(int i = 0; i < updateProductImageList.size(); i++){
            if(updateProductImageList.get(i).isEmpty()) { // 이미지 변경 안했을 경우 기존값을 넣어준다.
                log.info("뭘까용??? ==>> {}", updateProductImageList.get(i));
                updateProductImageList.set(i, prevImageList.get(i).getImageName());
                log.info("뭘까용??? ==>> {}", updateProductImageList.get(i));
            }else{
                S3UpdateProductImageDelete(prevImageList, i);
            }
        }
    }

    public void S3UpdateProductImageDelete(List<ProductImage> prevImageList, int i){
        try{
            String prevImage = prevImageList.get(i).getImageName();
            //                bucket + "/productimage/" + bucketName
            String bucketName = bucket + "/productimage/" + prevImageList.get(i).getBucketName();
            String[] S3imageName = prevImage.split(prevImageList.get(i).getBucketName()+"/");
            log.info("previmage ===>>> {}", prevImage);
            log.info("bucketname ===>>{}", bucketName);
            log.info("sub ====>>>{}", S3imageName[0]);
            log.info("sub ====>>>{}", S3imageName[1]);

            boolean isObjectExist = s3Config.amazonS3Client().doesObjectExist(bucketName, S3imageName[1]);
            if(isObjectExist){
                s3Config.amazonS3Client().deleteObject(bucketName,S3imageName[1]);
                log.info("삭제성공!!!!");
            }else{
                log.info("삭제실패!!!!");
            }

        }catch (Exception e){

        }
    }
}
