/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.*;
import java.util.ArrayList;
import java.sql.*;
import DbConnect.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author NGHIAPC
 */
public class ServiceImp implements ServiceInterface {

    ArrayList<Voucher> listVoucher = new ArrayList<>();
    ArrayList<KhuyenMai> listKhuyenMai = new ArrayList<>();
    ArrayList<NguoiDung> listNguoiDung = new ArrayList<>();
    ArrayList<SanPham> listSanPham = new ArrayList<>();
    ArrayList<KhachHang> listKhachHang = new ArrayList<>();
    public ArrayList<Login> listLogin = new ArrayList<>();
    ArrayList<HoaDon> listHoaDon = new ArrayList<>();
    ArrayList<HoaDonChiTiet> listHoaDonChiTiet = new ArrayList<>();
    ArrayList<LichSuGia> listLichSuGia = new ArrayList<>();
    ArrayList<NguoiDung> listQuanLy = new ArrayList<>();
    ArrayList<Login> listLoginTam = new ArrayList<>();
    ArrayList<ChiTietHoaDon> listCTHD = new ArrayList<>();
    ArrayList<NguoiDung> listEMail = new ArrayList<>();
    ArrayList<SanPham> listHinhAnh = new ArrayList<>();

    public ArrayList<KhachHang> getAllKhachHang() {
        String sql = "select * from KhachHang";
        listKhachHang.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString(1));
                kh.setTenKhachHang(rs.getString(2));
                kh.setSDT(rs.getString(3));
                kh.setDiaChi(rs.getString(4));
                listKhachHang.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listKhachHang;
    }

    public void addKhachHang(KhachHang kh) {
        String sql = ("insert into KhachHang (MaKhachHang, TenKhachHang, SDT, DiaChi) values (?,?,?,?)");
        try {

            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, kh.getMaKhachHang());
            stm.setString(2, kh.getTenKhachHang());
            stm.setString(3, kh.getSDT());
            stm.setString(4, kh.getDiaChi());

            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateKhachHang(KhachHang kh) {
        String sql = "update KhachHang set TenKhachHang = ?, SDT = ?, DiaChi = ? where MaKhachHang = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, kh.getTenKhachHang());
            stm.setString(2, kh.getSDT());
            stm.setString(3, kh.getDiaChi());
            stm.setString(4, kh.getMaKhachHang());

            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteKhachHang(String MaKhachHang) {
        String sql = "DELETE FROM KhachHang WHERE MaKhachHang=?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, MaKhachHang);

            stm.executeUpdate();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public KhachHang getRowKhachHang(int row) {
        return listKhachHang.get(row);
    }

    public ArrayList<ChiTietHoaDon> getALlCTHD() {
        listCTHD.clear();
        String sql = "select hd.MaHoaDon,NgayTao,TrangThai,MaVoucher,MaNhanVien,NgayHoanThanh,LoaiThanhToan, MaSanPhamChiTiet,SoLuong\n"
                + "from HoaDon hd  join ChiTietHoaDon cthd on hd.MaHoaDon = cthd.MaHoaDon\n"
                + "Group By hd.MaHoaDon,NgayTao,TrangThai,MaVoucher,MaNhanVien,NgayHoanThanh,LoaiThanhToan,MaSanPhamChiTiet,SoLuong ";

        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                ChiTietHoaDon cthd = new ChiTietHoaDon();
                cthd.setMaHoaDon(rs.getString(1));
                cthd.setNgayTao(rs.getString(2));
                cthd.setTrangThai(rs.getString(3));
                cthd.setMaVoucher(rs.getString(4));
                cthd.setMaNhanVien(rs.getString(5));
                cthd.setNgayHoanThanh(rs.getString(6));
                cthd.setLoaiThanhToan(rs.getString(7));
                cthd.setMaSanPhamChiTiet(rs.getString(8));
                cthd.setSoLuong(rs.getInt(9));
                listCTHD.add(cthd);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCTHD;
    }

    public Integer tongHoaDonThanhToan() {
        String sql = "SELECT COUNT(*) AS 'TONG_DON_HANG_THANH_CONG' FROM HoaDon WHERE TrangThai =N'Đã hoàn thành'";
        Integer tongHoaDonThanhToan = 0;
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                tongHoaDonThanhToan = rs.getInt(1);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongHoaDonThanhToan;
    }

    public Integer tongHoaDonMD() {
        String sql = "SELECT COUNT(*) AS TONG_DON_HANG FROM HoaDon";
        Integer tongHoaDon = 0;
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                tongHoaDon = rs.getInt(1);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongHoaDon;
    }

    public Double tongDoanhThuMD() {
        String sql = "	SELECT sum(GiaDau * ctsp.SoLuong) FROM HoaDon a \n"
                + "	join  ChiTietHoaDon b on a.MaHoaDon = b.MaHoaDon \n"
                + "	join ChiTietSanPham ctsp on ctsp.MaSanPhamChiTiet = b.MaSanPhamChiTiet\n"
                + "	join LichSuDonGia lsdg on lsdg.MaSanPhamChiTiet = ctsp.MaSanPhamChiTiet WHERE a.TrangThai = N'Đã hoàn thành' ";
        Double tongDoanhThuMD = 0.0;
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                tongDoanhThuMD = rs.getDouble(1);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongDoanhThuMD;
    }

    public Integer tongDoanhSoMD() {
        String sql = "SELECT sum(b.SoLuong) as 'Tong_Doanh_So_Ban' FROM HoaDon a join  ChiTietHoaDon b on a.MaHoaDon = b.MaHoaDon WHERE a.TrangThai = N'Đã hoàn thành'  ";
        Integer tongDoanhSo = 0;
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                tongDoanhSo = rs.getInt("Tong_Doanh_So_Ban");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongDoanhSo;
    }

    public Integer tonghoadon(String ngayBatDau, String ngayKetThuc) {
        String sql = "SELECT COUNT(*) AS TONG_DON_HANG FROM HoaDon WHERE NgayTao BETWEEN ? AND ?";
        Integer tongHoaDon = 0;
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, ngayBatDau);
            stm.setString(2, ngayKetThuc);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                tongHoaDon = rs.getInt(1);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongHoaDon;
    }

    public Double tongDoanhThu(String ngayBatDau, String ngayKetThuc) {
        String sql = "SELECT SUM(lsdg.GiaDau) AS Tong_Doanh_Thu\n"
                + "FROM HoaDon a\n"
                + "JOIN ChiTietHoaDon b ON a.MaHoaDon = b.MaHoaDon\n"
                + "JOIN ChiTietSanPham ctsp ON ctsp.MaSanPhamChiTiet = b.MaSanPhamChiTiet\n"
                + "JOIN LichSuDonGia lsdg ON lsdg.MaSanPhamChiTiet = ctsp.MaSanPhamChiTiet\n"
                + "WHERE a.TrangThai = N'Đã hoàn thành' AND a.NgayTao >= ? AND a.NgayTao <= ?;";
        Double tongDoanhThu = 0.0;
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, ngayBatDau);
            stm.setString(2, ngayKetThuc);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                tongDoanhThu = rs.getDouble("Tong_Doanh_Thu");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tongDoanhThu;
    }

    public Integer tongDoanhSo(String ngayBatDau, String ngayKetThuc) {
        String sql = "SELECT SUM(b.SoLuong) AS TONG_SO_LUONG FROM HoaDon a JOIN ChiTietHoaDon b ON a.MaHoaDon = b.MaHoaDon WHERE a.TrangThai = N'Đã hoàn thành' AND a.NgayHoanThanh BETWEEN ? AND ?";
        Integer tongDoanhSo = 0;
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, ngayBatDau);
            stm.setString(2, ngayKetThuc);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                tongDoanhSo = rs.getInt(1);

            }
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tongDoanhSo;
    }

    @Override
    public ArrayList<Voucher> getAllVoucher() {
        String sql = "select * from Voucher";
        listVoucher.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Voucher vc = new Voucher();
                vc.setMaVoucher(rs.getString(1));
                vc.setTenVoucher(rs.getString(2));
                vc.setSoLuongVC(rs.getInt(3));
                vc.setHanSuDungVC(rs.getString(4));
                vc.setNgayBatDauVC(rs.getString(5));
                vc.setSoTienGiam(rs.getDouble(6));
                vc.setSoTienYeuCau(rs.getDouble(7));
                listVoucher.add(vc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVoucher;

    }

    public ArrayList<Login> LoginSearch(String user, String pass) {
        String sql = "select TenDangNhap, MatKhau, Roles.TenRole, Email from NguoiDung \n"
                + "join Roles on NguoiDung.Roles = Roles.Marole\n"
                + "where NguoiDung.TenDangNhap = ? and MatKhau = ?";
        listLogin.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, user);
            stm.setString(2, pass);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Login lg = new Login();
                lg.setUserName(rs.getString(1));
                lg.setPassword(rs.getString(2));
                lg.setEmail(rs.getString(4));
                lg.setRole(rs.getString(3));
                listLogin.add(lg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLogin;
    }

    public ArrayList<Login> FogotPassword(String user, String email) {
        String sql = "select TenDangNhap, MatKhau, Roles.TenRole, Email from NguoiDung \n"
                + "join Roles on NguoiDung.Roles = Roles.Marole\n"
                + "where NguoiDung.TenDangNhap = ? and Email = ?";
        listLogin.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, user);
            stm.setString(2, email);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Login lg = new Login();
                lg.setUserName(rs.getString(1));
                lg.setPassword(rs.getString(2));
                lg.setEmail(rs.getString(4));
                lg.setRole(rs.getString(3));
                listLogin.add(lg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLogin;
    }

    @Override
    public ArrayList<KhuyenMai> getAllKhuyenMai() {
        listKhuyenMai.clear();
        String sql = "select KhuyenMai.MaKhuyenMai, TenKhuyenMai, NgayBatDau, HanSuDung, PTKhuyenMai, ChiTietKhuyenMai.MaSanPhamChiTiet from KhuyenMai\n"
                + "left join ChiTietKhuyenMai on ChiTietKhuyenMai.MaKhuyenMai = KhuyenMai.MaKhuyenMai";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKM(rs.getString(1));
                km.setTenKM(rs.getString(2));
                km.setNgayBatDauKM(rs.getString(3));
                km.setHanSuDungKM(rs.getString(4));
                km.setGiamGia(rs.getDouble(5));
                km.setMaCTSP(rs.getString(6));
                listKhuyenMai.add(km);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listKhuyenMai;
    }

    @Override
    public ArrayList<NguoiDung> getAllNhanVien(boolean trangThai) {
        String sql = "select * from NguoiDung where Roles = 'NV' and TrangThai = ?";
        listNguoiDung.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setBoolean(1, trangThai);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                NguoiDung nd = new NguoiDung();
                nd.setMaNguoiDung(rs.getString(1));
                nd.setTenNguoiDung(rs.getString(2));
                nd.setGioiTinh(rs.getBoolean(3));
                nd.setTuoi(rs.getInt(4));
                nd.setSDT(rs.getString(5));
                nd.setEmail(rs.getString(6));
                nd.setRoles(rs.getString(7));
                nd.setTenDN(rs.getString(8));
                nd.setPassWord(rs.getString(9));
                nd.setTrangThai(rs.getBoolean(10));
                listNguoiDung.add(nd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNguoiDung;
    }

    public ArrayList<SanPham> getAllSanPham() {
        listSanPham.clear();
        String sql = "SELECT distinct\n"
                + "                   c.MaSanPham,\n"
                + "                   TenSanPham,\n"
                + "                    TenNCC, \n"
                + "                    case when l.ThoiGianBatDau <= CURRENT_TIMESTAMP AND l.ThoiGianKetThuc >= CURRENT_TIMESTAMP THEN l.GiaSau\n"
                + "					else l.GiaDau \n"
                + "					end as Gia,\n"
                + "                   c.SoLuong, \n"
                + "                   MauSac, \n"
                + "                    KichThuoc, \n"
                + "                   Mau, \n"
                + "                    TenChatLieu,\n"
                + "                \n"
                + "                   STRING_AGG(HinhAnh, ',') AS HinhAnh,\n"
                + "                Hang\n"
                + "                FROM \n"
                + "                  ChiTietSanPham c\n"
                + "                JOIN \n"
                + "                   SanPham s ON c.MaSanPham = s.MaSanPham\n"
                + "                JOIN \n"
                + "                   NhaCungCap n ON n.MaNCC = c.NCC\n"
                + "                JOIN \n"
                + "                   LichSuDonGia l ON l.MaSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "                JOIN \n"
                + "                   MauSac m ON m.MaMauSac = c.MaMauSac\n"
                + "                JOIN \n"
                + "                   KichThuoc k ON k.MaKichThuoc = c.MaKichThuoc\n"
                + "                JOIN \n"
                + "                   ChatLieu cl ON cl.MaChatLieu = c.ChatLieu\n"
                + "                left JOIN\n"
                + "                HinhAnh ha ON ha.MaSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "                join \n"
                + "                chiTietKhuyenMai ctkm on ctkm.maSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "where TrangThai = 1"
                + "                group by  c.MaSanPham, \n"
                + "                  TenSanPham,\n"
                + "                    TenNCC,\n"
                + "                    GiaDau,\n"
                + "                   c.SoLuong, \n"
                + "                   MauSac, \n"
                + "                   KichThuoc, \n"
                + "                    Mau, \n"
                + "                   TenChatLieu,\n"
                + "                Hang,\n"
                + "				l.ThoiGianBatDau,\n"
                + "				l.ThoiGianKetThuc,\n"
                + "				l.GiaSau\n";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString(1));
                sp.setTenSP(rs.getString(2));
                sp.setNhaCungCap(rs.getString(3));
                sp.setDonGia(rs.getDouble(4));
                sp.setSoLuongSP(rs.getInt(5));
                sp.setMauSac(rs.getString(6));
                sp.setKichThuoc(rs.getString(7));
                sp.setMau(rs.getString(8));
                sp.setChatLieu(rs.getString(9));
                sp.setHinhAnh(rs.getString(10));
                sp.setHang(rs.getString(11));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public NguoiDung getRowNhanVien(boolean trangThai, int row) {
        return getAllNhanVien(trangThai).get(row);
    }

    @Override
    public ArrayList<NguoiDung> searchNhanVien(String ma, String tenNV) {
        String sql = "select * from NguoiDung where Roles = 'NV' and MaNguoiDung like ? or TenNguoiDung like ? and TrangThai = 1";
        listNguoiDung.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + ma + "%");
            stm.setString(2, "%" + tenNV + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                NguoiDung nd = new NguoiDung();
                nd.setMaNguoiDung(rs.getString(1));
                nd.setTenNguoiDung(rs.getString(2));
                nd.setGioiTinh(rs.getBoolean(3));
                nd.setTuoi(rs.getInt(4));
                nd.setSDT(rs.getString(5));
                nd.setEmail(rs.getString(6));
                nd.setRoles(rs.getString(7));
                nd.setTenDN(rs.getString(8));
                nd.setPassWord(rs.getString(9));
                nd.setTrangThai(rs.getBoolean(10));
                listNguoiDung.add(nd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNguoiDung;
    }

    @Override
    public ArrayList<NguoiDung> sapXepTheoMaNhVien() {
        String sql = "select * from NguoiDung where Roles = 'NV' and TrangThai = 1 order by MaNguoiDung desc ";
        listNguoiDung.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                NguoiDung nd = new NguoiDung();
                nd.setMaNguoiDung(rs.getString(1));
                nd.setTenNguoiDung(rs.getString(2));
                nd.setGioiTinh(rs.getBoolean(3));
                nd.setTuoi(rs.getInt(4));
                nd.setSDT(rs.getString(5));
                nd.setEmail(rs.getString(6));
                nd.setRoles(rs.getString(7));
                nd.setTenDN(rs.getString(8));
                nd.setPassWord(rs.getString(9));
                nd.setTrangThai(rs.getBoolean(10));
                listNguoiDung.add(nd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listNguoiDung;
    }

    @Override
    public ArrayList<Voucher> searchVoucher(String maVC) {
        listVoucher.clear();
        String sql = "select * from Voucher where MaVoucher like ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + maVC + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Voucher vc = new Voucher();
                vc.setMaVoucher(rs.getString(1));
                vc.setTenVoucher(rs.getString(2));
                vc.setSoLuongVC(rs.getInt(3));
                vc.setHanSuDungVC(rs.getString(4));
                vc.setNgayBatDauVC(rs.getString(5));
                vc.setSoTienGiam(rs.getDouble(6));
                vc.setSoTienYeuCau(rs.getDouble(7));
                listVoucher.add(vc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVoucher;

    }

    public ArrayList<NguoiDung> sapXepTheoTenNhVien() {
        String sql = "select * from NguoiDung where Roles = 'NV' and TrangThai = 1 order by RIGHT(TenNguoiDung, CHARINDEX(' ', REVERSE(TenNguoiDung)) - 1)";
        listNguoiDung.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                NguoiDung nd = new NguoiDung();
                nd.setMaNguoiDung(rs.getString(1));
                nd.setTenNguoiDung(rs.getString(2));
                nd.setGioiTinh(rs.getBoolean(3));
                nd.setTuoi(rs.getInt(4));
                nd.setSDT(rs.getString(5));
                nd.setEmail(rs.getString(6));
                nd.setRoles(rs.getString(7));
                nd.setTenDN(rs.getString(8));
                nd.setPassWord(rs.getString(9));
                nd.setTrangThai(rs.getBoolean(10));
                listNguoiDung.add(nd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNguoiDung;
    }

    @Override
    public Boolean addNhanVien(NguoiDung nd) {
        String sql = "insert into NguoiDung values(?,?,?,?,?,?,?,?,?,?)";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, nd.getMaNguoiDung());
            stm.setString(2, nd.getTenNguoiDung());
            stm.setBoolean(3, nd.isGioiTinh());
            stm.setInt(4, nd.getTuoi());
            stm.setString(5, nd.getSDT());
            stm.setString(6, nd.getEmail());
            stm.setString(7, nd.getRoles());
            stm.setString(8, nd.getTenDN());
            stm.setString(9, nd.getPassWord());
            stm.setBoolean(10, true);
            stm.executeUpdate();
            conn.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public ArrayList<NguoiDung> getAllNguoiDung() {
        String sql = "select * from NguoiDung";
        listNguoiDung.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                NguoiDung nd = new NguoiDung();
                nd.setMaNguoiDung(rs.getString(1));
                nd.setTenNguoiDung(rs.getString(2));
                nd.setGioiTinh(rs.getBoolean(3));
                nd.setTuoi(rs.getInt(4));
                nd.setSDT(rs.getString(5));
                nd.setEmail(rs.getString(6));
                nd.setRoles(rs.getString(7));
                nd.setTenDN(rs.getString(8));
                nd.setPassWord(rs.getString(9));
                listNguoiDung.add(nd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNguoiDung;

    }

    public ArrayList<HoaDon> getAllHoaDon() {
        String sql = "select * from HoaDon";
        listHoaDon.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getString(1));
                hd.setMaNhanVien(rs.getString(5));
                hd.setNgayTao(rs.getString(2));
                hd.setLoaiThanhToan(rs.getString(7));
                hd.setMaKhachHang(rs.getString(8));
                hd.setMaVoucher(rs.getString(4));
                hd.setNgayHoanThanh(rs.getString(6));
                hd.setTrangThai(rs.getString(3));
                listHoaDon.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDon;
    }

    public ArrayList<HoaDonChiTiet> getAllHoaDonChiTiet(String maHoaDon) {
        String sql = "SELECT distinct cthd.MaHoaDon, cthd.MaSanPhamChiTiet, sp.TenSanPham, cthd.SoLuong, ls.GiaDau,\n"
                + "       CASE\n"
                + "           WHEN (NgayHoanThanh BETWEEN ThoiGianBatDau AND ThoiGianKetThuc) OR\n"
                + "                (NgayHoanThanh BETWEEN km.NgayBatDau AND km.HanSuDung) THEN ls.GiaSau - (ls.GiaSau * km.PTKhuyenMai / 100)\n"
                + "           ELSE ls.GiaDau\n"
                + "       END AS Gia\n"
                + "FROM ChiTietSanPham ct\n"
                + "JOIN LichSuDonGia ls ON ls.MaSanPhamChiTiet = ct.MaSanPhamChiTiet\n"
                + "JOIN SanPham sp ON sp.MaSanPham = ct.MaSanPham\n"
                + "JOIN ChiTietHoaDon cthd ON cthd.MaSanPhamChiTiet = ct.MaSanPhamChiTiet\n"
                + "JOIN HoaDon hd ON hd.MaHoaDon = cthd.MaHoaDon \n"
                + "JOIN (\n"
                + "    SELECT MaSanPhamChiTiet, MAX(kmm.PTKhuyenMai) AS MaxPTKhuyenMai\n"
                + "    FROM ChiTietKhuyenMai\n"
                + "	join KhuyenMai kmm on kmm.MaKhuyenMai = ChiTietKhuyenMai.MaKhuyenMai\n"
                + "    GROUP BY MaSanPhamChiTiet\n"
                + ") max_km ON ct.MaSanPhamChiTiet = max_km.MaSanPhamChiTiet\n"
                + "JOIN KhuyenMai km ON km.PTKhuyenMai = max_km.MaxPTKhuyenMai\n"
                + "WHERE cthd.MaHoaDon = ?;";
        listHoaDonChiTiet.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, maHoaDon);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setMaHoaDon(rs.getString(1));
                hdct.setMaSanPham(rs.getString(2));
                hdct.setTenSanPham(rs.getString(3));
                hdct.setSoLuong(rs.getInt(4));
                hdct.setDonGia(rs.getDouble(5));
                hdct.setDonGiaSau(rs.getDouble(6));
                listHoaDonChiTiet.add(hdct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDonChiTiet;
    }

    @Override
    public ArrayList<Voucher> sXMaVoucher() {
        listVoucher.clear();
        String sql = "select * from Voucher\n"
                + "order by MaVoucher desc";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Voucher vc = new Voucher();
                vc.setMaVoucher(rs.getString(1));
                vc.setTenVoucher(rs.getString(2));
                vc.setSoLuongVC(rs.getInt(3));
                vc.setHanSuDungVC(rs.getString(4));
                vc.setNgayBatDauVC(rs.getString(5));
                vc.setSoTienGiam(rs.getDouble(6));
                vc.setSoTienYeuCau(rs.getDouble(7));
                listVoucher.add(vc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVoucher;
    }

    public ArrayList<LichSuGia> getAllLichSuGia() {
        String sql = "select MaSanPham, MaDonGia, GiaDau, GiaSau, ThoiGianBatDau, ThoiGianKetThuc from LichSuDonGia\n"
                + "join ChiTietSanPham on ChiTietSanPham.DonGia = LichSuDonGia.MaDonGia\n";
        listLichSuGia.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                LichSuGia lsg = new LichSuGia();
                lsg.setMaSanPham(rs.getString(1));
                lsg.setMaDonGia(rs.getString(2));
                lsg.setGiaDau(rs.getDouble(3));
                lsg.setGiaSau(rs.getDouble(4));
                lsg.setNgayBatDau(rs.getString(5));
                lsg.setNgayKetThuc(rs.getString(6));
                listLichSuGia.add(lsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLichSuGia;
    }

    @Override
    public ArrayList<Voucher> sXTTTenVoucher() {
        listVoucher.clear();
        String sql = "select * from Voucher\n"
                + "order by TenVoucher desc";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Voucher vc = new Voucher();
                vc.setMaVoucher(rs.getString(1));
                vc.setTenVoucher(rs.getString(2));
                vc.setSoLuongVC(rs.getInt(3));
                vc.setHanSuDungVC(rs.getString(4));
                vc.setNgayBatDauVC(rs.getString(5));
                vc.setSoTienGiam(rs.getDouble(6));
                vc.setSoTienYeuCau(rs.getDouble(7));
                listVoucher.add(vc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVoucher;

    }

    public ArrayList<HoaDonChiTiet> updateSoluongSanPhamBanHang(String maSanPham, Integer soLuong, String maHoaDon) {
        String sql = "update ChiTietHoaDon set SoLuong = SoLuong + ? where MaHoaDon = ? and MaSanPhamChiTiet = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, soLuong);
            stm.setString(2, maHoaDon);
            stm.setString(3, maSanPham);
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDonChiTiet;
    }

    public HoaDon getRowHoaDon(int row) {
        return listHoaDon.get(row);
    }

    public SanPham getRowSanPham(int row) {
        return listSanPham.get(row);
    }

    public String getMaSanPhamChiTietFromSanPham(String maSanPham) {
        String sql = "select MaSanPhamChiTiet from ChiTietSanPham join SanPham on SanPham.MaSanPham = ChiTietSanPham.MaSanPham where SanPham.MaSanPham = ?";
        String kq = "";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, maSanPham);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                kq = rs.getString("MaSanPhamChiTiet");
            }
            System.out.println(kq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    public void updateNV(NguoiDung nd) {
        String sql = "update NguoiDung set TenNguoiDung = ?, GioiTinh = ?, Tuoi = ?, SDT = ?, Email = ?, TenDangNhap = ?, MatKhau = ? where MaNguoiDung = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, nd.getTenNguoiDung());
            stm.setBoolean(2, nd.isGioiTinh());
            stm.setInt(3, nd.getTuoi());
            stm.setString(4, nd.getSDT());
            stm.setString(5, nd.getEmail());
            stm.setString(6, nd.getTenDN());
            stm.setString(7, nd.getPassWord());
            stm.setString(8, nd.getMaNguoiDung());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SanPham> updateSanPhamTruBanHang(String maSanPham, Integer soLuong) {
        String sql = "update ChiTietSanPham set SoLuong = SoLuong - ? where MaSanPhamChiTiet = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, soLuong);
            stm.setString(2, maSanPham);
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public ArrayList<SanPham> updateSanPhamCongBanHang(String maSanPham, Integer soLuong) {
        String sql = "update ChiTietSanPham set SoLuong = SoLuong + ? where MaSanPhamChiTiet = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, soLuong);
            stm.setString(2, maSanPham);
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public ArrayList<HoaDonChiTiet> updateSoluongSanPhamBanHangTru(String maSanPham, Integer soLuong, String maHoaDon) {
        String sql = "update ChiTietHoaDon set SoLuong = SoLuong - ? where MaHoaDon = ? and MaSanPhamChiTiet = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, soLuong);
            stm.setString(2, maHoaDon);
            stm.setString(3, maSanPham);
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDonChiTiet;
    }

    @Override
    public ArrayList<HoaDonChiTiet> getAllHoaDonChiTiet() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<NguoiDung> getAllQuanLy() {
        String sql = "select MaNguoiDung, Email, Roles, TenDangNhap from NguoiDung where Roles = 'QL'";
        listQuanLy.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                NguoiDung nd = new NguoiDung();
                nd.setMaNguoiDung(rs.getString(1));
                nd.setEmail(rs.getString(2));
                nd.setRoles(rs.getString(3));
                nd.setTenDN(rs.getString(4));
                listQuanLy.add(nd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listQuanLy;
    }

    public ArrayList<HoaDonChiTiet> addHoaDonChiTiet(HoaDonChiTiet hdct) {
        String sql = "insert into ChiTietHoaDon(MaHoaDon, MaSanPhamChiTiet, SoLuong) values(?,?,?)";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, hdct.getMaHoaDon());
            stm.setString(2, hdct.getMaSanPham());
            stm.setInt(3, hdct.getSoLuong());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDonChiTiet;
    }

    public ArrayList<HoaDonChiTiet> deleteHoaDonChiTiet(String maSanPhamChiTiet, String maHoaDon) {
        String sql = "delete ChiTietHoaDon where MaHoaDon = ? and MaSanPhamChiTiet = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, maHoaDon);
            stm.setString(2, maSanPhamChiTiet);
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDonChiTiet;
    }

    public String getMaSanPhamFromChiTietSanPham(String maSanPhamChiTiet) {
        String maSanPham = "";
        String sql = "select MaSanPham from ChiTietSanPham \n"
                + "where MaSanPhamChiTiet = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, maSanPhamChiTiet);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                maSanPham = rs.getString(1);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maSanPham;
    }

    public ArrayList<SanPham> sapXepSanPhamTheoTenBanHang() {
        listSanPham.clear();
        String sql = "	SELECT distinct\n"
                + "                   c.MaSanPham,\n"
                + "                   TenSanPham,\n"
                + "                    TenNCC, \n"
                + "                    case when l.ThoiGianBatDau <= CURRENT_TIMESTAMP AND l.ThoiGianKetThuc >= CURRENT_TIMESTAMP THEN l.GiaSau\n"
                + "					else l.GiaDau \n"
                + "					end as Gia,\n"
                + "                   c.SoLuong, \n"
                + "                   MauSac, \n"
                + "                    KichThuoc, \n"
                + "                   Mau, \n"
                + "                    TenChatLieu,\n"
                + "                \n"
                + "                   STRING_AGG(HinhAnh, ',') AS HinhAnh,\n"
                + "                Hang\n"
                + "                FROM \n"
                + "                  ChiTietSanPham c\n"
                + "                JOIN \n"
                + "                   SanPham s ON c.MaSanPham = s.MaSanPham\n"
                + "                JOIN \n"
                + "                   NhaCungCap n ON n.MaNCC = c.NCC\n"
                + "                JOIN \n"
                + "                   LichSuDonGia l ON l.MaSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "                JOIN \n"
                + "                   MauSac m ON m.MaMauSac = c.MaMauSac\n"
                + "                JOIN \n"
                + "                   KichThuoc k ON k.MaKichThuoc = c.MaKichThuoc\n"
                + "                JOIN \n"
                + "                   ChatLieu cl ON cl.MaChatLieu = c.ChatLieu\n"
                + "                left JOIN\n"
                + "                HinhAnh ha ON ha.MaSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "                join \n"
                + "                chiTietKhuyenMai ctkm on ctkm.maSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "where trangThai = 1"
                + "                group by  c.MaSanPham, \n"
                + "                  TenSanPham,\n"
                + "                    TenNCC,\n"
                + "                    GiaDau,\n"
                + "                   c.SoLuong, \n"
                + "                   MauSac, \n"
                + "                   KichThuoc, \n"
                + "                    Mau, \n"
                + "                   TenChatLieu,\n"
                + "                Hang,\n"
                + "				l.ThoiGianBatDau,\n"
                + "				l.ThoiGianKetThuc,\n"
                + "				l.GiaSau\n"
                + "	order by TenSanPham";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString(1));
                sp.setTenSP(rs.getString(2));
                sp.setNhaCungCap(rs.getString(3));
                sp.setDonGia(rs.getDouble(4));
                sp.setSoLuongSP(rs.getInt(5));
                sp.setMauSac(rs.getString(6));
                sp.setKichThuoc(rs.getString(7));
                sp.setMau(rs.getString(8));
                sp.setChatLieu(rs.getString(9));
                sp.setHinhAnh(rs.getString(10));
                sp.setHang(rs.getString(11));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public ArrayList<SanPham> sapXepSanPhamTheoMaBanHang() {
        listSanPham.clear();
        String sql = "	SELECT distinct\n"
                + "                   c.MaSanPham,\n"
                + "                   TenSanPham,\n"
                + "                    TenNCC, \n"
                + "                    case when l.ThoiGianBatDau <= CURRENT_TIMESTAMP AND l.ThoiGianKetThuc >= CURRENT_TIMESTAMP THEN l.GiaSau\n"
                + "					else l.GiaDau \n"
                + "					end as Gia,\n"
                + "                   c.SoLuong, \n"
                + "                   MauSac, \n"
                + "                    KichThuoc, \n"
                + "                   Mau, \n"
                + "                    TenChatLieu,\n"
                + "                \n"
                + "                   STRING_AGG(HinhAnh, ',') AS HinhAnh,\n"
                + "                Hang\n"
                + "                FROM \n"
                + "                  ChiTietSanPham c\n"
                + "                JOIN \n"
                + "                   SanPham s ON c.MaSanPham = s.MaSanPham\n"
                + "                JOIN \n"
                + "                   NhaCungCap n ON n.MaNCC = c.NCC\n"
                + "                JOIN \n"
                + "                   LichSuDonGia l ON l.MaSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "                JOIN \n"
                + "                   MauSac m ON m.MaMauSac = c.MaMauSac\n"
                + "                JOIN \n"
                + "                   KichThuoc k ON k.MaKichThuoc = c.MaKichThuoc\n"
                + "                JOIN \n"
                + "                   ChatLieu cl ON cl.MaChatLieu = c.ChatLieu\n"
                + "                left JOIN\n"
                + "                HinhAnh ha ON ha.MaSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "                join \n"
                + "                chiTietKhuyenMai ctkm on ctkm.maSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "where trangThai = 1"
                + "                group by  c.MaSanPham, \n"
                + "                  TenSanPham,\n"
                + "                    TenNCC,\n"
                + "                    GiaDau,\n"
                + "                   c.SoLuong, \n"
                + "                   MauSac, \n"
                + "                   KichThuoc, \n"
                + "                    Mau, \n"
                + "                   TenChatLieu,\n"
                + "                Hang,\n"
                + "				l.ThoiGianBatDau,\n"
                + "				l.ThoiGianKetThuc,\n"
                + "				l.GiaSau\n"
                + "			order by c.MaSanPham";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString(1));
                sp.setTenSP(rs.getString(2));
                sp.setNhaCungCap(rs.getString(3));
                sp.setDonGia(rs.getDouble(4));
                sp.setSoLuongSP(rs.getInt(5));
                sp.setMauSac(rs.getString(6));
                sp.setKichThuoc(rs.getString(7));
                sp.setMau(rs.getString(8));
                sp.setChatLieu(rs.getString(9));
                sp.setHinhAnh(rs.getString(10));
                sp.setHang(rs.getString(11));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public ArrayList<SanPham> sapXepSanPhamTheoGiaBanHang() {
        listSanPham.clear();
        String sql = "	SELECT distinct\n"
                + "                   c.MaSanPham,\n"
                + "                   TenSanPham,\n"
                + "                    TenNCC, \n"
                + "                    case when l.ThoiGianBatDau <= CURRENT_TIMESTAMP AND l.ThoiGianKetThuc >= CURRENT_TIMESTAMP THEN l.GiaSau\n"
                + "					else l.GiaDau \n"
                + "					end as Gia,\n"
                + "                   c.SoLuong, \n"
                + "                   MauSac, \n"
                + "                    KichThuoc, \n"
                + "                   Mau, \n"
                + "                    TenChatLieu,\n"
                + "                \n"
                + "                   STRING_AGG(HinhAnh, ',') AS HinhAnh,\n"
                + "                Hang\n"
                + "                FROM \n"
                + "                  ChiTietSanPham c\n"
                + "                JOIN \n"
                + "                   SanPham s ON c.MaSanPham = s.MaSanPham\n"
                + "                JOIN \n"
                + "                   NhaCungCap n ON n.MaNCC = c.NCC\n"
                + "                JOIN \n"
                + "                   LichSuDonGia l ON l.MaSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "                JOIN \n"
                + "                   MauSac m ON m.MaMauSac = c.MaMauSac\n"
                + "                JOIN \n"
                + "                   KichThuoc k ON k.MaKichThuoc = c.MaKichThuoc\n"
                + "                JOIN \n"
                + "                   ChatLieu cl ON cl.MaChatLieu = c.ChatLieu\n"
                + "                left JOIN\n"
                + "                HinhAnh ha ON ha.MaSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "                join \n"
                + "                chiTietKhuyenMai ctkm on ctkm.maSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "where trangThai = 1"
                + "                group by  c.MaSanPham, \n"
                + "                  TenSanPham,\n"
                + "                    TenNCC,\n"
                + "                    GiaDau,\n"
                + "                   c.SoLuong, \n"
                + "                   MauSac, \n"
                + "                   KichThuoc, \n"
                + "                    Mau, \n"
                + "                   TenChatLieu,\n"
                + "                Hang,\n"
                + "				l.ThoiGianBatDau,\n"
                + "				l.ThoiGianKetThuc,\n"
                + "				l.GiaSau\n"
                + "			order by Gia";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString(1));
                sp.setTenSP(rs.getString(2));
                sp.setNhaCungCap(rs.getString(3));
                sp.setDonGia(rs.getDouble(4));
                sp.setSoLuongSP(rs.getInt(5));
                sp.setMauSac(rs.getString(6));
                sp.setKichThuoc(rs.getString(7));
                sp.setMau(rs.getString(8));
                sp.setChatLieu(rs.getString(9));
                sp.setHinhAnh(rs.getString(10));
                sp.setHang(rs.getString(11));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    @Override
    public void deleteNhanVien(String maNV) {
        String sql = " delete NguoiDung where MaNguoiDung = ? and TrangThai = 0";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, maNV);
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<NguoiDung> sapXepTheoTuoiNV() {
        String sql = "select * from NguoiDung where Roles = 'NV' and TrangThai = 1 order by Tuoi";
        listNguoiDung.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                NguoiDung nd = new NguoiDung();
                nd.setMaNguoiDung(rs.getString(1));
                nd.setTenNguoiDung(rs.getString(2));
                nd.setGioiTinh(rs.getBoolean(3));
                nd.setTuoi(rs.getInt(4));
                nd.setSDT(rs.getString(5));
                nd.setEmail(rs.getString(6));
                nd.setRoles(rs.getString(7));
                nd.setTenDN(rs.getString(8));
                nd.setPassWord(rs.getString(9));
                nd.setTrangThai(rs.getBoolean(10));
                listNguoiDung.add(nd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listNguoiDung;
    }

    @Override
    public ArrayList<SanPham> TimKiemSanPhamTheoMaVaTenBanHang(String keyWord) {
        listSanPham.clear();
        String sql = "		SELECT distinct\n"
                + "                                  c.MaSanPham,\n"
                + "                                  TenSanPham,\n"
                + "                                   TenNCC, \n"
                + "                                    case when l.ThoiGianBatDau <= CURRENT_TIMESTAMP AND l.ThoiGianKetThuc >= CURRENT_TIMESTAMP THEN l.GiaSau\n"
                + "                					else l.GiaDau \n"
                + "                					end as Gia,\n"
                + "                                   c.SoLuong, \n"
                + "                                   MauSac, \n"
                + "                                    KichThuoc, \n"
                + "                                   Mau, \n"
                + "                                    TenChatLieu,\n"
                + "                                \n"
                + "                                   STRING_AGG(HinhAnh, ',') AS HinhAnh,\n"
                + "                                Hang\n"
                + "                                FROM \n"
                + "                                  ChiTietSanPham c\n"
                + "                              JOIN\n"
                + "                                SanPham s ON c.MaSanPham = s.MaSanPham\n"
                + "                               JOIN\n"
                + "                                  NhaCungCap n ON n.MaNCC = c.NCC\n"
                + "                               JOIN\n"
                + "                                   LichSuDonGia l ON l.MaSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "                               JOIN \n"
                + "                                  MauSac m ON m.MaMauSac = c.MaMauSac\n"
                + "                               JOIN \n"
                + "                                   KichThuoc k ON k.MaKichThuoc = c.MaKichThuoc\n"
                + "                                JOIN \n"
                + "                                  ChatLieu cl ON cl.MaChatLieu = c.ChatLieu\n"
                + "                               left JOIN\n"
                + "                               HinhAnh ha ON ha.MaSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "                               join \n"
                + "                               chiTietKhuyenMai ctkm on ctkm.maSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "                               group by  c.MaSanPham, \n"
                + "                                 TenSanPham,\n"
                + "                                TenNCC,\n"
                + "                                GiaDau,\n"
                + "                                c.SoLuong,\n"
                + "                                   MauSac,\n"
                + "                                 KichThuoc, \n"
                + "                                    Mau, \n"
                + "                                  TenChatLieu,\n"
                + "                               Hang,\n"
                + "                			l.ThoiGianBatDau,\n"
                + "                				l.ThoiGianKetThuc,\n"
                + "                				l.GiaSau\n"
                + "                having c.MaSanPham like ? or TenSanPham like ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + keyWord + "%");
            stm.setString(2, "%" + keyWord + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString(1));
                sp.setTenSP(rs.getString(2));
                sp.setNhaCungCap(rs.getString(3));
                sp.setDonGia(rs.getDouble(4));
                sp.setSoLuongSP(rs.getInt(5));
                sp.setMauSac(rs.getString(6));
                sp.setKichThuoc(rs.getString(7));
                sp.setMau(rs.getString(8));
                sp.setChatLieu(rs.getString(9));
                sp.setHinhAnh(rs.getString(10));
                sp.setHang(rs.getString(11));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    @Override
    public ArrayList<HoaDon> locHoaDonTheoTrangThaiBanHang(String trangThai) {
        String sql = "select * from HoaDon where TrangThai = ?";
        listHoaDon.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, trangThai);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getString(1));
                hd.setMaNhanVien(rs.getString(5));
                hd.setNgayTao(rs.getString(2));
                hd.setLoaiThanhToan(rs.getString(7));
                hd.setMaKhachHang(rs.getString(8));
                hd.setMaVoucher(rs.getString(4));
                hd.setNgayHoanThanh(rs.getString(6));
                hd.setTrangThai(rs.getString(3));
                listHoaDon.add(hd);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDon;
    }

    public String updateTrangThaiNhanVien(boolean trangThai, String maNhanVien) {
        String sql = "update NguoiDung set TrangThai = ? where MaNguoiDung = ? and Roles = 'NV'";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setBoolean(1, trangThai);
            stm.setString(2, maNhanVien);
            stm.executeUpdate();
            conn.close();
            return "Thành công";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Thất bại";
    }

    @Override
    public ArrayList<NguoiDung> searchNhanVienNghi(String ma, String tenNV) {
        String sql = "select * from NguoiDung where Roles = 'NV' and TrangThai = 0 and MaNguoiDung like ? or TenNguoiDung like ?";
        listNguoiDung.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + ma + "%");
            stm.setString(2, "%" + tenNV + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                NguoiDung nd = new NguoiDung();
                nd.setMaNguoiDung(rs.getString(1));
                nd.setTenNguoiDung(rs.getString(2));
                nd.setGioiTinh(rs.getBoolean(3));
                nd.setTuoi(rs.getInt(4));
                nd.setSDT(rs.getString(5));
                nd.setEmail(rs.getString(6));
                nd.setRoles(rs.getString(7));
                nd.setTenDN(rs.getString(8));
                nd.setPassWord(rs.getString(9));
                nd.setTrangThai(rs.getBoolean(10));
                listNguoiDung.add(nd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNguoiDung;
    }

    @Override
    public ArrayList<NguoiDung> sapXepTheoMaNhVienNghi() {
        String sql = "select * from NguoiDung where Roles = 'NV' and TrangThai = 0 order by MaNguoiDung desc ";
        listNguoiDung.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                NguoiDung nd = new NguoiDung();
                nd.setMaNguoiDung(rs.getString(1));
                nd.setTenNguoiDung(rs.getString(2));
                nd.setGioiTinh(rs.getBoolean(3));
                nd.setTuoi(rs.getInt(4));
                nd.setSDT(rs.getString(5));
                nd.setEmail(rs.getString(6));
                nd.setRoles(rs.getString(7));
                nd.setTenDN(rs.getString(8));
                nd.setPassWord(rs.getString(9));
                nd.setTrangThai(rs.getBoolean(10));
                listNguoiDung.add(nd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listNguoiDung;
    }

    @Override
    public ArrayList<NguoiDung> sapXepTheoTenNhVienNghi() {
        String sql = "select * from NguoiDung where Roles = 'NV' and TrangThai = 0 order by RIGHT(TenNguoiDung, CHARINDEX(' ', REVERSE(TenNguoiDung)) - 1)";
        listNguoiDung.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                NguoiDung nd = new NguoiDung();
                nd.setMaNguoiDung(rs.getString(1));
                nd.setTenNguoiDung(rs.getString(2));
                nd.setGioiTinh(rs.getBoolean(3));
                nd.setTuoi(rs.getInt(4));
                nd.setSDT(rs.getString(5));
                nd.setEmail(rs.getString(6));
                nd.setRoles(rs.getString(7));
                nd.setTenDN(rs.getString(8));
                nd.setPassWord(rs.getString(9));
                nd.setTrangThai(rs.getBoolean(10));
                listNguoiDung.add(nd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNguoiDung;
    }

    @Override
    public ArrayList<NguoiDung> sapXepTheoTuoiNVNghi() {
        String sql = "select * from NguoiDung where Roles = 'NV' and TrangThai = 0 order by Tuoi";
        listNguoiDung.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                NguoiDung nd = new NguoiDung();
                nd.setMaNguoiDung(rs.getString(1));
                nd.setTenNguoiDung(rs.getString(2));
                nd.setGioiTinh(rs.getBoolean(3));
                nd.setTuoi(rs.getInt(4));
                nd.setSDT(rs.getString(5));
                nd.setEmail(rs.getString(6));
                nd.setRoles(rs.getString(7));
                nd.setTenDN(rs.getString(8));
                nd.setPassWord(rs.getString(9));
                nd.setTrangThai(rs.getBoolean(10));
                listNguoiDung.add(nd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listNguoiDung;
    }

    @Override
    public ArrayList<HoaDon> getAllQuanLyHD() {
        String sql = "select distinct h.* from HoaDon h\n"
                + "join ChiTietHoaDon cthd on h.MaHoaDon = cthd.MaHoaDon\n"
                + "join ChiTietSanPham ctsp  on ctsp.MaSanPhamChiTiet = cthd.MaSanPhamChiTiet\n"
                + "join LichSuDonGia lsdg on lsdg.MaSanPhamChiTiet = ctsp.MaSanPhamChiTiet\n"
                + "where h.TrangThai not in (N'Đã huỷ')";
        listHoaDon.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getString(1));
                hd.setNgayTao(rs.getString(2));
                hd.setTrangThai(rs.getString(3));
                hd.setMaVoucher(rs.getString(4));
                hd.setMaNhanVien(rs.getString(5));
                hd.setNgayHoanThanh(rs.getString(6));
                hd.setLoaiThanhToan(rs.getString(7));
                hd.setMaKhachHang(rs.getString(8));
                listHoaDon.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDon;
    }

    @Override
    public ArrayList<SanPham> getAllQuanLyHDSP(String maHoaDon) {
        String sql = "select s.TenSanPham, c.TenChatLieu, k.KichThuoc, m.MauSac, s.Mau, s.Hang, cthd.SoLuong, l.GiaSau, (cthd.SoLuong * l.GiaSau) as ThanhTien from SanPham s\n"
                + "                join ChiTietSanPham ctsp on ctsp.MaSanPham = s.MaSanPham\n"
                + "                join MauSac m on m.MaMauSac = ctsp.MaMauSac\n"
                + "                join KichThuoc k on k.MaKichThuoc = ctsp.MaKichThuoc\n"
                + "                join ChatLieu c on c.MaChatLieu = ctsp.ChatLieu\n"
                + "                join LichSuDonGia l on l.MaSanPhamChiTiet = ctsp.MaSanPhamChiTiet\n"
                + "                join ChiTietHoaDon cthd on cthd.MaSanPhamChiTiet = ctsp.MaSanPhamChiTiet\n"
                + "                where  cthd.MaHoaDon = ?";
        listSanPham.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, maHoaDon);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setTenSP(rs.getString(1));
                sp.setChatLieu(rs.getString(2));
                sp.setKichThuoc(rs.getString(3));
                sp.setMauSac(rs.getString(4));
                sp.setMau(rs.getString(5));
                sp.setHang(rs.getString(6));
                sp.setSoLuongSP(rs.getInt(7));
                sp.setDonGia(rs.getDouble(8));
                listSanPham.add(sp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    @Override
    public ArrayList<HoaDon> getAllQLHDHuy() {
        String sql = "select h.* from HoaDon h\n"
                + "join ChiTietHoaDon cthd on h.MaHoaDon = cthd.MaHoaDon\n"
                + "join ChiTietSanPham ctsp  on ctsp.MaSanPhamChiTiet = cthd.MaSanPhamChiTiet\n"
                + "join LichSuDonGia lsdg on lsdg.MaSanPhamChiTiet = ctsp.MaSanPhamChiTiet\n"
                + "where h.TrangThai = N'Đã huỷ'";
        listHoaDon.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getString(1));
                hd.setNgayTao(rs.getString(2));
                hd.setTrangThai(rs.getString(3));
                hd.setMaVoucher(rs.getString(4));
                hd.setMaNhanVien(rs.getString(5));
                hd.setNgayHoanThanh(rs.getString(6));
                hd.setLoaiThanhToan(rs.getString(7));
                hd.setMaKhachHang(rs.getString(8));
                listHoaDon.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDon;
    }

    public ArrayList<HoaDon> addHoaDonBanHang(HoaDon hd) {
        String sql = "insert into HoaDon(MaHoaDon, NgayTao, TrangThai, MaNhanVien, LoaiThanhToan) values(?,?,?,?,?)";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, hd.getMaHoaDon());
            stm.setString(2, hd.getNgayTao());
            stm.setString(3, hd.getTrangThai());
            stm.setString(4, hd.getMaNhanVien());
            stm.setString(5, hd.getLoaiThanhToan());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDon;
    }

    @Override
    public ArrayList<HoaDon> searchQLHD(String maHoaDon) {
        String sql = "select h.* from HoaDon h\n"
                + "join ChiTietHoaDon cthd on h.MaHoaDon = cthd.MaHoaDon\n"
                + "join ChiTietSanPham ctsp  on ctsp.MaSanPhamChiTiet = cthd.MaSanPhamChiTiet\n"
                + "join LichSuDonGia lsdg on lsdg.MaSanPhamChiTiet = ctsp.MaSanPhamChiTiet\n"
                + "where h.TrangThai not in (N'Đã huỷ') and h.MaHoaDon like ?";
        listHoaDon.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + maHoaDon + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getString(1));
                hd.setNgayTao(rs.getString(2));
                hd.setTrangThai(rs.getString(3));
                hd.setMaVoucher(rs.getString(4));
                hd.setMaNhanVien(rs.getString(5));
                hd.setNgayHoanThanh(rs.getString(6));
                hd.setLoaiThanhToan(rs.getString(7));
                hd.setMaKhachHang(rs.getString(8));
                listHoaDon.add(hd);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDon;
    }

    public String searchMaNhanVienTheoTenDangNhap(String tenDangNhap) {
        String sql = "select MaNguoiDung from NguoiDung where TenDangNhap = ?";
        String kq = "";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, tenDangNhap);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                kq = rs.getString(1);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    public ArrayList<Login> layUserName(Login lg) {
        //listLoginTam.clear();
        listLoginTam.add(lg);
        return listLoginTam;
    }

    public String listLoginBanHang() {
        if (!listLoginTam.isEmpty()) { // Kiểm tra nếu danh sách không rỗng
            return listLoginTam.get(0).getUserName();
        } else {
            // Xử lý trường hợp danh sách rỗng, có thể thông báo lỗi hoặc trả về giá trị mặc định
            return "Danh sách đăng nhập rỗng";
        }
    }

    public HoaDon getRowHoaDonTheoMa(String maHoaDon) {
        for (HoaDon hd : listHoaDon) {
            if (hd.equals(maHoaDon)) {
                return hd;
            }
        }
        return new HoaDon();
    }

    @Override
    public ArrayList<Voucher> tKTNVoucher(String ngayBD, String HanSD) {
        listVoucher.clear();
        String sql = "select * from Voucher\n"
                + "where NgayBatDau >= ? and HanSuDung <= ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, ngayBD);
            stm.setString(2, HanSD);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Voucher vc = new Voucher();
                vc.setMaVoucher(rs.getString(1));
                vc.setTenVoucher(rs.getString(2));
                vc.setSoLuongVC(rs.getInt(3));
                vc.setHanSuDungVC(rs.getString(4));
                vc.setNgayBatDauVC(rs.getString(5));
                vc.setSoTienGiam(rs.getDouble(6));
                vc.setSoTienYeuCau(rs.getDouble(7));
                listVoucher.add(vc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVoucher;
    }

    @Override
    public ArrayList<KhuyenMai> tKTNKhuyenMai(String ngayBD, String HanSD) {
        listKhuyenMai.clear();
        String sql = "select KhuyenMai.MaKhuyenMai, TenKhuyenMai, HanSuDung, NgayBatDau, PTKhuyenMai, MaSanPhamChiTiet from KhuyenMai\n"
                + "join ChiTietKhuyenMai on ChiTietKhuyenMai.MaKhuyenMai  = KhuyenMai.MaKhuyenMai\n"
                + "where NgayBatDau >= ? and HanSuDung <= ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, ngayBD);
            stm.setString(2, HanSD);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKM(rs.getString(1));
                km.setTenKM(rs.getString(2));
                km.setHanSuDungKM(rs.getString(3));
                km.setNgayBatDauKM(rs.getString(4));
                km.setGiamGia(rs.getDouble(5));
                km.setMaCTSP(rs.getString(6));
                listKhuyenMai.add(km);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listKhuyenMai;
    }

    public Boolean addVoucher(Voucher vc) {
        String sql = "insert into Voucher values (?,?,?,?,?,?,?)";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, vc.getMaVoucher());
            stm.setString(2, vc.getTenVoucher());
            stm.setInt(3, vc.getSoLuongVC());
            stm.setString(4, vc.getHanSuDungVC());
            stm.setString(5, vc.getNgayBatDauVC());
            stm.setDouble(6, vc.getSoTienGiam());
            stm.setDouble(7, vc.getSoTienYeuCau());

            stm.executeUpdate();

            conn.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean updateVoucher(Voucher vc) {
        String sql = "update Voucher set TenVoucher =? , SoLuong=? , HanSuDung=?, NgayBatDau=?, SoTienGiam=?, SoTienYeuCau=? Where MaVoucher =?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, vc.getTenVoucher());
            stm.setInt(2, vc.getSoLuongVC());
            stm.setString(3, vc.getHanSuDungVC());
            stm.setString(4, vc.getNgayBatDauVC());
            stm.setDouble(5, vc.getSoTienGiam());
            stm.setDouble(6, vc.getSoTienYeuCau());
            stm.setString(7, vc.getMaVoucher());

            stm.executeUpdate();
            conn.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean deleteVoucher(String mvc) {
        String sql = "Delete Voucher where MaVoucher=?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, mvc);

            stm.executeUpdate();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public ArrayList<SanPham> getAllMauSac() {
        listSanPham.clear();
        String sql = "select DISTINCT MaMauSac, MauSac from MauSac";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaMS(rs.getString(1));
                sp.setMauSac(rs.getString(2));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public ArrayList<SanPham> getAllKichThuoc() {
        listSanPham.clear();
        String sql = "select DISTINCT MaKichThuoc, KichThuoc from KichThuoc";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaKT(rs.getString(1));
                sp.setKichThuoc(rs.getString(2));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public ArrayList<SanPham> getAllChatLieu() {
        listSanPham.clear();
        String sql = "select DISTINCT MaChatLieu, TenChatLieu from ChatLieu";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaCL(rs.getString(1));
                sp.setChatLieu(rs.getString(2));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public ArrayList<SanPham> getAllNCC() {
        listSanPham.clear();
        String sql = "select DISTINCT MaNCC, TenNCC, DiaChi, SDT, Email from NhaCungCap";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaNCC(rs.getString(1));
                sp.setNhaCungCap(rs.getString(2));
                sp.setDiaChiNCC(rs.getString(3));
                sp.setSDTNCC(rs.getString(4));
                sp.setEmailNCC(rs.getString(5));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public void addMauSacTTSP(SanPham s) {
        listSanPham.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "Insert into MauSac( MaMauSac, MauSac)Values (?,?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, s.getMaMS());
            stm.setString(2, s.getMauSac());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMauSacTTSP(SanPham s) {
        listSanPham.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "Update MauSac Set MauSac=? where MaMauSac=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(2, s.getMaMS());
            stm.setString(1, s.getMauSac());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteMauSacTTSP(String ma) {
        listSanPham.clear();
        try {

            Connection conn = DBConnect1.getConnection();
            String sql = "DELETE FROM MauSac Where MaMauSac = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, ma);

            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addKichThuocTTSP(SanPham s) {
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "Insert into KichThuoc( MaKichThuoc, KichThuoc)Values (?,?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, s.getMaKT());
            stm.setString(2, s.getKichThuoc());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateKichThuocTTSP(SanPham s) {
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "Update KichThuoc Set KichThuoc=? where MaKichThuoc=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(2, s.getMaKT());
            stm.setString(1, s.getKichThuoc());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteKichThuocTTSP(String ma) {
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "DELETE FROM KichThuoc Where MaKichThuoc = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, ma);

            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addChatLieuTTSP(SanPham s) {
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "Insert into ChatLieu( MaChatLieu, TenChatLieu)Values (?,?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, s.getMaCL());
            stm.setString(2, s.getChatLieu());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateChatLieuTTSP(SanPham s) {
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "Update ChatLieu Set TenChatLieu=? where MaChatLieu=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(2, s.getMaCL());
            stm.setString(1, s.getChatLieu());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteChatLieuTTSP(String ma) {
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "DELETE FROM ChatLieu Where MaChatLieu = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, ma);

            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNCCTTSP(SanPham s) {
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "Insert into NhaCungCap( MaNCC, TenNCC, DiaChi, SDT, Email)Values (?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, s.getMaNCC());
            stm.setString(2, s.getNhaCungCap());
            stm.setString(3, s.getDiaChiNCC());
            stm.setString(4, s.getSDTNCC());
            stm.setString(5, s.getEmailNCC());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateNCCTTSP(SanPham s) {
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "Update NhaCungCap Set TenNCC=?, DiaChi=?, SDT=?, Email=?  where MaNCC=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(5, s.getMaNCC());
            stm.setString(1, s.getNhaCungCap());
            stm.setString(2, s.getDiaChiNCC());
            stm.setString(3, s.getSDTNCC());
            stm.setString(4, s.getEmailNCC());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteNCCTTSP(String ma) {
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "DELETE FROM NhaCungCap Where MaNCC = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, ma);

            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SanPham> getAllSanPhamTTSP() {
        listSanPham.clear();
        String sql = "select MaSanPham, TenSanPham, Mau, Hang from SanPham";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString(1));
                sp.setTenSP(rs.getString(2));
                sp.setMau(rs.getString(3));
                sp.setHang(rs.getString(4));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public ArrayList<SanPham> getAllSXTheoTenSP() {
        listSanPham.clear();
        String sql = "select MaSanPham, TenSanPham, Mau, Hang from SanPham order by TenSanPham";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString(1));
                sp.setTenSP(rs.getString(2));
                sp.setMau(rs.getString(3));
                sp.setHang(rs.getString(4));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public ArrayList<SanPham> getAllSXTheoMaSP() {
        listSanPham.clear();
        String sql = "select MaSanPham, TenSanPham, Mau, Hang from SanPham order by MaSanPham";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString(1));
                sp.setTenSP(rs.getString(2));
                sp.setMau(rs.getString(3));
                sp.setHang(rs.getString(4));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public ArrayList<SanPham> getTimKiemSPTTSP(String keyword) {
        listSanPham.clear();
        String sql = "select MaSanPham, TenSanPham, Mau, Hang from SanPham\n"
                + "where MaSanPham like ? ";

        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + keyword + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString(1));
                sp.setTenSP(rs.getString(2));
                sp.setMau(rs.getString(3));
                sp.setHang(rs.getString(4));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public ServiceImp() {
    }

    public void addSanPhamTTSP(SanPham s) {
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "Insert into SanPham( MaSanPham, TenSanPham, Mau, Hang)Values (?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, s.getMaSP());
            stm.setString(2, s.getTenSP());
            stm.setString(3, s.getMau());
            stm.setString(4, s.getHang());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSanPhamTTSP(SanPham s) {
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "Update SanPham Set TenSanPham=?, Mau=?, Hang=?  where MaSanPham=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(4, s.getMaSP());
            stm.setString(1, s.getTenSP());
            stm.setString(2, s.getMau());
            stm.setString(3, s.getHang());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SanPham> getTimKiemSanPhamTTSP(String keyword) {
        listSanPham.clear();
        String sql = "SELECT DISTINCT\n"
                + "    c.MaSanPhamChiTiet, \n"
                + "    TenSanPham, \n"
                + "    TenNCC,  \n"
                + "    c.SoLuong, \n"
                + "    MauSac, \n"
                + "    KichThuoc, \n"
                + "    Mau, \n"
                + "    TenChatLieu, \n"
                + "    STRING_AGG(HinhAnh, ',') AS HinhAnh, \n"
                + "    Hang,\n"
                + "    trangThai\n"
                + "FROM \n"
                + "    ChiTietSanPham c\n"
                + "JOIN \n"
                + "    SanPham s ON c.MaSanPham = s.MaSanPham\n"
                + "JOIN \n"
                + "    NhaCungCap n ON n.MaNCC = c.NCC\n"
                + "JOIN \n"
                + "    MauSac m ON m.MaMauSac = c.MaMauSac\n"
                + "JOIN \n"
                + "    KichThuoc k ON k.MaKichThuoc = c.MaKichThuoc\n"
                + "JOIN \n"
                + "    ChatLieu cl ON cl.MaChatLieu = c.ChatLieu\n"
                + "LEFT JOIN \n"
                + "    HinhAnh ha ON ha.MaSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "WHERE \n"
                + "    c.MaSanPhamChiTiet LIKE ?\n"
                + "GROUP BY  \n"
                + "    c.MaSanPhamChiTiet, \n"
                + "    TenSanPham, \n"
                + "    TenNCC, \n"
                + "    c.SoLuong, \n"
                + "    MauSac, \n"
                + "    KichThuoc, \n"
                + "    Mau, \n"
                + "    TenChatLieu, \n"
                + "    Hang,;"
                + "    trangThai;";

        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + keyword + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSPCT(rs.getString(1));
                sp.setTenSP(rs.getString(2));
                sp.setNhaCungCap(rs.getString(3));
                sp.setSoLuongSP(rs.getInt(4));
                sp.setMauSac(rs.getString(5));
                sp.setKichThuoc(rs.getString(6));
                sp.setMau(rs.getString(7));
                sp.setChatLieu(rs.getString(8));
                sp.setHinhAnh(rs.getString(9));
                sp.setHang(rs.getString(10));
                sp.setTrangThai(rs.getBoolean(11));

                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    @Override
    public ArrayList<HoaDon> searchQLHuy(String maHoaDon) {
        String sql = "select h.* from HoaDon h\n"
                + "join ChiTietHoaDon cthd on h.MaHoaDon = cthd.MaHoaDon\n"
                + "join ChiTietSanPham ctsp  on ctsp.MaSanPhamChiTiet = cthd.MaSanPhamChiTiet\n"
                + "join LichSuDonGia lsdg on lsdg.MaSanPhamChiTiet = ctsp.MaSanPhamChiTiet\n"
                + "where h.TrangThai = N'Đã huỷ' and h.MaHoaDon = ? ";
        listHoaDon.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, maHoaDon);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getString(1));
                hd.setNgayTao(rs.getString(2));
                hd.setTrangThai(rs.getString(3));
                hd.setMaVoucher(rs.getString(4));
                hd.setMaNhanVien(rs.getString(5));
                hd.setNgayHoanThanh(rs.getString(6));
                hd.setLoaiThanhToan(rs.getString(7));
                hd.setMaKhachHang(rs.getString(8));
                listHoaDon.add(hd);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDon;
    }

    public ArrayList<SanPham> SapXepTheoMaSP() {
        listSanPham.clear();
        String sql = " SELECT DISTINCT\n"
                + "    c.MaSanPhamChiTiet, TenSanPham, TenNCC,  c.SoLuong, MauSac, KichThuoc, Mau, TenChatLieu,STRING_AGG(HinhAnh, ',') AS HinhAnh,Hang,trangThai\n"
                + "FROM \n"
                + "    ChiTietSanPham c\n"
                + "	\n"
                + "JOIN \n"
                + "    SanPham s ON c.MaSanPham = s.MaSanPham\n"
                + "JOIN \n"
                + "    NhaCungCap n ON n.MaNCC = c.NCC\n"
                + "JOIN \n"
                + "    MauSac m ON m.MaMauSac = c.MaMauSac\n"
                + "JOIN \n"
                + "    KichThuoc k ON k.MaKichThuoc = c.MaKichThuoc\n"
                + "JOIN \n"
                + "    ChatLieu cl ON cl.MaChatLieu = c.ChatLieu\n"
                + "LEFT JOIN \n"
                + "    HinhAnh ha ON ha.MaSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "GROUP BY  \n"
                + "    c.MaSanPhamChiTiet, \n"
                + "    TenSanPham, \n"
                + "    TenNCC, \n"
                + "    c.SoLuong, \n"
                + "    MauSac, \n"
                + "    KichThuoc, \n"
                + "    Mau, \n"
                + "    TenChatLieu,\n"
                + "    Hang,\n"
                + "    TrangThai\n"
                + "ORDER BY\n"
                + "    c.MaSanPhamChiTiet ";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSPCT(rs.getString(1));
                sp.setTenSP(rs.getString(2));
                sp.setNhaCungCap(rs.getString(3));
                sp.setSoLuongSP(rs.getInt(4));
                sp.setMauSac(rs.getString(5));
                sp.setKichThuoc(rs.getString(6));
                sp.setMau(rs.getString(7));
                sp.setChatLieu(rs.getString(8));
                sp.setHinhAnh(rs.getString(9));
                sp.setHang(rs.getString(10));
                sp.setTrangThai(rs.getBoolean(11));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public ArrayList<SanPham> SapXepTheoTenSP() {
        listSanPham.clear();
        String sql = " SELECT DISTINCT\n"
                + "    c.MaSanPhamChiTiet, TenSanPham, TenNCC,  c.SoLuong, MauSac, KichThuoc, Mau, TenChatLieu,STRING_AGG(HinhAnh, ',') AS HinhAnh,Hang, trangThai\n"
                + "FROM \n"
                + "    ChiTietSanPham c\n"
                + "	\n"
                + "JOIN \n"
                + "    SanPham s ON c.MaSanPham = s.MaSanPham\n"
                + "JOIN \n"
                + "    NhaCungCap n ON n.MaNCC = c.NCC\n"
                + "JOIN \n"
                + "    MauSac m ON m.MaMauSac = c.MaMauSac\n"
                + "JOIN \n"
                + "    KichThuoc k ON k.MaKichThuoc = c.MaKichThuoc\n"
                + "JOIN \n"
                + "    ChatLieu cl ON cl.MaChatLieu = c.ChatLieu\n"
                + "LEFT JOIN \n"
                + "    HinhAnh ha ON ha.MaSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "\n"
                + "		\n"
                + "GROUP BY  \n"
                + "    c.MaSanPhamChiTiet, \n"
                + "    TenSanPham, \n"
                + "    TenNCC, \n"
                + "    c.SoLuong, \n"
                + "    MauSac, \n"
                + "    KichThuoc, \n"
                + "    Mau, \n"
                + "    TenChatLieu,\n"
                + "    Hang,\n"
                + "    TrangThai\n"
                + "ORDER BY\n"
                + "    TenSanPham ";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSPCT(rs.getString(1));
                sp.setTenSP(rs.getString(2));
                sp.setNhaCungCap(rs.getString(3));
                sp.setSoLuongSP(rs.getInt(4));
                sp.setMauSac(rs.getString(5));
                sp.setKichThuoc(rs.getString(6));
                sp.setMau(rs.getString(7));
                sp.setChatLieu(rs.getString(8));
                sp.setHinhAnh(rs.getString(9));
                sp.setHang(rs.getString(10));
                sp.setTrangThai(rs.getBoolean(11));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public ArrayList<SanPham> getAllSanPhamCT() {
        listSanPham.clear();
        String sql = "SELECT DISTINCT\n"
                + "    c.MaSanPhamChiTiet, TenSanPham, TenNCC,  c.SoLuong, MauSac, KichThuoc, Mau, TenChatLieu,STRING_AGG(HinhAnh, ',') AS HinhAnh,Hang, trangThai\n"
                + "FROM \n"
                + "    ChiTietSanPham c\n"
                + "	\n"
                + "JOIN \n"
                + "    SanPham s ON c.MaSanPham = s.MaSanPham\n"
                + "JOIN \n"
                + "    NhaCungCap n ON n.MaNCC = c.NCC\n"
                + "JOIN \n"
                + "    MauSac m ON m.MaMauSac = c.MaMauSac\n"
                + "JOIN \n"
                + "    KichThuoc k ON k.MaKichThuoc = c.MaKichThuoc\n"
                + "JOIN \n"
                + "    ChatLieu cl ON cl.MaChatLieu = c.ChatLieu\n"
                + "LEFT JOIN \n"
                + "    HinhAnh ha ON ha.MaSanPhamChiTiet = c.MaSanPhamChiTiet\n"
                + "\n"
                + "		\n"
                + "GROUP BY  \n"
                + "    c.MaSanPhamChiTiet, \n"
                + "    TenSanPham, \n"
                + "    TenNCC, \n"
                + "    c.SoLuong, \n"
                + "    MauSac, \n"
                + "    KichThuoc, \n"
                + "    Mau, \n"
                + "    TenChatLieu,\n"
                + "    Hang,\n"
                + "trangThai";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSPCT(rs.getString(1));
                sp.setTenSP(rs.getString(2));
                sp.setNhaCungCap(rs.getString(3));
                sp.setSoLuongSP(rs.getInt(4));
                sp.setMauSac(rs.getString(5));
                sp.setKichThuoc(rs.getString(6));
                sp.setMau(rs.getString(7));
                sp.setChatLieu(rs.getString(8));
                sp.setHinhAnh(rs.getString(9));
                sp.setHang(rs.getString(10));
                sp.setTrangThai(rs.getBoolean(11));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public String getIDSanPham(String tenSP) {
        listSanPham.clear();
        String sql = "Select MaSanPham from SanPham where TenSanPham = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, tenSP);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            stm.executeUpdate();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }

    public String getIDMauSac(String tenMS) {
        listSanPham.clear();
        String sql = "Select MaMauSac from MauSac where MauSac = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, tenMS);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            stm.executeUpdate();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }

    public String getIDKichThuoc(String tenKT) {
        listSanPham.clear();
        String sql = "Select MaKichThuoc from KichThuoc where KichThuoc = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, tenKT);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            stm.executeUpdate();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }

    public String getIDChatLieu(String tenCL) {
        listSanPham.clear();
        String sql = "Select MaChatLieu from ChatLieu where TenChatLieu = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, tenCL);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            stm.executeUpdate();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }

    public String getIDNCC(String tenNCC) {
        listSanPham.clear();
        String sql = "Select MaNCC from NhaCungCap where TenNCC = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, tenNCC);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            stm.executeUpdate();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }

    public static void main(String[] args) {
        ServiceInterface ser = new ServiceImp();
        System.out.println(ser.getIDNCC("Công ty TNHH A"));
        System.out.println(ser.getMaSPCT("SP001"));
    }

    public void addCTSPTTSP(SanPham s) {
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "Insert into Chitietsanpham( MaSanPhamChiTiet, MaSanPham, SoLuong, MaKichThuoc, MaMauSac, NCC, ChatLieu, trangThai)Values (?,?,?,?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, s.getMaSPCT());
            stm.setString(2, s.getMaSP());
            stm.setInt(3, s.getSoLuongSP());
            stm.setString(4, s.getMaKT());
            stm.setString(5, s.getMaMS());
            stm.setString(6, s.getMaNCC());
            stm.setString(7, s.getMaCL());
            stm.setBoolean(8, s.getTrangThai());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSLCTSPTTSP(SanPham s) {
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "Update Chitietsanpham set   SoLuong = SoLuong + ?, trangthai = ? Where MaSanPhamChiTiet = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(3, s.getMaSPCT());
            stm.setInt(1, s.getSoLuongSP());
            stm.setBoolean(2,s.getTrangThai());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCTSPTTSP(SanPham s) {
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "Update Chitietsanpham set  MaSanPham = ?, SoLuong = ? , MaKichThuoc = ?, MaMauSac = ?, NCC = ?, ChatLieu = ?, trangThai=? Where MaSanPhamChiTiet = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(8, s.getMaSPCT());
            stm.setString(1, s.getMaSP());
            stm.setInt(2, s.getSoLuongSP());
            stm.setString(3, s.getMaKT());
            stm.setString(4, s.getMaMS());
            stm.setString(5, s.getMaNCC());
            stm.setString(6, s.getMaCL());
            stm.setBoolean(7, s.getTrangThai());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<LichSuGia> getAllLichSuDonGia() {
        listLichSuGia.clear();
        String sql = "Select ls.MaDonGia, ls.MaSanPhamChiTiet,sp.TenSanPham, GiaDau ,\n"
                + "GiaSau , CONVERT(Varchar , ThoiGianBatDau, 110 ) ThoiGianBatDau,\n"
                + "CONVERT(Varchar , ThoiGianKetThuc, 110 ) ThoiGianKetThuc from LichSuDonGia ls\n"
                + "join ChiTietSanPham ct on ls.MaSanPhamChiTiet = ct.MaSanPhamChiTiet\n"
                + "join SanPham sp on ct.MaSanPham = sp.MaSanPham";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                LichSuGia ls = new LichSuGia();
                ls.setMaDonGia(rs.getString(1));
                ls.setMaSPCT(rs.getString(2));
                ls.setTenSP(rs.getString(3));
                ls.setGiaDau(rs.getDouble(4));
                ls.setGiaSau(rs.getDouble(5));
                ls.setNgayBatDau(rs.getString(6));
                ls.setNgayKetThuc(rs.getString(7));
                listLichSuGia.add(ls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLichSuGia;
    }

    public ArrayList<LichSuGia> locLichSuDonGia(String ngayBatDau, String ngayKetThuc) {
        String sql = "Select ls.MaDonGia, ls.MaSanPhamChiTiet,sp.TenSanPham, GiaDau ,\n"
                + "GiaSau , CONVERT(Varchar , ThoiGianBatDau, 110 ) ThoiGianBatDau,\n"
                + "CONVERT(Varchar , ThoiGianKetThuc, 110 ) ThoiGianKetThuc from LichSuDonGia ls\n"
                + "join ChiTietSanPham ct on ls.MaSanPhamChiTiet = ct.MaSanPhamChiTiet\n"
                + "join SanPham sp on ct.MaSanPham = sp.MaSanPham\n"
                + "where ls.ThoiGianBatDau between ? and ? ";
        listLichSuGia.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, ngayBatDau);
            stm.setString(2, ngayKetThuc);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                LichSuGia ls = new LichSuGia();
                ls.setMaDonGia(rs.getString(1));
                ls.setMaSPCT(rs.getString(2));
                ls.setTenSP(rs.getString(3));
                ls.setGiaDau(rs.getDouble(4));
                ls.setGiaSau(rs.getDouble(5));
                ls.setNgayBatDau(rs.getString(6));
                ls.setNgayKetThuc(rs.getString(7));
                listLichSuGia.add(ls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLichSuGia;
    }

    public ArrayList<LichSuGia> getTimKiemLSG(String keyword) {
        listLichSuGia.clear();
        String sql = "SELECT ls.MaDonGia, ls.MaSanPhamChiTiet, sp.TenSanPham, GiaDau, GiaSau, \n"
                + "       CONVERT(VARCHAR, ThoiGianBatDau, 110) AS ThoiGianBatDau, \n"
                + "       CONVERT(VARCHAR, ThoiGianKetThuc, 110) AS ThoiGianKetThuc \n"
                + "FROM LichSuDonGia ls\n"
                + "JOIN ChiTietSanPham ct ON ls.MaSanPhamChiTiet = ct.MaSanPhamChiTiet\n"
                + "JOIN SanPham sp ON ct.MaSanPham = sp.MaSanPham\n"
                + "WHERE ls.MaDonGia LIKE ?";

        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + keyword + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                LichSuGia ls = new LichSuGia();
                ls.setMaDonGia(rs.getString(1));
                ls.setMaSPCT(rs.getString(2));
                ls.setTenSP(rs.getString(3));
                ls.setGiaDau(rs.getDouble(4));
                ls.setGiaSau(rs.getDouble(5));
                ls.setNgayBatDau(rs.getString(6));
                ls.setNgayKetThuc(rs.getString(7));
                listLichSuGia.add(ls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLichSuGia;
    }

    public ArrayList<LichSuGia> getSXTheoMaLSDG() {
        listLichSuGia.clear();
        String sql = "Select ls.MaDonGia, ls.MaSanPhamChiTiet,sp.TenSanPham, GiaDau ,\n"
                + "GiaSau , CONVERT(Varchar , ThoiGianBatDau, 110 ) ThoiGianBatDau,\n"
                + "CONVERT(Varchar , ThoiGianKetThuc, 110 ) ThoiGianKetThuc from LichSuDonGia ls\n"
                + "join ChiTietSanPham ct on ls.MaSanPhamChiTiet = ct.MaSanPhamChiTiet\n"
                + "join SanPham sp on ct.MaSanPham = sp.MaSanPham"
                + " order by ls.MaDonGia";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                LichSuGia ls = new LichSuGia();
                ls.setMaDonGia(rs.getString(1));
                ls.setMaSPCT(rs.getString(2));
                ls.setTenSP(rs.getString(3));
                ls.setGiaDau(rs.getDouble(4));
                ls.setGiaSau(rs.getDouble(5));
                ls.setNgayBatDau(rs.getString(6));
                ls.setNgayKetThuc(rs.getString(7));
                listLichSuGia.add(ls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLichSuGia;
    }

    public ArrayList<LichSuGia> getSXTheoGiaLSDG() {
        listLichSuGia.clear();
        String sql = "Select ls.MaDonGia, ls.MaSanPhamChiTiet,sp.TenSanPham, GiaDau ,\n"
                + "GiaSau , CONVERT(Varchar , ThoiGianBatDau, 110 ) ThoiGianBatDau,\n"
                + "CONVERT(Varchar , ThoiGianKetThuc, 110 ) ThoiGianKetThuc from LichSuDonGia ls\n"
                + "join ChiTietSanPham ct on ls.MaSanPhamChiTiet = ct.MaSanPhamChiTiet\n"
                + "join SanPham sp on ct.MaSanPham = sp.MaSanPham"
                + " order by ls.GiaDau";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                LichSuGia ls = new LichSuGia();
                ls.setMaDonGia(rs.getString(1));
                ls.setMaSPCT(rs.getString(2));
                ls.setTenSP(rs.getString(3));
                ls.setGiaDau(rs.getDouble(4));
                ls.setGiaSau(rs.getDouble(5));
                ls.setNgayBatDau(rs.getString(6));
                ls.setNgayKetThuc(rs.getString(7));
                listLichSuGia.add(ls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLichSuGia;
    }

    public void addLichSuGia(LichSuGia ls) {
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "Insert into LichSuDonGia( MaDonGia, Masanphamchitiet , GiaDau, GiaSau, ThoiGianBatDau, ThoiGianketThuc)Values (?,?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, ls.getMaDonGia());
            stm.setString(2, ls.getMaSPCT());
            stm.setDouble(3, ls.getGiaDau());
            stm.setDouble(4, ls.getGiaSau());
            stm.setString(5, ls.getNgayBatDau());
            stm.setString(6, ls.getNgayKetThuc());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateLichSuGia(LichSuGia ls) {
        try {
            Connection conn = DBConnect1.getConnection();
            String sql = "update LichSuDonGia set MaSanPhamChiTiet = ?, GiaDau=?, GiaSau=?, ThoiGianBatDau=?, ThoiGianketThuc=? where MaDonGia = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(6, ls.getMaDonGia());
            stm.setString(1, ls.getMaSPCT());
            stm.setDouble(2, ls.getGiaDau());
            stm.setDouble(3, ls.getGiaSau());
            stm.setString(4, ls.getNgayBatDau());
            stm.setString(5, ls.getNgayKetThuc());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SanPham> getAllMau() {
        listSanPham.clear();
        String sql = "select  Mau from SanPham group by Mau";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMau(rs.getString(1));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    @Override
    public ArrayList<HoaDon> locHDTheoNgay(String ngayBatDau, String ngayKetThuc) {
        String sql = "select MaHoaDon, NgayTao, TrangThai, MaVoucher, MaNhanVien, NgayHoanThanh, LoaiThanhToan, MaKhachHang from HoaDon\n"
                + "where  TrangThai not in (N'Đã huỷ') and NgayTao between ? and ? ";
        listHoaDon.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, ngayBatDau);
            stm.setString(2, ngayKetThuc);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getString(1));
                hd.setNgayTao(rs.getString(2));
                hd.setTrangThai(rs.getString(3));
                hd.setMaVoucher(rs.getString(4));
                hd.setMaNhanVien(rs.getString(5));
                hd.setNgayHoanThanh(rs.getString(6));
                hd.setLoaiThanhToan(rs.getString(7));
                hd.setMaKhachHang(rs.getString(8));
                listHoaDon.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDon;
    }

    @Override
    public ArrayList<HoaDon> locHDHuyTheoNgay(String ngayBatDau, String ngayKetThuc) {
        String sql = "select MaHoaDon, NgayTao, TrangThai, MaVoucher, MaNhanVien, NgayHoanThanh, LoaiThanhToan, MaKhachHang from HoaDon\n"
                + "where  TrangThai = N'Đã huỷ' and NgayTao between ? and ? ";
        listHoaDon.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, ngayBatDau);
            stm.setString(2, ngayKetThuc);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getString(1));
                hd.setNgayTao(rs.getString(2));
                hd.setTrangThai(rs.getString(3));
                hd.setMaVoucher(rs.getString(4));
                hd.setMaNhanVien(rs.getString(5));
                hd.setNgayHoanThanh(rs.getString(6));
                hd.setLoaiThanhToan(rs.getString(7));
                hd.setMaKhachHang(rs.getString(8));
                listHoaDon.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDon;
    }

    public ArrayList<HoaDon> updateLoaiThanhToanMaKhachHangBanHang(HoaDon hd) {
        String sql = "  update HoaDon set LoaiThanhToan = ? , MaKhachHang = ? where MaHoaDon = ?";
        if (hd.getMaKhachHang().isEmpty()) {
            hd.setMaKhachHang(null);
        }
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, hd.getLoaiThanhToan());
            stm.setString(2, hd.getMaKhachHang());
            stm.setString(3, hd.getMaHoaDon());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDon;
    }

    public String thanhToanHoaDon(String trangThai, String ngayHoanThanh, String maHoaDon) {
        String sql = "update HoaDon set TrangThai = ?, NgayHoanThanh = ? where MaHoaDon = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, trangThai);
            stm.setString(2, ngayHoanThanh);
            stm.setString(3, maHoaDon);
            stm.executeUpdate();
            conn.close();
            return "Thanh toán thành công";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Thanh toán thất bại";
    }

    @Override
    public ArrayList<HoaDon> huyHoaDonBanHang(String maHoaDon, String trangThai) {
        String sql = "update HoaDon set TrangThai = ? where maHoaDon = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, trangThai);
            stm.setString(2, maHoaDon);
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDon;
    }

    @Override
    public ArrayList<HoaDon> xoaHoaDonBanhang(String maHoaDon) {
        String sql = "delete HoaDon where maHoaDon = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, maHoaDon);
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDon;
    }

    public ArrayList<Voucher> showHoaDonTheoVoucher(Integer tien) {
        String sql = "select * from voucher where ? >= SoTienYeuCau and HanSuDung >= CURRENT_TIMESTAMP order by SoTienGiam desc";
        listVoucher.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, tien);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Voucher vc = new Voucher();
                vc.setMaVoucher(rs.getString(1));
                vc.setTenVoucher(rs.getString(2));
                vc.setSoLuongVC(rs.getInt(3));
                vc.setHanSuDungVC(rs.getString(4));
                vc.setNgayBatDauVC(rs.getString(5));
                vc.setSoTienGiam(rs.getDouble(6));
                vc.setSoTienYeuCau(rs.getDouble(7));
                listVoucher.add(vc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVoucher;
    }

    @Override
    public ArrayList<Login> reSetPassWord(String user, String passWord) {
        String sql = "select TenDangNhap, MatKhau, TenRole, Email from NguoiDung \n"
                + "join Roles on NguoiDung.Roles = Roles.Marole\n"
                + "where NguoiDung.TenDangNhap = ? and MatKhau = ?";
        listLogin.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, user);
            stm.setString(2, passWord);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Login lg = new Login();
                lg.setUserName(rs.getString(1));
                lg.setPassword(rs.getString(2));
                lg.setRole(rs.getString(3));
                lg.setEmail(rs.getString(4));
                listLogin.add(lg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLogin;

    }

    public ArrayList<HoaDon> getAllHoaDonChuaHoanThanh() {
        String sql = "select h.* from HoaDon h where h.TrangThai = N'Chưa hoàn thành'";
        listHoaDon.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getString(1));
                hd.setMaNhanVien(rs.getString(5));
                hd.setNgayTao(rs.getString(2));
                hd.setLoaiThanhToan(rs.getString(7));
                hd.setMaKhachHang(rs.getString(8));
                hd.setMaVoucher(rs.getString(4));
                hd.setNgayHoanThanh(rs.getString(6));
                hd.setTrangThai(rs.getString(3));
                listHoaDon.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listHoaDon;
    }

    @Override
    public ArrayList<KhuyenMai> sXMaKM() {
        listKhuyenMai.clear();
        String sql = "select KhuyenMai.MaKhuyenMai, TenKhuyenMai, NgayBatDau, HanSuDung, PTKhuyenMai, ChiTietKhuyenMai.MaSanPhamChiTiet from KhuyenMai\n"
                + "join ChiTietKhuyenMai on ChiTietKhuyenMai.MaKhuyenMai = KhuyenMai.MaKhuyenMai\n"
                + "order by KhuyenMai.MaKhuyenMai desc";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKM(rs.getString(1));
                km.setTenKM(rs.getString(2));
                km.setNgayBatDauKM(rs.getString(3));
                km.setHanSuDungKM(rs.getString(4));
                km.setGiamGia(rs.getDouble(5));
                km.setMaCTSP(rs.getString(6));
                listKhuyenMai.add(km);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listKhuyenMai;
    }

    @Override
    public ArrayList<KhuyenMai> sXTTTenKM() {
        listKhuyenMai.clear();
        String sql = "select KhuyenMai.MaKhuyenMai, TenKhuyenMai, NgayBatDau, HanSuDung, PTKhuyenMai, ChiTietKhuyenMai.MaSanPhamChiTiet from KhuyenMai\n"
                + "join ChiTietKhuyenMai on ChiTietKhuyenMai.MaKhuyenMai = KhuyenMai.MaKhuyenMai\n"
                + "order by TenKhuyenMai desc";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKM(rs.getString(1));
                km.setTenKM(rs.getString(2));
                km.setNgayBatDauKM(rs.getString(3));
                km.setHanSuDungKM(rs.getString(4));
                km.setGiamGia(rs.getDouble(5));
                km.setMaCTSP(rs.getString(6));
                listKhuyenMai.add(km);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listKhuyenMai;
    }

    @Override
    public ArrayList<Voucher> sXTNgayVoucher() {
        listVoucher.clear();
        String sql = "select * from Voucher\n"
                + "order by NgayBatDau desc";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Voucher vc = new Voucher();
                vc.setMaVoucher(rs.getString(1));
                vc.setTenVoucher(rs.getString(2));
                vc.setSoLuongVC(rs.getInt(3));
                vc.setHanSuDungVC(rs.getString(4));
                vc.setNgayBatDauVC(rs.getString(5));
                vc.setSoTienGiam(rs.getDouble(6));
                vc.setSoTienYeuCau(rs.getDouble(7));
                listVoucher.add(vc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVoucher;
    }

    @Override
    public ArrayList<KhuyenMai> sXTNgayKhuyenMai() {
        listKhuyenMai.clear();
        String sql = "select KhuyenMai.MaKhuyenMai, TenKhuyenMai, NgayBatDau, HanSuDung, PTKhuyenMai, ChiTietKhuyenMai.MaSanPhamChiTiet from KhuyenMai\n"
                + "join ChiTietKhuyenMai on ChiTietKhuyenMai.MaKhuyenMai = KhuyenMai.MaKhuyenMai\n"
                + "order by NgayBatDau desc";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKM(rs.getString(1));
                km.setTenKM(rs.getString(2));
                km.setNgayBatDauKM(rs.getString(3));
                km.setHanSuDungKM(rs.getString(4));
                km.setGiamGia(rs.getDouble(5));
                km.setMaCTSP(rs.getString(6));
                listKhuyenMai.add(km);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listKhuyenMai;
    }

    @Override
    public void addKhuyenMai(KhuyenMai km) {
        String sql = "insert into KhuyenMai values (?,?,?,?,?)";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, km.getMaKM());
            stm.setString(2, km.getTenKM());
            stm.setString(3, km.getHanSuDungKM());
            stm.setString(4, km.getNgayBatDauKM());
            stm.setDouble(5, km.getGiamGia());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMK(String user, String passWord) {
        String sql = "update NguoiDung set MatKhau = ? where TenDangNhap = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, passWord);
            stm.setString(2, user);
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<HoaDon> thanhToanApVoucher(String maVoucher, String maHoaDon) {
        String sql = "update HoaDon set MaVoucher = ? where MaHoaDon = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, maVoucher);
            stm.setString(2, maHoaDon);
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDon;
    }

    public void updateKhuyenMai(KhuyenMai km) {
        String sql = "update KhuyenMai set TenKhuyenMai = ?, HanSuDung = ?, NgayBatDau = ? where MaKhuyenMai = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, km.getTenKM());
            stm.setString(2, km.getHanSuDungKM());
            stm.setString(3, km.getNgayBatDauKM());
            stm.setString(4, km.getMaKM());
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String updateSoLuongVoucherTru(String maVoucher) {
        String sql = "update Voucher set SoLuong = SoLuong - 1 where MaVoucher = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, maVoucher);
            stm.executeUpdate();
            conn.close();
            return "Số lượng voucher " + maVoucher + " -1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public void addSPKM(SanPham sp) {
        String sql = "insert into ChiTietKhuyenMai values (?,?)";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, sp.getMaSPCT());
            stm.setString(2, sp.getMaSPKM());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getMaSPCT(String MaSP) {
        String maSPCT = "";
        String sql = "select b.MaSanPhamChiTiet from SanPham a\n"
                + "join ChiTietSanPham b on b.MaSanPham = a.MaSanPham\n"
                + "where a.MaSanPham = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, MaSP);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return maSPCT = rs.getString(1);
            }
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maSPCT;
    }

    public Integer layGiaGiamVoucher(String maVoucher) {
        Integer gia = 0;
        String sql = "select SoTienGiam from Voucher where MaVoucher = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, maVoucher);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return gia = rs.getInt(1);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gia;
    }

    public ArrayList<HoaDonChiTiet> getAllHoaDonChiTietChuaHoanThanh(String maHoaDon) {
        String sql = "SELECT distinct cthd.MaHoaDon, cthd.MaSanPhamChiTiet, sp.TenSanPham, cthd.SoLuong, ls.GiaDau,\n"
                + "       CASE\n"
                + "           WHEN (CURRENT_TIMESTAMP BETWEEN ThoiGianBatDau AND ThoiGianKetThuc) OR\n"
                + "                (CURRENT_TIMESTAMP BETWEEN km.NgayBatDau AND km.HanSuDung) THEN ls.GiaSau - (ls.GiaSau * km.PTKhuyenMai / 100)\n"
                + "           ELSE ls.GiaDau\n"
                + "       END AS Gia\n"
                + "FROM ChiTietSanPham ct\n"
                + "JOIN LichSuDonGia ls ON ls.MaSanPhamChiTiet = ct.MaSanPhamChiTiet\n"
                + "JOIN SanPham sp ON sp.MaSanPham = ct.MaSanPham\n"
                + "JOIN ChiTietHoaDon cthd ON cthd.MaSanPhamChiTiet = ct.MaSanPhamChiTiet\n"
                + "JOIN HoaDon hd ON hd.MaHoaDon = cthd.MaHoaDon \n"
                + "JOIN (\n"
                + "    SELECT MaSanPhamChiTiet, MAX(kmm.PTKhuyenMai) AS MaxPTKhuyenMai\n"
                + "    FROM ChiTietKhuyenMai\n"
                + "	join KhuyenMai kmm on kmm.MaKhuyenMai = ChiTietKhuyenMai.MaKhuyenMai\n"
                + "    GROUP BY MaSanPhamChiTiet\n"
                + ") max_km ON ct.MaSanPhamChiTiet = max_km.MaSanPhamChiTiet\n"
                + "JOIN KhuyenMai km ON km.PTKhuyenMai = max_km.MaxPTKhuyenMai\n"
                + "WHERE cthd.MaHoaDon = ?;";
        listHoaDonChiTiet.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, maHoaDon);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setMaHoaDon(rs.getString(1));
                hdct.setMaSanPham(rs.getString(2));
                hdct.setTenSanPham(rs.getString(3));
                hdct.setSoLuong(rs.getInt(4));
                hdct.setDonGia(rs.getDouble(5));
                hdct.setDonGiaSau(rs.getDouble(6));
                listHoaDonChiTiet.add(hdct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDonChiTiet;
    }

    public ArrayList<SanPham> getAllSanPhamKM() {
        listSanPham.clear();
        String sql = "select ChiTietSanPham.MaSanPham, TenSanPham, ChiTietKhuyenMai.MaKhuyenMai from ChiTietSanPham\n"
                + "join SanPham on SanPham.MaSanPham = ChiTietSanPham.MaSanPham\n"
                + "right join ChiTietKhuyenMai on ChiTietKhuyenMai.MaSanPhamChiTiet = ChiTietSanPham.MaSanPhamChiTiet";
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString(1));
                sp.setTenSP(rs.getString(2));
                sp.setMaSPKM(rs.getString(3));
                listSanPham.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    @Override
    public ArrayList<Voucher> timKiemVoucherBanHang(String keyWord) {
        String sql = "select * from Voucher where MaVoucher like ?  or TenVoucher like ?";
        listVoucher.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + keyWord + "%");
            stm.setString(2, "%" + keyWord + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Voucher vc = new Voucher();
                vc.setMaVoucher(rs.getString(1));
                vc.setTenVoucher(rs.getString(2));
                vc.setSoLuongVC(rs.getInt(3));
                vc.setHanSuDungVC(rs.getString(4));
                vc.setNgayBatDauVC(rs.getString(5));
                vc.setSoTienGiam(rs.getDouble(6));
                vc.setSoTienYeuCau(rs.getDouble(7));
                listVoucher.add(vc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVoucher;
    }

    @Override
    public String getEmail(String tenDN) {
        String getEmail = "";
        String sql = "select email from NguoiDung where tenDangNhap = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, tenDN);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                getEmail = rs.getString("email");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getEmail;
    }

    public ArrayList<KhachHang> timKiemTenKhachHangSDT(String tenHoacSDT) {
        ArrayList<KhachHang> listKhachHang = new ArrayList<>();

        String sql = "SELECT * FROM KhachHang WHERE TenKhachHang LIKE ? OR SDT LIKE ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + tenHoacSDT + "%");
            stm.setString(2, "%" + tenHoacSDT + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("MaKhachHang"));
                kh.setTenKhachHang(rs.getString("TenKhachHang"));
                kh.setSDT(rs.getString("SDT"));
                kh.setDiaChi(rs.getString("DiaChi"));
                listKhachHang.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listKhachHang;
    }

    @Override
    public ArrayList<NguoiDung> listEmail(NguoiDung nd) {
        listEMail.add(nd);
        return listEMail;
    }

    @Override
    public String getTenDN() {
        return listEMail.get(0).getTenDN();
    }

    @Override
    public ArrayList<NguoiDung> listNV() {
        return listEMail;
    }

    @Override
    public void themAnhVaoCTSP(String hinhAnh, String maSanPhamChiTiet, String maHinhAnh) {
        String sql = "insert into HinhAnh values(?,?,?)";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, maHinhAnh);
            stm.setString(2, maSanPhamChiTiet);
            stm.setString(3, hinhAnh);
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer getAllHinhAnh() {
        String sql = "select count(MaHinhAnh)as slHA from HinhAnh";
        int dem = 0;
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                dem = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dem;
    }

    @Override
    public void updateHinhAnhVaoCTSP(String hinhAnh, String maSanPhamChiTiet) {
        String sql = "update HinhAnh set HinhAnh = ? where MaSanPhamChiTiet = ?";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, hinhAnh);
            stm.setString(2, maSanPhamChiTiet);
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<LichSuGia> getLichSuGiaList(String maSPCT) {
        ArrayList<LichSuGia> lichSuGiaList = new ArrayList<>();

        try {
            // Thực hiện truy vấn để lấy dữ liệu từ bảng lịch sử giá dựa trên maSPCT
            String sql = "SELECT CONVERT(Varchar, ThoiGianBatDau, 110) AS ThoiGianBatDau,\n"
                    + "       CONVERT(Varchar, ThoiGianKetThuc, 110) AS ThoiGianKetThuc\n"
                    + "FROM LichSuDonGia WHERE MaSanPhamChiTiet = ?";
            Connection conn = DBConnect1.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maSPCT);
            ResultSet rs = ps.executeQuery();

            // Xử lý dữ liệu lấy được và tạo các đối tượng LichSuGia
            while (rs.next()) {
                LichSuGia ls = new LichSuGia();
                ls.setStartTime(rs.getString("ThoiGianBatDau"));
                ls.setEndTime(rs.getString("ThoiGianKetThuc"));
                lichSuGiaList.add(ls);
            }

            // Đóng tài nguyên
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lichSuGiaList;
    }
    @Override
    public void updateTrangThaiSanPhamSoLuong() {
        String sql = "update ChiTietSanPham set TrangThai = 0 where SoLuong = 0";
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<SanPham> dsSPThongKe() {
        String sql = "SELECT \n"
                + "    s.TenSanPham, \n"
                + "    c.TenChatLieu, \n"
                + "    k.KichThuoc, \n"
                + "    m.MauSac, \n"
                + "    s.Mau, \n"
                + "    s.Hang, \n"
                + "    SUM(cthd.SoLuong) AS TongSoLuong, \n"
                + "	l.giaSau,\n"
                + "    SUM(l.GiaSau * cthd.SoLuong) AS DoanhThu\n"
                + "FROM \n"
                + "    SanPham s\n"
                + "JOIN \n"
                + "    ChiTietSanPham ctsp ON ctsp.MaSanPham = s.MaSanPham\n"
                + "JOIN \n"
                + "    MauSac m ON m.MaMauSac = ctsp.MaMauSac\n"
                + "JOIN \n"
                + "    KichThuoc k ON k.MaKichThuoc = ctsp.MaKichThuoc\n"
                + "JOIN \n"
                + "    ChatLieu c ON c.MaChatLieu = ctsp.ChatLieu\n"
                + "JOIN \n"
                + "    LichSuDonGia l ON l.MaSanPhamChiTiet = ctsp.MaSanPhamChiTiet\n"
                + "JOIN \n"
                + "    ChiTietHoaDon cthd ON cthd.MaSanPhamChiTiet = ctsp.MaSanPhamChiTiet\n"
                + "JOIN \n"
                + "	HoaDon h on h.MaHoaDon = cthd.MaHoaDon\n"
                + "WHERE \n"
                + "	h.TrangThai = N'Đã hoàn thành'\n"
                + "GROUP BY \n"
                + "    s.TenSanPham, c.TenChatLieu, k.KichThuoc, m.MauSac, s.Mau, s.Hang, l.GiaSau\n"
                + "ORDER BY \n"
                + "    SUM(cthd.SoLuong) DESC;";
        listSanPham.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setTenSP(rs.getString(1));
                sp.setChatLieu(rs.getString(2));
                sp.setKichThuoc(rs.getString(3));
                sp.setMauSac(rs.getString(4));
                sp.setMau(rs.getString(5));
                sp.setHang(rs.getString(6));
                sp.setSoLuongSP(rs.getInt(7));
                sp.setDonGia(rs.getDouble(8));
                listSanPham.add(sp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    @Override
    public ArrayList<ChiTietHoaDon> dsHDThongKe() {
        listCTHD.clear();
        String sql = "	select distinct h.MaHoaDon, h.MaNhanVien,h.NgayTao, h.NgayHoanThanh from HoaDon h\n"
                + "                join ChiTietHoaDon cthd on h.MaHoaDon = cthd.MaHoaDon\n"
                + "                join ChiTietSanPham ctsp  on ctsp.MaSanPhamChiTiet = cthd.MaSanPhamChiTiet\n"
                + "                join LichSuDonGia lsdg on lsdg.MaSanPhamChiTiet = ctsp.MaSanPhamChiTiet\n"
                + "                where h.TrangThai = N'Đã hoàn thành'";

        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                ChiTietHoaDon cthd = new ChiTietHoaDon();
                cthd.setMaHoaDon(rs.getString(1));
                cthd.setMaNhanVien(rs.getString(2));
                cthd.setNgayTao(rs.getString(3));
                cthd.setNgayHoanThanh(rs.getString(4));
                listCTHD.add(cthd);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCTHD;

    }

    @Override
    public Integer tongHoaDonThanhToanDHuy(String ngayBatDau, String ngayKetThuc) {
        String sql = "SELECT COUNT(*) AS 'TONG_DON_HANG_THANH_CONG' FROM HoaDon WHERE TrangThai =N'Đã huỷ' and ngayTao between ? and ?";
        Integer tongHoaDonThanhToan = 0;
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);

            stm.setString(1, ngayBatDau);
            stm.setString(2, ngayKetThuc);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                tongHoaDonThanhToan = rs.getInt(1);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongHoaDonThanhToan;
    }

    @Override
    public Integer tongHoaDonThanhToanDHT(String ngayBatDau, String ngayKetThuc) {

        String sql = "SELECT COUNT(*) AS 'TONG_DON_HANG_THANH_CONG' FROM HoaDon WHERE TrangThai =N'Đã hoàn thành' and ngayTao between ? and ?";
        Integer tongHoaDonThanhToan = 0;
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);

            stm.setString(1, ngayBatDau);
            stm.setString(2, ngayKetThuc);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                tongHoaDonThanhToan = rs.getInt(1);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongHoaDonThanhToan;
    }

    @Override
    public Integer tongHoaDonThanhToanHuy() {
        String sql = "SELECT COUNT(*) AS 'TONG_DON_HANG_THANH_CONG' FROM HoaDon WHERE TrangThai =N'Đã huỷ'";
        Integer tongHoaDonThanhToan = 0;
        try {
            Connection conn = DBConnect1.getConnection();
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                tongHoaDonThanhToan = rs.getInt(1);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongHoaDonThanhToan;
    }

    @Override
    public ArrayList<HoaDonChiTiet> getHDCTBCTK(String maHoaDon) {
        String sql = "SELECT distinct cthd.MaHoaDon, cthd.MaSanPhamChiTiet, sp.TenSanPham, cthd.SoLuong, ls.GiaDau,\n"
                + "       CASE\n"
                + "           WHEN (NgayHoanThanh BETWEEN ThoiGianBatDau AND ThoiGianKetThuc) OR\n"
                + "                (NgayHoanThanh BETWEEN km.NgayBatDau AND km.HanSuDung) THEN ls.GiaSau - (ls.GiaSau * km.PTKhuyenMai / 100)\n"
                + "           ELSE ls.GiaDau\n"
                + "       END AS Gia\n"
                + "FROM ChiTietSanPham ct\n"
                + "JOIN LichSuDonGia ls ON ls.MaSanPhamChiTiet = ct.MaSanPhamChiTiet\n"
                + "JOIN SanPham sp ON sp.MaSanPham = ct.MaSanPham\n"
                + "JOIN ChiTietHoaDon cthd ON cthd.MaSanPhamChiTiet = ct.MaSanPhamChiTiet\n"
                + "JOIN HoaDon hd ON hd.MaHoaDon = cthd.MaHoaDon \n"
                + "JOIN (\n"
                + "    SELECT MaSanPhamChiTiet, MAX(kmm.PTKhuyenMai) AS MaxPTKhuyenMai\n"
                + "    FROM ChiTietKhuyenMai\n"
                + "	join KhuyenMai kmm on kmm.MaKhuyenMai = ChiTietKhuyenMai.MaKhuyenMai\n"
                + "    GROUP BY MaSanPhamChiTiet\n"
                + ") max_km ON ct.MaSanPhamChiTiet = max_km.MaSanPhamChiTiet\n"
                + "JOIN KhuyenMai km ON km.PTKhuyenMai = max_km.MaxPTKhuyenMai\n"
                + "WHERE cthd.MaHoaDon = ? and hd.TrangThai = N'Đã hoàn thành';";
        listHoaDonChiTiet.clear();
        try {
            Connection conn = DBConnect1.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, maHoaDon);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setMaHoaDon(rs.getString(1));
                hdct.setMaSanPham(rs.getString(2));
                hdct.setTenSanPham(rs.getString(3));
                hdct.setSoLuong(rs.getInt(4));
                hdct.setDonGia(rs.getDouble(5));
                hdct.setDonGiaSau(rs.getDouble(6));
                listHoaDonChiTiet.add(hdct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHoaDonChiTiet;
    }
}


