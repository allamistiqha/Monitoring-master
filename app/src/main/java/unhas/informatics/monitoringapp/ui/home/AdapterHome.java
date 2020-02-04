package unhas.informatics.monitoringapp.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import unhas.informatics.monitoringapp.Model.DataPelanggan;
import unhas.informatics.monitoringapp.Preference.SharedPrefManager;
import unhas.informatics.monitoringapp.R;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.BindHome> implements Filterable{

    private List<DataPelanggan> homes;
    private List<DataPelanggan> contactList;
    private Context context;

    AdapterHome(List<DataPelanggan> homes, Context context) {
        this.homes = homes;
        this.contactList = homes;
        this.context = context;
    }

    @NonNull
    @Override
    public BindHome onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BindHome(LayoutInflater.from(parent.getContext()).inflate(R.layout.pelanggan, parent, false));
    }

    @Override
    public void onBindViewHolder(BindHome holder, int position) {
        holder.DataPelanggan(contactList.get(position));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    contactList = homes;
                } else {
                    List<DataPelanggan> filteredList = new ArrayList<>();
                    for (DataPelanggan row : homes) {
                        if (row.getTanggal().toLowerCase().contains(charString.toLowerCase()) || row.getEvent().contains(constraint)) {
                            filteredList.add(row);
                        }
                    }
                    contactList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = contactList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contactList = (ArrayList<DataPelanggan>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class BindHome extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, PopupMenu.OnMenuItemClickListener, DatePickerDialog.OnDateSetListener {
        TextView nama, status, tanggal,barang,lokasi,phone, ulp, tangglAkhir, event;
        String key;
        LinearLayoutCompat ly;
        BindHome(@NonNull View itemView) {
            super(itemView);
        }

        void DataPelanggan(DataPelanggan home){
            ly   = itemView.findViewById(R.id.lyPelanggan);
            nama = itemView.findViewById(R.id.nama);
            status = itemView.findViewById(R.id.statusPelanggan);
            tanggal =itemView.findViewById(R.id.tglPemakaian);
            barang =itemView.findViewById(R.id.barang);
            lokasi =itemView.findViewById(R.id.lokasi);
            phone =itemView.findViewById(R.id.phone);
            ulp = itemView.findViewById(R.id.ulp);
            tangglAkhir = itemView.findViewById(R.id.tglBerakhir);
            event = itemView.findViewById(R.id.Event);
            key = home.getKey();

            nama.setText(home.getNama());
            status.setText(home.getStatus());
            tanggal.setText(home.getTanggal());
            barang.setText(home.getDaya());
            phone.setText(home.getPhone());
            ulp.setText(home.getUlp());
            lokasi.setText(home.getLokasi());
            tangglAkhir.setText(home.getTanggalAkhir());
            event.setText(home.getEvent());

            if (SharedPrefManager.getRegisteredStatus(context).equals("Admin")) {
                ly.setOnClickListener(v -> {
                    PopupMenu popup = new PopupMenu(v.getContext(), v);
                    popup.getMenuInflater().inflate(R.menu.menu_context, popup.getMenu());
                    popup.setOnMenuItemClickListener(this);
                    popup.show();
                });
            }else{
                ly.setOnClickListener(v -> {
                    PopupMenu popup = new PopupMenu(v.getContext(), v);
                    popup.getMenuInflater().inflate(R.menu.menu_context_user, popup.getMenu());
                    popup.setOnMenuItemClickListener(this);
                    popup.show();
                });
            }
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            popup.getMenuInflater().inflate(R.menu.menu_context, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_detail:
                    DialogFormDetail();
                    break;
                case R.id.action_edit:
                    DialogForm();
                    break;
                case R.id.action_delete:
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user/Data_peminjam");
                    ref.child(key).removeValue();
                    Navigation.findNavController(itemView).navigate(R.id.action_navigation_home_self);
                    break;
                case R.id.action_ugb:
                    SharedPrefManager.setNamePelanggan(context,homes.get(getAdapterPosition()).getKey());
                    SharedPrefManager.setTngglPemakaian(context, tanggal.getText().toString());
                    SharedPrefManager.setTngglBerakhir(context, tangglAkhir.getText().toString());
                    SharedPrefManager.setStatusPelanggan(context,status.getText().toString());
                    SharedPrefManager.setUlp(context, ulp.getText().toString());
                    Navigation.findNavController(itemView).navigate(R.id.action_navigation_home_to_navigation_ugb);
                    break;
                case R.id.action_ups:
                    SharedPrefManager.setNamePelanggan(context,homes.get(getAdapterPosition()).getKey());
                    SharedPrefManager.setTngglPemakaian(context, tanggal.getText().toString());
                    SharedPrefManager.setTngglBerakhir(context, tangglAkhir.getText().toString());
                    SharedPrefManager.setStatusPelanggan(context,status.getText().toString());
                    SharedPrefManager.setUlp(context, ulp.getText().toString());
                    Navigation.findNavController(itemView).navigate(R.id.action_navigation_home_to_navigation_ups);
                    break;
                case R.id.action_genset:
                    SharedPrefManager.setNamePelanggan(context,homes.get(getAdapterPosition()).getKey());
                    SharedPrefManager.setTngglPemakaian(context, tanggal.getText().toString());
                    SharedPrefManager.setTngglBerakhir(context, tangglAkhir.getText().toString());
                    SharedPrefManager.setStatusPelanggan(context,status.getText().toString());
                    SharedPrefManager.setUlp(context, ulp.getText().toString());
                    Navigation.findNavController(itemView).navigate(R.id.action_navigation_home_to_navigation_genset);
                    break;
            }
            return false;
        }

        private void DialogFormDetail() {
            dialog = new AlertDialog.Builder(context);
            inflater = ((Activity)context).getLayoutInflater();
            dialogView = inflater.inflate(R.layout.data_detail, null);
            dialog.setView(dialogView);
            dialog.setCancelable(true);

            txt_status  = dialogView.findViewById(R.id.etStatus);
            txt_nama    = dialogView.findViewById(R.id.etNamaPelanggan);
            txt_phone   = dialogView.findViewById(R.id.etPhone);
            txt_event   = dialogView.findViewById(R.id.etEvent);
            txt_daya    = dialogView.findViewById(R.id.etDaya);
            txt_ulp     = dialogView.findViewById(R.id.etUlp);
            txt_lokasi  =  dialogView.findViewById(R.id.etLokasi);
            txt_tanggal = dialogView.findViewById(R.id.etTgl);
            txt_tanggalAkhir = dialogView.findViewById(R.id.etTglBerakhir);

            txt_status.setText(status.getText().toString());
            txt_event.setText(event.getText().toString());
            txt_nama.setText(nama.getText().toString());
            txt_phone.setText(phone.getText().toString());
            txt_daya.setText(barang.getText().toString());
            txt_ulp.setText(ulp.getText().toString());
            txt_lokasi.setText(lokasi.getText().toString());
            txt_tanggal.setText(tanggal.getText().toString());
            txt_tanggalAkhir.setText(tangglAkhir.getText().toString());
            dialog.show();
        }

        LayoutInflater inflater;
        View dialogView;
        AlertDialog.Builder dialog;
        EditText txt_nama , txt_phone,txt_event, txt_ulp,txt_status, txt_daya, txt_lokasi,txt_tanggal,txt_tanggalAkhir;
        String namaa, daya, phonee, tanggall,ulpp,lokasii,statuss, tangglAkhirr, eventt;

        private void DialogForm()  {
            dialog = new AlertDialog.Builder(context);
            inflater = ((Activity)context).getLayoutInflater();
            dialogView = inflater.inflate(R.layout.data_pelanggan, null);
            dialog.setView(dialogView);
            dialog.setCancelable(true);

            txt_status  = dialogView.findViewById(R.id.etStatus);
            txt_nama    = dialogView.findViewById(R.id.etNamaPelanggan);
            txt_phone   = dialogView.findViewById(R.id.etPhone);
            txt_event   = dialogView.findViewById(R.id.etEvent);
            txt_daya    = dialogView.findViewById(R.id.etDaya);
            txt_ulp     = dialogView.findViewById(R.id.etUlp);
            txt_lokasi  =  dialogView.findViewById(R.id.etLokasi);
            txt_tanggal = dialogView.findViewById(R.id.etTgl);
            txt_tanggalAkhir = dialogView.findViewById(R.id.etTglBerakhir);

            txt_status.setText(status.getText().toString());
            txt_event.setText(event.getText().toString());
            txt_nama.setText(nama.getText().toString());
            txt_phone.setText(phone.getText().toString());
            txt_daya.setText(barang.getText().toString());
            txt_ulp.setText(ulp.getText().toString());
            txt_lokasi.setText(lokasi.getText().toString());
            txt_tanggal.setText(tanggal.getText().toString());
            txt_tanggalAkhir.setText(tangglAkhir.getText().toString());

            txt_status.setOnClickListener(v -> {
                showMenuStatus(v);
            });
            txt_ulp.setOnClickListener(v -> {
                showPopupMenu(v);
            });

            txt_tanggal.setOnClickListener(v -> {
                SharedPrefManager.setTnggl(context, true);
                Calendar now = Calendar.getInstance();
                DatePickerDialog date = DatePickerDialog
                        .newInstance(this,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH));
                date.show(Objects.requireNonNull((FragmentActivity)context).getSupportFragmentManager(), "Date_Picker");
            });

            txt_tanggalAkhir.setOnClickListener(v -> {
                SharedPrefManager.clearTanggl(context);
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog
                        .newInstance(this,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show(Objects.requireNonNull((FragmentActivity)context).getSupportFragmentManager(), "DatePicker");
            });

            dialog.setPositiveButton("SUBMIT", (dialog, which) -> {
                DatabaseReference ref;
                namaa    = txt_nama.getText().toString();
                phonee    = txt_phone.getText().toString();
                eventt = txt_event.getText().toString();
                statuss  = txt_status.getText().toString();
                tanggall = txt_tanggal.getText().toString();
                daya = txt_daya.getText().toString();
                lokasii = txt_lokasi.getText().toString();
                tangglAkhirr = txt_tanggalAkhir.getText().toString();
                ulpp = txt_ulp.getText().toString();

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
                    DataPelanggan model = new
                            DataPelanggan(statuss,namaa,phonee,eventt,daya,lokasii,ulpp,tanggall, tangglAkhirr,barang,key);
                    ref.child(key)
                            .setValue(model)
                            .addOnCompleteListener(task -> Toast.makeText(context,
                                    "Data Berhasil Ditambahkan",
                                    Toast.LENGTH_SHORT).show());
                }
                dialog.dismiss();
            });

            dialog.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
            dialog.show();

        }
        private void showMenuStatus(View v) {
            android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(context, v);
            popupMenu.getMenuInflater().inflate(R.menu.menu_status, popupMenu.getMenu());
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
            android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(context, v);
            popupMenu.getMenuInflater().inflate(R.menu.menu_lokasi, popupMenu.getMenu());
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
            if (SharedPrefManager.getTnggl(context.getApplicationContext())){
                txt_tanggal.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
            }else {
                txt_tanggalAkhir.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
            }
        }
    }

}
