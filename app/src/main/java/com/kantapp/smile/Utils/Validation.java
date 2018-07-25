package com.kantapp.smile.Utils;

import java.util.regex.Pattern;

/**
 * Created by Kantapp Inc. on 7/18/2018.
 */
public class Validation {
    public static boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isValidName(String name)
    {
        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern=Pattern.compile(regx);
        if (name==null)
            return false;
        return pattern.matcher(name).matches();
    }
}
