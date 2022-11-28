package com.example.partyquickv2;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class Fragment_Add_Event extends Fragment {

    private AppCompatActivity activity;
    private TextInputLayout date_day, date_month, date_year;
    private TextInputLayout time_hour, time_min;
    private TextInputLayout party_name, party_place, party_url;
    private MaterialButton party_add;


    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__add_event, container, false);
        findViews(view);


        party_add.setOnClickListener(v -> {
            Party p = getInfo();
            DataManager.addEvent(p);
            Toast.makeText(activity, "Event added successfully", Toast.LENGTH_LONG).show();
            clearScreen();
        });


        return view;
    }

    private void clearScreen() {
        Objects.requireNonNull(date_day.getEditText()).setText("");
        Objects.requireNonNull(date_month.getEditText()).setText("");
        Objects.requireNonNull(date_year.getEditText()).setText("");
        Objects.requireNonNull(time_hour.getEditText()).setText("");
        Objects.requireNonNull(time_min.getEditText()).setText("");
        Objects.requireNonNull(party_name.getEditText()).setText("");
        Objects.requireNonNull(party_place.getEditText()).setText("");
        Objects.requireNonNull(party_url.getEditText()).setText("");
    }

    private Party getInfo() {
       Time t =new Time()
               .setHour(Integer.parseInt(Objects.requireNonNull(time_hour.getEditText()).getText().toString()))
               .setMin(Integer.parseInt(Objects.requireNonNull(time_min.getEditText()).getText().toString()));

       Date d = new Date()
               .setTime(t)
               .setYear(Integer.parseInt(Objects.requireNonNull(date_year.getEditText()).getText().toString()))
               .setMonth(Integer.parseInt(Objects.requireNonNull(date_month.getEditText()).getText().toString()))
               .setDay(Integer.parseInt(Objects.requireNonNull(date_day.getEditText()).getText().toString()));

       Party p = new Party()
               .setDate(d)
               .setLocation(Objects.requireNonNull(party_place.getEditText()).getText().toString())
               .setTitle(Objects.requireNonNull(party_name.getEditText()).getText().toString())
               .setImage(Objects.requireNonNull(party_url.getEditText()).getText().toString());

//       DataManager.generateParties().add(p);
        return p;
    }

    private void findViews(View view) {
        date_day = view.findViewById(R.id.date_day);
        date_month = view.findViewById(R.id.date_month);
        date_year = view.findViewById(R.id.date_year);
        time_hour = view.findViewById(R.id.time_hour);
        time_min = view.findViewById(R.id.time_min);
        party_name = view.findViewById(R.id.party_name);
        party_place = view.findViewById(R.id.party_place);
        party_url = view.findViewById(R.id.party_url);
        party_add = view.findViewById(R.id.party_add);
    }
}