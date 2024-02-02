package com.kj.codyBoards.codyBoard;

import com.kj.codyBoards.codyBoard.dto.CodyBoardInputDto;
import com.kj.codyBoards.codyBoard.dto.CodyBoardListReturnDto;
import com.kj.codyBoards.codyBoard.dto.CodyBoardSearchCondition;
import com.kj.codyBoards.codyBoard.entiry.CodyBoard;
import com.kj.codyBoards.codyBoardComments.entity.CodyBoardComment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.raw.Mod;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/codyboard")
@RequiredArgsConstructor
@Slf4j
public class CodyBoardController {
    private final CodyBoardService codyBoardService;

    @GetMapping("/insert")
    public String insertCodyBoard(
            Model model
    ){
        String codyBoardBucket = String.valueOf(UUID.randomUUID());
        log.info("코디버켓 ===>> {}", codyBoardBucket);
        model.addAttribute("codyBoardBucket", codyBoardBucket);
        return "/codyboard/insert";
    }

    @PostMapping("/insert")
    @ResponseBody
    public String insertCodyBoardProcess(
            @ModelAttribute CodyBoardInputDto codyBoardInputDto
            ) throws IOException {
        log.info("코디보드입력값 ===>> {}", codyBoardInputDto);
        codyBoardService.insert(codyBoardInputDto);
        return "aa";
    }

    @GetMapping("/list/{page}")
    public String codyBoardList(
            @PathVariable int page,
            @ModelAttribute CodyBoardSearchCondition codyBoardSearchCondition,
            Model model
    ){
        log.info("asdasd ===>> {}", codyBoardSearchCondition);
        PageImpl<CodyBoardListReturnDto> codyBoardList =  codyBoardService.findListCodyBoard(page, codyBoardSearchCondition);

        model.addAttribute("codyBoardList", codyBoardList);
        return "/codyboard/list";
    }


    @GetMapping("/view")
    public String codyBoardView(
            @RequestParam Long codyBoardId,
            Model model
    ){
        log.info("asdasdad ====?? {}", codyBoardId);
        CodyBoard codyBoard = codyBoardService.findByCodyBoard(codyBoardId);
        model.addAttribute("codyBoard", codyBoard);
        return "/codyboard/view";
    }

//    @GetMapping("/redisTest1")
//    @ResponseBody
//    public String aaa1(){
//        log.info("여기");
//        codyBoardService.redisSetTest();
//        return "헤헤";
//    }
//    @GetMapping("/redisTest2")
//    @ResponseBody
//    public String aaa2(){
//        codyBoardService.redisSetListTest();
//        return "헤헤";
//    }
//    @GetMapping("/redisTest3")
//    @ResponseBody
//    public String aaa3(){
//        codyBoardService.redisGetTest();
//        return "헤헤";
//    }
//    @GetMapping("/redisTest4")
//    @ResponseBody
//    public String aaa4(){
//        codyBoardService.redisGetListTest();
//        return "헤헤";
//    }
}
