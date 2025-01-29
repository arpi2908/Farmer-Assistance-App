package com.example.farmerassistantcustom.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Util {

    public static void setSP(Context con, String uid) {
        SharedPreferences sp = con.getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("uid", uid);
        editor.apply();
        editor.commit();
    }

    public static String getSP(Context con) {
        SharedPreferences sp = con.getSharedPreferences("User", Context.MODE_PRIVATE);
        return sp.getString("uid", "");
    }

    public static void setType(Context con, String type) {
        SharedPreferences sp = con.getSharedPreferences("User1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("type", type);
        editor.apply();
        editor.commit();
    }

    public static String gettype(Context con) {
        SharedPreferences sp = con.getSharedPreferences("User1", Context.MODE_PRIVATE);
        return sp.getString("type", "");
    }
    public static void setName(Context con, String type) {
        SharedPreferences sp = con.getSharedPreferences("NAme", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", type);
        editor.apply();
        editor.commit();
    }

    public static String getName(Context con) {
        SharedPreferences sp = con.getSharedPreferences("NAme", Context.MODE_PRIVATE);
        return sp.getString("name", "");
    }
    public static  Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
