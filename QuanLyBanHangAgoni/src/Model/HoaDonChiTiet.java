/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author NGHIAPC
 */
public class HoaDonChiTiet {

    String maHoaDon;
    String maHoaDonChiTiet;
    Integer soLuong;
    double donGia;
    double donGiaSau;
    String maSanPham;
    String tenSanPham;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(String maHoaDon, String maHoaDonChiTiet, Integer soLuong, double donGia, double donGiaSau, String maSanPham, String tenSanPham) {
        this.maHoaDon = maHoaDon;
        this.maHoaDonChiTiet = maHoaDonChiTiet;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.donGiaSau = donGiaSau;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaHoaDonChiTiet() {
        return maHoaDonChiTiet;
    }

    public void setMaHoaDonChiTiet(String maHoaDonChiTiet) {
        this.maHoaDonChiTiet = maHoaDonChiTiet;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getDonGiaSau() {
        return donGiaSau;
    }

    public void setDonGiaSau(double donGiaSau) {
        this.donGiaSau = donGiaSau;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public HoaDonChiTiet(String maHoaDon, Integer soLuong, double donGia, double donGiaSau, String maSanPham, String tenSanPham) {
        this.maHoaDon = maHoaDon;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.donGiaSau = donGiaSau;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
    }

    public HoaDonChiTiet(String maHoaDon, Integer soLuong, String maSanPham) {
        this.maHoaDon = maHoaDon;
        this.soLuong = soLuong;
        this.maSanPham = maSanPham;
    }
    

    @Override
    public String toString() {
        return "HoaDonChiTiet{" + "maHoaDon=" + maHoaDon + ", maHoaDonChiTiet=" + maHoaDonChiTiet + ", soLuong=" + soLuong + ", donGia=" + donGia + ", donGiaSau=" + donGiaSau + ", maSanPham=" + maSanPham + ", tenSanPham=" + tenSanPham + '}';
    }

    public void inThonTin() {
        System.out.println("HoaDonChiTiet{" + "maHoaDon=" + maHoaDon + ", maHoaDonChiTiet=" + maHoaDonChiTiet + ", soLuong=" + soLuong + ", donGia=" + donGia + ", donGiaSau=" + donGiaSau + ", maSanPham=" + maSanPham + ", tenSanPham=" + tenSanPham + '}');
    }
}
