package unhas.informatics.monitoringapp.ui.ups;

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
import androidx.navigation.NavController;
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
import unhas.informatics.monitoringapp.Model.Ups;
import unhas.informatics.monitoringapp.Preference.SharedPrefManager;
import unhas.informatics.monitoringapp.R;

public class UpsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ups, container, false);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView;
        AdapterUps adapterUps;
        AVLoadingIndicatorView loading;
        FloatingActionButton ftb_Ups;
        List<Barang> upss = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/UPS");
        ftb_Ups = view.findViewById(R.id.tambahDataUps);
        if (SharedPrefManager.getRegisteredStatus(getContext()).equals("Admin")){
            ftb_Ups.setOnClickListener(v -> {
                DialogForm();
            });
        }else {
            ftb_Ups.setVisibility(View.GONE);
        }
        recyclerView = view.findViewById(R.id.rvUps);
        loading = view.findViewById(R.id.loadingViewUps);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);

        adapterUps = new AdapterUps(upss, this);

        adapterUps.notifyDataSetChanged();
        recyclerView.setLayoutManager(manager);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                upss.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        Barang ugb = data.getValue(Barang.class);
                        upss.add(ugb);
                    }
                    recyclerView.setAdapter(adapterUps);
                    loading.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("UPS", databaseError.getMessage());
            }
        });
    }
    String nama,idUser;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    EditText txtNama, txtDaya;
    View dialogView;
    private void DialogForm() {
        dialog = new AlertDialog.Builder(getContext());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.tambah_data_daya, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        txtNama = dialogView.findViewById(R.id.etNamaDaya);
        txtDaya = dialogView.findViewById(R.id.etDayaa);

        txtNama.setText("UPS");
        nama = txtNama.getText().toString();

        dialog.setPositiveButton("SUBMIT", (dialog, which) -> {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user/UPS");
            idUser = ref.push().getKey();
            Ups model = new Ups(nama,txtDaya.getText().toString(),idUser);
            ref.child(model.getKey())
                    .setValue(model)
                    .addOnCompleteListener(task -> Toast.makeText(getContext(),
                            "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show());
            dialog.dismiss();
        });
        dialog.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
        dialog.show();
    }

    public void changeFragment() {
        Navigation.findNavController(Objects.requireNonNull(getView())).navigate(R.id.action_navigation_ups_to_navigation_ulp);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        SharedPrefManager.clearDataUlp(getContext());
    }
}