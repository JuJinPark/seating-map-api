package com.gabia.internproject.exception.customExceptions;

import com.gabia.internproject.exception.ErrorCode;

public class JwtTokenException extends BusinessException {

    public JwtTokenException(String reason) {
        super(ErrorCode.INVALID_JWT_TOKEN,reason);

    }


}
