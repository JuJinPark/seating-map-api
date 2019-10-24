package com.gabia.internproject.exception.customExceptions;

import com.gabia.internproject.exception.ErrorCode;

public class SocialEmailNotFoundException extends BusinessException {

    public SocialEmailNotFoundException(String reason) {
        super(ErrorCode.SOCIAL_lOGIN_EMAIL_NOT_FOUND,reason);

    }


}
