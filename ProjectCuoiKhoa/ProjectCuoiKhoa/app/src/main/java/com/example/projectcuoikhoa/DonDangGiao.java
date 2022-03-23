package com.example.projectcuoikhoa;

import java.io.Serializable;
import java.util.List;

public class DonDangGiao implements Serializable {
    Address ThongTinNhanHang;
    List<GioHang> GioHangList;
    long TongTien, TongTienHang;
    String NgayTao;

    public DonDangGiao(Address thongTinNhanHang, List<GioHang> gioHangList, long tongTien, long tongTienHang, String ngayTao) {
        ThongTinNhanHang = thongTinNhanHang;
        GioHangList = gioHangList;
        TongTien = tongTien;
        TongTienHang = tongTienHang;
        NgayTao = ngayTao;
    }

    public DonDangGiao() {
    }

    public Address getThongTinNhanHang() {
        return ThongTinNhanHang;
    }

    public void setThongTinNhanHang(Address thongTinNhanHang) {
        ThongTinNhanHang = thongTinNhanHang;
    }

    public List<GioHang> getGioHangList() {
        return GioHangList;
    }

    public void setGioHangList(List<GioHang> gioHangList) {
        GioHangList = gioHangList;
    }

    public long getTongTien() {
        return TongTien;
    }

    public void setTongTien(long tongTien) {
        TongTien = tongTien;
    }

    public long getTongTienHang() {
        return TongTienHang;
    }

    public void setTongTienHang(long tongTienHang) {
        TongTienHang = tongTienHang;
    }

    public String getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(String ngayTao) {
        NgayTao = ngayTao;
    }
}
