/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author NGHIAPC
 */
public class LichSuGia {

    String maDonGia;
    String maSPCT;
    String TenSP;
    String maSanPham;
    String ngayBatDau;
    String ngayKetThuc;
    double giaDau;
    double giaSau;
    String startTime;
    String endTime;

    public LichSuGia() {
    }

    public LichSuGia(String maDonGia, String maSPCT, String TenSP, String maSanPham, String ngayBatDau, String ngayKetThuc, double giaDau, double giaSau, String startTime, String endTime) {
        this.maDonGia = maDonGia;
        this.maSPCT = maSPCT;
        this.TenSP = TenSP;
        this.maSanPham = maSanPham;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.giaDau = giaDau;
        this.giaSau = giaSau;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getMaDonGia() {
        return maDonGia;
    }

    public void setMaDonGia(String maDonGia) {
        this.maDonGia = maDonGia;
    }

    public String getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(String maSPCT) {
        this.maSPCT = maSPCT;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String TenSP) {
        this.TenSP = TenSP;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    
   
 
  
}
