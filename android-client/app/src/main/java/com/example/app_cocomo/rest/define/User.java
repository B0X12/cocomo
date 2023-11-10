package com.example.app_cocomo.rest.define;

import java.util.Date;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String userId;
    private String passwd; // 외부에 노출 안되는 데이터
    private String userName;
    private String email;
    private String phone;
    private Date joinDate; // 과거 데이터로만

}
