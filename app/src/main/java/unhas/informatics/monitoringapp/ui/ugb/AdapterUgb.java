package unhas.informatics.monitoringapp.ui.ugb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import unhas.informatics.monitoringapp.Model.Barang;
import unhas.informatics.monitoringapp.Model.Ugb;
import unhas.informatics.monitoringapp.R;

public class AdapterUgb extends RecyclerView.Adapter<AdapterUgb.BindUgb> {

    private List<Barang> ugbs;

    public AdapterUgb(List<Barang> ugbs) {
        this.ugbs = ugbs;
    }

    @NonNull
    @Override
    public BindUgb onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BindUgb(LayoutInflater.from(parent.getContext()).inflate(R.layout.data_barang, parent, false));
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

        public BindUgb(@NonNull View itemView) {
            super(itemView);
        }

        void barangPinjaman(Barang ugb){
            nama = itemView.findViewById(R.id.namaBarang);
            daya = itemView.findViewById(R.id.daya);

            nama.setText(ugb.getNama());
            daya.setText(ugb.getDaya());

        }
    }
}
