package com.kj.product;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kj.config.S3Config;
import com.kj.product.dto.ProductInputDto;
import com.kj.product.entity.Product;
import com.kj.product.entity.ProductSize;
import com.kj.product.repository.ProductRepository;
import com.kj.product.repository.ProductSizeRepository;
import com.kj.productCategory.Repository.ProductCategoryRepository;
import com.kj.productCategory.entity.ProductCategory;
import com.kj.productCategory.entity.QProductCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductSizeRepository productSizeRepository;
    private final S3Config s3Config;
    @Value("${file.path}")
    private String myLocalFolder;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public void insertProduct(ProductInputDto productInputDto){
        Optional<ProductCategory> findProductCategory = productCategoryRepository.findById(productInputDto.getSubProductCategoryId());


        if(findProductCategory.isPresent()){
//            Product insertProduct = new Product(productInputDto)
        }

//        ProductSize productSizeS = new ProductSize(productInputDto.getProductSizeS(),productInputDto.getProductCountS());
//        ProductSize productSizeM = new ProductSize(productInputDto.getProductSizeM(),productInputDto.getProductCountM());
//        ProductSize productSizeL = new ProductSize(productInputDto.getProductSizeL(),productInputDto.getProductCountL());
//        ProductSize productSizeXL = new ProductSize(productInputDto.getProductSizeXL(),productInputDto.getProductCountXL());
//        ProductSize productSizeXXL = new ProductSize(productInputDto.getProductSizeXXL(),productInputDto.getProductCountXXL());
//        productSizeRepository.saveAll(Arrays.asList(productSizeS,productSizeM,productSizeL,productSizeXL,productSizeXXL));
//        productSizeRepository.save


//        Product insertProduct = productRepository.save(product);

    }

    public void insertProductImage(ProductInputDto productInputDto){
        String localLocation = myLocalFolder + UUID.randomUUID() + "/";
        File folder = new File(localLocation);
//        String file1 = productInputDto.getFile1().getOriginalFilename();
//        String file2 = productInputDto.getFile2().getOriginalFilename();
//        String file3 = productInputDto.getFile3().getOriginalFilename();
//        String file4 = productInputDto.getFile4().getOriginalFilename();
//        String file5 = productInputDto.getFile5().getOriginalFilename();

//        String ext1 = file1.substring(file1.indexOf("."));

//        String ext = fileName.substring(fileName.indexOf("."));
//        String uuidFileName = UUID.randomUUID() + ext;
        log.info("이미지들!!====>>> 1 === {}" , productInputDto.getFile1().getOriginalFilename());

        if(!folder.exists()){
            folder.mkdir();
            log.info("폴더생성");
        }else{
            log.info("폴더실패");
        }

    }

    public int findByMaxProductId(){
        int result = productRepository.findByMaxProductId();
        return result;

    }
    public String S3UploadProductDetailImage(MultipartFile productDetailImage, int no) throws IOException {
        String localLocation = myLocalFolder;

        String fileName = productDetailImage.getOriginalFilename();
        String ext = fileName.substring(fileName.indexOf("."));
        String uuidFileName = UUID.randomUUID() + ext;

        log.info("이미지 ===>>{}" , uuidFileName );
        String localPath = localLocation + uuidFileName;
        log.info("localPath ===>>{}" , localPath );
        File localFile = new File(localPath);
//        File aaa = new File(uuidFileName);
        log.info("localFile ===>>{}" , localFile);
//        log.info("new File ===>> {}" , new File(uuidFileName));
        productDetailImage.transferTo(localFile);

        s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket + "/productdetailimage/" + no ,uuidFileName,localFile).withCannedAcl(CannedAccessControlList.PublicRead));
        String s3Url = s3Config.amazonS3Client().getUrl(bucket + "/productdetailimage/" + no, uuidFileName).toString();
        log.info("s3yurl ===>> {}" , s3Url);
        localFile.delete();
        return s3Url;
    }
}
