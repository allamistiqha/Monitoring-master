package unhas.informatics.monitoringapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import unhas.informatics.monitoringapp.Preference.SharedPrefManager;
import unhas.informatics.monitoringapp.R;

public class MainActivity extends AppCompatActivity {

    DrawerLayout darwer;
    ImageView imageProfill;
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    TextView nama, email, status,Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageProfill = findViewById(R.id.imageProfile);
        darwer = findViewById(R.id.drawer);
        nama = findViewById(R.id.name);
        email = findViewById(R.id.emali);
        status = findViewById(R.id.statusUser);
        Logout = findViewById(R.id.Logout);

        nama.setText(SharedPrefManager.getRegisteredName(this));
        email.setText(SharedPrefManager.getLoggedInUser(this));
        status.setText(SharedPrefManager.getRegisteredStatus(this));
        Glide.with(this).load(R.drawable.ic_person_black_24dp)
                .circleCrop()
                .into(imageProfill);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavigationView navDrawer = findViewById(R.id.navigationView);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_ups,
                R.id.navigation_ugb, R.id.navigation_genset, R.id.navigation_ulp,R.id.tambaData)
                .setDrawerLayout(darwer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navDrawer, navController);
        NavigationUI.setupWithNavController(navView, navController);

        Logout.setOnClickListener(v -> {
            SharedPrefManager.clearLoggedInUser(this);
            startActivity(new Intent(this, Login.class));
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        if (darwer.isDrawerOpen(GravityCompat.START)) {
            darwer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration);
    }
}
