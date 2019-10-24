package com.gabia.internproject.exception.customExceptions;

import com.gabia.internproject.exception.ErrorCode;

public class AccessTokenNotAcquiredException extends CheckedBusinessException {

    public AccessTokenNotAcquiredException(String reason) {
        super(ErrorCode.ACCESS_TOKEN_NOT_ACQUIRED,reason);

    }


}
