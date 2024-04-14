/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class KhuyenMai {
    private String maKM, tenKM, ngayBatDauKM, hanSuDungKM, maCTSP;
    private Double giamGia;

    public KhuyenMai() {
    }

    public KhuyenMai(String maKM, String tenKM, String ngayBatDauKM, String hanSuDungKM, String maCTSP, Double giamGia) {
        this.maKM = maKM;
        this.tenKM = tenKM;
        this.ngayBatDauKM = ngayBatDauKM;
        this.hanSuDungKM = hanSuDungKM;
        this.maCTSP = maCTSP;
        this.giamGia = giamGia;
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getTenKM() {
        return tenKM;
    }

    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }

    public String getNgayBatDauKM() {
        return ngayBatDauKM;
    }

    public void setNgayBatDauKM(String ngayBatDauKM) {
        this.ngayBatDauKM = ngayBatDauKM;
    }

    public String getHanSuDungKM() {
        return hanSuDungKM;
    }

    public void setHanSuDungKM(String hanSuDungKM) {
        this.hanSuDungKM = hanSuDungKM;
    }

    public String getMaCTSP() {
        return maCTSP;
    }

    public void setMaCTSP(String maCTSP) {
        this.maCTSP = maCTSP;
    }

    public Double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(Double giamGia) {
        this.giamGia = giamGia;
    }

    
    
}
