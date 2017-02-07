package com.ronviet.ron.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by LENOVO on 1/8/2017.
 */

public class CommonUtils {
    public static String convertDateFormat(Date date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        return formatter.format(date);
    }

    public static String convertDateStandardFormat(Date date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }

    public static int getWidthScreen(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static String formatCurrency(int tongTien)
    {
        Locale vn = new Locale("vn", "VN");
        NumberFormat vnFormat = NumberFormat.getCurrencyInstance(vn);
        return vnFormat.format(tongTien).substring(1);
    }
}
