package unhas.informatics.monitoringapp.ui.ugb;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import unhas.informatics.monitoringapp.Model.Barang;
import unhas.informatics.monitoringapp.Model.Ugb;
import unhas.informatics.monitoringapp.Preference.SharedPrefManager;
import unhas.informatics.monitoringapp.R;

public class AdapterUgb extends RecyclerView.Adapter<AdapterUgb.BindUgb> {

    private List<Ugb> ugbs;

    public AdapterUgb(List<Ugb> ugbs) {
        this.ugbs = ugbs;
    }

    @NonNull
    @Override
    public BindUgb onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BindUgb(LayoutInflater.from(parent.getContext()).inflate(R.layout.data_ugb, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BindUgb holder, int position) {
        holder.barangPinjaman(ugbs.get(position));
    }

    @Override
    public int getItemCount() {
        return ugbs.size();
    }

    class BindUgb extends RecyclerView.ViewHolder{
        TextView nama, daya;
        LinearLayoutCompat ly;

        public BindUgb(@NonNull View itemView) {
            super(itemView);
        }

        void barangPinjaman(Ugb ugb){
            ly   = itemView.findViewById(R.id.lyUgb);
            nama = itemView.findViewById(R.id.namaBarang);
            daya = itemView.findViewById(R.id.daya);

            nama.setText(ugb.getNama());
            daya.setText(ugb.getDaya());

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
