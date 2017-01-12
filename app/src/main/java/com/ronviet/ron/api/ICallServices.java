package com.ronviet.ron.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by LENOVO on 1/8/2017.
 */

public interface ICallServices {

//    @GET("api/Common/GetListKhu/{ngon_ngu_id}")
//    Call<ResponseAreaInfoData> getAreaInfoTemp(@Field("trung_tam_id") long trungTamId, @Field("ngon_ngu_id") long ngonNguId, @Field("loai_hinh_kinh_doanh_id") long loaiHinhKinhDoanhId);

    @GET("/api/Common/GetListKhu")
    Call<ResponseAreaInfoData> getAreaInfo(@Query("trung_tam_id") long trungTamId, @Query("ngon_ngu_id") long ngonNguId, @Query("loai_hinh_kinh_doanh_id") long loaiHinhKinhDoanhId);

    @GET("/api/SoDoBan/GetSoDoBan")
    Call<ResponseTableInfoData> getTableInfo(@Query("khu_vuc_id") long khuId, @Query("ngon_ngu_id") long ngonNguId, @Query("trung_tam_id") long trungTamId);

    @GET("/api/SoDoBan/GetMaPhieu")
    Call<ResponseCreateMaPhieuData> getMaPhieu(@Query("loai_hinh_kinh_doanh_id") long loaiHinhKinhDoanhId, @Query("trung_tam_id") long trungTamId, @Query("quay_id") long quayId, @Query("ngay_ban_hang") String ngayBanHang,
                                           @Query("ban_hang_khac_ngay") boolean banHangKhacNgay);

    //@Query("ma_phieu") String maPhieu,
    @GET("/api/SoDoBan/GetTaoPhieu")
    Call<ResponseCreatePhieuData> getPhieuId(@Query("phieu_id") long phieuId, @Query("loai_hinh_kinh_doanh_id") long loaiHinhKinhDoanhId,
                                             @Query("nguoi_tao_phieu") String nguoiTaoPhieu, @Query("thu_ngan") String thuNgan,
                                             @Query("phuc_vu") String phucVu, @Query("quan_ly") String quanLi, @Query("khach_hang_id") long khachHangId,
                                             @Query("ma_khach_hang") String maKh, @Query("ten_khach_hang") String tenKh, @Query("so_luong_khach") int soluongKhachHang,
                                             @Query("la_phieu_ban_le") boolean isPhieuBanLe, @Query("co_hoa_don") boolean isCoHD, @Query("ghi_chu") String ghiChu,
                                             @Query("phan_tram_dich_vu") int phanTramDV, @Query("phan_tram_vat") int phanTramVAT, @Query("phan_tram_giam_phieu") int phanTramGiamPhieu,
                                             @Query("khu_id") long khuId, @Query("co_chuyen") boolean isCoChuyen, @Query("co_ghep") boolean isCoGhep,
                                             @Query("co_huy") boolean isCoHuy, @Query("co_goi_lai") boolean isCoGuiLai, @Query("co_giu_phieu") boolean isCoGiuPhieu,
                                             @Query("co_tiep_khach") boolean isCoTiepKhach, @Query("co_dat_coc") boolean isCoDatCoc, @Query("co_no") boolean isCoNo,
                                             @Query("tien_te_id") int tienTeId, @Query("quay_id") long quayId, @Query("trung_tam_id") long trungTamId,
                                             @Query("ban_id") long banId, @Query("ngay_ban_hang") String ngayBanHang, @Query("ban_hang_khac_ngay") boolean isBanHangNgayKhac,
                                             @Query("thoi_gian_tao_phieu") String thoiGianTaoPhieu, @Query("thoi_gian_sua_phieu") String thoiGianSuaPhieu, @Query("trang_thai") String trangThai,
                                             @Query("danh_muc_ca_id") long danhMucCaId, @Query("ma_ca") String maCa
                                                );

