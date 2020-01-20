package unhas.informatics.monitoringapp.ui.genset;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import unhas.informatics.monitoringapp.Model.Barang;
import unhas.informatics.monitoringapp.Preference.SharedPrefManager;
import unhas.informatics.monitoringapp.R;

public class AdapterGenset extends RecyclerView.Adapter<AdapterGenset.BindGenset> {

    private List<Barang> barangs;

    public AdapterGenset(List<Barang> barangs) {
        this.barangs = barangs;
    }

    @NonNull
    @Override
    public BindGenset onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BindGenset(LayoutInflater.from(parent.getContext()).inflate(R.layout.data_barang, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BindGenset holder, int position) {
        holder.barangPinjaman(barangs.get(position));
    }

    @Override
    public int getItemCount() {
        return barangs.size();
    }

    class BindGenset extends RecyclerView.ViewHolder{
        TextView nama, daya;
        LinearLayoutCompat ly;
        BindGenset(@NonNull View itemView) {
            super(itemView);
        }

        void barangPinjaman(Barang barang){
            ly   = itemView.findViewById(R.id.lyBarang);
            nama = itemView.findViewById(R.id.namaBarang);
            daya = itemView.findViewById(R.id.daya);

            nama.setText(barang.getNama());
            daya.setText(barang.getDaya());

            ly.setOnClickListener(v -> {

            });
            if (SharedPrefManager.getRegisteredStatus(itemView.getContext()).equals("admin")) {
                ly.setOnClickListener(v -> {
                    ly.setBackgroundColor(Color.RED);
                    Toast.makeText(itemView.getContext(), daya.getText().toString(), Toast.LENGTH_SHORT).show();
                });
            }else{
                ly.setOnClickListener(v -> Toast.makeText(itemView.getContext(),
                        "anda adalah : "+SharedPrefManager.getRegisteredStatus(itemView.getContext())
                        ,Toast.LENGTH_SHORT).show());
            }
        }
    }
}
