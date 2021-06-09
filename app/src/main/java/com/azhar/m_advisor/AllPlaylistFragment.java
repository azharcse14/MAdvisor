package com.azhar.m_advisor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.azhar.m_advisor.Data.Playlist;
import com.azhar.m_advisor.RecyclerView.All.AllAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AllPlaylistFragment extends Fragment {

    DatabaseReference databaseReference;

    ArrayList<Playlist> playlists;

    RecyclerView recyclerView;
    AllAdapter adapter;
    ProgressBar progressBar;
    ShimmerFrameLayout shimmerFrameLayout;

    public AllPlaylistFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_playlist, container, false);

        progressBar = view.findViewById(R.id.progress_circular);
        recyclerView = view.findViewById(R.id.allPlaylistRecyclerViewId);
        shimmerFrameLayout = view.findViewById(R.id.shimmerFrameLayoutId);
        databaseReference = FirebaseDatabase.getInstance().getReference("All Playlist");
        playlists = new ArrayList<>();
        adapter = new AllAdapter(getContext(), playlists);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Playlist playlist = dataSnapshot.getValue(Playlist.class);
                    playlists.add(playlist);

                    progressBar.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    System.out.println(playlist.getUrl());
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

}