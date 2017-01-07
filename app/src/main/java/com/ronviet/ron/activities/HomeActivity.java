package com.ronviet.ron.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.ronviet.ron.R;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout mBtnSale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initLayout();
    }

    private void initLayout()
    {
        mBtnSale = (LinearLayout) findViewById(R.id.btn_sale);

        mBtnSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SaleActivity.class));
            }
        });
    }
}
