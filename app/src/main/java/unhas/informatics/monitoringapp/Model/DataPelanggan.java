package unhas.informatics.monitoringapp.Model;

public class DataPelanggan  {

    String status;
    String nama;
    String event;
    String phone;
    String daya;
    String lokasi;
    String ulp;
    String tanggal;
    String tanggalAkhir;
    String barang;
    String key;

    public String getBarang() {
        return barang;
    }

    public void setBarang(String barang) {
        this.barang = barang;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTanggalAkhir() {
        return tanggalAkhir;
    }

    public void setTanggalAkhir(String tanggalAkhir) {
        this.tanggalAkhir = tanggalAkhir;
    }

//    DataPelanggan(status,nama,phone,daya,lokasi,ulp,tanggal, tangglAkhir,idUser);
    public DataPelanggan(String status,
                         String nama,
                         String phone,
                         String event,
                         String daya,
                         String lokasi,
                         String ulp,
                         String tanggal,
                         String tanggalAkhir,
                         String barang,
                         String key) {
        this.status = status;
        this.nama = nama;
        this.phone = phone;
        this.event = event;
        this.daya = daya;
        this.lokasi = lokasi;
        this.ulp = ulp;
        this.tanggal = tanggal;
        this.tanggalAkhir = tanggalAkhir;
        this.barang = barang;
        this.key = key;
    }

    public String getUlp() {
        return ulp;
    }

    public void setUlp(String ulp) {
        this.ulp = ulp;
    }

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

}
