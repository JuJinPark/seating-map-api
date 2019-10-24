package com.gabia.internproject.exception.customExceptions;

import com.gabia.internproject.exception.ErrorCode;

public class EmployeeNotFoundException extends BusinessException {

    public EmployeeNotFoundException(String reason) {
        super(ErrorCode.EMPLOYEE_NOT_FOUND,reason);

    }


}
