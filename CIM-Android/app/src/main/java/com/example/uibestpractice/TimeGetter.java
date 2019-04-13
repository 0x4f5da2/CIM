package com.example.uibestpractice;

/**
 * Created by chen1 on 2018/2/28.
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeGetter {
    public static String getTime() {
        Date date = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}