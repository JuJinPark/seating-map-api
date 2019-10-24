package com.gabia.internproject.exception.customExceptions;

import com.gabia.internproject.exception.ErrorCode;

public class NotRegisteredEmail extends BusinessException {

    public NotRegisteredEmail(String reason) {
        super(ErrorCode.EMAIL_NOT_REGISTERED,reason);

    }


}
