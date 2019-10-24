package com.gabia.internproject.exception.customExceptions;

import com.gabia.internproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CheckedBusinessException extends Exception  {
    private ErrorCode errorCode;
    private String reason;

    public CheckedBusinessException(ErrorCode errorCode,String reason) {
        this.errorCode=errorCode;
        this.reason=reason;
    }


}
