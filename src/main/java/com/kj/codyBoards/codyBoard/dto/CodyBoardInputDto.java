package com.kj.codyBoards.codyBoard.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CodyBoardInputDto {
    private String codyBoardTitle;
    private String content;
    private List<MultipartFile> file;
    private String userName;
    private String codyBoardBucket;
}
