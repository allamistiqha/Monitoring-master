package unhas.informatics.monitoringapp.ui.ups;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

import unhas.informatics.monitoringapp.Model.Barang;
import unhas.informatics.monitoringapp.Preference.SharedPrefManager;
import unhas.informatics.monitoringapp.R;
import unhas.informatics.monitoringapp.ui.ulp.UlpFragment;

public class AdapterUps extends RecyclerView.Adapter<AdapterUps.BindUps> {
    private List<Barang> upss;
    private Fragment fragment;
    public AdapterUps(List<Barang> upss, Fragment fragmnet) {
        this.upss = upss;
        this.fragment =  fragmnet;
    }

    @NonNull
    @Override
    public AdapterUps.BindUps onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterUps.BindUps(LayoutInflater.from(parent.getContext()).inflate(R.layout.data_barang, parent, false));
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
        BindUps(@NonNull View itemView) {
            super(itemView);
        }
        void barangPinjaman(Barang barang){
            nama = itemView.findViewById(R.id.namaBarang);
            daya = itemView.findViewById(R.id.daya);

            nama.setText(barang.getNama());
            daya.setText(barang.getDaya());

            if (SharedPrefManager.getRegisteredStatus(itemView.getContext()).equals("Admin")) {
                itemView.setOnClickListener(v -> {
                    String alamatUlp = SharedPrefManager.getUlp(itemView.getContext());
                    if (SharedPrefManager.getStatusUlp(itemView.getContext()).equals("Ulp")){
                        if (!SharedPrefManager.getNamepelanggan(itemView.getContext()).isEmpty()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                            builder.setTitle("Ups");
                            builder.setMessage("Anda yakin ingin memilih : "
                                    +barang.getNama()+" "+barang.getDaya()+"?");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yakin", (dialog, which) -> {
                                Toast.makeText(itemView.getContext(), "Menyiapkan Data", Toast.LENGTH_SHORT).show();
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/UPS");
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
                                            Fragment fragmentt = new UlpFragment();
                                            FragmentManager fragmentManager = ((FragmentActivity)itemView.getContext()).getSupportFragmentManager();
                                            fragmentManager.beginTransaction().replace(R.id.drawer, fragmentt);
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
                            builder.setTitle("UPS");
                            builder.setMessage("Anda yakin ingin menghapus : "
                                    + barang.getNama() +" "+barang.getDaya()+"?");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yakin", (dialog, which) -> {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/UPS");
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
                            builder.setTitle("UPS");
                            builder.setMessage("Anda yakin ingin memilih : "+barang.getNama()+" "
                                    +barang.getDaya()+"?");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yakin", (dialog, which) -> {
                                Toast.makeText(itemView.getContext(), "Menyiapkan Data", Toast.LENGTH_SHORT).show();
                                SharedPrefManager.setNamaBarang(itemView.getContext(),barang.getNama());
                                SharedPrefManager.setDaya(itemView.getContext(),barang.getDaya());
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/UPS");
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
                                            Toast.makeText(itemView.getContext(),
                                                    "Data Berhasil Dimasukkan", Toast.LENGTH_SHORT).show();
                                            SharedPrefManager.clearDataUlp(itemView.getContext());
                                            Fragment fragmentt = new UlpFragment();
                                            FragmentManager fragmentManager = ((FragmentActivity)itemView.getContext()).getSupportFragmentManager();
                                            fragmentManager.beginTransaction().replace(R.id.drawer, fragmentt);
//                                            ups.changeFragment();
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
                            builder.setTitle("UPS");
                            builder.setMessage("Anda yakin ingin menghapus : "
                                    + barang.getNama() +" "+barang.getDaya()+"?");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yakin", (dialog, which) -> {
                                DatabaseReference referencee = FirebaseDatabase.getInstance().getReference("user/UPS");
                                referencee.child(barang.getKey())
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
                itemView.setOnClickListener(v -> Toast.makeText(itemView.getContext(),
                        "Maaf Anda Bukanlah Admin"
                        ,Toast.LENGTH_SHORT).show());
            }
        }
    }
}