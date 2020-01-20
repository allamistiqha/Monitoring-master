package unhas.informatics.monitoringapp.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class DataPelanggan  {

    String status;
    String nama;
    String phone;
    String daya;
    String lokasi;
    String tanggal;
    String key;
    public  DataPelanggan(){}
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDaya() {
        return daya;
    }

    public void setDaya(String daya) {
        this.daya = daya;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public DataPelanggan(String status, String nama, String phone, String daya, String lokasi, String tanggal,String key){
        this.status = status;
        this.nama = nama;
        this.phone = phone;
        this.daya = daya;
        this.lokasi = lokasi;
        this.tanggal = tanggal;
        this.key =key;
    }




}
