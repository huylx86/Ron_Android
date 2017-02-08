package com.ronviet.ron.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ronviet.ron.R;
import com.ronviet.ron.utils.DialogUtiils;
import com.ronviet.ron.utils.SharedPreferenceUtils;

public class SignInActivity extends AppCompatActivity {

    private Button mBtnSignIn;
    private Context mContext;
    private ImageView mIvLogo;

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
                    Intent iSignIn = new Intent(SignInActivity.this, HomeActivity.class);
                    iSignIn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(iSignIn);
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
}
