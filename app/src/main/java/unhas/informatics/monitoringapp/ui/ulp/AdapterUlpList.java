package unhas.informatics.monitoringapp.ui.ulp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import unhas.informatics.monitoringapp.Model.Barang;
import unhas.informatics.monitoringapp.Model.DataPelanggan;
import unhas.informatics.monitoringapp.Model.UlpPelanggan;
import unhas.informatics.monitoringapp.Preference.SharedPrefManager;
import unhas.informatics.monitoringapp.R;
import unhas.informatics.monitoringapp.ui.ugb.RvClickListener;

public class AdapterUlpList extends RecyclerView.Adapter<AdapterUlpList.BindUlp> {

    private List<DataPelanggan> takalars;
    AdapterUlpList(List<DataPelanggan> takalars) {
        this.takalars = takalars;
    }

    @NonNull
    @Override
    public BindUlp onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BindUlp(LayoutInflater.from(parent.getContext()).inflate(R.layout.data_ulp, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BindUlp holder, int position) {
        holder.barangPinjaman(takalars.get(position));
    }

    @Override
    public int getItemCount() {
        return takalars.size();
    }

    class BindUlp extends RecyclerView.ViewHolder {
        TextView nama, tanggal, status, daya;
        Button delete, tambah;
        RecyclerView rvChild;

        BindUlp(@NonNull View itemView) {
            super(itemView);
        }

        void barangPinjaman(DataPelanggan ulp) {
            nama = itemView.findViewById(R.id.namaUlp);
            tanggal = itemView.findViewById(R.id.tglPemakaianUlp);
            status = itemView.findViewById(R.id.statusPelangganUlp);
            daya = itemView.findViewById(R.id.dayah);
            delete = itemView.findViewById(R.id.delete);
            tambah =  itemView.findViewById(R.id.tambahBarang);

            nama.setText(ulp.getEvent());
            tanggal.setText(ulp.getTanggal());
            status.setText(ulp.getStatus());
            daya.setText(ulp.getDaya());

            List<UlpPelanggan> ulpPelanggan = new ArrayList<>();
            AdapterUlpListDalam adapterUlpListDalam = new AdapterUlpListDalam(ulpPelanggan);
            rvChild = itemView.findViewById(R.id.rvChild);
            rvChild.setVisibility(View.VISIBLE);
            rvChild.setHasFixedSize(true);
            DatabaseReference referenceUlp = FirebaseDatabase
                    .getInstance().getReference("user/Ulp/"+ulp.getUlp()+"/"+ulp.getKey());
            GridLayoutManager lm = new GridLayoutManager(rvChild.getContext(),3,
                    GridLayoutManager.VERTICAL,
                    false);
            rvChild.setLayoutManager(lm);
            rvChild.addOnItemTouchListener(new RvClickListener(
                    itemView.getContext(),rvChild,
                    new RvClickListener.OnTouchActionListener() {
                        @Override
                        public void onLeftSwipe(View view, int position) {

                        }
                        @Override
                        public void onRightSwipe(View view, int position) {

                        }
                        @Override
                        public void onClick(View view, int position) {
                            if (rvChild != null){
                                if (SharedPrefManager.getRegisteredStatus(itemView.getContext()).equals("Admin")) {
                                    view.setOnClickListener(v -> {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                                        builder.setTitle("ULP");
                                        builder.setMessage("Apakah Barang Sudah Dikembalikan : "
                                                +ulpPelanggan.get(position).getNama()
                                                +" "+ulpPelanggan.get(position).getDaya()+"?");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("Sudah", (dialog, which) -> {
                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user/"+ulpPelanggan
                                                    .get(position).getNama());
                                            String key = ref.push().getKey();
                                            Barang model = new Barang(ulpPelanggan
                                                    .get(position).getNama(),ulpPelanggan
                                                    .get(position).getDaya(),key);
                                            ref.child(model.getKey())
                                                    .setValue(model)
                                                    .addOnCompleteListener(task -> Toast.makeText(itemView.getContext(),
                                                            "Data Berhasil Dikembalikan", Toast.LENGTH_SHORT).show()
                                                    );
                                            DatabaseReference reference = FirebaseDatabase.getInstance()
                                                    .getReference("user/Ulp/"+ulp.getUlp()+"/"+ulp.getKey());
                                            reference.child(ulpPelanggan.get(position).getKey())
                                                    .removeValue()
                                                    .addOnCompleteListener(task ->
                                                            Toast.makeText(itemView.getContext(),
                                                                    "Barang Dikembalikan", Toast.LENGTH_SHORT).show()
                                                    );
                                            Navigation.findNavController(view).navigate(R.id.action_navigation_ulp_self);
                                        });
                                        builder.setNegativeButton("Belum", (dialog, which) -> {
                                            dialog.cancel();
                                        });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    });
                                }else{
                                    itemView.setOnClickListener(v -> Toast.makeText(itemView.getContext(),
                                            "Maaf Anda Bukanlah Admin"
                                            ,Toast.LENGTH_SHORT).show());
                                }
                            }else {
                                Toast.makeText(itemView.getContext(), "Tidak Ada Data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }));
            adapterUlpListDalam.notifyDataSetChanged();
            referenceUlp.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ulpPelanggan.clear();
                    if (dataSnapshot.exists()){
                        for (DataSnapshot snap : dataSnapshot.getChildren()){
                            UlpPelanggan ulpPelanggan1 = snap.getValue(UlpPelanggan.class);
                            if (snap != null){
                                ulpPelanggan.add(ulpPelanggan1);
                            }
                        }
                        try {
                            rvChild.setAdapter(new AdapterUlpListDalam(ulpPelanggan));
                        }catch (Exception e){
                            Log.e("Error", Objects.requireNonNull(e.getMessage()));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Error", databaseError.getMessage());
                }
            });
            if (SharedPrefManager.getRegisteredStatus(itemView.getContext()).equals("Admin")) {
                delete.setOnClickListener(v -> {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/Data_peminjam");
                    reference.child(ulp.getKey())
                            .removeValue()
                            .addOnCompleteListener(task ->
                                    Toast.makeText(itemView.getContext(), "Data Deleted", Toast.LENGTH_SHORT).show()
                            );
                    for (int a = 0; a < ulpPelanggan.size() ; a++){
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user/"+ulpPelanggan
                                .get(a).getNama());
                        String key = ref.push().getKey();
                        Barang model = new Barang(ulpPelanggan
                                .get(a).getNama(),ulpPelanggan
                                .get(a).getDaya(),key);
                        ref.child(model.getKey())
                                .setValue(model)
                                .addOnCompleteListener(task -> Toast.makeText(itemView.getContext(),
                                        "Data Berhasil Dikembalikan", Toast.LENGTH_SHORT).show()
                                );
                        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("user/Ulp/"+ulp.getUlp());
                        ref2.child(ulp.getKey())
                                .removeValue();

                        referenceUlp.child(ulp.getKey())
                                .removeValue()
                                .addOnCompleteListener(task ->
                                        Toast.makeText(itemView.getContext(),
                                                "Data Deleted", Toast.LENGTH_SHORT).show()
                                );
                    }
                    Navigation.findNavController(v).navigate(R.id.action_navigation_ulp_self);
                });
                tambah.setOnClickListener(v -> {
                    showPopUp(v, ulp);
                });
            }
            else{
                delete.setVisibility(View.GONE);
                tambah.setVisibility(View.GONE);
            }
        }
        private void showPopUp(View v, DataPelanggan ulp) {
            PopupMenu popupMenu = new PopupMenu(itemView.getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.ulp_tambah_data, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.action_UGB:
                        SharedPrefManager.setNamePelanggan(itemView.getContext(),ulp.getKey());
                        SharedPrefManager.setTngglPemakaian(itemView.getContext(), this.tanggal.getText().toString());
                        SharedPrefManager.setStatusPelanggan(itemView.getContext(),this.status.getText().toString());
                        SharedPrefManager.setStatusUlp(itemView.getContext(),"Ulp");
                        SharedPrefManager.setUlp(itemView.getContext(),ulp.getUlp());
                        SharedPrefManager.setId(itemView.getContext(),ulp.getKey());
                        Navigation.findNavController(itemView).navigate(R.id.action_navigation_ulp_to_navigation_ugb);
                        break;
                    case R.id.action_UPS:
                        SharedPrefManager.setNamePelanggan(itemView.getContext(),ulp.getKey());
                        SharedPrefManager.setTngglPemakaian(itemView.getContext(), this.tanggal.getText().toString());
                        SharedPrefManager.setStatusPelanggan(itemView.getContext(),this.status.getText().toString());
                        SharedPrefManager.setStatusUlp(itemView.getContext(),"Ulp");
                        SharedPrefManager.setUlp(itemView.getContext(),ulp.getUlp());
                        SharedPrefManager.setId(itemView.getContext(),ulp.getKey());
                        Navigation.findNavController(itemView).navigate(R.id.action_navigation_ulp_to_navigation_ups);
                        break;
                    case R.id.action_Genset:
                        SharedPrefManager.setNamePelanggan(itemView.getContext(),ulp.getKey());
                        SharedPrefManager.setTngglPemakaian(itemView.getContext(), this.tanggal.getText().toString());
                        SharedPrefManager.setStatusPelanggan(itemView.getContext(),this.status.getText().toString());
                        SharedPrefManager.setStatusUlp(itemView.getContext(),"Ulp");
                        SharedPrefManager.setUlp(itemView.getContext(),ulp.getUlp());
                        SharedPrefManager.setId(itemView.getContext(),ulp.getKey());
                        Navigation.findNavController(itemView).navigate(R.id.action_navigation_ulp_to_navigation_genset);
                        break;
                }
                return false;
            });
            popupMenu.show();
        }
    }
}


