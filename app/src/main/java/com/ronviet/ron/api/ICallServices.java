package com.ronviet.ron.api;

import retrofit2.Call;
import retrofit2.http.Field;
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

    @POST("")
    Call<ResponseCreateMaPhieuData> getMaPhieu(@Field("loai_hinh_kinh_doanh_id") long loaiHinhKinhDoanhId, @Field("trung_tam_id") long trungTamId, @Field("quay_id") long quayId, @Field("ngay_ban_hang") String ngayBanHang,
                                           @Field("ban_hang_khac_ngay") boolean banHangKhacNgay);

    @POST("")
    Call<ResponseCreatePhieuData> getPhieuId(@Field("phieu_id") long phieuId, @Field("ma_phieu") String maPhieu, @Field("loai_hinh_kinh_doanh_id") long loaiHinhKinhDoanhId,
                                             @Field("nguoi_tao_phieu") String nguoiTaoPhieu, @Field("thu_ngan") String thuNgan,
                                             @Field("phuc_vu") String phucVu, @Field("quan_li") String quanLi, @Field("khach_hang_id") long khachHangId,
                                             @Field("ma_khach_hang") String maKh, @Field("ten_khach_hang") String tenKh, @Field("so_luong_khach") int soluongKhachHang,
                                             @Field("la_phieu_ban_le") boolean isPhieuBanLe, @Field("co_hoa_don") boolean isCoHD, @Field("ghi_chu") String ghiChu,
                                             @Field("phan_tram_dich_vu") int phanTramDV, @Field("phan_tram_vat") int phanTramVAT, @Field("phan_tram_giam_phieu") int phanTramGiamPhieu,
                                             @Field("khu_id") long khuId, @Field("co_chuyen") boolean isCoChuyen, @Field("co_ghep") boolean isCoGhep,
                                             @Field("co_huy") boolean isCoHuy, @Field("co_goi_lai") boolean isCoGuiLai, @Field("co_giu_phieu") boolean isCoGiuPhieu,
                                             @Field("co_tiep_khach") boolean isCoTiepKhach, @Field("co_dat_coc") boolean isCoDatCoc, @Field("co_no") boolean isCoNo,
                                             @Field("tien_te_id") int tienTeId, @Field("quay_id") long quayId, @Field("trung_tam_id") long trungTamId,
                                             @Field("ban_id") long banId, @Field("ngay_ban_hang") String ngayBanHang, @Field("ban_hang_khac_ngay") boolean isBanHangNgayKhac,
                                             @Field("thoi_gian_tao_phieu") String thoiGianTaoPhieu, @Field("thoi_gian_sua_phieu") String thoiGianSuaPhieu, @Field("trang_thai") String trangThai,
                                             @Field("danh_muc_ca_id") long danhMucCaId, @Field("ma_ca") String maCa
                                                );

    @GET("/api/SoDoBan/GetDanhSachNhom")
    Call<ResponseProductCatData> getProductCategories(@Query("loai_hinh_kinh_doanh_id") long loaiHinhKinhDoanhId, @Query("trung_tam_id") long trungTamId, @Query("ngon_ngu_id") long ngonNguId, @Query("khu_vuc_id") long khuId,
                                               @Query("tien_te_id") long tienTeId,  @Query("loai_hang_hoa") int loaiHangHoa, @Query("cap_menu") int capMenu, @Query("ngay_ban_hang") String ngayBan);

    @GET("/api/SoDoBan/GetDanhSachMonAn")
    Call<ResponseProductData> getProducts(@Query("loai_hinh_kinh_doanh_id") long loaiHinhKinhDoanhId, @Query("khu_vuc_id") long khuId, @Query("ngay_ban_hang") String ngayBanHang, @Query("id_nhom") long idProductCat,
                                                      @Query("loai_tien_te") long tienTeId,  @Query("ngon_ngu_id") int ngonNguId, @Query("trung_tam_id") long trungTamId);

    @POST("")
    Call<ResponseCreateOrderCodeData> getOrderCode(@Field("trung_tam_id") long trungTamId, @Field("loai_hinh_kinh_doanh_id") long loaiHinhKinhDanhId, @Field("ngay_ban_hang") String ngayBanHang, @Field("ban_hang_khac_ngay") boolean isBanHangNgayKhac);

    @POST("")
    Call<ResponseCommon> submitOrderTungMon(@Field("order_id") long orderId, @Field("order_code") String orderCode, @Field("order_id_chi_tiet") long orderIdChiTietPhieu, @Field("id_phieu") long idPhieu,
                                        @Field("trang_thai") String trangThai, @Field("id_mon") long idMon, @Field("ma_mon") String maMon, @Field("ten_mon") String tenMon,
                                        @Field("so_luong") float soLuong, @Field("don_vi_tinh_id") long idDonViTinh, @Field("gia_goc") float giaGoc, @Field("don_gia") float donGia,
                                        @Field("gia_co_thue") boolean isGiaCoThue, @Field("thue") float thue, @Field("ma_may") int maMay, @Field("trung_tam_id") long trungTamId,
                                        @Field("loai_hinh_kinh_doanh_id") long loaiHinhKinhDoanhId, @Field("ngay_ban_hang") String ngayBanHang, @Field("id_ban") long idBan, @Field("yeu_cau_them") String yeuCauThem);

    @POST("")
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

