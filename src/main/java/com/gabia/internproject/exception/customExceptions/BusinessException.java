package com.gabia.internproject.exception.customExceptions;

import com.gabia.internproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException  extends RuntimeException {

    private ErrorCode errorCode;
    private String reason;

    public BusinessException(ErrorCode errorCode,String reason) {
        this.errorCode=errorCode;
        this.reason=reason;
    }


}
