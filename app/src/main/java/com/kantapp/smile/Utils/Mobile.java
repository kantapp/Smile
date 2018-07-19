package com.kantapp.smile.Utils;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Kantapp Inc. on 7/18/2018.
 */
public class Mobile
{
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


}
