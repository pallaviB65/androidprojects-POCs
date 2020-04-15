package com.example.androidprojectpocs.activity.Reminder.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.androidprojectpocs.R;
import com.example.androidprojectpocs.activity.Reminder.activity.utils.Constants;

import androidx.recyclerview.widget.RecyclerView;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            this.imageView = (ImageView) v.findViewById(R.id.image_item_image);
        }
    }

    @Override
    public int getItemCount() {
        return Constants.background.length;
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_adapter_item, parent, false);
        v.setLayoutParams(new LinearLayout.LayoutParams(parent.getWidth() / 2,
                parent.getWidth() / 4));
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.imageView.setImageResource(Constants.background[position]);
    }

}
