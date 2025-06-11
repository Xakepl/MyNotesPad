package com.missdark.mynotespad;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Themes {
    static final String namePref = "themePrefs";
    static final String keyTheme = "current_theme";
    static SharedPreferences prefs;

   static int Red = 0;
   static int Orange = 1;
   static int Yellow = 2;
   static int Green = 3;
   static int LtBlue = 4;
   static int Blue = 5;
   static int Purple = 6;
   static int Pink = 7;
   static int Gray = 8;
   static int ChocoLoco = 9;

    public static void applyTheme(Activity activity){
        switch(getSavedTheme(activity)) {
            case Red: activity.setTheme(R.style.AppTheme_Red); break;
            case Orange: activity.setTheme(R.style.AppTheme_Orange); break;
            case Yellow: activity.setTheme(R.style.AppTheme_Yellow); break;
            case Green: activity.setTheme(R.style.AppTheme_Green); break;
            case LtBlue: activity.setTheme(R.style.AppTheme_LtBlue); break;
            case Blue: activity.setTheme(R.style.AppTheme_Blue); break;
            case Purple: activity.setTheme(R.style.AppTheme_Purple); break;
            case Pink: activity.setTheme(R.style.AppTheme_Pink); break;
            case Gray: activity.setTheme(R.style.AppTheme_Gray); break;
            case ChocoLoco: activity.setTheme(R.style.AppTheme_ChocoLoco); break;
        }
    }


    public static void saveTheme(Context context, int theme) {
    prefs = context.getSharedPreferences(namePref, Context.MODE_PRIVATE);
    prefs.edit().putInt(keyTheme, theme).apply();}

    public static int getSavedTheme(Context context) {prefs = context.getSharedPreferences(namePref, Context.MODE_PRIVATE);
    return prefs.getInt(keyTheme, Red);}



}

//    fun applyTheme(activity: Activity) {
//        when (getSavedTheme(activity)) {
//            THEME_DARK -> activity.setTheme(R.style.AppTheme_Dark)
//            else -> activity.setTheme(R.style.AppTheme_Light)
//        }
//    }
//    fun saveTheme(context: Context, theme: Int) {
//        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//        prefs.edit().putInt(KEY_THEME, theme).apply()
//    }
//    fun getSavedTheme(context: Context): Int {
//        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//        return prefs.getInt(KEY_THEME, THEME_LIGHT)
//    }
//}





















//