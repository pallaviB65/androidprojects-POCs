package com.example.androidprojectpocs.activity.voicerecognition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.androidprojectpocs.R;

public class DisplayCreatedItemActivity extends AppCompatActivity {
    private TextView titleTV, boardNameTV, descriptionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_created_item);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        titleTV = findViewById(R.id.titleTV);
        boardNameTV = findViewById(R.id.boardNameTV);
        descriptionTV = findViewById(R.id.descriptionTV);

        if (getIntent() != null) {
            titleTV.setText(getIntent().getStringExtra("title"));
            boardNameTV.setText(getIntent().getStringExtra("board"));
            descriptionTV.setText(getIntent().getStringExtra("description"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }
}
