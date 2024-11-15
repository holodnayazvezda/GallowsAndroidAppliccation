package com.example.artemapp.gui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.artemapp.R;


public class WordInputActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_input_activity);

        EditText textToGuess = findViewById(R.id.guessedText);
        Button nextBtn = findViewById(R.id.nextBtn);

        nextBtn.setOnClickListener(v -> {
            String word = textToGuess.getText().toString().trim();
            if (!word.isEmpty()) {
                Intent intent = new Intent(WordInputActivity.this, PassToAnotherUserActivity.class);
                intent.putExtra("word", word);
                startActivity(intent);
            } else {
                Toast.makeText(WordInputActivity.this, "Введите слово", Toast.LENGTH_SHORT).show();
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(WordInputActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
