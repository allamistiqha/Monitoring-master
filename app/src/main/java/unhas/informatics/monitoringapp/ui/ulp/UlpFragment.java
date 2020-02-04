package unhas.informatics.monitoringapp.ui.ulp;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import unhas.informatics.monitoringapp.AviLoading.AVLoadingIndicatorView;
import unhas.informatics.monitoringapp.Model.DataPelanggan;
import unhas.informatics.monitoringapp.R;

public class UlpFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ulp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView rv1,rv2,rv3,rv4,rv5,rv6;
        AdapterUlpList adapterUlpList,adapterUlpList1,adapterUlpList2,adapterUlpList3,adapterUlpList4,adapterUlpList5;
        AVLoadingIndicatorView loadingP, loadingMt,loadingMl,loadingSm,loadingT,loadingKb;

        rv4 = view.findViewById(R.id.rvTakalar);
        rv3 = view.findViewById(R.id.rvPanakukang);
        rv6 = view.findViewById(R.id.rvSungguminasa);
        rv1 = view.findViewById(R.id.rvKalebajeng);
        rv5 = view.findViewById(R.id.rvMattoanging);
        rv2 = view.findViewById(R.id.rvMalino);

        loadingP  = view.findViewById(R.id.loadingViewPanakukang);
        loadingMt = view.findViewById(R.id.loadingViewMattoanging);
        loadingSm = view.findViewById(R.id.loadingViewSungguminasa);
        loadingT = view.findViewById(R.id.loadingViewTakalar);
        loadingMl = view.findViewById(R.id.loadingViewMalino);
        loadingKb = view.findViewById(R.id.loadingViewKaleBajeng);

        rv5.setLayoutManager(new GridLayoutManager(getActivity(),
                1,
                GridLayoutManager.HORIZONTAL,
                false));
        rv1.setLayoutManager(new GridLayoutManager(getActivity(),
                1,
                GridLayoutManager.HORIZONTAL,
                false));
        rv6.setLayoutManager(new GridLayoutManager(getActivity(),
                1,
                GridLayoutManager.HORIZONTAL,
                false));
        rv3.setLayoutManager(new GridLayoutManager(getActivity(),
                1,
                GridLayoutManager.HORIZONTAL,
                false));
        rv4.setLayoutManager(new GridLayoutManager(getActivity(),
                1,
                GridLayoutManager.HORIZONTAL,
                false));
        rv2.setLayoutManager(new GridLayoutManager(getActivity(),
                1,
                GridLayoutManager.HORIZONTAL,
                false));

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/Data_peminjam/");

        Query referenceTkl = reference.orderByChild("ulp").equalTo("ULP Takalar");
        Query referenceP = reference.orderByChild("ulp").equalTo("ULP Panakukang");
        Query referenceSm = reference.orderByChild("ulp").equalTo("ULP Sungguminasa");
        Query referenceKb = reference.orderByChild("ulp").equalTo("ULP Kalebajeng");
        Query referenceMt = reference.orderByChild("ulp").equalTo("ULP Mattoanging");
        Query referenceMalino = reference.orderByChild("ulp").equalTo("ULP Malino");

        List<DataPelanggan> ulpPelanggan = new ArrayList<>();
        List<DataPelanggan> ulpPelanggan1 = new ArrayList<>();
        List<DataPelanggan> ulpPelanggan2 = new ArrayList<>();
        List<DataPelanggan> ulpPelanggan3 = new ArrayList<>();
        List<DataPelanggan> ulpPelanggan4 = new ArrayList<>();
        List<DataPelanggan> ulpPelanggan5 = new ArrayList<>();

        adapterUlpList = new AdapterUlpList(ulpPelanggan);
        adapterUlpList1 = new AdapterUlpList(ulpPelanggan1);
        adapterUlpList2 = new AdapterUlpList(ulpPelanggan2);
        adapterUlpList3 = new AdapterUlpList(ulpPelanggan3);
        adapterUlpList4 = new AdapterUlpList(ulpPelanggan4);
        adapterUlpList5 = new AdapterUlpList(ulpPelanggan5);

        adapterUlpList.notifyDataSetChanged();
        referenceP.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ulpPelanggan.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        DataPelanggan dataPelanggan = snapshot.getValue(DataPelanggan.class);
                        if (dataPelanggan != null){
                            ulpPelanggan.add(dataPelanggan);
                        }else {
                            rv3.setAdapter(null);
                        }
                    }
                    rv3.setAdapter(adapterUlpList);
                    loadingP.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error", databaseError.getMessage());
            }
        });
        referenceMt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ulpPelanggan1.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        DataPelanggan dataPelanggan = snapshot.getValue(DataPelanggan.class);
                        ulpPelanggan1.add(dataPelanggan);
                    }
                    adapterUlpList1.notifyDataSetChanged();
                    rv5.setAdapter(adapterUlpList1);
                    loadingMt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error", databaseError.getMessage());
            }
        });
        referenceSm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ulpPelanggan2.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        DataPelanggan dataPelanggan = snapshot.getValue(DataPelanggan.class);
                        ulpPelanggan2.add(dataPelanggan);
                    }
                    adapterUlpList2.notifyDataSetChanged();
                    rv6.setAdapter(adapterUlpList2);
                    loadingSm.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error", databaseError.getMessage());
            }
        });
        referenceTkl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ulpPelanggan3.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        DataPelanggan dataPelanggan = snapshot.getValue(DataPelanggan.class);
                        ulpPelanggan3.add(dataPelanggan);
                    }
                    adapterUlpList3.notifyDataSetChanged();
                    rv4.setAdapter(adapterUlpList3);
                    loadingT.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error", databaseError.getMessage());
            }
        });
        referenceMalino.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ulpPelanggan4.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snap : dataSnapshot.getChildren()){
                        DataPelanggan dataPelanggan = snap.getValue(DataPelanggan.class);
                        ulpPelanggan4.add(dataPelanggan);
                    }
                    adapterUlpList4.notifyDataSetChanged();
                    rv2.setAdapter(adapterUlpList4);
                    loadingMl.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error", databaseError.getMessage());
            }
        });
        referenceKb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ulpPelanggan5.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        DataPelanggan dataPelanggan = snapshot.getValue(DataPelanggan.class);
                        ulpPelanggan5.add(dataPelanggan);
                    }
                    adapterUlpList5.notifyDataSetChanged();
                    rv1.setAdapter(adapterUlpList5);
                    loadingKb.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error", databaseError.getMessage());
            }
        });
    }
}




