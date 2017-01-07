package com.ronviet.ron.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ronviet.ron.R;
import com.ronviet.ron.models.TableInfo;

import java.util.List;

/**
 * Created by LENOVO on 9/4/2016.
 */
public class TableRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TableInfo> mLstTables;
    private Context mContext;

    public TableRecyclerViewAdapter(Context context, List<TableInfo> lstTables) {
        this.mLstTables = lstTables;
        this.mContext = context;
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

    public class TableRecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

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
        }
    }
}
