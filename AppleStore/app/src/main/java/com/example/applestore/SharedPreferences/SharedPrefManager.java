package com.example.applestore.SharedPreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.applestore.Activity.LoginActivity;
import com.example.applestore.model.User;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "retrofitregisterlogin";
    private static final String KEY_NAME = "keyname";
    private static final String KEY_PASSWORD = "keypassword";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_PHONE = "keyphone";
    private static final String KEY_ID = "keyid";
    private static final String KEY_IS_USER = "keyisuser";
    private static final String KEY_IS_ADMIN = "keyisadmin";
    private static final String KEY_ADDRESS = "keyaddress";

    private static final String KEY_IMAGES = "keyimages";

    private static SharedPrefManager mInstance;
    private static Context ctx;

    private SharedPrefManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if(mInstance==null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void userLogin(User user){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getMaKH());
        editor.putString(KEY_NAME,user.getTenKH());
        editor.putString(KEY_EMAIL,user.getEmail());
        editor.putString(KEY_PHONE,user.getPhone());
        editor.putString(KEY_ADDRESS,user.getDiaChi());
        editor.putString(KEY_PASSWORD,user.getmK());
        editor.putInt(KEY_IS_USER,user.getIsUser());
        editor.putInt(KEY_IS_USER,user.getIsAdmin());

//        editor.putString(KEY_IMAGES, user.getImages());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL,null) != null;
    }
    public User getUser() {
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID,-1),
                sharedPreferences.getString(KEY_NAME,null),
                sharedPreferences.getString(KEY_EMAIL,null),
                sharedPreferences.getString(KEY_PHONE,null),
                sharedPreferences.getString(KEY_ADDRESS,null),
                sharedPreferences.getString(KEY_PASSWORD,null),
                sharedPreferences.getInt(KEY_IS_USER,-1),
                sharedPreferences.getInt(KEY_IS_ADMIN,-1)
        );
    }
    public void logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, LoginActivity.class));
    }
}
