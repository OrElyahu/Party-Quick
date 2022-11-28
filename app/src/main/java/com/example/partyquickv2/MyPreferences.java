package com.example.partyquickv2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyPreferences {

    private static final String DATA_LOGIN = "status_login";
    private static final String UID = "UID";

    private static SharedPreferences getSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setDataLogin(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(DATA_LOGIN,status);
        editor.apply();
    }

    public static void clearData(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(DATA_LOGIN);
        editor.remove(UID);
        editor.apply();
    }

    public static boolean getDataLogin(Context context){
        return getSharedPreferences(context).getBoolean(DATA_LOGIN,false);
    }

    public static void setUID(Context context, String uid){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(UID,uid);
        editor.apply();
    }

    public static String getUID(Context context){
        return getSharedPreferences(context).getString(UID,null);
    }
}
