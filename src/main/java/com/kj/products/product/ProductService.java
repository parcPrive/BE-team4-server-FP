package com.kj.products.product;

import com.amazonaws.services.s3.model.*;
import com.kj.config.S3Config;
import com.kj.member.entity.Member;
import com.kj.member.repository.MemberRepository;
import com.kj.products.product.dto.*;
import com.kj.products.product.entity.*;
import com.kj.products.product.repository.*;
import com.kj.productCategory.Repository.ProductCategoryRepository;
import com.kj.productCategory.entity.ProductCategory;
import com.kj.products.productCart.dto.ProductCartInsertDto;
import com.kj.products.productCart.entity.ProductCart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            insertProductTag(productInputDto.getProductTag(),result);
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
    public void insertProductTag(List<String> productTags, Product product){
        for(String productTag : productTags){
            log.info("productTag ===>> {}", productTag );
            ProductTag insertProductTag = new ProductTag(productTag,product);
            productTagRepository.save(insertProductTag);

        }
    }



    public int findByMaxProductId(){
        int result = productRepository.findByMaxProductId();
        return result;
    }
    // ======================update===========================================
    @Transactional
    public void updateProduct(int no, ProductUpdateInputDto productUpdateInputDto) throws IOException {
        Product findProduct = productRepository.findByProductId(no).orElseThrow(() -> new RuntimeException("asd"));
        ProductCategory findProductCategory = productCategoryRepository.findById(productUpdateInputDto.getSubProductCategoryId()).orElseThrow(() -> new RuntimeException("카테고리가 없습니다."));
        List<String> updateImageList = updateImageCheck(findProduct, productUpdateInputDto); // 업데이트 한 이미지와 기존 이미지가 있다.
        int checkProductDetailImageFolder = updateCkeditorS3FolderCheck(productUpdateInputDto.getNewBucketName()); // ck에디터에 새로운 폴더의 생성 여부 판단 있으면 1이상 없으면 0
        if(checkProductDetailImageFolder != 0){
            productUpdateInputDto.setProductDetailImageBucket(productUpdateInputDto.getNewBucketName());
            productUpdateInputDto.setProductDetailImage(productUpdateInputDto.getProductDetailImage());

        }else{
            productUpdateInputDto.setProductDetailImage(productUpdateInputDto.getProductDetailImage()); //디테일이미지의 사진은 그대로 지만 글을 바꿨을때
        }
        findProduct.setProduct(productUpdateInputDto, findProductCategory); // product entity의 바뀐내용들
        productImageRepository.deleteProductImagebyProductId(no);
        for(String bbb : updateImageList){
            log.info("진짜 진짜진짜 ===>>> {}", bbb);
        }

        insertProductUpdateImage(updateImageList, findProduct);
        productSizeRepository.deleteProductSizeByProductId(findProduct.getId());
        insertProductUpdateSize(productUpdateInputDto, findProduct);
        productTagRepository.deleteProductTagbyProductId(findProduct.getId());
        insertProductTag(productUpdateInputDto.getProductTag(), findProduct);
    }



    public String S3UploadProductDetailImage(MultipartFile productDetailImage, String bucketName) throws IOException {
        String localLocation = myLocalFolder;
        String fileName = productDetailImage.getOriginalFilename();
        String ext = fileName.substring(fileName.indexOf("."));
        String uuidFileName = UUID.randomUUID() + ext;
        String localPath = localLocation + uuidFileName;
        File localFile = new File(localPath);

        productDetailImage.transferTo(localFile);
        String S3bucketName = bucket + "/productdetailimage/" + bucketName;
        s3Config.amazonS3Client().putObject(new PutObjectRequest(S3bucketName, uuidFileName,localFile).withCannedAcl(CannedAccessControlList.PublicRead));
        String s3Url = s3Config.amazonS3Client().getUrl(S3bucketName, uuidFileName).toString();
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
    private void insertProductUpdateSize(ProductUpdateInputDto productUpdateInputDto, Product findProduct) {
            ProductSize productSizeS = new ProductSize(productUpdateInputDto.getProductSizeS(), productUpdateInputDto.getProductCountS(), findProduct);
            ProductSize productSizeM = new ProductSize(productUpdateInputDto.getProductSizeM(), productUpdateInputDto.getProductCountM(), findProduct);
            ProductSize productSizeL = new ProductSize(productUpdateInputDto.getProductSizeL(), productUpdateInputDto.getProductCountL(), findProduct);
            ProductSize productSizeXL = new ProductSize(productUpdateInputDto.getProductSizeXL(), productUpdateInputDto.getProductCountXL(), findProduct);
            ProductSize productSizeXXL = new ProductSize(productUpdateInputDto.getProductSizeXXL(), productUpdateInputDto.getProductCountXXL(), findProduct);
            productSizeRepository.saveAll(Arrays.asList(productSizeS, productSizeM, productSizeL, productSizeXL, productSizeXXL));
    }

    @Transactional
    private void insertProductUpdateImage(List<String> updateImageList, Product findProduct) {
        List<ProductImage> insertProductUpdateImages = new ArrayList<>();
        log.info("여기가 0이라고???? ===>>> {}",updateImageList.size());
        for(int i = 0; i < updateImageList.size(); i++){
            log.info("이미지들 ===>>> {}", updateImageList.get(i));
        }
        for(int i = 0; i < updateImageList.size(); i++){
            if(i == 0)  insertProductUpdateImages.add(new ProductImage(updateImageList.get(i), 1, findProduct.getProductImages().get(i).getBucketName(), findProduct));
            else insertProductUpdateImages.add(new ProductImage(updateImageList.get(i), 0, findProduct.getProductImages().get(i).getBucketName(), findProduct));
        }
        productImageRepository.saveAll(insertProductUpdateImages);
    }

    private int updateCkeditorS3FolderCheck(String newBucketName) {
        String S3FolderName = "productdetailimage/"+ newBucketName +"/";
        ObjectListing productDetailImageFolder =  s3Config.amazonS3Client().listObjects(bucket, S3FolderName);
        log.info("asdasdasdas {}", newBucketName);
        log.info("버킷사이즈는???  ===>>> {}", productDetailImageFolder.getObjectSummaries().size());
        int checkProductDetailImgaeFolder = productDetailImageFolder.getObjectSummaries().size();
        return checkProductDetailImgaeFolder;
    }

    @Transactional
    public List<String> updateImageCheck(Product findproduct, ProductUpdateInputDto productUpdateInputDto) throws IOException {
        log.info("productInputDto ==> {}", productUpdateInputDto);
        List<ProductImage> prevImageList = findproduct.getProductImages(); // 수정 전의 이미지들의 값들
        List<MultipartFile> updateProductFiles = new ArrayList<>(); // 수정한 이미지들의 파일들
        Map<Long, String> deleteProductIamgeList = new HashMap<Long, String>(); // 기존에 있는 이미지를 삭제할 사진들
        List<String> updateProductImageList = new ArrayList<>(); // 수정한 이미지들의 이름들 그리고 이게 데이터 베이스에 저장 될 것들
        String bucketName = prevImageList.get(1).getBucketName();
        // 수정 안 한 이미지는 꺼내보면 공백이라 체크해서 원본 이미지를 넣어준다. 그리고 수정된 이미지는 공백으로 준다
        // 공백으로 주는 이유는 updateProductImageList 여기에는 데이터베이스에 들어가는거기 때문에 s3url을 담아야 하기 때문에 공백으로 준거다.
        for(int i = 0; i < productUpdateInputDto.getFile().size(); i++){
            if(productUpdateInputDto.getFile().get(i).getOriginalFilename().isEmpty()){
                updateProductImageList.add(prevImageList.get(i).getImageName());
            }else{
                updateProductFiles.add(productUpdateInputDto.getFile().get(i));
                deleteProductIamgeList.put(prevImageList.get(i).getId(), prevImageList.get(i).getImageName());
                updateProductImageList.add("");
            }
        }

        S3UpdateProductImageDelete(deleteProductIamgeList, bucketName); // 수정된 이미지가 있을 경우 원본 이미지를 삭제하는 로직이다.
        List<String> updateProductImageUrl = S3UpdateProductImageInsert(updateProductFiles, bucketName); // 수정된 이미지 파일들을 s3에 저장하고 이미지 url을 반환한다.

        for(int i = 0; i < updateProductImageList.size(); i++){
            if(updateProductImageList.get(i).isEmpty()){
                updateProductImageList.set(i, updateProductImageUrl.get(i));
            }
        }
        for(String update : updateProductImageList){
            log.info("없데이트 사진 제발 나외라 =====>>>{}", update);
        }
        return updateProductImageList;
    }

    @Transactional
    private List<String> S3UpdateProductImageInsert(List<MultipartFile> updateProductFiles, String bucketName) throws IOException {
        String localLocation = myLocalFolder + UUID.randomUUID() + "/";
        File folder = new File(localLocation);
        List<String> updateProductImageName = new ArrayList<>();
        for(MultipartFile updateProductFile : updateProductFiles){
            updateProductImageName.add(updateProductFile.getOriginalFilename());
        }
        if(!folder.exists()) folder.mkdir();
        else log.info("폴더 생성 실패");
        List<String> updateProductImageUrl = new ArrayList<>();
        for(int i = 0; i < updateProductImageName.size(); i++){
            String localPath = localLocation + updateProductImageName.get(i);
            File localFile = new File(localPath);
            updateProductFiles.get(i).transferTo(localFile);
            s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket + "/productimage/" + bucketName, updateProductImageName.get(i), localFile).withCannedAcl(CannedAccessControlList.PublicRead));
            String s3Url = s3Config.amazonS3Client().getUrl(bucket + "/productimage/" + bucketName, updateProductImageName.get(i)).toString();
            log.info("s3User ===>>> {}", s3Url);
            updateProductImageUrl.add(s3Url);
        }
        while(folder.exists()){
            File[] folderList = folder.listFiles();
            for(int i = 0; i < folderList.length; i++){
                folderList[i].delete();
            }
            if(folderList.length == 0 && folder.isDirectory()){
                folder.delete();
                log.info("폴더삭제");
            }
        }
        return updateProductImageUrl;
    }
