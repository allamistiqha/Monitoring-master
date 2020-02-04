package unhas.informatics.monitoringapp.ui.genset;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

            if (SharedPrefManager.getRegisteredStatus(itemView.getContext()).equals("Admin")) {
                ly.setOnClickListener(v -> {
                    String alamatUlp = SharedPrefManager.getUlp(itemView.getContext());
                    if (SharedPrefManager.getStatusUlp(itemView.getContext()).equals("Ulp")){
                        if (!SharedPrefManager.getNamepelanggan(itemView.getContext()).isEmpty()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                            builder.setTitle("Genset");
                            builder.setMessage("Anda yakin ingin memilih : "
                                    +barang.getNama()+" "+barang.getDaya()+"?");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yakin", (dialog, which) -> {
                                Toast.makeText(itemView.getContext(), "Menyiapkan Data", Toast.LENGTH_SHORT).show();
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/Genset");
                                reference.child(barang.getKey()).removeValue();

                                DatabaseReference referenceUlp = FirebaseDatabase.getInstance()
                                        .getReference("user/Ulp/"+alamatUlp+"/"+SharedPrefManager.getNamepelanggan(itemView.getContext()));
                                String newKey  =  reference.push().getKey();
                                Barang dataUlp = new Barang(
                                        barang.getNama(),
                                        barang.getDaya()
                                        ,newKey
                                );
                                referenceUlp.child(newKey)
                                        .setValue(dataUlp)
                                        .addOnCompleteListener(task -> {
                                            Toast.makeText(itemView.getContext(), "Data Berhasil Ditambahkan",
                                                    Toast.LENGTH_SHORT).show();
                                            SharedPrefManager.clearDataUlp(itemView.getContext());
                                        });
                            });
                            builder.setNegativeButton("Batal", (dialog, which) -> {
                                dialog.cancel();
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                            //
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                            builder.setTitle("Genset");
                            builder.setMessage("Anda yakin ingin menghapus : "
                                    + barang.getNama() +" "+barang.getDaya()+"?");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yakin", (dialog, which) -> {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/Genset");
                                reference.child(barang.getKey())
                                        .removeValue()
                                        .addOnCompleteListener(task ->
                                                Toast.makeText(itemView.getContext(), "Data Deleted", Toast.LENGTH_SHORT).show()
                                        );
                            });
                            builder.setNegativeButton("Batal", (dialog, which) -> {
                                dialog.cancel();
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }else {
                        if (!SharedPrefManager.getNamepelanggan(itemView.getContext()).isEmpty()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                            builder.setTitle("Genset");
                            builder.setMessage("Anda yakin ingin memilih : "+barang.getNama()+" "
                                    +barang.getDaya()+"?");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yakin", (dialog, which) -> {
                                Toast.makeText(itemView.getContext(), "Menyiapkan Data", Toast.LENGTH_SHORT).show();
                                SharedPrefManager.setNamaBarang(itemView.getContext(),barang.getNama());
                                SharedPrefManager.setDaya(itemView.getContext(),barang.getDaya());
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/Genset");
                                reference.child(barang.getKey()).removeValue();

                                DatabaseReference referenceUlp = FirebaseDatabase.getInstance()
                                        .getReference("user/Ulp/"+alamatUlp+"/"+SharedPrefManager.getNamepelanggan(itemView.getContext()));
                                String newKey  =  referenceUlp.push().getKey();
                                Barang dataUlp = new Barang(
                                        barang.getNama(),
                                        barang.getDaya()
                                        ,newKey
                                );
                                referenceUlp.child(newKey)
                                        .setValue(dataUlp)
                                        .addOnCompleteListener(task -> {
                                            Toast.makeText(itemView.getContext(),
                                                    "Data Berhasil Dimasukkan", Toast.LENGTH_SHORT).show();
                                            SharedPrefManager.clearDataUlp(itemView.getContext());
                                        });
                            });
                            builder.setNegativeButton("Batal", (dialog, which) -> {
                                dialog.cancel();
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                            //
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                            builder.setTitle("Genset");
                            builder.setMessage("Anda yakin ingin menghapus : "
                                    + barang.getNama() +" "+barang.getDaya()+"?");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yakin", (dialog, which) -> {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/Genset");
                                reference.child(barang.getKey())
                                        .removeValue()
                                        .addOnCompleteListener(task ->
                                                Toast.makeText(itemView.getContext(), "Data Deleted", Toast.LENGTH_SHORT).show()
                                        );
                            });
                            builder.setNegativeButton("Batal", (dialog, which) -> {
                                dialog.cancel();
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }
                });
            }else{
                ly.setOnClickListener(v -> Toast.makeText(itemView.getContext(),
                        "Maaf Anda bukalah Admin : "
                        ,Toast.LENGTH_SHORT).show());
            }
        }
//        private void changeFragment() {
//            Navigation.findNavController(Objects.requireNonNull(itemView)).navigate(R.id.action_navigation_genset_to_navigation_ulp);
//        }
    }
}
