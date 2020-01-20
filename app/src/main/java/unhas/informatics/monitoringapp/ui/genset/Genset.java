package unhas.informatics.monitoringapp.ui.genset;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import unhas.informatics.monitoringapp.AviLoading.AVLoadingIndicatorView;
import unhas.informatics.monitoringapp.Model.Barang;
import unhas.informatics.monitoringapp.R;

public class Genset extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_genset, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView;
        AdapterGenset adapterGenset;
        AVLoadingIndicatorView loading;
        List<Barang> barangs = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/Genset");

        recyclerView = view.findViewById(R.id.rvgenset);
        loading = view.findViewById(R.id.loadingView);
//    Datar    RecyclerView.LayoutManager manager = new  GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manager = new  GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);

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
}
