package com.jinghan.memo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }
}
