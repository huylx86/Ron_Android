package com.ronviet.ron.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ronviet.ron.R;
import com.ronviet.ron.models.TableInfo;
import com.ronviet.ron.utils.Constants;

import java.util.List;

/**
 * Created by LENOVO on 9/4/2016.
 */
public class TableRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<TableInfo> mLstTables;
    private Context mContext;
    private Handler mHandlerProcessTable;

    private int mCurrentSelectPos = -1;
    private int iCount;

    public TableRecyclerViewAdapter(Context context, List<TableInfo> lstTables, Handler handlerProcessTable) {
        this.mLstTables = lstTables;
        this.mContext = context;
        mHandlerProcessTable = handlerProcessTable;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_item, null);
        TableRecyclerViewHolders holders = new TableRecyclerViewHolders(view);
        return holders;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TableRecyclerViewHolders tableHolder = (TableRecyclerViewHolders) holder;
        TableInfo tableInfo = mLstTables.get(position);
        tableHolder.mTableName.setText(tableInfo.getName());
        if(tableInfo.isSelection()){
            tableHolder.mTableName.setBackgroundColor(ContextCompat.getColor(mContext, R.color.button_select));
        } else {
            tableHolder.mTableName.setBackgroundColor(Color.parseColor(tableInfo.getMaMau()));
//            tableHolder.mTableName.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
        }

        tableHolder.mView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return this.mLstTables == null ? 0 : this.mLstTables.size();
    }

    public void updateData(List<TableInfo> lstTables) {
        mLstTables = lstTables;
        notifyDataSetChanged();
    }


    public class TableRecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTableName;
        public View mView;

        public TableRecyclerViewHolders(View itemView) {
            super(itemView);
            mView = itemView;
            itemView.setOnClickListener(this);
            mTableName = (TextView) itemView.findViewById(R.id.tv_table_name);
        }

        @Override
        public void onClick(View view) {
            int pos = Integer.parseInt(view.getTag().toString());
            //TODO : Send Handler to main activity to process

            TableInfo info = mLstTables.get(pos);

            if(mCurrentSelectPos != pos) {
                mCurrentSelectPos = pos;
                iCount =0;
            }

            iCount++;

            Handler handler = new Handler();
            Runnable r = new Runnable() {

                @Override
                public void run() {
                    iCount = 0;
                }
            };

            if (iCount == 1) {
                //Single click
                refreshSelectAllTables();
                info.setSelection(true);
                notifyDataSetChanged();

                Message msg = Message.obtain();
                msg.obj = info;
                if(info.getIdPhieu() > 0){
                    msg.what = Constants.HANDLER_OPEN_SUB_MENU;
                    mHandlerProcessTable.sendMessage(msg);
                } else {
                    msg.what = Constants.HANDLER_CLOSE_SUB_MENU;
                    mHandlerProcessTable.sendMessage(msg);
                }
                handler.postDelayed(r, 550);
            } else if (iCount == 2) {
                //Double click
                iCount = 0;
                Message msg = Message.obtain();
                msg.what = Constants.HANDLER_OPEN_TABLE;
                msg.obj = info;
                mHandlerProcessTable.sendMessage(msg);
            }


        }
    }

    private void refreshSelectAllTables()
    {
        for(TableInfo info : mLstTables)
        {
            info.setSelection(false);
        }

    }
}
