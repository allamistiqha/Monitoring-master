package unhas.informatics.monitoringapp.ui.genset;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import unhas.informatics.monitoringapp.AviLoading.AVLoadingIndicatorView;
import unhas.informatics.monitoringapp.Model.Barang;
import unhas.informatics.monitoringapp.Preference.SharedPrefManager;
import unhas.informatics.monitoringapp.R;

public class Genset extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_genset, container, false);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView;
        AdapterGenset adapterGenset;
        AVLoadingIndicatorView loading;
        FloatingActionButton fbt_genset;
        List<Barang> barangs = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/Genset");

        fbt_genset = view.findViewById(R.id.tambahDataGenset);
        recyclerView = view.findViewById(R.id.rvgenset);
        loading = view.findViewById(R.id.loadingView);

        if (SharedPrefManager.getRegisteredStatus(getContext()).equals("Admin")){
            fbt_genset.setOnClickListener(v -> {
                DialogForm();
            });
        }else {
            fbt_genset.setVisibility(View.GONE);
        }
        RecyclerView.LayoutManager manager = new  GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);

        adapterGenset = new AdapterGenset(barangs);

        adapterGenset.notifyDataSetChanged();
        recyclerView.setLayoutManager(manager);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                barangs.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        Barang barang = data.getValue(Barang.class);
                        barangs.add(barang);
                    }
                    recyclerView.setAdapter(adapterGenset);
                    loading.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Genset", databaseError.getMessage());
            }
        });
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

        txtNama.setText("Genset");
            String nama = txtNama.getText().toString();
            dialog.setPositiveButton("SUBMIT", (dialog, which) -> {
                    DatabaseReference ref;
                    ref = FirebaseDatabase.getInstance().getReference("user/Genset");
                    String idUser = ref.push().getKey();
                    Barang model = new Barang(nama,txtDaya.getText().toString(),idUser);
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

    @Override
    public void onDetach() {
        super.onDetach();
        SharedPrefManager.clearDataUlp(getContext());
    }
}
