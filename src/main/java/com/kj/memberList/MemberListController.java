package com.kj.memberList;

import com.kj.log.LogService;
import com.kj.log.entity.Log;
import com.kj.member.entity.Member;
import com.kj.utils.BigFaqCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/memberList")
@RequiredArgsConstructor
@Slf4j
public class MemberListController {
    private final MemberListService memberListService;
    private final LogService logService;
    private int paginationSize=5;

    @GetMapping("/list")
    public String listMember(Model model, @RequestParam(value = "page", required = true, defaultValue = "0") int page) {
        Page<Member> pagination = memberListService.findAllPageMember(page);
        List<Log> logList = logService.findByLog();
        List<Member> registerDateList = memberListService.findByRegisterDate();
        List<Member> memberList = pagination.getContent();
        int start = (int)(Math.floor((double) pagination.getNumber() / paginationSize)*paginationSize);
        int end =  start + paginationSize;
        String xx = BigFaqCategory.FAQ001.getValue();

        model.addAttribute("start",start);
        model.addAttribute("end",end);
        model.addAttribute("pagination",pagination);
        model.addAttribute("memberList",memberList);
        model.addAttribute("logList",logList);
        model.addAttribute("registerDateList",registerDateList);
        model.addAttribute("xx",xx);
        return "memberList/list";
    }
    @GetMapping("/search")
    public String searchList(Model model,
                             @RequestParam String category,
                             @RequestParam String keyword,
                             String black,
                             @RequestParam(value = "page", required = true,defaultValue = "0")int page){
        black=".";
        Page<Member> pagination = memberListService.findAllSearchPageMember(category,keyword,page,black);
        List<Member> registerDateList = memberListService.findByRegisterDate();
        List<Log> logList = logService.findByLog();
        List<Member> memberList = pagination.getContent();
        int start = (int)(Math.floor((double) pagination.getNumber() / paginationSize)*paginationSize);
        int end =  start + paginationSize;
        log.info("start==={},end==={}",start,end);
        model.addAttribute("start",start);
        model.addAttribute("end",end);
        model.addAttribute("pagination",pagination);
        model.addAttribute("memberList",memberList);
        model.addAttribute("registerDateList",registerDateList);
        model.addAttribute("logList",logList);
        return "memberList/list";
    }
    @GetMapping("/searchBlack")
    public String searchBlackList(Model model,
                             @RequestParam String category,
                             @RequestParam String keyword, @RequestParam String black,
                             @RequestParam(value = "page", required = true,defaultValue = "0")int page){
        Page<Member> pagination = memberListService.findAllSearchPageMember(category,keyword,page,black);
        List<Member> memberList = pagination.getContent();
        int start = (int)(Math.floor((double) pagination.getNumber() / paginationSize)*paginationSize);
        int end =  start + paginationSize;

        log.info("start==={},end==={}",start,end);
        model.addAttribute("start",start);
        model.addAttribute("end",end);
        model.addAttribute("pagination",pagination);
        model.addAttribute("memberList",memberList);
        return "memberList/blackList";
    }
    @GetMapping("/level")
    @ResponseBody
    public Map<String, Object> updateLevel(Model model, String level, Long id){
        log.info("==={}",id);
        memberListService.updateLevel(level,id);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("isLevel",true);
        return resultMap;
    }
    @GetMapping("/levelAll")
    @ResponseBody
    public Map<String, Object> updateLevelAll(Model model, String level, @RequestParam(value = "id") List<Long> id){
        log.info("==={}",id);
        memberListService.updateLevelAll(level,id);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("isLevel",true);
        return resultMap;
    }

    @GetMapping("/blackList")
    public String blackListMember(Model model, @RequestParam(value = "page", required = true, defaultValue = "0") int page) {
        Page<Member> pagination = memberListService.findAllBlackPageMember(page);
        log.info("pageBoardList.getTotalPages()==={}",pagination.getTotalPages());
        log.info(pagination.toString());
        List<Member> memberList = pagination.getContent();
        int start = (int)(Math.floor((double) pagination.getNumber() / paginationSize)*paginationSize);
        int end =  start + paginationSize;

        log.info("start==={},end==={}",start,end);
        model.addAttribute("start",start);
        model.addAttribute("end",end);
        model.addAttribute("pagination",pagination);
        model.addAttribute("memberList",memberList);
        return "memberList/blackList";
    }
}
