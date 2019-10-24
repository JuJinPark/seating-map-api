package com.gabia.internproject.exception.customExceptions;

import com.gabia.internproject.exception.ErrorCode;

public class ApiExecutionException extends CheckedBusinessException {

    public ApiExecutionException(String reason) {
        super(ErrorCode.API_REQUEST_EXECUTION_FAIL,reason);

    }


}
