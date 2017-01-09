package com.ronviet.ron.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ronviet.ron.R;
import com.ronviet.ron.models.OrderInfo;
import com.ronviet.ron.models.ProductInfo;
import com.ronviet.ron.models.TableInfo;

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
            showInputDialog(info);
        }
    }

    private void showInputDialog(final ProductInfo prod)
    {
        LayoutInflater li = LayoutInflater.from(mContext);
        View promptsView = li.inflate(R.layout.dialog_input_number_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.edt_input_number);

        TextView tvProdName = (TextView)promptsView.findViewById(R.id.tv_prod_name);
        tvProdName.setText(prod.getTenMon());

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                String soLuong = userInput.getText().toString();
                                float fSoLuong = Float.parseFloat(soLuong);
                                OrderInfo order = new OrderInfo();
                                order.setId(prod.getId());
                                order.setTenMon(prod.getTenMon());
                                order.setSoLuong(fSoLuong);
                                order.setMaMon(prod.getMaMon());
                                order.setDonGia(prod.getDonGia());
                                order.setDonViTinhId(prod.getDonViTinhId());
                                order.setGiaGoc(prod.getGiaGoc());
                                order.setGiaCoThue(prod.isGiaCoThue());
                                order.setThue(prod.getThue());

                                Message msg = Message.obtain();
                                msg.obj = order;
                                mHandlerInputSoLuong.sendMessage(msg);
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
