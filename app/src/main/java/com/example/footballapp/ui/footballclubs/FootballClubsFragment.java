package com.example.footballapp.ui.footballclubs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.footballapp.R;
import com.example.footballapp.databinding.FragmentGalleryBinding;
import com.example.footballapp.databinding.FragmentItemListBinding;
import com.example.footballapp.ui.footballclubs.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.List;

public class FootballClubsFragment extends Fragment {
    private FragmentItemListBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.list.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<FootballClub> footballClubs = new ArrayList<>();
        MyFootballClubsRecyclerViewAdapter adapter = new MyFootballClubsRecyclerViewAdapter(requireContext(), footballClubs);
        binding.list.setAdapter(adapter);

        try (FootballClubsDbHelper footballClubsDbHelper = new FootballClubsDbHelper(requireContext())) {

            footballClubsDbHelper.removeAllFootballClubs();

            footballClubsDbHelper.addFootballClub(new FootballClub("Shakhtar", "Donetsk", "Ukraine", 1936, 42.000));
            footballClubsDbHelper.addFootballClub(new FootballClub("Dynamo", "Kyiv", "Ukraine",1927, 20.500));
            footballClubsDbHelper.addFootballClub(new FootballClub("Zorya", "Luhansk", "Ukraine",1923, 16.000));
            footballClubsDbHelper.addFootballClub(new FootballClub("Dnipro-1", "Dnipro", "Ukraine",2015, 12.000));
            footballClubsDbHelper.addFootballClub(new FootballClub("Vorskla", "Poltava", "Ukraine",2015, 4.500));
            footballClubsDbHelper.addFootballClub(new FootballClub("Kryvbas", "Kryvyi Rih", "Ukraine",1959, 2.500));
            footballClubsDbHelper.addFootballClub(new FootballClub("Polissya", "Zhytomir", "Ukraine",1959, 1.500));
            footballClubsDbHelper.addFootballClub(new FootballClub("Kolos", "Kovalivka", "Ukraine",2012, 4.000));
            footballClubsDbHelper.addFootballClub(new FootballClub("Desna", "Chernihiv", "Ukraine",1960, 2.000));
            footballClubsDbHelper.addFootballClub(new FootballClub("Real", "Madrid", "Spain",1902, 121.000));
            footballClubsDbHelper.addFootballClub(new FootballClub("Chelsea", "London", "England",1905, 81.000));
            footballClubsDbHelper.addFootballClub(new FootballClub("Inter", "Milan", "Italy",1908, 79.000));
            //footballClubs = footballClubsDbHelper.getUkrainianFootballClubs();
            //adapter.setFootballClubs(footballClubs);
        }

        binding.allClubsB.setOnClickListener(view1 -> {
            try (FootballClubsDbHelper footballClubsDbHelper = new FootballClubsDbHelper(requireContext())) {
                adapter.setFootballClubs(footballClubsDbHelper.getAllFootballClubs());
            }
        });

        binding.ukClubsB.setOnClickListener(view1 -> {
            try (FootballClubsDbHelper footballClubsDbHelper = new FootballClubsDbHelper(requireContext())) {
                adapter.setFootballClubs(footballClubsDbHelper.getUkrainianFootballClubs());
            }
        });

        binding.kyivClubsRankB.setOnClickListener(view1 -> {
            try (FootballClubsDbHelper footballClubsDbHelper = new FootballClubsDbHelper(requireContext())) {
                double totalKyivRank = footballClubsDbHelper.getTotalKyivFootballClubsRank();
                binding.rankValue.setText(String.format("%.3f", totalKyivRank));
                binding.rankText.setVisibility(View.VISIBLE);
                binding.rankValue.setVisibility(View.VISIBLE);
            }
        });

    }
}