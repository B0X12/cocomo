package com.example.app_cocomo.rest.define;

import lombok.Data;

@Data
public class AuthUser {

    private Integer id;
    private String userId;
    private String otpKey;
    private String otpCode;
    private int authResult;

}
