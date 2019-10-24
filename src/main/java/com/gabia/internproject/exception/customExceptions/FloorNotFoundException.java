package com.gabia.internproject.exception.customExceptions;

import com.gabia.internproject.exception.ErrorCode;

public class FloorNotFoundException extends BusinessException {

    public FloorNotFoundException(String reason) {
        super(ErrorCode.FLOOR_NOT_FOUND,reason);

    }


}
