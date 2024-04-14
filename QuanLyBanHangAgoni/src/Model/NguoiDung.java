/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author NGHIAPC
 */
public class NguoiDung {

    String maNguoiDung;
    String tenNguoiDung;
    boolean gioiTinh;
    int tuoi;
    String SDT;
    String Email;
    String Roles;
    String tenDN;
    String passWord;
    boolean trangThai;

    public NguoiDung() {
    }

    public NguoiDung(String maNguoiDung, String tenNguoiDung, boolean gioiTinh, int tuoi, String SDT, String Email, String Roles, String tenDN, String passWord, boolean trangThai) {
        this.maNguoiDung = maNguoiDung;
        this.tenNguoiDung = tenNguoiDung;
        this.gioiTinh = gioiTinh;
        this.tuoi = tuoi;
        this.SDT = SDT;
        this.Email = Email;
        this.Roles = Roles;
        this.tenDN = tenDN;
        this.passWord = passWord;
        this.trangThai = trangThai;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getRoles() {
        return Roles;
    }

    public void setRoles(String Roles) {
        this.Roles = Roles;
    }

    public String getTenDN() {
        return tenDN;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "NguoiDung{" + "maNguoiDung=" + maNguoiDung + ", tenNguoiDung=" + tenNguoiDung + ", gioiTinh=" + gioiTinh + ", tuoi=" + tuoi + ", SDT=" + SDT + ", Email=" + Email + ", Roles=" + Roles + ", tenDN=" + tenDN + ", passWord=" + passWord + ", trangThai=" + trangThai + '}';
    }

    public void inInf() {
        System.out.println("NguoiDung{" + "maNguoiDung=" + maNguoiDung + ", tenNguoiDung=" + tenNguoiDung + ", gioiTinh=" + gioiTinh + ", tuoi=" + tuoi + ", SDT=" + SDT + ", Email=" + Email + ", Roles=" + Roles + ", tenDN=" + tenDN + ", passWord=" + passWord + ", trangThai=" + trangThai + '}');
    }

    public NguoiDung(String tenDN) {
        this.tenDN = tenDN;
    }

}
