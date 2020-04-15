package com.example.androidprojectpocs.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.androidprojectpocs.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = (DrawerLayout)findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, dl,R.string.open, R.string.close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.voiceRecognition:
                        Intent i = new Intent(MainActivity.this, SpeechToTextActivity.class);
                        startActivity(i);
                        break;
                    case R.id.callDialer:
                        Toast.makeText(MainActivity.this, "Call dialer POC", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.reminder:
                        Toast.makeText(MainActivity.this, "Event reminder POC", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.indoorMaps:
                        Toast.makeText(MainActivity.this, "Google Indoor POC", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.widgets:
                        Toast.makeText(MainActivity.this, "Customisable widgets POC", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }
                return true;

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
