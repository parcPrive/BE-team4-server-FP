package com.kj.products.productQnA;

import com.kj.products.productQnA.dto.ProductAdminQnAInputDto;
import com.kj.products.productQnA.dto.ProductAdminQnAReturnDto;
import com.kj.products.productQnA.dto.ProductQnAInputDto;
import com.kj.products.productQnA.dto.ProductQnAInsertReturnDto;
import com.kj.products.productQnA.entity.ProductQnA;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductQnAController {
    private final ProductQnAService productQnAService;

    @PostMapping("/product/insertqna")
    public String insertProductQnA(
            @ModelAttribute ProductQnAInputDto productQnAInputDto
            ){
        log.info("큐앤에이 디티오 ===>> {}", productQnAInputDto);
        ProductQnAInsertReturnDto insertProductQnADto = productQnAService.insertProductQnAUser(productQnAInputDto);
        return "redirect:/product/view/" + productQnAInputDto.getProductId() + "?reviewPage=1&qnaPage=1";
    }

    @PostMapping("/admin/product/insertqna")
    @ResponseBody
    public ProductAdminQnAReturnDto insertProductAdminQnA(
            @ModelAttribute ProductAdminQnAInputDto productAdminQnAInputDto
            ){
        log.info("프로덕트어드민  =>>>> {}", productAdminQnAInputDto);
        return productQnAService.insertProductAdminQnA(productAdminQnAInputDto);
    }
    @GetMapping("/product/aaa")
    @ResponseBody
    public PageImpl<ProductQnA> Aaa(){
        PageImpl<ProductQnA>  aa = productQnAService.pageNationProductQnA(1);
//        aa.
      return aa;
    }
}
