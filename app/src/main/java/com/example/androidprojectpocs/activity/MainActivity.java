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
import com.example.androidprojectpocs.activity.Reminder.activity.ReminderActivity;
import com.example.androidprojectpocs.activity.calldialer.CallDialerActivity;
import com.example.androidprojectpocs.activity.voicerecognition.CreateActivity;
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
                        Intent i = new Intent(MainActivity.this, CreateActivity.class);
                        startActivity(i);
                        break;
                    case R.id.callDialer:
                        Intent in = new Intent(MainActivity.this, CallDialerActivity.class);
                        startActivity(in);
                        break;
                    case R.id.reminder:
                       Intent intent = new Intent(MainActivity.this, ReminderActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.indoorMaps:
                        Toast.makeText(MainActivity.this, "Google Indoor POC", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.widgets:
                        Intent intent1 = new Intent(MainActivity.this, EventListActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.darkmode:
                        Intent darkintent = new Intent(MainActivity.this, DarkModeActivity.class);
                        startActivity(darkintent);
                        break;
                    case R.id.multiLanguage:
                        Intent newIntent = new Intent(MainActivity.this, MultiLanguageActivity.class);
                        startActivity(newIntent);
                        break;
                    case R.id.gestures:
                        //launch your activity from here
                        Toast.makeText(MainActivity.this, "Gestures POC", Toast.LENGTH_SHORT).show();
                    case R.id.splitScreen:
                        //launch your activity from here
                        Intent splitScreenIntent = new Intent(MainActivity.this, SplitScreenActivity.class);
                        startActivity(splitScreenIntent);
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
