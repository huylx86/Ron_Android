package com.ronviet.ron.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ronviet.ron.R;
import com.ronviet.ron.api.APIConstants;
import com.ronviet.ron.api.ResponseUsersData;
import com.ronviet.ron.api.UserAPIHelper;
import com.ronviet.ron.models.UserInfo;
import com.ronviet.ron.utils.DialogUtiils;
import com.ronviet.ron.utils.SharedPreferenceUtils;

import java.util.List;

public class SignInActivity extends AppCompatActivity {

    private Button mBtnSignIn;
    private Context mContext;
    private ImageView mIvLogo;
    private EditText mEdtCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mContext = this;

        mBtnSignIn = (Button)findViewById(R.id.btn_sign_in);
        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isInputFirstConfig()) {
//                    Intent iSignIn = new Intent(SignInActivity.this, HomeActivity.class);
//                    iSignIn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(iSignIn);
                    if(mEdtCode.getText().toString().trim().equalsIgnoreCase("")) {
                        new DialogUtiils().showDialog(mContext, getString(R.string.input_code), false);
                    } else {
                        new UserAPIHelper(mContext).getListUsers(mHandlerGetListUser, true);
                    }

                } else {
                    new DialogUtiils().showDialogConfirm(mContext, getString(R.string.input_config_required), new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            Intent iConfig = new Intent(SignInActivity.this, FirstConfigActivity.class);
                            startActivity(iConfig);
                        }
                    });
                }
            }
        });

        mIvLogo = (ImageView) findViewById(R.id.iv_logo);
        mIvLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iConfig = new Intent(SignInActivity.this, FirstConfigActivity.class);
                startActivity(iConfig);
            }
        });

        mEdtCode = (EditText)findViewById(R.id.edt_code);

        if(!isInputFirstConfig()) {
            new DialogUtiils().showDialogConfirm(mContext, getString(R.string.input_config_required), new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    Intent iConfig = new Intent(SignInActivity.this, FirstConfigActivity.class);
                    startActivity(iConfig);
                }
            });
        }
    }

    private boolean isInputFirstConfig()
    {
        String website = SharedPreferenceUtils.getWebSite(this);
        if(website.equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }

    private Handler mHandlerGetListUser = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseUsersData res = (ResponseUsersData) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        boolean isCorrectPass = false;
                        String pass = mEdtCode.getText().toString();
                        List<UserInfo> lstUsers = res.data;
                        for(UserInfo user : lstUsers){
                            if(user.getUs_password().equalsIgnoreCase(pass)) {
                                isCorrectPass = true;

                                SharedPreferenceUtils.saveNhanVienId(mContext, user.getNv_autoid());
                                SharedPreferenceUtils.saveTenNhanVien(mContext, user.getNv_tennv());
                                SharedPreferenceUtils.saveUsername(mContext, user.getUs_username());

                                Intent iSignIn = new Intent(SignInActivity.this, HomeActivity.class);
                                iSignIn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(iSignIn);
                                break;
                            }
                        }
                        if(!isCorrectPass) {
                            new DialogUtiils().showDialog(mContext, getString(R.string.code_incorrect), false);
                            mEdtCode.setText("");
                        }

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
