package com.ronviet.ron.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ronviet.ron.R;

public class SignInActivity extends AppCompatActivity {

    private Button mBtnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mBtnSignIn = (Button)findViewById(R.id.btn_sign_in);
        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, HomeActivity.class));
            }
        });
    }
}