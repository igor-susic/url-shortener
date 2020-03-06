package com.demo.urlshortener.models;

/**
 * Defines characters allowed to be used in auto generated password
 */
public class PasswordModel {

    private static final String UPPER_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String LOWER_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMERIC_CHARACTERS ="0123456789";

    public static final Integer PASSWORD_LENGTH = 8;
    public static final String ALLOWED_CHARACTERS = UPPER_CHARACTERS + LOWER_CHARACTERS + NUMERIC_CHARACTERS;
}
