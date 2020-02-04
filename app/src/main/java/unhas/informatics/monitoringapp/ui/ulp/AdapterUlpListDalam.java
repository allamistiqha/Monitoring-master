package unhas.informatics.monitoringapp.ui.ulp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import unhas.informatics.monitoringapp.Model.UlpPelanggan;
import unhas.informatics.monitoringapp.R;

public class AdapterUlpListDalam extends RecyclerView.Adapter<AdapterUlpListDalam.BindUlpMalino> {
    private List<UlpPelanggan> ulpPelanggans;
    public AdapterUlpListDalam(List<UlpPelanggan> ulpPelanggans) {
        this.ulpPelanggans = ulpPelanggans;
    }

    @NonNull
    @Override
    public BindUlpMalino onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BindUlpMalino(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BindUlpMalino holder, int position) {
        holder.barangPinjaman(ulpPelanggans.get(position));
    }

    @Override
    public int getItemCount() {
        return ulpPelanggans.size();
    }

    class BindUlpMalino extends RecyclerView.ViewHolder {
        TextView nama, daya;
        public BindUlpMalino(@NonNull View itemView) {
            super(itemView);
        }
        void barangPinjaman(UlpPelanggan barang){
            nama = itemView.findViewById(R.id.namaBarangL);
            daya = itemView.findViewById(R.id.dayaL);

            nama.setText(barang.getNama());
            daya.setText(barang.getDaya());
        }
    }
}


