package com.ronviet.ron.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ronviet.ron.R;
import com.ronviet.ron.activities.ProductDetailActivity;
import com.ronviet.ron.models.ProductInfo;
import com.ronviet.ron.models.TableInfo;
import com.ronviet.ron.utils.Constants;

import java.util.List;

/**
 * Created by LENOVO on 9/4/2016.
 */
public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ProductInfo> mLstProducts;
    private TableInfo mTableInfo;
    private Context mContext;
    private boolean mIsProductDetail = false;

    public ProductRecyclerViewAdapter(Context context, List<ProductInfo> lstProducts, TableInfo tableInfo, boolean isProductDetail) {
        this.mLstProducts = lstProducts;
        this.mContext = context;
        mIsProductDetail = isProductDetail;
        mTableInfo = tableInfo;
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
        prodHolder.mProdName.setText(prodInfo.getName());
        prodHolder.mProdDes.setText(prodInfo.getDes());
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

            if(mIsProductDetail) {
               showInputDialog(info.getName());
            } else {
                Intent iProduct = new Intent(mContext, ProductDetailActivity.class);
                iProduct.putExtra(Constants.EXTRA_PRODUCT, info);
                iProduct.putExtra(Constants.EXTRA_TABLE, mTableInfo);
                mContext.startActivity(iProduct);
            }


        }
    }

    private void showInputDialog(String prodName)
    {
        LayoutInflater li = LayoutInflater.from(mContext);
        View promptsView = li.inflate(R.layout.dialog_input_number_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.edt_input_number);

        TextView tvProdName = (TextView)promptsView.findViewById(R.id.tv_prod_name);
        tvProdName.setText(prodName);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
