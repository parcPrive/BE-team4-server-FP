package com.kj.products.productReview;

import com.kj.products.productReview.dto.ProductinsertReviewDto;
import com.kj.products.productReview.entity.ProductReview;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductReviewController {
    private final ProductReviewService productReviewService;


    @PostMapping("/insertreview")
    public String productReview(
            @ModelAttribute ProductinsertReviewDto productinsertReviewDto
    ) throws IOException {
        log.info("====================================여길안들어오나요??====================================");
        log.info("리뷰정보들 ===<>>> {}", productinsertReviewDto);
        ProductReview insertProductReview =  productReviewService.InsertProductReview(productinsertReviewDto);
        return "redirect:/product/view/" + insertProductReview.getProduct().getId()+ "?reviewPage=1&qnaPage=1";
    }


}
