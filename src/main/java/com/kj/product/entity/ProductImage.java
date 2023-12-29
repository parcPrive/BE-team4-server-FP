package com.kj.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.EnableMBeanExport;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {
    @Id
    private String id; // 저장 폴더이름

    private String imageName;

}
