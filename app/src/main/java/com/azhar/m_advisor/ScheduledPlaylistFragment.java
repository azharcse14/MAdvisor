package com.azhar.m_advisor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.azhar.m_advisor.Data.Playlist;
import com.azhar.m_advisor.Data.ScheduledPlaylist;
import com.azhar.m_advisor.RecyclerView.Schedule.ScheduleAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.TimeUnit;


public class ScheduledPlaylistFragment extends Fragment {
    RecyclerView recyclerView;
    ScheduleAdapter adapter;
    DatabaseReference databaseReference;
    int currentTime, ct;

    Timer timer;
    private BroadcastReceiver minuteUpdateReceiver;
    IntentFilter intentFilter;

    SwipeRefreshLayout swipeRefreshLayout;

//    ArrayList<Playlist> playlists;
    ArrayList<ScheduledPlaylist> scheduledPlaylists;
    ArrayList<String> arrayListTitle;
    ArrayList<String> arrayListProvaider;
    ArrayList<String> arrayListUri;
    ArrayList<String> arrayListUnixTime;
    ArrayList<String> arrayListStartTime;
    ArrayList<String> arrayListImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scheduled_playlist, container, false);
        recyclerView = view.findViewById(R.id.schedulePlaylistRecyclerViewId);
        swipeRefreshLayout = view.findViewById(R.id.swipRefreshLayoutId);
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule Playlist");

        scheduledPlaylists = new ArrayList<>();
        arrayListTitle = new ArrayList<>();
        arrayListProvaider = new ArrayList<>();
        arrayListUri = new ArrayList<>();
        arrayListUnixTime = new ArrayList<>();
        arrayListStartTime = new ArrayList<>();
        arrayListImage = new ArrayList<>();

//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
////                adapter.notifyDataSetChanged();
//            }
//        }, 1000);

        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        minuteUpdateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ct = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                adapter = new ScheduleAdapter(getContext(), arrayListTitle, arrayListProvaider, arrayListUri, arrayListUnixTime,arrayListStartTime, arrayListImage, ct);
                recyclerView.setAdapter(adapter);
//                Toast.makeText(getContext(), ct,Toast.LENGTH_LONG).show();
                System.out.println("currentTime ->>> "+ct);
                adapter.notifyDataSetChanged();
            }
        };

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        currentTime = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

        adapter = new ScheduleAdapter(getContext(), arrayListTitle, arrayListProvaider, arrayListUri, arrayListUnixTime,arrayListStartTime, arrayListImage, currentTime);

        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ScheduledPlaylist playlist = dataSnapshot.getValue(ScheduledPlaylist.class);

                    int unixTime = Integer.parseInt(Objects.requireNonNull(playlist.getUnix()));
                    int audioLength = Integer.parseInt(Objects.requireNonNull(playlist.getLength()));


                    int finishingTime = unixTime + audioLength;

                    System.out.println("startTime" +unixTime);



                    System.out.println("currentTime: "+currentTime);
                    if (currentTime < finishingTime) {
                        arrayListTitle.add(playlist.getTitle());
                        //Deleting Same entries
                        HashSet<String> hashSetTitle = new HashSet<String>();
                        hashSetTitle.addAll(arrayListTitle);
                        arrayListTitle.clear();
                        arrayListTitle.addAll(hashSetTitle);

                        //====================================================
                        arrayListProvaider.add(playlist.getProvider());
                        //Deleting Same entries
                        HashSet<String> hashSetProvaider = new HashSet<String>();
                        hashSetProvaider.addAll(arrayListProvaider);
                        arrayListProvaider.clear();
                        arrayListProvaider.addAll(hashSetProvaider);

                        //================================================
                        arrayListUri.add(playlist.getUrl());
                        //Deleting Same entries
                        HashSet<String> hashSetUri = new HashSet<String>();
                        hashSetUri.addAll(arrayListUri);
                        arrayListUri.clear();
                        arrayListUri.addAll(hashSetUri);

                        //================================================
                        arrayListUnixTime.add(playlist.getUnix());
                        //Deleting Same entries
                        HashSet<String> hashSetUnixTime = new HashSet<String>();
                        hashSetUnixTime.addAll(arrayListUnixTime);
                        arrayListUnixTime.clear();
                        arrayListUnixTime.addAll(hashSetUnixTime);

                        //================================================
                        arrayListStartTime.add(playlist.getStart());
                        //Deleting Same entries
                        HashSet<String> hashSetStartTime = new HashSet<String>();
                        hashSetStartTime.addAll(arrayListStartTime);
                        arrayListStartTime.clear();
                        arrayListStartTime.addAll(hashSetStartTime);

                        //================================================
                        arrayListImage.add(playlist.getImage());
                        //Deleting Same entries
                        HashSet<String> hashSetImage = new HashSet<String>();
                        hashSetImage.addAll(arrayListImage);
                        arrayListImage.clear();
                        arrayListImage.addAll(hashSetImage);
                    }

                    adapter.notifyDataSetChanged();
                }



                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    public void startMinuteUpdater() {


    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(minuteUpdateReceiver, intentFilter);
    }
    @Override
    public void onPause() {
        super.onPause();

        getActivity().unregisterReceiver(minuteUpdateReceiver);
    }
}