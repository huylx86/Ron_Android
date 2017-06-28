package com.ronviet.ron.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ronviet.ron.R;
import com.ronviet.ron.models.AreaInfo;
import com.ronviet.ron.utils.CommonUtils;

import java.util.List;

/**
 * Created by LENOVO on 9/4/2016.
 */
public class AreaRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AreaInfo> mLstAreas;
    private Context mContext;
    private Handler mHandlerAreaSelection;

    public AreaRecyclerViewAdapter(Context context, List<AreaInfo> lstAreas, Handler handlerAreaSelection) {
        this.mLstAreas = lstAreas;
        this.mContext = context;
        mHandlerAreaSelection = handlerAreaSelection;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.area_item, null);
        if(this.mLstAreas.size() < 4) {
            view.setMinimumWidth(CommonUtils.getWidthScreen(mContext) / mLstAreas.size());
        } else {
            view.setMinimumWidth(CommonUtils.getWidthScreen(mContext) / 4);
        }
        AreaRecyclerViewHolders holders = new AreaRecyclerViewHolders(view);
        return holders;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final AreaRecyclerViewHolders areaHolder = (AreaRecyclerViewHolders) holder;
        AreaInfo areaInfo = mLstAreas.get(position);
        areaHolder.mAreaName.setText(areaInfo.getName());
        if(areaInfo.ismIsSelection()) {
            areaHolder.mAreaName.setBackgroundColor(ContextCompat.getColor(mContext, R.color.button_selected));
        } else {
            areaHolder.mAreaName.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lavender));
        }
        areaHolder.mView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return this.mLstAreas == null ? 0 : this.mLstAreas.size();
    }

    public void updateData(List<AreaInfo> lstAreas) {
        mLstAreas = lstAreas;
        notifyDataSetChanged();
    }

    public class AreaRecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mAreaName;
        public View mView;

        public AreaRecyclerViewHolders(View itemView) {
            super(itemView);
            mView = itemView;
            itemView.setOnClickListener(this);
            mAreaName = (TextView) itemView.findViewById(R.id.tv_area_name);
        }

        @Override
        public void onClick(View view) {
            int pos = Integer.parseInt(view.getTag().toString());
            //TODO : Send Handler to main activity to process
            refreshSelectArea();
            AreaInfo area = mLstAreas.get(pos);
            area.setmIsSelection(true);
            notifyDataSetChanged();
            mHandlerAreaSelection.sendEmptyMessage(pos);
        }
    }

    private void refreshSelectArea()
    {
        for(AreaInfo area : mLstAreas){
            area.setmIsSelection(false);
        }
    }
}
