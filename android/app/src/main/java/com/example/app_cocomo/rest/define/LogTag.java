package com.example.app_cocomo.rest.define;

import lombok.Data;

@Data
public class LogTag {

    private static String MainTag = "　[#COCOMO : ";
    public static String JoinTag = MainTag + " JOIN] ";
    public static String LoginTag = MainTag + " LOGIN] ";
    public static String HomeTag = MainTag + " HOME] ";
    public static String QRTag = MainTag + " QR-SCAN] ";

    public static String SUCCESS = " >>> Success";
    public static String FAILED = " >>> Failed >>> ";
    public static String INVAILD = " >>> Invalid >>> ";

    public static String WHAT = " >>> WHAT >>> ";

}
