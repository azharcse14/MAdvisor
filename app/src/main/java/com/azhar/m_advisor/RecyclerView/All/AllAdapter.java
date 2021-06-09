package com.azhar.m_advisor.RecyclerView.All;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.azhar.m_advisor.Data.Playlist;
import com.azhar.m_advisor.MediaPlayerActivity;
import com.azhar.m_advisor.R;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllAdapter extends RecyclerView.Adapter<AllViewHolder> {

    Context context;
    ArrayList<Playlist> playlists;

    public AllAdapter(Context context, ArrayList<Playlist> playlists) {
        this.context = context;
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public AllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.child_layout, parent, false);

        AllViewHolder allViewHolder = new AllViewHolder(view);

        return allViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllViewHolder holder, int position) {

        Shimmer shimmer = new Shimmer.ColorHighlightBuilder()
                .setBaseColor(Color.parseColor("#F3F3F3"))
                .setBaseAlpha(1)
                .setHighlightColor(Color.parseColor("#E7E7E7"))
                .setHighlightAlpha(1)
                .setDropoff(50)
                .build();

        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);

        Playlist playlist = playlists.get(position);

        ImageView icon = holder.iconImg;
        Picasso.get().load(playlist.getImage()).into(icon);

        TextView nameTv = holder.nameTv;
        nameTv.setText(playlists.get(position).getTitle());

        TextView providerTv = holder.providerTv;
        providerTv.setText(playlist.getProvider());

        TextView startTimeTv = holder.startTimeTv;
        startTimeTv.setText(playlist.getDate());

        ImageView playImg = holder.playImg;


        playImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MediaPlayerActivity.class);
                intent.putExtra("name", playlist.getTitle());
                intent.putExtra("URI", playlist.getUrl());
                intent.putExtra("Schedule", "0");
                intent.putExtra("Start_Time", playlist.getDate());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }
}
