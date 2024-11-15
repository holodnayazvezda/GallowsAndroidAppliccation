package com.example.artemapp.gui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.artemapp.R;

import java.util.Objects;

public class PassToAnotherUserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pass_to_another_user);

        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(PassToAnotherUserActivity.this, GuessWordActivity.class);
            intent.putExtras(Objects.requireNonNull(getIntent().getExtras()));
            startActivity(intent);
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(PassToAnotherUserActivity.this, WordInputActivity.class);
                intent.putExtras(Objects.requireNonNull(getIntent().getExtras()));
                startActivity(intent);
                finish();
            }
        });
    }
}
