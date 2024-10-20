package com.example.footballapp.ui.footballclubs;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.footballapp.databinding.FragmentItemBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyFootballClubsRecyclerViewAdapter extends RecyclerView.Adapter<MyFootballClubsRecyclerViewAdapter.FootballClubsHolder> {

    Context context;
    private List<FootballClub> footballClubs;

    public MyFootballClubsRecyclerViewAdapter(Context context, List<FootballClub> items) {
        this.context = context;
        footballClubs = items;
    }

    @Override
    public FootballClubsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    return new FootballClubsHolder(FragmentItemBinding.inflate(LayoutInflater.from(context), parent, false));

    }

    @Override
    public void onBindViewHolder(final FootballClubsHolder holder, int position) {
        holder.nameView.setText(footballClubs.get(position).getName());
        holder.rankView.setText(String.format("%.3f", footballClubs.get(position).getRank()));
        FootballClub footballClub = footballClubs.get(position);

        holder.itemView.setOnClickListener(view -> openPathToFootballClub(footballClub));
    }

    @Override
    public int getItemCount() {
        return footballClubs.size();
    }

    public class FootballClubsHolder extends RecyclerView.ViewHolder {
        public final TextView nameView;
        public final TextView rankView;

        public FootballClubsHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            nameView = binding.itemName;
            rankView = binding.itemRank;
        }
    }

    public void setFootballClubs(List<FootballClub> items) {
        footballClubs = items;
        this.notifyDataSetChanged();
    }

    private void openPathToFootballClub(FootballClub footballClub){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(footballClub.getCity(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                double latitude = address.getLatitude();
                double longitude = address.getLongitude();
                Intent mapIntent = new Intent(context, MapsActivity.class);
                mapIntent.putExtra("latitude", latitude);
                mapIntent.putExtra("longitude", longitude);
                context.startActivity(mapIntent);
            } else {
                Toast.makeText(context, "City is not exist", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(context, "City is not exist", Toast.LENGTH_SHORT).show();
        }
    }

}