package com.azhar.m_advisor.RecyclerView.Schedule;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.azhar.m_advisor.MediaPlayerActivity;
import com.azhar.m_advisor.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {

    Context context;
    ArrayList<String> arrayListTitle;
    ArrayList<String> arrayListProvaider;
    ArrayList<String> arrayListUri;
    ArrayList<String> arrayListUnixTime;
    ArrayList<String> arrayListStartTime;
    ArrayList<String> arrayListImage;

    int timeStamp;

    public ScheduleAdapter(Context context, ArrayList<String> arrayListTitle, ArrayList<String> arrayListProvaider, ArrayList<String> arrayListUri, ArrayList<String> arrayListUnixTime, ArrayList<String> arrayListStartTime, ArrayList<String> arrayListImage, int timeStamp) {
        this.context = context;
        this.arrayListTitle = arrayListTitle;
        this.arrayListProvaider = arrayListProvaider;
        this.arrayListUri = arrayListUri;
        this.arrayListUnixTime = arrayListUnixTime;
        this.arrayListStartTime = arrayListStartTime;
        this.arrayListImage = arrayListImage;
        this.timeStamp = timeStamp;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.child_layout, parent, false);

        ScheduleViewHolder scheduleViewHolder = new ScheduleViewHolder(view);

        return scheduleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  ScheduleViewHolder holder, int position) {


        ImageView icon = holder.iconImg;
        Picasso.get().load(arrayListImage.get(position)).into(icon);

        TextView nameTv = holder.nameTv;
        nameTv.setText(arrayListTitle.get(position));

        TextView providerTv = holder.providerTv;
        providerTv.setText(arrayListProvaider.get(position));

        TextView startTimeTv = holder.startTimeTv;
        startTimeTv.setText(arrayListStartTime.get(position));

        ImageView playImg = holder.playImg;

//        String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

//        timeStamp = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());


        int schedule = Integer.parseInt(arrayListUnixTime.get(position));

        System.out.println("schedule: "+schedule);

        System.out.println("timeStamp: "+timeStamp);

            playImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (timeStamp >= schedule) {
                        Intent intent = new Intent(context, MediaPlayerActivity.class);
                        intent.putExtra("name", arrayListTitle.get(position));
                        intent.putExtra("URI", arrayListUri.get(position));
                        intent.putExtra("Schedule", arrayListUnixTime.get(position));
                        intent.putExtra("Start_Time", arrayListStartTime.get(position));

                        context.startActivity(intent);
                    }else {
                        Toast.makeText(context, "Please Wait", Toast.LENGTH_LONG).show();
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return arrayListTitle.size();
    }
}
