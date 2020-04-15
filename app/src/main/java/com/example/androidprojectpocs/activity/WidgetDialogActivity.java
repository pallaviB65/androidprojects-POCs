package com.example.androidprojectpocs.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.androidprojectpocs.R;

public class WidgetDialogActivity extends Activity {
    private Button btnSubmit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.widgetdialog_activity);
        String eventName = "Android Training";
        EditText txt = (EditText) findViewById(R.id.title2);
        txt.setText(eventName);
        String eventDetails = "Details of Training";
       EditText txt2 =(EditText) findViewById(R.id.description2);
       txt2.setText(eventDetails);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
            });

    }

}
