package com.example.myapplication.AsyncTaskLoader;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.myapplication.API.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class SaveNewsLoader extends AsyncTaskLoader<String> {
    private String iduser;
    private int idnews;
    public SaveNewsLoader(@NonNull Context context,String iduser,int idnews) {
        super(context);
        this.iduser = iduser;
        this.idnews = idnews;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        String result = null;
        try {
            JSONObject jsonObject = new JSONObject(NetworkUtils.saveNews(iduser,idnews));
            result = jsonObject.getString("savenews");
            Log.d("save",result);
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
