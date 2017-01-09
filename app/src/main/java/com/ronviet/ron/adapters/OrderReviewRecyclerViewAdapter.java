package com.ronviet.ron.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ronviet.ron.R;
import com.ronviet.ron.models.OrderInfo;

import java.util.List;

/**
 * Created by LENOVO on 9/4/2016.
 */
public class OrderReviewRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<OrderInfo> mLstOrders;
    private Context mContext;

    public OrderReviewRecyclerViewAdapter(Context context, List<OrderInfo> lstOrders) {
        this.mLstOrders = lstOrders;
        this.mContext = context;
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
        orderHolder.mProdPrice.setText(String.valueOf(orderInfo.getDonGia()));
        orderHolder.mProdPromotion.setText(orderInfo.getPromotion());
        orderHolder.mProdNumber.setText(String.valueOf(orderInfo.getSoLuong()));
        orderHolder.mProdTotal.setText(String.valueOf(orderInfo.getTotal()));
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

    public class OrderReviewRecyclerViewHolders extends RecyclerView.ViewHolder {

        public TextView mProdName, mProdPrice, mProdPromotion;
        public TextView mProdNumber, mProdTotal;
        public View mView;

        public OrderReviewRecyclerViewHolders(View itemView) {
            super(itemView);
            mView = itemView;
            mProdName = (TextView) itemView.findViewById(R.id.tv_prod_name);
            mProdPrice = (TextView) itemView.findViewById(R.id.tv_prod_price);
            mProdPromotion = (TextView) itemView.findViewById(R.id.tv_prod_promotion);
            mProdNumber = (TextView) itemView.findViewById(R.id.tv_prod_number);
            mProdTotal = (TextView) itemView.findViewById(R.id.tv_prod_total);
        }
    }

}
