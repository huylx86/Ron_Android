package com.ronviet.ron.api;

import android.content.Context;

import com.ronviet.ron.R;
import com.ronviet.ron.utils.ProgressDialogUtils;

/**
 * Created by LENOVO on 9/26/2016.
 */

public class APIHelper {
    public static final String HOST_NAME = "http://pon.cm";

    protected ProgressDialogUtils mProgressDialogUtils;

    protected void showProgressDialog(Context context) {

        if(mProgressDialogUtils == null) {
            mProgressDialogUtils = new ProgressDialogUtils(context, "", context.getString(R.string.connecting));
        }
        mProgressDialogUtils.show();
    }

    protected void closeDialog() {
        if(mProgressDialogUtils != null){
            mProgressDialogUtils.hide();
        }
    }

}