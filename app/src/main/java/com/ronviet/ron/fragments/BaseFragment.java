package com.ronviet.ron.fragments;


import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import com.ronviet.ron.R;
import com.ronviet.ron.adapters.ProductCatRecyclerViewAdapter;
import com.ronviet.ron.api.APIConstants;
import com.ronviet.ron.api.ResponseProductCatData;
import com.ronviet.ron.models.ProductCatInfo;
import com.ronviet.ron.models.TableInfo;
import com.ronviet.ron.utils.DialogUtiils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    protected TableInfo mTableInfo;
    protected ProductCatRecyclerViewAdapter mAdapterProductCat;
    protected List<ProductCatInfo> mLstProductCats;

    public BaseFragment() {

    }



    protected Handler mHandlerProductCat = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseProductCatData res = (ResponseProductCatData) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        mLstProductCats = res.data;
                        mAdapterProductCat.updateData(mLstProductCats);
                    } else {
                        if(res.message != null) {
                            new DialogUtiils().showDialog(getContext(), res.message, false);
                        } else {
                            new DialogUtiils().showDialog(getContext(), getString(R.string.server_error), false);
                        }
                    }
                    break;
                case APIConstants.HANDLER_REQUEST_SERVER_FAILED:
                    new DialogUtiils().showDialog(getContext(), getString(R.string.server_error), false);
                    break;
            }
        }
    };

}
