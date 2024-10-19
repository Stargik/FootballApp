package com.example.footballapp.ui.footballclubs;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.footballapp.ui.footballclubs.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.footballapp.databinding.FragmentItemBinding;

import java.text.DecimalFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
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
}