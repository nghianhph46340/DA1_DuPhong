/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model.ChiTietHoaDon;
import Model.HoaDon;
import Model.HoaDonChiTiet;
import Model.NguoiDung;
import Model.KhuyenMai;
import Model.LichSuGia;
import Model.SanPham;
import Model.Voucher;
import Service.ServiceImp;
import Service.ServiceInterface;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NGHIAPC
 */
public class ViewTrangChu_QuanLy extends javax.swing.JFrame {

    /**
     * Creates new form ViewTrangChu
     */
    ServiceInterface ser = new ServiceImp();
    DefaultTableModel dtm;
    ArrayList<SanPham> listSPTrong = new ArrayList<>();
    ArrayList<SanPham> listsp1 = new ArrayList<>();
    ArrayList<HoaDon> listHoaDon = new ArrayList<>();
    ArrayList<LichSuGia> listLSGTrong = new ArrayList<>();
    DecimalFormat df = new DecimalFormat("#");
    Double tongDoanhThu = ser.tongDoanhThuMD();
    String formattedResult = df.format(tongDoanhThu);
    JFileChooser dlg = new JFileChooser();
    String linkAnh = "";
    String linkAnhSet = "";

    public ViewTrangChu_QuanLy() {
        initComponents();
        this.setLocationRelativeTo(null);
        loadDataVoucher(ser.getAllVoucher());
        loadDataNhanVien(ser.getAllNhanVien(true));
        loadDataKhuyenMai(ser.getAllKhuyenMai());
        loadDataSPKM(ser.getAllSanPhamKM());
        loadDataKMChonSP(ser.getAllKhuyenMai());
        loadDataQuanLy(ser.getAllQuanLy());
        loadDataNhanVienNghi(ser.getAllNhanVien(false));
        loadDataQuanLyHD(ser.getAllQuanLyHD());
        loadDataQuanLyHDHuy(ser.getAllQLHDHuy());
        rdAllVoucher.setSelected(true);
        loadDataQLSP(ser.getAllSanPhamCT());
        loadDataLichSuGia(ser.getAllLichSuDonGia());
        loadDataSanPhamTTSP(ser.getAllSanPhamTTSP());
        showCboMauSacTT();
        showCboKichThuoc();
        showCboChatLieu();
        showCboNCC();
        showCboMau();
        showCboSPCT();
        loadDataMauSac();
        loadDataChatLieu();
        loadDataKichThuoc();
        loadDataChiTietSP(ser.getAllSanPham());
        loadDaTaChiTietHD(ser.getALlCTHD());
        txtTongDonHang.setText(String.valueOf(ser.tongHoaDonMD()));
        txtTongDoanhThu.setText(formattedResult);
        txtTongDoanhSoBanHang.setText(String.valueOf(ser.tongDoanhSoMD()));
        lblThanhCong.setText(String.valueOf(ser.tongHoaDonThanhToan()));
        lblCTT.setText(String.valueOf(ser.tongHoaDonMD() - ser.tongHoaDonThanhToan()));
        cboMauTTSP.setEnabled(false);
        txtTenSPSPCT.setEditable(false);
        txtHang.setEditable(false);

    }

    public static String generateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    void loadDaTaChiTietHD(ArrayList<ChiTietHoaDon> list) {
        dtm = (DefaultTableModel) tblChiTietHoaDon.getModel();
        dtm.setRowCount(0);
        for (ChiTietHoaDon cthd : list) {
            dtm.addRow(new Object[]{
                cthd.getMaHoaDon(),
                cthd.getNgayTao(),
                cthd.getTrangThai(),
                cthd.getMaVoucher(),
                cthd.getMaNhanVien(),
                cthd.getNgayHoanThanh(),
                cthd.getLoaiThanhToan(),
                cthd.getMaSanPhamChiTiet(),
                cthd.getSoLuong()

            });
        }
    }

    void loadDataChiTietSP(ArrayList<SanPham> list) {
        dtm = (DefaultTableModel) tblThongTinChiTietSanPham.getModel();
        dtm.setRowCount(0);
        for (SanPham sp : list) {
            dtm.addRow(new Object[]{
                sp.getMaSP(), sp.getSoLuongSP(), sp.getKichThuoc(), sp.getMauSac(), sp.getDonGia(), sp.getNhaCungCap(), sp.getChatLieu()
            });
        }
    }

    void loadDataVoucher(ArrayList<Voucher> list) {
        dtm = (DefaultTableModel) tblVoucher.getModel();
        dtm.setRowCount(0);
        for (Voucher voucher : list) {
            dtm.addRow(new Object[]{
                voucher.getMaVoucher(),
                voucher.getTenVoucher(),
                voucher.getSoLuongVC(),
                voucher.getHanSuDungVC(),
                voucher.getNgayBatDauVC(),
                voucher.getSoTienGiam(),
                voucher.getSoTienYeuCau()
            });
        }
    }

    Voucher getFormVoucher() {
        Voucher vc = new Voucher();
        vc.setMaVoucher(txtMaVoucher.getText());
        vc.setTenVoucher(txtTenVoucher.getText());
        if (txtSoLuongVoucher.getText() == "" || txtSoLuongVoucher.getText() == "0") {
            vc.setSoLuongVC(null);
        } else {
            vc.setSoLuongVC(Integer.parseInt(txtSoLuongVoucher.getText()));
        }
        vc.setNgayBatDauVC(txtNBatDauVoucher.getText());
        vc.setHanSuDungVC(txtNKetThucVoucher.getText());
        if (txtSoTienGiamVoucher.getText() == "" || txtSoTienGiamVoucher.getText() == "0") {
            vc.setSoTienGiam(null);
        } else {
            vc.setSoTienGiam(Double.parseDouble(txtSoTienGiamVoucher.getText()));
        }
        if (txtSoTienYCVoucher.getText() == "" || txtSoTienYCVoucher.getText() == "0") {
            vc.setSoTienYeuCau(null);
        } else {
            vc.setSoTienYeuCau(Double.parseDouble(txtSoTienYCVoucher.getText()));
        }
        return vc;
    }

    void setFormVoucher(int roww) {
        txtMaVoucher.setText(String.valueOf(tblVoucher.getValueAt(roww, 0)));
        txtTenVoucher.setText(String.valueOf(tblVoucher.getValueAt(roww, 1)));
        if (tblVoucher.getValueAt(roww, 2).equals(0)) {
            txtSoLuongVoucher.setText("0");
        } else {
            txtSoLuongVoucher.setText(String.valueOf(tblVoucher.getValueAt(roww, 2)));
        }
        txtNBatDauVoucher.setText(String.valueOf(tblVoucher.getValueAt(roww, 3)));
        txtNKetThucVoucher.setText(String.valueOf(tblVoucher.getValueAt(roww, 4)));
        if (tblVoucher.getValueAt(roww, 5).equals(0.0)) {
            txtSoTienGiamVoucher.setText("0");
        } else {
            txtSoTienGiamVoucher.setText(String.valueOf(tblVoucher.getValueAt(roww, 5)));
        }
        if (tblVoucher.getValueAt(roww, 6).equals(0.0)) {
            txtSoTienYCVoucher.setText("0");
        } else {
            txtSoTienYCVoucher.setText(String.valueOf(tblVoucher.getValueAt(roww, 6)));
        }
    }

    void loadDataNhanVien(ArrayList<NguoiDung> list) {
        dtm = (DefaultTableModel) tblNhanVien.getModel();
        dtm.setRowCount(0);
        for (NguoiDung nd : list) {
            dtm.addRow(new Object[]{
                nd.getMaNguoiDung(),
                nd.getTenNguoiDung(),
                nd.isGioiTinh() ? "Nam" : "Nữ",
                nd.getTuoi(),
                nd.getSDT(),
                nd.getEmail(),
                nd.getRoles(),
                nd.getTenDN(),
                nd.getPassWord(),
                nd.isTrangThai() ? "Đang hoạt động" : "Không hoạt động"
            });
        }
    }

    NguoiDung getFormNhanVien() {
        NguoiDung nd = new NguoiDung();
        nd.setMaNguoiDung(txtMaNV.getText().trim());
        nd.setTenNguoiDung(txtTenNV.getText().trim());
        boolean gioiTinh;
        if (rdNam.isSelected()) {
            gioiTinh = true;
        } else {
            gioiTinh = false;
        }
        nd.setGioiTinh(gioiTinh);
        nd.setTuoi(Integer.valueOf(txtTuoi.getText().trim()));
        nd.setSDT(txtSDT.getText().trim());
        nd.setEmail(txtEmail.getText().trim());
        nd.setRoles(lblRoles.getText().trim());
        nd.setTenDN(txtTenDN.getText().trim());
        nd.setPassWord(generateMD5(txtPassword.getText().trim()));
        return nd;
    }

    NguoiDung getFormNhanVienEmail() {
        NguoiDung nd = new NguoiDung();
        nd.setMaNguoiDung(txtMaNV.getText().trim());
        nd.setTenNguoiDung(txtTenNV.getText().trim());
        boolean gioiTinh;
        if (rdNam.isSelected()) {
            gioiTinh = true;
        } else {
            gioiTinh = false;
        }
        nd.setGioiTinh(gioiTinh);
        nd.setTuoi(Integer.valueOf(txtTuoi.getText().trim()));
        nd.setSDT(txtSDT.getText().trim());
        nd.setEmail(txtEmail.getText().trim());
        nd.setRoles(lblRoles.getText().trim());
        nd.setTenDN(txtTenDN.getText().trim());
        nd.setPassWord(txtPassword.getText().trim());
        return nd;
    }

    void setFormNhanVien(NguoiDung nd) {
        txtMaNV.setText(nd.getMaNguoiDung());
        txtTenNV.setText(nd.getTenNguoiDung());
        Boolean gioiTinh = nd.isGioiTinh();
        if (gioiTinh) {
            rdNam.setSelected(true);
        } else {
            rdNu.setSelected(true);
        }
        txtTuoi.setText(nd.getTuoi() + "");
        txtSDT.setText(nd.getSDT());
        txtEmail.setText(nd.getEmail());
        lblRoles.setText(nd.getRoles());
        txtTenDN.setText(nd.getTenDN());
        txtPassword.setText(nd.getPassWord());
    }

    void loadDataKhuyenMai(ArrayList<KhuyenMai> list) {
        dtm = (DefaultTableModel) tblKhuyenMai.getModel();
        dtm.setRowCount(0);
        for (KhuyenMai khuyenMai : list) {
            dtm.addRow(new Object[]{
                khuyenMai.getMaKM(),
                khuyenMai.getTenKM(),
                khuyenMai.getHanSuDungKM(),
                khuyenMai.getNgayBatDauKM(),
                khuyenMai.getGiamGia(),
                khuyenMai.getMaCTSP()
            });
        }
    }

    KhuyenMai getFormKhuyenMai() {
        KhuyenMai km = new KhuyenMai();
        km.setMaKM(txtMaKhuyenMai.getText());
        km.setTenKM(txtTenKhuyenMai.getText());
        km.setNgayBatDauKM(txtNBatDauKhuyenMai.getText());
        km.setHanSuDungKM(txtNKetThucKhuyenMai.getText());
        if (txtGiamGiaKhuyenMai.getText().equals("")) {
            km.setGiamGia(0.0);
        } else {
            km.setGiamGia(Double.parseDouble(txtGiamGiaKhuyenMai.getText()));
        }

        return km;
    }

    void setFormKhuyenMai(int row) {
        txtMaKhuyenMai.setText(tblKhuyenMai.getValueAt(row, 0) + "");
        txtTenKhuyenMai.setText(tblKhuyenMai.getValueAt(row, 1) + "");
        txtNBatDauKhuyenMai.setText(tblKhuyenMai.getValueAt(row, 2) + "");
        txtNKetThucKhuyenMai.setText(tblKhuyenMai.getValueAt(row, 3) + "");
        if (tblKhuyenMai.getValueAt(row, 4).equals(0.0)) {
            txtGiamGiaKhuyenMai.setText("0");
        } else {
            txtGiamGiaKhuyenMai.setText(tblKhuyenMai.getValueAt(row, 4) + "");
        }
    }

    void loadDataSPKM(ArrayList<SanPham> list) {
        dtm = (DefaultTableModel) tblSPKM.getModel();
        dtm.setRowCount(0);
        for (SanPham sanPham : list) {
            dtm.addRow(new Object[]{
                sanPham.getMaSP(),
                sanPham.getTenSP(),
                sanPham.getMaSPKM()
            });
        }
    }

    void loadDataKMChonSP(ArrayList<KhuyenMai> list) {
        dtm = (DefaultTableModel) tblKMChonSP.getModel();
        dtm.setRowCount(0);
        for (KhuyenMai khuyenMai : list) {
            dtm.addRow(new Object[]{
                khuyenMai.getMaKM(),
                khuyenMai.getTenKM(),
                khuyenMai.getGiamGia()
            });
        }
    }

    void loadDataQuanLy(ArrayList<NguoiDung> list) {
        dtm = (DefaultTableModel) tblQuanLy.getModel();
        dtm.setRowCount(0);
        for (NguoiDung nd : list) {
            dtm.addRow(new Object[]{
                nd.getMaNguoiDung(),
                nd.getEmail(),
                nd.getRoles(),
                nd.getTenDN()
            });
        }
    }

    public boolean checkNhanVien() {
        int i = tblNhanVien.getSelectedRow();
        int count = 0;
        if (txtMaNV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống mã nhân viên");
            count++;
        }
        if (txtTenNV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống tên nhân viên");
            count++;
        }
        if (rdNam.isSelected() == false && rdNu.isSelected() == false) {
            JOptionPane.showMessageDialog(this, "Chưa chọn giới tính");
            count++;
        }
        if (txtTuoi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống tuổi");
            count++;
        }
        if (txtSDT.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống số điện thoại");
            count++;
        }

        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống email");
            count++;
        }
        if (txtTenDN.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống tên đăng nhập");
            count++;
        }
        if (txtPassword.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống password");
            count++;
        }

