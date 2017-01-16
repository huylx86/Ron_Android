package com.ronviet.ron.adapters;

import android.app.Dialog;
import android.content.Context;
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
import com.ronviet.ron.utils.CommonUtils;
import com.ronviet.ron.utils.Constants;

import java.util.List;

/**
 * Created by LENOVO on 9/4/2016.
 */
public class OrderReviewRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<OrderInfo> mLstOrders;
    private Context mContext;
    private Handler mHandlerCallback;

    public OrderReviewRecyclerViewAdapter(Context context, List<OrderInfo> lstOrders, Handler handlerCallback) {
        this.mLstOrders = lstOrders;
        this.mContext = context;
        mHandlerCallback = handlerCallback;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false);
        OrderReviewRecyclerViewHolders holders = new OrderReviewRecyclerViewHolders(view);
        return holders;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderReviewRecyclerViewHolders orderHolder = (OrderReviewRecyclerViewHolders) holder;
        OrderInfo orderInfo = mLstOrders.get(position);
        orderHolder.mProdName.setText(orderInfo.getTenMon());
        orderHolder.mProdPrice.setText(CommonUtils.formatCurrency((int)orderInfo.getDonGia()));
        if(orderInfo.getPromotion() != null){
            orderHolder.mProdPromotion.setText(orderInfo.getPromotion());
            orderHolder.mProdPromotion.setVisibility(View.VISIBLE);
        } else {
            orderHolder.mProdPromotion.setVisibility(View.GONE);
        }
        orderHolder.mProdNumber.setText(String.valueOf(orderInfo.getSoLuong()));
        orderHolder.mProdTotal.setText(CommonUtils.formatCurrency((int)orderInfo.getTotal()));
        orderHolder.mView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return this.mLstOrders == null ? 0 : this.mLstOrders.size();
    }

    public void updateData(List<OrderInfo> lstOrders) {
        mLstOrders = lstOrders;
        notifyDataSetChanged();
    }

    public class OrderReviewRecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView mProdName, mProdPrice, mProdPromotion;
        public TextView mProdNumber, mProdTotal;
        public View mView;

        public OrderReviewRecyclerViewHolders(View itemView) {
            super(itemView);
            mView = itemView;
            itemView.setOnClickListener(this);
            mProdName = (TextView) itemView.findViewById(R.id.tv_prod_name);
            mProdPrice = (TextView) itemView.findViewById(R.id.tv_prod_price);
            mProdPromotion = (TextView) itemView.findViewById(R.id.tv_prod_promotion);
            mProdNumber = (TextView) itemView.findViewById(R.id.tv_prod_number);
            mProdTotal = (TextView) itemView.findViewById(R.id.tv_prod_total);
        }

        @Override
        public void onClick(View view) {
            int pos = Integer.parseInt(view.getTag().toString());
            OrderInfo info = mLstOrders.get(pos);
            showInputSuaSoLuongDialog(info);
        }
    }

    public void showInputSuaSoLuongDialog(final OrderInfo order)
    {
        final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        dialog.setContentView(R.layout.dialog_input_number_layout);
        dialog.setTitle(order.getTenMon());

        final EditText userInput = (EditText) dialog.findViewById(R.id.edt_input_number);

        TextView tvOk = (TextView)dialog.findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView)dialog.findViewById(R.id.tv_cancel);
        TextView tvDelete = (TextView)dialog.findViewById(R.id.tv_delete);

        tvDelete.setVisibility(View.VISIBLE);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soLuong = userInput.getText().toString();
                float fSoLuong = Float.parseFloat(soLuong);
//                OrderInfo newOrder = new OrderInfo();
//                newOrder.setId(order.getId());
//                newOrder.setTenMon(order.getTenMon());
//                newOrder.setSoLuong(fSoLuong);
//                newOrder.setMaMon(order.getMaMon());
//                newOrder.setDonGia(order.getDonGia());
//                newOrder.setDonViTinhId(order.getDonViTinhId());
//                newOrder.setGiaGoc(order.getGiaGoc());
//                newOrder.setGiaCoThue(order.isGiaCoThue());
//                newOrder.setThue(order.getThue());

                if(fSoLuong > 0) {
                    OrderInfo newOrder = order;
                    newOrder.setSoLuong(fSoLuong);
                    notifyDataSetChanged();

                    Message msg = Message.obtain();
                    msg.obj = newOrder;
                    msg.what = Constants.HANDLER_INPUT_SO_LUONG;
                    mHandlerCallback.sendMessage(msg);
                } else {
                    Message msg = Message.obtain();
                    msg.what = Constants.HANDLER_XOA_ORDER;
                    msg.obj = order;
                    mHandlerCallback.sendMessage(msg);
                }
                dialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = Message.obtain();
                msg.what = Constants.HANDLER_XOA_ORDER;
                msg.obj = order;
                mHandlerCallback.sendMessage(msg);
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
