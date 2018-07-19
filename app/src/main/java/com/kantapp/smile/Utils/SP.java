package com.kantapp.smile.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.kantapp.smile.Model.User;

import org.json.JSONObject;

/**
 * Created by Kantapp Inc. on 7/19/2018.
 */
public class SP
{
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    private static final String LOGIN_TABLE="loginTable";

    public static void saveUserdetails(Activity activity, User user)
    {
        sharedPreferences=activity.getSharedPreferences(LOGIN_TABLE, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putInt(User.ID,user.getId());
        editor.putString(User.EMAIL,user.getEmail());
        editor.putString(User.FULLNAME,user.getFullname());
        editor.putString(User.PROFILE,user.getProfile());
        editor.putString(User.GENDER,user.getGender());
        editor.putString(User.TOKEN,user.getToken());
        editor.apply();
    }

    public static User getData(Activity activity)
    {
        User user=new User();
        sharedPreferences=activity.getSharedPreferences(LOGIN_TABLE,Context.MODE_PRIVATE);
        user.setId(sharedPreferences.getInt(User.ID,0));
        user.setEmail(sharedPreferences.getString(User.EMAIL,null));
        user.setFullname(sharedPreferences.getString(User.FULLNAME,null));
        user.setProfile(sharedPreferences.getString(User.PROFILE,null));
        user.setGender(sharedPreferences.getString(User.GENDER,null));
        user.setToken(sharedPreferences.getString(User.TOKEN,null));
        return user;
    }
}
