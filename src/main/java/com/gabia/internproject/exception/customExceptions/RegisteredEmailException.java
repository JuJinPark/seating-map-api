package com.gabia.internproject.exception.customExceptions;

import com.gabia.internproject.exception.ErrorCode;

public class RegisteredEmailException extends BusinessException {

    public RegisteredEmailException(String reason) {
        super(ErrorCode.EMAIL_DUPLICATION,reason);

    }


}
