package org.example;

import java.util.regex.*;
import java.util.HashMap;
import java.util.Map;

public class Translator {
    private final Map<String, String> dictionary;

    public Translator() {
        this.dictionary = new HashMap<>();
    }

    public void addWordPair(String english, String ukrainian) {
        dictionary.put(english.toLowerCase(), ukrainian);
    }

    public String translatePhrase(String englishPhrase) {
        validatePhrase(englishPhrase);

        String translated = translateTokens(englishPhrase);
        return capitalizeFirstLetter(translated);
    }

    private void validatePhrase(String phrase) {
        if (phrase == null || phrase.trim().isEmpty()) {
            throw new IllegalArgumentException("The phrase for translation is empty");
        }
    }

    private String translateTokens(String phrase) {
        StringBuilder translatedPhrase = new StringBuilder();

        Pattern pattern = Pattern.compile("\\w+|[.,!?;:]");
        Matcher matcher = pattern.matcher(phrase);

        boolean firstToken = true;
        while (matcher.find()) {
            String token = matcher.group();

            if (isWord(token)) {
                String translation = translateWord(token);
                if (!firstToken) {
                    translatedPhrase.append(" ");
                }
                translatedPhrase.append(translation);
            } else {
                translatedPhrase.append(token);
            }

            firstToken = false;
        }

        return translatedPhrase.toString().trim();
    }

    private boolean isWord(String token) {
        return token.matches("\\w+");
    }

    private String translateWord(String word) {
        return dictionary.getOrDefault(word.toLowerCase(), "[" + word + "]");
    }

    private String capitalizeFirstLetter(String phrase) {
        if (phrase.isEmpty()) return phrase;
        return phrase.substring(0, 1).toUpperCase() + phrase.substring(1);
    }
}
