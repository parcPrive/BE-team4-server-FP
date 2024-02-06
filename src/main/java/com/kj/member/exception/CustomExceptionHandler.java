package com.kj.member.exception;

import com.kj.code.ErrorCode;
import com.kj.member.dto.ErrorDto;
import com.kj.member.dto.JoinDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    // 여기에 에러 다 모아서 쓰기....
    /*@ExceptionHandler(BoardException.class)
    public String runtimeHandle(BoardException e) {
        ErrorDto response = ErrorDto.builder()
                .errorCode(e.getErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
        return "/errors/error";
    }*/

    /*@ExceptionHandler(SQLException.class)
    @ResponseBody
    public ErrorDto duplicateMember(SQLException e) {
        ErrorDto response = ErrorDto.builder()
                .errorCode(ErrorCode.DUPLICATE_MEMBER)
                .errorMessage(ErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                .build();
        return response;
    }*/

    /*
    @ExceptionHandler(MemberException.class)
    @ResponseBody
    public ErrorDto memberHandler(MemberException e) {
        ErrorDto response = ErrorDto.builder()
                .errorCode(e.getErrorCode())
                .errorMessage(e.getMessage())
                .build();
        return response;
    }
    */
    /*@ExceptionHandler(MemberException.class)
    public String memberHandler(MemberException e, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ErrorDto response = ErrorDto.builder()
                .errorCode(e.getErrorCode())
                .errorMessage(e.getMessage())
                .build();
        model.addAttribute("error",response);
        model.addAttribute("joinDto",new JoinDto());
        log.info("==============={}",request.getRequestURI());
        //return "/errors/error";
        return ""+request.getRequestURI();
    }*/

    @ExceptionHandler(IllegalArgumentException.class)
    public String  notPassword(IllegalArgumentException e, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ErrorDto response = ErrorDto.builder()
                .errorCode(ErrorCode.NOT_FAILPASSWORD)
                .errorMessage(ErrorCode.NOT_FAILPASSWORD.getMessage())
                .build();
        Long id = Long.parseLong(e.getMessage());
        model.addAttribute("error",response);
        model.addAttribute("exception",response.getErrorMessage());
        return "redirect:/cs/qna/check/"+id;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public String  notFound(UsernameNotFoundException e, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ErrorDto response = ErrorDto.builder()
                .errorCode(ErrorCode.NOT_FOUND)
                .errorMessage(ErrorCode.NOT_FOUND.getMessage())
                .build();
        model.addAttribute("error",response);
        model.addAttribute("exception",response.getErrorMessage());
        return "/member/login";
    }
    @ExceptionHandler(FaqCategoryException.class)
    public String  duplication(FaqCategoryException e, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ErrorDto response = ErrorDto.builder()
                .errorCode(ErrorCode.NOT_FOUND)
                .errorMessage(ErrorCode.NOT_FOUND.getMessage())
                .build();
        model.addAttribute("error",response);
        model.addAttribute("exception",response.getErrorMessage());
        return "redirect:/cs/insertFaqCategory";
    }
    /*@ExceptionHandler(RuntimeException.class)
    public String anonymousException(RuntimeException e,Model model) {
        ErrorDto response = ErrorDto.builder()
                .errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
                .errorMessage(ErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                .build();
        model.addAttribute("error",response);
        return "/error/error";
    }*/
}
