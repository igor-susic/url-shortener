package com.demo.urlshortener.models.domain;

/**
 * Defines characters that can be used in short version of the URL
 */
public class ShortUrl {
    private static final String UPPER_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String LOWER_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMERIC_CHARACTERS ="0123456789";

    public static final String ALPHABET = UPPER_CHARACTERS + LOWER_CHARACTERS + NUMERIC_CHARACTERS;
    public static final int BASE = ALPHABET.length();
}
