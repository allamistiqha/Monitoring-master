package unhas.informatics.monitoringapp.ui.ups;

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

import unhas.informatics.monitoringapp.Model.Ups;
import unhas.informatics.monitoringapp.Preference.SharedPrefManager;
import unhas.informatics.monitoringapp.R;
import unhas.informatics.monitoringapp.ui.genset.AdapterGenset;

public class AdapterUps extends RecyclerView.Adapter<AdapterUps.BindUps> {
    private List<Ups> upss;

    public AdapterUps(List<Ups> upss) {
        this.upss = upss;
    }

    @NonNull
    @Override
    public AdapterUps.BindUps onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterUps.BindUps(LayoutInflater.from(parent.getContext()).inflate(R.layout.data_ups, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUps.BindUps holder, int position) {
        holder.barangPinjaman(upss.get(position));
    }

    @Override
    public int getItemCount() {
        return upss.size();
    }

    class BindUps extends RecyclerView.ViewHolder{
        TextView nama, daya;
        LinearLayoutCompat ly;
        BindUps(@NonNull View itemView) {
            super(itemView);
        }

        void barangPinjaman(Ups barang){
            ly   = itemView.findViewById(R.id.lyUps);
            nama = itemView.findViewById(R.id.namaBarang);
            daya = itemView.findViewById(R.id.daya);

            nama.setText(barang.getNama());
            daya.setText(barang.getDaya());

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

