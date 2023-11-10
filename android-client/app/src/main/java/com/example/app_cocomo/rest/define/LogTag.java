package com.example.app_cocomo.rest.define;

import lombok.Data;

@Data
public class LogTag {

    private static String MainTag = "　[#COCOMO : ";
    public static String JoinTag = MainTag + " JOIN] ";
    public static String LoginTag = MainTag + " LOGIN] ";
    public static String HomeTag = MainTag + " HOME] ";


    public static String OtpTag = MainTag + " OTP] ";
    public static String QrTag = MainTag + " QRSCAN] ";
    public static String FingerTag = MainTag + " FINGER] ";



    public static String SUCCESS = " >>> Success";
    public static String FAILED = " >>> Failed >>> ";
    public static String INVAILD = " >>> Invalid >>> ";

    public static String WHAT = " >>> WHAT >>> ";

}
