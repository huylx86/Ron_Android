package com.ronviet.ron.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ronviet.ron.R;
import com.ronviet.ron.activities.ProductDetailActivity;
import com.ronviet.ron.models.ProductCatInfo;
import com.ronviet.ron.models.TableInfo;
import com.ronviet.ron.utils.Constants;

import java.util.List;

/**
 * Created by LENOVO on 9/4/2016.
 */
public class ProductCatRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ProductCatInfo> mLstProductCats;
    private TableInfo mTableInfo;
    private Context mContext;

    public ProductCatRecyclerViewAdapter(Context context, List<ProductCatInfo> lstProductCats, TableInfo tableInfo) {
        this.mLstProductCats = lstProductCats;
        this.mContext = context;
        mTableInfo = tableInfo;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, null);
        ProductCatRecyclerViewHolders holders = new ProductCatRecyclerViewHolders(view);
        return holders;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProductCatRecyclerViewHolders prodHolder = (ProductCatRecyclerViewHolders) holder;
        ProductCatInfo prodCatInfo = mLstProductCats.get(position);
        prodHolder.mProdName.setText(prodCatInfo.getTenNhom());
        prodHolder.mProdName.setBackgroundColor(Color.parseColor(prodCatInfo.getMaMau()));
        prodHolder.mView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return this.mLstProductCats == null ? 0 : this.mLstProductCats.size();
    }

    public void updateData(List<ProductCatInfo> lstProductCats) {
        mLstProductCats = lstProductCats;
        notifyDataSetChanged();
    }

    public class ProductCatRecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mProdName; //mProdDes;
        public View mView;

        public ProductCatRecyclerViewHolders(View itemView) {
            super(itemView);
            mView = itemView;
            itemView.setOnClickListener(this);
            mProdName = (TextView) itemView.findViewById(R.id.tv_prod_name);
//            mProdDes = (TextView) itemView.findViewById(R.id.tv_prod_des);
        }

        @Override
        public void onClick(View view) {
            int pos = Integer.parseInt(view.getTag().toString());

            ProductCatInfo info = mLstProductCats.get(pos);

            Intent iProduct = new Intent(mContext, ProductDetailActivity.class);
            iProduct.putExtra(Constants.EXTRA_PRODUCT, info);
            iProduct.putExtra(Constants.EXTRA_TABLE, mTableInfo);
            mContext.startActivity(iProduct);
        }
    }
}
