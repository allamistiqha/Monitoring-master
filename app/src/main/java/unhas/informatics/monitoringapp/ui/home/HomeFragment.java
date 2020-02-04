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
import android.view.MenuItem;
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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import unhas.informatics.monitoringapp.AviLoading.AVLoadingIndicatorView;
import unhas.informatics.monitoringapp.Model.DataPelanggan;
import unhas.informatics.monitoringapp.Preference.SharedPrefManager;
import unhas.informatics.monitoringapp.R;


public class HomeFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    AVLoadingIndicatorView loading;
    private  EditText txt_nama = null, txt_phone, txt_ulp,txt_event,txt_status, txt_daya, txt_lokasi,txt_tanggal,txt_tanggalAkhir;
    private String nama, daya, phone, tanggal,ulp,lokasi,status, tangglAkhir, event;
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

    AdapterHome adapterHome;
    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        loading = view.findViewById(R.id.loadingViewHome);
        FloatingActionButton btnAddData;
        btnAddData = view.findViewById(R.id.tambahData);
        btnAddData.setVisibility(View.VISIBLE);
        if (SharedPrefManager.getRegisteredStatus(mContext).equals("Admin")){
            btnAddData.setOnClickListener(v -> DialogForm());
        }else {
            btnAddData.setVisibility(View.GONE);
        }
        RecyclerView recyclerView;

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
                    loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Data pelanggan", databaseError.getMessage());
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.pencarian, menu);
        SearchManager search = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);
        android.widget.SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        assert search != null;
        searchView.setSearchableInfo(search.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterHome.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterHome.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogForm()  {
         dialog = new AlertDialog.Builder(mContext);
         inflater = getLayoutInflater();
         dialogView = inflater.inflate(R.layout.data_pelanggan, null);
         dialog.setView(dialogView);
         dialog.setCancelable(true);

         txt_status  = dialogView.findViewById(R.id.etStatus);
         txt_event = dialogView.findViewById(R.id.etEvent);
         txt_nama    = dialogView.findViewById(R.id.etNamaPelanggan);
         txt_phone   = dialogView.findViewById(R.id.etPhone);
         txt_daya    = dialogView.findViewById(R.id.etDaya);
         txt_ulp     = dialogView.findViewById(R.id.etUlp);
         txt_lokasi  =  dialogView.findViewById(R.id.etLokasi);
         txt_tanggal = dialogView.findViewById(R.id.etTgl);
         txt_tanggalAkhir = dialogView.findViewById(R.id.etTglBerakhir);

         txt_tanggalAkhir.setOnClickListener(v -> {
             SharedPrefManager.clearTanggl(getContext());
             Calendar now = Calendar.getInstance();
             DatePickerDialog datePickerDialog = DatePickerDialog
                     .newInstance(this,
                             now.get(Calendar.YEAR),
                             now.get(Calendar.MONTH),
                             now.get(Calendar.DAY_OF_MONTH));
             datePickerDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "DatePicker");
         });

         txt_tanggal.setOnClickListener(v -> {
             SharedPrefManager.setTnggl(getContext(), true);
             Calendar now = Calendar.getInstance();
             DatePickerDialog date = DatePickerDialog
                     .newInstance(this,
                             now.get(Calendar.YEAR),
                             now.get(Calendar.MONTH),
                             now.get(Calendar.DAY_OF_MONTH));
             date.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "Date_Picker");
         });
         txt_status.setOnClickListener(v -> {
             showMenuStatus(v);
         });
         txt_ulp.setOnClickListener(v -> showPopupMenu(v));

        dialog.setPositiveButton("SUBMIT", (dialog, which) -> {
            DatabaseReference ref;
            nama    = txt_nama.getText().toString();
            event = txt_event.getText().toString();
            phone    = txt_phone.getText().toString();
            status  = txt_status.getText().toString();
            tanggal = txt_tanggal.getText().toString();
            daya = txt_daya.getText().toString();
            lokasi = txt_lokasi.getText().toString();
            tangglAkhir = txt_tanggalAkhir.getText().toString();
            ulp = txt_ulp.getText().toString();

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
                String barang = "Belum Ada Barang";
                ref = FirebaseDatabase.getInstance().getReference("user/Data_peminjam");
                String idUser = ref.push().getKey();
                DataPelanggan model = new DataPelanggan(status,nama,phone,event,daya,lokasi,ulp,tanggal, tangglAkhir,barang,idUser);
                ref.child(model.getKey())
                        .setValue(model)
                        .addOnCompleteListener(task -> Toast.makeText(getContext(),
                                                    "Data Berhasil Ditambahkan",
                                                    Toast.LENGTH_SHORT).show());
            }
            dialog.dismiss();
        });

        dialog.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
        dialog.show();

    }

    private void showMenuStatus(View v) {
        android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(getContext(), v);
        //menampilkan layout menu_popup.xml
        popupMenu.getMenuInflater().inflate(R.menu.menu_status, popupMenu.getMenu());
        //aksi klik
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.bermohon:
                    txt_status.setText("Bermohon");
                    break;
                case R.id.bertanya:
                    txt_status.setText("Bertanya");
                    break;
                case R.id.sudah_bayar:
                    txt_status.setText("Sudah Bayar");
                    break;
            }
            return false;
        });
        popupMenu.show();
    }

    private void showPopupMenu(View v) {
        android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(getContext(), v);
        //menampilkan layout menu_popup.xml
        popupMenu.getMenuInflater().inflate(R.menu.menu_lokasi, popupMenu.getMenu());
        //aksi klik
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.ulp_panakukang:
                    txt_ulp.setText("ULP Panakukang");
                    break;
                case R.id.ulp_mattoanging:
                    txt_ulp.setText("ULP Mattoanging");
                    break;
                case R.id.ulp_sungguminasa:
                    txt_ulp.setText("ULP Sungguminasa");
                    break;
                case R.id.ulp_takalar:
                    txt_ulp.setText("ULP Takalar");
                    break;
                case R.id.ulp_malino:
                    txt_ulp.setText("ULP Malino");
                    break;
                case R.id.ulp_kalebajeng:
                    txt_ulp.setText("ULP Kalebajeng");
                    break;
            }
            return false;
        });
        popupMenu.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear+=1;
        if (SharedPrefManager.getTnggl(getActivity().getBaseContext())){
            txt_tanggal.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
        }else {
            txt_tanggalAkhir.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
        }
    }

}