package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TranslatorTest {

    private Translator translator;

    @BeforeEach
    void setUp() {
        translator = new Translator();

        translator.addWordPair("hello", "привіт");
        translator.addWordPair("world", "світ");
        translator.addWordPair("java", "джава");
        translator.addWordPair("is", "є");
        translator.addWordPair("programming", "програмування");
        translator.addWordPair("great", "чудовий");
        translator.addWordPair("and", "та");
    }

    @Test
    void addWordPairSimpleCase() {
        translator.addWordPair("test", "тест");
        assertEquals("Тест", translator.translatePhrase("test"));
    }

    @Test
    void addWordPairOverwriteExistingWord() {
        translator.addWordPair("java", "нова_джава");
        assertEquals("Нова_джава є світ", translator.translatePhrase("Java is world"));
    }

    @Test
    void addWordPairDifferentCases() {
        translator.addWordPair("UPPER", "верхній");
        assertEquals("Верхній", translator.translatePhrase("upper"));
        assertEquals("Верхній", translator.translatePhrase("UPPER"));
    }

    @Test
    void translatePhraseStandardSentence() {
        String phrase = "Hello java world";
        String expected = "Привіт джава світ";
        assertEquals(expected, translator.translatePhrase(phrase));
    }

    @Test
    void translatePhraseMixedCaseInput() {
        String phrase = "JaVa iS Great pROgRaMmInG";
        String expected = "Джава є чудовий програмування";
        assertEquals(expected, translator.translatePhrase(phrase));
    }

    @Test
    void translatePhraseFirstLetterCapitalization() {
        String phrase = "programming is great";
        String expected = "Програмування є чудовий";
        assertEquals(expected, translator.translatePhrase(phrase));
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello world!", "hello world.", "hello world?"})
    void translatePhraseWithEndPunctuation(String input) {
        String expected = "Привіт світ" + input.substring(11);
        assertEquals(expected, translator.translatePhrase(input));
    }

    @Test
    void translatePhraseWithInternalPunctuation() {
        String phrase = "java, is great and world!";
        String expected = "Джава, є чудовий та світ!";
        assertEquals(expected, translator.translatePhrase(phrase));
    }

    @Test
    void translatePhraseAllUnknownWords() {
        String phrase = "unknown word list";
        String expected = "[unknown] [word] [list]";
        assertEquals(expected, translator.translatePhrase(phrase));
    }

    @Test
    void translatePhraseMixedKnownAndUnknown() {
        String phrase = "java and python is programming";
        String expected = "Джава та [python] є програмування";
        assertEquals(expected, translator.translatePhrase(phrase));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  \t  ", "\n"})
    void translatePhraseNullOrEmptyPhrase(String phrase) {
        assertThrows(IllegalArgumentException.class, () -> translator.translatePhrase(phrase));
    }

    @Test
    void translatePhraseSingleKnownWord() {
        assertEquals("Привіт", translator.translatePhrase("hello"));
    }

    @Test
    void translatePhraseSingleUnknownWord() {
        assertEquals("[testword]", translator.translatePhrase("testword"));
    }

    @Test
    void translatePhraseMultipleSpaces() {
        String phrase = "hello   world  is   great";
        String expected = "Привіт світ є чудовий";
        assertEquals(expected, translator.translatePhrase(phrase));
    }
}