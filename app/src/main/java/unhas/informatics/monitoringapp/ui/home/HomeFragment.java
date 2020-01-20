package unhas.informatics.monitoringapp.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shagi.materialdatepicker.date.DatePickerFragmentDialog;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import unhas.informatics.monitoringapp.Model.DataPelanggan;
import unhas.informatics.monitoringapp.Preference.SharedPrefManager;
import unhas.informatics.monitoringapp.R;


public class HomeFragment extends Fragment{
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    private  EditText txt_nama = null, txt_phone, txt_status, txt_daya, txt_lokasi,txt_tanggal;
    private String nama, daya, phone, tanggal,lokasi,status;
    private Context mContext;
    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        FloatingActionButton btnAddData;
        btnAddData = view.findViewById(R.id.tambahData);
        btnAddData.setVisibility(View.VISIBLE);
        if (SharedPrefManager.getRegisteredStatus(mContext).equals("admin")){
            btnAddData.setOnClickListener(v -> DialogForm());
        }else {
            btnAddData.setVisibility(View.GONE);
        }
        RecyclerView recyclerView;
        AdapterHome adapterHome;

        List<DataPelanggan> homes = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/Data_peminjam/");
        recyclerView = view.findViewById(R.id.rvHome);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        adapterHome = new AdapterHome(homes,mContext);

        adapterHome.notifyDataSetChanged();
        recyclerView.setLayoutManager(manager);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                homes.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        DataPelanggan dataPelanggan = data.getValue(DataPelanggan.class);
                        homes.add(dataPelanggan);
                    }
                    recyclerView.setAdapter(adapterHome);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Data pelanggan", databaseError.getMessage());
            }
        });


    }
                    private void DialogForm()  {
                    dialog = new AlertDialog.Builder(mContext);
                    inflater = getLayoutInflater();
                    dialogView = inflater.inflate(R.layout.data_pelanggan, null);
                    dialog.setView(dialogView);
                    dialog.setCancelable(true);

                    txt_status =dialogView.findViewById(R.id.etStatus);
                    txt_nama    =  dialogView.findViewById(R.id.etNamaPelanggan);
                    txt_phone    = dialogView.findViewById(R.id.etPhone);
                    txt_daya  =  dialogView.findViewById(R.id.etDaya);
                    txt_lokasi =  dialogView.findViewById(R.id.etLokasi);
                    txt_tanggal = dialogView.findViewById(R.id.etTgl);

                    txt_tanggal.setOnClickListener(v -> {
                        selectDate(this, txt_tanggal);
                    });
                    txt_lokasi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showPopupMenu(v);
                            }
                            private void showPopupMenu(View v) {
                            android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(getContext(), v);
                            //menampilkan layout menu_popup.xml
                            popupMenu.getMenuInflater().inflate(R.menu.menu_lokasi, popupMenu.getMenu());
                            //aksi klik
                            popupMenu.setOnMenuItemClickListener(item -> {
                                switch (item.getItemId()) {
                                    case R.id.ulp_panakukang:
                                        txt_lokasi.setText("ULP Panakukang");
                                        Toast.makeText(getContext(),"Anda Memilih Menu Profile", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.ulp_mattoanging:
                                        Toast.makeText(getContext(),"Anda Memilih Menu Pesan", Toast.LENGTH_SHORT).show();
                                        txt_lokasi.setText("ULP Mottoanging");
                                        break;
                                    case R.id.ulp_sungguminasa:
                                        Toast.makeText(getContext(),"Anda Memilih Menu Pengaturan", Toast.LENGTH_SHORT).show();
                                        txt_lokasi.setText("ULP Sungguminasa");
                                        break;
                                    case R.id.ulp_takalar:
                                        Toast.makeText(getContext(),"Anda Memilih Ulp Takalar", Toast.LENGTH_SHORT).show();
                                        txt_lokasi.setText("ULP Takalat");
                                        break;
                                    case R.id.ulp_malino:
                                        txt_lokasi.setText("ULP Malino");
                                        Toast.makeText(getContext(),"Anda Memilih Menu Pesan", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.ulp_kalebajeng:
                                        txt_lokasi.setText("ULP Kalebajeng");
                                        Toast.makeText(getContext(),"Anda Memilih Menu Pengaturan", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                                return false;
                            });
                            popupMenu.show();
                        }
                    });

                    dialog.setPositiveButton("SUBMIT", (dialog, which) -> {
                        DatabaseReference ref;
                        nama    = txt_nama.getText().toString();
                        phone    = txt_phone.getText().toString();
                        status  = txt_status.getText().toString();
                        tanggal = txt_tanggal.getText().toString();
                        daya = txt_daya.getText().toString();
                        lokasi = txt_lokasi.getText().toString();
                                int errorStep = 0;
                                if (txt_nama.length() < 1) {
                                    errorStep++;
                                    txt_nama.setError("Provide a task name.");
                                }

                                if (txt_tanggal.length() < 1) {
                                    errorStep++;
                                    txt_tanggal.setError("Provide a specific date");
                                }
                                if (errorStep == 0) {

                                    ref = FirebaseDatabase.getInstance().getReference("user/Data_peminjam");
                                    String idUser = ref.push().getKey();
                                    DataPelanggan model = new DataPelanggan(status,nama,phone,daya,lokasi,tanggal,idUser);
                                    ref.child(model.getKey())
                                            .setValue(model)
                                            .addOnCompleteListener(task -> Toast.makeText(getContext(),
                                                    "Data Berhasil Ditambahkan",
                                                    Toast.LENGTH_SHORT).show());
//
                                }

                        dialog.dismiss();
                    });

                    dialog.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
                    dialog.show();

                }

    private void selectDate(HomeFragment h, EditText txt_tanggal) {
            txt_tanggal.setOnClickListener(v -> {
                @SuppressLint("SetTextI18n")
                DatePickerFragmentDialog dialoga = DatePickerFragmentDialog.newInstance((view, year, monthOfYear, dayOfMonth) -> {
                    String month,day;
                    if(monthOfYear < 10){
                        month = "0"+ monthOfYear;
                    }else{
                        month = String.valueOf(monthOfYear);
                    }
                    if(dayOfMonth < 10){
                        day = "0"+dayOfMonth;
                    }else{
                        day = String.valueOf(dayOfMonth);
                    }
                    txt_tanggal.setText(year+"-"+month+"-"+day);
                });
                assert h.getFragmentManager() != null;
                dialoga.show(h.getFragmentManager(), "Date_Picker");
            });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.pencarian, menu);
        SearchManager search = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);
        android.widget.SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        assert search != null;
        searchView.setSearchableInfo(search.getSearchableInfo(getActivity().getComponentName()));
        super.onCreateOptionsMenu(menu, inflater);
    }

}