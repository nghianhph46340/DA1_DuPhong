/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import Service.*;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import java.security.SecureRandom;
import java.util.Random;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.TableWrapper;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.BaseFont;
import javax.swing.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 *
 * @author NGHIAPC
 */
public class ViewTrangChu_NhanVien extends javax.swing.JFrame {

    ServiceInterface ser = new ServiceImp();
    DefaultTableModel dftm;
    private static final String chuoiKyTu = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int leghth = 8;
    private static Random sR = new Random();
    ArrayList<HoaDon> listHoaDon = new ArrayList<>();
    ArrayList<SanPham> listSanPham = new ArrayList<>();
    ArrayList<HoaDon> locHoaDonVoucher = new ArrayList<>();
    ArrayList<HoaDon> listHoaDonApVoucher = new ArrayList<>();
    ArrayList<HoaDon> listHoaDonApVoucherPhu = new ArrayList<>();
    ArrayList<Voucher> listVoucer = new ArrayList<>();
    String tienThuaThongBao = "";

    public static String genTuDongHoaDon() {
        StringBuilder sB = new StringBuilder(leghth);
        for (int i = 0; i < leghth; i++) {

        }
        return null;
    }

    public LocalDate getLocalDate() {
        return LocalDate.now();
    }

    public ViewTrangChu_NhanVien() {
        initComponents();
        loadDataSanPhamBanHang(ser.getAllSanPham());
        loadDataKhachHang(ser.getAllKhachHang());
        loadDataHoaDonBanHang(ser.getAllHoaDon());
        loadDataVoucher(ser.getAllVoucher());
        loadDataKhuyenMai(ser.getAllKhuyenMai());
        //loadDataHoaDonVoucher(ser.getAllHoaDon());
    }

    void loadDataKhachHang(ArrayList<KhachHang> list) {

        dftm = (DefaultTableModel) tblKhachHang.getModel();
        dftm.setRowCount(0);
        for (KhachHang kh : list) {
            dftm.addRow(new Object[]{
                kh.getMaKhachHang(),
                kh.getTenKhachHang(),
                kh.getSDT(),
                kh.getDiaChi()
            });
        }
    }

    void loadDataXacNhanVoucher(String maHoaDon, String maVoucher) {
        dftm = (DefaultTableModel) tblXacNhanVoucher.getModel();
        dftm.setRowCount(0);
        dftm.addRow(new Object[]{
            maHoaDon,
            maVoucher
        });
    }

    void loadDataVoucher(ArrayList<Voucher> list) {
        dftm = (DefaultTableModel) tblDanhSachVoucher.getModel();
        dftm.setRowCount(0);
        for (Voucher vc : list) {
            dftm.addRow(new Object[]{
                vc.getMaVoucher(),
                vc.getTenVoucher(),
                vc.getSoLuongVC(),
                vc.getHanSuDungVC(),
                vc.getNgayBatDauVC(),
                vc.getSoTienGiam(),
                vc.getSoTienYeuCau()
            });
        }
    }

    void loadDataKhuyenMai(ArrayList<KhuyenMai> list) {
        dftm = (DefaultTableModel) tblDanhSachKhuyenMai.getModel();
        dftm.setRowCount(0);
        for (KhuyenMai km : list) {
            dftm.addRow(new Object[]{
                km.getMaKM(),
                km.getTenKM(),
                km.getHanSuDungKM(),
                km.getNgayBatDauKM(),
                km.getGiamGia()
            });
        }
    }

    double thanhTien(Integer soLuong, double donGiaSau) {

        return (soLuong * donGiaSau);
    }

    double setGiaTheoNgayLichSuDonGia(String maSanPham, ArrayList<LichSuGia> list) {
        double gia = 0;
        for (LichSuGia lsg : list) {
            LocalDate day1 = LocalDate.parse(lsg.getNgayBatDau());
            LocalDate day2 = LocalDate.parse(lsg.getNgayKetThuc());
            if (lsg.getMaSanPham().equals(maSanPham) && day1.isBefore(getLocalDate()) && day2.isAfter(getLocalDate())) {
                gia = lsg.getGiaSau();
            } else {
                gia = lsg.getGiaSau();
            }
        }
        return gia;
    }

    void tinhTongTienBanHang(ArrayList<HoaDonChiTiet> list) {
        double tongTien = 0;
        for (HoaDonChiTiet hdct : list) {
            tongTien += hdct.getDonGiaSau() * hdct.getSoLuong();
        }
        txtTongTien.setText(tongTien + "");
    }

    void tinhThanhTien() {
        double tongTien = 0;
        int row = tblThanhToanBanHang.getRowCount();
        for (int i = 0; i < row; i++) {
            tongTien += Double.parseDouble(tblThanhToanBanHang.getValueAt(i, 5).toString());
        }
        Integer tongTienSet = (int) (tongTien);
        txtTongTien.setText(tongTienSet + "");
    }

    void loadDataHoaDonChiTiet(ArrayList<HoaDonChiTiet> listHDCT) {
        dftm = (DefaultTableModel) tblThanhToanBanHang.getModel();
        dftm.setRowCount(0);
        for (HoaDonChiTiet hdct : listHDCT) {
            dftm.addRow(new Object[]{
                hdct.getMaSanPham(),
                hdct.getTenSanPham(),
                hdct.getSoLuong(),
                hdct.getDonGia(),
                hdct.getDonGiaSau(),
                thanhTien(hdct.getSoLuong(), hdct.getDonGiaSau())
            });
        }
    }

    void loadDataHoaDonBanHang(ArrayList<HoaDon> list) {
        dftm = (DefaultTableModel) tblHoaDonBanHang.getModel();
        dftm.setRowCount(0);
        for (HoaDon hd : list) {
            dftm.addRow(new Object[]{
                hd.getMaHoaDon(),
                hd.getMaNhanVien(),
                hd.getNgayTao(),
                hd.getNgayHoanThanh(),
                hd.getTrangThai(),
                hd.getLoaiThanhToan()
            });
        }

    }

    void loadDataHoaDonBanHangApDungKM(HoaDon hd) {
        dftm = (DefaultTableModel) tblHoaDonApDungVoucher.getModel();
        dftm.setRowCount(0);
        dftm.addRow(new Object[]{
            hd.getMaHoaDon(),
            hd.getMaNhanVien(),
            hd.getNgayTao(),
            hd.getTrangThai(),
            hd.getMaKhachHang()
        });

    }

    void loadDataSanPhamBanHang(ArrayList<SanPham> list) {
        dftm = (DefaultTableModel) tblSanPhamBanHang.getModel();
        dftm.setRowCount(0);
        for (SanPham sp : list) {
            dftm.addRow(new Object[]{
                sp.getMaSP(),
                sp.getTenSP(),
                sp.getChatLieu(),
                sp.getMau(),
                sp.getHang(),
                sp.getSoLuongSP(),
                sp.getKichThuoc(),
                sp.getMauSac(),
                sp.getHinhAnh(),
                sp.getDonGia(),
                sp.getNhaCungCap()
            });
        }
    }

    void loadDataHoaDonVoucher(ArrayList<HoaDon> list) {
        dftm = (DefaultTableModel) tblHoaDonApDungVoucher.getModel();
        dftm.setRowCount(0);
        for (HoaDon hd : list) {
            dftm.addRow(new Object[]{
                hd.getMaHoaDon(),
                hd.getMaNhanVien(),
                hd.getNgayTao(),
                hd.getTrangThai(),
                hd.getMaKhachHang()
            });
        }
    }

