package com.kj.faq;

import com.kj.faq.dto.FaqBoardDto;
import com.kj.faq.dto.FaqCategoryDto;
import com.kj.faq.entity.FaqBoard;
import com.kj.faq.entity.FaqCategory;
import com.kj.faq.repository.FaqCategoryRepository;
import com.kj.faq.service.FaqBoardService;
import com.kj.faq.service.FaqCategoryService;
import com.kj.log.entity.Log;
import com.kj.member.dto.CustomUserDetails;
import com.kj.member.entity.Member;
import com.kj.utils.BigFaqCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/cs")
@RequiredArgsConstructor
@Slf4j
public class FaqController {
    private final FaqCategoryService faqCategoryService;
    private final FaqBoardService faqBoardService;
    private int paginationSize=5;
    @Value("${file.path}")
    private String uploadFolder;

    @GetMapping("/insertFaq")
    public String insertFaq(Model model){
        List<FaqCategory> faqCategoryList = faqCategoryService.findByAllCategory();
        model.addAttribute("faqCategoryList",faqCategoryList);
        return "/customService/insertFaq";
    }

    @Transactional
    @PostMapping("/insertFaq")
    public String insertFaqProcess(@ModelAttribute FaqBoardDto faqBoardDto,@RequestParam Long faqCategoryId,
                                   @AuthenticationPrincipal CustomUserDetails customUserDetails){
        log.info("=={}",faqCategoryId);
        FaqCategory faqCategory = faqCategoryService.findByCategoryId(faqCategoryId);
        faqBoardService.insertFaqBoard(faqBoardDto,customUserDetails,faqCategory);
        return "redirect:/cs/faq";
    }

    @GetMapping("/insertFaqCategory")
    public String insertFaqCategory(Model model,@RequestParam(value = "error", required = false) String error,
                                    @RequestParam(value = "exception", required = false) String exception){
        List<FaqCategory> faqCategoryList = faqCategoryService.findByAllCategory();
        model.addAttribute("faqCategoryList",faqCategoryList);
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/customService/insertFaqCategory";
    }
    @PostMapping("/insertFaqCategory")
    public String insertFaqCategoryProcess(Model model, FaqCategoryDto faqCategoryDto){
        faqCategoryService.insertCategory(faqCategoryDto);
        return "redirect:/cs/insertFaqCategory";
    }

    @GetMapping("/faq")
    public String listFaq(Model model, @RequestParam(value = "page", required = true, defaultValue = "0") int page) {
        Page<FaqBoard> pagination = faqBoardService.findAllPageFaq(page);
        List<FaqBoard> faqBoardList = pagination.getContent();
        int start = (int)(Math.floor((double) pagination.getNumber() / paginationSize)*paginationSize);
        int end =  start + paginationSize;

        model.addAttribute("start",start);
        model.addAttribute("end",end);
        model.addAttribute("pagination",pagination);
        model.addAttribute("faqBoardList",faqBoardList);
        return "/customService/faqList";
    }
    @GetMapping("/faq/{code}")
    public String listFaqCode( @PathVariable String code, Model model, @RequestParam(value = "page", required = true, defaultValue = "0") int page,
                               @RequestParam(defaultValue = "FAQ001") String faqCategory) {
        faqCategory = "FAQ"+code;
        Page<FaqBoard> pagination = faqBoardService.findByPageFaq(page,faqCategory);
        List<FaqBoard> faqBoardList = pagination.getContent();
        int start = (int)(Math.floor((double) pagination.getNumber() / paginationSize)*paginationSize);
        int end =  start + paginationSize;

        model.addAttribute("start",start);
        model.addAttribute("end",end);
        model.addAttribute("pagination",pagination);
        model.addAttribute("faqBoardList",faqBoardList);
        return "/customService/faqList";
    }
    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        FaqBoard faqBoardInfo = faqBoardService.findById(id);
        model.addAttribute("faqBoardInfo",faqBoardInfo);
        return "/customService/viewFaq";
    }

    @GetMapping("/updateFaq/{id}")
    public String update(@PathVariable Long id, Model model) {
        List<FaqCategory> faqCategoryList = faqCategoryService.findByAllCategory();
        FaqBoard faqBoardInfo = faqBoardService.findById(id);
        model.addAttribute("faqCategoryList",faqCategoryList);
        model.addAttribute("faqBoardInfo",faqBoardInfo);
        return "/customService/updateFaq";
    }
    @Transactional
    @PostMapping("/updateFaq")
    public String updateProcess(@RequestParam Long id,@ModelAttribute FaqBoardDto faqBoardDto,@RequestParam Long faqCategoryId,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails){
        FaqCategory faqCategory = faqCategoryService.findByCategoryId(faqCategoryId);
        faqBoardService.updateFaqBoard(faqBoardDto,faqCategory);

        return "redirect:/cs/view/"+id;
    }
    @GetMapping("/deleteFaq/{id}")
    public String delete(@PathVariable Long id) {
        boolean isDelete = faqBoardService.deleteById(id);
        if(isDelete){
            return "redirect:/cs/faq";
        }else {
        return "redirect:/";

        }
    }
    @PostMapping("/deleteFaqCategory")
    @ResponseBody
    public Map<String,Object> deleteFaqCategory(@RequestParam(value = "id",required = false) Long id) {
        boolean result = faqCategoryService.deleteCategory(id);

        Map<String,Object> resultMap = new HashMap<>();
        if (result){
            resultMap.put("isDelete",true);
        }else {
            resultMap.put("isDelete",false) ;
        }
        return resultMap;
    }
    @PostMapping("/upload")
    @ResponseBody
    public Map<String,Object> upload(@RequestParam MultipartFile upload) {
        log.info("upload===={}",upload);
        log.info("originalFileName==={}",upload.getOriginalFilename());

        String originalFile = upload.getOriginalFilename(); // 이게 진짜 파일 이름...
        String renamedFile = null;
        String folder =  null;
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        folder = simpleDateFormat.format(now);
        File dir = new File(uploadFolder+File.separator+folder);
        if(!dir.exists()) dir.mkdirs();

        // file이름 분리하고 확장자 분리
        String fileName = originalFile.substring(0,originalFile.lastIndexOf("."));
        String ext = originalFile.substring(originalFile.lastIndexOf("."));
        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String strNow = simpleDateFormat.format(now);
        log.info("strNow==={}",strNow);
        renamedFile = fileName+"_"+strNow+ext;
        Path imgFilePath = Paths.get(dir+File.separator+renamedFile);

        try {
            Files.write(imgFilePath,upload.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> resultMap =  new HashMap<>();
        resultMap.put("uploaded",true);
        resultMap.put("url","/upload/"+folder+"/"+renamedFile);
        return resultMap;
    }

}
