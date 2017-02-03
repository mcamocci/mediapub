package com.haikarose.cman.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.haikarose.cman.pojos.UserPreference;


/**
 * Created by root on 9/2/16.
 */
public class PreferenceManager {

    public static final String EMAIL="PHONE";
    public static final String PASSWORD="PASSWORD";
    public static final String USER_ID="user_id";

    public static void storeUser(UserPreference userPreference, Context context){

        SharedPreferences sharedPreferences=context.getSharedPreferences("USER_LOGIN",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(EMAIL,userPreference.getEmail());
        editor.putString(PASSWORD,userPreference.getPassword());
        editor.commit();
    }

    public static UserPreference UserStored(Context context){

        SharedPreferences sharedPreferences=context.getSharedPreferences("USER_LOGIN",0);
        UserPreference user_pref=new UserPreference();
        user_pref.setEmail(sharedPreferences.getString(EMAIL,"0"));
        user_pref.setPassword(sharedPreferences.getString(PASSWORD,"0"));


        return user_pref;
    }

    public static void ClearLogins(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("USER_LOGIN",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


}
