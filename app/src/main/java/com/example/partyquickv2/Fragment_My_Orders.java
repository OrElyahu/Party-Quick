package com.example.partyquickv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Fragment_My_Orders extends Fragment {

    private RecyclerView LST_orders;

    private HashMap<Integer, Party> orders = new HashMap<>();
    private Adapter_Party adapter_party;
    private AppCompatActivity activity;
    private MaterialTextView self_LBL_name;
    private MaterialTextView self_LBL_phone;
    private MaterialButton self_BTN_details;
    private HashMap<Integer, Party> partyList;
    private MaterialTextView self_LBL_orders;


    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        findViews(view);
        partyList = DataManager.generateParties();
        initProfile();
        LST_orders.setLayoutManager(new GridLayoutManager(activity, 1));
        LST_orders.setHasFixedSize(true);
        LST_orders.setItemAnimator(new DefaultItemAnimator());


        if (orders.size() != 0) {
            adapter_party = new Adapter_Party(activity, orders);
        } else {
            adapter_party = new Adapter_Party(activity, partyList);
        }

        LST_orders.setAdapter(adapter_party);

        adapter_party.setPartyItemClickListener(new Adapter_Party.PartyItemClickListener() {
            @Override
            public void partyItemClicked(Party party, int position) {
                Toast.makeText(activity, party.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


        self_BTN_details.setOnClickListener(v -> {
            startActivity(new Intent(activity, LoginActivity.class));
            MyPreferences.clearData(activity);
            activity.finish();
        });
        return view;
    }

    private void initProfile() {
        final String uid = MyPreferences.getUID(activity);
        FirebaseDatabase.getInstance().getReference().child("User").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                self_LBL_name.setText((String) snapshot.child("fullName").getValue());
                self_LBL_phone.setText((String) snapshot.child("phone").getValue());
                self_LBL_orders.setText(String.valueOf(snapshot.child("partyListCount").getValue()));

                for (int i = 0; i < Integer.parseInt(self_LBL_orders.getText().toString()); i++) {
                    int partyNo = Integer.parseInt(String.valueOf(snapshot.child("partyList").child("" + 0).getValue()));
                    Party p = partyList.get(partyNo);
                    if (p != null)
                        orders.put(partyNo, p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void findViews(View view) {

        LST_orders = view.findViewById(R.id.LST_parties);
        self_LBL_name = view.findViewById(R.id.self_LBL_name);
        self_LBL_phone = view.findViewById(R.id.self_LBL_phone);
        self_BTN_details = view.findViewById(R.id.self_BTN_details);
        self_LBL_orders = view.findViewById(R.id.self_LBL_orders);
    }
}
