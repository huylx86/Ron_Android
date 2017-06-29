package com.ronviet.ron.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        return vnFormat.format(tongTien);//.substring(1);
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
//        try {
//            // Create MD5 Hash
//            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
//            digest.update(s.getBytes());
//            byte messageDigest[] = digest.digest();
//
//            // Create Hex String
//            StringBuffer hexString = new StringBuffer();
//            for (int i=0; i<messageDigest.length; i++)
//                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
//            return hexString.toString();
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
    }
}
