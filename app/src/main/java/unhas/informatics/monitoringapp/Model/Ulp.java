package unhas.informatics.monitoringapp.Model;
import com.google.firebase.database.Exclude;
import java.io.Serializable;

public class Ulp implements Serializable {
    String nama,tanggal,status,barang,id;

    public Ulp(String nama, String tanggal, String status, String barang, String id) {
        this.nama = nama;
        this.tanggal = tanggal;
        this.status = status;
        this.barang = barang;
        this.id = id;
    }

    public Ulp(){

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


}
