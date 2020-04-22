package com.example.androidprojectpocs.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.androidprojectpocs.R;

import java.util.ArrayList;

public class SecondSplitScreenActivity extends BaseActivity implements RecyclerViewAdapter.ItemClickListener {
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_split_screen);
        
        // data to populate the RecyclerView with
        ArrayList<String> viewNames = new ArrayList<>();
        viewNames.add("View 1");
        viewNames.add("View 2");
        viewNames.add("View 3");
        viewNames.add("View 4");
        viewNames.add("View 5");
        viewNames.add("View 6");
        viewNames.add("View 7");
        viewNames.add("View 8");
        viewNames.add("View 9");
        viewNames.add("View 10");

        // set up the RecyclerView
        recyclerView = findViewById(R.id.rvViews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, viewNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setupRecyclerView(getRecyclerViewOrientation(newConfig.orientation));
    }

    private int getRecyclerViewOrientation(final int orientation) {
        return isOrientationPortrait(orientation) ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL;
    }

    private boolean isOrientationPortrait(final int orientation) {
        return orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    private void setupRecyclerView(final int orientation) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, orientation, false));
        recyclerView.setAdapter(adapter);
    }

}

