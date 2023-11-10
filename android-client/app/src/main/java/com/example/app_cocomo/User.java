package com.example.app_cocomo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    // private Integer id;
    private String userId;
    private String passwd;
    private String userName;
    private String email;
    private String phone;
    private Date joinDate;

}
