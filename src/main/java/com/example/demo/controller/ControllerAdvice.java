package com.example.demo.controller; // 패키지 선언 필요

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

// 파일 이름과 동일한 최상위 public 클래스로 선언합니다.
@ControllerAdvice
public class GlobalControllerAdvice { // 클래스 이름은 파일 이름에 맞게 수정해주세요 (예: GlobalControllerAdvice)

    // MethodArgumentTypeMismatchException 핸들링 (예: Long ID에 문자열 입력)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleTypeMismatchException(MethodArgumentTypeMismatchException ex, Model model) {
        
        String errorMessage = "잘못된 요청입니다! 요청하신 데이터 형식이 올바르지 않거나 찾을 수 없습니다.";
        
        // 상세 에러 정보(선택 사항)
        String paramName = ex.getName();
        Object value = ex.getValue();
        errorMessage += " [파라미터: " + paramName + ", 입력값: " + value + "]";
        
        model.addAttribute("errorMessage", errorMessage);
        
        // 렌더링할 에러 페이지 지정
        return "error/error_custom"; // templates/error/error_custom.html 렌더링
    }
    
    // 추가적인 예외 처리를 여기에 구현합니다.

}