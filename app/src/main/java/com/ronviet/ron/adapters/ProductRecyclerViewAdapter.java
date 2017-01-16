package com.ronviet.ron.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ronviet.ron.R;
import com.ronviet.ron.models.ProductInfo;
import com.ronviet.ron.models.TableInfo;
import com.ronviet.ron.utils.DialogUtiils;

import java.util.List;

/**
 * Created by LENOVO on 9/4/2016.
 */
public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ProductInfo> mLstProducts;
    private TableInfo mTableInfo;
    private Context mContext;
    private Handler mHandlerInputSoLuong;

    public ProductRecyclerViewAdapter(Context context, List<ProductInfo> lstProducts, TableInfo tableInfo, Handler handlerInputSoLuong) {
        this.mLstProducts = lstProducts;
        this.mContext = context;
        mTableInfo = tableInfo;
        mHandlerInputSoLuong = handlerInputSoLuong;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, null);
        ProductRecyclerViewHolders holders = new ProductRecyclerViewHolders(view);
        return holders;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProductRecyclerViewHolders prodHolder = (ProductRecyclerViewHolders) holder;
        ProductInfo prodInfo = mLstProducts.get(position);
        prodHolder.mProdName.setText(prodInfo.getTenMon());
        prodHolder.mProdName.setBackgroundColor(Color.parseColor(prodInfo.getMaMau()));
//        prodHolder.mProdDes.setText(String.valueOf(prodInfo.getDonGia()));
        prodHolder.mView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return this.mLstProducts == null ? 0 : this.mLstProducts.size();
    }

    public void updateData(List<ProductInfo> lstProducts) {
        mLstProducts = lstProducts;
        notifyDataSetChanged();
    }

    public class ProductRecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mProdName, mProdDes;
        public View mView;

        public ProductRecyclerViewHolders(View itemView) {
            super(itemView);
            mView = itemView;
            itemView.setOnClickListener(this);
            mProdName = (TextView) itemView.findViewById(R.id.tv_prod_name);
            mProdDes = (TextView) itemView.findViewById(R.id.tv_prod_des);
        }

        @Override
        public void onClick(View view) {
            int pos = Integer.parseInt(view.getTag().toString());
            ProductInfo info = mLstProducts.get(pos);
            new DialogUtiils().showInputDialog(mContext, info, mHandlerInputSoLuong);
        }
    }


}
