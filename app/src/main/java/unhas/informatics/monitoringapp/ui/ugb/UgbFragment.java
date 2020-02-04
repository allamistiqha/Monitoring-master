package unhas.informatics.monitoringapp.ui.ugb;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import unhas.informatics.monitoringapp.AviLoading.AVLoadingIndicatorView;
import unhas.informatics.monitoringapp.Model.Barang;
import unhas.informatics.monitoringapp.Model.Ugb;
import unhas.informatics.monitoringapp.Preference.SharedPrefManager;
import unhas.informatics.monitoringapp.R;

public class UgbFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_ugb, container, false);

    }
    String nama;
    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView;
        AdapterUgb adapterUgb;
        FloatingActionButton fbt_Ugb;
        AVLoadingIndicatorView loading;
        List<Barang> ugbs = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/UGB");

        recyclerView = view.findViewById(R.id.rvUgb);
        loading = view.findViewById(R.id.loadingViewUgb);
        fbt_Ugb = view.findViewById(R.id.tambahDataUgb);

        if (SharedPrefManager.getRegisteredStatus(getContext()).equals("Admin")){
            fbt_Ugb.setOnClickListener(v -> {
                DialogForm();
            });
        }else {
            fbt_Ugb.setVisibility(View.GONE);
        }

        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);

        adapterUgb = new AdapterUgb(ugbs);

        adapterUgb.notifyDataSetChanged();
        recyclerView.setLayoutManager(manager);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ugbs.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        Barang ugb = data.getValue(Barang.class);
                        nama = ugb.getDaya();
                        ugbs.add(ugb);
                    }
                    recyclerView.setAdapter(adapterUgb);
                    loading.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("UGB", databaseError.getMessage());
            }
        });

        recyclerView.addOnItemTouchListener(new RvClickListener(
                getContext(),recyclerView,
                new RvClickListener.OnTouchActionListener() {
                    @Override
                    public void onLeftSwipe(View view, int position) {
                    }
                    @Override
                    public void onRightSwipe(View view, int position) {
                    }
                    @Override
                    public void onClick(View view, int position) {
                        String alamatUlp = SharedPrefManager.getUlp(getContext());
                        if (SharedPrefManager.getRegisteredStatus(getContext()).equals("Admin")) {
                            if (SharedPrefManager.getStatusUlp(getContext()).equals("Ulp")){
                                if (!SharedPrefManager.getNamepelanggan(getContext()).isEmpty()){
                                    if (SharedPrefManager.getRegisteredStatus(getContext()).equals("Admin")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle("UGB");
                                        builder.setMessage("Anda yakin ingin memilih : "
                                                +ugbs.get(position).getNama()+" "+ugbs.get(position).getDaya()+"?");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("Yakin", (dialog, which) -> {
                                            loading.setVisibility(View.VISIBLE);
                                            Toast.makeText(getContext(), "Menyiapkan Data", Toast.LENGTH_SHORT).show();
                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/UGB");
                                            reference.child(ugbs.get(position).getKey()).removeValue();
                                            DatabaseReference referenceUlp = FirebaseDatabase.getInstance()
                                                    .getReference("user/Ulp/"+alamatUlp+"/"+
                                                            SharedPrefManager.getNamepelanggan(getContext()));
                                            String newKey  =  reference.push().getKey();
                                            Barang dataUlp = new Barang(
                                                    ugbs.get(position).getNama(),
                                                    ugbs.get(position).getDaya()
                                                    ,newKey
                                            );
                                            referenceUlp.child(newKey)
                                                    .setValue(dataUlp)
                                                    .addOnCompleteListener(task -> {
                                                        loading.setVisibility(View.GONE);
                                                        Toast.makeText(getContext(), "Data Berhasil Ditambahkan",
                                                                Toast.LENGTH_SHORT).show();
                                                        SharedPrefManager.clearDataUlp(getContext());
                                                        changeFragment();
                                                    });
                                        });
                                        builder.setNegativeButton("Batal", (dialog, which) -> {
                                            dialog.cancel();
                                        });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }else{
                                        Toast.makeText(getContext(),
                                                "anda adalah : "+SharedPrefManager.getRegisteredStatus(getContext())
                                                ,Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                }
                            }else {
                                if (!SharedPrefManager.getNamepelanggan(getContext()).isEmpty()){
                                    if (SharedPrefManager.getRegisteredStatus(getContext()).equals("Admin")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle("UGB");
                                        builder.setMessage("Anda yakin ingin memilih : "+ugbs.get(position).getNama()+" "+ugbs.get(position).getDaya()+"?");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("Yakin", (dialog, which) -> {
                                            loading.setVisibility(View.VISIBLE);
                                            Toast.makeText(getContext(), "Menyiapkan Data", Toast.LENGTH_SHORT).show();
                                            SharedPrefManager.setNamaBarang(getContext(),ugbs.get(position).getNama());
                                            SharedPrefManager.setDaya(getContext(),ugbs.get(position).getDaya());
                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/UGB");
                                            reference.child(ugbs.get(position).getKey()).removeValue();

                                            DatabaseReference referenceUlp = FirebaseDatabase.getInstance().getReference("user/Ulp/"+alamatUlp+"/"+
                                                    SharedPrefManager.getNamepelanggan(getContext()));
                                            String newKey  =  referenceUlp.push().getKey();
                                            Barang dataUlp = new Barang(
                                                    ugbs.get(position).getNama(),
                                                    ugbs.get(position).getDaya()
                                                    ,newKey
                                            );
                                            referenceUlp.child(newKey)
                                                    .setValue(dataUlp)
                                                    .addOnCompleteListener(task -> {
                                                        loading.setVisibility(View.GONE);
                                                        Toast.makeText(getContext(), "Data Berhasil Dimasukkan", Toast.LENGTH_SHORT).show();
                                                        SharedPrefManager.clearDataUlp(getContext());
                                                        changeFragment();
                                                    });
                                        });
                                        builder.setNegativeButton("Batal", (dialog, which) -> {
                                            dialog.cancel();
                                        });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }else{
                                        Toast.makeText(getContext(),
                                                "anda adalah : "+SharedPrefManager.getRegisteredStatus(getContext())
                                                ,Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("UGB");
                                    builder.setMessage("Anda yakin ingin menghapus : "
                                            + ugbs.get(position).getNama() +" "+ugbs.get(position).getDaya()+"?");
                                    builder.setCancelable(false);
                                    builder.setPositiveButton("Yakin", (dialog, which) -> {
                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/UGB");
                                        reference.child(ugbs.get(position).getKey()).removeValue()
                                                .addOnCompleteListener(task ->
                                                        Toast.makeText(getContext(), "Data Deleted", Toast.LENGTH_SHORT).show()
                                                );
                                    });
                                    builder.setNegativeButton("Batal", (dialog, which) -> {
                                        dialog.cancel();
                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                            }
                        }else {
                            Toast.makeText(getContext(), "Maaf Anda bukan Admin", Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
    }

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    private void DialogForm() {
        dialog = new AlertDialog.Builder(getContext());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.tambah_data_daya, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        EditText txtNama, txtDaya;
        txtNama = dialogView.findViewById(R.id.etNamaDaya);
        txtDaya = dialogView.findViewById(R.id.etDayaa);

        txtNama.setText("UGB");
            String nama = txtNama.getText().toString();

            dialog.setPositiveButton("SUBMIT", (dialog, which) -> {
                    DatabaseReference ref;
                    ref = FirebaseDatabase.getInstance().getReference("user/UGB");
                    String idUser = ref.push().getKey();
                    Ugb model = new Ugb(nama,txtDaya.getText().toString(),idUser);
                    ref.child(model.getKey())
                            .setValue(model)
                            .addOnCompleteListener(task -> Toast.makeText(getContext(),
                                    "Data Berhasil Ditambahkan",
                                    Toast.LENGTH_SHORT).show());
                    dialog.dismiss();
            });
        dialog.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
        dialog.show();
    }

    private void changeFragment() {
        Navigation.findNavController(Objects.requireNonNull(getView())).navigate(R.id.action_navigation_ugb_to_navigation_ulp);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        SharedPrefManager.clearDataUlp(getContext());
    }
}