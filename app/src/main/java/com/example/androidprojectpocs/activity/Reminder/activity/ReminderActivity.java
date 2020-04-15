package com.example.androidprojectpocs.activity.Reminder.activity;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.androidprojectpocs.R;
import com.example.androidprojectpocs.activity.Reminder.activity.adapter.RecyclerAdapter;
import com.example.androidprojectpocs.activity.Reminder.activity.item.RecyclerItemClickListener;
import com.example.androidprojectpocs.activity.Reminder.activity.item.RecyclerViewClickListener;
import com.example.androidprojectpocs.activity.Reminder.activity.item.SyncTask;
import com.example.androidprojectpocs.activity.Reminder.activity.utils.DatabaseProcess;
import com.example.androidprojectpocs.activity.Reminder.activity.utils.Events;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ReminderActivity extends AppCompatActivity implements RecyclerViewClickListener,
        EasyPermissions.PermissionCallbacks {
    public static GoogleAccountCredential mCredential;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    public static final int REQUEST_AUTHORIZATION = 1001;
    public static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};
    // TextView txtHello;
    DatabaseProcess databaseProcess;
    public static RecyclerView lstEvent;
    List<Events> listViewItems;
    public static RecyclerAdapter recyclerAdapter2;

    public static Context context;
    ProgressDialog progress;

    public static SharedPreferences sharedPreferences;


    private enum AppStart {
        FIRST_TIME, FIRST_TIME_VERSION, NORMAL
    }

    private static final String LAST_APP_VERSION = "last_app_version";
    public static final String IS_USE_SYNC = "is_use_sync";
    public static final String CAL_ID = "cal_id";
    public static final String DISPLAY_DAY = "display";

    int currentVersionCode;

   private Menu result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        context = getApplication().getApplicationContext();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        progress = new ProgressDialog(ReminderActivity.this);

        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
        mCredential.setSelectedAccountName(getPreferences(Context.MODE_PRIVATE)
                .getString(PREF_ACCOUNT_NAME, null));

        lstEvent = (RecyclerView) findViewById(R.id.lstEvent);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        databaseProcess = new DatabaseProcess(context);

        switch (checkAppStart()) {
            case NORMAL:
                Intent i = getIntent();
                if(i.getIntExtra("fromAddingEvent", 0) == 0) {
                    databaseProcess.dropAllTable();
                    databaseProcess = new DatabaseProcess(context);
                    databaseProcess.initializeFirstTime();
                    if (sharedPreferences.getBoolean(IS_USE_SYNC, true)) {
                        if (isDeviceOnline()) {
                           new SyncTask(ReminderActivity.this).execute();
                            getResultsFromApi();
                        }
                    }
                }
                break;
            case FIRST_TIME_VERSION:
                sharedPreferences.edit().putInt(LAST_APP_VERSION, currentVersionCode).apply();
                break;
            case FIRST_TIME:
                try {
                    databaseProcess.initializeFirstTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sharedPreferences.edit().putInt(LAST_APP_VERSION, currentVersionCode).apply();
                sharedPreferences.edit().putBoolean(IS_USE_SYNC, false).apply();
                sharedPreferences.edit().putBoolean(DISPLAY_DAY, true).apply();
                break;
            default:
                break;
        }
        // scheduleNotification(getNotification("5 second delay"), 5000);
        listViewItems = rearrangeList(databaseProcess.getAllEvent(-1));
        recyclerAdapter2 = new RecyclerAdapter(this, listViewItems, this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        lstEvent.setLayoutManager(mLayoutManager);
        lstEvent.setAdapter(recyclerAdapter2);
        lstEvent.addOnItemTouchListener(
                new RecyclerItemClickListener(context
                        , new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ((FoldingCell) view).toggle(false);
                        recyclerAdapter2.registerToggle(position);
                    }
                })
        );
    }


    @Override
    public void recyclerViewListClicked(int button, View v, final int position) {
        if (button == 1) {
            Events listViewItem = listViewItems.get(position);
            Intent intent = new Intent(ReminderActivity.this, AddingEventActivity.class);
            intent.putExtra("id", listViewItem.getId());
            intent.putExtra("name", listViewItem.getName());
            intent.putExtra("loop", listViewItem.getLoop());
            intent.putExtra("spinner", listViewItem.getKind() - 1);
            intent.putExtra("date", listViewItem.getDate());
            intent.putExtra("img", listViewItem.getImg());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            databaseProcess.deleteWaitingEvent(listViewItems.get(position).getId());
            if (sharedPreferences.getBoolean(IS_USE_SYNC, false) && isDeviceOnline()) {
                new SyncTask(listViewItems.get(position).getIdSync()
                        , ReminderActivity.this).execute();
            }
            listViewItems = recyclerAdapter2.removeAt(position);
        }
    }

    public void addEvent(View target) {
        Intent intent = new Intent(ReminderActivity.this, AddingEventActivity.class);
        startActivity(intent);
    }

    public AppStart checkAppStart() {
        PackageInfo pInfo;

        AppStart appStart = AppStart.NORMAL;
        try {
            int lastVersionCode = sharedPreferences.getInt(LAST_APP_VERSION, -1);
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            currentVersionCode = pInfo.versionCode;
            appStart = checkAppStart(currentVersionCode, lastVersionCode);

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "Unable to determine current app version from pacakge manager."
                    + " Defensively assuming normal app start.", Toast.LENGTH_SHORT).show();
        }
        return appStart;
    }

    public AppStart checkAppStart(int currentVersionCode, int lastVersionCode) {
        if (lastVersionCode == -1) {
            return AppStart.FIRST_TIME;
        } else if (lastVersionCode < currentVersionCode) {
            return AppStart.FIRST_TIME_VERSION;
        } else {
            return AppStart.NORMAL;
        }
    }

    public static List<Events> rearrangeList(List<Events> listViewItems) {
        int countPositive = 0;
        int countZero = 0;
        for (int i = 0; i < listViewItems.size() - 1; i++) {
            for (int j = i + 1; j < listViewItems.size(); j++) {
                Events e1 = listViewItems.get(i);
                Events e2 = listViewItems.get(j);
                if (e1.getDiff() < e2.getDiff()) {
                    Collections.swap(listViewItems, i, j);
                }
            }
        }
        for (int i = 0; i < listViewItems.size(); i++) {
            if (listViewItems.get(i).getDiff() > 0)
                countPositive++;
            if (listViewItems.get(i).getDiff() == 0)
                countZero++;
        }
        List<Events> items = new ArrayList<>();
        for (int i = countPositive; i < countPositive + countZero; i++) {
            if (listViewItems.get(i).getDeleted() == 0)
                items.add(listViewItems.get(i));
        }
        for (int i = countPositive - 1; i > -1; i--) {
            if (listViewItems.get(i).getDeleted() == 0)
                items.add(listViewItems.get(i));
        }
        for (int i = countPositive + countZero; i < listViewItems.size(); i++) {
            if (listViewItems.get(i).getDeleted() == 0)
                items.add(listViewItems.get(i));
        }
        return items;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_main_0:
                getResultsFromApi();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();
    }

    public void getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (!isDeviceOnline()) {
            Toast.makeText(ReminderActivity.this,
                    "No network connection available.", Toast.LENGTH_LONG).show();
        } else {
            if (sharedPreferences.getBoolean(IS_USE_SYNC, false))
                sharedPreferences.edit().putBoolean(IS_USE_SYNC, true).apply();
            new SyncTask(ReminderActivity.this).execute();
        }
    }

    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {

                    Toast.makeText(ReminderActivity.this,
                            "This app requires Google Play Services. Please install \" +\n" +
                                    " \"Google Play Services on your device and relaunch this app."
                            , Toast.LENGTH_LONG).show();

                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    public boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    public void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                ReminderActivity.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }
}
