package com.ronviet.ron.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ronviet.ron.R;
import com.ronviet.ron.models.OrderReturnInfo;
import com.ronviet.ron.utils.CommonUtils;
import com.ronviet.ron.utils.Constants;
import com.ronviet.ron.utils.DialogUtiils;

import java.util.List;

/**
 * Created by LENOVO on 9/4/2016.
 */
public class OrderReturnRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<OrderReturnInfo> mLstReturnOrders;
    private Context mContext;
    private Handler mHandlerProcessSubmitOrder;
    private float mRemainingProdCheck = 0;
    private OrderReturnInfo mOrderReturnProd;
    private float mSoLuongTra = 0;

    public OrderReturnRecyclerViewAdapter(Context context, List<OrderReturnInfo> lstReturnOrders, Handler handler) {
        this.mLstReturnOrders = lstReturnOrders;
        this.mContext = context;
        mHandlerProcessSubmitOrder = handler;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false);
        OrderReturnRecyclerViewHolders holders = new OrderReturnRecyclerViewHolders(view);
        return holders;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderReturnRecyclerViewHolders orderHolder = (OrderReturnRecyclerViewHolders) holder;
        OrderReturnInfo orderInfo = mLstReturnOrders.get(position);
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
        return this.mLstReturnOrders == null ? 0 : this.mLstReturnOrders.size();
    }

    public void updateData(List<OrderReturnInfo> lstReturnOrders) {
        mLstReturnOrders = lstReturnOrders;
        notifyDataSetChanged();
    }

    public class OrderReturnRecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mProdName, mProdPrice, mProdPromotion;
        public TextView mProdNumber, mProdTotal;
        public View mView;

        public OrderReturnRecyclerViewHolders(View itemView) {
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
            OrderReturnInfo info = mLstReturnOrders.get(pos);
            mOrderReturnProd = info;
            mRemainingProdCheck = info.getSoLuong();
            showInputDialog(info);
        }
    }

    private void showInputDialog(final OrderReturnInfo order)
    {
        LayoutInflater li = LayoutInflater.from(mContext);
        View promptsView = li.inflate(R.layout.dialog_input_return_prod_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);

        alertDialogBuilder.setView(promptsView);

        TextView tvBeforeChangeOrder = (TextView)promptsView.findViewById(R.id.tv_before_change_order);
        final TextView tvAfterChangeOrder = (TextView) promptsView.findViewById(R.id.tv_after_change_order);

        final String textAfterChange = mContext.getString(R.string.after_change_order);

        mSoLuongTra = 1;
        mRemainingProdCheck = order.getSoLuong() - mSoLuongTra;
        tvBeforeChangeOrder.setText(mContext.getString(R.string.before_change_order) + order.getSoLuong() );
        tvAfterChangeOrder.setText(textAfterChange + String.valueOf(mRemainingProdCheck));

        final EditText userInput = (EditText) promptsView.findViewById(R.id.edt_change_order);
        userInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    mSoLuongTra = Float.parseFloat(userInput.getText().toString());
                }catch (Exception e){}
                mRemainingProdCheck = order.getSoLuong() - mSoLuongTra;
                tvAfterChangeOrder.setText(textAfterChange + String.format("%.2f", mRemainingProdCheck));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        TextView tvProdName = (TextView)promptsView.findViewById(R.id.tv_prod_name);
        tvProdName.setText(order.getTenMon());

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if(mRemainingProdCheck < 0) {
                            new DialogUtiils().showDialog(mContext, mContext.getString(R.string.validate_so_luong_con_lai), false);
                        } else {
                            if (mRemainingProdCheck != order.getSoLuong()) {
                                mOrderReturnProd.setSoLuong(mRemainingProdCheck);
                                mOrderReturnProd.setSoLuongTra(mSoLuongTra);
                                Message msg = Message.obtain();
                                msg.obj = mOrderReturnProd;
                                msg.what = Constants.HANDLER_OPEN_SUB_MENU;
                                mHandlerProcessSubmitOrder.sendMessage(msg);
                                notifyDataSetChanged();
                            }
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });

        userInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        // show it
        alertDialog.show();
    }

//    private boolean isChangeOrder()
//    {
//        for(OrderInfo order : mLstOrders){
//            if(order.isReturnProd()) {
//                return true;
//            }
//        }
//        return false;
//    }
}
