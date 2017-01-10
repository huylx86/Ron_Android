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

    public static String getOrderCodeFromPendingOrder(Context context, long banId){
       List<PendingOrder> lstPendingOrders = getPendingOrder(context);
        for(PendingOrder order : lstPendingOrders) {
            if (banId == order.banId) {
                return order.orderCode;
            }
        }
        return null;
    }

    public static boolean removeOrderCode(Context context, String orderCode)
    {
        List<PendingOrder> lstPendingOrder = getPendingOrder(context);
        for(PendingOrder order : lstPendingOrder){
            if(order.orderCode.equalsIgnoreCase(orderCode)) {
                lstPendingOrder.remove(order);
                return true;
            }
        }
        return false;
    }
}
