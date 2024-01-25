package com.kj.products.productQnA;

import com.kj.products.productQnA.dto.ProductAdminQnAInputDto;
import com.kj.products.productQnA.dto.ProductAdminQnAReturnDto;
import com.kj.products.productQnA.dto.ProductQnAInputDto;
import com.kj.products.productQnA.dto.ProductQnAInsertReturnDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductQnAController {
    private final ProductQnAService productQnAService;

    @PostMapping("/product/insertqna")
    @ResponseBody
    public ProductQnAInsertReturnDto insertProductQnA(
            @ModelAttribute ProductQnAInputDto productQnAInputDto
            ){
        log.info("큐앤에이 디티오 ===>> {}", productQnAInputDto);
        return productQnAService.insertProductQnAUser(productQnAInputDto);
    }

    @PostMapping("/admin/product/insertqna")
    @ResponseBody
    public ProductAdminQnAReturnDto insertProductAdminQnA(
            @ModelAttribute ProductAdminQnAInputDto productAdminQnAInputDto
            ){
        log.info("프로덕트어드민  =>>>> {}", productAdminQnAInputDto);
        return productQnAService.insertProductAdminQnA(productAdminQnAInputDto);

    }
}
