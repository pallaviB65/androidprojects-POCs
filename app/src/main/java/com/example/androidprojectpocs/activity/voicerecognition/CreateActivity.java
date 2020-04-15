package com.example.androidprojectpocs.activity.voicerecognition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidprojectpocs.R;

import java.util.ArrayList;
import java.util.Locale;

public class CreateActivity extends AppCompatActivity {
    private EditText titleET, boardET, descriptionET;
    private TextView titleSpeaker, boardSpeaker;
    private Button createBT;
    private Boolean titleB = false, boardB = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        checkPermission();

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        titleET = findViewById(R.id.titleET);
        boardET = findViewById(R.id.boardET);
        descriptionET = findViewById(R.id.descriptionET);
        titleSpeaker = findViewById(R.id.titleSpeaker);
        boardSpeaker = findViewById(R.id.boardSpeaker);
        //boardSpeaker.setVisibility(View.GONE);
        createBT = findViewById(R.id.createBT);

        final SpeechRecognizer mSpeechRecognizer1 = SpeechRecognizer.createSpeechRecognizer(this);
        //final SpeechRecognizer mSpeechRecognizer2 = SpeechRecognizer.createSpeechRecognizer(this);
        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        //mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, new Long(10000));
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());


        mSpeechRecognizer1.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null){
                    if(titleB){
                        titleET.setText(matches.get(0));
                    }else{
                        boardET.setText(matches.get(0));
                    }

                }

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        titleSpeaker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer1.stopListening();
                        titleET.setHint("You will see input here");
                        break;

                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizer1.startListening(mSpeechRecognizerIntent);
                        titleET.setText("");
                        titleET.setHint("Listening...");
                        titleB = true;
                        boardB = false;
                        break;
                }
                return false;
            }
        });

        boardSpeaker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer1.stopListening();
                        boardET.setHint("You will see input here");
                        break;

                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizer1.startListening(mSpeechRecognizerIntent);
                        boardET.setText("");
                        boardET.setHint("Listening...");
                        titleB = false;
                        boardB = true;
                        break;
                }
                return false;
            }
        });

        createBT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!titleET.getText().toString().isEmpty() &&  titleET.getText().toString().length() > 3 && !boardET.getText().toString().isEmpty() && boardET.getText().toString().length() > 3){
                    Toast.makeText(CreateActivity.this, "Item created successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CreateActivity.this, DisplayCreatedItemActivity.class);
                    i.putExtra("title", titleET.getText().toString());
                    i.putExtra("board", boardET.getText().toString());
                    i.putExtra("description", descriptionET.getText().toString());
                    startActivity(i);
                }else{
                    Toast.makeText(CreateActivity.this, "Please fill title and board name", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
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
