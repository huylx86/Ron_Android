package com.ronviet.ron.api;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.ronviet.ron.R;
import com.ronviet.ron.utils.CommonUtils;
import com.ronviet.ron.utils.DialogUtiils;
import com.ronviet.ron.utils.NetworkUtils;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LENOVO on 1/8/2017.
 */

public class SaleAPIHelper extends APIHelper {

    public void getAreaInfo(Context context, final Handler handler, boolean isShowProgress)
    {
        if(NetworkUtils.isNetworkAvailable(context)) {
            if (isShowProgress) {
                showProgressDialog(context);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HOST_NAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ICallServices service = retrofit.create(ICallServices.class);

            Call<ResponseAreaInfoData> response = service.getAreaInfo(3, 1, 1);

            response.enqueue(new Callback<ResponseAreaInfoData>() {
                @Override
                public void onResponse(Call<ResponseAreaInfoData> call, Response<ResponseAreaInfoData> response) {
                    ResponseAreaInfoData res = response.body();
                    if (res == null) {
                        res = new ResponseAreaInfoData();
                        res.code = APIConstants.REQUEST_FAILED;
                    }

                    Message msg = Message.obtain();
                    msg.what = APIConstants.HANDLER_REQUEST_SERVER_SUCCESS;
                    msg.obj = res;
                    handler.sendMessage(msg);
                    closeDialog();
                }

                @Override
                public void onFailure(Call<ResponseAreaInfoData> call, Throwable t) {
                    handler.sendEmptyMessage(APIConstants.HANDLER_REQUEST_SERVER_FAILED);
                    closeDialog();
                }
            });
        } else {
            new DialogUtiils().showDialog(context, context.getString(R.string.network_not_avaiable), false);
        }
    }

    public void getTableInfo(Context context, long areaId, final Handler handler, boolean isShowProgress)
    {
        if(NetworkUtils.isNetworkAvailable(context)) {
            if (isShowProgress) {
                showProgressDialog(context);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HOST_NAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ICallServices service = retrofit.create(ICallServices.class);

            Call<ResponseTableInfoData> response = service.getTableInfo(areaId, 1, 3);

            response.enqueue(new Callback<ResponseTableInfoData>() {
                @Override
                public void onResponse(Call<ResponseTableInfoData> call, Response<ResponseTableInfoData> response) {
                    ResponseTableInfoData res = response.body();
                    if (res == null) {
                        res = new ResponseTableInfoData();
                        res.code = APIConstants.REQUEST_FAILED;
                    }

                    Message msg = Message.obtain();
                    msg.what = APIConstants.HANDLER_REQUEST_SERVER_SUCCESS;
                    msg.obj = res;
                    handler.sendMessage(msg);
                    closeDialog();
                }

                @Override
                public void onFailure(Call<ResponseTableInfoData> call, Throwable t) {
                    handler.sendEmptyMessage(APIConstants.HANDLER_REQUEST_SERVER_FAILED);
                    closeDialog();
                }
            });
        } else {
            new DialogUtiils().showDialog(context, context.getString(R.string.network_not_avaiable), false);
        }
    }

    public void getMaPhieu(Context context, final Handler handler, boolean isShowProgress)
    {
        if(NetworkUtils.isNetworkAvailable(context)) {
            if (isShowProgress) {
                showProgressDialog(context);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HOST_NAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ICallServices service = retrofit.create(ICallServices.class);

            Call<ResponseCreateMaPhieuData> response = service.getMaPhieu(1, 3, 2, CommonUtils.convertDateFormat(new Date()), false);

            response.enqueue(new Callback<ResponseCreateMaPhieuData>() {
                @Override
                public void onResponse(Call<ResponseCreateMaPhieuData> call, Response<ResponseCreateMaPhieuData> response) {
                    ResponseCreateMaPhieuData res = response.body();
                    if (res == null) {
                        res = new ResponseCreateMaPhieuData();
                        res.code = APIConstants.REQUEST_FAILED;
                    }

                    Message msg = Message.obtain();
                    msg.what = APIConstants.HANDLER_REQUEST_SERVER_SUCCESS;
                    msg.obj = res;
                    handler.sendMessage(msg);
                    closeDialog();
                }

                @Override
                public void onFailure(Call<ResponseCreateMaPhieuData> call, Throwable t) {
                    handler.sendEmptyMessage(APIConstants.HANDLER_REQUEST_SERVER_FAILED);
                    closeDialog();
                }
            });
        } else {
            new DialogUtiils().showDialog(context, context.getString(R.string.network_not_avaiable), false);
        }
    }

    public void getIdPhieu(Context context, String maPhieu, long khuId, long banId, final Handler handler, boolean isShowProgress)
    {
        if(NetworkUtils.isNetworkAvailable(context)) {
            if (isShowProgress) {
                showProgressDialog(context);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HOST_NAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ICallServices service = retrofit.create(ICallServices.class);

            Call<ResponseCreatePhieuData> response = service.getPhieuId(-1000, maPhieu, 1, "", "", "", "", -1000, "", "", 1,
                                                        false, false, "", 0, 0, 0, khuId, false, false, false, false, false, false,
                                                        false, false, 1, 2, 3, banId, CommonUtils.convertDateFormat(new Date()), false,
                                                        CommonUtils.convertDateFormat(new Date()), CommonUtils.convertDateFormat(new Date()),
                                                        "INSERT", 1, "1");

            response.enqueue(new Callback<ResponseCreatePhieuData>() {
                @Override
                public void onResponse(Call<ResponseCreatePhieuData> call, Response<ResponseCreatePhieuData> response) {
                    ResponseCreatePhieuData res = response.body();
                    if (res == null) {
                        res = new ResponseCreatePhieuData();
                        res.code = APIConstants.REQUEST_FAILED;
                    }

                    Message msg = Message.obtain();
                    msg.what = APIConstants.HANDLER_REQUEST_SERVER_SUCCESS;
                    msg.obj = res;
                    handler.sendMessage(msg);
                    closeDialog();
                }

                @Override
                public void onFailure(Call<ResponseCreatePhieuData> call, Throwable t) {
                    handler.sendEmptyMessage(APIConstants.HANDLER_REQUEST_SERVER_FAILED);
                    closeDialog();
                }
            });
        } else {
            new DialogUtiils().showDialog(context, context.getString(R.string.network_not_avaiable), false);
        }
    }

    public void getProductCategories(Context context, long khuId, int loaiHangHoa, final Handler handler, boolean isShowProgress)
    {
        if(NetworkUtils.isNetworkAvailable(context)) {
            if (isShowProgress) {
                showProgressDialog(context);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HOST_NAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ICallServices service = retrofit.create(ICallServices.class);

            Call<ResponseProductCatData> response = service.getProductCategories(1, 3, 1, khuId, 1, loaiHangHoa, 1, CommonUtils.convertDateFormat(new Date()));

            response.enqueue(new Callback<ResponseProductCatData>() {
                @Override
                public void onResponse(Call<ResponseProductCatData> call, Response<ResponseProductCatData> response) {
                    ResponseProductCatData res = response.body();
                    if (res == null) {
                        res = new ResponseProductCatData();
                        res.code = APIConstants.REQUEST_FAILED;
                    }

                    Message msg = Message.obtain();
                    msg.what = APIConstants.HANDLER_REQUEST_SERVER_SUCCESS;
                    msg.obj = res;
                    handler.sendMessage(msg);
                    closeDialog();
                }

                @Override
                public void onFailure(Call<ResponseProductCatData> call, Throwable t) {
                    handler.sendEmptyMessage(APIConstants.HANDLER_REQUEST_SERVER_FAILED);
                    closeDialog();
                }
            });
        } else {
            new DialogUtiils().showDialog(context, context.getString(R.string.network_not_avaiable), false);
        }
    }

    public void getProducts(Context context, long khuId, long idProductCat, final Handler handler, boolean isShowProgress)
    {
        if(NetworkUtils.isNetworkAvailable(context)) {
            if (isShowProgress) {
                showProgressDialog(context);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HOST_NAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ICallServices service = retrofit.create(ICallServices.class);

            Call<ResponseProductData> response = service.getProducts(1, khuId, CommonUtils.convertDateFormat(new Date()), idProductCat, 1, 1, 3);

            response.enqueue(new Callback<ResponseProductData>() {
                @Override
                public void onResponse(Call<ResponseProductData> call, Response<ResponseProductData> response) {
                    ResponseProductData res = response.body();
                    if (res == null) {
                        res = new ResponseProductData();
                        res.code = APIConstants.REQUEST_FAILED;
                    }

                    Message msg = Message.obtain();
                    msg.what = APIConstants.HANDLER_REQUEST_SERVER_SUCCESS;
                    msg.obj = res;
                    handler.sendMessage(msg);
                    closeDialog();
                }

                @Override
                public void onFailure(Call<ResponseProductData> call, Throwable t) {
                    handler.sendEmptyMessage(APIConstants.HANDLER_REQUEST_SERVER_FAILED);
                    closeDialog();
                }
            });
        } else {
            new DialogUtiils().showDialog(context, context.getString(R.string.network_not_avaiable), false);
        }
    }

    public void getOrderCode(Context context, final Handler handler, boolean isShowProgress)
    {
        if(NetworkUtils.isNetworkAvailable(context)) {
            if (isShowProgress) {
                showProgressDialog(context);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HOST_NAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ICallServices service = retrofit.create(ICallServices.class);

            Call<ResponseCreateOrderCodeData> response = service.getOrderCode(3, 1, CommonUtils.convertDateFormat(new Date()), false);

            response.enqueue(new Callback<ResponseCreateOrderCodeData>() {
                @Override
                public void onResponse(Call<ResponseCreateOrderCodeData> call, Response<ResponseCreateOrderCodeData> response) {
                    ResponseCreateOrderCodeData res = response.body();
                    if (res == null) {
                        res = new ResponseCreateOrderCodeData();
                        res.code = APIConstants.REQUEST_FAILED;
                    }

                    Message msg = Message.obtain();
                    msg.what = APIConstants.HANDLER_REQUEST_SERVER_SUCCESS;
                    msg.obj = res;
                    handler.sendMessage(msg);
                    closeDialog();
                }

                @Override
                public void onFailure(Call<ResponseCreateOrderCodeData> call, Throwable t) {
                    handler.sendEmptyMessage(APIConstants.HANDLER_REQUEST_SERVER_FAILED);
                    closeDialog();
                }
            });
        } else {
            new DialogUtiils().showDialog(context, context.getString(R.string.network_not_avaiable), false);
        }
    }

    public void submitOrderTungMon(Context context, String orderCode, long idPhieu, long idMon, String maMon, String tenMon,
                               float soLuong, long donViTinhId, float giaGoc, float donGia, boolean giaCoThue, float thue,
                               long idBan, String yeuCauThem, final Handler handler, boolean isShowProgress)
    {
        if(NetworkUtils.isNetworkAvailable(context)) {
            if (isShowProgress) {
                showProgressDialog(context);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HOST_NAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ICallServices service = retrofit.create(ICallServices.class);

            Call<ResponseCommon> response = service.submitOrderTungMon(-1000, orderCode, -1000, idPhieu, "ORDER", idMon, maMon, tenMon, soLuong,
                                                            donViTinhId, giaGoc, donGia, giaCoThue, thue, 3, 3, 1, CommonUtils.convertDateFormat(new Date()),
                                                            idBan, yeuCauThem);

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
            new DialogUtiils().showDialog(context, context.getString(R.string.network_not_avaiable), false);
        }
    }

    public void getReviewOrder(Context context, String orderCode, final Handler handler, boolean isShowProgress)
    {
        if(NetworkUtils.isNetworkAvailable(context)) {
            if (isShowProgress) {
                showProgressDialog(context);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HOST_NAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ICallServices service = retrofit.create(ICallServices.class);

            Call<ResponseReviewOrderData> response = service.getReviewOrder(orderCode);

            response.enqueue(new Callback<ResponseReviewOrderData>() {
                @Override
                public void onResponse(Call<ResponseReviewOrderData> call, Response<ResponseReviewOrderData> response) {
                    ResponseReviewOrderData res = response.body();
                    if (res == null) {
                        res = new ResponseReviewOrderData();
                        res.code = APIConstants.REQUEST_FAILED;
                    }

                    Message msg = Message.obtain();
                    msg.what = APIConstants.HANDLER_REQUEST_SERVER_SUCCESS;
                    msg.obj = res;
                    handler.sendMessage(msg);
                    closeDialog();
                }

                @Override
                public void onFailure(Call<ResponseReviewOrderData> call, Throwable t) {
                    handler.sendEmptyMessage(APIConstants.HANDLER_REQUEST_SERVER_FAILED);
                    closeDialog();
                }
            });
        } else {
            new DialogUtiils().showDialog(context, context.getString(R.string.network_not_avaiable), false);
        }
    }

    public void confirmOrder(Context context, long idPhieu, long idBan, String orderCode, final Handler handler, boolean isShowProgress)
    {
        if(NetworkUtils.isNetworkAvailable(context)) {
            if (isShowProgress) {
                showProgressDialog(context);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HOST_NAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ICallServices service = retrofit.create(ICallServices.class);

            Call<ResponseCommon> response = service.confirmOrder(idPhieu, idBan, orderCode, 1);

            response.enqueue(new Callback<ResponseCommon>() {
                @Override
                public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                    ResponseCommon res = response.body();
                    if (res == null) {
                        res = new ResponseReviewOrderData();
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
            new DialogUtiils().showDialog(context, context.getString(R.string.network_not_avaiable), false);
        }
    }

    public void getOrderForReturn(Context context, long idPhieu, final Handler handler, boolean isShowProgress)
    {
        if(NetworkUtils.isNetworkAvailable(context)) {
            if (isShowProgress) {
                showProgressDialog(context);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HOST_NAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ICallServices service = retrofit.create(ICallServices.class);

            Call<ResponseReturnOrderData> response = service.getOrderForReturn(idPhieu);

            response.enqueue(new Callback<ResponseReturnOrderData>() {
                @Override
                public void onResponse(Call<ResponseReturnOrderData> call, Response<ResponseReturnOrderData> response) {
                    ResponseReturnOrderData res = response.body();
                    if (res == null) {
                        res = new ResponseReturnOrderData();
                        res.code = APIConstants.REQUEST_FAILED;
                    }

                    Message msg = Message.obtain();
                    msg.what = APIConstants.HANDLER_REQUEST_SERVER_SUCCESS;
                    msg.obj = res;
                    handler.sendMessage(msg);
                    closeDialog();
                }

                @Override
                public void onFailure(Call<ResponseReturnOrderData> call, Throwable t) {
                    handler.sendEmptyMessage(APIConstants.HANDLER_REQUEST_SERVER_FAILED);
                    closeDialog();
                }
            });
        } else {
            new DialogUtiils().showDialog(context, context.getString(R.string.network_not_avaiable), false);
        }
    }

    public void submitReturnOrderTungMon(Context context, long idChiTietPhieu, long idPhieu, long idMon, String maMon, String tenMon, float soLuongTra,
                                   long donViTinhId, String mota, final Handler handler, boolean isShowProgress)
    {
        if(NetworkUtils.isNetworkAvailable(context)) {
            if (isShowProgress) {
                showProgressDialog(context);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HOST_NAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ICallServices service = retrofit.create(ICallServices.class);

            Call<ResponseCommon> response = service.submitReturnOrderTungMon(idChiTietPhieu, idPhieu, idMon, maMon, tenMon, soLuongTra, donViTinhId,
                                                                    mota, 3, 1, 3);

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
            new DialogUtiils().showDialog(context, context.getString(R.string.network_not_avaiable), false);
        }
    }

    public void confirmReturn(Context context, long idPhieu, String orderCode, final Handler handler, boolean isShowProgress)
    {
        if(NetworkUtils.isNetworkAvailable(context)) {
            if (isShowProgress) {
                showProgressDialog(context);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HOST_NAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ICallServices service = retrofit.create(ICallServices.class);

            Call<ResponseCommon> response = service.confirmReturn(idPhieu, orderCode, 1,"TRAHANG");

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
            new DialogUtiils().showDialog(context, context.getString(R.string.network_not_avaiable), false);
        }
    }
}