        if (count > 0) {
            return false;
        } else {
            return true;

        }
    }

    public boolean checkTuoiNV() {
        try {
            int tuoi = Integer.parseInt(txtTuoi.getText());
            if (tuoi < 0) {
                JOptionPane.showMessageDialog(this, "Tuổi phải là số dương");
            } else if (tuoi < 18 || tuoi > 55) {
                JOptionPane.showMessageDialog(this, "Độ tuổi không nằm trong độ tuổi lao động");
            }
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tuổi phải là số");
            return false;
        }
    }

    public boolean checkEmailNV(String email) {
        String emailRegex = "[A-Za-z0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(email);
        return matcher.find();
    }

    boolean emailNV() {
        if (checkEmailNV(txtEmail.getText())) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Sai định dạng email");
            return false;
        }
    }

    public boolean checkSDT(String SDT) {
        String sdtRegex = "^((\\+84)|(0))(9[0-9]|3[2-9]|7[06-9]|8[1-9]|5[68])\\d{7}$";
        Pattern sdtPat = Pattern.compile(sdtRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = sdtPat.matcher(SDT);
        return matcher.find();
    }

    boolean sdtNV() {
        if (checkSDT(txtSDT.getText())) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Sai định dạng số điện thoại");
            return false;
        }
    }

    public boolean checkTrungMaNhanVien(String ma) {
        int count = 0;
        String result = ma.replace("", "");
        for (NguoiDung nd : ser.getAllNguoiDung()) {
            if (nd.getMaNguoiDung().trim().equals(result)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng mã");
            return false;
        } else {
            return true;
        }

    }

    public boolean checkTrungTenDNNhanVien(String ten) {
        int count = 0;
        for (NguoiDung nd : ser.getAllNguoiDung()) {
            if (nd.getTenDN().trim().equals(ten)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng tên đăng nhập");
            return false;
        } else {
            return true;
        }

    }

    public boolean checkTrungEmailNhanVien(String email) {
        int count = 0;
        for (NguoiDung nd : ser.getAllNguoiDung()) {
            if (nd.getEmail().trim().equals(email)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng email");
            return false;
        } else {
            return true;
        }
    }

    public boolean checkTrungSDTNhanVien(String SDT) {
        int count = 0;
        for (NguoiDung nd : ser.getAllNguoiDung()) {
            if (nd.getSDT().trim().equals(SDT)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng số điện thoại");
            return false;
        } else {
            return true;
        }
    }

    public boolean checkTrungEmailTenDNNhanVien(String maNV, String email, String tenDN, String SDT) {

        ArrayList<NguoiDung> listEmailTenDN = ser.getAllNguoiDung();
        for (int i = 0; i < listEmailTenDN.size(); i++) {
            if (listEmailTenDN.get(i).getMaNguoiDung().trim().equals(maNV)) {
                listEmailTenDN.remove(i);
            }
        }
        int count = 0;
        for (NguoiDung nd : listEmailTenDN) {
            if (nd.getEmail().trim().equals(email)) {
                count++;
                JOptionPane.showMessageDialog(this, "Email này đã tồn tại");
            }
            if (nd.getTenDN().trim().equals(tenDN)) {
                count++;
                JOptionPane.showMessageDialog(this, "Tên đăng nhập này đã tồn tại");
            }
            if (nd.getSDT().trim().equals(SDT)) {
                count++;
                JOptionPane.showMessageDialog(this, "Số điện thoại này đã tồn tại");
            }
        }
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

    void loadDataNhanVienNghi(ArrayList<NguoiDung> list) {
        dtm = (DefaultTableModel) tblNhanVienNghi.getModel();
        dtm.setRowCount(0);
        for (NguoiDung nd : list) {
            dtm.addRow(new Object[]{
                nd.getMaNguoiDung(),
                nd.getTenNguoiDung(),
                nd.isGioiTinh() ? "Nam" : "Nữ",
                nd.getTuoi(),
                nd.getSDT(),
                nd.getEmail(),
                nd.getRoles(),
                nd.getTenDN(),
                nd.getPassWord(),
                nd.isTrangThai() ? "Đang hoạt động" : "Không hoạt động"
            });
        }
    }

    void loadDataQLSP(List<SanPham> listSP) {
        dtm = (DefaultTableModel) tblSanPhamCTSP.getModel();
        dtm.setRowCount(0);
        for (SanPham sp : listSP) {
            dtm.addRow(new Object[]{
                sp.getMaSPCT(),
                sp.getTenSP(),
                sp.getNhaCungCap(),
                sp.getHang(),
                sp.getSoLuongSP(),
                sp.getMauSac(),
                sp.getKichThuoc(),
                sp.getMau(),
                sp.getChatLieu(),
                sp.getHinhAnh()
            });
        }
    }

    SanPham getFormSanPhamTTSP() {
        SanPham sp = new SanPham();
        sp.setMaSPCT(txtMaSPCT.getText());
        sp.setMaSP(ser.getIDSanPham(txtTenSPSPCT.getText()));
        sp.setMaNCC(ser.getIDNCC(cboTenNCCTTSP.getSelectedItem().toString()));
        sp.setHang(txtHang.getText());
        sp.setSoLuongSP(Integer.valueOf(txtSoLuongTTSP.getText()));
        sp.setMaMS(ser.getIDMauSac(cboMauSacTTSP.getSelectedItem().toString()));
        sp.setMaKT(ser.getIDKichThuoc(cboKichThuocTTSP.getSelectedItem().toString()));
        sp.setMaCL(ser.getIDChatLieu(cboChatLieuTTSP.getSelectedItem().toString()));
        sp.setMau(String.valueOf(cboMauTTSP.getSelectedItem() + ""));
        System.out.println("" + sp.toString());
        return sp;
    }

    void setFormSanPhamTTSP(SanPham sp) {
        txtMaSPCT.setText(sp.getMaSPCT());
        txtTenSPSPCT.setText(sp.getTenSP());
        cboTenNCCTTSP.setSelectedItem(sp.getNhaCungCap());
        txtHang.setText(sp.getHang());

        txtSoLuongTTSP.setText(sp.getSoLuongSP() + "");
        cboMauSacTTSP.setSelectedItem(sp.getMauSac());
        cboKichThuocTTSP.setSelectedItem(sp.getKichThuoc());
        cboChatLieuTTSP.setSelectedItem(sp.getChatLieu());
        cboMauTTSP.setSelectedItem(sp.getMau());
    }

    void setFormSanPhamTTSP1(int row) {
        txtMaSPCT.setText(tblSanPhamCTSP.getValueAt(row, 0) + "");
        txtTenSPSPCT.setText(tblSanPhamCTSP.getValueAt(row, 1) + "");
        cboTenNCCTTSP.setSelectedItem(tblSanPhamCTSP.getValueAt(row, 2) + "");
        txtHang.setText(tblSanPhamCTSP.getValueAt(row, 3) + "");
        txtSoLuongTTSP.setText(tblSanPhamCTSP.getValueAt(row, 4) + "");
        cboMauSacTTSP.setSelectedItem(tblSanPhamCTSP.getValueAt(row, 5) + "");
        cboKichThuocTTSP.setSelectedItem(tblSanPhamCTSP.getValueAt(row, 6) + "");
        cboChatLieuTTSP.setSelectedItem(tblSanPhamCTSP.getValueAt(row, 8) + "");
        cboMauTTSP.setSelectedItem(tblSanPhamCTSP.getValueAt(row, 7) + "");
    }

    void showCboMau() {
        List<SanPham> mau = ser.getAllMau();
        for (SanPham m : mau) {
            cboMauTTSP.addItem(m.getMau());
        }
    }

    void showCboKichThuoc() {
        List<SanPham> KT = ser.getAllKichThuoc();
        for (SanPham kt : KT) {
            cboKichThuocTTSP.addItem(kt.getKichThuoc());
        }
    }

    void showCboChatLieu() {
        List<SanPham> KT = ser.getAllChatLieu();
        for (SanPham kt : KT) {
            cboChatLieuTTSP.addItem(kt.getChatLieu());
        }
    }

    void showCboMauSacTT() {

        List<SanPham> mauSac = ser.getAllMauSac();
        for (SanPham ms : mauSac) {
            cboMauSacTTSP.addItem(ms.getMauSac());
        }

    }

    void showCboNCC() {
        List<SanPham> mauSac = ser.getAllNCC();
        for (SanPham ms : mauSac) {
            cboTenNCCTTSP.addItem(ms.getNhaCungCap());
        }
    }

    void loadDataMauSac() {
        dtm = (DefaultTableModel) tblMauSacTTSP.getModel();
        dtm.setRowCount(0);
        for (SanPham sp : ser.getAllMauSac()) {
            dtm.addRow(new Object[]{
                sp.getMaMS(),
                sp.getMauSac()

            });
        }
    }

    SanPham getFormMauSac() {
        SanPham sp = new SanPham();
        sp.setMaMS(txtMaMauSac.getText());
        sp.setMauSac(txtTenMauSac.getText());
        return sp;
    }

    void setFormMauSac(SanPham sp) {
        txtMaMauSac.setText(sp.getMaMS());
        txtTenMauSac.setText(sp.getMauSac());
    }

    void loadDataChatLieu() {
        dtm = (DefaultTableModel) tblChatLieuTTSP.getModel();
        dtm.setRowCount(0);
        for (SanPham sp : ser.getAllChatLieu()) {
            dtm.addRow(new Object[]{
                sp.getMaCL(),
                sp.getChatLieu()

            });
        }
    }

    SanPham getFormChatLieu() {
        SanPham sp = new SanPham();
        sp.setMaCL(txtMaChatLieu.getText());
        sp.setChatLieu(txtTenChatLieu.getText());
        return sp;
    }

    void setFormChatLieu(SanPham sp) {
        txtMaChatLieu.setText(sp.getMaCL());
        txtTenChatLieu.setText(sp.getChatLieu());
    }

    void loadDataKichThuoc() {
        dtm = (DefaultTableModel) tblKichThuocTTSP.getModel();
        dtm.setRowCount(0);
        for (SanPham sp : ser.getAllKichThuoc()) {
            dtm.addRow(new Object[]{
                sp.getMaKT(),
                sp.getKichThuoc()

            });
        }
    }

    SanPham getFormKichThuoc() {
        SanPham sp = new SanPham();
        sp.setMaKT(txtMaKichThuoc.getText());
        sp.setKichThuoc(txtTenKichThuoc.getText());
        return sp;
    }

    void setFormKichThuoc(SanPham sp) {
        txtMaKichThuoc.setText(sp.getMaKT());
        txtTenKichThuoc.setText(sp.getKichThuoc());
    }

    public boolean checkMauSac() {
        if (txtMaMauSac.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mã màu sắc");
            return false;
        }
        if (txtTenMauSac.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên màu sắc");
            return false;
        }
        return true;
    }

    public boolean checkTrungMaMauSac(String ma) {
        int count = 0;
        for (SanPham sp : ser.getAllMauSac()) {
            if (sp.getMaMS().equals(ma)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng mã màu sắc");
            return false;
        } else {
            return true;
        }

    }

    public boolean checkTrungTenMauSac(String ten) {
        int count = 0;
        for (SanPham sp : ser.getAllMauSac()) {
            if (sp.getMauSac().equals(ten)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng tên màu sắc");
            return false;
        } else {
            return true;
        }

    }

    public boolean checkKichThuoc() {
        if (txtMaKichThuoc.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mã kích thước");
            return false;
        }
        if (txtTenKichThuoc.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập kích thước ");
            return false;
        }
        return true;
    }

    public boolean checkTrungMaKichThuoc(String ma) {
        int count = 0;
        for (SanPham sp : ser.getAllKichThuoc()) {
            if (sp.getMaKT().equals(ma)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng mã ");
            return false;
        } else {
            return true;
        }

    }

    public boolean checkTrungTenKichThuoc(String ten) {
        int count = 0;
        for (SanPham sp : ser.getAllKichThuoc()) {
            if (sp.getKichThuoc().equals(ten)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng tên ");
            return false;
        } else {
            return true;
        }

    }

    public boolean checkChatLieu() {
        if (txtMaChatLieu.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mã chất liệu");
            return false;
        }
        if (txtTenChatLieu.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập chất liệu");
            return false;
        }
        return true;
    }

    public boolean checkTrungMaChatLieu(String ma) {
        int count = 0;
        for (SanPham sp : ser.getAllChatLieu()) {
            if (sp.getMaCL().equals(ma)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng mã");
            return false;
        } else {
            return true;
        }

    }

    public boolean checkTrungTenChatLieu(String ten) {
        int count = 0;
        for (SanPham sp : ser.getAllChatLieu()) {
            if (sp.getChatLieu().equals(ten)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng tên");
            return false;
        } else {
            return true;
        }

    }

    public boolean checkTrungMaNCC(String ma) {
        int count = 0;
        for (SanPham sp : ser.getAllNCC()) {
            if (sp.getMaNCC().equals(ma)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng mã");
            return false;
        } else {
            return true;
        }

    }

    public boolean checkTrungTenNCC(String ten) {
        int count = 0;
        for (SanPham sp : ser.getAllNCC()) {
            if (sp.getNhaCungCap().equals(ten)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng tên");
            return false;
        } else {
            return true;
        }

    }

    public boolean checkTTSP() {
        if (txtMaSPCT.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mã sản phẩm");
            return false;
        }
        if (txtTenSPSPCT.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên sản phẩm");
            return false;
        }
        if (txtHang.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập hãng sản phẩm");
            return false;
        }

        if (txtSoLuongTTSP.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập số lượng sản phẩm");
            return false;
        }

        return true;
    }

    public boolean checkTrungMaSP(String ma) {
        int count = 0;
        for (SanPham sp : ser.getAllSanPham()) {
            if (sp.getMaSP().equals(ma)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng mã");
            return false;
        } else {
            return true;
        }

    }

    public boolean checkTrungTenSP(String ten) {
        int count = 0;
        for (SanPham sp : ser.getAllSanPham()) {
            if (sp.getTenSP().equals(ten)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng tên");
            return false;
        } else {
            return true;
        }

    }

    void loadDataSanPhamTTSP(List<SanPham> listSXSP) {
        dtm = (DefaultTableModel) tblSanPhamTTSP.getModel();
        dtm.setRowCount(0);
        for (SanPham sp : listSXSP) {
            dtm.addRow(new Object[]{
                sp.getMaSP(),
                sp.getTenSP(),
                sp.getMau(),
                sp.getHang()

            });
        }
    }

    SanPham getFormSPTTSP() {
        SanPham sp = new SanPham();
        sp.setMaSP(txtMaSP.getText());
        sp.setTenSP(txtTenSPTTSP.getText());
        sp.setMau(txtMauTTSP.getText());
        sp.setHang(txtHangTTSP.getText());
        return sp;
    }

    void setFormSPTTSP(int row) {
        txtMaSP.setText(tblSanPhamTTSP.getValueAt(row, 0) + "");
        txtTenSPTTSP.setText(tblSanPhamTTSP.getValueAt(row, 1) + "");
        txtMauTTSP.setText(tblSanPhamTTSP.getValueAt(row, 2) + "");
        txtHangTTSP.setText(tblSanPhamTTSP.getValueAt(row, 3) + "");

    }

    public boolean checkSPTTSP() {
        if (txtMaSP.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mã sản phẩm");
            return false;
        }
        if (txtTenSPTTSP.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên sản phẩm");
            return false;
        }
        if (txtMauTTSP.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mẫu sản phẩm");
            return false;
        }
        if (txtHangTTSP.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập hãng sản phẩm");
            return false;
        }
        return true;
    }

    public boolean checkTrungMaSPTTSP(String ma) {
        int count = 0;
        for (SanPham sp : ser.getAllSanPhamTTSP()) {
            if (sp.getMaSP().equals(ma)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng mã sản phẩm");
            return false;
        } else {
            return true;
        }

    }

    public boolean checkTrungTenSPTTSP(String ten) {
        int count = 0;
        for (SanPham sp : ser.getAllSanPhamTTSP()) {
            if (sp.getTenSP().equals(ten)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm đã tồn tại");
            return false;
        } else {
            return true;
        }

    }

    void showCboSPCT() {
        List<SanPham> lsg = ser.getAllSanPhamCT();
        for (SanPham ls : lsg) {
            cboMaSPCTLSG.addItem(ls.getMaSPCT());
        }
    }

    void loadDataLichSuGia(List<LichSuGia> listLSG) {
        dtm = (DefaultTableModel) tblLichSuDonGia.getModel();
        dtm.setRowCount(0);
        for (LichSuGia sp : listLSG) {
            dtm.addRow(new Object[]{
                sp.getMaDonGia(),
                sp.getMaSPCT(),
                sp.getTenSP(),
                sp.getGiaDau(),
                sp.getGiaSau(),
                sp.getNgayBatDau(),
                sp.getNgayKetThuc()
            });
        }
    }

    LichSuGia getFormLichSuGia() {
        LichSuGia ls = new LichSuGia();
        ls.setMaDonGia(txtMaDonGia.getText());
        ls.setMaSPCT(cboMaSPCTLSG.getSelectedItem() + "");
        ls.setTenSP(lblTenSPLSG.getText());
        ls.setGiaDau(Double.valueOf(txtGiaDau.getText()));
        ls.setGiaSau(Double.valueOf(txtGiaSau.getText()));
        ls.setNgayBatDau(txtTGBatDauLSDG.getText());
        ls.setNgayKetThuc(txtTGKetThucLSDG.getText());
        return ls;
    }

    void setFormLichSuGia(int row) {
        txtMaDonGia.setText(tblLichSuDonGia.getValueAt(row, 0) + "");
        cboMaSPCTLSG.setSelectedItem(tblLichSuDonGia.getValueAt(row, 1) + "");
        lblTenSPLSG.setText(tblLichSuDonGia.getValueAt(row, 2) + "");
        txtGiaDau.setText(tblLichSuDonGia.getValueAt(row, 3) + "");
        txtGiaSau.setText(tblLichSuDonGia.getValueAt(row, 4) + "");
        txtTGBatDauLSDG.setText(tblLichSuDonGia.getValueAt(row, 5) + "");
        txtTGKetThucLSDG.setText(tblLichSuDonGia.getValueAt(row, 6) + "");

    }

    public boolean checkLSDG() {
        if (txtMaDonGia.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mã đơn giá");
            return false;
        }
        if (txtGiaDau.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập giá đầu");
            return false;
        }
        if (txtGiaSau.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập giá sau");
            return false;
        }
        if (txtTGBatDauLSDG.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập thời gian bắt đầu");
            return false;
        }
        if (txtTGKetThucLSDG.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập thời gian kết thúc");
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false); // To enforce strict date parsing

        try {
            dateFormat.parse(txtTGBatDauLSDG.getText().trim());
            dateFormat.parse(txtTGKetThucLSDG.getText().trim());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày tháng không hợp lệ. Vui lòng nhập theo định dạng dd-MM-yyyy");
            return false;
        }

        return true;
    }

    public boolean checkTrungMaDonGia(String ma) {
        int count = 0;
        for (LichSuGia sp : ser.getAllLichSuDonGia()) {
            if (sp.getMaDonGia().equals(ma)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng mã");
            return false;
        } else {
            return true;
        }

    }

    public boolean checkTrungMaSPCTLSG(String ma) {
        int count = 0;
        for (LichSuGia sp : ser.getAllLichSuDonGia()) {
            if (sp.getMaSPCT().equals(ma)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Mã sản phẩm chi tiết đã tồn tại");
            return false;
        } else {
            return true;
        }

    }

    double tinhTongTienTheoHoaDon(String maHoaDon) {
        double tinhTien = 0.0;
        for (HoaDonChiTiet hdct : ser.getAllHoaDonChiTiet(maHoaDon)) {
            tinhTien += hdct.getDonGiaSau() * hdct.getSoLuong();
        }
        return tinhTien;
    }

    void loadDataQuanLyHD(ArrayList<HoaDon> list) {
        dtm = (DefaultTableModel) tblQLHoaDon.getModel();
        dtm.setRowCount(0);
        for (HoaDon hd : list) {
            dtm.addRow(new Object[]{
                hd.getMaHoaDon(),
                hd.getNgayTao(),
                hd.getTrangThai(),
                hd.getMaVoucher(),
                hd.getMaNhanVien(),
                hd.getNgayHoanThanh(),
                hd.getLoaiThanhToan(),
                hd.getMaKhachHang(),
                tinhTongTienTheoHoaDon(hd.getMaHoaDon())
            });
        }
    }

    void loadDataQuanLyHDHuy(ArrayList<HoaDon> list) {
        dtm = (DefaultTableModel) tblQLHoaDonHuy.getModel();
        dtm.setRowCount(0);
        for (HoaDon hd : list) {
            dtm.addRow(new Object[]{
                hd.getMaHoaDon(),
                hd.getNgayTao(),
                hd.getTrangThai(),
                hd.getMaVoucher(),
                hd.getMaNhanVien(),
                "null",
                hd.getLoaiThanhToan(),
                hd.getMaKhachHang(),
                tinhTongTienTheoHoaDon(hd.getMaHoaDon())
            });
        }
    }

    void loadDataQLHDSP(ArrayList<SanPham> list) {
        dtm = (DefaultTableModel) tblQLSanPham.getModel();
        dtm.setRowCount(0);
        for (SanPham sp : list) {
            dtm.addRow(new Object[]{
                sp.getTenSP(),
                sp.getChatLieu(),
                sp.getKichThuoc(),
                sp.getMauSac(),
                sp.getMau(),
                sp.getHang(),
                sp.getSoLuongSP(),
                sp.getDonGia(),
                sp.getSoLuongSP() * sp.getDonGia()
            });

        }
    }

    void loadDataQLHDSPHuy(ArrayList<SanPham> list) {
        dtm = (DefaultTableModel) tblSanPhamHuy.getModel();
        dtm.setRowCount(0);
        for (SanPham sp : list) {
            dtm.addRow(new Object[]{
                sp.getTenSP(),
                sp.getChatLieu(),
                sp.getKichThuoc(),
                sp.getMauSac(),
                sp.getMau(),
                sp.getHang(),
                sp.getSoLuongSP(),
                sp.getDonGia(),
                sp.getSoLuongSP() * sp.getDonGia()
            });

        }
    }

    public LocalDate getLocaldate() {
        return LocalDate.now();
    }

    void setFormSPKM(int row) {
        txtMaSPThemKM.setText(tblSPKM.getValueAt(row, 0) + "");
        txtTenSPKM.setText(tblSPKM.getValueAt(row, 1) + "");
        if (tblSPKM.getValueAt(row, 2).equals("")) {
            cbTrangThaiThemKM.setSelected(false);
        } else {
            cbTrangThaiThemKM.setSelected(true);
        }

    }

    void setFormKMSP(int row) {
        txtMaKMChonSP.setText(tblKMChonSP.getValueAt(row, 0) + "");
        txtTenKMChonSP.setText(tblKMChonSP.getValueAt(row, 1) + "");
        txtGiamGiaKMChonSP.setText(tblKMChonSP.getValueAt(row, 2) + "");
    }

    public boolean checkVoucher() {
        if (txtMaVoucher.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mã Voucher");
            return false;
        }
        if (txtTenVoucher.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên Voucher");
            return false;
        }

        String soLuongText = txtSoLuongVoucher.getText().trim();
        if (soLuongText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập số lượng Voucher");
            return false;
        }
        try {
            double soLuong = Double.parseDouble(soLuongText);
            if (soLuong < 0) {
                JOptionPane.showMessageDialog(this, "Số lượng Voucher phải là số không âm");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng Voucher không hợp lệ");
            return false;
        }

        String soTienGiamText = txtSoTienGiamVoucher.getText().trim();
        if (soTienGiamText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập số tiền giảm cho Voucher");
            return false;
        }
        try {
            double soTienGiam = Double.parseDouble(soTienGiamText);
            if (soTienGiam < 0) {
                JOptionPane.showMessageDialog(this, "Số tiền giảm cho Voucher phải là số không âm");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số tiền giảm cho Voucher không hợp lệ");
            return false;
        }

        String soTienYeuCauText = txtSoTienYCVoucher.getText().trim();
        if (soTienYeuCauText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập số tiền yêu cầu để áp dụng Voucher");
            return false;
        }
        try {
            double soTienYeuCau = Double.parseDouble(soTienYeuCauText);
            if (soTienYeuCau < 0) {
                JOptionPane.showMessageDialog(this, "Số tiền yêu cầu để áp dụng Voucher phải là số không âm");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số tiền yêu cầu để áp dụng Voucher không hợp lệ");
            return false;
        }

        return true;
    }

    public boolean checkTrungMaVoucher(String ma) {
        int count = 0;
        for (Voucher sp : ser.getAllVoucher()) {
            if (sp.getMaVoucher().equals(ma)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng mã Voucher");
            return false;
        } else {
            return true;
        }

    }

    public boolean checkTrungTenVoucher(String ten) {
        int count = 0;
        for (Voucher sp : ser.getAllVoucher()) {
            if (sp.getTenVoucher().equals(ten)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Trùng tên Voucher");
            return false;
        } else {
            return true;
        }

    }

    public boolean checkKhuyenMai() {
        if (txtMaKhuyenMai.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mã khuyến mãi");
            return false;
        }
        if (txtTenKhuyenMai.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên khuyến mãi");
            return false;
        }
        if (txtNBatDauKhuyenMai.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập ngày bắt đầu");
            return false;
        }
        if (txtNKetThucKhuyenMai.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập ngày kết thúc");
            return false;
        }
        if (txtGiamGiaKhuyenMai.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập giảm giá");
            return false;
        }
        // Kiểm tra định dạng dữ liệu của trường Giảm giá
        try {
            double giamGia = Double.parseDouble(txtGiamGiaKhuyenMai.getText().trim());
            if (giamGia < 0) {
                JOptionPane.showMessageDialog(this, "Giảm giá không được âm");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giảm giá phải là một số");
            return false;
        }
        return true;
    }

    public boolean checkTrungMaKM(String ma) {
        int count = 0;
        for (KhuyenMai sp : ser.getAllKhuyenMai()) {
            if (sp.getMaKM().equals(ma)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Mã Khuyến mãi đã tồn tại");
            return false;
        } else {
            return true;
        }

    }

    public boolean checkTrungTenKM(String ten) {
        int count = 0;
        for (KhuyenMai sp : ser.getAllKhuyenMai()) {
            if (sp.getTenKM().equals(ten)) {
                count++;
            }
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, "Tên Khuyến mãi đã tồn tại");
            return false;
        } else {
            return true;
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        jPanel33 = new javax.swing.JPanel();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        buttonGroup8 = new javax.swing.ButtonGroup();
        buttonGroup9 = new javax.swing.ButtonGroup();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel39 = new javax.swing.JPanel();
        lblAnhNen = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jPanel32 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        btnHienThiNV = new javax.swing.JButton();
        btnSearchNV = new javax.swing.JButton();
        txtSearchNV = new javax.swing.JTextField();
        rdTheoMaNV = new javax.swing.JRadioButton();
        rdTheoTenNV = new javax.swing.JRadioButton();
        rdTheoTuoiNhanVien = new javax.swing.JRadioButton();
        btnAddNhanVien = new javax.swing.JButton();
        btnUpdateNhanVien = new javax.swing.JButton();
        btnDeleteNhanVien = new javax.swing.JButton();
        btnNewNhanVien = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jScrollPane17 = new javax.swing.JScrollPane();
        tblQuanLy = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        lblRoles = new javax.swing.JLabel();
        txtTuoi = new javax.swing.JTextField();
        txtMaNV = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtTenDN = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        rdNam = new javax.swing.JRadioButton();
        rdNu = new javax.swing.JRadioButton();
        txtEmail = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        tblNhanVienNghi = new javax.swing.JTable();
        jPanel35 = new javax.swing.JPanel();
        btnHienThiNVNghi = new javax.swing.JButton();
        btnSearchNVNghi = new javax.swing.JButton();
        txtSearchNVNghi = new javax.swing.JTextField();
        rdTheoMaNVNghi = new javax.swing.JRadioButton();
        rdTheoTenNVNghi = new javax.swing.JRadioButton();
        rdTheoTuoiNhanVienNghi = new javax.swing.JRadioButton();
        btnKhoiPhuc = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        btnSearchHD = new javax.swing.JButton();
        btnHienThiHD = new javax.swing.JButton();
        txtSearchHD = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblQLSanPham = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblQLHoaDon = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        txtBatDauHD = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtKetThucHD = new javax.swing.JTextField();
        btnLocHD = new javax.swing.JButton();
        btnBoLocHD = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        btnSearchHuy = new javax.swing.JButton();
        btnHienThiHuy = new javax.swing.JButton();
        txtSearchHuy = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        txtBatDauHuy = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtKetThucHuy = new javax.swing.JTextField();
        btnLocHuy = new javax.swing.JButton();
        btnBoLocHuy = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblQLHoaDonHuy = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblSanPhamHuy = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel38 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        txtTenSPTTSP = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        txtMauTTSP = new javax.swing.JTextField();
        jLabel93 = new javax.swing.JLabel();
        txtHangTTSP = new javax.swing.JTextField();
        btnNewTTSP = new javax.swing.JButton();
        btnUpdateSP = new javax.swing.JButton();
        btnAddSanPham = new javax.swing.JButton();
        btnUpdateSP1 = new javax.swing.JButton();
        jLabel85 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblSanPhamTTSP = new javax.swing.JTable();
        btnTimKiemMaSP = new javax.swing.JButton();
        txtTimKiemMaSP = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        rdoSXTheoMaSPTTSP = new javax.swing.JRadioButton();
        rdoSXTheoTenTTSP = new javax.swing.JRadioButton();
        jLabel46 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        txtMaSPCT = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        lblHinhAnh = new javax.swing.JLabel();
        txtTenSPSPCT = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtSoLuongTTSP = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        cboMauSacTTSP = new javax.swing.JComboBox<>();
        cboKichThuocTTSP = new javax.swing.JComboBox<>();
        cboMauTTSP = new javax.swing.JComboBox<>();
        cboChatLieuTTSP = new javax.swing.JComboBox<>();
        txtHang = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        txtNewTTSP = new javax.swing.JButton();
        btnAddSPCT = new javax.swing.JButton();
        btnSuaChiTietSanPham = new javax.swing.JButton();
        cboTenNCCTTSP = new javax.swing.JComboBox<>();
        btnThemThuocTinh = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        txtTimKiemSPTTSP = new javax.swing.JTextField();
        rdoSXMaTTSP = new javax.swing.JRadioButton();
        rdoSXTenTTSP = new javax.swing.JRadioButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblSanPhamCTSP = new javax.swing.JTable();
        btnTimKiemSP = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        txtMaMauSac = new javax.swing.JTextField();
        btnNewMauSac = new javax.swing.JButton();
        btnSuaMauSacTTSP = new javax.swing.JButton();
        btnXoaMauSacTTSP = new javax.swing.JButton();
        jLabel76 = new javax.swing.JLabel();
        txtTenMauSac = new javax.swing.JTextField();
        btnAddMauSac = new javax.swing.JButton();
        jLabel77 = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        tblMauSacTTSP = new javax.swing.JTable();
        jPanel36 = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        txtMaKichThuoc = new javax.swing.JTextField();
        btnUpdateKichThuoc = new javax.swing.JButton();
        btnXoaKichThuoc = new javax.swing.JButton();
        jLabel79 = new javax.swing.JLabel();
        txtTenKichThuoc = new javax.swing.JTextField();
        btnAddKichThuocTTSP = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        btnNewKichThuoc = new javax.swing.JButton();
        jScrollPane20 = new javax.swing.JScrollPane();
        tblKichThuocTTSP = new javax.swing.JTable();
        jPanel37 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        txtMaChatLieu = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        txtTenChatLieu = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        jButton21 = new javax.swing.JButton();
        btnAddChatLieu = new javax.swing.JButton();
        btnUpdateChatLieu = new javax.swing.JButton();
        btnXoaChatLieu = new javax.swing.JButton();
        jScrollPane21 = new javax.swing.JScrollPane();
        tblChatLieuTTSP = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTKVoucher = new javax.swing.JTextField();
        rdSXTMaVoucher = new javax.swing.JRadioButton();
        rdSXTTenVoucher = new javax.swing.JRadioButton();
        rdSXTNgayVoucher = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVoucher = new javax.swing.JTable();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        txtNKetThucVoucher = new javax.swing.JTextField();
        txtNBatDauVoucher = new javax.swing.JTextField();
        txtSoLuongVoucher = new javax.swing.JTextField();
        txtTenVoucher = new javax.swing.JTextField();
        txtMaVoucher = new javax.swing.JTextField();
        btnThemVoucher = new javax.swing.JButton();
        btnSuaVoucher = new javax.swing.JButton();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        txtSoTienYCVoucher = new javax.swing.JTextField();
        txtSoTienGiamVoucher = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        rdAllVoucher = new javax.swing.JRadioButton();
        jLabel66 = new javax.swing.JLabel();
        txtTKNKTVoucher = new javax.swing.JTextField();
        txtTKNBDVoucher = new javax.swing.JTextField();
        btnTKTNVoucher = new javax.swing.JButton();
        btnNewFormVoucher = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        txtTKKhuyenMai = new javax.swing.JTextField();
        rdSXTMaKhuyenMai = new javax.swing.JRadioButton();
        rdSXTTenKhuyenMai = new javax.swing.JRadioButton();
        rdSXTNgayKhuyenMai = new javax.swing.JRadioButton();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        txtMaKhuyenMai = new javax.swing.JTextField();
        btnThemKhuyenMai = new javax.swing.JButton();
        btnSuaKhuyenMai = new javax.swing.JButton();
        jLabel59 = new javax.swing.JLabel();
        txtTenKhuyenMai = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        txtNBatDauKhuyenMai = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        txtNKetThucKhuyenMai = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        txtGiamGiaKhuyenMai = new javax.swing.JTextField();
        jScrollPane14 = new javax.swing.JScrollPane();
        tblKhuyenMai = new javax.swing.JTable();
        rdAllKhuyenMai = new javax.swing.JRadioButton();
        jLabel65 = new javax.swing.JLabel();
        txtTKNBDKhuyenMai = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        txtTKNKTKhuyeMai = new javax.swing.JTextField();
        btnTKTKNKM = new javax.swing.JButton();
        btnNewFormKM = new javax.swing.JButton();
        jPanel31 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        txtTenSPKM = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        txtMaSPThemKM = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        cbTrangThaiThemKM = new javax.swing.JCheckBox();
        jScrollPane15 = new javax.swing.JScrollPane();
        tblSPKM = new javax.swing.JTable();
        btnThemSPKM = new javax.swing.JButton();
        btnXoaSPKM = new javax.swing.JButton();
        jScrollPane16 = new javax.swing.JScrollPane();
        tblKMChonSP = new javax.swing.JTable();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        txtTenKMChonSP = new javax.swing.JTextField();
        txtGiamGiaKMChonSP = new javax.swing.JTextField();
        txtMaKMChonSP = new javax.swing.JTextField();
        jPanel41 = new javax.swing.JPanel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        txtTGBatDauLSDG = new javax.swing.JTextField();
        txtMaDonGia = new javax.swing.JTextField();
        txtGiaSau = new javax.swing.JTextField();
        txtGiaDau = new javax.swing.JTextField();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        txtTGKetThucLSDG = new javax.swing.JTextField();
        btnNewLSDG = new javax.swing.JButton();
        btnAddLSDG = new javax.swing.JButton();
        btnUpdateLSDG = new javax.swing.JButton();
        cboMaSPCTLSG = new javax.swing.JComboBox<>();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        lblTenSPLSG = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        jScrollPane23 = new javax.swing.JScrollPane();
        tblLichSuDonGia = new javax.swing.JTable();
        jLabel101 = new javax.swing.JLabel();
        txtTimKiemMaDonGia = new javax.swing.JTextField();
        btnTKMaLSDG = new javax.swing.JButton();
        rdoSXMaLSDG = new javax.swing.JRadioButton();
        rdoSXNgayLSDG = new javax.swing.JRadioButton();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        txtTimKiemTGBDLSDG = new javax.swing.JTextField();
        txtTimKiemTGKTLSDG = new javax.swing.JTextField();
        btnTKNgayLSDG = new javax.swing.JButton();
        btnTKNgayLSDG1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtTongDoanhThu = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtTongDoanhSoBanHang = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtTongDonHang = new javax.swing.JTextField();
        lblThanhCong = new javax.swing.JLabel();
        lblCTT = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtNgayKetThuc = new javax.swing.JTextField();
        txtNgayBatDau = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        btnHienThiBCTK = new javax.swing.JButton();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        jPanel22 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblThongTinChiTietSanPham = new javax.swing.JTable();
        jLabel30 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblChiTietHoaDon = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnDangXuat = new javax.swing.JButton();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jTabbedPane2.setForeground(new java.awt.Color(51, 153, 255));
        jTabbedPane2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        lblAnhNen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/chanGaGoiDemAgoNi2.png"))); // NOI18N
        lblAnhNen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAnhNen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhNenMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblAnhNen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblAnhNen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Trang chủ", jPanel39);

        jTabbedPane6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 153, 255));
        jLabel8.setText("Thông tin nhân viên");

        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnHienThiNV.setBackground(new java.awt.Color(51, 153, 255));
        btnHienThiNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHienThiNV.setForeground(new java.awt.Color(255, 255, 255));
        btnHienThiNV.setText("Hiển thị");
        btnHienThiNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHienThiNVActionPerformed(evt);
            }
        });

        btnSearchNV.setBackground(new java.awt.Color(51, 153, 255));
        btnSearchNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearchNV.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search-interface-symbol.png"))); // NOI18N
        btnSearchNV.setText("Tìm kiếm ");
        btnSearchNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchNVActionPerformed(evt);
            }
        });

        txtSearchNV.setForeground(new java.awt.Color(153, 153, 153));
        txtSearchNV.setText("Nhập mã hoặc tên nhân viên");
        txtSearchNV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchNVFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchNVFocusLost(evt);
            }
        });
        txtSearchNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtSearchNVMouseReleased(evt);
            }
        });
        txtSearchNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchNVKeyReleased(evt);
            }
        });

        buttonGroup3.add(rdTheoMaNV);
        rdTheoMaNV.setText("Sắp xếp theo mã");
        rdTheoMaNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdTheoMaNVMouseClicked(evt);
            }
        });

        buttonGroup3.add(rdTheoTenNV);
        rdTheoTenNV.setText("Sắp xếp theo tên");
        rdTheoTenNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdTheoTenNVMouseClicked(evt);
            }
        });

        buttonGroup3.add(rdTheoTuoiNhanVien);
        rdTheoTuoiNhanVien.setText("Sắp xếp theo tuổi");
        rdTheoTuoiNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdTheoTuoiNhanVienMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHienThiNV, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(btnSearchNV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchNV, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(rdTheoMaNV)
                        .addGap(112, 112, 112)
                        .addComponent(rdTheoTenNV)
                        .addGap(112, 112, 112)
                        .addComponent(rdTheoTuoiNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(242, Short.MAX_VALUE))
        );

        jPanel15Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnHienThiNV, btnSearchNV});

        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchNV, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdTheoMaNV)
                    .addComponent(rdTheoTenNV)
                    .addComponent(btnSearchNV)
                    .addComponent(rdTheoTuoiNhanVien))
                .addGap(18, 18, 18)
                .addComponent(btnHienThiNV, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jPanel15Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnHienThiNV, btnSearchNV});

        btnAddNhanVien.setBackground(new java.awt.Color(51, 153, 255));
        btnAddNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        btnAddNhanVien.setText("Add");
        btnAddNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNhanVienActionPerformed(evt);
            }
        });

        btnUpdateNhanVien.setBackground(new java.awt.Color(51, 153, 255));
        btnUpdateNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdateNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateNhanVien.setText("Update");
        btnUpdateNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateNhanVienActionPerformed(evt);
            }
        });

        btnDeleteNhanVien.setBackground(new java.awt.Color(51, 153, 255));
        btnDeleteNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDeleteNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteNhanVien.setText("Delete");
        btnDeleteNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteNhanVienActionPerformed(evt);
            }
        });

        btnNewNhanVien.setBackground(new java.awt.Color(51, 153, 255));
        btnNewNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNewNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        btnNewNhanVien.setText("New");
        btnNewNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewNhanVienActionPerformed(evt);
            }
        });

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Giới tính", "Tuổi", "Số điện thoại", "Email", "Roles", "Tên đăng nhập", "Password", "Trạng thái"
            }
        ));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tblNhanVien);

        tblQuanLy.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã quản lý", "Email", "Roles", "Tên đăng nhập"
            }
        ));
        jScrollPane17.setViewportView(tblQuanLy);

        jLabel16.setText("Roles");

        lblRoles.setText("NV");

        jLabel9.setText("Mã nhân viên");

        jLabel29.setText("Tuổi");

        jLabel12.setText("Tên nhân viên");

        jLabel14.setText("Số điện thoại");

        jLabel17.setText("Tên đăng nhập");

        jLabel15.setText("Giới tính");

        buttonGroup4.add(rdNam);
        rdNam.setText("Nam");

        buttonGroup4.add(rdNu);
        rdNu.setText("Nữ");

        jLabel18.setText("Password");

        jLabel13.setText("Email");

        btnExit.setBackground(new java.awt.Color(51, 153, 255));
        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("Exit");
        btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExitMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel32Layout.createSequentialGroup()
                                        .addComponent(btnAddNhanVien)
                                        .addGap(160, 160, 160)
                                        .addComponent(btnUpdateNhanVien)
                                        .addGap(160, 160, 160)
                                        .addComponent(btnDeleteNhanVien)
                                        .addGap(160, 160, 160)
                                        .addComponent(btnNewNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11))
                                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 879, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                                        .addComponent(btnExit)
                                        .addGap(159, 159, 159))))))
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaNV)
                            .addComponent(txtTuoi)
                            .addComponent(lblRoles, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenDN, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                                        .addGap(117, 117, 117)
                                        .addComponent(rdNam, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(64, 64, 64)
                                        .addComponent(rdNu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel32Layout.createSequentialGroup()
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(36, 36, 36)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(519, 519, 519))
        );

        jPanel32Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddNhanVien, btnDeleteNhanVien, btnExit, btnNewNhanVien, btnUpdateNhanVien});

        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel8)
                .addGap(44, 44, 44)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel32Layout.createSequentialGroup()
                                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel12)
                                            .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(26, 26, 26)
                                        .addComponent(jLabel14)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTenDN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17)))
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18))))
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel32Layout.createSequentialGroup()
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(txtTuoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(lblRoles)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel32Layout.createSequentialGroup()
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(rdNam)
                            .addComponent(rdNu))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdateNhanVien)
                    .addComponent(btnAddNhanVien)
                    .addComponent(btnNewNhanVien)
                    .addComponent(btnDeleteNhanVien)
                    .addComponent(btnExit))
                .addGap(39, 39, 39)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70))
        );

        jPanel32Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddNhanVien, btnDeleteNhanVien, btnExit, btnNewNhanVien, btnUpdateNhanVien});

        jPanel32Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jScrollPane17, jScrollPane9});

        jTabbedPane6.addTab("Danh sách nhân viên", jPanel32);

        tblNhanVienNghi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Giới tính", "Tuổi", "Số điện thoại", "Email", "Roles", "Tên đăng nhập", "Password", "Trạng thái"
            }
        ));
        jScrollPane18.setViewportView(tblNhanVienNghi);

        jPanel35.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnHienThiNVNghi.setBackground(new java.awt.Color(51, 153, 255));
        btnHienThiNVNghi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHienThiNVNghi.setForeground(new java.awt.Color(255, 255, 255));
        btnHienThiNVNghi.setText("Hiển thị");
        btnHienThiNVNghi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHienThiNVNghiActionPerformed(evt);
            }
        });

        btnSearchNVNghi.setBackground(new java.awt.Color(51, 153, 255));
        btnSearchNVNghi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearchNVNghi.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchNVNghi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search-interface-symbol.png"))); // NOI18N
        btnSearchNVNghi.setText("Tìm kiếm ");
        btnSearchNVNghi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchNVNghiActionPerformed(evt);
            }
        });

        txtSearchNVNghi.setForeground(new java.awt.Color(153, 153, 153));
        txtSearchNVNghi.setText("Nhập mã hoặc tên nhân viên");
        txtSearchNVNghi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchNVNghiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchNVNghiFocusLost(evt);
            }
        });
        txtSearchNVNghi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtSearchNVNghiMouseReleased(evt);
            }
        });
        txtSearchNVNghi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchNVNghiKeyReleased(evt);
            }
        });

        buttonGroup3.add(rdTheoMaNVNghi);
        rdTheoMaNVNghi.setText("Sắp xếp theo mã");
        rdTheoMaNVNghi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdTheoMaNVNghiMouseClicked(evt);
            }
        });

        buttonGroup3.add(rdTheoTenNVNghi);
        rdTheoTenNVNghi.setText("Sắp xếp theo tên");
        rdTheoTenNVNghi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdTheoTenNVNghiMouseClicked(evt);
            }
        });

        buttonGroup3.add(rdTheoTuoiNhanVienNghi);
        rdTheoTuoiNhanVienNghi.setText("Sắp xếp theo tuổi");
        rdTheoTuoiNhanVienNghi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdTheoTuoiNhanVienNghiMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHienThiNVNghi, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addComponent(btnSearchNVNghi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchNVNghi, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(rdTheoMaNVNghi)
                        .addGap(112, 112, 112)
                        .addComponent(rdTheoTenNVNghi)
                        .addGap(112, 112, 112)
                        .addComponent(rdTheoTuoiNhanVienNghi, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel35Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnHienThiNVNghi, btnSearchNVNghi});

        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchNVNghi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdTheoMaNVNghi)
                    .addComponent(rdTheoTenNVNghi)
                    .addComponent(btnSearchNVNghi)
                    .addComponent(rdTheoTuoiNhanVienNghi))
                .addGap(18, 18, 18)
                .addComponent(btnHienThiNVNghi, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jPanel35Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnHienThiNVNghi, btnSearchNVNghi});

        btnKhoiPhuc.setBackground(new java.awt.Color(51, 153, 255));
        btnKhoiPhuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnKhoiPhuc.setForeground(new java.awt.Color(255, 255, 255));
        btnKhoiPhuc.setText("Khôi phục");
        btnKhoiPhuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoiPhucActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 867, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(btnKhoiPhuc))
                    .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(221, 221, 221))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(181, 181, 181)
                        .addComponent(btnKhoiPhuc)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("Danh sách nhân viên đã nghỉ", jPanel34);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(187, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Quản lý nhân viên", jPanel10);

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setAutoscrolls(true);
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnSearchHD.setBackground(new java.awt.Color(51, 153, 255));
        btnSearchHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearchHD.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search-interface-symbol.png"))); // NOI18N
        btnSearchHD.setText("Tìm kiếm ");
        btnSearchHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchHDActionPerformed(evt);
            }
        });

        btnHienThiHD.setBackground(new java.awt.Color(51, 153, 255));
        btnHienThiHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHienThiHD.setForeground(new java.awt.Color(255, 255, 255));
        btnHienThiHD.setText("Hiển thị");
        btnHienThiHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHienThiHDActionPerformed(evt);
            }
        });

        txtSearchHD.setForeground(new java.awt.Color(153, 153, 153));
        txtSearchHD.setText("Nhập mã hoá đơn cần tìm");
        txtSearchHD.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchHDFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchHDFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHienThiHD, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(btnSearchHD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchHD, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnHienThiHD, btnSearchHD});

        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchHD, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchHD))
                .addGap(18, 18, 18)
                .addComponent(btnHienThiHD, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnHienThiHD, btnSearchHD});

        tblQLSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sản phẩm", "Chất liệu", "Kích thước", "Màu sắc", "Mẫu", "Hãng", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        jScrollPane6.setViewportView(tblQLSanPham);

        tblQLHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hoá đơn", "Ngày tạo", "Trạng thái", "Mã voucher", "Mã nhân viên", "Ngày hoàn thành", "Loại thanh toán", "Mã khách hàng", "Tổng tiền"
            }
        ));
        tblQLHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLHoaDonMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblQLHoaDon);

        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtBatDauHD.setForeground(new java.awt.Color(153, 153, 153));
        txtBatDauHD.setText("dd-mm-yyyy");
        txtBatDauHD.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBatDauHDFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBatDauHDFocusLost(evt);
            }
        });

        jLabel10.setText("Bắt đầu");

        jLabel11.setText("Kết thúc");

        txtKetThucHD.setForeground(new java.awt.Color(153, 153, 153));
        txtKetThucHD.setText("dd-mm-yyyy");
        txtKetThucHD.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtKetThucHDFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtKetThucHDFocusLost(evt);
            }
        });

        btnLocHD.setBackground(new java.awt.Color(51, 153, 255));
        btnLocHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLocHD.setForeground(new java.awt.Color(255, 255, 255));
        btnLocHD.setText("Lọc");
        btnLocHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocHDActionPerformed(evt);
            }
        });

        btnBoLocHD.setBackground(new java.awt.Color(51, 153, 255));
        btnBoLocHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBoLocHD.setForeground(new java.awt.Color(255, 255, 255));
        btnBoLocHD.setText("Bỏ lọc");
        btnBoLocHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBoLocHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBatDauHD, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKetThucHD, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(btnLocHD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(btnBoLocHD)
                .addGap(47, 47, 47))
        );

        jPanel16Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBoLocHD, btnLocHD});

        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBatDauHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(23, 23, 23)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtKetThucHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLocHD)
                    .addComponent(btnBoLocHD))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh sách hoá đơn", jPanel3);

        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnSearchHuy.setBackground(new java.awt.Color(51, 153, 255));
        btnSearchHuy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearchHuy.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search-interface-symbol.png"))); // NOI18N
        btnSearchHuy.setText("Tìm kiếm ");
        btnSearchHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchHuyActionPerformed(evt);
            }
        });

        btnHienThiHuy.setBackground(new java.awt.Color(51, 153, 255));
        btnHienThiHuy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHienThiHuy.setForeground(new java.awt.Color(255, 255, 255));
        btnHienThiHuy.setText("Hiển thị");
        btnHienThiHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHienThiHuyActionPerformed(evt);
            }
        });

        txtSearchHuy.setForeground(new java.awt.Color(153, 153, 153));
        txtSearchHuy.setText("Nhập mã hoá đơn cần tìm");
        txtSearchHuy.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchHuyFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchHuyFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHienThiHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(btnSearchHuy)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );

        jPanel13Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnHienThiHuy, btnSearchHuy});

        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchHuy))
                .addGap(18, 18, 18)
                .addComponent(btnHienThiHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jPanel13Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnHienThiHuy, btnSearchHuy});

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtBatDauHuy.setForeground(new java.awt.Color(153, 153, 153));
        txtBatDauHuy.setText("dd-mm-yyyy");
        txtBatDauHuy.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBatDauHuyFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBatDauHuyFocusLost(evt);
            }
        });

        jLabel6.setText("Bắt đầu");

        jLabel7.setText("Kết thúc");

        txtKetThucHuy.setForeground(new java.awt.Color(153, 153, 153));
        txtKetThucHuy.setText("dd-mm-yyyy");
        txtKetThucHuy.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtKetThucHuyFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtKetThucHuyFocusLost(evt);
            }
        });

        btnLocHuy.setBackground(new java.awt.Color(51, 153, 255));
        btnLocHuy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLocHuy.setForeground(new java.awt.Color(255, 255, 255));
        btnLocHuy.setText("Lọc");
        btnLocHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocHuyActionPerformed(evt);
            }
        });

        btnBoLocHuy.setBackground(new java.awt.Color(51, 153, 255));
        btnBoLocHuy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBoLocHuy.setForeground(new java.awt.Color(255, 255, 255));
        btnBoLocHuy.setText("Bỏ lọc");
        btnBoLocHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBoLocHuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBatDauHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKetThucHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(btnLocHuy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(btnBoLocHuy)
                .addGap(47, 47, 47))
        );

        jPanel14Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBoLocHuy, btnLocHuy});

        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBatDauHuy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(23, 23, 23)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtKetThucHuy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLocHuy)
                    .addComponent(btnBoLocHuy))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        tblQLHoaDonHuy.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hoá đơn", "Ngày tạo", "Trạng thái", "Mã voucher", "Mã nhân viên", "Ngày hoàn thành", "Loại thanh toán", "Mã khách hàng", "Tổng tiền"
            }
        ));
        tblQLHoaDonHuy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLHoaDonHuyMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblQLHoaDonHuy);

        tblSanPhamHuy.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sản phẩm", "Chất liệu", "Kích thước", "Màu sắc", "Mẫu", "Hãng", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        jScrollPane8.setViewportView(tblSanPhamHuy);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)))
                .addGap(19, 19, 19))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh sách hoá đơn đã huỷ", jPanel4);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 957, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(204, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Quản lý hoá đơn", jPanel6);

        jTabbedPane4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane4.setForeground(new java.awt.Color(0, 51, 255));
        jTabbedPane4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel38.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel40.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel90.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel90.setText("Mã sản phẩm:");

        jLabel91.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel91.setText("Tên sản phẩm:");

        jLabel92.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel92.setText("Mẫu:");

        jLabel93.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel93.setText("Hãng:");

        btnNewTTSP.setBackground(new java.awt.Color(51, 102, 255));
        btnNewTTSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNewTTSP.setForeground(new java.awt.Color(255, 255, 255));
        btnNewTTSP.setText("Làm mới");
        btnNewTTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewTTSPActionPerformed(evt);
            }
        });

        btnUpdateSP.setBackground(new java.awt.Color(51, 102, 255));
        btnUpdateSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdateSP.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateSP.setText("Sửa");
        btnUpdateSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateSPActionPerformed(evt);
            }
        });

        btnAddSanPham.setBackground(new java.awt.Color(51, 102, 255));
        btnAddSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnAddSanPham.setText("Thêm");
        btnAddSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSanPhamActionPerformed(evt);
            }
        });

        btnUpdateSP1.setBackground(new java.awt.Color(51, 102, 255));
        btnUpdateSP1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdateSP1.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateSP1.setText(" Thêm chi tiết sản phẩm");
        btnUpdateSP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateSP1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel90)
                            .addComponent(jLabel91))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTenSPTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(267, 267, 267)
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel92)
                            .addComponent(jLabel93))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMauTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHangTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGap(437, 437, 437)
                        .addComponent(btnNewTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAddSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdateSP, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdateSP1)))
                .addContainerGap(243, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel90)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel92)
                    .addComponent(txtMauTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel91)
                    .addComponent(txtTenSPTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel93)
                    .addComponent(txtHangTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewTTSP)
                    .addComponent(btnAddSanPham)
                    .addComponent(btnUpdateSP)
                    .addComponent(btnUpdateSP1))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLabel85.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(51, 153, 255));
        jLabel85.setText("Thông tin sản phẩm");

        jPanel43.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblSanPhamTTSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Mẫu", "Hãng"
            }
        ));
        tblSanPhamTTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamTTSPMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(tblSanPhamTTSP);

        btnTimKiemMaSP.setBackground(new java.awt.Color(51, 153, 255));
        btnTimKiemMaSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiemMaSP.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemMaSP.setText("Tìm Kiếm");
        btnTimKiemMaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemMaSPActionPerformed(evt);
            }
        });

        jLabel83.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel83.setText("Tìm kiếm mã sản phẩm:");

        buttonGroup7.add(rdoSXTheoMaSPTTSP);
        rdoSXTheoMaSPTTSP.setText("Sắp xếp theo mã");
        rdoSXTheoMaSPTTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoSXTheoMaSPTTSPMouseClicked(evt);
            }
        });

        buttonGroup7.add(rdoSXTheoTenTTSP);
        rdoSXTheoTenTTSP.setText("Sắp xếp theo tên");
        rdoSXTheoTenTTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoSXTheoTenTTSPMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 1337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel83)
                .addGap(18, 18, 18)
                .addComponent(txtTimKiemMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnTimKiemMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93)
                .addComponent(rdoSXTheoMaSPTTSP)
                .addGap(18, 18, 18)
                .addComponent(rdoSXTheoTenTTSP)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel83)
                    .addComponent(txtTimKiemMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemMaSP)
                    .addComponent(rdoSXTheoMaSPTTSP)
                    .addComponent(rdoSXTheoTenTTSP))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(51, 153, 255));
        jLabel46.setText("Danh sách sản phẩm");

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jLabel85))
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel46)))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel85)
                .addGap(18, 18, 18)
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(170, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Thông tin sản phẩm", jPanel38);

        jPanel24.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 153, 255));
        jLabel31.setText("Thông tin chi tiết sản phẩm");

        jPanel25.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setText("Mã SPCT:");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setText("Tên sản phẩm:");

        lblHinhAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinhAnh.setText("Hình ảnh");
        lblHinhAnh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhAnhMouseClicked(evt);
            }
        });

        txtTenSPSPCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSPSPCTActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setText("Tên nhà cung cấp:");

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel37.setText("Số lượng:");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel38.setText("Màu sắc:");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel39.setText("Kích thước:");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel40.setText("Mẫu:");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel41.setText("Chất liệu:");

        cboKichThuocTTSP.setToolTipText("");

        cboMauTTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMauTTSPActionPerformed(evt);
            }
        });

        jLabel75.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel75.setText("Hãng:");

        txtNewTTSP.setBackground(new java.awt.Color(51, 153, 255));
        txtNewTTSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtNewTTSP.setForeground(new java.awt.Color(255, 255, 255));
        txtNewTTSP.setText("Làm mới");
        txtNewTTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewTTSPActionPerformed(evt);
            }
        });

        btnAddSPCT.setBackground(new java.awt.Color(51, 153, 255));
        btnAddSPCT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddSPCT.setForeground(new java.awt.Color(255, 255, 255));
        btnAddSPCT.setText("Thêm");
        btnAddSPCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSPCTActionPerformed(evt);
            }
        });

        btnSuaChiTietSanPham.setBackground(new java.awt.Color(51, 153, 255));
        btnSuaChiTietSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaChiTietSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaChiTietSanPham.setText("Sửa");
        btnSuaChiTietSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaChiTietSanPhamMouseClicked(evt);
            }
        });

        cboTenNCCTTSP.setToolTipText("");
        cboTenNCCTTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenNCCTTSPActionPerformed(evt);
            }
        });

        btnThemThuocTinh.setBackground(new java.awt.Color(51, 153, 255));
        btnThemThuocTinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemThuocTinh.setForeground(new java.awt.Color(255, 255, 255));
        btnThemThuocTinh.setText("Thêm thuộc tính");
        btnThemThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemThuocTinhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel32)
                            .addComponent(jLabel33)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel75, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(18, 18, 18)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addComponent(txtSoLuongTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtHang, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaSPCT, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenSPSPCT, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboTenNCCTTSP, javax.swing.GroupLayout.Alignment.LEADING, 0, 320, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                        .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(160, 160, 160)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel39)
                                .addComponent(jLabel38)
                                .addComponent(jLabel41))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jLabel40)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboMauTTSP, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboKichThuocTTSP, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboMauSacTTSP, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboChatLieuTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(105, 105, 105))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtNewTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSuaChiTietSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnThemThuocTinh)
                .addGap(467, 467, 467))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel33)
                                    .addComponent(txtTenSPSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel35)
                                    .addComponent(cboTenNCCTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel75)))
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel25Layout.createSequentialGroup()
                                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cboMauSacTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel38))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cboKichThuocTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel39))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cboChatLieuTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel41))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel40)
                                            .addComponent(cboMauTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtMaSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel32)))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(txtSoLuongTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNewTTSP)
                    .addComponent(btnSuaChiTietSanPham)
                    .addComponent(btnAddSPCT)
                    .addComponent(btnThemThuocTinh))
                .addGap(23, 23, 23))
        );

        jPanel26.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel42.setText("Tìm kiếm mã sản phẩm chi tiết:");

        buttonGroup6.add(rdoSXMaTTSP);
        rdoSXMaTTSP.setText("Sắp xếp theo mã");
        rdoSXMaTTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoSXMaTTSPMouseClicked(evt);
            }
        });

        buttonGroup6.add(rdoSXTenTTSP);
        rdoSXTenTTSP.setText("Sắp xếp theo tên");
        rdoSXTenTTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoSXTenTTSPMouseClicked(evt);
            }
        });

        tblSanPhamCTSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SPCT", "Tên sản phẩm", "Tên NCC", "Hãng", "Số lượng", "Màu sắc", "Kích thước", "Mẫu", "Chất liệu", "Hình ảnh"
            }
        ));
        tblSanPhamCTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamCTSPMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tblSanPhamCTSP);

        btnTimKiemSP.setBackground(new java.awt.Color(51, 153, 255));
        btnTimKiemSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiemSP.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemSP.setText("Tìm Kiếm");
        btnTimKiemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jLabel42)
                .addGap(18, 18, 18)
                .addComponent(txtTimKiemSPTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(rdoSXMaTTSP)
                .addGap(52, 52, 52)
                .addComponent(rdoSXTenTTSP)
                .addGap(125, 125, 125))
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(txtTimKiemSPTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoSXMaTTSP)
                    .addComponent(rdoSXTenTTSP)
                    .addComponent(btnTimKiemSP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(51, 153, 255));
        jLabel43.setText("Quản lý chi tiết sản phẩm");

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Remove");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel43))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jTabbedPane4.addTab("Chi tiết sản phẩm", jPanel24);

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(51, 153, 255));
        jLabel44.setText("Thuộc tính sản phẩm");

        jPanel28.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel45.setText("Mã màu sắc:");

        txtMaMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaMauSacActionPerformed(evt);
            }
        });

        btnNewMauSac.setBackground(new java.awt.Color(51, 153, 255));
        btnNewMauSac.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNewMauSac.setForeground(new java.awt.Color(255, 255, 255));
        btnNewMauSac.setText("Làm mới");
        btnNewMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewMauSacActionPerformed(evt);
            }
        });

        btnSuaMauSacTTSP.setBackground(new java.awt.Color(51, 153, 255));
        btnSuaMauSacTTSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaMauSacTTSP.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaMauSacTTSP.setText("Sửa");
        btnSuaMauSacTTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaMauSacTTSPActionPerformed(evt);
            }
        });

        btnXoaMauSacTTSP.setBackground(new java.awt.Color(51, 153, 255));
        btnXoaMauSacTTSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaMauSacTTSP.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaMauSacTTSP.setText("Xóa");
        btnXoaMauSacTTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaMauSacTTSPActionPerformed(evt);
            }
        });

        jLabel76.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel76.setText("Tên màu sắc:");

        txtTenMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenMauSacActionPerformed(evt);
            }
        });

        btnAddMauSac.setBackground(new java.awt.Color(51, 153, 255));
        btnAddMauSac.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddMauSac.setForeground(new java.awt.Color(255, 255, 255));
        btnAddMauSac.setText("Thêm");
        btnAddMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMauSacActionPerformed(evt);
            }
        });

        jLabel77.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(51, 153, 255));
        jLabel77.setText("Màu sắc");

        tblMauSacTTSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã màu sắc", "Tên màu sắc"
            }
        ));
        tblMauSacTTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMauSacTTSPMouseClicked(evt);
            }
        });
        jScrollPane19.setViewportView(tblMauSacTTSP);

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane19)
                            .addGroup(jPanel28Layout.createSequentialGroup()
                                .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                        .addGap(0, 59, Short.MAX_VALUE)
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                                .addComponent(jLabel45)
                                .addGap(18, 18, 18)
                                .addComponent(txtMaMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(152, 152, 152)
                                .addComponent(jLabel76)
                                .addGap(18, 18, 18)
                                .addComponent(txtTenMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(139, 139, 139))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                                .addComponent(btnNewMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(btnAddMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSuaMauSacTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnXoaMauSacTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(474, 474, 474))))))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel77)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaMauSacTTSP)
                    .addComponent(btnNewMauSac)
                    .addComponent(btnAddMauSac)
                    .addComponent(btnSuaMauSacTTSP))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel36.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel78.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel78.setText("Mã kích thước:");

        txtMaKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKichThuocActionPerformed(evt);
            }
        });

        btnUpdateKichThuoc.setBackground(new java.awt.Color(51, 153, 255));
        btnUpdateKichThuoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdateKichThuoc.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateKichThuoc.setText("Sửa");
        btnUpdateKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateKichThuocActionPerformed(evt);
            }
        });

        btnXoaKichThuoc.setBackground(new java.awt.Color(51, 153, 255));
        btnXoaKichThuoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaKichThuoc.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaKichThuoc.setText("Xóa");
        btnXoaKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKichThuocActionPerformed(evt);
            }
        });

        jLabel79.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel79.setText("Tên kích thước:");

        txtTenKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKichThuocActionPerformed(evt);
            }
        });

        btnAddKichThuocTTSP.setBackground(new java.awt.Color(51, 153, 255));
        btnAddKichThuocTTSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddKichThuocTTSP.setForeground(new java.awt.Color(255, 255, 255));
        btnAddKichThuocTTSP.setText("Thêm");
        btnAddKichThuocTTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddKichThuocTTSPActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(51, 153, 255));
        jLabel34.setText("Kích thước");

        btnNewKichThuoc.setBackground(new java.awt.Color(51, 153, 255));
        btnNewKichThuoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNewKichThuoc.setForeground(new java.awt.Color(255, 255, 255));
        btnNewKichThuoc.setText("Làm mới");
        btnNewKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewKichThuocActionPerformed(evt);
            }
        });

        tblKichThuocTTSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã kích thước", "Tên kích thước"
            }
        ));
        tblKichThuocTTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKichThuocTTSPMouseClicked(evt);
            }
        });
        jScrollPane20.setViewportView(tblKichThuocTTSP);

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane20)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel36Layout.createSequentialGroup()
                                .addGap(433, 433, 433)
                                .addComponent(btnNewKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAddKichThuocTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdateKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnXoaKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel34))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel78)
                .addGap(18, 18, 18)
                .addComponent(txtMaKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                .addComponent(jLabel79)
                .addGap(18, 18, 18)
                .addComponent(txtTenKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addComponent(jLabel34)
                .addGap(12, 12, 12)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaKichThuoc)
                    .addComponent(btnNewKichThuoc)
                    .addComponent(btnAddKichThuocTTSP)
                    .addComponent(btnUpdateKichThuoc))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel37.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel80.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel80.setText("Mã chất liệu:");

        txtMaChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaChatLieuActionPerformed(evt);
            }
        });

        jLabel81.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel81.setText("Tên chất liệu:");

        txtTenChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenChatLieuActionPerformed(evt);
            }
        });

        jLabel82.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(51, 153, 255));
        jLabel82.setText("Chất liệu");

        jButton21.setBackground(new java.awt.Color(51, 153, 255));
        jButton21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton21.setForeground(new java.awt.Color(255, 255, 255));
        jButton21.setText("Làm mới");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        btnAddChatLieu.setBackground(new java.awt.Color(51, 153, 255));
        btnAddChatLieu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddChatLieu.setForeground(new java.awt.Color(255, 255, 255));
        btnAddChatLieu.setText("Thêm");
        btnAddChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddChatLieuActionPerformed(evt);
            }
        });

        btnUpdateChatLieu.setBackground(new java.awt.Color(51, 153, 255));
        btnUpdateChatLieu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdateChatLieu.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateChatLieu.setText("Sửa");
        btnUpdateChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateChatLieuActionPerformed(evt);
            }
        });

        btnXoaChatLieu.setBackground(new java.awt.Color(51, 153, 255));
        btnXoaChatLieu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaChatLieu.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaChatLieu.setText("Xóa");
        btnXoaChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaChatLieuActionPerformed(evt);
            }
        });

        tblChatLieuTTSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã chất liệu", "Tên chất liệu"
            }
        ));
        tblChatLieuTTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChatLieuTTSPMouseClicked(evt);
            }
        });
        jScrollPane21.setViewportView(tblChatLieuTTSP);

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1268, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane21))
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(441, 441, 441)
                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdateChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXoaChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jLabel80)
                .addGap(18, 18, 18)
                .addComponent(txtMaChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel81)
                .addGap(18, 18, 18)
                .addComponent(txtTenChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel82)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaChatLieu)
                    .addComponent(jButton21)
                    .addComponent(btnAddChatLieu)
                    .addComponent(btnUpdateChatLieu))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel27Layout.createSequentialGroup()
                            .addGap(26, 26, 26)
                            .addComponent(jLabel44))
                        .addGroup(jPanel27Layout.createSequentialGroup()
                            .addGap(73, 73, 73)
                            .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(129, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44)
                .addGap(18, 18, 18)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Thuộc tính sản phẩm", jPanel27);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 783, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Quản lý sản phẩm", jPanel9);

        jTabbedPane5.setForeground(new java.awt.Color(51, 153, 255));
        jTabbedPane5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel2.setForeground(new java.awt.Color(51, 153, 255));
        jPanel2.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 153, 255));
        jLabel2.setText("Voucher");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Tìm kiếm voucher");

        txtTKVoucher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTKVoucherKeyReleased(evt);
            }
        });

        buttonGroup2.add(rdSXTMaVoucher);
        rdSXTMaVoucher.setText("Sắp xếp theo mã");
        rdSXTMaVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdSXTMaVoucherActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdSXTTenVoucher);
        rdSXTTenVoucher.setText("Sắp xếp theo tên");
        rdSXTTenVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdSXTTenVoucherActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdSXTNgayVoucher);
        rdSXTNgayVoucher.setText("Sắp xếp theo ngày tạo");
        rdSXTNgayVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdSXTNgayVoucherActionPerformed(evt);
            }
        });

        tblVoucher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã voucher", "Tên voucher", "Số lượng", "Ngày bắt đầu", "Ngày hết hạn", "Số tiền giảm", "Số tiền yêu cầu"
            }
        ));
        tblVoucher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVoucherMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblVoucher);

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel47.setText("Thông tin voucher");

        jLabel48.setText("Mã voucher");

        jLabel49.setText("Tên voucher");

        jLabel50.setText("Số lượng");

        jLabel51.setText("Ngày bắt đầu");

        jLabel52.setText("Ngày hết hạn");

        btnThemVoucher.setBackground(new java.awt.Color(51, 153, 255));
        btnThemVoucher.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemVoucher.setForeground(new java.awt.Color(255, 255, 255));
        btnThemVoucher.setText("Thêm");
        btnThemVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemVoucherActionPerformed(evt);
            }
        });

        btnSuaVoucher.setBackground(new java.awt.Color(51, 153, 255));
        btnSuaVoucher.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaVoucher.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaVoucher.setText("Sửa");
        btnSuaVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaVoucherActionPerformed(evt);
            }
        });

        jLabel53.setText("Số tiền giảm");

        jLabel54.setText("Số tiền yêu cầu");

        jLabel64.setText("Tìm kiếm ngày bắt đầu");

        buttonGroup2.add(rdAllVoucher);
        rdAllVoucher.setText("Tất cả");
        rdAllVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdAllVoucherActionPerformed(evt);
            }
        });

        jLabel66.setText("Tìm kiếm ngày kết thúc");

        btnTKTNVoucher.setBackground(new java.awt.Color(51, 153, 255));
        btnTKTNVoucher.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTKTNVoucher.setForeground(new java.awt.Color(255, 255, 255));
        btnTKTNVoucher.setText("Tìm kiếm theo ngày");
        btnTKTNVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKTNVoucherActionPerformed(evt);
            }
        });

        btnNewFormVoucher.setBackground(new java.awt.Color(51, 153, 255));
        btnNewFormVoucher.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNewFormVoucher.setForeground(new java.awt.Color(255, 255, 255));
        btnNewFormVoucher.setText("Làm mới");
        btnNewFormVoucher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNewFormVoucherMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel47)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel52)
                            .addComponent(jLabel51)
                            .addComponent(jLabel50)
                            .addComponent(jLabel49)
                            .addComponent(jLabel48)
                            .addComponent(jLabel54)
                            .addComponent(jLabel53))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtSoTienYCVoucher, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenVoucher, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSoLuongVoucher, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTKVoucher, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                            .addComponent(txtNKetThucVoucher, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNBatDauVoucher, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaVoucher)
                            .addComponent(txtSoTienGiamVoucher))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdSXTMaVoucher)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdSXTTenVoucher)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnThemVoucher, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSuaVoucher, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnNewFormVoucher, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdSXTNgayVoucher)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdAllVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel66)
                                    .addComponent(jLabel64))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTKNKTVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTKNBDVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTKTNVoucher)))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1278, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(261, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTKVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdSXTMaVoucher)
                    .addComponent(rdSXTTenVoucher)
                    .addComponent(rdSXTNgayVoucher)
                    .addComponent(rdAllVoucher))
                .addGap(18, 18, 18)
                .addComponent(jLabel47)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(txtMaVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemVoucher)
                    .addComponent(jLabel64)
                    .addComponent(txtTKNBDVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(txtTenVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaVoucher)
                    .addComponent(jLabel66)
                    .addComponent(txtTKNKTVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(txtSoLuongVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTKTNVoucher)
                    .addComponent(btnNewFormVoucher))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(txtNBatDauVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(txtNKetThucVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(txtSoTienGiamVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(txtSoTienYCVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(185, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Voucher", jPanel2);

        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(51, 153, 255));
        jLabel55.setText("Khuyến mãi");

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel56.setText("Tìm kiếm khuyến mãi");

        buttonGroup5.add(rdSXTMaKhuyenMai);
        rdSXTMaKhuyenMai.setText("Sắp xếp theo mã");
        rdSXTMaKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdSXTMaKhuyenMaiActionPerformed(evt);
            }
        });

        buttonGroup5.add(rdSXTTenKhuyenMai);
        rdSXTTenKhuyenMai.setText("Sắp xếp theo tên");
        rdSXTTenKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdSXTTenKhuyenMaiActionPerformed(evt);
            }
        });

        buttonGroup5.add(rdSXTNgayKhuyenMai);
        rdSXTNgayKhuyenMai.setText("Sắp xếp theo ngày tạo");
        rdSXTNgayKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdSXTNgayKhuyenMaiActionPerformed(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel57.setText("Thông tin khuyến mãi");

        jLabel58.setText("Mã khuyến mãi");

        btnThemKhuyenMai.setBackground(new java.awt.Color(51, 153, 255));
        btnThemKhuyenMai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemKhuyenMai.setForeground(new java.awt.Color(255, 255, 255));
        btnThemKhuyenMai.setText("Thêm");
        btnThemKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKhuyenMaiActionPerformed(evt);
            }
        });

        btnSuaKhuyenMai.setBackground(new java.awt.Color(51, 153, 255));
        btnSuaKhuyenMai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaKhuyenMai.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaKhuyenMai.setText("Sửa");
        btnSuaKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaKhuyenMaiActionPerformed(evt);
            }
        });

        jLabel59.setText("Tên khuyến mãi");

        jLabel61.setText("Ngày bắt đầu");

        jLabel62.setText("Ngày hết hạn");

        jLabel63.setText("Giảm giá(%)");

        tblKhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã khuyến mãi", "Tên khuyến mãi", "Ngày bắt đầu", "Ngày hết hạn", "Giảm giá(%)", "Mã sản phẩm"
            }
        ));
        tblKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhuyenMaiMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(tblKhuyenMai);

        buttonGroup5.add(rdAllKhuyenMai);
        rdAllKhuyenMai.setText("Tất cả");
        rdAllKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdAllKhuyenMaiActionPerformed(evt);
            }
        });

        jLabel65.setText("Tìm kiếm theo ngày đầu");

        jLabel67.setText("Tìm kiếm ngày kết thúc");

        btnTKTKNKM.setBackground(new java.awt.Color(51, 153, 255));
        btnTKTKNKM.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTKTKNKM.setForeground(new java.awt.Color(255, 255, 255));
        btnTKTKNKM.setText("Tìm kiếm theo khoảng ngày");
        btnTKTKNKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKTKNKMActionPerformed(evt);
            }
        });

        btnNewFormKM.setBackground(new java.awt.Color(51, 153, 255));
        btnNewFormKM.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNewFormKM.setForeground(new java.awt.Color(255, 255, 255));
        btnNewFormKM.setText("Làm mới");
        btnNewFormKM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNewFormKMMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel55)
                    .addComponent(jLabel57)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel56)
                            .addComponent(jLabel62)
                            .addComponent(jLabel61)
                            .addComponent(jLabel59)
                            .addComponent(jLabel58)
                            .addComponent(jLabel63))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtTenKhuyenMai, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTKKhuyenMai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                            .addComponent(txtNKetThucKhuyenMai, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNBatDauKhuyenMai, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaKhuyenMai)
                            .addComponent(txtGiamGiaKhuyenMai))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdSXTMaKhuyenMai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdSXTTenKhuyenMai)
                            .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnThemKhuyenMai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSuaKhuyenMai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnNewFormKM, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel30Layout.createSequentialGroup()
                                .addComponent(rdSXTNgayKhuyenMai)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdAllKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel30Layout.createSequentialGroup()
                                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel67)
                                    .addComponent(jLabel65))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTKNKTKhuyeMai, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTKNBDKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTKTKNKM)))))
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 1273, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(266, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(txtTKKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdSXTMaKhuyenMai)
                    .addComponent(rdSXTTenKhuyenMai)
                    .addComponent(rdSXTNgayKhuyenMai)
                    .addComponent(rdAllKhuyenMai))
                .addGap(18, 18, 18)
                .addComponent(jLabel57)
                .addGap(18, 18, 18)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel58)
                            .addComponent(txtMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemKhuyenMai))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel59)
                            .addComponent(txtTenKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSuaKhuyenMai)))
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel65)
                            .addComponent(txtTKNBDKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel67)
                            .addComponent(txtTKNKTKhuyeMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTKTKNKM)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel61)
                            .addComponent(txtNBatDauKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNewFormKM))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel62)
                            .addComponent(txtNKetThucKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel63)
                            .addComponent(txtGiamGiaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(59, 59, 59)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(224, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Khuyến mãi", jPanel30);

        jLabel68.setText("Tên sản phẩm");

        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(51, 153, 255));
        jLabel69.setText("Thêm khuyến mãi vào sản phẩm");

        jLabel70.setText("Mã sản phẩm");

        jLabel71.setText("Trạng thái");

        cbTrangThaiThemKM.setText("Thêm khuyến mãi vào sản phẩm");

        tblSPKM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Trạng thái"
            }
        ));
        tblSPKM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPKMMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(tblSPKM);

        btnThemSPKM.setBackground(new java.awt.Color(51, 153, 255));
        btnThemSPKM.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemSPKM.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSPKM.setText("Thêm");
        btnThemSPKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPKMActionPerformed(evt);
            }
        });

        btnXoaSPKM.setBackground(new java.awt.Color(51, 153, 255));
        btnXoaSPKM.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaSPKM.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaSPKM.setText("Xoá");

        tblKMChonSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã khuyến mãi", "Tên khuyến mãi", "Giảm giá(%)"
            }
        ));
        tblKMChonSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKMChonSPMouseClicked(evt);
            }
        });
        jScrollPane16.setViewportView(tblKMChonSP);

        jLabel72.setText("Mã khuyến mãi");

        jLabel73.setText("Tên khuyến mãi");

        jLabel74.setText("Giảm giá(%)");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel69)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel31Layout.createSequentialGroup()
                                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel70)
                                            .addComponent(jLabel71))
                                        .addGap(50, 50, 50)
                                        .addComponent(cbTrangThaiThemKM))
                                    .addGroup(jPanel31Layout.createSequentialGroup()
                                        .addComponent(jLabel68)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtMaSPThemKM, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                                            .addComponent(txtTenSPKM))
                                        .addGap(162, 162, 162)
                                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnThemSPKM, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnXoaSPKM, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(159, 159, 159)
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel73)
                                    .addComponent(jLabel74)
                                    .addComponent(jLabel72))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTenKMChonSP, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGiamGiaKMChonSP, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaKMChonSP, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel69)
                .addGap(18, 18, 18)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel68)
                            .addComponent(txtTenSPKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemSPKM)
                            .addComponent(txtMaKMChonSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel72))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel70)
                            .addComponent(txtMaSPThemKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoaSPKM)
                            .addComponent(jLabel73)
                            .addComponent(txtTenKMChonSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel71)
                            .addComponent(cbTrangThaiThemKM)
                            .addComponent(jLabel74)
                            .addComponent(txtGiamGiaKMChonSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(398, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Chọn sản phẩm khuyến mãi", jPanel31);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane5)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane5)
        );

        jTabbedPane2.addTab("Quản lý ưu đãi", jPanel5);

        jLabel99.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(51, 153, 255));
        jLabel99.setText("Quản lý đơn giá");

        jLabel100.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(51, 153, 255));
        jLabel100.setText("Lịch sử đơn giá");

        jPanel42.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel94.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel94.setText("Mã đơn giá:");

        jLabel95.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel95.setText("Giá đầu:");

        jLabel96.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel96.setText("Giá sau:");

        txtTGBatDauLSDG.setForeground(new java.awt.Color(153, 153, 153));
        txtTGBatDauLSDG.setText("dd-mm-yyyy");
        txtTGBatDauLSDG.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTGBatDauLSDGFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTGBatDauLSDGFocusLost(evt);
            }
        });
        txtTGBatDauLSDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTGBatDauLSDGActionPerformed(evt);
            }
        });

        jLabel97.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel97.setText("Thời gian bắt đầu:");

        jLabel98.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel98.setText("Thời gian kết thúc:");

        txtTGKetThucLSDG.setForeground(new java.awt.Color(153, 153, 153));
        txtTGKetThucLSDG.setText("dd-mm-yyyy");
        txtTGKetThucLSDG.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTGKetThucLSDGFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTGKetThucLSDGFocusLost(evt);
            }
        });
        txtTGKetThucLSDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTGKetThucLSDGActionPerformed(evt);
            }
        });

        btnNewLSDG.setBackground(new java.awt.Color(51, 102, 255));
        btnNewLSDG.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNewLSDG.setForeground(new java.awt.Color(255, 255, 255));
        btnNewLSDG.setText("Làm mới");
        btnNewLSDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewLSDGActionPerformed(evt);
            }
        });

        btnAddLSDG.setBackground(new java.awt.Color(51, 102, 255));
        btnAddLSDG.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddLSDG.setForeground(new java.awt.Color(255, 255, 255));
        btnAddLSDG.setText("Thêm");
        btnAddLSDG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddLSDGMouseClicked(evt);
            }
        });

        btnUpdateLSDG.setBackground(new java.awt.Color(51, 102, 255));
        btnUpdateLSDG.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdateLSDG.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateLSDG.setText("Sửa");
        btnUpdateLSDG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateLSDGMouseClicked(evt);
            }
        });

        cboMaSPCTLSG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaSPCTLSGActionPerformed(evt);
            }
        });

        jLabel104.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel104.setText("Mã SPCT:");

        jLabel105.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel105.setText("Tên sản phẩm:");

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel104, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel105, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboMaSPCTLSG, 0, 244, Short.MAX_VALUE)
                    .addComponent(txtMaDonGia)
                    .addComponent(lblTenSPLSG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel98)
                    .addComponent(jLabel97)
                    .addComponent(jLabel96)
                    .addComponent(jLabel95))
                .addGap(18, 18, 18)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtGiaDau)
                    .addComponent(txtGiaSau)
                    .addComponent(txtTGBatDauLSDG)
                    .addComponent(txtTGKetThucLSDG, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(296, 296, 296)
                .addComponent(btnNewLSDG, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddLSDG, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdateLSDG, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiaSau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboMaSPCTLSG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel104, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTGBatDauLSDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel105, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTenSPLSG, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTGKetThucLSDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewLSDG)
                    .addComponent(btnAddLSDG)
                    .addComponent(btnUpdateLSDG))
                .addGap(25, 25, 25))
        );

        jPanel44.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblLichSuDonGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã đơn giá", "Mã SPCT", "Tên sản phẩm", "Giá đầu", "Giá sau", "Thời gian bắt đầu", "Thời gian kết thúc"
            }
        ));
        tblLichSuDonGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLichSuDonGiaMouseClicked(evt);
            }
        });
        jScrollPane23.setViewportView(tblLichSuDonGia);

        jLabel101.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel101.setText("Tìm kiếm theo mã đơn giá:");

        btnTKMaLSDG.setBackground(new java.awt.Color(51, 102, 255));
        btnTKMaLSDG.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTKMaLSDG.setForeground(new java.awt.Color(255, 255, 255));
        btnTKMaLSDG.setText("Tìm kiếm");
        btnTKMaLSDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKMaLSDGActionPerformed(evt);
            }
        });

        buttonGroup9.add(rdoSXMaLSDG);
        rdoSXMaLSDG.setText("Sắp xếp theo mã");
        rdoSXMaLSDG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoSXMaLSDGMouseClicked(evt);
            }
        });

        buttonGroup9.add(rdoSXNgayLSDG);
        rdoSXNgayLSDG.setText("Sắp xếp theo giá");
        rdoSXNgayLSDG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoSXNgayLSDGMouseClicked(evt);
            }
        });

        jLabel102.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel102.setText("Ngày bắt đầu:");

        jLabel103.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel103.setText("Ngày kết thúc:");

        txtTimKiemTGBDLSDG.setForeground(new java.awt.Color(153, 153, 153));
        txtTimKiemTGBDLSDG.setText("dd-mm-yyyy");
        txtTimKiemTGBDLSDG.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTimKiemTGBDLSDGFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTimKiemTGBDLSDGFocusLost(evt);
            }
        });
        txtTimKiemTGBDLSDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemTGBDLSDGActionPerformed(evt);
            }
        });

        txtTimKiemTGKTLSDG.setForeground(new java.awt.Color(153, 153, 153));
        txtTimKiemTGKTLSDG.setText("dd-mm-yyyy");
        txtTimKiemTGKTLSDG.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTimKiemTGKTLSDGFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTimKiemTGKTLSDGFocusLost(evt);
            }
        });
        txtTimKiemTGKTLSDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemTGKTLSDGActionPerformed(evt);
            }
        });

        btnTKNgayLSDG.setBackground(new java.awt.Color(51, 102, 255));
        btnTKNgayLSDG.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTKNgayLSDG.setForeground(new java.awt.Color(255, 255, 255));
        btnTKNgayLSDG.setText("Lọc");
        btnTKNgayLSDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKNgayLSDGActionPerformed(evt);
            }
        });

        btnTKNgayLSDG1.setBackground(new java.awt.Color(51, 102, 255));
        btnTKNgayLSDG1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTKNgayLSDG1.setForeground(new java.awt.Color(255, 255, 255));
        btnTKNgayLSDG1.setText("Bỏ lọc");
        btnTKNgayLSDG1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKNgayLSDG1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane23)
                .addContainerGap())
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel101)
                    .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel102)
                        .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimKiemMaDonGia)
                    .addComponent(txtTimKiemTGBDLSDG)
                    .addComponent(txtTimKiemTGKTLSDG, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnTKNgayLSDG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTKMaLSDG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTKNgayLSDG1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                .addComponent(rdoSXMaLSDG)
                .addGap(18, 18, 18)
                .addComponent(rdoSXNgayLSDG)
                .addGap(51, 51, 51))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiemMaDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTKMaLSDG)
                    .addComponent(rdoSXMaLSDG)
                    .addComponent(rdoSXNgayLSDG))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiemTGBDLSDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTKNgayLSDG))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiemTGKTLSDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTKNgayLSDG1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel41Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel41Layout.createSequentialGroup()
                            .addGap(64, 64, 64)
                            .addComponent(jLabel99))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel41Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel100)))
                .addContainerGap(549, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel99)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel100)
                .addGap(18, 18, 18)
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(129, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Lịch sử đơn giá", jPanel41);

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel11.setBackground(new java.awt.Color(255, 153, 153));

        jLabel5.setText("Tổng Doanh Thu");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(txtTongDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 21, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTongDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17.setBackground(new java.awt.Color(204, 255, 204));

        jLabel20.setText("Tổng Doanh Số Bán");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel20))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(txtTongDoanhSoBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTongDoanhSoBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel19.setBackground(new java.awt.Color(153, 153, 255));
        jPanel19.setForeground(new java.awt.Color(255, 255, 255));

        jLabel24.setText("Tổng đơn hàng");

        jLabel25.setText("Đã TT");

        jLabel26.setText("Chưa TT");

        lblThanhCong.setText("jLabel21");

        lblCTT.setText("jLabel22");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel24)
                                .addComponent(txtTongDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel25))
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblThanhCong, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblCTT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTongDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(lblThanhCong))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCTT))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jLabel4.setText("Ngày Bắt Đầu");

        jLabel19.setText("Ngày Kết Thúc");

        btnTimKiem.setText("TimKiem");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnHienThiBCTK.setBackground(new java.awt.Color(51, 51, 255));
        btnHienThiBCTK.setForeground(new java.awt.Color(255, 255, 255));
        btnHienThiBCTK.setText("Hiển Thị");
        btnHienThiBCTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHienThiBCTKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnHienThiBCTK, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(149, 149, 149)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(btnTimKiem))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel19))
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnHienThiBCTK, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))))
        );

        jPanel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblThongTinChiTietSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sản Phẩm", "SốLuong", "MãKhíchthước", "Mã màu sắc", "Đơngia", "NCC", "Chất liệu"
            }
        ));
        jScrollPane11.setViewportView(tblThongTinChiTietSanPham);

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setText("Thông tin chi tiết sản phẩm");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addComponent(jLabel30))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel30)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(156, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addGap(0, 181, Short.MAX_VALUE)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane7.addTab("Sản phẩm", jPanel22);

        jLabel21.setText("Chi Tiết Hoá Đơn");

        tblChiTietHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MaHoaDon", "NgayTao", "TrangThai", "MaVourcher", "MNV", "NgayHoanThanh", "LoaiThanhToan", "MaSanPhamChiTiet", "soLuong"
            }
        ));
        jScrollPane10.setViewportView(tblChiTietHoaDon);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(380, 380, 380)
                        .addComponent(jLabel21))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 871, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jTabbedPane7.addTab("Chi tiết hoá đơn", jPanel18);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane7))
                .addContainerGap(627, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Báo cáo thống kê", jPanel8);

        jLabel1.setFont(new java.awt.Font("Segoe WP SemiLight", 3, 48)); // NOI18N
        jLabel1.setText("Đì dai by Nhóm Năm");

        btnDangXuat.setFont(new java.awt.Font("Segoe UI", 3, 48)); // NOI18N
        btnDangXuat.setForeground(new java.awt.Color(255, 153, 204));
        btnDangXuat.setText("Đăng xuất");
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(281, 281, 281))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(193, 193, 193)
                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 885, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(467, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 171, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(287, 287, 287))
        );

        jTabbedPane2.addTab("?", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 798, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBoLocHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBoLocHuyActionPerformed
        // TODO add your handling code here:
        loadDataQuanLyHDHuy(ser.getAllQLHDHuy());
    }//GEN-LAST:event_btnBoLocHuyActionPerformed

    private void btnSearchHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchHuyActionPerformed
        // TODO add your handling code here:
        listHoaDon.clear();
        String searchHuy = txtSearchHuy.getText();
        listHoaDon = ser.searchQLHuy(searchHuy);
