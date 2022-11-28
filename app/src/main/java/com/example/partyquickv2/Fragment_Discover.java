package com.example.partyquickv2;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Fragment_Discover extends Fragment {

    private RecyclerView LST_parties;
    private AppCompatActivity activity;
    HashMap<Integer, Party> parties;


    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        findViews(view);
        parties= DataManager.generateParties();
        Adapter_Party adapter_party = new Adapter_Party(activity, parties);
        LST_parties.setLayoutManager(new GridLayoutManager(activity,1));
        LST_parties.setHasFixedSize(true);
        LST_parties.setItemAnimator(new DefaultItemAnimator());
        LST_parties.setAdapter(adapter_party);

        adapter_party.setPartyItemClickListener(new Adapter_Party.PartyItemClickListener() {
            @Override
            public void partyItemClicked(Party party, int position) {
//                Toast.makeText(activity, party.getTitle(), Toast.LENGTH_SHORT).show();
                addPartyToUser(position);
            }
        });

        return view;
    }

    private void findViews(View view) {
        LST_parties = view.findViewById(R.id.LST_parties);
    }

    void addPartyToUser(Integer pos){
        final String uid = MyPreferences.getUID(activity);
        List<Integer> ls = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(uid);
        databaseReference.child("partyListCount").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        String countStr = String.valueOf(dataSnapshot.getValue());
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(uid).child("partyList");
                        for(int counter = 0; counter < Integer.parseInt(countStr); counter++){
                            databaseReference.child("" + counter).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if(task.isSuccessful()){
                                        if(task.getResult().exists()){
                                            DataSnapshot dataSnapshot1 = task.getResult();
                                            String partyNo = String.valueOf(dataSnapshot1.getValue());
                                            ls.add(Integer.parseInt(partyNo));
                                        }
                                    }
                                }
                            });

                        }

                    }
                }
            }
        });

        for (int i = 0; i < ls.size(); i++) {
            if(ls.get(i).equals(pos))
                return;
        }
        ls.add(pos);
        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(uid).child("partyList");
        databaseReference.child("" + (ls.size()-1)).setValue(pos);
        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(uid).child("partyListCount");
        databaseReference.setValue(ls.size());

    }
}
