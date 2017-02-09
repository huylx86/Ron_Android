package com.ronviet.ron.api;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.ronviet.ron.R;
import com.ronviet.ron.utils.DialogUtiils;
import com.ronviet.ron.utils.NetworkUtils;
import com.ronviet.ron.utils.SharedPreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hle59 on 2/9/2017.
 */

public class UserAPIHelper extends APIHelper {

    private String mHostName;
    private Context mContext;

    public UserAPIHelper(Context context) {
        mContext = context;
        mHostName = getHostName(context);
    }

    public void getListUsers(final Handler handler, boolean isShowProgress)
    {
        if(NetworkUtils.isNetworkAvailable(mContext)) {
            if (isShowProgress) {
                showProgressDialog(mContext);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(mHostName)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ICallServices service = retrofit.create(ICallServices.class);

            long ttId = Long.parseLong(SharedPreferenceUtils.getIdTrungTam(mContext));
            long ngonNgu = Long.parseLong(SharedPreferenceUtils.getNgonNgu(mContext));

            Call<ResponseCommon> response = service.getListUsers(ngonNgu, ttId);

            response.enqueue(new Callback<ResponseCommon>() {
                @Override
                public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                    ResponseCommon res = response.body();
                    if (res == null) {
                        res = new ResponseCommon();
                        res.code = APIConstants.REQUEST_FAILED;
                    }

                    Message msg = Message.obtain();
                    msg.what = APIConstants.HANDLER_REQUEST_SERVER_SUCCESS;
                    msg.obj = res;
                    handler.sendMessage(msg);
                    closeDialog();
                }

                @Override
                public void onFailure(Call<ResponseCommon> call, Throwable t) {
                    handler.sendEmptyMessage(APIConstants.HANDLER_REQUEST_SERVER_FAILED);
                    closeDialog();
                }
            });
        } else {
            new DialogUtiils().showDialog(mContext, mContext.getString(R.string.network_not_avaiable), false);
        }
    }
}
