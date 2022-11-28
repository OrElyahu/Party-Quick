package com.example.partyquickv2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Fragment_Search extends Fragment {

    private TextInputLayout frame_discover_EDT_search;
    private MaterialButton frame_discover_BTN_search;
    private ImageView frame_discover_result;
    private HashMap<Integer, Party> orders;
    private Party result;

    private AppCompatActivity activity;


    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        findViews(view);
        orders = DataManager.generateParties();


        frame_discover_BTN_search.setOnClickListener(v -> {
            for(Party p : orders.values()){
                if(p.sameTitle(Objects.requireNonNull(frame_discover_EDT_search.getEditText()).getText().toString())){
                    Toast.makeText(activity, "Event Found", Toast.LENGTH_LONG).show();
                    Objects.requireNonNull(frame_discover_EDT_search.getEditText()).setText("");
                    result = p;
                    Glide.with(activity).load(p.getImage()).into(frame_discover_result);
                    return;
                }
            }
            Toast.makeText(activity, "Event doesn't exist", Toast.LENGTH_LONG).show();
        });

        frame_discover_result.setOnClickListener(v -> {
           if(result == null)
               return;
            Toast.makeText(activity, result.getTitle(), Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    private void findViews(View view) {
        frame_discover_EDT_search = view.findViewById(R.id.frame_discover_EDT_search);
        frame_discover_BTN_search = view.findViewById(R.id.frame_discover_BTN_search);
        frame_discover_result = view.findViewById(R.id.frame_discover_result);
    }
}