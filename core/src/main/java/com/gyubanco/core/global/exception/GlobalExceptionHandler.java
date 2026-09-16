package com.gyubanco.core.global.exception;

import com.gyubanco.core.global.api.Api;
import com.gyubanco.core.global.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<Api<Object>> handleApiException(ApiException e) {
    log.error("ApiException 발생: [code: {}] {}",
        e.getErrorCodeInterface().getHttpStatusCode(), e.getMessage(), e);

    return ResponseEntity
        .status(e.getErrorCodeInterface().getHttpStatusCode())
        .body(Api.ERROR(e.getErrorCodeInterface(), e.getErrorMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Api<Object>> handleException(Exception e) {
    log.error("알 수 없는 예외 발생", e);
    return ResponseEntity
        .status(500)
        .body(Api.ERROR(ErrorCode.SERVER_ERROR));
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<Api<Object>> handleNoResourceFoundException(NoResourceFoundException e) {
    log.warn("404 리소스 없음 - Path: {}", e.getResourcePath());
    return ResponseEntity
        .status(404)
        .body(Api.ERROR(ErrorCode.NOT_FOUND));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Api<Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    log.warn("요청 바디 파싱 실패 - Message: {}", e.getMessage());
    return ResponseEntity
        .badRequest()
        .body(Api.ERROR(ErrorCode.BAD_REQUEST, "요청 바디의 형식이 올바르지 않습니다."));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Api<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

    ObjectError allError = e.getBindingResult().getAllErrors().get(0);

    String errorMessage = allError.getDefaultMessage();

    log.warn("유효성 검사 실패 - 메시지: {}",
        errorMessage, e);

    return ResponseEntity
        .badRequest()
        .body(Api.ERROR(ErrorCode.INVALID_INPUT, errorMessage));
  }
}
