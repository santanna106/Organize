package com.example.organize.activity.helper;

import java.text.SimpleDateFormat;

public class DataUtil {
    public static String dataAtual(){
        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        String dataString = simpleDateFormat.format(date);
        return dataString;
    }

    public static String dataMesAno(String data){
       String retornoData[] =  data.split("/");
       String dia = retornoData[0];
       String mes = retornoData[1];
       String ano = retornoData[2];

       return (mes + ano);

    }
}
