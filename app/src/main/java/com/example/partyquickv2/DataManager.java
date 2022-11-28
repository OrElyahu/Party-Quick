package com.example.partyquickv2;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DataManager {

    private static HashMap<Integer, Party> parties = new HashMap<>();


    private static int partyCounter = 0;
    private static boolean isFirstTime = true;

    private static void DetectSize() {
        partyCounter = 0;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Party").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    partyCounter++;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static HashMap<Integer, Party> generateParties() {
        if(isFirstTime){
            DetectSize();
            isFirstTime = false;
        }
        for (int counter = 0; counter <= partyCounter; counter++) {
            readData(String.valueOf(counter));
        }
        return parties;

//        if (parties == null) {
//            parties = new ArrayList<>();
//
//            //TODO: generate parties from FireBase
//
//            parties.add(new Party()
//                    .setTitle("Shalvata")
//                    .setLocation("Tel-Aviv")
//                    .setDate(new Date()
//                            .setMonth(2)
//                            .setDay(10)
//                            .setYear(2022)
//                            .setTime(new Time()
//                                    .setHour(20)
//                                    .setMin(0)))
//                    .setImage("https://static.wixstatic.com/media/9bd396_5013d7945fcd4d4497c6ff8b7804e594.jpg/v1/fill/w_1244,h_493,al_c,q_85,usm_0.66_1.00_0.01/9bd396_5013d7945fcd4d4497c6ff8b7804e594.webp"));
//
//
//            parties.add(new Party()
//                    .setTitle("Black - Milk")
//                    .setLocation("Tel-Aviv")
//                    .setDate(new Date()
//                            .setMonth(2)
//                            .setDay(10)
//                            .setYear(2022)
//                            .setTime(new Time()
//                                    .setHour(20)
//                                    .setMin(0)))
//                    .setImage("data:image/jpeg;base64,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"));
//
//            parties.add(new Party()
//                    .setTitle("Forever - TLV")
//                    .setLocation("Tel-Aviv")
//                    .setDate(new Date()
//                            .setMonth(2)
//                            .setDay(10)
//                            .setYear(2022)
//                            .setTime(new Time()
//                                    .setHour(20)
//                                    .setMin(0)))
//                    .setImage("data:image/jpeg;base64,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AREQEREBERAREQERED//2Q=="));
//
//
//            parties.add(new Party()
//                    .setTitle("Shpagat")
//                    .setLocation("Tel-Aviv")
//                    .setDate(new Date()
//                            .setMonth(2)
//                            .setDay(10)
//                            .setYear(2022)
//                            .setTime(new Time()
//                                    .setHour(20)
//                                    .setMin(0)))
//                    .setImage("https://www.secrettelaviv.com/wp-content/uploads/2016/02/11194432_824052261019026_9125408714783274685_o-1024x683.jpg"));
//
//            parties.add(new Party()
//                    .setTitle("OFFER NISSIM")
//                    .setLocation("Tel-Aviv")
//                    .setDate(new Date()
//                            .setMonth(2)
//                            .setDay(10)
//                            .setYear(2022)
//                            .setTime(new Time()
//                                    .setHour(20)
//                                    .setMin(0)))
//                    .setImage("https://i1.sndcdn.com/artworks-oyVqJ8mTKshURXYn-EGM3Vg-t500x500.jpg"));
//
//
//            return parties;
//        };
    }

    public static void addEvent(Party p) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Party").child("" + partyCounter);
        partyCounter++;
        myRef.setValue(p);
    }

    private static void readData(String counter) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Party");
        databaseReference.child(counter).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        String title = String.valueOf(dataSnapshot.child("title").getValue());
                        String location = String.valueOf(dataSnapshot.child("location").getValue());
                        String image = String.valueOf(dataSnapshot.child("image").getValue());
                        String day = String.valueOf(dataSnapshot.child("date").child("day").getValue());
                        String month = String.valueOf(dataSnapshot.child("date").child("month").getValue());
                        String year = String.valueOf(dataSnapshot.child("date").child("year").getValue());
                        String hour = String.valueOf(dataSnapshot.child("date").child("time").child("hour").getValue());
                        String min = String.valueOf(dataSnapshot.child("date").child("time").child("min").getValue());

                        Time t = new Time()
                                .setHour(Integer.parseInt(hour))
                                .setMin(Integer.parseInt(min));
                        Date d = new Date()
                                .setYear(Integer.parseInt(year))
                                .setMonth(Integer.parseInt(month))
                                .setYear(Integer.parseInt(day))
                                .setTime(t);
                        Party p = new Party()
                                .setDate(d)
                                .setTitle(title)
                                .setLocation(location)
                                .setImage(image);
                        parties.put(Integer.parseInt(counter), p);
                    }
                }
            }
        });
    }
}
