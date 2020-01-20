package unhas.informatics.monitoringapp.Model;

import java.io.Serializable;

public class UlpPanakukang implements Serializable {
    public UlpPanakukang(){

    }
    public UlpPanakukang(String nama, String tanggal, String status, String barang, String id) {
        this.nama = nama;
        this.tanggal = tanggal;
        this.status = status;
        this.barang = barang;
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBarang() {
        return barang;
    }

    public void setBarang(String barang) {
        this.barang = barang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String nama,tanggal,status,barang,id;
}
