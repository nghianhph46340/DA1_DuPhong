/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author NGHIAPC
 */
public class LichSuGia {

    String maDonGia;
    String maSanPham;
    String ngayBatDau;
    String ngayKetThuc;
    double giaDau;
    double giaSau;

    public LichSuGia() {
    }

    public LichSuGia(String maDonGia, String maSanPham, String ngayBatDau, String ngayKetThuc, double giaDau, double giaSau) {
        this.maDonGia = maDonGia;
        this.maSanPham = maSanPham;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.giaDau = giaDau;
        this.giaSau = giaSau;
    }

    public String getMaDonGia() {
        return maDonGia;
    }

    public void setMaDonGia(String maDonGia) {
        this.maDonGia = maDonGia;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public double getGiaDau() {
        return giaDau;
    }

    public void setGiaDau(double giaDau) {
        this.giaDau = giaDau;
    }

    public double getGiaSau() {
        return giaSau;
    }

    public void setGiaSau(double giaSau) {
        this.giaSau = giaSau;
    }

}
