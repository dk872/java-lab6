package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Translator translator = new Translator();

        System.out.println("Console Application: English-Ukrainian Translator.");
        System.out.println("-----------------------------------------------------");

        populateInitialDictionary(translator);

        try (Scanner scanner = new Scanner(System.in)) {
            addNewWordPairs(translator, scanner);
            translatePhrase(translator, scanner);
        } catch (Exception e) {
            System.err.println("An error occurred during application runtime: " + e.getMessage());
        }
    }

    private static void populateInitialDictionary(Translator translator) {
        System.out.println("1. Populating dictionary with initial words...");

        translator.addWordPair("hello", "привіт");
        translator.addWordPair("world", "світ");
        translator.addWordPair("java", "джава");
        translator.addWordPair("programming", "програмування");
        translator.addWordPair("is", "є");
        translator.addWordPair("great", "чудовий");
        translator.addWordPair("a", "це");
        translator.addWordPair("language", "мова");
        translator.addWordPair("it's", "це є");
        translator.addWordPair("mother-in-law", "теща");

        System.out.println("Added 10 initial word pairs.");
    }

    private static void addNewWordPairs(Translator translator, Scanner scanner) {
        System.out.println("\n2. Adding new word pairs (enter 'done' in the English field to finish):");

        while (true) {
            System.out.print("Enter English word (or 'done' to continue): ");
            String englishWord = scanner.nextLine().trim();

            if (englishWord.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter Ukrainian translation: ");
            String ukrainianWord = scanner.nextLine().trim();

            if (!englishWord.isEmpty() && !ukrainianWord.isEmpty()) {
                translator.addWordPair(englishWord, ukrainianWord);
                System.out.println("Word pair added: " + englishWord + " -> " + ukrainianWord);
            } else {
                System.out.println("Words cannot be empty. Try again.");
            }
        }
    }

    private static void translatePhrase(Translator translator, Scanner scanner) {
        System.out.println("\n3. Phrase translation.");

        String englishPhrase;
        while (true) {
            System.out.print("Enter an English phrase to translate (e.g., 'Java is a great language'): ");
            englishPhrase = scanner.nextLine().trim();

            if (englishPhrase.isEmpty()) {
                System.out.println("You entered an empty phrase. Please try again.");
            } else {
                break;
            }
        }

        String translatedPhrase = translator.translatePhrase(englishPhrase);

        System.out.println("\n-----------------------------------------------------");
        System.out.println("Input Phrase (English): " + englishPhrase);
        System.out.println("Translation (Ukrainian): " + translatedPhrase);
        System.out.println("-----------------------------------------------------");
    }
}
