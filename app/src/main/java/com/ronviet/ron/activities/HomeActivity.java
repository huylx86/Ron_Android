package com.ronviet.ron.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ronviet.ron.R;
import com.ronviet.ron.api.APIConstants;
import com.ronviet.ron.api.ResponseCaLamViecData;
import com.ronviet.ron.api.UserAPIHelper;
import com.ronviet.ron.utils.CommonUtils;
import com.ronviet.ron.utils.Constants;
import com.ronviet.ron.utils.DialogUtiils;
import com.ronviet.ron.utils.SharedPreferenceUtils;

import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout mBtnSale;
    private TextView mTvDate, mTvTenNhanVien, mTvCaLamViec;
    private boolean isRestartSaleScreen = false;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = this;

        isRestartSaleScreen = getIntent().getBooleanExtra(Constants.EXTRA_RESTART_SALE_SCREEN, false);
        if(isRestartSaleScreen) {
            goToSaleScreen();
        }
        initLayout();
        initData();
        loadData();
    }

    @Override
    public void onBackPressed() {
        new DialogUtiils().showDialogConfirm(mContext, mContext.getString(R.string.message_confirm_exit), new Handler(){
            @Override
            public void handleMessage(Message msg) {
                ((Activity)mContext).finish();
            }
        });
    }

    private void initLayout()
    {
        mBtnSale = (LinearLayout) findViewById(R.id.btn_sale);
        mTvDate = (TextView)findViewById(R.id.tv_date);
        mTvTenNhanVien = (TextView) findViewById(R.id.tv_name) ;
        mTvCaLamViec = (TextView) findViewById(R.id.tv_working_time);

        mBtnSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            goToSaleScreen();
            }
        });


    }

    private void initData()
    {
        String tenNV = SharedPreferenceUtils.getTenNhanVien(mContext);
        mTvTenNhanVien.setText(tenNV);

        mTvDate.setText(CommonUtils.convertDateStandardFormat(new Date()));
    }

    private void loadData()
    {
        String username = SharedPreferenceUtils.getUsername(mContext);
        new UserAPIHelper(mContext).getCaLamViec(username, mHandlerGetCaLamViec, true);
    }

    private void goToSaleScreen()
    {
        startActivity(new Intent(HomeActivity.this, SaleActivity.class));
    }

    private Handler mHandlerGetCaLamViec = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseCaLamViecData res = (ResponseCaLamViecData) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        SharedPreferenceUtils.saveCaId(mContext, res.data.getDanh_muc_ca_id());
                        SharedPreferenceUtils.saveMaCa(mContext, res.data.getDanh_muc_ca_maca());

                        mTvCaLamViec.setText("Ca " + res.data.getDanh_muc_ca_maca());
                    } else {
                        if(res.message != null) {
                            new DialogUtiils().showDialog(mContext, res.message, false);
                        } else {
                            new DialogUtiils().showDialog(mContext, getString(R.string.server_error), false);
                        }
                    }
                    break;
                case APIConstants.HANDLER_REQUEST_SERVER_FAILED:
                    new DialogUtiils().showDialog(mContext, getString(R.string.server_error), false);
                    break;
            }
        }
    };
}
