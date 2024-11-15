package com.example.artemapp.gui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.artemapp.R;
import com.example.artemapp.core.WordGuesser;

import java.util.Objects;

public class GuessWordActivity extends AppCompatActivity {
    WordGuesser wordGuesser;

    Button checkButton;
    EditText letterToGuess;
    TextView wordToGuess;
    TextView unusedLetters;
    TextView attemptsLeft;

    public void updateTexts() {
        wordToGuess.setText(wordGuesser.getWordToGuessWithAlreadyGuessedLetters());
        unusedLetters.setText(wordGuesser.getStringOfUnusedLetters());
        attemptsLeft.setText(wordGuesser.getAttemsLeftString());
    }

    public void checkLetter() {
        String letter = letterToGuess.getText().toString().trim();
        if (letter.isEmpty()) {
            Toast.makeText(this, "Введите букву!", Toast.LENGTH_SHORT).show();
            return;
        }
        wordGuesser.guessWord(letter);
        updateTexts();
        letterToGuess.setText("");
        int game = wordGuesser.isGameContinue();
        if (game == 1) {
            showWinDialog();
        } else if (game == -1) {
            showGameOverDialog();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_word_activity);

        wordGuesser = new WordGuesser(Objects.requireNonNull(getIntent().getStringExtra("word")));

        checkButton = findViewById(R.id.checkButton);
        letterToGuess = findViewById(R.id.letterToCheck);
        wordToGuess = findViewById(R.id.wordToGuess);
        unusedLetters = findViewById(R.id.unusedLetters);
        attemptsLeft = findViewById(R.id.attemptsLeft);

        // Устонавливаем текст на textViews
        updateTexts();

        // Ограничиваем ввод только одной буквы
        letterToGuess.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        // Автоматически вводить заглавные буквы
        letterToGuess.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        // привязываем действие при нажатии галочки на клавиатуре
        letterToGuess.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                checkLetter();
                return true;
            }
            return false;
        });

        checkButton.setOnClickListener(v -> checkLetter());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(GuessWordActivity.this, PassToAnotherUserActivity.class);
                intent.putExtras(Objects.requireNonNull(getIntent().getExtras()));
                startActivity(intent);
                finish();
            }
        });
    }

    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Вы проиграли!")
                .setMessage("Вы не угадали слово. Хотите попробовать снова?")
                .setCancelable(false) // Диалог нельзя закрыть кнопкой "Назад" или кликом вне окна
                .setPositiveButton("Начать сначала", (dialog, which) -> restartActivity());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showWinDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Поздравляем!")
                .setMessage("Вы угадали слово! Хотите сыграть ещё раз?")
                .setCancelable(false) // Диалог нельзя закрыть кнопкой "Назад" или кликом вне окна
                .setPositiveButton("Ещё раз", (dialog, which) -> goToInputWordActivity());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void restartActivity() {
        Intent intent = new Intent(this, GuessWordActivity.class);
        intent.putExtras(Objects.requireNonNull(getIntent().getExtras())); // Передаём слово снова
        startActivity(intent);
        finish(); // Закрываем текущую активность
    }

    private void goToInputWordActivity() {
        Intent intent = new Intent(this, WordInputActivity.class);
        startActivity(intent);
        finish(); // Закрываем текущую активность
    }
}
