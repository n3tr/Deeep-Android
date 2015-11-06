package io.jkn.deeep.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import io.jkn.deeep.models.UserDO;

/**
 * Created by n3tr on 1/2/2015 AD.
 */

public class ApplicationManager {

    private static final String PREF_CREDENTIAL_FILE = "io.jkn.deeep.PREF_CREDENTIAL_FILE";

    private static ApplicationManager instance;
    private Context mContext;
    private String currentUserAccessToken;

    private ApplicationManager(Context appContext) {
        mContext = appContext;
    }

    public static  ApplicationManager getInstance(Context context) {
        if (instance == null)
            instance = new ApplicationManager(context.getApplicationContext());
        return instance;
    }


    public String getUserAccessToken() {
        if (currentUserAccessToken != null){
            return currentUserAccessToken;
        }
        SharedPreferences pref = mContext.getSharedPreferences(PREF_CREDENTIAL_FILE,Context.MODE_PRIVATE);
        String token = pref.getString("userAccessToken",null);
        currentUserAccessToken = token;
        return token;
    }


    public void setUserAccessToken(String accessToken) {
        if (accessToken == null)
            return;

        SharedPreferences pref = mContext.getSharedPreferences(PREF_CREDENTIAL_FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userAccessToken",accessToken);
        editor.commit();
    }

    public void clearUserAccessToken() {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_CREDENTIAL_FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("userAccessToken");
        editor.commit();
    }


    private UserDO currentUser;

    public void setCurrentUser(UserDO user) {
        String json = new Gson().toJson(user);

        SharedPreferences pref = mContext.getSharedPreferences(PREF_CREDENTIAL_FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("currentUser",json);
        editor.commit();

        currentUser = user;
    }

    public UserDO getCurrentUser() {
        if (currentUser == null) {
            SharedPreferences pref = mContext.getSharedPreferences(PREF_CREDENTIAL_FILE,Context.MODE_PRIVATE);
            String json = pref.getString("currentUser","");
            if (json.equals(""))
                return null;

            currentUser = new Gson().fromJson(json,UserDO.class);
        }
        return currentUser;
    }

}