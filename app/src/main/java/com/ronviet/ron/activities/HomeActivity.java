package com.ronviet.ron.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.ronviet.ron.R;
import com.ronviet.ron.utils.Constants;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout mBtnSale;
    private boolean isRestartSaleScreen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        isRestartSaleScreen = getIntent().getBooleanExtra(Constants.EXTRA_RESTART_SALE_SCREEN, false);
        if(isRestartSaleScreen) {
            goToSaleScreen();
        }
        initLayout();
    }

    private void initLayout()
    {
        mBtnSale = (LinearLayout) findViewById(R.id.btn_sale);

        mBtnSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            goToSaleScreen();
            }
        });
    }

    private void goToSaleScreen()
    {
        startActivity(new Intent(HomeActivity.this, SaleActivity.class));
    }
}
