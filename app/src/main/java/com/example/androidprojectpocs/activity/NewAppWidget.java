package com.example.androidprojectpocs.activity;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.androidprojectpocs.R;

import io.paperdb.Paper;
import static com.example.androidprojectpocs.activity.WidgetDialogActivity.KEY_BUTTON_TEXT;
import static com.example.androidprojectpocs.activity.WidgetDialogActivity.SHARED_PREFS;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    private static final String SHOW_DIALOG_ACTION = "com.example.androidprojectpocs.activity.widgetshowdialog";

    private void prepareWidget(Context context) {
        // static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
        // int appWidgetId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, NewAppWidget.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int appWidgetId : allWidgetIds) {
            Paper.init(context);
            String eventName = Paper.book().read("name");
            String eventDetails = Paper.book().read("details");
            //  String efforts = Paper.book().read("efforts");
            SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
            String buttonText = prefs.getString(KEY_BUTTON_TEXT+appWidgetId,eventName);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

            views.setTextViewText(R.id.title1, eventName);
            views.setTextViewText(R.id.description1, eventDetails);
            // views.setTextViewText(R.id.effortNum1, efforts);
            Intent intent = new Intent(context, NewAppWidget.class);
            intent.setAction(SHOW_DIALOG_ACTION);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.description1, pendingIntent);
            // views.setOnClickPendingIntent(R.id.effortNum1, pendingIntent);
            views.setCharSequence(R.id.title1,"setText", buttonText);

        }
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        ////////
        prepareWidget(context);
        super.onUpdate(context,appWidgetManager, appWidgetIds);
        //////
      //  for (int appWidgetId : appWidgetIds) {
      //      updateAppWidget(context, appWidgetManager, appWidgetId);
       //     }
    }
    @Override public void onReceive(final Context context, Intent intent) {
        if(intent.getAction().equals(SHOW_DIALOG_ACTION)){
            Intent i = new Intent(context, WidgetDialogActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
   }

