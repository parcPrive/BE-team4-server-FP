package com.kj.products.productPayment.repository;

import com.kj.products.productPayment.entity.ProductPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPaymentRepository extends JpaRepository<ProductPayment,Long>, ProductPaymentRepositoryCustom {
}
