package com.example.weshowedup2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder {

    ImageView mImageView;
    TextView mTitle, mLocation, mData, mOrganiser;

    public MyHolder(@NonNull View itemView) {
        super(itemView);

        this.mImageView = itemView.findViewById(R.id.image_view);
        this.mTitle = itemView.findViewById(R.id.title_view);
        this.mLocation = itemView.findViewById(R.id.location_view);
        this.mData = itemView.findViewById(R.id.data_view);
        this.mOrganiser = itemView.findViewById(R.id.oraniser_view);
    }
}
