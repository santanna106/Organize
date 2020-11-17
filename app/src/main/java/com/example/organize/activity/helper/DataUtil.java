package com.example.organize.activity.helper;

import java.text.SimpleDateFormat;

public class DataUtil {
    public static String dataAtual(){
        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        String dataString = simpleDateFormat.format(date);
        return dataString;
    }
}
