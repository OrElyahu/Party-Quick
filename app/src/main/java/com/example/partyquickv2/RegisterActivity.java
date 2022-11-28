package com.example.partyquickv2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private CountryCodePicker ccp_register;
    private TextInputLayout t1_register;
    private TextInputLayout t_name_register;
    private TextInputLayout t_password_register;
    private MaterialButton b1_register;
    private MaterialButton BTN_Login;
    private Validator v;
    private Validator v2;
    private Validator v3;
    private boolean isValid = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViews();
        init();
        valid();
    }

    private void findViews() {
        ccp_register = findViewById(R.id.ccp_register);
        t1_register = findViewById(R.id.t1_register);
        t_name_register = findViewById(R.id.t_name_register);
        t_password_register = findViewById(R.id.t_password_register);
        b1_register = findViewById(R.id.b1_register);
        BTN_Login = findViewById(R.id.BTN_Login);
    }

    private void init() {
        ccp_register.setDefaultCountryUsingNameCode("IL");
        ccp_register.resetToDefaultCountry();
        ccp_register.registerCarrierNumberEditText(t1_register.getEditText());
        b1_register.setOnClickListener(v -> {
            String mobile = ccp_register.getFullNumberWithPlus().replace(" ", "");
            if (!checkBeforeRegister(mobile)) {
                return;
            }

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                final String mobile = ccp_register.getFullNumberWithPlus().replace(" ", "");
                boolean isPhoneAvail = true;

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String uid = ds.getKey();
                        if (Objects.equals(snapshot.child(uid).child("phone").getValue(String.class), mobile)) {
                            isPhoneAvail = false;
                        }
                    }
                    if (!isPhoneAvail) {
                        Toast.makeText(getApplicationContext(), "Phone already in system", Toast.LENGTH_LONG).show();
                    }else{
                        Intent intent = new Intent(RegisterActivity.this, ManageOTPActivity.class);
                        intent.putExtra("mobile", mobile);
                        intent.putExtra("password", Objects.requireNonNull(t_password_register.getEditText()).getText().toString());
                        intent.putExtra("full_name", Objects.requireNonNull(t_name_register.getEditText()).getText().toString());
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });

        BTN_Login.setOnClickListener(v1 -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean checkBeforeRegister(String mobile) {
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
        isValid = this.v3.validateIt();
        if(!isValid){
            Toast.makeText(getApplicationContext(), this.v3.getError(), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void valid() {
        v = Validator.Builder
                .make(t1_register)
                .addWatcher(new Validator.Watcher_Number("Not a Number"))
                .addWatcher(new Validator.Watcher_Exact_Len("Phone must contain 10 digits", 10))
                .build();

        v2 = Validator.Builder
                .make(t_password_register)
                .addWatcher(new Validator.Watcher_Number("Not a Number"))
                .addWatcher(new Validator.Watcher_Minimun_Len("Password must contain at least 6 digits", 6))
                .build();

        v3 = Validator.Builder
                .make(t_name_register)
                .addWatcher(new Validator.Watcher_Minimun_Len("Name should be at least 2 chars",2))
                .build();

    }
}
