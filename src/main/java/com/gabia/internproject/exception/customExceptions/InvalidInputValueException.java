package com.gabia.internproject.exception.customExceptions;

import com.gabia.internproject.exception.ErrorCode;

public class InvalidInputValueException extends BusinessException {

    public InvalidInputValueException(String reason) {
        super(ErrorCode.INVALID_INPUT_VALUE,reason);

    }


}
