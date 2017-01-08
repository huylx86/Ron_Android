package com.ronviet.ron.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by LENOVO on 1/8/2017.
 */

public interface ICallServices {

    @FormUrlEncoded
    @POST("")
    Call<ResponseAreaInfoData> getAreaInfo(@Field("trung_tam_id") long trungTamId, @Field("ngon_ngu_id") long ngonNguId, @Field("loai_hinh_kinh_doanh_id") long loaiHinhKinhDoanhId);

    @POST("")
    Call<ResponseTableInfoData> getTableInfo(@Field("khu_vuc_id") long khuId, @Field("ngon_ngu_id") long ngonNguId, @Field("trung_tam_id") long trungTamId);

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

    @POST("")
    Call<ResponseProductCatData> getProductCategories(@Field("loai_hinh_kinh_doanh_id") long loaiHinhKinhDoanhId, @Field("trung_tam_id") long trungTamId, @Field("ngon_ngu_id") long ngonNguId, @Field("khu_vuc_id") long khuId,
                                               @Field("tien_te_id") long tienTeId,  @Field("loai_hang_hoa") int loaiHangHoa);

    @POST("")
    Call<ResponseProductData> getProducts(@Field("loai_hinh_kinh_doanh_id") long loaiHinhKinhDoanhId, @Field("khu_vuc_id") long khuId, @Field("ngay_ban_hang") String ngayBanHang, @Field("id_nhom") long idProductCat,
                                                      @Field("loai_tien_te") long tienTeId,  @Field("ngon_ngu_id") int ngonNguId);
}