//        System.out.println(listSearchHuy);
//        System.out.println(searchHD);
        if (searchHuy.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hoá đơn");
        } else {
            if (listHoaDon.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tồn tại hoá đơn này");
//                System.out.println(listSearchHD.get(0).getMaHoaDon());

            } else {
                loadDataQuanLyHDHuy(listHoaDon);

                loadDataQLHDSPHuy(ser.getAllQuanLyHDSP(searchHuy));
            }
        }
    }//GEN-LAST:event_btnSearchHuyActionPerformed

    private void btnSearchHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchHDActionPerformed
        // TODO add your handling code here:
        String searchHD = txtSearchHD.getText();
        ArrayList<HoaDon> listSearchHD = ser.searchQLHD(searchHD);
        System.out.println(listSearchHD);
//        System.out.println(searchHD);
        if (searchHD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hoá đơn");
        } else {
            if (listSearchHD.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tồn tại hoá đơn này");
//                System.out.println(listSearchHD.get(0).getMaHoaDon());

            } else {
                loadDataQuanLyHD(ser.searchQLHD(searchHD));
                loadDataQLHDSP(ser.getAllQuanLyHDSP(searchHD));
            }
        }
    }//GEN-LAST:event_btnSearchHDActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        // TODO add your handling code here:
        ViewDangNhap viewDN = new ViewDangNhap();
        this.setVisible(false);
        viewDN.setVisible(true);
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void tblVoucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVoucherMouseClicked
        // TODO add your handling code here:
        int row = tblVoucher.getSelectedRow();
        if (row >= 0) {
            setFormVoucher(row);
        }
    }//GEN-LAST:event_tblVoucherMouseClicked

    private void txtTKVoucherKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTKVoucherKeyReleased
        // TODO add your handling code here:
        String search = txtTKVoucher.getText();
        rdAllVoucher.setSelected(false);
        if (search.isEmpty()) {
            loadDataVoucher(ser.getAllVoucher());
        } else {
            loadDataVoucher(ser.searchVoucher(search));
        }
    }//GEN-LAST:event_txtTKVoucherKeyReleased

    private void rdAllVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdAllVoucherActionPerformed
        // TODO add your handling code here:
        loadDataVoucher(ser.getAllVoucher());
        txtTKVoucher.setText("");
    }//GEN-LAST:event_rdAllVoucherActionPerformed

    private void rdSXTMaVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdSXTMaVoucherActionPerformed
        // TODO add your handling code here:
        loadDataVoucher(ser.sXMaVoucher());
        txtTKVoucher.setText("");
    }//GEN-LAST:event_rdSXTMaVoucherActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:


    }//GEN-LAST:event_formWindowActivated

    private void tblQLHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLHoaDonMouseClicked
        // TODO add your handling code here:
        int row = tblQLHoaDon.getSelectedRow();
        listHoaDon.clear();
        listHoaDon = ser.getAllQuanLyHD();
        if (row >= 0) {
            String maHoaDon = listHoaDon.get(row).getMaHoaDon();
            System.out.println(maHoaDon);
            loadDataQLHDSP(ser.getAllQuanLyHDSP(maHoaDon));
        }

    }//GEN-LAST:event_tblQLHoaDonMouseClicked

    private void btnAddNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNhanVienActionPerformed
        // TODO add your handling code here:
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không");
        int count = 0, dem = 0;
        if (check == JOptionPane.YES_OPTION) {
            if (!checkNhanVien()) {
                count++;
                dem++;
            }
            if (dem == 0) {

                if (!checkTrungMaNhanVien(getFormNhanVien().getMaNguoiDung())) {
                    count++;
                }
                if (!checkTuoiNV()) {
                    count++;
                }
                if (!sdtNV()) {
                    count++;
                }
                if (!emailNV()) {
                    count++;
                }
                if (!checkTrungEmailNhanVien(getFormNhanVien().getEmail())) {
                    count++;
                }
                if (!checkTrungSDTNhanVien(getFormNhanVien().getSDT())) {
                    count++;
                }
                if (!checkTrungTenDNNhanVien(getFormNhanVien().getTenDN())) {
                    count++;
                }

            }

            if (count == 0) {
//                System.out.println("tgkjggf");
                ser.addNhanVien(getFormNhanVien());
                NguoiDung nd = getFormNhanVien();
                System.out.println(nd.getMaNguoiDung());
                JOptionPane.showMessageDialog(this, "Mã nhân viên: " + nd.getMaNguoiDung() + "\n"
                        + "Tên nhân viên: " + nd.getTenNguoiDung() + "\n"
                        + "Giới tính: " + nd.isGioiTinh() + "\n"
                        + "Tuổi: " + nd.getTuoi() + "\n"
                        + "Số điện thoại: " + nd.getSDT() + "\n"
                        + "Email: " + nd.getEmail() + "\n"
                        + "Roles: " + nd.getRoles() + "\n"
                        + "Password: " + txtPassword.getText() + "\n"
                );
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                loadDataNhanVien(ser.getAllNhanVien(true));
                String tenDN = ser.getEmail(ser.getTenDN());
                NguoiDung ndnv = getFormNhanVienEmail();
                NguoiDung ndql = new NguoiDung(tenDN);
                ser.listNV().clear();
                ser.listEmail(ndnv);
                ser.listEmail(ndql);
                ViewEmail viewEmail = new ViewEmail();
                viewEmail.setVisible(true);
                viewEmail.setLocationRelativeTo(null);
                viewEmail.ser.listEmail(ndnv);
                viewEmail.ser.listEmail(ndql);

            } else {

                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }

        }
    }//GEN-LAST:event_btnAddNhanVienActionPerformed

    private void btnUpdateNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateNhanVienActionPerformed
        // TODO add your handling code here:
        int count = 0, dem = 0;
        int i = tblNhanVien.getSelectedRow();
        if (i < 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng");
        } else {
            int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa không");
            if (check == JOptionPane.YES_OPTION) {
                if (!checkNhanVien()) {
                    count++;
                    dem++;
                }
                if (dem == 0) {
                    if (!checkTuoiNV()) {
                        count++;
                    }
                    if (!sdtNV()) {
                        count++;
                    }
                    if (!emailNV()) {
                        count++;
                    }

                    if (!checkTrungEmailTenDNNhanVien(txtMaNV.getText(), txtEmail.getText(), txtTenDN.getText(), txtSDT.getText())) {
                        count++;
                    }
                    if (count == 0) {
                        NguoiDung nd = getFormNhanVien();
//                        int d = 0;
//                        NguoiDung nv = ser.getRowNhanVien(true, i);
//                        System.out.println(nv.toString());
//                            if (nd == nv) {
//                                d++;
//                            
//                            System.out.println(nv);
//                        }
//                        System.out.println(d);
                        //System.out.println(nd);
//                        if (d<=0) {
//                            JOptionPane.showMessageDialog(this, "Chưa thay đổi dữ liệu");
//                        } else {
                        ser.updateNV(nd);

                        JOptionPane.showMessageDialog(this, "Sửa thành công");
                        loadDataNhanVien(ser.getAllNhanVien(true));

//                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Sửa thất bại");
                    }

                }
            }
        }
    }//GEN-LAST:event_btnUpdateNhanVienActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        int i = tblNhanVien.getSelectedRow();
        if (i >= 0) {
            setFormNhanVien(ser.getRowNhanVien(true, i));
            txtMaNV.setEnabled(false);
            //       ser.getRowNhanVien(i).inInf();

        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnDeleteNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteNhanVienActionPerformed
        // TODO add your handling code here:
        int i = tblNhanVien.getSelectedRow();
        String maNhanVien = ser.getRowNhanVien(true, i).getMaNguoiDung();
        if (i < 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn dòng để xoá");
        } else {
            int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá không");
            if (check == JOptionPane.YES_OPTION) {

                // boolean trangThaiFalse = false;
                JOptionPane.showMessageDialog(this, ser.updateTrangThaiNhanVien(false, maNhanVien));

                loadDataNhanVien(ser.getAllNhanVien(true));
                loadDataNhanVienNghi(ser.getAllNhanVien(false));
            } else {
                JOptionPane.showMessageDialog(this, "Xoá thất bại");
            }
        }
    }//GEN-LAST:event_btnDeleteNhanVienActionPerformed

    private void btnNewNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewNhanVienActionPerformed
        // TODO add your handling code here:
        txtMaNV.setEnabled(true);
        setFormNhanVien(new NguoiDung(" ", " ", true, 0, " ", " ", "NV", " ", " ", true));
        txtTuoi.setText("");
        buttonGroup4.clearSelection();
    }//GEN-LAST:event_btnNewNhanVienActionPerformed

    private void rdTheoTuoiNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdTheoTuoiNhanVienMouseClicked
        // TODO add your handling code here:
        loadDataNhanVien(ser.sapXepTheoTuoiNV());
    }//GEN-LAST:event_rdTheoTuoiNhanVienMouseClicked

    private void rdTheoTenNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdTheoTenNVMouseClicked
        // TODO add your handling code here:
        loadDataNhanVien(ser.sapXepTheoTenNhVien());
    }//GEN-LAST:event_rdTheoTenNVMouseClicked

    private void rdTheoMaNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdTheoMaNVMouseClicked
        // TODO add your handling code here:
        loadDataNhanVien(ser.sapXepTheoMaNhVien());
    }//GEN-LAST:event_rdTheoMaNVMouseClicked

    private void txtSearchNVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchNVKeyReleased
        // TODO add your handling code here:
        //        String searchNV = txtSearchNV.getText();
        //        ArrayList<NguoiDung> listSearchNV = ser.searchNguoiDung(searchNV);
        //        if (searchNV.isEmpty()) {
        //            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hoặc tên");
        //        } else {
        //            if (listSearchNV.isEmpty()) {
        //                JOptionPane.showMessageDialog(this, "Không tồn tại nhân viên này");
        //                txtSearchNV.setText("");
        //                txtMaNV.setText("");
        //                txtTenNV.setText("");
        //                buttonGroup4.clearSelection();
        //                txtSDT.setText("");
        //                txtEmail.setText("");
        //                txtRoles.setText("");
        //                txtTenDN.setText("");
        //                txtPassword.setText("");
        //            } else {
        //                loadDataNguoiDung(ser.searchNguoiDung(searchNV));
        //            }
        //        }
    }//GEN-LAST:event_txtSearchNVKeyReleased

    private void txtSearchNVMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchNVMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchNVMouseReleased

    private void btnSearchNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchNVActionPerformed
        // TODO add your handling code here:

        String searchNV = txtSearchNV.getText();
        String searchTen = txtSearchNV.getText();
        ArrayList<NguoiDung> listSearchNV = ser.searchNhanVien(searchNV, searchTen);
        if (searchNV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã");
        } else {
            if (listSearchNV.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tồn tại nhân viên này");
                txtSearchNV.setText("");
                txtMaNV.setText("");
                txtTenNV.setText("");
                buttonGroup4.clearSelection();
                txtSDT.setText("");
                txtEmail.setText("");
                lblRoles.setText("");
                txtTenDN.setText("");
                txtPassword.setText("");
            } else {
                loadDataNhanVien(ser.searchNhanVien(searchNV, searchTen));
            }
        }
    }//GEN-LAST:event_btnSearchNVActionPerformed

    private void btnHienThiNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHienThiNVActionPerformed
        // TODO add your handling code here:
        loadDataNhanVien(ser.getAllNhanVien(true));
    }//GEN-LAST:event_btnHienThiNVActionPerformed

    private void btnHienThiNVNghiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHienThiNVNghiActionPerformed
        // TODO add your handling code here:
        loadDataNhanVienNghi(ser.getAllNhanVien(false));
    }//GEN-LAST:event_btnHienThiNVNghiActionPerformed

    private void btnSearchNVNghiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchNVNghiActionPerformed
        // TODO add your handling code here:
        String searchNV = txtSearchNVNghi.getText();
        String searchTen = txtSearchNVNghi.getText();
        ArrayList<NguoiDung> listSearchNVNghi = ser.searchNhanVienNghi(searchNV, searchTen);
        if (searchNV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã");
        } else {
            if (listSearchNVNghi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tồn tại nhân viên này");
                txtSearchNVNghi.setText("");
            } else {
                loadDataNhanVienNghi(ser.searchNhanVienNghi(searchNV, searchTen));
            }
        }

    }//GEN-LAST:event_btnSearchNVNghiActionPerformed

    private void txtSearchNVNghiMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchNVNghiMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchNVNghiMouseReleased

    private void txtSearchNVNghiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchNVNghiKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchNVNghiKeyReleased

    private void rdTheoMaNVNghiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdTheoMaNVNghiMouseClicked
        // TODO add your handling code here:
        loadDataNhanVienNghi(ser.sapXepTheoMaNhVienNghi());
    }//GEN-LAST:event_rdTheoMaNVNghiMouseClicked

    private void rdTheoTenNVNghiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdTheoTenNVNghiMouseClicked
        // TODO add your handling code here:
        loadDataNhanVienNghi(ser.sapXepTheoTenNhVienNghi());

    }//GEN-LAST:event_rdTheoTenNVNghiMouseClicked

    private void rdTheoTuoiNhanVienNghiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdTheoTuoiNhanVienNghiMouseClicked
        // TODO add your handling code here:
        loadDataNhanVienNghi(ser.sapXepTheoTuoiNVNghi());
    }//GEN-LAST:event_rdTheoTuoiNhanVienNghiMouseClicked

    private void btnKhoiPhucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoiPhucActionPerformed
        // TODO add your handling code here:
        int i = tblNhanVienNghi.getSelectedRow();
        String maNV = ser.getRowNhanVien(false, i).getMaNguoiDung();
        if (i < 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng");
        } else {
            int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn khôi phục nhân viên không");
            if (check == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, ser.updateTrangThaiNhanVien(true, maNV));
                loadDataNhanVienNghi(ser.getAllNhanVien(false));
                loadDataNhanVien(ser.getAllNhanVien(true));
            }
        }
    }//GEN-LAST:event_btnKhoiPhucActionPerformed

    private void tblQLHoaDonHuyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLHoaDonHuyMouseClicked
        // TODO add your handling code here:
        int row = tblQLHoaDonHuy.getSelectedRow();
        listHoaDon.clear();
        listHoaDon = ser.getAllQLHDHuy();
        if (row >= 0) {
            String maHoaDon = listHoaDon.get(row).getMaHoaDon();
            System.out.println(maHoaDon);
            loadDataQLHDSPHuy(ser.getAllQuanLyHDSP(maHoaDon));
        }
    }//GEN-LAST:event_tblQLHoaDonHuyMouseClicked
    private void btnTKTNVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKTNVoucherActionPerformed
        // TODO add your handling code here:
        String ngayBD = txtTKNBDVoucher.getText();
        String hanSD = txtTKNKTVoucher.getText();
        loadDataVoucher(ser.tKTNVoucher(ngayBD, hanSD));