    @GET("/api/SoDoBan/GetDanhSachNhom")
    Call<ResponseProductCatData> getProductCategories(@Query("loai_hinh_kinh_doanh_id") long loaiHinhKinhDoanhId, @Query("trung_tam_id") long trungTamId, @Query("ngon_ngu_id") long ngonNguId, @Query("khu_vuc_id") long khuId,
                                               @Query("tien_te_id") long tienTeId,  @Query("loai_hang_hoa") int loaiHangHoa, @Query("cap_menu") int capMenu, @Query("ngay_ban_hang") String ngayBan);

    @GET("/api/SoDoBan/GetDanhSachMonAn")
    Call<ResponseProductData> getProducts(@Query("loai_hinh_kinh_doanh_id") long loaiHinhKinhDoanhId, @Query("khu_vuc_id") long khuId, @Query("ngay_ban_hang") String ngayBanHang, @Query("id_nhom") long idProductCat,
                                                      @Query("loai_tien_te") long tienTeId,  @Query("ngon_ngu_id") int ngonNguId, @Query("trung_tam_id") long trungTamId);

    @GET("/api/SoDoBan/GetMaOrder")
    Call<ResponseCreateOrderCodeData> getOrderCode(@Query("trung_tam_id") long trungTamId, @Query("loai_hinh_kinh_doanh_id") long loaiHinhKinhDanhId, @Query("quay_id") long quayId, @Query("ngay_ban_hang") String ngayBanHang, @Query("ban_hang_khac_ngay") boolean isBanHangNgayKhac);

    @FormUrlEncoded
    @POST("/api/SoDoBan/DatMon/")
    Call<ResponseCommon> submitOrderTungMon(@Field("order_id") long orderId, @Field("order_code") String orderCode, @Field("order_id_chi_tiet") long orderIdChiTietPhieu, @Field("id_phieu") long idPhieu,
                                        @Field("trang_thai") String trangThai, @Field("id_mon") long idMon, @Field("ma_mon") String maMon, @Field("ten_mon") String tenMon,
                                        @Field("so_luong") float soLuong, @Field("don_vi_tinh_id") long idDonViTinh, @Field("gia_goc") float giaGoc, @Field("don_gia") float donGia,
                                        @Field("gia_co_thue") boolean isGiaCoThue, @Field("thue") float thue, @Field("ma_may") int maMay, @Field("trung_tam_id") long trungTamId,
                                        @Field("loai_hinh_kinh_doanh_id") long loaiHinhKinhDoanhId, @Field("ngay_ban_hang") String ngayBanHang, @Field("id_ban") long idBan, @Field("yeu_cau_them") String yeuCauThem,
                                            @Field("is_mix") boolean isMix, @Field("id_mat_hang_cha") long idMatHangCha, @Field("nv_id") long nvId,
                                            @Field("user_fullname") long userFullName, @Field("ban_hang_khac_ngay") boolean banHangKhacNgay, @Field("loai_tien_te_id") long loaiTienTeId);

    @POST("/api/SoDoBan/XemOrderTruocSubmit")
    Call<ResponseReviewOrderData> getReviewOrder(@Field("order_code") String orderCode);

    @POST("")
    Call<ResponseCommon> confirmOrder(@Field("id_phieu") long idPhieu, @Field("id_ban") long idBan, @Field("order_code") String orderCode, @Field("id_nhan_vien") long isNhanVien);

    @POST("")
    Call<ResponseReturnOrderData> getOrderForReturn(@Field("id_phieu") long idPhieu);

    @POST("")
    Call<ResponseCommon> submitReturnOrderTungMon(@Field("id_chi_tiet_phieu") long idChiTietPhieu, @Field("id_phieu") long idPhieu,
                                                     @Field("id_mon") long idMon, @Field("ma_mon") String maMon,
                                                     @Field("ten_mon") String tenMon, @Field("so_luong_tra") float soLuongTra,
                                                     @Field("don_vi_tinh_id") long donViTinhId, @Field("mo_ta") String moTa,
                                                     @Field("trung_tam_id") long trungTamId, @Field("tien_te_id") long tienTeId,
                                                     @Field("pc_id") long pcId);


    @POST("")
    Call<ResponseCommon> confirmReturn(@Field("id_phieu") long idPhieu, @Field("order_code") String orderCode, @Field("nhan_vien_id") long nhanVienId, @Field("trang_thai") String trangThai);

}

