package com.example.app_cocomo.rest.define;

import lombok.Data;

@Data
public class ErrorResponse {

    private String timestamp;
    private String message;
    private String details;

    // toString()을 Override 해주지 않으면 객체 주소값을 출력함
    @Override
    public String toString() {
        return "ErrorResponse{" +
                "timestamp=" + timestamp +
                ", message=" + message +
                ", details='" + details +
                '}';
    }

}
