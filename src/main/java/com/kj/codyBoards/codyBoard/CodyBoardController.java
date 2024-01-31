package com.kj.codyBoards.codyBoard;

import com.kj.codyBoards.codyBoard.dto.CodyBoardInputDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping("/list")
    public String codyBoardList(){

        return "/codyboard/list";
    }
}
