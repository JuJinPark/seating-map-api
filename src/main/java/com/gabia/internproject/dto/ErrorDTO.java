package com.gabia.internproject.dto;

import com.gabia.internproject.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorDTO {


        private String message;
        private int status;
        private  String reason;
        private String code;

  public static ErrorDTO of(ErrorCode errorCode, String reason){
      ErrorDTO errorDto =new ErrorDTO();
      errorDto.code=errorCode.getCode();
      errorDto.status=errorCode.getStatus();
      errorDto.message=errorCode.getMessage();
      errorDto.reason=reason;
      return errorDto;


  }
    public static ErrorDTO of(ErrorCode errorCode){
        return of(errorCode,"");

    }


}
