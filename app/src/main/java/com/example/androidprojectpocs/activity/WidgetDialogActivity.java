package com.example.androidprojectpocs.activity;


import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.androidprojectpocs.R;

public class WidgetDialogActivity extends Activity {

    private Button btnSubmit;
    private EditText txt2;
    public static final String SHARED_PREFS = "prefs";
    public static final String KEY_BUTTON_TEXT = "keyButtonText";

    private  int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.widgetdialog_activity);
        String eventName = "Android training";
        TextView txt = (TextView) findViewById(R.id.title2);
        txt.setText(eventName);
        String eventDetails = "Details of training";
        txt2 =(EditText) findViewById(R.id.description2);
        txt2.setText(eventDetails);
        // efforts = (EditText)findViewById(R.id.effortNum2);
        //  efforts.setText("10");
      /* Intent configIntent = getIntent();
       Bundle extras = configIntent.getExtras();
       if(extras != null){
           appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
       }
       if(appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID){
           finish();
       }
        btnSubmit = findViewById(R.id.btnSubmit);*/
      /* btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(WidgetDialogActivity.this);

                Intent intent = new Intent(WidgetDialogActivity.this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(WidgetDialogActivity.this, 0, intent, 0);

                String buttonText = txt.getText().toString();

                RemoteViews views = new RemoteViews(WidgetDialogActivity.this.getPackageName(), R.layout.new_app_widget);
                views.setOnClickPendingIntent(R.id.title1, pendingIntent);
                views.setCharSequence(R.id.title1, "setText", buttonText);

                appWidgetManager.updateAppWidget(appWidgetId, views);

                SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(KEY_BUTTON_TEXT + appWidgetId, buttonText);
                editor.apply();

                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }


        });*/
    }
    public void onSubmit(View v)
    {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(WidgetDialogActivity.this);

        Intent intent = new Intent(WidgetDialogActivity.this, WidgetDialogActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(WidgetDialogActivity.this, 0, intent, 0);

        String buttonText = txt2.getText().toString();

        RemoteViews views = new RemoteViews(WidgetDialogActivity.this.getPackageName(), R.layout.new_app_widget);
        views.setOnClickPendingIntent(R.id.title1, pendingIntent);
        views.setCharSequence(R.id.title1, "setText", buttonText);

        appWidgetManager.updateAppWidget(appWidgetId, views);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_BUTTON_TEXT + appWidgetId, buttonText);
        editor.apply();

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();

    }

}
