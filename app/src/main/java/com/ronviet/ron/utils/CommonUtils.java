package com.ronviet.ron.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LENOVO on 1/8/2017.
 */

public class CommonUtils {
    public static String convertDateFormat(Date date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        return formatter.format(date);
    }
}
