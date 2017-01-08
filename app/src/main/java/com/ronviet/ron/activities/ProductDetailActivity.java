package com.ronviet.ron.activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ronviet.ron.R;
import com.ronviet.ron.adapters.ProductRecyclerViewAdapter;
import com.ronviet.ron.models.ProductInfo;
import com.ronviet.ron.models.TableInfo;
import com.ronviet.ron.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends BaseActivity {

    private ProductRecyclerViewAdapter mAdapterProduct;
    private List<ProductInfo> mLstProducts;
    private ProductInfo mProductInfo;
    private TableInfo mTableInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        mContext = this;
        mProductInfo = (ProductInfo)getIntent().getSerializableExtra(Constants.EXTRA_PRODUCT);
        mTableInfo = (TableInfo)getIntent().getSerializableExtra(Constants.EXTRA_TABLE);
        mTableSelection = mTableInfo;
        dummyData();
        initLayout();

    }

    private void initLayout()
    {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view_product);
        GridLayoutManager tableLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(tableLayoutManager);
        mAdapterProduct = new ProductRecyclerViewAdapter(this, mLstProducts, mTableInfo, true);
        recyclerView.setAdapter(mAdapterProduct);

        initHeader();
        setTotal(String.valueOf(mTableInfo.getTotal()));
        if(mProductInfo != null) {
            setTitle(mProductInfo.getTenMon());
        }
    }

    private void dummyData()
    {
        mLstProducts = new ArrayList<>();
        for(int i=0; i<20; i++) {
            ProductInfo info = new ProductInfo();
            info.setId(i);
            info.setTenMon(mProductInfo.getTenMon() + " - " + i);
            info.setDonGia(i);
            mLstProducts.add(info);
        }
    }
}
