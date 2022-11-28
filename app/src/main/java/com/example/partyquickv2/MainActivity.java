package com.example.partyquickv2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private final int FRAGMENT_DISCOVER_INDEX = 0;
    private final int FRAGMENT_SEARCH_INDEX = 1;
    private final int FRAGMENT_MY_ORDERS_INDEX = 2;
    private final int FRAGMENT_ADD_EVENT_INDEX = 3;

    private Fragment_Search fragmentSearch;
    private Fragment_Discover fragmentDiscover;
    private Fragment_My_Orders fragmentMyOrders;
    private Fragment_Add_Event fragment_add_event;

    private int fragmentIndex = 0;

    private MaterialButton BTN_DISCOVER;
    private MaterialButton BTN_SEARCH;
    private MaterialButton BTN_MY_ORDERS;
    private MaterialButton BTN_ADD_EVENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentDiscover = new Fragment_Discover();
        fragmentDiscover.setActivity(this);
        getSupportFragmentManager().beginTransaction().add(R.id.frame1, fragmentDiscover).commit();




        BTN_DISCOVER = findViewById(R.id.BTN_DISCOVER);
        BTN_DISCOVER.setOnClickListener(v -> {
            if(fragmentIndex == FRAGMENT_DISCOVER_INDEX)
                return;

            fragmentIndex = FRAGMENT_DISCOVER_INDEX;
            if(fragmentSearch!=null){
                getSupportFragmentManager().beginTransaction().hide(fragmentSearch).commit();
            }
            if(fragmentMyOrders!=null){
                getSupportFragmentManager().beginTransaction().hide(fragmentMyOrders).commit();
            }
            if(fragment_add_event != null){
                getSupportFragmentManager().beginTransaction().hide(fragment_add_event).commit();
            }
            fragmentDiscover = new Fragment_Discover();
            fragmentDiscover.setActivity(this);
            getSupportFragmentManager().beginTransaction().add(R.id.frame1, fragmentDiscover).commit();
        });

        BTN_SEARCH = findViewById(R.id.BTN_SEARCH);
        BTN_SEARCH.setOnClickListener(v -> {
            if(fragmentIndex == FRAGMENT_SEARCH_INDEX)
                return;

            fragmentIndex = FRAGMENT_SEARCH_INDEX;
            if(fragmentDiscover!= null){
                getSupportFragmentManager().beginTransaction().hide(fragmentDiscover).commit();
            }
            if(fragmentMyOrders!=null){
                getSupportFragmentManager().beginTransaction().hide(fragmentMyOrders).commit();
            }
            if(fragment_add_event != null){
                getSupportFragmentManager().beginTransaction().hide(fragment_add_event).commit();
            }
            fragmentSearch = new Fragment_Search();
            fragmentSearch.setActivity(this);
            getSupportFragmentManager().beginTransaction().add(R.id.frame1, fragmentSearch).commit();
        });

        BTN_MY_ORDERS = findViewById(R.id.BTN_MY_ORDERS);
        BTN_MY_ORDERS.setOnClickListener(v -> {
            if(fragmentIndex == FRAGMENT_MY_ORDERS_INDEX)
                return;

            fragmentIndex = FRAGMENT_MY_ORDERS_INDEX;
            if(fragmentDiscover!= null){
                getSupportFragmentManager().beginTransaction().hide(fragmentDiscover).commit();
            }
            if(fragmentSearch !=null){
                getSupportFragmentManager().beginTransaction().hide(fragmentSearch).commit();
            }
            if(fragment_add_event != null){
                getSupportFragmentManager().beginTransaction().hide(fragment_add_event).commit();
            }
            fragmentMyOrders = new Fragment_My_Orders();
            fragmentMyOrders.setActivity(this);
            getSupportFragmentManager().beginTransaction().add(R.id.frame1, fragmentMyOrders).commit();
        });


        BTN_ADD_EVENT = findViewById(R.id.BTN_ADD_EVENT);
        BTN_ADD_EVENT.setOnClickListener(v -> {
            if(fragmentIndex == FRAGMENT_ADD_EVENT_INDEX)
                return;

            fragmentIndex = FRAGMENT_ADD_EVENT_INDEX;
            if(fragmentDiscover!= null){
                getSupportFragmentManager().beginTransaction().hide(fragmentDiscover).commit();
            }
            if(fragmentSearch !=null){
                getSupportFragmentManager().beginTransaction().hide(fragmentSearch).commit();
            }
            if(fragmentMyOrders != null){
                getSupportFragmentManager().beginTransaction().hide(fragmentMyOrders).commit();
            }
            fragment_add_event = new Fragment_Add_Event();
            fragment_add_event.setActivity(this);
            getSupportFragmentManager().beginTransaction().add(R.id.frame1, fragment_add_event).commit();
        });


    }



}