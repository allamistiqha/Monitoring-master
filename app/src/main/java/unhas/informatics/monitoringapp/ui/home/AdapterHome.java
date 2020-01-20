package unhas.informatics.monitoringapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import unhas.informatics.monitoringapp.Model.DataPelanggan;
import unhas.informatics.monitoringapp.Preference.SharedPrefManager;
import unhas.informatics.monitoringapp.R;
import unhas.informatics.monitoringapp.ui.genset.Genset;
import unhas.informatics.monitoringapp.ui.ugb.UgbFragment;
import unhas.informatics.monitoringapp.ui.ups.UpsFragment;

import static androidx.core.content.ContextCompat.startActivity;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.BindHome> {

    public List<DataPelanggan> homes;
    private Context context;
    public AdapterHome(List<DataPelanggan> homes,Context context) {
        this.homes = homes;
        this.context =context;
    }

    @NonNull
    @Override
    public BindHome onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BindHome(LayoutInflater.from(parent.getContext()).inflate(R.layout.pelanggan, parent, false));
    }

    @Override
    public void onBindViewHolder(BindHome holder, int position) {
        holder.DataPelanggan(homes.get(position));
    }

    @Override
    public int getItemCount() {
        return homes.size();
    }

    public interface OnItemSelectedListener {
    }

    class BindHome extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, PopupMenu.OnMenuItemClickListener{
        TextView nama, status, tanggal,barang,lokasi,phone;
        String key;

        LinearLayoutCompat ly;

        public BindHome(@NonNull View itemView) {
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
            key = home.getKey();
            nama.setText(home.getNama());
            status.setText(home.getStatus());
            tanggal.setText(home.getTanggal());
            barang.setText(home.getDaya());
            phone.setText(home.getPhone());
            lokasi.setText(home.getLokasi());

            if (SharedPrefManager.getRegisteredStatus(context).equals("admin")) {
                ly.setOnClickListener(v -> {
                    PopupMenu popup = new PopupMenu(v.getContext(), v);
                    popup.getMenuInflater().inflate(R.menu.menu_context, popup.getMenu());
                    popup.setOnMenuItemClickListener(this);
                    popup.show();
                });
            }else{
                ly.setOnClickListener(v -> Toast.makeText(context,
                        "anda adalah : "+SharedPrefManager.getRegisteredStatus(context)
                        ,Toast.LENGTH_SHORT).show());
            }
        }

        @Override
        public void onClick(View v) {

        }
        public int getLastClickedItemPosition() {
            getAdapterPosition();
            return 0;
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
                case R.id.action_edit:

                    break;
                case R.id.action_delete:
                    //handle menu2 click
                    break;
                case R.id.action_ugb:
                    UgbFragment mfragment = new UgbFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("id",key );
                    mfragment.setArguments(bundle);
                    Navigation.findNavController(itemView).navigate(R.id.action_navigation_home_to_navigation_ugb, bundle);
                    break;
                case R.id.action_ups:
                    Fragment fragment = new UpsFragment();
                    loadFragment(fragment);
                    break;
                case R.id.action_genset:
                    Fragment genset = new Genset();
                    loadFragment(genset);
                    break;
            }
            return false;
        }
        private boolean loadFragment(Fragment fragment) {
            //switching fragment
            if (fragment != null) {
                ((AppCompatActivity)context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                return true;
            }
            return false;
        }
    }
}
