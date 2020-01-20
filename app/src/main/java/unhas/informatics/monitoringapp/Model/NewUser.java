package unhas.informatics.monitoringapp.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class NewUser implements Serializable {
    private String nama;
    private String pass;
    private String email;
    private String status;
    private String key;

    public NewUser() {
    }
    public NewUser(String nama, String pass, String email, String status) {
        this.nama = nama;
        this.pass = pass;
        this.email = email;
        this.status = status;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public NewUser(String nama, String pass) {
        this.nama = nama;
        this.pass = pass;
    }
    @Exclude
    public String getKey() {
        return key;
    }
    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
