package com.can.controller.response;

import static com.can.core.constants.Constants.*;

public class BaseResponse {

    private String message;

    public BaseResponse() {
        this.message = RESPONSE_MESSAGE_SUCCESS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
