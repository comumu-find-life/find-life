package com.api.exception;


import com.common.utils.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @ControllerAdvice : Spring MVC 에서 전역 예외처리, 전역 데이터 바인딩, 전역 모델 객체 정의를 도와주는 애노테이션이다.
 * GlobalExceptionHandler 클래스에서는 전역에서 발생하는 예외를 처리하는 핸들러로 사용한다.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SuccessResponse> handleGenericException(Exception ex) {
        SuccessResponse response = new SuccessResponse(false,  ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
