package com.ronviet.ron.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ronviet.ron.models.PendingOrder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 9/28/2016.
 */

public class SharedPreferenceUtils {

    private static final String PENDING_ORDER = "Pending_Order";
    private static final String WEBSITE = "WebSite";
    private static final String ID_TT = "ID_TRUNG_TAM";
    private static final String ID_MAY = "ID_MAY";
    private static final String NGON_NGU = "NGON_NGU";
    private static final String NHAN_VIEN_ID = "NHAN_VIEN_ID";
    private static final String TEN_NHAN_VIEN = "TEN_NHAN_VIEN";
    private static final String USER_NAME = "USER_NAME";
    private static final String CA_ID = "CA_ID";
    private static final String MA_CA = "MA_CA";

    public static void savePendingOrder(Context context,PendingOrder pendingOrder)
    {

        List<PendingOrder> lstPendingOrders = getPendingOrder(context);
        if(lstPendingOrders != null) {
            lstPendingOrders.add(pendingOrder);
        } else {
            lstPendingOrders = new ArrayList<>();
            lstPendingOrders.add(pendingOrder);
        }
        Gson gson = new Gson();
        String result = gson.toJson(lstPendingOrders);


        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PENDING_ORDER, result);
        editor.commit();
    }

    public static List<PendingOrder> getPendingOrder(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        String strPendingOrder = sharedPreferences.getString(PENDING_ORDER, "");

        Type type = new TypeToken<List< PendingOrder >>() {}.getType();
        Gson gson = new Gson();
        List<PendingOrder> lstPendingOrders = gson.fromJson(strPendingOrder, type);
        return lstPendingOrders;
    }

    public static PendingOrder getPendingOrderFromList(Context context, long banId){
       List<PendingOrder> lstPendingOrders = getPendingOrder(context);
        if(lstPendingOrders != null && lstPendingOrders.size() > 0) {
            for (PendingOrder order : lstPendingOrders) {
                if (banId == order.banId) {
                    return order;
                }
            }
        }
        return null;
    }

    public static String getOrderCodeFromPendingOrder(Context context, long banId){
        List<PendingOrder> lstPendingOrders = getPendingOrder(context);
        if(lstPendingOrders != null && lstPendingOrders.size() > 0) {
            for (PendingOrder order : lstPendingOrders) {
                if (banId == order.banId) {
                    return order.orderCode;
                }
            }
        }
        return null;
    }

    public static void updateTongTienToPendingOrder(Context context, long banId, float tongTien){
        List<PendingOrder> lstPendingOrders = getPendingOrder(context);
        if(lstPendingOrders != null && lstPendingOrders.size() > 0) {
            for (PendingOrder order : lstPendingOrders) {
                if (banId == order.banId) {
                    order.tongTien = tongTien;
                }
            }
        }
        Gson gson = new Gson();
        String result = gson.toJson(lstPendingOrders);


        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PENDING_ORDER, result);
        editor.commit();
    }

    public static float getTongTienFromPendingOrder(Context context, long banId){
        List<PendingOrder> lstPendingOrders = getPendingOrder(context);
        if(lstPendingOrders != null && lstPendingOrders.size() > 0) {
            for (PendingOrder order : lstPendingOrders) {
                if (banId == order.banId) {
                    return order.tongTien;
                }
            }
        }
        return 0;
    }

    public static void removeOrderCode(Context context, String orderCode)
    {
        List<PendingOrder> lstPendingOrder = getPendingOrder(context);
        int removedOrder = -1;
        for(int i=0; i< lstPendingOrder.size(); i++){
            PendingOrder order = lstPendingOrder.get(i);
            if(order.orderCode.equalsIgnoreCase(orderCode)) {
                removedOrder = i;
                break;
            }
        }
        if(removedOrder > -1) {
            lstPendingOrder.remove(removedOrder);
        }
        Gson gson = new Gson();
        String result = gson.toJson(lstPendingOrder);


        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PENDING_ORDER, result);
        editor.commit();
    }

    //Config

    public static void saveWebSite(Context context, String website)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WEBSITE, website);
        editor.commit();
    }

    public static String getWebSite(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(WEBSITE, "");
    }

    public static void saveIdTrungTam(Context context, String idTrungTam)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID_TT, idTrungTam);
        editor.commit();
    }

    public static String getIdTrungTam(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ID_TT, "");
    }

    public static void saveIdMay(Context context, String idMay)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID_MAY, idMay);
        editor.commit();
    }

    public static String getIdMay(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ID_MAY, "");
    }

    public static void saveNgonNgu(Context context, String ngonNgu)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NGON_NGU, ngonNgu);
        editor.commit();
    }

    public static String getNgonNgu(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(NGON_NGU, "");
    }

    public static void saveNhanVienId(Context context, long nhanvienId)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(NHAN_VIEN_ID, nhanvienId);
        editor.commit();
    }

    public static long getNhanVienId(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(NHAN_VIEN_ID, -1);
    }

    public static void saveTenNhanVien(Context context, String tenNV)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEN_NHAN_VIEN, tenNV);
        editor.commit();
    }

    public static String getTenNhanVien(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TEN_NHAN_VIEN, "");
    }

    public static void saveUsername(Context context, String username)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, username);
        editor.commit();
    }

    public static String getUsername(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_NAME, "");
    }

    public static void saveCaId(Context context, long caId)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(CA_ID, caId);
        editor.commit();
    }

    public static long getCaId(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(CA_ID, -1);
    }

    public static void saveMaCa(Context context, String maCa)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MA_CA, maCa);
        editor.commit();
    }

    public static String getMaCa(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MA_CA, "");
    }
}
