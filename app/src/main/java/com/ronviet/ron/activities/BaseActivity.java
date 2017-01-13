package com.ronviet.ron.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ronviet.ron.R;
import com.ronviet.ron.models.TableInfo;
import com.ronviet.ron.utils.CommonUtils;
import com.ronviet.ron.utils.Constants;

public class BaseActivity extends AppCompatActivity {

    protected ImageView mIvBack, mIvListOrder;
    protected TextView mTvTitle;
    protected TextView mTvProdTotal;
    protected LinearLayout mLnTotal;
    protected TableInfo mTableSelection;
    protected boolean isAddOrder = false;
    protected long mOrderId;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected void initHeader()
    {
        mIvBack = (ImageView)findViewById(R.id.iv_back);
        mIvListOrder = (ImageView)findViewById(R.id.iv_list_order);
        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mTvProdTotal = (TextView)findViewById(R.id.tv_total);
        mLnTotal = (LinearLayout)findViewById(R.id.ln_order);

        if(mIvBack != null){
            mIvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }

        if(mLnTotal != null){
            mLnTotal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mTableSelection != null) {
                        if(isAddOrder){
                            Intent iProduct = new Intent(mContext, ProductActivity.class);
                            iProduct.putExtra(Constants.EXTRA_TABLE, mTableSelection);
                            mContext.startActivity(iProduct);
                            ((Activity)mContext).finish();
                        } else {
                            Intent iOrder = new Intent(mContext, OrderReviewActivity.class);
                            iOrder.putExtra(Constants.EXTRA_TABLE, mTableSelection);
                            startActivity(iOrder);
                        }
                    }
                }
            });
        }
    }

    protected void setTitle(String title)
    {
        if(mTvTitle != null){
            mTvTitle.setText(title);
        }
    }

    protected void setTotal(float total)
    {
        if(mTvProdTotal != null){
            mTvProdTotal.setText(CommonUtils.formatCurrency((int)total));
        }
    }

    protected void setAddOrder()
    {
        mIvListOrder.setImageResource(R.drawable.add_order);
        isAddOrder = true;
        mTvProdTotal.setVisibility(View.GONE);
    }

    protected void hidePayment()
    {
        if(mLnTotal != null) {
            mLnTotal.setVisibility(View.GONE);
        }
    }
}
