package com.example.androidprojectpocs.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidprojectpocs.R;

import java.util.Locale;


public class SplitScreenActivity extends AppCompatActivity {
    Button loginBT;
    EditText emailET, pwdET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.split_screen_main_layout);
        loginBT = findViewById(R.id.loginBT);
        emailET = findViewById(R.id.emailET);
        pwdET = findViewById(R.id.pwdET);

        Locale.getDefault().getDisplayLanguage();
        loginBT.setText(getApplicationContext().getResources().getString(R.string.login));
        emailET.setHint(getApplicationContext().getResources().getString(R.string.email));
        pwdET.setHint(getApplicationContext().getResources().getString(R.string.password));

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplitScreenActivity.this, SecondSplitScreenActivity.class);
                startActivity(intent);
            }
        });
    }
}