package unhas.informatics.monitoringapp.ui.ulp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import unhas.informatics.monitoringapp.Model.UlpPanakukang;
import unhas.informatics.monitoringapp.Preference.SharedPrefManager;
import unhas.informatics.monitoringapp.R;

public class AdapterUlpPanakukang extends RecyclerView.Adapter<AdapterUlpPanakukang.BindUlpPanakukang> {

    private List<UlpPanakukang> panakukangs;

    public AdapterUlpPanakukang(List<UlpPanakukang> panakukangs) {
        this.panakukangs = panakukangs;
    }

    @NonNull
    @Override
    public BindUlpPanakukang onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BindUlpPanakukang(LayoutInflater.from(parent.getContext()).inflate(R.layout.data_ulp, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull BindUlpPanakukang holder, int position) {
        holder.barangPinjaman(panakukangs.get(position));
    }

    @Override
    public int getItemCount() {
        return panakukangs.size();
    }

    class BindUlpPanakukang extends RecyclerView.ViewHolder {
        TextView nama, barang, tanggal, status;
        LinearLayoutCompat ly;

        public BindUlpPanakukang(@NonNull View itemView) {
            super(itemView);
        }

        void barangPinjaman(UlpPanakukang ulp) {
            ly = itemView.findViewById(R.id.lyUlp);
            nama = itemView.findViewById(R.id.nama);
            barang = itemView.findViewById(R.id.barang);
            tanggal = itemView.findViewById(R.id.tglPemakaian);
            status = itemView.findViewById(R.id.statusPelanggan);


            nama.setText(ulp.getNama());
            barang.setText(ulp.getBarang());
            tanggal.setText(ulp.getTanggal());
            status.setText(ulp.getStatus());

            if (SharedPrefManager.getRegisteredStatus(itemView.getContext()).equals("admin")) {
                ly.setOnClickListener(v -> {
                    ly.setBackgroundColor(Color.RED);
                    Toast.makeText(itemView.getContext(), nama.getText().toString(), Toast.LENGTH_SHORT).show();
                });
            }else{
                ly.setOnClickListener(v -> Toast.makeText(itemView.getContext(),
                        "anda adalah : "+SharedPrefManager.getRegisteredStatus(itemView.getContext())
                        ,Toast.LENGTH_SHORT).show());
            }
        }
    }
}


