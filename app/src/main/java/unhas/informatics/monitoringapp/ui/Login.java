package unhas.informatics.monitoringapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import unhas.informatics.monitoringapp.Model.NewUser;
import unhas.informatics.monitoringapp.Preference.SharedPrefManager;
import unhas.informatics.monitoringapp.R;

public class Login extends AppCompatActivity {

    Button login;
    EditText etNama, etPass;
    ProgressBar progressBar;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.progressBar);
        login = findViewById(R.id.btn_login);
        etNama = findViewById(R.id.etNama);
        etPass = findViewById(R.id.etPass);

        login.setOnClickListener(v -> {
            loginAccount();
        });
    }

    private void loginAccount() {
        progressBar.setVisibility(View.VISIBLE);

        String email, password;
        email = etNama.getText().toString();
        password = etPass.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        reference = FirebaseDatabase.getInstance().getReference("user/account");
//        Query phoneQuery = reference.orderByChild(phoneNo).equalTo("+923336091371");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        NewUser user = snapshot.getValue(NewUser.class);
                        if (user != null) {
                            if (user.getEmail().equals(email) && user.getPass().equals(password)){
                                Toast.makeText(Login.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                SharedPrefManager.setRegisteredName(getBaseContext(), user.getNama());
                                SharedPrefManager.setRegisteredStatus(getBaseContext(),user.getStatus());
                                verify();
                            }else {
                                Toast.makeText(Login.this, "Failes Login", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("canceled","onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getLoggedInStatus(getBaseContext())){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void verify(){
        etNama.setError(null);
        etPass.setError(null);
        View view = null;
        boolean cancel = false;

        String username = etNama.getText().toString();
        String password = etPass.getText().toString();

        if (TextUtils.isEmpty(username)){
            etNama.setError("silahkan masukkan username anda");
            view = etNama;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)){
            etPass.setError("silahkan masukkan Password anda");
            view = etPass;
            cancel = true;
        }

        if (cancel)
            view.requestFocus();
        else{
            SharedPrefManager.setRegisteredUser(getBaseContext(), username);
            SharedPrefManager.setRegisteredPass(getBaseContext(), password);
            masuk();
        }
    }
    private void masuk() {
        SharedPrefManager.setLoggedInUser(getBaseContext(), SharedPrefManager.getRegisteredUser(getBaseContext()));
        SharedPrefManager.setLoggedInPass(getBaseContext(), SharedPrefManager.getRegisteredPass(getBaseContext()));
        SharedPrefManager.setLoggedInStatus(getBaseContext(), true);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}