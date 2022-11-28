package com.example.partyquickv2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private CountryCodePicker ccp;
    private TextInputLayout t1;
    private TextInputLayout t_password;
    private MaterialButton b1;
    private MaterialButton BTN_Register;
    private Validator v;
    private Validator v2;
    private boolean isValid = false;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        init();
        valid();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (MyPreferences.getDataLogin(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("UID", MyPreferences.getDataLogin(this));
            startActivity(intent);
            finish();
        }
    }

    private void findViews() {
        ccp = findViewById(R.id.ccp);
        t1 = findViewById(R.id.t1);
        t_password = findViewById(R.id.t_password);
        b1 = findViewById(R.id.b1);
        BTN_Register = findViewById(R.id.BTN_Register);
    }

    private void init() {
        ccp.setDefaultCountryUsingNameCode("IL");
        ccp.resetToDefaultCountry();
        ccp.registerCarrierNumberEditText(t1.getEditText());
        firebaseAuth = FirebaseAuth.getInstance();
        b1.setOnClickListener(v -> {
            if (!checkBeforeLog())
                return;

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                final String mobile = ccp.getFullNumberWithPlus().replace(" ", "");
                final String password = Objects.requireNonNull(t_password.getEditText()).getText().toString();
                boolean isUserPhone = false;

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String uid = ds.getKey();
                        if (Objects.equals(snapshot.child(uid).child("phone").getValue(String.class), mobile)) {
                            isUserPhone = true;
                            if (Objects.equals(snapshot.child(uid).child("password").getValue(String.class), password)) {
                                Toast.makeText(getApplicationContext(), "Logged in! :)", Toast.LENGTH_LONG).show();
                                MyPreferences.setDataLogin(LoginActivity.this, true);
                                MyPreferences.setUID(LoginActivity.this, uid);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    if (!isUserPhone) {
                        Toast.makeText(getApplicationContext(), "Phone doesn't exist", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });


        BTN_Register.setOnClickListener(v1 -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean checkBeforeLog() {
        isValid = this.v.validateIt();
        if (!isValid) {
            Toast.makeText(getApplicationContext(), this.v.getError(), Toast.LENGTH_LONG).show();
            return false;
        }
        isValid = this.v2.validateIt();
        if (!isValid) {
            Toast.makeText(getApplicationContext(), this.v2.getError(), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void valid() {
        v = Validator.Builder
                .make(t1)
                .addWatcher(new Validator.Watcher_Number("Not a Number"))
                .addWatcher(new Validator.Watcher_Exact_Len("Phone must contain 10 digits", 10))
                .build();

        v2 = Validator.Builder
                .make(t_password)
                .addWatcher(new Validator.Watcher_Minimun_Len("Password must contain at least 6 digits", 6))
                .build();

    }
}
