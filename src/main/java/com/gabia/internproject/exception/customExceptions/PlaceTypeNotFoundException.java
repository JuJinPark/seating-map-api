package com.gabia.internproject.exception.customExceptions;

import com.gabia.internproject.exception.ErrorCode;

public class PlaceTypeNotFoundException extends BusinessException {

    public PlaceTypeNotFoundException(String reason) {
        super(ErrorCode.PlACE_TYPE_NOT_FOUND,reason);

    }


}
