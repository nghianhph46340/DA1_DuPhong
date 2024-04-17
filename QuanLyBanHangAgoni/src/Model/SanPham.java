/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class SanPham {
    private String maSP, tenSP, mau, hang, chatLieu, mauSac, kichThuoc, nhaCungCap, hinhAnh, maSPKM, maMS, maKT,maCL, MaNCC, diaChiNCC, SDTNCC, emailNCC, maSPCT ;
    Boolean trangThai;
    private double donGia;
    private Integer soLuongSP;

    public SanPham() {
    }

    public SanPham(String maSP, String tenSP, String mau, String hang, String chatLieu, String mauSac, String kichThuoc, String nhaCungCap, String hinhAnh, String maSPKM, String maMS, String maKT, String maCL, String MaNCC, String diaChiNCC, String SDTNCC, String emailNCC, String maSPCT, Boolean trangThai, double donGia, Integer soLuongSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.mau = mau;
        this.hang = hang;
        this.chatLieu = chatLieu;
        this.mauSac = mauSac;
        this.kichThuoc = kichThuoc;
        this.nhaCungCap = nhaCungCap;
        this.hinhAnh = hinhAnh;
        this.maSPKM = maSPKM;
        this.maMS = maMS;
        this.maKT = maKT;
        this.maCL = maCL;
        this.MaNCC = MaNCC;
        this.diaChiNCC = diaChiNCC;
        this.SDTNCC = SDTNCC;
        this.emailNCC = emailNCC;
        this.maSPCT = maSPCT;
        this.trangThai = trangThai;
        this.donGia = donGia;
        this.soLuongSP = soLuongSP;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMau() {
        return mau;
    }

    public void setMau(String mau) {
        this.mau = mau;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMaSPKM() {
        return maSPKM;
    }

    public void setMaSPKM(String maSPKM) {
        this.maSPKM = maSPKM;
    }

    public String getMaMS() {
        return maMS;
    }

    public void setMaMS(String maMS) {
        this.maMS = maMS;
    }

    public String getMaKT() {
        return maKT;
    }

    public void setMaKT(String maKT) {
        this.maKT = maKT;
    }

    public String getMaCL() {
        return maCL;
    }

    public void setMaCL(String maCL) {
        this.maCL = maCL;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String MaNCC) {
        this.MaNCC = MaNCC;
    }

    public String getDiaChiNCC() {
        return diaChiNCC;
    }

    public void setDiaChiNCC(String diaChiNCC) {
        this.diaChiNCC = diaChiNCC;
    }

    public String getSDTNCC() {
        return SDTNCC;
    }

    public void setSDTNCC(String SDTNCC) {
        this.SDTNCC = SDTNCC;
    }

    public String getEmailNCC() {
        return emailNCC;
    }

    public void setEmailNCC(String emailNCC) {
        this.emailNCC = emailNCC;
    }

    public String getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(String maSPCT) {
        this.maSPCT = maSPCT;
    }

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public Integer getSoLuongSP() {
        return soLuongSP;
    }

    public void setSoLuongSP(Integer soLuongSP) {
        this.soLuongSP = soLuongSP;
    }

    

    @Override
    public String toString() {
        return "SanPham{" + "maSP=" + maSP + ", tenSP=" + tenSP + ", mau=" + mau + ", hang=" + hang + ", chatLieu=" + chatLieu + ", mauSac=" + mauSac + ", kichThuoc=" + kichThuoc + ", nhaCungCap=" + nhaCungCap + ", hinhAnh=" + hinhAnh + ", maSPKM=" + maSPKM + ", maMS=" + maMS + ", maKT=" + maKT + ", maCL=" + maCL + ", MaNCC=" + MaNCC + ", diaChiNCC=" + diaChiNCC + ", SDTNCC=" + SDTNCC + ", emailNCC=" + emailNCC + ", maSPCT=" + maSPCT + ", trangThai=" + trangThai + ", donGia=" + donGia + ", soLuongSP=" + soLuongSP + '}';
    }


    

    public void inThongTin() {
        System.out.println("SanPham{" + "maSP=" + maSP + ", tenSP=" + tenSP + ", mau=" + mau + ", hang=" + hang + ", chatLieu=" + chatLieu + ", mauSac=" + mauSac + ", kichThuoc=" + kichThuoc + ", nhaCungCap=" + nhaCungCap + ", hinhAnh=" + hinhAnh + ", maSPKM=" + maSPKM + ", donGia=" + donGia + ", soLuongSP=" + soLuongSP + '}');
    }
}