//    private void deletePrevProductDetailImageFolder(String productDetailImageBucket) {
//        try{
//            String S3BucketName = bucket + "/productdetailimage";
//            String S3FolderName = "16aec1d0-9c5a-4ace-ae38-1d92793dac73";
//            boolean isS3Folder = s3Config.amazonS3Client().doesObjectExist(S3BucketName, S3FolderName);
//            log.info("============");
//            log.info("{}",isS3Folder);
//            log.info("============");
//        }catch (AmazonS3Exception e){
//            log.info("amazonexception ===>>> {}", e);
//        }
//    }
    public void S3UpdateProductImageDelete(Map<Long, String> deleteProductIamgeMap, String bucketName){
        try{
            deleteProductIamgeMap.forEach((imageId, imageName) ->{
                String deleteProductimageName =  imageName;
                String S3bucketName = bucket + "/productimage/" + bucketName;
                String[] S3imageName = imageName.split(bucketName + "/");
                log.info("previmage ===>>> {}", deleteProductimageName);
                log.info("bucketname ===>>{}", S3bucketName);
                log.info("S3imageName[0] ====>>>{}", S3imageName[0]);
                log.info("S3imageName[1] ====>>>{}", S3imageName[1]);

                boolean isObjectExist = s3Config.amazonS3Client().doesObjectExist(S3bucketName, S3imageName[1]);
                if(isObjectExist){
                    log.info("여기서는??? =-==>>>{}", S3imageName[1]);
                s3Config.amazonS3Client().deleteObject(new DeleteObjectRequest(S3bucketName, S3imageName[1]));
                    log.info("삭제성공!!!!");
                }else{
                    log.info("삭제실패!!!!");
                }
            });

        }catch (AmazonS3Exception e){
            log.info("{} ", e);
        }
    }
