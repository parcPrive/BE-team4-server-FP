package com.kj.product.entity;

import jakarta.persistence.*;
import org.springframework.context.annotation.EnableMBeanExport;

@Entity
public class ProductImage {
    @Id
    private String id; // 저장 폴더이름

    private String imageName;

}
