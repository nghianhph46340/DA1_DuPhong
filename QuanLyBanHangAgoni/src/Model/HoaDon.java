/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author NGHIAPC
 */
public class HoaDon {

    String maHoaDon;
    String ngayTao;
    String TrangThai;
    String maVoucher;
    String maNhanVien;
    String ngayHoanThanh;
    String loaiThanhToan;
    String maKhachHang;

    public HoaDon() {
    }

    public HoaDon(String maHoaDon, String ngayTao, String TrangThai, String maVoucher, String maNhanVien, String ngayHoanThanh, String loaiThanhToan, String maKhachHang) {
        this.maHoaDon = maHoaDon;
        this.ngayTao = ngayTao;
        this.TrangThai = TrangThai;
        this.maVoucher = maVoucher;
        this.maNhanVien = maNhanVien;
        this.ngayHoanThanh = ngayHoanThanh;
        this.loaiThanhToan = loaiThanhToan;
        this.maKhachHang = maKhachHang;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getNgayHoanThanh() {
        return ngayHoanThanh;
    }

    public void setNgayHoanThanh(String ngayHoanThanh) {
        this.ngayHoanThanh = ngayHoanThanh;
    }

    public String getLoaiThanhToan() {
        return loaiThanhToan;
    }

    public void setLoaiThanhToan(String loaiThanhToan) {
        this.loaiThanhToan = loaiThanhToan;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public HoaDon(String maHoaDon, String ngayTao, String TrangThai, String maNhanVien, String loaiThanhToan) {
        this.maHoaDon = maHoaDon;
        this.ngayTao = ngayTao;
        this.TrangThai = TrangThai;
        this.maNhanVien = maNhanVien;
        this.loaiThanhToan = loaiThanhToan;
    }

    @Override
    public String toString() {
        return "HoaDon{" + "maHoaDon=" + maHoaDon + ", ngayTao=" + ngayTao + ", TrangThai=" + TrangThai + ", maVoucher=" + maVoucher + ", maNhanVien=" + maNhanVien + ", ngayHoanThanh=" + ngayHoanThanh + ", loaiThanhToan=" + loaiThanhToan + ", maKhachHang=" + maKhachHang + '}';
    }

    public void inThongTin() {
        System.out.println("HoaDon{" + "maHoaDon=" + maHoaDon + ", ngayTao=" + ngayTao + ", TrangThai=" + TrangThai + ", maVoucher=" + maVoucher + ", maNhanVien=" + maNhanVien + ", ngayHoanThanh=" + ngayHoanThanh + ", loaiThanhToan=" + loaiThanhToan + ", maKhachHang=" + maKhachHang + '}');
    }

}
