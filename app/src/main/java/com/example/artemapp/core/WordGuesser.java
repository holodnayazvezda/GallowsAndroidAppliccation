package com.example.artemapp.core;

import java.util.ArrayList;
import java.util.Locale;

public class WordGuesser {
    private final String wordToGuess;
    private int amountOfAttempts;
    private final ArrayList<String> guessedLetters;
    private final ArrayList<String> unusedLetters;

    public WordGuesser(String wordToGuess) {
        this.wordToGuess = wordToGuess.toLowerCase(Locale.ROOT);
        amountOfAttempts = 2 * wordToGuess.length();
        guessedLetters = new ArrayList<>();
        unusedLetters = new ArrayList<>();

        guessedLetters.add(String.valueOf(wordToGuess.charAt(0)));
        for (int i = 0; i < wordToGuess.length() - 2; i++) {
            guessedLetters.add("");
        }
        guessedLetters.add(String.valueOf(wordToGuess.charAt(wordToGuess.length() - 1)));
    }

    public String getWordToGuessWithAlreadyGuessedLetters() {
        try {
            StringBuilder wordToGuessStringBuilder = new StringBuilder();
            for (int i = 0; i < wordToGuess.length(); i++) {
                String letter = guessedLetters.get(i);
                if (letter.isEmpty()) {
                    wordToGuessStringBuilder.append("?");
                } else {
                    wordToGuessStringBuilder.append(letter.toUpperCase(Locale.ROOT));
                }
            }
            return wordToGuessStringBuilder.toString();
        } catch (IndexOutOfBoundsException e) {
            return "?".repeat(wordToGuess.length());
        }
    }

    public String getStringOfUnusedLetters() {
        StringBuilder unusedLettersString = new StringBuilder();
        for (String letter: unusedLetters) {
            if (unusedLettersString.length() == 0) {
                unusedLettersString.append(letter);
            } else {
                unusedLettersString.append(", ");
                unusedLettersString.append(letter);
            }
        }
        return unusedLettersString.toString();
    }

    public String getAttemsLeftString() {
        return String.format(Locale.ROOT, "Осталось попыткок: %2d", amountOfAttempts);
    }

    public boolean isWin() {
        StringBuilder wordToCheck = new StringBuilder();
        for (String letter: guessedLetters) {
            wordToCheck.append(letter);
        }
        return wordToCheck.toString().contentEquals(wordToGuess);
    }

    public int isGameContinue() {
        if (isWin()) {
            return 1;
        } else if (amountOfAttempts > 0) {
            return 0;
        } else {
            return -1;
        }
    }


    public void guessWord(String letter) {
        letter = letter.toLowerCase(Locale.ROOT);
        if (guessedLetters.contains(letter) || unusedLetters.contains(letter)) {
            return;
        }
        amountOfAttempts --;
        if (wordToGuess.contains(letter)) {
            if (!guessedLetters.contains(letter)) {
                for (int i = 0; i < wordToGuess.length(); i++) {
                    String currLetter = String.valueOf(wordToGuess.charAt(i));
                    if (currLetter.equals(letter)) {
                        guessedLetters.set(i, currLetter);
                    }
                }
            }
        } else {
            unusedLetters.add(letter);
        }
    }
}
