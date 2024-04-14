/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class Voucher {
    private String maVoucher, tenVoucher, hanSuDungVC, ngayBatDauVC;
    private Integer soLuongVC;
    private Double soTienGiam, soTienYeuCau;

    public Voucher() {
    }

    public Voucher(String maVoucher, String tenVoucher, String hanSuDungVC, String ngayBatDauVC, Integer soLuongVC, Double soTienGiam, Double soTienYeuCau) {
        this.maVoucher = maVoucher;
        this.tenVoucher = tenVoucher;
        this.hanSuDungVC = hanSuDungVC;
        this.ngayBatDauVC = ngayBatDauVC;
        this.soLuongVC = soLuongVC;
        this.soTienGiam = soTienGiam;
        this.soTienYeuCau = soTienYeuCau;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public String getTenVoucher() {
        return tenVoucher;
    }

    public void setTenVoucher(String tenVoucher) {
        this.tenVoucher = tenVoucher;
    }

    public String getHanSuDungVC() {
        return hanSuDungVC;
    }

    public void setHanSuDungVC(String hanSuDungVC) {
        this.hanSuDungVC = hanSuDungVC;
    }

    public String getNgayBatDauVC() {
        return ngayBatDauVC;
    }

    public void setNgayBatDauVC(String ngayBatDauVC) {
        this.ngayBatDauVC = ngayBatDauVC;
    }

    public Integer getSoLuongVC() {
        return soLuongVC;
    }

    public void setSoLuongVC(Integer soLuongVC) {
        this.soLuongVC = soLuongVC;
    }

    public Double getSoTienGiam() {
        return soTienGiam;
    }

    public void setSoTienGiam(Double soTienGiam) {
        this.soTienGiam = soTienGiam;
    }

    public Double getSoTienYeuCau() {
        return soTienYeuCau;
    }

    public void setSoTienYeuCau(Double soTienYeuCau) {
        this.soTienYeuCau = soTienYeuCau;
    }
    
    
}
