package com.example.partyquickv2;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class Adapter_Party extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private HashMap<Integer, Party> parties;
    private PartyItemClickListener partyItemClickListener;

    public Adapter_Party(Activity activity, HashMap<Integer, Party> parties) {
        this.activity = activity;
        this.parties = parties;
    }

    public Adapter_Party setPartyItemClickListener(PartyItemClickListener partyItemClickListener) {
        this.partyItemClickListener = partyItemClickListener;
        return this;
    }

    @Override
    public RecyclerView.ViewHolder
    onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_party_small, viewGroup, false);
        return new PartyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PartyViewHolder partyViewHolder = (PartyViewHolder) holder;
        Party party = getItem(position);
        //Log.d("pttt", "position= " + position);

        partyViewHolder.party_LBL_title.setText(position + "\n" + party.getTitle());
        partyViewHolder.party_LBL_location.setText(party.getLocation());
        partyViewHolder.party_LBL_date.setText(party.getDate().toString());


        Glide
                .with(activity)
                .load(party.getImage())
                .into(partyViewHolder.party_IMG);

    }

    @Override
    public int getItemCount() {
        return parties.size();
    }

    private Party getItem(int position) {

        return  parties.get(position);
    }

    public interface PartyItemClickListener {
        void partyItemClicked(Party party, int position);
    }

    public class PartyViewHolder extends RecyclerView.ViewHolder {

        public AppCompatImageView party_IMG;
        public MaterialTextView party_LBL_title;
        public MaterialTextView party_LBL_location;
        public MaterialTextView party_LBL_date;

        public PartyViewHolder(final View itemView) {
            super(itemView);
            this.party_IMG = itemView.findViewById(R.id.self_IMG);
            this.party_LBL_title = itemView.findViewById(R.id.self_LBL_name);
            this.party_LBL_location = itemView.findViewById(R.id.party_LBL_location);
            this.party_LBL_date = itemView.findViewById(R.id.party_LBL_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    partyItemClickListener.partyItemClicked(getItem(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
