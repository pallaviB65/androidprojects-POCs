package com.example.androidprojectpocs.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidprojectpocs.R;

import java.util.ArrayList;
import java.util.Locale;

public class CreateFromVoiceActivity extends AppCompatActivity {
    private EditText titleET, boardET, descriptionET;
    private TextView titleSpeaker, boardSpeaker;
    private Button createBT;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private Boolean titleB = false, boardB = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_from_voice);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        titleET = findViewById(R.id.titleET);
        boardET = findViewById(R.id.boardET);
        descriptionET = findViewById(R.id.descriptionET);
        titleSpeaker = findViewById(R.id.titleSpeaker);
        boardSpeaker = findViewById(R.id.boardSpeaker);
        createBT = findViewById(R.id.createBT);

        titleSpeaker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
                titleB = true;
                boardB = false;
            }
        });

        boardSpeaker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
                titleB = false;
                boardB = true;
            }
        });

        createBT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!titleET.getText().toString().isEmpty() &&  titleET.getText().toString().length() > 3 && !boardET.getText().toString().isEmpty() && boardET.getText().toString().length() > 3){
                    Toast.makeText(CreateFromVoiceActivity.this, "Item created successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CreateFromVoiceActivity.this, DisplayCreatedItemActivity.class);
                    i.putExtra("title", titleET.getText().toString());
                    i.putExtra("board", boardET.getText().toString());
                    i.putExtra("description", descriptionET.getText().toString());
                    startActivity(i);
                }else{
                    Toast.makeText(CreateFromVoiceActivity.this, "Please fill title and board name", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(titleB){
                        titleET.setText(result.get(0));
                    }else{
                        boardET.setText(result.get(0));
                    }

                }
                break;
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
