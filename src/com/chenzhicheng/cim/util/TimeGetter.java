package com.chenzhicheng.cim.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeGetter {
    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
