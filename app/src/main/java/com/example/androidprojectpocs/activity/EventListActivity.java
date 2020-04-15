package com.example.androidprojectpocs.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidprojectpocs.R;

import io.paperdb.Paper;

public class EventListActivity extends AppCompatActivity {
    private Button btnPin;
    private Context mContext;
    TextView eventName;
    TextView eventDetails;
    SharedPreferences sharedPreferences;
    static final String eName = "NameKey";
    static final String eDetails = "DetailsKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = EventListActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        btnPin = findViewById(R.id.btnPin);
        Paper.init(this);
        eventName = (TextView)findViewById(R.id.title);
        eventDetails = (TextView)findViewById(R.id.description);
        btnPin.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                String tempName = eventName.getText().toString();
                Log.i("tempnME","TEmp...");
                String tempDetails = eventDetails.getText().toString();
                Paper.book().write("name",tempName);
                Paper.book().write("details",tempDetails);
                //SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                //editor.putString(eName,tempName);
                // editor.putString(eDetails,tempDetails);
                // editor.apply();

                //  SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                //  eventDetails.setText(prefs.getString(eName,""));
                //  eventName.setText(prefs.getString(eDetails,""));
            }
        });
    }
}
