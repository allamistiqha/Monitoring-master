package unhas.informatics.monitoringapp.ui.ulp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import unhas.informatics.monitoringapp.Model.DataPelanggan;
import unhas.informatics.monitoringapp.Model.UlpMalino;
import unhas.informatics.monitoringapp.Model.UlpPanakukang;
import unhas.informatics.monitoringapp.Model.UlpTakalar;
import unhas.informatics.monitoringapp.R;
import unhas.informatics.monitoringapp.ui.ulp.RvClickListener;


public class Ulp extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ulp, container, false);

//        Bundle bundle=getArguments();
//        String txtv;
//        (String.valueOf(bundle.getString("id")));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView,recyclerViewMalino,recyclerViewPanakukang,recyclerViewTakalar;
        AdapterUlpKalebajeng adapterUlpKalebajeng;
        AdapterUlpMalino adapterUlpMalino;
        AdapterUlpPanakukang adapterUlpPanakukang;
        AdapterUlpTakalar adapterUlpTakalar;
        Bundle bundle = this.getArguments();
        List<unhas.informatics.monitoringapp.Model.Ulp> ulps = new ArrayList<>();
        List<UlpMalino> ulpMalinos = new ArrayList<>();
        List<UlpPanakukang> ulpPanakukangs = new ArrayList<>();
        List<UlpTakalar> ulpTakalars = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/Ulp/Kalebajeng");
    recyclerView = view.findViewById(R.id.rvKalebajeng);
    RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());

    adapterUlpKalebajeng = new AdapterUlpKalebajeng(ulps);

        adapterUlpKalebajeng.notifyDataSetChanged();
        recyclerView.setLayoutManager(manager);

        reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            ulps.clear();
            if (dataSnapshot.exists()){
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    unhas.informatics.monitoringapp.Model.Ulp ulp = data.getValue(unhas.informatics.monitoringapp.Model.Ulp.class);
                    ulps.add(ulp);
                }
                recyclerView.setAdapter(adapterUlpKalebajeng);

            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e("ULP bajeng", databaseError.getMessage());
        }
    });

        DatabaseReference panakukangreference = FirebaseDatabase.getInstance().getReference("user/Ulp/Panakukang");
        recyclerViewPanakukang = view.findViewById(R.id.rvPanakukang);
        RecyclerView.LayoutManager malinomanager = new LinearLayoutManager(getActivity());

        adapterUlpPanakukang = new AdapterUlpPanakukang(ulpPanakukangs);

        adapterUlpPanakukang.notifyDataSetChanged();
        recyclerViewPanakukang.setLayoutManager(malinomanager);

        panakukangreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ulpMalinos.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        UlpPanakukang ulpPanakukang = data.getValue(UlpPanakukang.class);
                        ulpPanakukangs.add(ulpPanakukang);
                    }
                    recyclerViewPanakukang.setAdapter(adapterUlpPanakukang);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ULP Panakukang", databaseError.getMessage());
            }
        });
        DatabaseReference malinoreference = FirebaseDatabase.getInstance().getReference("user/Ulp/Malino");
        recyclerViewMalino = view.findViewById(R.id.rvMalino);
        RecyclerView.LayoutManager panakukangmanajer = new GridLayoutManager(
                getActivity(),1, GridLayoutManager.HORIZONTAL, false
        );

        adapterUlpMalino = new AdapterUlpMalino(ulpMalinos);

        adapterUlpMalino.notifyDataSetChanged();
        recyclerViewMalino.setLayoutManager(panakukangmanajer);

        malinoreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ulpMalinos.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        UlpMalino ulpMalino = data.getValue(UlpMalino.class);
                        ulpMalinos.add(ulpMalino);
                    }
                    recyclerViewMalino.setAdapter(adapterUlpMalino);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ULP Malino", databaseError.getMessage());
            }
        });

        DatabaseReference takalarreference = FirebaseDatabase.getInstance().getReference("user/Ulp/Takalar");
        recyclerViewTakalar = view.findViewById(R.id.rvTakalar);
        RecyclerView.LayoutManager takalarmanajer = new GridLayoutManager(getActivity(),
                1,GridLayoutManager.HORIZONTAL,  false);
        adapterUlpTakalar= new AdapterUlpTakalar(ulpTakalars);
        adapterUlpTakalar.notifyDataSetChanged();
        recyclerViewTakalar.setLayoutManager(takalarmanajer);
        takalarreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ulpTakalars.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        UlpTakalar ulpTakalar = data.getValue(UlpTakalar.class);
                        ulpTakalars.add(ulpTakalar);
                    }
                    recyclerViewTakalar.setAdapter(adapterUlpTakalar);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ULP Takalar", databaseError.getMessage());
            }
        });

        recyclerView.addOnItemTouchListener(new unhas.informatics.monitoringapp.ui.ulp.RvClickListener(
                getContext(),recyclerView,
                new unhas.informatics.monitoringapp.ui.ulp.RvClickListener.OnTouchActionListener() {
                    @Override
                    public void onLeftSwipe(View view, int position) {

                    }
                    @Override
                    public void onRightSwipe(View view, int position) {
                        Toast.makeText(getContext(), "NoDataSet", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onClick(View view, int position) {
                        String kirim = String.valueOf(bundle.getString("id"));
                        Toast.makeText(getContext(),kirim , Toast.LENGTH_SHORT).show();
                    }
                }));

    }

    }




