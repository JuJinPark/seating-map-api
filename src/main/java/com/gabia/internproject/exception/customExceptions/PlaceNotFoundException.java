package com.gabia.internproject.exception.customExceptions;

import com.gabia.internproject.exception.ErrorCode;

public class PlaceNotFoundException extends BusinessException {

    public PlaceNotFoundException(String reason) {
        super(ErrorCode.PlACE_INOT_FOUND,reason);

    }


}
