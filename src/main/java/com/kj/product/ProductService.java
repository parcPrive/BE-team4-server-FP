package com.kj.product;

import com.kj.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    @Value("${file.path}")
    private String myLocalFolder;

    public int findByMaxProductId(){
        int result = productRepository.findByMaxProductId();
        return result;

    }
    public String S3UploadProductDetailImage(MultipartFile productDetailImage, int no){
        String localLocation = myLocalFolder;
        String fileName = productDetailImage.getOriginalFilename();
        return null;
    }
}