// =================================productListPage==============================================
@Transactional
public PageImpl<ProductListDto> findListProductPage(int page, ProductSearchCondotion productSearchCondotion) {
    Pageable pageable = PageRequest.of(page - 1, 12);
    PageImpl<ProductListDto> findListProducts = productRepository.findListProducPage(pageable, productSearchCondotion);
    return findListProducts;

}

    public void insertTest(ProductInputDto productInputDto) {
        Optional<ProductCategory> findProductCategory = productCategoryRepository.findById(productInputDto.getSubProductCategoryId());
        if(findProductCategory.isPresent()) {
            for(int i = 0; i < 100; i++){
                productInputDto.setProductName("반팔" + i);
                productInputDto.setProductPrice(123 + i);
                productInputDto.setProductDetailImage("<p>123<img src=\"https://s3.ap-northeast-2.amazonaws.com/mok-s3/productdetailimage/9c34234b-dfda-446f-9e87-391cff3098df/1a19b245-4c65-4e0e-9a7a-b97d249ffe35.jpg\" width=\"150\" height=\"150\"></p>");
                productInputDto.setProductDetailImageBucket("123");
                Product insertProduct = new Product(productInputDto, findProductCategory.get());
                Product result = productRepository.save(insertProduct);
                insertProductSize(productInputDto,result);

                List<ProductImage> insertProductImages = new ArrayList<>();
                insertProductImages.add(new ProductImage("https://s3.ap-northeast-2.amazonaws.com/mok-s3/productdetailimage/9c34234b-dfda-446f-9e87-391cff3098df/1a19b245-4c65-4e0e-9a7a-b97d249ffe35.jpg", 1, "asdasd", result));
                insertProductImages.add(new ProductImage("https://s3.ap-northeast-2.amazonaws.com/mok-s3/productdetailimage/9c34234b-dfda-446f-9e87-391cff3098df/1a19b245-4c65-4e0e-9a7a-b97d249ffe35.jpg", 0, "asdasd", result));

                productImageRepository.saveAll(insertProductImages);

                insertProductTag(productInputDto.getProductTag(),result);

            }
        }
    }

    @Transactional
    public ProductFindOneDto findOneByProductId(int productId) {
        ProductFindOneDto findOneProduct = productRepository.findByProductId1(productId);
        log.info("파인트원 프로덕트 ====>>> {}", findOneProduct);
        return findOneProduct;
    }

}
