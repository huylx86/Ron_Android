package com.ronviet.ron.api;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.ronviet.ron.R;
import com.ronviet.ron.utils.CommonUtils;
import com.ronviet.ron.utils.DialogUtiils;
import com.ronviet.ron.utils.NetworkUtils;
import com.ronviet.ron.utils.SharedPreferenceUtils;

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

    private String mHostName;
    private Context mContext;

    public SaleAPIHelper(Context context) {
        mContext = context;
        mHostName = getHostName(context);
    }
    public void getAreaInfo(final Handler handler, boolean isShowProgress)
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

            Call<ResponseAreaInfoData> response = service.getAreaInfo(ttId, ngonNgu, 1);

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
            new DialogUtiils().showDialog(mContext, mContext.getString(R.string.network_not_avaiable), false);
        }
    }

    public void getTableInfo(long areaId, final Handler handler, boolean isShowProgress)
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

            Call<ResponseTableInfoData> response = service.getTableInfo(areaId, ngonNgu, ttId);

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
            new DialogUtiils().showDialog(mContext, mContext.getString(R.string.network_not_avaiable), false);
        }
    }

    public void getMaPhieu(final Handler handler, boolean isShowProgress)
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

            Call<ResponseCreateMaPhieuData> response = service.getMaPhieu(1, ttId, 2, CommonUtils.convertDateFormat(new Date()), false);

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
            new DialogUtiils().showDialog(mContext, mContext.getString(R.string.network_not_avaiable), false);
        }
    }

    public void getIdPhieu(long khuId, long banId, final Handler handler, boolean isShowProgress)
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
            long caId = SharedPreferenceUtils.getCaId(mContext);
            String maCa = SharedPreferenceUtils.getMaCa(mContext);

            Call<ResponseCreatePhieuData> response = service.getPhieuId(-1000, 1, "huy", "aaa", "bbb", "", -1000, "", "", 1,
                                                        false, false, "", 0, 0, 0, khuId, false, false, false, false, false, false,
                                                        false, false, 1, 2, ttId, banId, CommonUtils.convertDateFormat(new Date()), false,
                                                        CommonUtils.convertDateFormat(new Date()), CommonUtils.convertDateFormat(new Date()),
                                                        "INSERT", caId, maCa);

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
            new DialogUtiils().showDialog(mContext, mContext.getString(R.string.network_not_avaiable), false);
        }
    }

    public void getProductCategories(long khuId, int loaiHangHoa, final Handler handler, boolean isShowProgress)
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

            Call<ResponseProductCatData> response = service.getProductCategories(1, ttId, ngonNgu, khuId, 1, loaiHangHoa, 1, CommonUtils.convertDateFormat(new Date()));

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
            new DialogUtiils().showDialog(mContext, mContext.getString(R.string.network_not_avaiable), false);
        }
    }

    public void getProducts(long khuId, long idProductCat, final Handler handler, boolean isShowProgress)
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

            Call<ResponseProductData> response = service.getProducts(1, khuId, CommonUtils.convertDateFormat(new Date()), idProductCat, 1, ngonNgu, ttId);

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
            new DialogUtiils().showDialog(mContext, mContext.getString(R.string.network_not_avaiable), false);
        }
    }

    public void getOrderCode(final Handler handler, boolean isShowProgress)
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

            Call<ResponseCreateOrderCodeData> response = service.getOrderCode(ttId, 1, 2, CommonUtils.convertDateFormat(new Date()), false);

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
            new DialogUtiils().showDialog(mContext, mContext.getString(R.string.network_not_avaiable), false);
        }
    }

    public void submitOrderTungMon(String orderCode, long idPhieu, long idMon, String maMon, String tenMon,
                               float soLuong, long donViTinhId, float giaGoc, float donGia, boolean giaCoThue, float thue,
                               long idBan, String yeuCauThem, String status, final Handler handler, boolean isShowProgress)
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
            long idMay = Long.parseLong(SharedPreferenceUtils.getIdMay(mContext));
            long nvId = SharedPreferenceUtils.getNhanVienId(mContext);

            Call<ResponseCommon> response = service.submitOrderTungMon(-1000, orderCode, -1000, idPhieu, status, idMon, maMon, tenMon, soLuong,
                                                            donViTinhId, giaGoc, donGia, giaCoThue, thue, idMay, ttId, 1, CommonUtils.convertDateFormat(new Date()),
                                                            idBan, yeuCauThem, false, -1, nvId, 3, false, 1);

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

    public void getReviewOrder(String orderCode, final Handler handler, boolean isShowProgress)
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
            new DialogUtiils().showDialog(mContext, mContext.getString(R.string.network_not_avaiable), false);
        }
    }

    public void confirmOrder(long idPhieu, long idBan, String orderCode, final Handler handler, boolean isShowProgress)
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

            long nvId = SharedPreferenceUtils.getNhanVienId(mContext);

            Call<ResponseCommon> response = service.confirmOrder(idPhieu, idBan, orderCode, nvId, "ORDER");

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
            new DialogUtiils().showDialog(mContext, mContext.getString(R.string.network_not_avaiable), false);
        }
    }

    public void khoiTaoTraHang(long idPhieu, final Handler handler, boolean isShowProgress)
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

            Call<ResponseCommon> response = service.khoiTaoTraHang(idPhieu);

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

    public void getOrderForReturn(long idPhieu, final Handler handler, boolean isShowProgress)
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
            new DialogUtiils().showDialog(mContext, mContext.getString(R.string.network_not_avaiable), false);
        }
    }

    public void submitReturnOrderTungMon(long idChiTietPhieu, long idPhieu, long idMon, String maMon, String tenMon, float soLuongTra,
                                   long donViTinhId, String mota, final Handler handler, boolean isShowProgress)
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
            long idMay = Long.parseLong(SharedPreferenceUtils.getIdMay(mContext));

            Call<ResponseCommon> response = service.submitReturnOrderTungMon(idChiTietPhieu, idPhieu, idMon, maMon, tenMon, soLuongTra, donViTinhId,
                                                                    mota, ttId, 1, idMay, "TRAHANG", 1);

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

    public void confirmReturn(long idPhieu, String orderCode, final Handler handler, boolean isShowProgress)
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

            long nvId = SharedPreferenceUtils.getNhanVienId(mContext);

            Call<ResponseCommon> response = service.confirmReturn(idPhieu, orderCode, nvId,"TRAHANG");

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

    public void deleteOrderAll(long idPhieu, String orderCode, final Handler handler, boolean isShowProgress)
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

            Call<ResponseCommon> response = service.deleteOrderAll(orderCode, idPhieu, 1);

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

    public void deleteOrderTungMon(long idPhieu, long orderId, long orderIdChiTiet, final Handler handler, boolean isShowProgress)
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

            Call<ResponseCommon> response = service.deleteOrderTungMon(orderId, idPhieu, orderIdChiTiet, false, false);

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

    public void cancelReturnOrder(long idPhieu, final Handler handler, boolean isShowProgress)
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

            Call<ResponseCommon> response = service.cancelReturnOrder(idPhieu);

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

    public void chuyenBan(long idPhieu, long idBanCu, long idBanMoi, final Handler handler, boolean isShowProgress)
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
            String tenNV = SharedPreferenceUtils.getTenNhanVien(mContext);
            long nvId = SharedPreferenceUtils.getNhanVienId(mContext);

            Call<ResponseCommon> response = service.chuyenBan(idPhieu, idBanCu, idBanMoi, tenNV, nvId, "Nguyen Van A", ttId, ngonNgu);

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
