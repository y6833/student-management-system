package com.yangy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelp {

    public static String convertToDate(String inputDateTime) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = inputFormat.parse(inputDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(date);
    }

    public static String dataToString(String dateString) {


        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH);
        Date dd = null; //将字符串改为date的格式
        try {
            dd = sdf.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String resDate= new SimpleDateFormat("yyyy-MM-dd").format(dd);
//        System.out.println(resDate);
        return resDate;

    }

    public static String dataTimetoData(String inputTime){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = inputFormat.parse(inputTime);
            String outputTime = outputFormat.format(date);
//            System.out.println(outputTime);
            return outputTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String dataTime(String imputTime){
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        String output = null;
        try {
            Date date = inputFormat.parse(imputTime);
            output = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;

    }
}