//        Voucher vc = new Voucher("", "", "", "", 0, 0.0, 0.0);
//        setFormVoucher(vc);
//        txtSoLuongVoucher.setText("");
//        txtSoTienGiamVoucher.setText("");
//        txtSoTienYCVoucher.setText("");
    }//GEN-LAST:event_btnTKTNVoucherActionPerformed

    private void btnTKTKNKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKTKNKMActionPerformed
        // TODO add your handling code here:
        String ngayBD = txtTKNBDKhuyenMai.getText();
        String hanSD = txtTKNKTKhuyeMai.getText();
        loadDataKhuyenMai(ser.tKTNKhuyenMai(ngayBD, hanSD));
    }//GEN-LAST:event_btnTKTKNKMActionPerformed

    private void tblKhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhuyenMaiMouseClicked
        // TODO add your handling code here:
        int row = tblKhuyenMai.getSelectedRow();
        if (row >= 0) {
            setFormKhuyenMai(row);
        }
    }//GEN-LAST:event_tblKhuyenMaiMouseClicked

    private void btnThemVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemVoucherActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkVoucher() && checkTrungMaVoucher(txtMaVoucher.getText()) && checkTrungTenVoucher(txtTenVoucher.getText())) {
                ser.addVoucher(getFormVoucher());
                loadDataVoucher(ser.getAllVoucher());
                JOptionPane.showMessageDialog(this, "Thêm thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }
    }//GEN-LAST:event_btnThemVoucherActionPerformed

    private void btnSuaVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaVoucherActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkVoucher()) {
                ser.updateVoucher(getFormVoucher());
                loadDataVoucher(ser.getAllVoucher());
                JOptionPane.showMessageDialog(this, "Sửa thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại");
            }
        }

    }//GEN-LAST:event_btnSuaVoucherActionPerformed

    private void btnHienThiHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHienThiHDActionPerformed
        // TODO add your handling code here:
        loadDataQuanLyHD(ser.getAllQuanLyHD());
    }//GEN-LAST:event_btnHienThiHDActionPerformed

    private void btnHienThiHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHienThiHuyActionPerformed
        // TODO add your handling code here:
        loadDataQuanLyHDHuy(ser.getAllQLHDHuy());
    }//GEN-LAST:event_btnHienThiHuyActionPerformed

    private void btnLocHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocHuyActionPerformed
        // TODO add your handling code here:
        int count = 0;
        String ngayBD = txtBatDauHuy.getText();
        String ngayKT = txtKetThucHuy.getText();

        if (ngayBD.isEmpty()) {
            JOptionPane.showMessageDialog(this, " Vui lòng nhập ngày bắt đầu");
            count++;
        }
        if (ngayKT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày kết thúc");
            count++;
        }

        if (count == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
            try {
                Date date1 = sdf.parse(ngayBD);
                System.out.println(ngayBD);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không đúng định dạng");
                System.out.println(e.getMessage());
            }
            try {
                Date date2 = sdf.parse(ngayKT);
                System.out.println(ngayKT);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc không đúng định dạng");
                System.out.println(e.getMessage());
            }
            try {
                Date date1 = sdf.parse(ngayBD);
                Date date2 = sdf.parse(ngayKT);
                if (date1.after(date2)) {
                    JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải lớn ngày kết thúc");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            ArrayList<HoaDon> listLocHDHuy = ser.locHDHuyTheoNgay(ngayBD, ngayKT);
            if (listLocHDHuy.isEmpty()) {
                JOptionPane.showMessageDialog(this, " Không tìm thấy hoá đơn");
                txtBatDauHD.setText(" ");
                txtKetThucHD.setText(" ");
                ArrayList<HoaDon> listHD = new ArrayList<>();
                loadDataQuanLyHDHuy(listHD);
            } else {
                loadDataQuanLyHDHuy(ser.locHDHuyTheoNgay(ngayBD, ngayKT));
            }
        }

    }//GEN-LAST:event_btnLocHuyActionPerformed

    private void btnLocHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocHDActionPerformed
        // TODO add your handling code here:
        int count = 0;
        String ngayBD = txtBatDauHD.getText();
        String ngayKT = txtKetThucHD.getText();

        if (ngayBD.isEmpty()) {
            JOptionPane.showMessageDialog(this, " Vui lòng nhập ngày bắt đầu");
            count++;
        }
        if (ngayKT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày kết thúc");
            count++;
        }

        if (count == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date date1 = sdf.parse(ngayBD);
                System.out.println(ngayBD);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không đúng định dạng");
                System.out.println(e.getMessage());
            }
            try {
                Date date2 = sdf.parse(ngayKT);
                System.out.println(ngayKT);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc không đúng định dạng");
                System.out.println(e.getMessage());
            }
            try {
                Date date1 = sdf.parse(ngayBD);
                Date date2 = sdf.parse(ngayKT);
                if (date1.after(date2)) {
                    JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải lớn ngày kết thúc");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            ArrayList<HoaDon> listLocHD = ser.locHDTheoNgay(ngayBD, ngayKT);
            if (listLocHD.isEmpty()) {
                JOptionPane.showMessageDialog(this, " Không tìm thấy hoá đơn");
                txtBatDauHD.setText(" ");
                txtKetThucHD.setText(" ");
                ArrayList<HoaDon> listHD = new ArrayList<>();
                loadDataQuanLyHD(listHD);
            } else {
                loadDataQuanLyHD(ser.locHDTheoNgay(ngayBD, ngayKT));
            }
        }
    }//GEN-LAST:event_btnLocHDActionPerformed

    private void btnBoLocHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBoLocHDActionPerformed
        // TODO add your handling code here:
        loadDataQuanLyHD(ser.getAllQuanLyHD());
    }//GEN-LAST:event_btnBoLocHDActionPerformed

    private void txtSearchNVFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchNVFocusGained
        // TODO add your handling code here:
        if (txtSearchNV.getText().equals("Nhập mã hoặc tên nhân viên")) {
            txtSearchNV.setText("");
            txtSearchNV.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtSearchNVFocusGained

    private void txtSearchNVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchNVFocusLost
        // TODO add your handling code here:
        if (txtSearchNV.getText().equals("")) {
            txtSearchNV.setText("Nhập mã hoặc tên nhân viên");
            txtSearchNV.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtSearchNVFocusLost

    private void txtSearchNVNghiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchNVNghiFocusGained
        // TODO add your handling code here:
        if (txtSearchNVNghi.getText().equals("Nhập mã hoặc tên nhân viên")) {
            txtSearchNVNghi.setText("");
            txtSearchNVNghi.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtSearchNVNghiFocusGained

    private void txtSearchNVNghiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchNVNghiFocusLost
        // TODO add your handling code here:
        if (txtSearchNV.getText().equals("")) {
            txtSearchNV.setText("Nhập mã hoặc tên nhân viên");
            txtSearchNV.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtSearchNVNghiFocusLost

    private void txtSearchHDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchHDFocusGained
        // TODO add your handling code here:
        if (txtSearchHD.getText().equals("Nhập mã hoá đơn cần tìm")) {
            txtSearchHD.setText("");
            txtSearchHD.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtSearchHDFocusGained

    private void txtSearchHDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchHDFocusLost
        // TODO add your handling code here:
        if (txtSearchHD.getText().equals("")) {
            txtSearchHD.setText("Nhập mã hoá đơn cần tìm");
            txtSearchHD.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtSearchHDFocusLost

    private void txtSearchHuyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchHuyFocusGained
        // TODO add your handling code here:
        if (txtSearchHuy.getText().equals("Nhập mã hoá đơn cần tìm")) {
            txtSearchHuy.setText("");
            txtSearchHuy.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtSearchHuyFocusGained

    private void txtSearchHuyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchHuyFocusLost
        // TODO add your handling code here:
        if (txtSearchHuy.getText().equals("")) {
            txtSearchHuy.setText("Nhập mã hoá đơn cần tìm");
            txtSearchHuy.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtSearchHuyFocusLost

    private void txtBatDauHDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBatDauHDFocusGained
        // TODO add your handling code here:
        if (txtBatDauHD.getText().equals("dd-mm-yyyy")) {
            txtBatDauHD.setText("");
            txtBatDauHD.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtBatDauHDFocusGained

    private void txtBatDauHDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBatDauHDFocusLost
        // TODO add your handling code here:
        if (txtBatDauHD.getText().equals("")) {
            txtBatDauHD.setText("dd-mm-yyyy");
            txtBatDauHD.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtBatDauHDFocusLost

    private void txtKetThucHDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtKetThucHDFocusGained
        // TODO add your handling code here:
        if (txtKetThucHD.getText().equals("dd-mm-yyyy")) {
            txtKetThucHD.setText("");
            txtKetThucHD.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtKetThucHDFocusGained

    private void txtKetThucHDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtKetThucHDFocusLost
        // TODO add your handling code here:
        if (txtKetThucHD.getText().equals("")) {
            txtKetThucHD.setText("dd-mm-yyyy");
            txtKetThucHD.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtKetThucHDFocusLost

    private void txtBatDauHuyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBatDauHuyFocusGained
        // TODO add your handling code here:
        if (txtBatDauHuy.getText().equals("dd-mm-yyyy")) {
            txtBatDauHuy.setText("");
            txtBatDauHuy.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtBatDauHuyFocusGained

    private void txtBatDauHuyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBatDauHuyFocusLost
        // TODO add your handling code here:
        if (txtBatDauHuy.getText().equals("")) {
            txtBatDauHuy.setText("dd-mm-yyyy");
            txtBatDauHuy.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtBatDauHuyFocusLost

    private void txtKetThucHuyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtKetThucHuyFocusGained
        // TODO add your handling code here:
        if (txtKetThucHuy.getText().equals("dd-mm-yyyy")) {
            txtKetThucHuy.setText("");
            txtKetThucHuy.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtKetThucHuyFocusGained

    private void txtKetThucHuyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtKetThucHuyFocusLost
        // TODO add your handling code here:
        if (txtKetThucHuy.getText().equals("")) {
            txtKetThucHuy.setText("dd-mm-yyyy");
            txtKetThucHuy.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtKetThucHuyFocusLost

    private void rdSXTTenVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdSXTTenVoucherActionPerformed
        // TODO add your handling code here:
        loadDataVoucher(ser.sXTTTenVoucher());
        txtTKVoucher.setText("");
    }//GEN-LAST:event_rdSXTTenVoucherActionPerformed

    private void rdSXTNgayVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdSXTNgayVoucherActionPerformed
        // TODO add your handling code here:
        loadDataVoucher(ser.sXTNgayVoucher());
        txtTKVoucher.setText("");
    }//GEN-LAST:event_rdSXTNgayVoucherActionPerformed

    private void rdSXTMaKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdSXTMaKhuyenMaiActionPerformed
        // TODO add your handling code here:
        loadDataKhuyenMai(ser.sXMaKM());
        txtTKKhuyenMai.setText("");
    }//GEN-LAST:event_rdSXTMaKhuyenMaiActionPerformed

    private void rdSXTTenKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdSXTTenKhuyenMaiActionPerformed
        // TODO add your handling code here:
        loadDataKhuyenMai(ser.sXTTTenKM());
        txtTKKhuyenMai.setText("");
    }//GEN-LAST:event_rdSXTTenKhuyenMaiActionPerformed

    private void rdSXTNgayKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdSXTNgayKhuyenMaiActionPerformed
        // TODO add your handling code here:
        loadDataKhuyenMai(ser.sXTNgayKhuyenMai());
        txtTKKhuyenMai.setText("");
    }//GEN-LAST:event_rdSXTNgayKhuyenMaiActionPerformed

    private void rdAllKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdAllKhuyenMaiActionPerformed
        // TODO add your handling code here:
        loadDataKhuyenMai(ser.getAllKhuyenMai());
        txtTKKhuyenMai.setText("");
    }//GEN-LAST:event_rdAllKhuyenMaiActionPerformed

    private void btnThemKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKhuyenMaiActionPerformed
        // TODO add your handling code here:
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkKhuyenMai() && checkTrungMaKM(txtMaKhuyenMai.getText()) && checkTrungTenKM(txtTenKhuyenMai.getText())) {
                ser.addKhuyenMai(getFormKhuyenMai());
                loadDataKhuyenMai(ser.getAllKhuyenMai());
                JOptionPane.showMessageDialog(this, "Thêm thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }
    }//GEN-LAST:event_btnThemKhuyenMaiActionPerformed

    private void btnSuaKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaKhuyenMaiActionPerformed
        // TODO add your handling code here:
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkKhuyenMai()) {
                ser.updateKhuyenMai(getFormKhuyenMai());
                loadDataKhuyenMai(ser.getAllKhuyenMai());
                JOptionPane.showMessageDialog(this, "Sửa thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại");
            }
        }
    }//GEN-LAST:event_btnSuaKhuyenMaiActionPerformed

    private void tblSPKMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPKMMouseClicked
        // TODO add your handling code here:
        int row = tblSPKM.getSelectedRow();
        if (row >= 0) {
            setFormSPKM(row);
        }
    }//GEN-LAST:event_tblSPKMMouseClicked

    private void tblKMChonSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKMChonSPMouseClicked
        // TODO add your handling code here:
        int row = tblKMChonSP.getSelectedRow();
        if (row >= 0) {
            setFormKMSP(row);
        }
    }//GEN-LAST:event_tblKMChonSPMouseClicked

    private void btnThemSPKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPKMActionPerformed
        // TODO add your handling code here:
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không");
        if (check == JOptionPane.YES_OPTION) {
            if (txtMaSPThemKM.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "không để trống mã sản phẩm");
                return;
            } else if (txtMaKMChonSP.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "không để trống mã khuyến mãi");
                return;
            } else {
                SanPham sp = new SanPham();
                sp.setMaSPCT(ser.getMaSPCT(txtMaSPCT.getText()));
                sp.setMaSPKM(txtMaKMChonSP.getText());
                ser.addSPKM(sp);
            }
        }
    }//GEN-LAST:event_btnThemSPKMActionPerformed
    private boolean isValidDate(String date) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }
    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        String ngayBatDau = txtNgayBatDau.getText();
        String NgayKetThuc = txtNgayKetThuc.getText();

        if (!isValidDate(ngayBatDau) || !isValidDate(NgayKetThuc)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu hoặc ngày kết thúc không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tongDonHang = ser.tonghoadon(ngayBatDau, NgayKetThuc);
        double tongDoanhThu = ser.tongDoanhThu(ngayBatDau, NgayKetThuc);
        int tongDoanhSoBanHang = ser.tongDoanhSo(ngayBatDau, NgayKetThuc);

        if (tongDonHang == 0 && tongDoanhThu == 0 && tongDoanhSoBanHang == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả phù hợp", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            txtTongDonHang.setText(String.valueOf(tongDonHang));
            txtTongDoanhThu.setText(String.valueOf(tongDoanhThu));
            txtTongDoanhSoBanHang.setText(String.valueOf(tongDoanhSoBanHang));
            lblThanhCong.setText(String.valueOf(ser.tongHoaDonThanhToan()));
            lblCTT.setText(String.valueOf(tongDonHang - ser.tongHoaDonThanhToan()));
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnHienThiBCTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHienThiBCTKActionPerformed
        txtTongDonHang.setText(String.valueOf(ser.tongHoaDonMD()));
        txtTongDoanhThu.setText(formattedResult);
        txtTongDoanhSoBanHang.setText(String.valueOf(ser.tongDoanhSoMD()));
        lblThanhCong.setText(String.valueOf(ser.tongHoaDonThanhToan()));
        lblCTT.setText(String.valueOf(ser.tongHoaDonMD() - ser.tongHoaDonThanhToan()));

    }//GEN-LAST:event_btnHienThiBCTKActionPerformed

    private void btnNewTTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTTSPActionPerformed
        txtMaSP.setText("");
        txtTenSPTTSP.setText("");
        txtMauTTSP.setText("");
        txtHangTTSP.setText("");
    }//GEN-LAST:event_btnNewTTSPActionPerformed

    private void btnUpdateSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateSPActionPerformed
        cboMauTTSP.removeAllItems();
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn Update không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkSPTTSP()) {
                int row = tblSanPhamTTSP.getSelectedRow();
                if (row >= 0) {
                    String ma = ser.getAllSanPhamTTSP().get(row).getMaSP();
                    SanPham sp = getFormSPTTSP();
                    sp.setMaSP(ma);
                    ser.updateSanPhamTTSP(sp);
                    JOptionPane.showMessageDialog(this, "Update thành công");
                    loadDataSanPhamTTSP(ser.getAllSanPhamTTSP());
                    loadDataQLSP(ser.getAllSanPhamCT());
                    showCboMau();

                } else {
                    JOptionPane.showMessageDialog(this, "Update thất bại");
                }
            }
        }
    }//GEN-LAST:event_btnUpdateSPActionPerformed

    private void btnAddSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSanPhamActionPerformed
        cboMauTTSP.removeAllItems();
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkSPTTSP() && checkTrungMaSPTTSP(txtMaSP.getText()) && checkTrungTenSPTTSP(txtTenSPTTSP.getText())) {
                ser.addSanPhamTTSP(getFormSPTTSP());
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                loadDataSanPhamTTSP(ser.getAllSanPhamTTSP());
                loadDataQLSP(ser.getAllSanPhamCT());
                showCboMau();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }
    }//GEN-LAST:event_btnAddSanPhamActionPerformed

    private void btnUpdateSP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateSP1ActionPerformed
        jTabbedPane4.setSelectedIndex(1);
        txtMaSPCT.setText("");
        txtTenSPSPCT.setText(txtTenSPTTSP.getText());
        txtHang.setText(txtHangTTSP.getText());
        cboMauTTSP.setSelectedItem(txtMauTTSP.getText());
        cboTenNCCTTSP.setSelectedIndex(0);
        txtSoLuongTTSP.setText("");
        cboMauSacTTSP.setSelectedIndex(0);
        cboChatLieuTTSP.setSelectedIndex(0);
        cboKichThuocTTSP.setSelectedIndex(0);

    }//GEN-LAST:event_btnUpdateSP1ActionPerformed

    private void tblSanPhamTTSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamTTSPMouseClicked

        int row = tblSanPhamTTSP.getSelectedRow();
        if (row >= 0) {
            setFormSPTTSP(row);
        }
    }//GEN-LAST:event_tblSanPhamTTSPMouseClicked

    private void btnTimKiemMaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemMaSPActionPerformed
        listsp1.clear();
        if (txtTimKiemMaSP.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mã đơn giá cần tìm");
        } else {
            String keyword = txtTimKiemMaSP.getText();
            listsp1 = ser.getTimKiemSPTTSP(keyword);

            if (listsp1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy mã đơn giá nào với mã: " + keyword);
            } else {
                dtm = (DefaultTableModel) tblSanPhamTTSP.getModel();
                dtm.setRowCount(0);
                for (SanPham sp : listsp1) {
                    dtm.addRow(new Object[]{
                        sp.getMaSP(),
                        sp.getTenSP(),
                        sp.getMau(),
                        sp.getHang()

                    });
                }
            }
        }
    }//GEN-LAST:event_btnTimKiemMaSPActionPerformed

    private void rdoSXTheoMaSPTTSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoSXTheoMaSPTTSPMouseClicked
        loadDataSanPhamTTSP(ser.getAllSXTheoMaSP());
    }//GEN-LAST:event_rdoSXTheoMaSPTTSPMouseClicked

    private void rdoSXTheoTenTTSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoSXTheoTenTTSPMouseClicked
        loadDataSanPhamTTSP(ser.getAllSXTheoTenSP());
    }//GEN-LAST:event_rdoSXTheoTenTTSPMouseClicked

    private void txtTenSPSPCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSPSPCTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenSPSPCTActionPerformed

    private void cboMauTTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMauTTSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMauTTSPActionPerformed

    private void txtNewTTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewTTSPActionPerformed
        // TODO add your handling code here:
        txtMaSPCT.setText("");
        txtTenSPSPCT.setText("");
        cboTenNCCTTSP.setSelectedIndex(0);
        txtHang.setText("");
        txtSoLuongTTSP.setText("");
        cboMauSacTTSP.setSelectedIndex(0);
        cboKichThuocTTSP.setSelectedIndex(0);
        cboChatLieuTTSP.setSelectedIndex(0);
        cboMauTTSP.setSelectedIndex(0);
    }//GEN-LAST:event_txtNewTTSPActionPerformed

    private void btnAddSPCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSPCTActionPerformed
        cboMaSPCTLSG.removeAllItems();
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkTTSP() && checkTrungMaSP(txtMaSPCT.getText()) && checkTrungTenSP(txtTenSPSPCT.getText())) {
                ser.addCTSPTTSP(getFormSanPhamTTSP());
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                loadDataQLSP(ser.getAllSanPhamCT());
                showCboSPCT();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }

    }//GEN-LAST:event_btnAddSPCTActionPerformed

    private void cboTenNCCTTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenNCCTTSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTenNCCTTSPActionPerformed

    private void rdoSXMaTTSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoSXMaTTSPMouseClicked
        loadDataQLSP(ser.SapXepTheoMaSP());
    }//GEN-LAST:event_rdoSXMaTTSPMouseClicked

    private void rdoSXTenTTSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoSXTenTTSPMouseClicked
        loadDataQLSP(ser.SapXepTheoTenSP());
    }//GEN-LAST:event_rdoSXTenTTSPMouseClicked

    private void tblSanPhamCTSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamCTSPMouseClicked
        // TODO add your handling code here:
        int row = tblSanPhamCTSP.getSelectedRow();
        if (row >= 0) {
            setFormSanPhamTTSP1(row);
        }
    }//GEN-LAST:event_tblSanPhamCTSPMouseClicked

    private void btnTimKiemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemSPActionPerformed
        listSPTrong.clear();
        if (txtTimKiemSPTTSP.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mã sản phẩm cần tìm");
        } else {
            String keyword = txtTimKiemSPTTSP.getText();
            listSPTrong = ser.getTimKiemSanPhamTTSP(keyword);

            if (listSPTrong.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên nào với mã: " + keyword);
            } else {
                dtm = (DefaultTableModel) tblSanPhamCTSP.getModel();
                dtm.setRowCount(0);
                for (SanPham sp : listSPTrong) {
                    dtm.addRow(new Object[]{
                        sp.getMaSPCT(),
                        sp.getTenSP(),
                        sp.getNhaCungCap(),
                        sp.getHang(),
                        sp.getDonGia(),
                        sp.getSoLuongSP(),
                        sp.getMauSac(),
                        sp.getKichThuoc(),
                        sp.getMau(),
                        sp.getChatLieu(),
                        sp.getHinhAnh()
                    });

                }
            }
        }
    }//GEN-LAST:event_btnTimKiemSPActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtMaMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaMauSacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaMauSacActionPerformed

    private void btnNewMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewMauSacActionPerformed
        txtMaMauSac.setText("");
        txtTenMauSac.setText("");
    }//GEN-LAST:event_btnNewMauSacActionPerformed

    private void btnSuaMauSacTTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaMauSacTTSPActionPerformed
        cboMauSacTTSP.removeAllItems();
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn Update không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkMauSac() && checkTrungTenMauSac(txtTenMauSac.getText())) {
                int row = tblMauSacTTSP.getSelectedRow();
                if (row >= 0) {
                    String ma = ser.getAllMauSac().get(row).getMaMS();
                    SanPham sp = getFormMauSac();
                    sp.setMaMS(ma);
                    ser.updateMauSacTTSP(sp);
                    JOptionPane.showMessageDialog(this, "Update thành công");
                    loadDataMauSac();
                    loadDataQLSP(ser.getAllSanPhamCT());
                    showCboMauSacTT();
                } else {
                    JOptionPane.showMessageDialog(this, "Update thất bại");
                }
            }
        }
    }//GEN-LAST:event_btnSuaMauSacTTSPActionPerformed

    private void btnXoaMauSacTTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaMauSacTTSPActionPerformed
        cboMauSacTTSP.removeAllItems();
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không");
        if (check == JOptionPane.YES_OPTION) {
            int row = tblMauSacTTSP.getSelectedRow();
            if (row >= 0) {
                String ma = txtMaMauSac.getText();
                ser.deleteMauSacTTSP(ma);
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                loadDataMauSac();
                loadDataQLSP(ser.getAllSanPhamCT());
                showCboMauSacTT();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại");
            }
        }
    }//GEN-LAST:event_btnXoaMauSacTTSPActionPerformed

    private void txtTenMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenMauSacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenMauSacActionPerformed

    private void btnAddMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMauSacActionPerformed
        cboMauSacTTSP.removeAllItems();
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkMauSac() && checkTrungMaMauSac(txtMaMauSac.getText()) && checkTrungTenMauSac(txtTenMauSac.getText())) {
                ser.addMauSacTTSP(getFormMauSac());
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                loadDataMauSac();
                loadDataQLSP(ser.getAllSanPhamCT());
                showCboMauSacTT();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }
    }//GEN-LAST:event_btnAddMauSacActionPerformed

    private void tblMauSacTTSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMauSacTTSPMouseClicked
        // TODO add your handling code here:
        int row = tblMauSacTTSP.getSelectedRow();
        if (row >= 0) {
            setFormMauSac(ser.getAllMauSac().get(row));
        }
    }//GEN-LAST:event_tblMauSacTTSPMouseClicked

    private void txtMaKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKichThuocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKichThuocActionPerformed

    private void btnUpdateKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateKichThuocActionPerformed
        cboKichThuocTTSP.removeAllItems();
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn Update không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkKichThuoc() && checkTrungTenKichThuoc(txtTenKichThuoc.getText())) {
                int row = tblKichThuocTTSP.getSelectedRow();
                if (row >= 0) {
                    String ma = ser.getAllKichThuoc().get(row).getMaKT();
                    SanPham sp = getFormKichThuoc();
                    sp.setMaKT(ma);
                    ser.updateKichThuocTTSP(sp);
                    JOptionPane.showMessageDialog(this, "Update thành công");
                    loadDataKichThuoc();
                    loadDataQLSP(ser.getAllSanPhamCT());
                    showCboKichThuoc();

                } else {
                    JOptionPane.showMessageDialog(this, "Update thất bại");
                }
            }
        }
    }//GEN-LAST:event_btnUpdateKichThuocActionPerformed

    private void btnXoaKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKichThuocActionPerformed
        cboKichThuocTTSP.removeAllItems();
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không");
        if (check == JOptionPane.YES_OPTION) {
            int row = tblKichThuocTTSP.getSelectedRow();
            if (row >= 0) {
                String ma = txtMaKichThuoc.getText();
                ser.deleteKichThuocTTSP(ma);
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                loadDataKichThuoc();
                loadDataQLSP(ser.getAllSanPhamCT());
                showCboKichThuoc();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại");
            }
        }
    }//GEN-LAST:event_btnXoaKichThuocActionPerformed

    private void txtTenKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenKichThuocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKichThuocActionPerformed

    private void btnAddKichThuocTTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddKichThuocTTSPActionPerformed
        cboKichThuocTTSP.removeAllItems();
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkKichThuoc() && checkTrungMaKichThuoc(txtMaKichThuoc.getText()) && checkTrungTenKichThuoc(txtTenKichThuoc.getText())) {
                ser.addKichThuocTTSP(getFormKichThuoc());
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                loadDataKichThuoc();
                loadDataQLSP(ser.getAllSanPhamCT());
                showCboKichThuoc();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }
    }//GEN-LAST:event_btnAddKichThuocTTSPActionPerformed

    private void btnNewKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewKichThuocActionPerformed
        // TODO add your handling code here:
        txtMaKichThuoc.setText("");
        txtTenKichThuoc.setText("");
    }//GEN-LAST:event_btnNewKichThuocActionPerformed

    private void tblKichThuocTTSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKichThuocTTSPMouseClicked
        // TODO add your handling code here:
        int row = tblKichThuocTTSP.getSelectedRow();
        if (row >= 0) {
            setFormKichThuoc(ser.getAllKichThuoc().get(row));
        }
    }//GEN-LAST:event_tblKichThuocTTSPMouseClicked

    private void txtMaChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaChatLieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaChatLieuActionPerformed

    private void txtTenChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenChatLieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenChatLieuActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        txtMaChatLieu.setText("");
        txtTenChatLieu.setText("");
    }//GEN-LAST:event_jButton21ActionPerformed

    private void btnAddChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddChatLieuActionPerformed
        cboChatLieuTTSP.removeAllItems();
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkChatLieu() && checkTrungMaChatLieu(txtMaChatLieu.getText()) && checkTrungTenChatLieu(txtTenChatLieu.getText())) {
                ser.addChatLieuTTSP(getFormChatLieu());
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                loadDataChatLieu();
                loadDataQLSP(ser.getAllSanPhamCT());
                showCboChatLieu();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }
    }//GEN-LAST:event_btnAddChatLieuActionPerformed

    private void btnUpdateChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateChatLieuActionPerformed
        cboChatLieuTTSP.removeAllItems();
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn Update không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkChatLieu() && checkTrungTenChatLieu(txtTenChatLieu.getText())) {
                int row = tblChatLieuTTSP.getSelectedRow();
                if (row >= 0) {
                    String ma = ser.getAllChatLieu().get(row).getMaCL();
                    SanPham sp = getFormChatLieu();
                    sp.setMaKT(ma);
                    ser.updateChatLieuTTSP(sp);
                    JOptionPane.showMessageDialog(this, "Update thành công");
                    loadDataChatLieu();
                    loadDataQLSP(ser.getAllSanPhamCT());
                    showCboChatLieu();

                } else {
                    JOptionPane.showMessageDialog(this, "Update thất bại");
                }
            }
        }
    }//GEN-LAST:event_btnUpdateChatLieuActionPerformed

    private void btnXoaChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaChatLieuActionPerformed
        cboChatLieuTTSP.removeAllItems();
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không");
        if (check == JOptionPane.YES_OPTION) {
            int row = tblChatLieuTTSP.getSelectedRow();
            if (row >= 0) {
                String ma = txtMaChatLieu.getText();
                ser.deleteChatLieuTTSP(ma);
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                loadDataChatLieu();
                loadDataQLSP(ser.getAllSanPhamCT());
                showCboChatLieu();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại");
            }
        }
    }//GEN-LAST:event_btnXoaChatLieuActionPerformed

    private void tblChatLieuTTSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChatLieuTTSPMouseClicked
        int row = tblChatLieuTTSP.getSelectedRow();
        if (row >= 0) {
            setFormChatLieu(ser.getAllChatLieu().get(row));
        }
    }//GEN-LAST:event_tblChatLieuTTSPMouseClicked

    private void jLabel89MouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(5);
    }

    private void btnNewFormVoucherActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        txtMaVoucher.setText("");
        txtTenVoucher.setText("");
        txtSoLuongVoucher.setText("");
        txtNBatDauVoucher.setText("");
        txtNKetThucVoucher.setText("");
        txtSoTienGiamVoucher.setText("");
        txtSoTienYCVoucher.setText("");
    }

    private void btnNewFormKMActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        txtMaKhuyenMai.setText("");
        txtTenKhuyenMai.setText("");
        txtNBatDauKhuyenMai.setText("");
        txtNKetThucKhuyenMai.setText("");
        txtGiamGiaKhuyenMai.setText("");

    }

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn Update không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkTTSP()) {
                int row = tblSanPhamCTSP.getSelectedRow();
                if (row >= 0) {
                    String ma = ser.getAllSanPhamCT().get(row).getMaSPCT();
                    SanPham sp = getFormSanPhamTTSP();
                    sp.setMaSPCT(ma);
                    ser.updateCTSPTTSP(sp);
                    JOptionPane.showMessageDialog(this, "Update thành công");
                    loadDataQLSP(ser.getAllSanPhamCT());

                } else {
                    JOptionPane.showMessageDialog(this, "Update thất bại");
                }
            }
        }
    }

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        System.exit(0);
    }

    private void txtTGBatDauLSDGFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTGBatDauLSDGFocusGained
        if (txtTGBatDauLSDG.getText().equals("dd-mm-yyyy")) {
            txtTGBatDauLSDG.setText("");
            txtTGBatDauLSDG.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtTGBatDauLSDGFocusGained

    private void txtTGBatDauLSDGFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTGBatDauLSDGFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTGBatDauLSDGFocusLost

    private void txtTGBatDauLSDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTGBatDauLSDGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTGBatDauLSDGActionPerformed

    private void txtTGKetThucLSDGFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTGKetThucLSDGFocusGained
        if (txtTGKetThucLSDG.getText().equals("dd-mm-yyyy")) {
            txtTGKetThucLSDG.setText("");
            txtTGKetThucLSDG.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtTGKetThucLSDGFocusGained

    private void txtTGKetThucLSDGFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTGKetThucLSDGFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTGKetThucLSDGFocusLost

    private void txtTGKetThucLSDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTGKetThucLSDGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTGKetThucLSDGActionPerformed

    private void btnNewLSDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewLSDGActionPerformed
        txtMaDonGia.setText("");
        txtGiaDau.setText("");
        txtGiaSau.setText("");
        txtTGBatDauLSDG.setText("dd-mm-yyyy");
        txtTGKetThucLSDG.setText("dd-mm-yyyy");
    }//GEN-LAST:event_btnNewLSDGActionPerformed

    private void btnAddLSDGActionPerformed(java.awt.event.ActionEvent evt) {
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkLSDG() && checkTrungMaDonGia(txtMaDonGia.getText()) && checkTrungMaSPCTLSG(cboMaSPCTLSG.getSelectedItem() + "")) {
                ser.addLichSuGia(getFormLichSuGia());
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                loadDataLichSuGia(ser.getAllLichSuDonGia());
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }
    }

    private void btnUpdateLSDGActionPerformed(java.awt.event.ActionEvent evt) {
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn Update không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkLSDG()) {
                int row = tblLichSuDonGia.getSelectedRow();
                if (row >= 0) {
                    String ma = ser.getAllLichSuDonGia().get(row).getMaDonGia();
                    LichSuGia ls = getFormLichSuGia();
                    ls.setMaDonGia(ma);
                    ser.updateLichSuGia(ls);
                    JOptionPane.showMessageDialog(this, "Update thành công");
                    loadDataLichSuGia(ser.getAllLichSuDonGia());

                } else {
                    JOptionPane.showMessageDialog(this, "Update thất bại");
                }
            }
        }
    }

    private void cboMaSPCTLSGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaSPCTLSGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMaSPCTLSGActionPerformed

    private void tblLichSuDonGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLichSuDonGiaMouseClicked
        int row = tblLichSuDonGia.getSelectedRow();
        if (row >= 0) {
            setFormLichSuGia(row);
        }
    }//GEN-LAST:event_tblLichSuDonGiaMouseClicked

    private void btnTKMaLSDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKMaLSDGActionPerformed
        listLSGTrong.clear();
        if (txtTimKiemMaDonGia.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mã đơn giá cần tìm");
        } else {
            String keyword = txtTimKiemMaDonGia.getText();
            listLSGTrong = ser.getTimKiemLSG(keyword);

            if (listLSGTrong.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy mã đơn giá nào với mã: " + keyword);
            } else {
                dtm = (DefaultTableModel) tblLichSuDonGia.getModel();
                dtm.setRowCount(0);
                for (LichSuGia sp : listLSGTrong) {
                    dtm.addRow(new Object[]{
                        sp.getMaDonGia(),
                        sp.getMaSPCT(),
                        sp.getTenSP(),
                        sp.getGiaDau(),
                        sp.getGiaSau(),
                        sp.getNgayBatDau(),
                        sp.getNgayKetThuc()
                    });

                }
            }
        }
    }//GEN-LAST:event_btnTKMaLSDGActionPerformed

    private void rdoSXMaLSDGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoSXMaLSDGMouseClicked
        loadDataLichSuGia(ser.getSXTheoMaLSDG());
    }//GEN-LAST:event_rdoSXMaLSDGMouseClicked

    private void rdoSXNgayLSDGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoSXNgayLSDGMouseClicked
        loadDataLichSuGia(ser.getSXTheoGiaLSDG());
    }//GEN-LAST:event_rdoSXNgayLSDGMouseClicked

    private void txtTimKiemTGBDLSDGFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemTGBDLSDGFocusGained
        if (txtTimKiemTGBDLSDG.getText().equals("dd-mm-yyyy")) {
            txtTimKiemTGBDLSDG.setText("");
            txtTimKiemTGBDLSDG.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtTimKiemTGBDLSDGFocusGained

    private void txtTimKiemTGBDLSDGFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemTGBDLSDGFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemTGBDLSDGFocusLost

    private void txtTimKiemTGBDLSDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemTGBDLSDGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemTGBDLSDGActionPerformed

    private void txtTimKiemTGKTLSDGFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemTGKTLSDGFocusGained
        if (txtTimKiemTGKTLSDG.getText().equals("dd-mm-yyyy")) {
            txtTimKiemTGKTLSDG.setText("");
            txtTimKiemTGKTLSDG.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtTimKiemTGKTLSDGFocusGained

    private void txtTimKiemTGKTLSDGFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemTGKTLSDGFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemTGKTLSDGFocusLost

    private void txtTimKiemTGKTLSDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemTGKTLSDGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemTGKTLSDGActionPerformed

    private void btnTKNgayLSDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKNgayLSDGActionPerformed
        int count = 0;
        String ngayBD = txtTimKiemTGBDLSDG.getText();
        String ngayKT = txtTimKiemTGKTLSDG.getText();

        if (ngayBD.isEmpty()) {
            JOptionPane.showMessageDialog(this, " Vui lòng nhập ngày bắt đầu");
            count++;
        }
        if (ngayKT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày kết thúc");
            count++;
        }
        if (count == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date date1 = sdf.parse(ngayBD);
                System.out.println(ngayBD);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không đúng định dạng");
                //                return;
            }
            try {
                Date date2 = sdf.parse(ngayKT);
                System.out.println(ngayKT);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc không đúng định dạng");
                //                return;
            }
            ArrayList<LichSuGia> listLocLSG = ser.locLichSuDonGia(ngayBD, ngayKT);
            if (listLocLSG.isEmpty()) {
                JOptionPane.showMessageDialog(this, " Không tìm thấy lịch sử giá");
                txtTimKiemTGBDLSDG.setText(" ");
                txtTimKiemTGKTLSDG.setText(" ");
                ArrayList<LichSuGia> listLichSuGia = new ArrayList<>();
                loadDataLichSuGia(listLocLSG);
            } else {
                loadDataLichSuGia(ser.locLichSuDonGia(ngayBD, ngayKT));

            }
        }
    }//GEN-LAST:event_btnTKNgayLSDGActionPerformed

    private void btnTKNgayLSDG1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKNgayLSDG1ActionPerformed
        loadDataLichSuGia(ser.getAllLichSuDonGia());
        txtTimKiemTGBDLSDG.setText(" ");
        txtTimKiemTGKTLSDG.setText(" ");
    }//GEN-LAST:event_btnTKNgayLSDG1ActionPerformed

    private void btnThemThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemThuocTinhActionPerformed
        jTabbedPane4.setSelectedIndex(2);
    }//GEN-LAST:event_btnThemThuocTinhActionPerformed

    private void lblHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhAnhMouseClicked
        // TODO add your handling code here:
        dlg.showOpenDialog(this);
        String path = dlg.getSelectedFile().getAbsolutePath();
        ImageIcon icon = new ImageIcon(path);
        Image imageIcon = icon.getImage().getScaledInstance(120, 150, Image.SCALE_SMOOTH);

    }//GEN-LAST:event_lblHinhAnhMouseClicked

    private void lblAnhNenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhNenMouseClicked
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(6);
    }//GEN-LAST:event_lblAnhNenMouseClicked

    private void btnAddLSDGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddLSDGMouseClicked
        // TODO add your handling code here:
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkLSDG() && checkTrungMaDonGia(txtMaDonGia.getText())) {
                ser.addLichSuGia(getFormLichSuGia());
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                loadDataLichSuGia(ser.getAllLichSuDonGia());
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }
    }//GEN-LAST:event_btnAddLSDGMouseClicked

    private void btnUpdateLSDGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateLSDGMouseClicked
        // TODO add your handling code here:
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn Update không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkLSDG()) {
                int row = tblLichSuDonGia.getSelectedRow();
                if (row >= 0) {
                    String ma = ser.getAllLichSuDonGia().get(row).getMaDonGia();
                    LichSuGia ls = getFormLichSuGia();
                    ls.setMaDonGia(ma);
                    ser.updateLichSuGia(ls);
                    JOptionPane.showMessageDialog(this, "Update thành công");
                    loadDataLichSuGia(ser.getAllLichSuDonGia());

                } else {
                    JOptionPane.showMessageDialog(this, "Update thất bại");
                }
            }
        }
    }//GEN-LAST:event_btnUpdateLSDGMouseClicked

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnExitMouseClicked

    private void btnNewFormKMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNewFormKMMouseClicked
        // TODO add your handling code here:
        txtMaKhuyenMai.setText("");
        txtTenKhuyenMai.setText("");
        txtNBatDauKhuyenMai.setText("");
        txtNKetThucKhuyenMai.setText("");
        txtGiamGiaKhuyenMai.setText("");
    }//GEN-LAST:event_btnNewFormKMMouseClicked

    private void btnNewFormVoucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNewFormVoucherMouseClicked
        // TODO add your handling code here:
        txtMaVoucher.setText("");
        txtTenVoucher.setText("");
        txtSoLuongVoucher.setText("");
        txtNBatDauVoucher.setText("");
        txtNKetThucVoucher.setText("");
        txtSoTienGiamVoucher.setText("");
        txtSoTienYCVoucher.setText("");
    }//GEN-LAST:event_btnNewFormVoucherMouseClicked

    private void btnSuaChiTietSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaChiTietSanPhamMouseClicked
        // TODO add your handling code here:
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn Update không");
        if (check == JOptionPane.YES_OPTION) {
            if (checkTTSP()) {
                int row = tblSanPhamCTSP.getSelectedRow();
                if (row >= 0) {
                    String ma = ser.getAllSanPhamCT().get(row).getMaSPCT();
                    SanPham sp = getFormSanPhamTTSP();
                    sp.setMaSPCT(ma);
                    ser.updateCTSPTTSP(sp);
                    JOptionPane.showMessageDialog(this, "Update thành công");
                    loadDataQLSP(ser.getAllSanPhamCT());

                } else {
                    JOptionPane.showMessageDialog(this, "Update thất bại");
                }
            }
        }
    }//GEN-LAST:event_btnSuaChiTietSanPhamMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewTrangChu_QuanLy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewTrangChu_QuanLy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewTrangChu_QuanLy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewTrangChu_QuanLy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        JFrame frame = new JFrame("Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewTrangChu_QuanLy().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddChatLieu;
    private javax.swing.JButton btnAddKichThuocTTSP;
    private javax.swing.JButton btnAddLSDG;
    private javax.swing.JButton btnAddMauSac;
    private javax.swing.JButton btnAddNhanVien;
    private javax.swing.JButton btnAddSPCT;
    private javax.swing.JButton btnAddSanPham;
    private javax.swing.JButton btnBoLocHD;
    private javax.swing.JButton btnBoLocHuy;
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnDeleteNhanVien;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHienThiBCTK;
    private javax.swing.JButton btnHienThiHD;
    private javax.swing.JButton btnHienThiHuy;
    private javax.swing.JButton btnHienThiNV;
    private javax.swing.JButton btnHienThiNVNghi;
    private javax.swing.JButton btnKhoiPhuc;
    private javax.swing.JButton btnLocHD;
    private javax.swing.JButton btnLocHuy;
    private javax.swing.JButton btnNewFormKM;
    private javax.swing.JButton btnNewFormVoucher;
    private javax.swing.JButton btnNewKichThuoc;
    private javax.swing.JButton btnNewLSDG;
    private javax.swing.JButton btnNewMauSac;
    private javax.swing.JButton btnNewNhanVien;
    private javax.swing.JButton btnNewTTSP;
    private javax.swing.JButton btnSearchHD;
    private javax.swing.JButton btnSearchHuy;
    private javax.swing.JButton btnSearchNV;
    private javax.swing.JButton btnSearchNVNghi;
    private javax.swing.JButton btnSuaChiTietSanPham;
    private javax.swing.JButton btnSuaKhuyenMai;
    private javax.swing.JButton btnSuaMauSacTTSP;
    private javax.swing.JButton btnSuaVoucher;
    private javax.swing.JButton btnTKMaLSDG;
    private javax.swing.JButton btnTKNgayLSDG;
    private javax.swing.JButton btnTKNgayLSDG1;
    private javax.swing.JButton btnTKTKNKM;
    private javax.swing.JButton btnTKTNVoucher;
    private javax.swing.JButton btnThemKhuyenMai;
    private javax.swing.JButton btnThemSPKM;
    private javax.swing.JButton btnThemThuocTinh;
    private javax.swing.JButton btnThemVoucher;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTimKiemMaSP;
    private javax.swing.JButton btnTimKiemSP;
    private javax.swing.JButton btnUpdateChatLieu;
    private javax.swing.JButton btnUpdateKichThuoc;
    private javax.swing.JButton btnUpdateLSDG;
    private javax.swing.JButton btnUpdateNhanVien;
    private javax.swing.JButton btnUpdateSP;
    private javax.swing.JButton btnUpdateSP1;
    private javax.swing.JButton btnXoaChatLieu;
    private javax.swing.JButton btnXoaKichThuoc;
    private javax.swing.JButton btnXoaMauSacTTSP;
    private javax.swing.JButton btnXoaSPKM;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.ButtonGroup buttonGroup8;
    private javax.swing.ButtonGroup buttonGroup9;
    private javax.swing.JCheckBox cbTrangThaiThemKM;
    private javax.swing.JComboBox<String> cboChatLieuTTSP;
    private javax.swing.JComboBox<String> cboKichThuocTTSP;
    private javax.swing.JComboBox<String> cboMaSPCTLSG;
    private javax.swing.JComboBox<String> cboMauSacTTSP;
    private javax.swing.JComboBox<String> cboMauTTSP;
    private javax.swing.JComboBox<String> cboTenNCCTTSP;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JLabel lblAnhNen;
    private javax.swing.JLabel lblCTT;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JLabel lblRoles;
    private javax.swing.JLabel lblTenSPLSG;
    private javax.swing.JLabel lblThanhCong;
    private javax.swing.JRadioButton rdAllKhuyenMai;
    private javax.swing.JRadioButton rdAllVoucher;
    private javax.swing.JRadioButton rdNam;
    private javax.swing.JRadioButton rdNu;
    private javax.swing.JRadioButton rdSXTMaKhuyenMai;
    private javax.swing.JRadioButton rdSXTMaVoucher;
    private javax.swing.JRadioButton rdSXTNgayKhuyenMai;
    private javax.swing.JRadioButton rdSXTNgayVoucher;
    private javax.swing.JRadioButton rdSXTTenKhuyenMai;
    private javax.swing.JRadioButton rdSXTTenVoucher;
    private javax.swing.JRadioButton rdTheoMaNV;
    private javax.swing.JRadioButton rdTheoMaNVNghi;
    private javax.swing.JRadioButton rdTheoTenNV;
    private javax.swing.JRadioButton rdTheoTenNVNghi;
    private javax.swing.JRadioButton rdTheoTuoiNhanVien;
    private javax.swing.JRadioButton rdTheoTuoiNhanVienNghi;
    private javax.swing.JRadioButton rdoSXMaLSDG;
    private javax.swing.JRadioButton rdoSXMaTTSP;
    private javax.swing.JRadioButton rdoSXNgayLSDG;
    private javax.swing.JRadioButton rdoSXTenTTSP;
    private javax.swing.JRadioButton rdoSXTheoMaSPTTSP;
    private javax.swing.JRadioButton rdoSXTheoTenTTSP;
    private javax.swing.JTable tblChatLieuTTSP;
    private javax.swing.JTable tblChiTietHoaDon;
    private javax.swing.JTable tblKMChonSP;
    private javax.swing.JTable tblKhuyenMai;
    private javax.swing.JTable tblKichThuocTTSP;
    private javax.swing.JTable tblLichSuDonGia;
    private javax.swing.JTable tblMauSacTTSP;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTable tblNhanVienNghi;
    private javax.swing.JTable tblQLHoaDon;
    private javax.swing.JTable tblQLHoaDonHuy;
    private javax.swing.JTable tblQLSanPham;
    private javax.swing.JTable tblQuanLy;
    private javax.swing.JTable tblSPKM;
    private javax.swing.JTable tblSanPhamCTSP;
    private javax.swing.JTable tblSanPhamHuy;
    private javax.swing.JTable tblSanPhamTTSP;
    private javax.swing.JTable tblThongTinChiTietSanPham;
    private javax.swing.JTable tblVoucher;
    private javax.swing.JTextField txtBatDauHD;
    private javax.swing.JTextField txtBatDauHuy;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtGiaDau;
    private javax.swing.JTextField txtGiaSau;
    private javax.swing.JTextField txtGiamGiaKMChonSP;
    private javax.swing.JTextField txtGiamGiaKhuyenMai;
    private javax.swing.JTextField txtHang;
    private javax.swing.JTextField txtHangTTSP;
    private javax.swing.JTextField txtKetThucHD;
    private javax.swing.JTextField txtKetThucHuy;
    private javax.swing.JTextField txtMaChatLieu;
    private javax.swing.JTextField txtMaDonGia;
    private javax.swing.JTextField txtMaKMChonSP;
    private javax.swing.JTextField txtMaKhuyenMai;
    private javax.swing.JTextField txtMaKichThuoc;
    private javax.swing.JTextField txtMaMauSac;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtMaSPCT;
    private javax.swing.JTextField txtMaSPThemKM;
    private javax.swing.JTextField txtMaVoucher;
    private javax.swing.JTextField txtMauTTSP;
    private javax.swing.JTextField txtNBatDauKhuyenMai;
    private javax.swing.JTextField txtNBatDauVoucher;
    private javax.swing.JTextField txtNKetThucKhuyenMai;
    private javax.swing.JTextField txtNKetThucVoucher;
    private javax.swing.JButton txtNewTTSP;
    private javax.swing.JTextField txtNgayBatDau;
    private javax.swing.JTextField txtNgayKetThuc;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSearchHD;
    private javax.swing.JTextField txtSearchHuy;
    private javax.swing.JTextField txtSearchNV;
    private javax.swing.JTextField txtSearchNVNghi;
    private javax.swing.JTextField txtSoLuongTTSP;
    private javax.swing.JTextField txtSoLuongVoucher;
    private javax.swing.JTextField txtSoTienGiamVoucher;
    private javax.swing.JTextField txtSoTienYCVoucher;
    private javax.swing.JTextField txtTGBatDauLSDG;
    private javax.swing.JTextField txtTGKetThucLSDG;
    private javax.swing.JTextField txtTKKhuyenMai;
    private javax.swing.JTextField txtTKNBDKhuyenMai;
    private javax.swing.JTextField txtTKNBDVoucher;
    private javax.swing.JTextField txtTKNKTKhuyeMai;
    private javax.swing.JTextField txtTKNKTVoucher;
    private javax.swing.JTextField txtTKVoucher;
    private javax.swing.JTextField txtTenChatLieu;
    private javax.swing.JTextField txtTenDN;
    private javax.swing.JTextField txtTenKMChonSP;
    private javax.swing.JTextField txtTenKhuyenMai;
    private javax.swing.JTextField txtTenKichThuoc;
    private javax.swing.JTextField txtTenMauSac;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTenSPKM;
    private javax.swing.JTextField txtTenSPSPCT;
    private javax.swing.JTextField txtTenSPTTSP;
    private javax.swing.JTextField txtTenVoucher;
    private javax.swing.JTextField txtTimKiemMaDonGia;
    private javax.swing.JTextField txtTimKiemMaSP;
    private javax.swing.JTextField txtTimKiemSPTTSP;
    private javax.swing.JTextField txtTimKiemTGBDLSDG;
    private javax.swing.JTextField txtTimKiemTGKTLSDG;
    private javax.swing.JTextField txtTongDoanhSoBanHang;
    private javax.swing.JTextField txtTongDoanhThu;
    private javax.swing.JTextField txtTongDonHang;
    private javax.swing.JTextField txtTuoi;
    // End of variables declaration//GEN-END:variables
}
