package unhas.informatics.monitoringapp.ui.ugb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import unhas.informatics.monitoringapp.Model.DataPelanggan;
import unhas.informatics.monitoringapp.Model.Ugb;
import unhas.informatics.monitoringapp.R;
import unhas.informatics.monitoringapp.ui.genset.AdapterGenset;
import unhas.informatics.monitoringapp.ui.ulp.Ulp;

public class UgbFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_ugb, container, false);

    }
    String nama;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView;
        AdapterUgb adapterUgb;
        AVLoadingIndicatorView loading;
        int myInt = 10;
        List<Ugb> ugbs = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/UGB");
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            myInt = bundle.getInt("id", 10);
        }




        recyclerView = view.findViewById(R.id.rvUgb);
        loading = view.findViewById(R.id.loadingView);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);

        adapterUgb = new AdapterUgb(ugbs);

        adapterUgb.notifyDataSetChanged();
        recyclerView.setLayoutManager(manager);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ugbs.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        Ugb ugb = data.getValue(Ugb.class);
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
                        Toast.makeText(getContext(), "NoDataSet", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onClick(View view, int position) {
                        Toast.makeText(getContext(),nama, Toast.LENGTH_SHORT).show();
                        Ugb model = new Ugb();
                        String id = model.getNama();
                        Ulp mfragment = new Ulp();
                        Bundle bundle = new Bundle();
                        bundle.putString("id",id);
                        mfragment.setArguments(bundle); //data being send to SecondFragment
                        Navigation.findNavController(view).navigate(R.id.action_navigation_ugb_to_navigation_ulp, bundle);
                    }
                }));
    }

}