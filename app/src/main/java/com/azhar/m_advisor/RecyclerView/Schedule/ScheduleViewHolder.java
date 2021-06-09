package com.azhar.m_advisor.RecyclerView.Schedule;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.azhar.m_advisor.R;

public class ScheduleViewHolder extends RecyclerView.ViewHolder {
    TextView nameTv, providerTv, startTimeTv;
    ImageView iconImg, playImg;

    public ScheduleViewHolder(@NonNull View itemView) {
        super(itemView);

        nameTv = itemView.findViewById(R.id.speakerTvId);
        providerTv = itemView.findViewById(R.id.providerTvId);
        startTimeTv = itemView.findViewById(R.id.startTimeTvId);
        iconImg = itemView.findViewById(R.id.icon);
        playImg = itemView.findViewById(R.id.playImgId);
    }
}
