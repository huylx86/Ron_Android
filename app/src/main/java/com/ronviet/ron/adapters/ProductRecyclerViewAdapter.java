package com.ronviet.ron.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

        public TextView mProdName;
//                mProdDes;
        public View mView;

        public ProductRecyclerViewHolders(View itemView) {
            super(itemView);
            mView = itemView;
            itemView.setOnClickListener(this);
            mProdName = (TextView) itemView.findViewById(R.id.tv_prod_name);
//            mProdDes = (TextView) itemView.findViewById(R.id.tv_prod_des);
        }

        @Override
        public void onClick(View view) {
            int pos = Integer.parseInt(view.getTag().toString());
            ProductInfo info = mLstProducts.get(pos);
            showInputSoLuongDialog(mContext, info, mHandlerInputSoLuong);
        }
    }

    public void showInputSoLuongDialog(Context context, final ProductInfo prod, final Handler hanlderCallback)
    {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        dialog.setContentView(R.layout.dialog_input_number_and_note_layout);
        dialog.setTitle(prod.getTenMon());

        final EditText userInput = (EditText) dialog.findViewById(R.id.edt_input_number);
        final EditText userNoted = (EditText) dialog.findViewById(R.id.edt_note);

        TextView tvOk = (TextView)dialog.findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView)dialog.findViewById(R.id.tv_cancel);
        TextView tvDelete = (TextView)dialog.findViewById(R.id.tv_delete);

        tvDelete.setVisibility(View.GONE);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soLuong = userInput.getText().toString();
                String ghiChu = userNoted.getText().toString();
                float fSoLuong = Float.parseFloat(soLuong);
                OrderInfo order = new OrderInfo();
                order.setId(prod.getId());
                order.setTenMon(prod.getTenMon());
                order.setSoLuong(fSoLuong);
                order.setGhiChu(ghiChu);
                order.setMaMon(prod.getMaMon());
                order.setDonGia(prod.getDonGia());
                order.setDonViTinhId(prod.getDonViTinhId());
                order.setGiaGoc(prod.getGiaGoc());
                order.setGiaCoThue(prod.isGiaCoThue());
                order.setThue(prod.getThue());

                Message msg = Message.obtain();
                msg.obj = order;
                hanlderCallback.sendMessage(msg);
                dialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        userInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        // show it
        dialog.show();
    }

}
