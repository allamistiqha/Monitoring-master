package unhas.informatics.monitoringapp.ui.ups;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import unhas.informatics.monitoringapp.AviLoading.AVLoadingIndicatorView;
import unhas.informatics.monitoringapp.Model.Barang;
import unhas.informatics.monitoringapp.Model.Ups;
import unhas.informatics.monitoringapp.R;
import unhas.informatics.monitoringapp.ui.genset.AdapterGenset;

public class UpsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ups, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView;
        AdapterUps adapterUps;
        AVLoadingIndicatorView loading;
        List<Ups> upss = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/UPS");

        recyclerView = view.findViewById(R.id.rvUps);
        loading = view.findViewById(R.id.loadingView);
//    Datar    RecyclerView.LayoutManager manager = new  GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);

        adapterUps = new AdapterUps(upss);

        adapterUps.notifyDataSetChanged();
        recyclerView.setLayoutManager(manager);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                upss.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        Ups ugb = data.getValue(Ups.class);
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

}