package com.gabia.internproject.exception;

public enum ErrorCode {
    // Common


    INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", "Invalid Input Value"),
    UNPROCESSABLE_ENTITY(422, "C003", "Invalid request"),
    MAPPER_NOT_FOUND(500, "C004", "There is no mapper to convert"),
    HANDLE_ACCESS_DENIED(403, "C005", "Access is Denied"),
    INVALID_JWT_TOKEN(403, "C006", "Invalid jwt token"),
    INTERNAL_SERVER_ERROR_FILTER(500, "C007", "SeverError in filter"),
    ACCESS_TOKEN_NOT_ACQUIRED(500, "C008", "failed to acquired accessToken"),
    API_REQUEST_EXECUTION_FAIL(500, "C009", "failed to execute api request"),




    // Member
    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),


    //login
    EMAIL_NOT_REGISTERED(500,"L001","Email is not registered"),
    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),

    //floor
    FLOOR_NOT_FOUND(400, "F001", "Floor can not be found"),

    //seat
    SEAT_NOT_FOUND(400, "S001", "Seat can not be found"),

    //employee
    EMPLOYEE_NOT_FOUND(400, "E001", "Employee can not be found"),

    //placetyoe
    PlACE_TYPE_NOT_FOUND(400, "PT001", "placeType can not be found"),

     //place
     PlACE_INOT_FOUND(400, "T001", "place can not be found"),

    //socialLoginEmail
    SOCIAL_lOGIN_EMAIL_NOT_FOUND(400, "SL001", "socialLoginEmail can not be found"),

            ;


    private final String code;
    private final String message;
    private int status;
    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
    public int getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public String getCode(){ return code; }

}
