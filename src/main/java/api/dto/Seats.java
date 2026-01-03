package api.dto;

public class Seats {
    private int maGhe;
    private String tenGhe;
    private String loaiGhe;
    private boolean daDat;
    private double giaVe;

    //getters and setters

    public int getMaGhe() {
        return maGhe;
    }
    public void setMaGhe(int maGhe) {
        this.maGhe = maGhe;
    }
    public String getTenGhe() {
        return tenGhe;
    }
    public void setTenGhe(String tenGhe) {
        this.tenGhe = tenGhe;
    }
    public String getLoaiGhe() {
        return loaiGhe;
    }
    public void setLoaiGhe(String loaiGhe) {
        this.loaiGhe = loaiGhe;
    }
    public boolean isDaDat() {
        return daDat;
    }
    public void setDaDat(boolean daDat) {
        this.daDat = daDat;
    }
    public double getGiaVe() {
        return giaVe;
    }
    public void setGiaVe(double giaVe) {
        this.giaVe = giaVe;
    }
}