    Boolean checkSo(String so) {
        try {
            Integer.parseInt(so);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    KhachHang getFormKhachHang() {
        String maKH = txtMaKH.getText().trim();
        String tenKH = txtTenKH.getText().trim();
        String sdt = txtSDTKH.getText().trim();
        String diaChi = txtDCKH.getText().trim();
        return new KhachHang(maKH, tenKH, sdt, diaChi);
    }

    void setFormKhachHang(KhachHang kh) {
        txtMaKH.setText(kh.getMaKhachHang());
        txtTenKH.setText(kh.getTenKhachHang());
        txtSDTKH.setText(kh.getSDT());
        txtDCKH.setText(kh.getDiaChi());
    }

    void setFormHoaDon(HoaDon hd) {
        lblMaHoaDonHoaDon.setText(hd.getMaHoaDon());
        lblMaHoaDon.setText(hd.getMaHoaDon());
        lblMaNhanVienHoaDon.setText(hd.getMaNhanVien());
        lblNgayTaoHD.setText(hd.getNgayTao());
        lblNgayTao.setText(hd.getNgayTao());
        lblNgayHoanThanh.setText(hd.getNgayHoanThanh());
        txtMaKhachHangBanHang.setText(hd.getMaKhachHang());
        txtMaKhachHangBanHangHDCT.setText(hd.getMaKhachHang());

        if (hd.getLoaiThanhToan().equals("Tiền mặt")) {
            rdTienMat.setSelected(true);
            rdChuyenKhoan.setSelected(false);
        } else if (hd.getLoaiThanhToan().equals("Chuyển khoản")) {
            rdChuyenKhoan.setSelected(true);
            rdTienMat.setSelected(false);
        } else if (hd.getLoaiThanhToan().equals("Tiền mặt, Chuyển khoản")) {
            rdTienMat.setSelected(true);
            rdChuyenKhoan.setSelected(true);
        } else if (hd.getLoaiThanhToan() == null || hd.getLoaiThanhToan().isEmpty()) {
            rdTienMat.setSelected(false);
            rdChuyenKhoan.setSelected(false);
        }
    }

    Boolean checkTrungHoaDonChiTiet(String maHoaDon, String maSanPham) {
        int dem = 0;
        for (HoaDonChiTiet hdct : ser.getAllHoaDonChiTiet(maHoaDon)) {
            if (hdct.getMaSanPham().equals(maSanPham)) {
                dem++;
            }
        }
        if (dem == 0) {
            return false;
        } else {
            return true;
        }
    }

    void loadDuLieu(String maHoaDon) {
        for (HoaDonChiTiet hdct : ser.getAllHoaDonChiTiet(maHoaDon)) {
            hdct.inThonTin();
        }
    }

    Integer setMaHoaDon(ArrayList<HoaDon> list) {
        return list.size() + 1;
    }

    Integer setMaHoaDonChuan(ArrayList<HoaDon> list) {
        String maHoaDonChuan = list.get(list.size() - 1).getMaHoaDon();
        int leght = maHoaDonChuan.length();
        String maHoaDonChuanDet = maHoaDonChuan.substring(leght - 3);
        System.out.println(maHoaDonChuanDet);
        return Integer.parseInt(maHoaDonChuanDet) + 1;
    }

    Boolean checkTrungKhachHang(String maKhachHang) {
        int count = 0;
        for (KhachHang kh : ser.getAllKhachHang()) {
            if (kh.getMaKhachHang().equals(maKhachHang)) {
                count++;
            }
        }
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void exportToPDF(String maHoaDon, ArrayList<HoaDonChiTiet> list) {
        int rowHD = tblHoaDonBanHang.getSelectedRow();
        ArrayList<HoaDonChiTiet> listInThongTin = new ArrayList();
        String rsCbb = cbbLocHoaDonBanHang.getSelectedItem().toString();
        listHoaDon.clear();
        if (rsCbb.equals("Tất cả")) {
            listHoaDon = ser.getAllHoaDon();
        } else if (rsCbb.equals("Chưa hoàn thành")) {
            listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Chưa hoàn thành");
        } else if (rsCbb.equals("Đã hoàn thành")) {
            listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Đã hoàn thành");
        } else {
            listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Đã huỷ");
        }

        try {
            // Tự động chọn vị trí lưu file PDF
            String filePath = maHoaDon + ".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            PdfPTable table = new PdfPTable(6);
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 30);

            // Sample data
            String maHoaDonn = maHoaDon;
            String maKhachHang = txtMaKhachHangBanHang.getText();
            ArrayList<HoaDonChiTiet> hdct = list;
            String maNhanVien = lblMaNhanVienHoaDon.getText();
            String tongTien = txtTongTien.getText();
            String tienKhachDua = txtTienKhachDua.getText();
            String tienThua = txtTienThua.getText();

            // Write data to PDF
            Paragraph chaoMung = new Paragraph("CHAN GA GOI NEM AGONI");
            chaoMung.setFont(font);
            chaoMung.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            document.add(chaoMung);
            Paragraph maHoaDonPara = new Paragraph("#" + maHoaDon);
            maHoaDonPara.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            maHoaDonPara.setFont(font);
            document.add(maHoaDonPara);
            document.add(new Paragraph("Ma khach hang: " + maKhachHang));
            document.add(new Paragraph("Ma nhan vien: " + maNhanVien));
            document.add(new Paragraph("Danh muc san pham:"));
            document.add(new Paragraph("-----------------------------------------------------------------------------"));
            document.add(new Paragraph("."));
            table.addCell("Ma san pham");
            table.addCell("Ten san pham");
            table.addCell("So luong");
            table.addCell("Don gia");
            table.addCell("Don gia sau");
            table.addCell("Thanh tien");
            for (HoaDonChiTiet hd : hdct) {
                table.addCell(hd.getMaSanPham());
                table.addCell(hd.getTenSanPham());
                table.addCell(hd.getSoLuong() + "");
                table.addCell(hd.getDonGia() + "");
                table.addCell(hd.getDonGiaSau() + "");
                table.addCell(hd.getDonGiaSau() * hd.getSoLuong() + "");
            }
            document.add(table);
            document.add(new Paragraph("-----------------------------------------------------------------------------"));
            document.add(new Paragraph("Tong tien: " + tongTien));
            document.add(new Paragraph("Tien khach dua: " + tienKhachDua));
            document.add(new Paragraph("Tien thua: " + tienThua));

            document.close();
            JOptionPane.showMessageDialog(this, "Hoa don da dc in ra");
        } catch (Exception ex) {
            ex.printStackTrace();
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
        pnNhanVien = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtTimKiemSanPham = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPhamBanHang = new javax.swing.JTable();
        rdTheoTen = new javax.swing.JRadioButton();
        rdTheoMa = new javax.swing.JRadioButton();
        rdTheoGia = new javax.swing.JRadioButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblMaHoaDonHoaDon = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblNgayHoanThanh = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblNgayTaoHD = new javax.swing.JLabel();
        lblMaNhanVienHoaDon = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtMaKhachHangBanHang = new javax.swing.JTextField();
        rdTienMat = new javax.swing.JRadioButton();
        rdChuyenKhoan = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDonBanHang = new javax.swing.JTable();
        btnTaoHoaDon = new javax.swing.JButton();
        btnHuyHoaDon = new javax.swing.JButton();
        btnSuaHoaDon = new javax.swing.JButton();
        cbbLocHoaDonBanHang = new javax.swing.JComboBox<>();
        btnThemSanPham = new javax.swing.JButton();
        rdTatCa = new javax.swing.JRadioButton();
        btnXoaSanPhamThem = new javax.swing.JButton();
        btnNewBanHang = new javax.swing.JButton();
        btnBotSanPham = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblMaHoaDon = new javax.swing.JLabel();
        lblNgayTao = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThanhToanBanHang = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        txtMaKhachHangBanHangHDCT = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        txtTienThua = new javax.swing.JTextField();
        btnThanhToan = new javax.swing.JButton();
        lbPhanTramTru = new javax.swing.JLabel();
        btnVoucher = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDanhSachVoucher = new javax.swing.JTable();
        txtTimKiemVoucher = new javax.swing.JTextField();
        btnTimKiemVoucher = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblHoaDonApDungVoucher = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblXacNhanVoucher = new javax.swing.JTable();
        btnXacNhanVoucher = new javax.swing.JButton();
        btnHuyVoucher = new javax.swing.JButton();
        txtNgayBatDauVoucher = new javax.swing.JTextField();
        txtNgayKetThucVoucher = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cbbSapXepTheoNgayHetHanVoucher = new javax.swing.JComboBox<>();
        btnChonVoucher = new javax.swing.JButton();
        btnHienThiVoucherBanHang = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblDanhSachKhuyenMai = new javax.swing.JTable();
        txtTimKiemKhuyenMai = new javax.swing.JTextField();
        cbbSapXepTheoNgayHetHanKhuyenMai = new javax.swing.JComboBox<>();
        txtNgayKetThucKhuyenMai = new javax.swing.JTextField();
        txtNgayBatDauKhuyenMai = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        btnTimKiemKhuyenMai = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblDanhSachSanPhamApDungKM = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblHoaDonApDungKhuyenMai = new javax.swing.JTable();
        btnXacNhanKhuyenMai = new javax.swing.JButton();
        btnXacNhanHuyHoaDonKM = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        txtMaKH = new javax.swing.JTextField();
        txtSDTKH = new javax.swing.JTextField();
        txtDCKH = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        btnThemKH = new javax.swing.JButton();
        tbnSuaKH = new javax.swing.JButton();
        btnXoaKH = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnDangXuat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        pnNhanVien.setForeground(new java.awt.Color(51, 153, 255));
        pnNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        pnNhanVien.setPreferredSize(new java.awt.Dimension(1280, 720));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/chanGaGoiDemAgoNi2.png"))); // NOI18N
        jLabel32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel32MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(969, 969, 969))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnNhanVien.addTab("Trang chủ", jPanel6);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 153, 255));
        jLabel2.setText("Danh mục sản phẩm");
        jLabel2.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Tìm kiếm sản phẩm");

        txtTimKiemSanPham.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemSanPhamKeyReleased(evt);
            }
        });

        tblSanPhamBanHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Chất liệu", "Mẫu", "Hãng", "Số lượng", "Kích thước", "Màu sắc", "Hình ảnh", "Đơn giá", "Nhà cung cấp"
            }
        ));
        tblSanPhamBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamBanHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPhamBanHang);

        buttonGroup1.add(rdTheoTen);
        rdTheoTen.setText("Sắp xếp theo tên");
        rdTheoTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdTheoTenActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdTheoMa);
        rdTheoMa.setText("Sắp xếp theo mã");
        rdTheoMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdTheoMaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdTheoGia);
        rdTheoGia.setText("Sắp xếp theo giá");
        rdTheoGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdTheoGiaActionPerformed(evt);
            }
        });

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 153, 255));
        jLabel6.setText("Hoá đơn");

        jLabel10.setText("Mã hoá đơn");

        jLabel13.setText("Ngày hoàn thành");

        jLabel12.setText("Ngày tạo");

        jLabel11.setText("Mã nhân viên");

        jLabel14.setText("Mã khách hàng");

        jLabel15.setText("Loại thanh toán");

        rdTienMat.setText("Tiền mặt");

        rdChuyenKhoan.setText("Chuyển khoản");
        rdChuyenKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdChuyenKhoanActionPerformed(evt);
            }
        });

        tblHoaDonBanHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hoá đơn", "Mã nhân viên", "Ngày tạo", "Ngày hoàn thành", "Trạng thái", "Loại thanh toán"
            }
        ));
        tblHoaDonBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonBanHangMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHoaDonBanHang);

        btnTaoHoaDon.setText("Tạo hoá đơn");
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        btnHuyHoaDon.setText("Huỷ hoá đơn");
        btnHuyHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyHoaDonActionPerformed(evt);
            }
        });

        btnSuaHoaDon.setText("Sửa hoá đơn");
        btnSuaHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaHoaDonActionPerformed(evt);
            }
        });

        cbbLocHoaDonBanHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Chưa hoàn thành", "Đã hoàn thành", "Đã huỷ" }));
        cbbLocHoaDonBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbbLocHoaDonBanHangMouseClicked(evt);
            }
        });
        cbbLocHoaDonBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbLocHoaDonBanHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblMaNhanVienHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                    .addComponent(lblMaHoaDonHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(rdTienMat, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdChuyenKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel14))
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblNgayTaoHD, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(txtMaKhachHangBanHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(lblNgayHoanThanh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(btnTaoHoaDon)
                        .addGap(18, 18, 18)
                        .addComponent(btnHuyHoaDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSuaHoaDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbbLocHoaDonBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHuyHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSuaHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblMaHoaDonHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(cbbLocHoaDonBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMaNhanVienHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNgayTaoHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNgayHoanThanh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMaKhachHangBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rdTienMat)
                            .addComponent(rdChuyenKhoan))
                        .addGap(141, 141, 141))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87))))
        );

        btnThemSanPham.setBackground(new java.awt.Color(51, 153, 255));
        btnThemSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSanPham.setText("Thêm sản phẩm");
        btnThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdTatCa);
        rdTatCa.setSelected(true);
        rdTatCa.setText("Tất cả");
        rdTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdTatCaActionPerformed(evt);
            }
        });

        btnXoaSanPhamThem.setBackground(new java.awt.Color(51, 153, 255));
        btnXoaSanPhamThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaSanPhamThem.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaSanPhamThem.setText("Xoá sản phẩm");
        btnXoaSanPhamThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSanPhamThemActionPerformed(evt);
            }
        });

        btnNewBanHang.setText("New");
        btnNewBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewBanHangActionPerformed(evt);
            }
        });

        btnBotSanPham.setBackground(new java.awt.Color(51, 153, 255));
        btnBotSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBotSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnBotSanPham.setText("Bớt sản phẩm");
        btnBotSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBotSanPhamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 841, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdTheoTen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdTheoMa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdTheoGia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdTatCa))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnNewBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBotSanPham)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnThemSanPham)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaSanPhamThem)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTimKiemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdTheoTen)
                    .addComponent(rdTheoMa)
                    .addComponent(rdTheoGia)
                    .addComponent(rdTatCa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoaSanPhamThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNewBanHang)
                    .addComponent(btnBotSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 153, 255));
        jLabel4.setText("Hoá đơn chi tiết");

        jLabel5.setText("Mã hoá đơn");

        jLabel8.setText("Ngày tạo");

        jLabel9.setText("Mã khách hàng");

        tblThanhToanBanHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Giá sau giảm", "Thành tiền"
            }
        ));
        tblThanhToanBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThanhToanBanHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblThanhToanBanHang);

        jLabel16.setText("Danh mục sản phẩm");

        jLabel7.setText("Tổng tiền");

        jLabel17.setText("Tiền khách đưa");

        jLabel18.setText("Tiền thừa");

        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaKeyReleased(evt);
            }
        });

        btnThanhToan.setBackground(new java.awt.Color(51, 153, 255));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("Thanh \n\ntoán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        lbPhanTramTru.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbPhanTramTru.setText("Số tiền trừ");

        btnVoucher.setBackground(new java.awt.Color(51, 153, 255));
        btnVoucher.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnVoucher.setForeground(new java.awt.Color(255, 255, 255));
        btnVoucher.setText("Voucher");
        btnVoucher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVoucherMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(lblMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(lblNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(txtMaKhachHangBanHangHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel16)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnVoucher))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 47, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtTongTien)
                            .addComponent(txtTienKhachDua, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                            .addComponent(txtTienThua, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(lbPhanTramTru, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(198, 198, 198)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaKhachHangBanHangHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVoucher))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbPhanTramTru, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18)))
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pnNhanVien.addTab("Bán hàng", jPanel4);

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 153, 255));
        jLabel19.setText("Danh sách voucher");

        tblDanhSachVoucher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã voucher", "Tên voucher", "Số lượng", "Hạn sử dụng", "Ngày bắt đầu", "Số tiền giảm", "Số tiền yêu cầu"
            }
        ));
        tblDanhSachVoucher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachVoucherMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblDanhSachVoucher);

        txtTimKiemVoucher.setToolTipText("");

        btnTimKiemVoucher.setBackground(new java.awt.Color(0, 153, 255));
        btnTimKiemVoucher.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiemVoucher.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemVoucher.setText("Tìm kiếm");
        btnTimKiemVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemVoucherActionPerformed(evt);
            }
        });

        tblHoaDonApDungVoucher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hoá đơn", "Mã nhân viên", "Ngày tạo", "Trạng thái", "Mã khách hàng"
            }
        ));
        tblHoaDonApDungVoucher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonApDungVoucherMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblHoaDonApDungVoucher);

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 153, 255));
        jLabel22.setText("Danh sách hoá đơn áp dụng");

        tblXacNhanVoucher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hoá đơn", "Mã áp dụng"
            }
        ));
        jScrollPane7.setViewportView(tblXacNhanVoucher);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnXacNhanVoucher.setBackground(new java.awt.Color(51, 153, 255));
        btnXacNhanVoucher.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXacNhanVoucher.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhanVoucher.setText("Xác nhận");
        btnXacNhanVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanVoucherActionPerformed(evt);
            }
        });

        btnHuyVoucher.setBackground(new java.awt.Color(51, 153, 255));
        btnHuyVoucher.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHuyVoucher.setForeground(new java.awt.Color(255, 255, 255));
        btnHuyVoucher.setText("Huỷ");
        btnHuyVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyVoucherActionPerformed(evt);
            }
        });

        jLabel20.setText("Ngày bắt đầu");

        jLabel24.setText("Ngày kết thúc");

        cbbSapXepTheoNgayHetHanVoucher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo ngày sắp hết hạn", "Dưới 1 Ngày", "Dưới 10 ngày", "Dưới 20 ngày" }));

        btnChonVoucher.setBackground(new java.awt.Color(51, 102, 255));
        btnChonVoucher.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnChonVoucher.setForeground(new java.awt.Color(255, 255, 255));
        btnChonVoucher.setText("Chọn");
        btnChonVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonVoucherActionPerformed(evt);
            }
        });

        btnHienThiVoucherBanHang.setBackground(new java.awt.Color(51, 153, 255));
        btnHienThiVoucherBanHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHienThiVoucherBanHang.setForeground(new java.awt.Color(255, 255, 255));
        btnHienThiVoucherBanHang.setText("Hiển thị");
        btnHienThiVoucherBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHienThiVoucherBanHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(txtTimKiemVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnTimKiemVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(cbbSapXepTheoNgayHetHanVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(37, 37, 37)
                                        .addComponent(btnHienThiVoucherBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel24))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNgayKetThucVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNgayBatDauVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnXacNhanVoucher)
                            .addComponent(btnHuyVoucher)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(40, 40, 40)
                                .addComponent(btnChonVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(500, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimKiemVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTimKiemVoucher))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbSapXepTheoNgayHetHanVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHienThiVoucherBanHang)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNgayBatDauVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNgayKetThucVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(btnChonVoucher))))
                .addGap(17, 17, 17)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(btnXacNhanVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHuyVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Voucher", jPanel7);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 153, 255));
        jLabel21.setText("Danh sách khuyến mại");

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        tblDanhSachKhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã khuyến mãi", "Tên khuyến mãi", "Hạn sử dụng", "Ngày bắt đầu", "Phần trăm giảm"
            }
        ));
        jScrollPane6.setViewportView(tblDanhSachKhuyenMai);

        txtTimKiemKhuyenMai.setToolTipText("");

        cbbSapXepTheoNgayHetHanKhuyenMai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo ngày sắp hết hạn", "Dưới 1 Ngày", "Dưới 10 ngày", "Dưới 20 ngày" }));

        jLabel25.setText("Ngày bắt đầu");

        jLabel26.setText("Ngày kết thúc");

        btnTimKiemKhuyenMai.setBackground(new java.awt.Color(51, 102, 255));
        btnTimKiemKhuyenMai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiemKhuyenMai.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemKhuyenMai.setText("Tìm kiếm");
        btnTimKiemKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemKhuyenMaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbSapXepTheoNgayHetHanKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(txtTimKiemKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiemKhuyenMai)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNgayKetThucKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayBatDauKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimKiemKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTimKiemKhuyenMai))
                        .addGap(18, 18, 18)
                        .addComponent(cbbSapXepTheoNgayHetHanKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNgayBatDauKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNgayKetThucKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        tblDanhSachSanPhamApDungKM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Nhà cung cấp"
            }
        ));
        jScrollPane8.setViewportView(tblDanhSachSanPhamApDungKM);

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(51, 153, 255));
        jLabel23.setText("Danh sách sản phẩm áp dụng");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                        .addGap(36, 36, 36))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tblHoaDonApDungKhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Voucher", "Hoá đơn"
            }
        ));
        jScrollPane9.setViewportView(tblHoaDonApDungKhuyenMai);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        btnXacNhanKhuyenMai.setBackground(new java.awt.Color(0, 153, 255));
        btnXacNhanKhuyenMai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXacNhanKhuyenMai.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhanKhuyenMai.setText("Xác nhận");

        btnXacNhanHuyHoaDonKM.setBackground(new java.awt.Color(51, 153, 255));
        btnXacNhanHuyHoaDonKM.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXacNhanHuyHoaDonKM.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhanHuyHoaDonKM.setText("Huỷ");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXacNhanKhuyenMai)
                    .addComponent(btnXacNhanHuyHoaDonKM))
                .addContainerGap(560, Short.MAX_VALUE))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnXacNhanHuyHoaDonKM, btnXacNhanKhuyenMai});

        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btnXacNhanKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXacNhanHuyHoaDonKM, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 61, Short.MAX_VALUE))))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnXacNhanHuyHoaDonKM, btnXacNhanKhuyenMai});

        jTabbedPane1.addTab("Khuyến mãi", jPanel12);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1787, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pnNhanVien.addTab("Ưu đãi", jPanel5);

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 153, 255));
        jLabel27.setText("Thông tin khách hàng");

        jLabel28.setText("Mã khách hàng");

        jLabel29.setText("Tên khách hàng");

        jLabel30.setText("Số điện thoại");

        jLabel31.setText("Địa chỉ");

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Địa chỉ"
            }
        ));
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tblKhachHang);

        btnThemKH.setBackground(new java.awt.Color(51, 153, 255));
        btnThemKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemKH.setForeground(new java.awt.Color(255, 255, 255));
        btnThemKH.setText("Thêm");
        btnThemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKHActionPerformed(evt);
            }
        });

        tbnSuaKH.setBackground(new java.awt.Color(51, 153, 255));
        tbnSuaKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tbnSuaKH.setForeground(new java.awt.Color(255, 255, 255));
        tbnSuaKH.setText("Sửa");
        tbnSuaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbnSuaKHActionPerformed(evt);
            }
        });

        btnXoaKH.setBackground(new java.awt.Color(51, 153, 255));
        btnXoaKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaKH.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaKH.setText("Xoá");
        btnXoaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addGap(37, 37, 37)
                                .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel31))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTenKH)
                                    .addComponent(txtSDTKH)
                                    .addComponent(txtDCKH, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))
                        .addGap(160, 160, 160)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnThemKH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tbnSuaKH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnXoaKH, javax.swing.GroupLayout.Alignment.LEADING))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 970, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 459, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txtSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(btnThemKH)
                        .addGap(18, 18, 18)
                        .addComponent(tbnSuaKH)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaKH)))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtDCKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(312, Short.MAX_VALUE))
        );

        pnNhanVien.addTab("Khách hàng", jPanel8);

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(565, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(281, 281, 281))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 885, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(jLabel1)
                .addGap(287, 287, 287))
        );

        pnNhanVien.addTab("?", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 1429, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdChuyenKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdChuyenKhoanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdChuyenKhoanActionPerformed

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        // TODO add your handling code here:
        //int row = tblHoaDonBanHang.getSelectedRow();
        //String trangThai = ser.getRowHoaDon(row).getTrangThai();
        //System.out.println(trangThai);
        String maNhanVien = ser.searchMaNhanVienTheoTenDangNhap(ser.listLoginBanHang());
        System.out.println(maNhanVien);
        int y = JOptionPane.showConfirmDialog(this, "Are you chắc tạo hoá alone");
        if (y == JOptionPane.YES_OPTION) {
            HoaDon hd = new HoaDon("HD0" + setMaHoaDonChuan(ser.getAllHoaDon()), getLocalDate() + "", "Chưa hoàn thành", maNhanVien, "Tiền mặt");
            loadDataHoaDonBanHang(ser.addHoaDonBanHang(hd));
            JOptionPane.showMessageDialog(this, "Thêm hoá đơn thành công");
            loadDataHoaDonBanHang(ser.getAllHoaDon());
        } else {
            JOptionPane.showMessageDialog(this, "Đã huỷ");
        }
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void btnHuyHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyHoaDonActionPerformed
        // TODO add your handling code here:

        String rsCbb = cbbLocHoaDonBanHang.getSelectedItem().toString();
        listHoaDon.clear();
        if (rsCbb.equals("Tất cả")) {
            listHoaDon = ser.getAllHoaDon();
        } else if (rsCbb.equals("Chưa hoàn thành")) {
            listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Chưa hoàn thành");
        } else if (rsCbb.equals("Đã hoàn thành")) {
            listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Đã hoàn thành");
        } else {
            listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Đã huỷ");
        }
        int rowHD = tblHoaDonBanHang.getSelectedRow();
        if (rowHD >= 0) {
            if (listHoaDon.get(rowHD).getTrangThai().equals("Chưa hoàn thành")) {
                if (ser.getAllHoaDonChiTiet(listHoaDon.get(rowHD).getMaHoaDon()).isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Hoá đơn này đã trống -> Xoá hoá đơn");
                    int yHD = JOptionPane.showConfirmDialog(this, "Xoá hoá đơn không?");
                    if (yHD == JOptionPane.YES_OPTION) {
                        loadDataHoaDonBanHang(ser.xoaHoaDonBanhang(listHoaDon.get(rowHD).getMaHoaDon()));
                        loadDataHoaDonBanHang(ser.getAllHoaDon());
                    } else {
                        JOptionPane.showMessageDialog(this, "Đã huỷ");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Hoá đơn này đã có sản phẩm chỉ có thể huỷ");
                    int yHDHuy = JOptionPane.showConfirmDialog(this, "Huỷ hoá đơn không?");
                    if (yHDHuy == JOptionPane.YES_OPTION) {
                        loadDataHoaDonBanHang(ser.huyHoaDonBanHang(listHoaDon.get(rowHD).getMaHoaDon(), "Đã huỷ"));
                        loadDataHoaDonBanHang(ser.getAllHoaDon());
                    } else {
                        JOptionPane.showMessageDialog(this, "Đã huỷ");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không thể huỷ hoá đơn đã thanh toán hoặc đã huỷ");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chưa chọn hoá đơn");
        }
    }//GEN-LAST:event_btnHuyHoaDonActionPerformed

    private void btnSuaHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaHoaDonActionPerformed
        // TODO add your handling code here:
        int row = tblHoaDonBanHang.getSelectedRow();
        if (row >= 0) {
            int y = JOptionPane.showConfirmDialog(this, "Có muốn sửa hoá đơn không?");
            if (y == JOptionPane.YES_OPTION) {
                String maKhachHang = txtMaKhachHangBanHang.getText().trim().toUpperCase();
                String loaiThanhToann = "";
                if (rdTienMat.isSelected()) {
                    loaiThanhToann = "Tiền mặt";
                }
                if (rdChuyenKhoan.isSelected()) {
                    loaiThanhToann = "Chuyển khoản";
                }
                if (rdChuyenKhoan.isSelected() && rdTienMat.isSelected()) {
                    loaiThanhToann = "Tiền mặt, Chuyển khoản";
                }
                if (!rdChuyenKhoan.isSelected() && !rdTienMat.isSelected()) {
                    JOptionPane.showMessageDialog(this, "Chưa chọn thể loại thành toán");
                    return;
                }
                if (checkTrungKhachHang(maKhachHang) || maKhachHang.isEmpty() || maKhachHang.equals("")) {
                    System.out.println("Đã chạy đến đây");
                    String rsCbb = cbbLocHoaDonBanHang.getSelectedItem().toString();
                    listHoaDon.clear();
                    if (rsCbb.equals("Tất cả")) {
                        listHoaDon = ser.getAllHoaDon();
                    } else if (rsCbb.equals("Chưa hoàn thành")) {
                        listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Chưa hoàn thành");
                    } else if (rsCbb.equals("Đã hoàn thành")) {
                        listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Đã hoàn thành");
                    } else {
                        listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Đã huỷ");
                    }
                    if (listHoaDon.get(row).getTrangThai().equals("Chưa hoàn thành")) {
                        HoaDon hd = listHoaDon.get(row);
                        hd.inThongTin();
                        HoaDon hdUp = new HoaDon(hd.getMaHoaDon(), hd.getNgayTao(), hd.getTrangThai(), hd.getMaVoucher(), hd.getMaNhanVien(), hd.getNgayHoanThanh(), loaiThanhToann, maKhachHang);
                        hdUp.inThongTin();
//                if (hd == hdUp) {
//                    JOptionPane.showMessageDialog(this, "Chưa thay đổi thông tin");
//                }else{
//                    JOptionPane.showMessageDialog(this, "Không trùng");
//                }
                        loadDataHoaDonBanHang(ser.updateLoaiThanhToanMaKhachHangBanHang(hdUp));
                        JOptionPane.showMessageDialog(this, "Update thành công");
                        loadDataHoaDonBanHang(ser.getAllHoaDon());
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể thay đổi thông tin hoá đơn đã thanh toán hoặc đã huỷ");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Khách hàng không có trong danh sách mời thêm khách hàng");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Đã huỷ");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chưa chọn hoá đơn");
        }


    }//GEN-LAST:event_btnSuaHoaDonActionPerformed

    private void btnThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamActionPerformed
        // TODO add your handling code here:
        if (!ser.TimKiemSanPhamTheoMaVaTenBanHang(txtTimKiemSanPham.getText().trim()).isEmpty() && !txtTimKiemSanPham.getText().trim().isEmpty()) {
            listSanPham.clear();
            listSanPham = ser.TimKiemSanPhamTheoMaVaTenBanHang(txtTimKiemSanPham.getText().trim());
        } else {
            if (rdTatCa.isSelected()) {
                listSanPham.clear();
                listSanPham = ser.getAllSanPham();
            } else if (rdTheoGia.isSelected()) {
                listSanPham.clear();
                listSanPham = ser.sapXepSanPhamTheoGiaBanHang();
            } else if (rdTheoMa.isSelected()) {
                listSanPham.clear();
                listSanPham = ser.sapXepSanPhamTheoMaBanHang();
            } else if (rdTheoTen.isSelected()) {
                listSanPham.clear();
                listSanPham = ser.sapXepSanPhamTheoTenBanHang();
            }
        }
        int row = tblSanPhamBanHang.getSelectedRow();
        int rowHD = tblHoaDonBanHang.getSelectedRow();
        String trangThaiHoaDon = ser.getRowHoaDon(rowHD).getTrangThai();
        System.out.println(trangThaiHoaDon);
        String maHoaDon = ser.getRowHoaDon(rowHD).getMaHoaDon();
        Integer soLuong = null;
        if (trangThaiHoaDon.equals("Chưa hoàn thành")) {
            if (row >= 0) {
                String input = JOptionPane.showInputDialog(this, "Mời chọn số lượng cần thêm");
                if (input != null) {
                    if (checkSo(input)) {
                        soLuong = Integer.parseInt(input);
                        if (soLuong > listSanPham.get(row).getSoLuongSP()) {
                            JOptionPane.showMessageDialog(this, "Nhập quá nhiều số lượng trong kho");
                        } else if (soLuong == 0) {
                            JOptionPane.showMessageDialog(this, "Chưa thay đổi số lượng");
                        } else if (soLuong < 0) {
                            JOptionPane.showMessageDialog(this, "Số lượng không thể âm");
                        } else if (soLuong > 0 && soLuong <= listSanPham.get(row).getSoLuongSP()) {
                            System.out.println("soLuong: " + soLuong);
                            String maSanPham = listSanPham.get(row).getMaSP();
                            String maSanPhamChiTiet = ser.getMaSanPhamChiTietFromSanPham(maSanPham);
                            loadDuLieu(maHoaDon);
                            if (checkTrungHoaDonChiTiet(maHoaDon, maSanPhamChiTiet)) {
                                JOptionPane.showMessageDialog(this, "Thêm số lượng sản phẩm thành công");
                                loadDataHoaDonChiTiet(ser.updateSoluongSanPhamBanHang(maSanPhamChiTiet, soLuong, maHoaDon));
                                loadDataSanPhamBanHang(ser.updateSanPhamTruBanHang(maSanPhamChiTiet, soLuong));
                                loadDataHoaDonChiTiet(ser.getAllHoaDonChiTietChuaHoanThanh(maHoaDon));
                                loadDataSanPhamBanHang(listSanPham);
                                tinhThanhTien();
                                txtTimKiemSanPham.setText("");
                            } else {
                                JOptionPane.showMessageDialog(this, "Đã thêm sản phẩm mới vào hoá đơn");
                                loadDataHoaDonChiTiet(ser.addHoaDonChiTiet(new HoaDonChiTiet(maHoaDon, soLuong, maSanPhamChiTiet)));
                                loadDataSanPhamBanHang(ser.updateSanPhamTruBanHang(maSanPhamChiTiet, soLuong));
                                loadDataHoaDonChiTiet(ser.getAllHoaDonChiTietChuaHoanThanh(maHoaDon));
                                loadDataSanPhamBanHang(listSanPham);
                                tinhThanhTien();
                                txtTimKiemSanPham.setText("");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể nhập ký tự ngoài số");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Đã huỷ");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm để thêm");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Hoá đơn này không thể thêm sản phẩm vì hoá đơn " + trangThaiHoaDon.toLowerCase());
        }

    }//GEN-LAST:event_btnThemSanPhamActionPerformed

    private void btnXoaSanPhamThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSanPhamThemActionPerformed
        // TODO add your handling code here:
        int rowHD = tblHoaDonBanHang.getSelectedRow();
        int rowHDCT = tblThanhToanBanHang.getSelectedRow();
        listSanPham.clear();
        if (rdTatCa.isSelected()) {
            listSanPham = ser.getAllSanPham();
        } else if (rdTheoGia.isSelected()) {
            listSanPham = ser.sapXepSanPhamTheoGiaBanHang();
        } else if (rdTheoMa.isSelected()) {
            listSanPham = ser.sapXepSanPhamTheoMaBanHang();
        } else if (rdTheoTen.isSelected()) {
            listSanPham = ser.sapXepSanPhamTheoTenBanHang();
        }
        String trangThaiHoaDon = ser.getRowHoaDon(rowHD).getTrangThai();
        System.out.println(trangThaiHoaDon);
        if (trangThaiHoaDon.equals("Chưa hoàn thành")) {
            if (rowHDCT >= 0) {
                String maHoaDon = ser.getRowHoaDon(rowHD).getMaHoaDon();
                String maSanPhamChiTiet = ser.getAllHoaDonChiTiet(maHoaDon).get(rowHDCT).getMaSanPham();
                int soLuong = ser.getAllHoaDonChiTiet(maHoaDon).get(rowHDCT).getSoLuong();
                System.out.println(soLuong);
                int y = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xoá sản phẩm không??");
                if (y == JOptionPane.YES_OPTION) {
                    loadDataHoaDonChiTiet(ser.deleteHoaDonChiTiet(maSanPhamChiTiet, maHoaDon));
                    loadDataSanPhamBanHang(ser.updateSanPhamCongBanHang(maSanPhamChiTiet, soLuong));
                    loadDataSanPhamBanHang(ser.getAllSanPham());
                    loadDataHoaDonChiTiet(ser.getAllHoaDonChiTietChuaHoanThanh(maHoaDon));
                    JOptionPane.showMessageDialog(this, "Xoá thành công sản phẩm khỏi hoá đơn");
                    tinhThanhTien();
                } else {
                    JOptionPane.showMessageDialog(this, "Đã huỷ");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Chưa chọn dòng để xoá");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Hoá đơn này không thể xoá sản phầm vì hoá đơn " + trangThaiHoaDon.toLowerCase());
        }

    }//GEN-LAST:event_btnXoaSanPhamThemActionPerformed

    private void btnTimKiemKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemKhuyenMaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemKhuyenMaiActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        // TODO add your handling code here:
        ViewDangNhap viewDN = new ViewDangNhap();
        this.setVisible(false);
        viewDN.setVisible(true);
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        // TODO add your handling code here:
        int row = tblKhachHang.getSelectedRow();
        setFormKhachHang(ser.getAllKhachHang().get(row));
    }//GEN-LAST:event_tblKhachHangMouseClicked

    boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d+");
    }

    boolean checkKH() {
        if (txtMaKH.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Mã Khách hàng không  để trống");
            txtMaKH.requestFocus();

            return false;
        } else if (txtTenKH.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Tên khách hàng không để trống");
            txtTenKH.requestFocus();

            return false;
        } else if (txtSDTKH.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không để trống");
            txtSDTKH.requestFocus();
            return false;
        } else if (txtDCKH.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "địa chỉ không để trống ");
            txtDCKH.requestFocus();

            return false;
        }
        // Kiểm tra số điện thoại chỉ chứa chữ số
        if (!isValidPhoneNumber(txtSDTKH.getText())) {
            JOptionPane.showMessageDialog(this, "Số ĐT chỉ được chứa chữ số");
            txtSDTKH.requestFocus();
            return false;
        }

        return true;
    }

    boolean checkTrung(ArrayList<KhachHang> list, String maKH) {
        int dem = 0;
        for (KhachHang kh : list) {
            if (kh.getMaKhachHang().equals(maKH)) {
                dem++;
            }
        }
        if (dem == 0) {
            return false;
        } else {
            return true;
        }
    }
    private void btnThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKHActionPerformed
        if (checkKH()) {
            String maKH = txtMaKH.getText();

            // Kiểm tra trùng mã khách hàng
            if (checkTrung(ser.getAllKhachHang(), maKH)) {
                JOptionPane.showMessageDialog(this, "Mã khách hàng đã tồn tại");
            } else {
                ser.addKhachHang(getFormKhachHang());
                loadDataKhachHang(ser.getAllKhachHang());
                JOptionPane.showMessageDialog(this, "Thêm thành công");
            }
        }
    }//GEN-LAST:event_btnThemKHActionPerformed

    private void tbnSuaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbnSuaKHActionPerformed
        if (checkKH()) {
            int row = tblKhachHang.getSelectedRow();
            if (row >= 0) {
                String maKH = txtMaKH.getText();

                KhachHang kh = getFormKhachHang();
                kh.setMaKhachHang(maKH);
                ser.updateKhachHang(kh);
                loadDataKhachHang(ser.getAllKhachHang());
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");

            }
        }
    }//GEN-LAST:event_tbnSuaKHActionPerformed

    private void btnXoaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKHActionPerformed
        int row = tblKhachHang.getSelectedRow();
        if (row >= 0) {
            String maKH = ser.getRowKhachHang(row).getMaKhachHang();
            ser.deleteKhachHang(maKH);
            loadDataKhachHang(ser.getAllKhachHang());
            JOptionPane.showMessageDialog(this, "Xoá thành công khách hàng");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xoá");
        }
    }//GEN-LAST:event_btnXoaKHActionPerformed
    private void tblHoaDonBanHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonBanHangMouseClicked
        // TODO add your handling code here:
        int row = tblHoaDonBanHang.getSelectedRow();
        txtTienThua.setText("");
        txtTienKhachDua.setText("");
        String rsCbb = cbbLocHoaDonBanHang.getSelectedItem().toString();
        listHoaDon.clear();
        if (rsCbb.equals("Tất cả")) {
            listHoaDon = ser.getAllHoaDon();
        } else if (rsCbb.equals("Chưa hoàn thành")) {
            listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Chưa hoàn thành");
        } else if (rsCbb.equals("Đã hoàn thành")) {
            listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Đã hoàn thành");
        } else {
            listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Đã huỷ");
        }

        System.out.println(row);
        if (row >= 0) {
            listHoaDonApVoucher.add(listHoaDon.get(row));
            //listHoaDonApVoucherPhu.add(listHoaDon.get(row));
            loadDataHoaDonBanHangApDungKM(listHoaDon.get(row));
            listHoaDonApVoucher.clear();
            if (listHoaDon.get(row).getTrangThai().equals("Đã hoàn thành") || listHoaDon.get(row).getTrangThai().equals("Đã huỷ")) {
                txtMaKhachHangBanHang.setEnabled(false);
                txtTienKhachDua.setEnabled(false);
                txtTienThua.setEnabled(false);
                btnThanhToan.setEnabled(false);
                btnChonVoucher.setEnabled(false);
                setFormHoaDon(listHoaDon.get(row));
                loadDataHoaDonChiTiet(ser.getAllHoaDonChiTiet(listHoaDon.get(row).getMaHoaDon()));
                String maVoucher = listHoaDon.get(row).getMaVoucher();
                if (maVoucher == null) {
                    tinhThanhTien();
                    loadDataVoucher(ser.showHoaDonTheoVoucher(Integer.valueOf(txtTongTien.getText())));
                } else {
                    double tongTien = 0;
                    int rowHDBH = tblThanhToanBanHang.getRowCount();
                    for (int i = 0; i < rowHDBH; i++) {
                        tongTien += Double.parseDouble(tblThanhToanBanHang.getValueAt(i, 5).toString());
                    }
                    Integer tongTienSet = (int) (tongTien);
                    Integer tienGiam = ser.layGiaGiamVoucher(maVoucher);
                    txtTongTien.setText(tongTienSet + " - " + tienGiam);
                    lbPhanTramTru.setText(tienGiam + "");
                }

            } else {
                txtMaKhachHangBanHang.setEnabled(true);
                txtTienKhachDua.setEnabled(true);
                txtTienKhachDua.setEditable(true);
                txtTienThua.setEnabled(true);
                tblSanPhamBanHang.setEnabled(true);
                btnThemSanPham.setEnabled(true);
                btnXoaSanPhamThem.setEnabled(true);
                btnBotSanPham.setEnabled(true);
                btnThanhToan.setEnabled(true);
                btnChonVoucher.setEnabled(true);
                lbPhanTramTru.setText("Số tiền giảm");
                setFormHoaDon(listHoaDon.get(row));
                loadDataHoaDonChiTiet(ser.getAllHoaDonChiTietChuaHoanThanh(listHoaDon.get(row).getMaHoaDon()));
                tinhThanhTien();
                loadDataVoucher(ser.showHoaDonTheoVoucher(Integer.valueOf(txtTongTien.getText())));
            }

        } else {
            listHoaDonApVoucher.clear();
            loadDataHoaDonBanHangApDungKM(listHoaDon.get(row));
        }
    }//GEN-LAST:event_tblHoaDonBanHangMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        tblSanPhamBanHang.setEnabled(false);
        btnThemSanPham.setEnabled(false);
        btnXoaSanPhamThem.setEnabled(false);
        btnBotSanPham.setEnabled(false);
        String thu = ser.listLoginBanHang();
        System.out.println(thu);
        txtTienKhachDua.setEditable(false);
        txtTienThua.setEditable(false);
        txtTongTien.setEditable(false);
        btnThanhToan.setEnabled(false);
    }//GEN-LAST:event_formWindowActivated

    private void btnNewBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewBanHangActionPerformed
        // TODO add your handling code here:
        loadDataHoaDonBanHang(ser.getAllHoaDon());
        loadDataHoaDonChiTiet(ser.getAllHoaDonChiTiet(""));
        tblSanPhamBanHang.setEnabled(false);
        btnThemSanPham.setEnabled(false);
        btnXoaSanPhamThem.setEnabled(false);
        btnBotSanPham.setEnabled(false);
        txtTongTien.setText("");
        setFormHoaDon(new HoaDon("", "", "", "", "", "", "", ""));
        rdTatCa.setSelected(true);
        txtTimKiemSanPham.setText("");
        loadDataSanPhamBanHang(ser.getAllSanPham());
        txtTienKhachDua.setEditable(false);
        txtTienThua.setEditable(false);
        txtTongTien.setEditable(false);
        txtTienKhachDua.setText("");
        txtTienThua.setText("");
        loadDataHoaDonBanHangApDungKM(new HoaDon());
        loadDataVoucher(ser.getAllVoucher());
        loadDataXacNhanVoucher("", "");
        lbPhanTramTru.setText("Số tiền giảm");
    }//GEN-LAST:event_btnNewBanHangActionPerformed

    private void tblSanPhamBanHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamBanHangMouseClicked
        // TODO add your handling code here:

        int row = tblSanPhamBanHang.getSelectedRow();

        if (!ser.TimKiemSanPhamTheoMaVaTenBanHang(txtTimKiemSanPham.getText().trim()).isEmpty() && !txtTimKiemSanPham.getText().trim().isEmpty()) {
            listSanPham.clear();
            listSanPham = ser.TimKiemSanPhamTheoMaVaTenBanHang(txtTimKiemSanPham.getText().trim());
        } else {
            System.out.println("Roong");
            if (rdTatCa.isSelected()) {
                listSanPham.clear();
                listSanPham = ser.getAllSanPham();
            } else if (rdTheoGia.isSelected()) {
                listSanPham.clear();
                listSanPham = ser.sapXepSanPhamTheoGiaBanHang();
            } else if (rdTheoMa.isSelected()) {
                listSanPham.clear();
                listSanPham = ser.sapXepSanPhamTheoMaBanHang();
            } else if (rdTheoTen.isSelected()) {
                listSanPham.clear();
                listSanPham = ser.sapXepSanPhamTheoTenBanHang();
            }
        }

        listSanPham.get(row).inThongTin();
    }//GEN-LAST:event_tblSanPhamBanHangMouseClicked

    private void btnBotSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBotSanPhamActionPerformed
        // TODO add your handling code here:
        int rowHD = tblHoaDonBanHang.getSelectedRow();
        int row = tblThanhToanBanHang.getSelectedRow();
        listSanPham.clear();
        if (rdTatCa.isSelected()) {
            listSanPham = ser.getAllSanPham();
        } else if (rdTheoGia.isSelected()) {
            listSanPham = ser.sapXepSanPhamTheoGiaBanHang();
        } else if (rdTheoMa.isSelected()) {
            listSanPham = ser.sapXepSanPhamTheoMaBanHang();
        } else if (rdTheoTen.isSelected()) {
            listSanPham = ser.sapXepSanPhamTheoTenBanHang();
        }
        String maHoaDon = ser.getRowHoaDon(rowHD).getMaHoaDon();
        System.out.println("mhd: " + maHoaDon);
        String trangThaiHoaDon = ser.getRowHoaDon(rowHD).getTrangThai();
        System.out.println(trangThaiHoaDon);
        Integer soLuong = null;
        if (trangThaiHoaDon.equals("Chưa hoàn thành")) {
            if (row >= 0) {
                String input = JOptionPane.showInputDialog(this, "Mời chọn số lượng cần sửa");
                if (input != null) {
                    if (checkSo(input)) {
                        soLuong = Integer.parseInt(input);
                        if (soLuong >= ser.getAllHoaDonChiTiet(maHoaDon).get(row).getSoLuong()) {
                            JOptionPane.showMessageDialog(this, "Số lượng sản phẩm tối thiểu là 1");
                        }
                        if (soLuong == 0) {
                            JOptionPane.showMessageDialog(this, "Chưa thay đổi số lượng");
                        } else if (soLuong < 0) {
                            JOptionPane.showMessageDialog(this, "Số lượng không thể âm");
                        } else if (soLuong > 0 && soLuong < ser.getAllHoaDonChiTiet(maHoaDon).get(row).getSoLuong()) {
                            String maSanPhamChiTiet = ser.getAllHoaDonChiTiet(maHoaDon).get(row).getMaSanPham();
                            JOptionPane.showMessageDialog(this, "Giảm số lượng thành công");
                            loadDataHoaDonChiTiet(ser.updateSoluongSanPhamBanHangTru(maSanPhamChiTiet, soLuong, maHoaDon));
                            loadDataSanPhamBanHang(ser.updateSanPhamCongBanHang(maSanPhamChiTiet, soLuong));
                            loadDataHoaDonChiTiet(ser.getAllHoaDonChiTietChuaHoanThanh(maHoaDon));
                            loadDataSanPhamBanHang(ser.getAllSanPham());
                            tinhThanhTien();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể nhập ký tự ngoài số");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Đã huỷ");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Chưa chọn dòng");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không thể bớt sản phẩm vì hoá đơn " + trangThaiHoaDon.toLowerCase());
        }

    }//GEN-LAST:event_btnBotSanPhamActionPerformed

    private void tblThanhToanBanHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThanhToanBanHangMouseClicked
        // TODO add your handling code here:
        int row = tblHoaDonBanHang.getSelectedRow();
        System.out.println(ser.getAllHoaDonChiTiet(ser.getRowHoaDon(row).getMaHoaDon()));
    }//GEN-LAST:event_tblThanhToanBanHangMouseClicked

    private void rdTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdTatCaActionPerformed
        // TODO add your handling code here:
        loadDataSanPhamBanHang(ser.getAllSanPham());
    }//GEN-LAST:event_rdTatCaActionPerformed

    private void rdTheoTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdTheoTenActionPerformed
        // TODO add your handling code here:
        loadDataSanPhamBanHang(ser.sapXepSanPhamTheoTenBanHang());
    }//GEN-LAST:event_rdTheoTenActionPerformed

    private void rdTheoMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdTheoMaActionPerformed
        // TODO add your handling code here:
        loadDataSanPhamBanHang(ser.sapXepSanPhamTheoMaBanHang());
    }//GEN-LAST:event_rdTheoMaActionPerformed

    private void rdTheoGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdTheoGiaActionPerformed
        // TODO add your handling code here:
        loadDataSanPhamBanHang(ser.sapXepSanPhamTheoGiaBanHang());
    }//GEN-LAST:event_rdTheoGiaActionPerformed

    private void txtTimKiemSanPhamKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemSanPhamKeyReleased
        // TODO add your handling code here:

        String timKiem = txtTimKiemSanPham.getText().trim();
        if (timKiem.isEmpty()) {
            rdTatCa.setEnabled(true);
            rdTheoGia.setEnabled(true);
            rdTheoMa.setEnabled(true);
            rdTheoTen.setEnabled(true);
        } else {
            rdTatCa.setEnabled(false);
            rdTheoGia.setEnabled(false);
            rdTheoMa.setEnabled(false);
            rdTheoTen.setEnabled(false);
        }
        loadDataSanPhamBanHang(ser.TimKiemSanPhamTheoMaVaTenBanHang(timKiem));
        if (!ser.TimKiemSanPhamTheoMaVaTenBanHang(timKiem).isEmpty()) {
            listSanPham = ser.TimKiemSanPhamTheoMaVaTenBanHang(timKiem);
        } else {
            System.out.println("Roong");

        }

    }//GEN-LAST:event_txtTimKiemSanPhamKeyReleased

    private void cbbLocHoaDonBanHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbbLocHoaDonBanHangMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_cbbLocHoaDonBanHangMouseClicked

    private void cbbLocHoaDonBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbLocHoaDonBanHangActionPerformed
        // TODO add your handling code here:
        String keyWord = cbbLocHoaDonBanHang.getSelectedItem().toString();
        System.out.println(keyWord);
        if (keyWord.equals("Tất cả")) {
            loadDataHoaDonBanHang(ser.getAllHoaDon());
        }
        if (keyWord.equals("Chưa hoàn thành")) {
            loadDataHoaDonBanHang(ser.locHoaDonTheoTrangThaiBanHang(keyWord));
        }
        if (keyWord.equals("Đã hoàn thành")) {
            loadDataHoaDonBanHang(ser.locHoaDonTheoTrangThaiBanHang(keyWord));
        }
        if (keyWord.equals("Đã huỷ")) {
            loadDataHoaDonBanHang(ser.locHoaDonTheoTrangThaiBanHang(keyWord));
        }
    }//GEN-LAST:event_cbbLocHoaDonBanHangActionPerformed

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased
        // TODO add your handling code here:
        tienThuaThongBao = "";
        String tienKhachDuaString = txtTienKhachDua.getText();
        String tongTienStrinng = txtTongTien.getText();
        Integer tongTien = Integer.parseInt(tongTienStrinng);
        Integer tienKhachDua = 0;
        Integer tienThua = 0;
        if (checkSo(tienKhachDuaString)) {
            tienKhachDua = Integer.parseInt(tienKhachDuaString);
//            if (tienKhachDua <= 0) {
//                //JOptionPane.showMessageDialog(this, "Tiền không thể nhỏ hơn hoặc bằng 0");
//            } else if (tienKhachDua > 0 && tienKhachDua < tongTien) {
//                ///JOptionPane.showMessageDialog(this, "Khách thiếu: " + (tongTien - tienKhachDua));
//            } else if (tienKhachDua > tongTien) {
//                
//            }
            tienThua = tienKhachDua - tongTien;
            if (tienThua < 0) {
                txtTienThua.setText("Thiếu: " + (-tienThua));
            } else {
                txtTienThua.setText(tienThua + "");
                tienThuaThongBao = txtTienThua.getText();
                System.out.println(tienThuaThongBao);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Không thể nhập ký tự");
        }

    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        System.out.println(tienThuaThongBao);
        int y = JOptionPane.showConfirmDialog(this, "Thanh toán?");
        if (y == JOptionPane.YES_OPTION) {
            int rowHD = tblHoaDonBanHang.getSelectedRow();
            String rsCbb = cbbLocHoaDonBanHang.getSelectedItem().toString();
            listHoaDon.clear();
            if (rsCbb.equals("Tất cả")) {
                listHoaDon = ser.getAllHoaDon();
            } else if (rsCbb.equals("Chưa hoàn thành")) {
                listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Chưa hoàn thành");
            } else if (rsCbb.equals("Đã hoàn thành")) {
                listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Đã hoàn thành");
            } else {
                listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Đã huỷ");
            }
            listVoucer.clear();

            listVoucer = ser.showHoaDonTheoVoucher(Integer.valueOf(txtTongTien.getText()));
            if (rowHD >= 0) {
                if (ser.getAllHoaDonChiTiet(listHoaDon.get(rowHD).getMaHoaDon()).isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không có sản phẩm trong hoá đơn để thanh toán");
                } else {
                    if (txtTienKhachDua.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Mòi thêm tiền khách đưa");
                    } else {
                        if (txtTienThua.getText().startsWith("Thiếu")) {
                            JOptionPane.showMessageDialog(this, "Thiếu tiền không thể thanh toán");
                        } else {
                            int rowVC = tblDanhSachVoucher.getSelectedRow();
                            if (rowVC >= 0) {
                                String maVoucher = listVoucer.get(rowVC).getMaVoucher();
                                System.out.println("VC hoá đơn: " + maVoucher);
                                String trangThai = "Đã hoàn thành";
                                loadDataHoaDonBanHang(ser.thanhToanApVoucher(maVoucher, listHoaDon.get(rowHD).getMaHoaDon()));
                                JOptionPane.showMessageDialog(this, ser.thanhToanHoaDon(trangThai, getLocalDate() + "", listHoaDon.get(rowHD).getMaHoaDon()));
                                JOptionPane.showMessageDialog(this, "Đã hoàn thành hoá đơn và trả khách: " + tienThuaThongBao + "VND");
                                exportToPDF(listHoaDon.get(rowHD).getMaHoaDon(), ser.getAllHoaDonChiTietChuaHoanThanh(listHoaDon.get(rowHD).getMaHoaDon()));
                                JOptionPane.showMessageDialog(this, ser.updateSoLuongVoucherTru(maVoucher));
                                loadDataVoucher(ser.getAllVoucher());
                                loadDataHoaDonBanHang(ser.getAllHoaDon());
                            } else {
                                int yVC = JOptionPane.showConfirmDialog(this, "Bạn chưa chọn voucher, có muốn thêm không");
                                if (yVC == JOptionPane.NO_OPTION) {
                                    String maVoucher = null;
                                    String trangThai = "Đã hoàn thành";
                                    loadDataHoaDonBanHang(ser.thanhToanApVoucher(maVoucher, listHoaDon.get(rowHD).getMaHoaDon()));
                                    JOptionPane.showMessageDialog(this, ser.thanhToanHoaDon(trangThai, getLocalDate() + "", listHoaDon.get(rowHD).getMaHoaDon()));
                                    JOptionPane.showMessageDialog(this, "Đã hoàn thành hoá đơn và trả khách: " + tienThuaThongBao + "VND");
                                    exportToPDF(listHoaDon.get(rowHD).getMaHoaDon(), ser.getAllHoaDonChiTietChuaHoanThanh(listHoaDon.get(rowHD).getMaHoaDon()));
                                    loadDataHoaDonBanHang(ser.getAllHoaDon());
                                } else {
                                    JOptionPane.showMessageDialog(this, "Mời áp voucher");
                                }

                            }

                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Chưa chọn hoá đơn để thanh toán");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Đã huỷ");
        }

    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void tblHoaDonApDungVoucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonApDungVoucherMouseClicked
        // TODO add your handling code here:
//        int rowHD = tblHoaDonApDungVoucher.getSelectedRow();
//        int rowVC = tblDanhSachVoucher.getSelectedRow();
//        String maVoucher = ser.getAllVoucher().get(rowVC).getMaVoucher();
//        String maHoaDon = listHoaDonApVoucherPhu.get(rowHD).getMaHoaDon();
//        String trangThai = listHoaDonApVoucherPhu.get(rowHD).getTrangThai();
//        System.out.println("Hoa don voucher" + maHoaDon);
//        listHoaDonApVoucherPhu.clear();
//        if (rowHD >= 0 && rowVC >= 0) {
//
//            loadDataXacNhanVoucher(maHoaDon, maVoucher);
//        }
    }//GEN-LAST:event_tblHoaDonApDungVoucherMouseClicked

    private void tblDanhSachVoucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachVoucherMouseClicked
        // TODO add your handling code here:
        int rowHD = tblHoaDonBanHang.getSelectedRow();
        if (rowHD >= 0) {
            listVoucer.clear();
        } else {

        }
    }//GEN-LAST:event_tblDanhSachVoucherMouseClicked

    private void btnChonVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonVoucherActionPerformed
        // TODO add your handling code here:

        String rsCbb = cbbLocHoaDonBanHang.getSelectedItem().toString();
        listHoaDon.clear();
        listVoucer.clear();
        if (rsCbb.equals("Tất cả")) {
            listHoaDon = ser.getAllHoaDon();
        } else if (rsCbb.equals("Chưa hoàn thành")) {
            listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Chưa hoàn thành");
        } else if (rsCbb.equals("Đã hoàn thành")) {
            listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Đã hoàn thành");
        } else {
            listHoaDon = ser.locHoaDonTheoTrangThaiBanHang("Đã huỷ");
        }
        int rowHD = tblHoaDonBanHang.getSelectedRow();
        int rowVC = tblDanhSachVoucher.getSelectedRow();
        listVoucer = ser.showHoaDonTheoVoucher(Integer.valueOf(txtTongTien.getText()));
        if (rowHD >= 0 && rowVC >= 0) {
            String maVoucher = listVoucer.get(rowVC).getMaVoucher();
            String maHoaDon = listHoaDon.get(rowHD).getMaHoaDon();
            String trangThai = listHoaDon.get(rowHD).getTrangThai();
            System.out.println("Hoa don voucher: " + maHoaDon);
            System.out.println("Voucer: " + maVoucher);
            listHoaDonApVoucherPhu.clear();
            if (trangThai.equals("Chưa hoàn thành")) {
                loadDataXacNhanVoucher(maHoaDon, maVoucher);
            } else {
                JOptionPane.showMessageDialog(this, "Hoá đơn đã hoàn thành hoặc đã huỷ không thể áp dụng voucher");
                loadDataXacNhanVoucher("", "");
            }
        }
    }//GEN-LAST:event_btnChonVoucherActionPerformed

    private void btnHuyVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyVoucherActionPerformed
        // TODO add your handling code here:
        loadDataXacNhanVoucher("", "");
        pnNhanVien.setSelectedIndex(1);
    }//GEN-LAST:event_btnHuyVoucherActionPerformed

    private void btnXacNhanVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanVoucherActionPerformed
        // TODO add your handling code here:
        listVoucer.clear();
        int rowVC = tblDanhSachVoucher.getSelectedRow();
        listVoucer = ser.showHoaDonTheoVoucher(Integer.valueOf(txtTongTien.getText()));
        if (rowVC >= 0) {
            double tien = listVoucer.get(rowVC).getSoTienGiam();
            Integer tienGiam = (int) tien;
            lbPhanTramTru.setText("<html>Số tiền giảm:<br>" + tienGiam + "</html>");
            Integer tongTienthis = Integer.valueOf(txtTongTien.getText());
            Integer kq = tongTienthis - tienGiam;
            txtTongTien.setText(kq + "");
        }
        pnNhanVien.setSelectedIndex(1);
    }//GEN-LAST:event_btnXacNhanVoucherActionPerformed

    private void btnTimKiemVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemVoucherActionPerformed
        // TODO add your handling code here:
        String keyWord = txtTimKiemVoucher.getText().trim();
        listVoucer.clear();
        listVoucer = ser.timKiemVoucherBanHang(keyWord);
        if (listVoucer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có voucher cần tìm");
        } else {
            loadDataVoucher(listVoucer);
        }
    }//GEN-LAST:event_btnTimKiemVoucherActionPerformed

    private void btnVoucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVoucherMouseClicked
        // TODO add your handling code here:
        pnNhanVien.setSelectedIndex(2);
    }//GEN-LAST:event_btnVoucherMouseClicked

    private void jLabel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseClicked
        // TODO add your handling code here:
        pnNhanVien.setSelectedIndex(1);
    }//GEN-LAST:event_jLabel32MouseClicked

    private void btnHienThiVoucherBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHienThiVoucherBanHangActionPerformed
        // TODO add your handling code here:
        loadDataVoucher(ser.getAllVoucher());
    }//GEN-LAST:event_btnHienThiVoucherBanHangActionPerformed

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
            java.util.logging.Logger.getLogger(ViewTrangChu_NhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewTrangChu_NhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewTrangChu_NhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewTrangChu_NhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewTrangChu_NhanVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBotSanPham;
    private javax.swing.JButton btnChonVoucher;
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnHienThiVoucherBanHang;
    private javax.swing.JButton btnHuyHoaDon;
    private javax.swing.JButton btnHuyVoucher;
    private javax.swing.JButton btnNewBanHang;
    private javax.swing.JButton btnSuaHoaDon;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemKH;
    private javax.swing.JButton btnThemSanPham;
    private javax.swing.JButton btnTimKiemKhuyenMai;
    private javax.swing.JButton btnTimKiemVoucher;
    private javax.swing.JButton btnVoucher;
    private javax.swing.JButton btnXacNhanHuyHoaDonKM;
    private javax.swing.JButton btnXacNhanKhuyenMai;
    private javax.swing.JButton btnXacNhanVoucher;
    private javax.swing.JButton btnXoaKH;
    private javax.swing.JButton btnXoaSanPhamThem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbLocHoaDonBanHang;
    private javax.swing.JComboBox<String> cbbSapXepTheoNgayHetHanKhuyenMai;
    private javax.swing.JComboBox<String> cbbSapXepTheoNgayHetHanVoucher;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbPhanTramTru;
    private javax.swing.JLabel lblMaHoaDon;
    private javax.swing.JLabel lblMaHoaDonHoaDon;
    private javax.swing.JLabel lblMaNhanVienHoaDon;
    private javax.swing.JLabel lblNgayHoanThanh;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblNgayTaoHD;
    private javax.swing.JTabbedPane pnNhanVien;
    private javax.swing.JRadioButton rdChuyenKhoan;
    private javax.swing.JRadioButton rdTatCa;
    private javax.swing.JRadioButton rdTheoGia;
    private javax.swing.JRadioButton rdTheoMa;
    private javax.swing.JRadioButton rdTheoTen;
    private javax.swing.JRadioButton rdTienMat;
    private javax.swing.JTable tblDanhSachKhuyenMai;
    private javax.swing.JTable tblDanhSachSanPhamApDungKM;
    private javax.swing.JTable tblDanhSachVoucher;
    private javax.swing.JTable tblHoaDonApDungKhuyenMai;
    private javax.swing.JTable tblHoaDonApDungVoucher;
    private javax.swing.JTable tblHoaDonBanHang;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblSanPhamBanHang;
    private javax.swing.JTable tblThanhToanBanHang;
    private javax.swing.JTable tblXacNhanVoucher;
    private javax.swing.JButton tbnSuaKH;
    private javax.swing.JTextField txtDCKH;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaKhachHangBanHang;
    private javax.swing.JTextField txtMaKhachHangBanHangHDCT;
    private javax.swing.JTextField txtNgayBatDauKhuyenMai;
    private javax.swing.JTextField txtNgayBatDauVoucher;
    private javax.swing.JTextField txtNgayKetThucKhuyenMai;
    private javax.swing.JTextField txtNgayKetThucVoucher;
    private javax.swing.JTextField txtSDTKH;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTienThua;
    private javax.swing.JTextField txtTimKiemKhuyenMai;
    private javax.swing.JTextField txtTimKiemSanPham;
    private javax.swing.JTextField txtTimKiemVoucher;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
