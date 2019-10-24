package com.gabia.internproject.exception;

import com.gabia.internproject.dto.ErrorDTO;
import com.gabia.internproject.exception.customExceptions.BusinessException;
import com.gabia.internproject.exception.customExceptions.CheckedBusinessException;
import com.gabia.internproject.exception.customExceptions.MapperNotFoundException;
import org.hibernate.exception.DataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private ObjectError error;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        StringBuilder sb=new StringBuilder();
        for(FieldError error:e.getBindingResult().getFieldErrors()){
            sb.append("(");
            sb.append(error.getField());
            sb.append(")") ;
            sb.append(error.getDefaultMessage());
            sb.append(System.getProperty("line.separator"));
        }
        ErrorDTO response = ErrorDTO.of(ErrorCode.INVALID_INPUT_VALUE,sb.toString());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder sb=new StringBuilder();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            sb.append("(");
            sb.append(violation.getPropertyPath().toString());
            sb.append(")") ;
            sb.append(violation.getMessage());
            sb.append(System.getProperty("line.separator"));
        }

        ErrorDTO response = ErrorDTO.of(ErrorCode.INVALID_INPUT_VALUE,sb.toString());
        return new ResponseEntity<>(response ,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value= HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleUnprosseasableMsgException(HttpMessageNotReadableException e) {
        ErrorDTO response = ErrorDTO.of(ErrorCode.UNPROCESSABLE_ENTITY,"요청하신 데이터를 처리 할수 없습니다.");
        return new ResponseEntity<>(response,HttpStatus.UNPROCESSABLE_ENTITY);


    }

    @ExceptionHandler(value= MapperNotFoundException.class)
    public ResponseEntity<ErrorDTO> MapperNotFoundException(MapperNotFoundException e) {
        ErrorDTO response = ErrorDTO.of(ErrorCode.MAPPER_NOT_FOUND,"요청한신 형태로 변환할 알맞는 매퍼를 찾을 수 없습니다.");
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);


    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorDTO> handleBusinessException(final BusinessException e) {

        final ErrorCode errorCode = e.getErrorCode();
        final ErrorDTO response = ErrorDTO.of(errorCode,e.getReason());
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(CheckedBusinessException.class)
    protected ResponseEntity<ErrorDTO> handleCheckedBusinessException(final CheckedBusinessException e) {

        final ErrorCode errorCode = e.getErrorCode();
        final ErrorDTO response = ErrorDTO.of(errorCode,e.getReason());
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(DataException.class)
    protected ResponseEntity<ErrorDTO> handleJPAException(final DataException e) {

        final ErrorDTO response = ErrorDTO.of(ErrorCode.INVALID_INPUT_VALUE,e.getCause().getMessage());
        return new ResponseEntity<>(response,HttpStatus.valueOf(ErrorCode.INVALID_INPUT_VALUE.getStatus()));
    }

}
