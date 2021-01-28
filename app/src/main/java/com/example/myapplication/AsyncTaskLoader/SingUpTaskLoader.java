package com.example.myapplication.AsyncTaskLoader;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.myapplication.API.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class SingUpTaskLoader extends AsyncTaskLoader<String> {
    private String UserMember;
    private String PassWord;
    public SingUpTaskLoader(@NonNull Context context, String usermember, String password) {
        super(context);
        this.UserMember = usermember;
        this.PassWord = password;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        String result = null;
        try {
            JSONObject  jsonObject = new JSONObject(NetworkUtils.singUp(UserMember, PassWord));
            result = jsonObject.getString("Singup");
            Log.d("TEST_SINGUP", result );
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
