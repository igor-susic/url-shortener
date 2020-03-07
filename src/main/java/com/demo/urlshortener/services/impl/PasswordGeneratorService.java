package com.demo.urlshortener.services.impl;

import com.demo.urlshortener.models.PasswordModel;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PasswordGeneratorService {

    /**
     * Returns plain text password using the rules from @see PasswordModel
     *
     * @return Plain text password, length of 8 characters
     */
    public String generate() {

        StringBuilder password = new StringBuilder(PasswordModel.PASSWORD_LENGTH);
        Random rnd = new Random(System.nanoTime());

        for (int i = 0; i < PasswordModel.PASSWORD_LENGTH; i++) {
            char character = PasswordModel.ALLOWED_CHARACTERS.charAt(rnd.nextInt(PasswordModel.ALLOWED_CHARACTERS.length()));
            password.append(character);
        }

        return password.toString();
    }
}
