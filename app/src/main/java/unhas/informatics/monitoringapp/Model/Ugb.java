package unhas.informatics.monitoringapp.Model;
import java.io.Serializable;

public class Ugb implements Serializable {
    public Ugb(){
    }
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDaya() {
        return daya;
    }

    public void setDaya(String daya) {
        this.daya = daya;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String nama;
    private String daya;
    private String key;

    public Ugb(String nama, String daya, String key) {
        this.nama = nama;
        this.daya = daya;
        this.key = key;
    }
}
