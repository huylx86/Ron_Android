package com.ronviet.ron.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ronviet.ron.R;
import com.ronviet.ron.models.OrderInfo;
import com.ronviet.ron.models.ProductInfo;

/**
 * Created by LENOVO on 4/23/2016.
 */
public class DialogUtiils {

    private AlertDialog mAlertDialog;
    private Dialog mStaffDialog;

    public void showDialog(final Context context, String message, final boolean isCloseScreen)
    {

        mAlertDialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.title_alert_dialog))
                .setMessage(message)
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(isCloseScreen) {
                            ((Activity) context).finish();
                        }
                        dialog.dismiss();

                    }
                })
                .show();

    }

    public void showInputDialog(Context context, final ProductInfo prod, final Handler hanlderCallback)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_input_number_layout);

        final EditText userInput = (EditText) dialog.findViewById(R.id.edt_input_number);

        TextView tvProdName = (TextView)dialog.findViewById(R.id.tv_prod_name);
        tvProdName.setText(prod.getTenMon());

        TextView tvOk = (TextView)dialog.findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView)dialog.findViewById(R.id.tv_cancel);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soLuong = userInput.getText().toString();
                float fSoLuong = Float.parseFloat(soLuong);
                OrderInfo order = new OrderInfo();
                order.setId(prod.getId());
                order.setTenMon(prod.getTenMon());
                order.setSoLuong(fSoLuong);
                order.setMaMon(prod.getMaMon());
                order.setDonGia(prod.getDonGia());
                order.setDonViTinhId(prod.getDonViTinhId());
                order.setGiaGoc(prod.getGiaGoc());
                order.setGiaCoThue(prod.isGiaCoThue());
                order.setThue(prod.getThue());

                Message msg = Message.obtain();
                msg.obj = order;
                hanlderCallback.sendMessage(msg);
                dialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        userInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        // show it
        dialog.show();
    }

//    public void showInputDialog(Context context, final ProductInfo prod, final Handler hanlderCallback)
//    {
//        LayoutInflater li = LayoutInflater.from(context);
//        View promptsView = li.inflate(R.layout.dialog_input_number_layout, null);
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                context);
//
//        alertDialogBuilder.setView(promptsView);
//
//        final EditText userInput = (EditText) promptsView.findViewById(R.id.edt_input_number);
//
//        TextView tvProdName = (TextView)promptsView.findViewById(R.id.tv_prod_name);
//        tvProdName.setText(prod.getTenMon());
//
//        alertDialogBuilder
//                .setCancelable(false)
//                .setPositiveButton("OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int id) {
//                                String soLuong = userInput.getText().toString();
//                                float fSoLuong = Float.parseFloat(soLuong);
//                                OrderInfo order = new OrderInfo();
//                                order.setId(prod.getId());
//                                order.setTenMon(prod.getTenMon());
//                                order.setSoLuong(fSoLuong);
//                                order.setMaMon(prod.getMaMon());
//                                order.setDonGia(prod.getDonGia());
//                                order.setDonViTinhId(prod.getDonViTinhId());
//                                order.setGiaGoc(prod.getGiaGoc());
//                                order.setGiaCoThue(prod.isGiaCoThue());
//                                order.setThue(prod.getThue());
//
//                                Message msg = Message.obtain();
//                                msg.obj = order;
//                                hanlderCallback.sendMessage(msg);
//                            }
//                        })
//                .setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//        // create alert dialog
//        final AlertDialog alertDialog = alertDialogBuilder.create();
//
//        userInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//                }
//            }
//        });
//        // show it
//        alertDialog.show();
//    }
}
