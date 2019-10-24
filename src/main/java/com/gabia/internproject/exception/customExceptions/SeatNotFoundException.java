package com.gabia.internproject.exception.customExceptions;

import com.gabia.internproject.exception.ErrorCode;

public class SeatNotFoundException extends BusinessException {

    public SeatNotFoundException(String reason) {
        super(ErrorCode.SEAT_NOT_FOUND,reason);

    }


}
