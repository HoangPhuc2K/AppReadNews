package com.example.myapplication.AsyncTaskLoader;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.myapplication.API.NetworkUtils;
import com.example.myapplication.data.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Member;
import java.util.LinkedList;

public class UserLoader extends AsyncTaskLoader<LinkedList<User>> {

    private String userName;
    private String passWord;
    public UserLoader(@NonNull Context context, String userName, String passWord) {
        super(context);
        this.userName = userName;
        this.passWord = passWord;
    }

    @Nullable
    @Override
    public LinkedList<User> loadInBackground() {
        LinkedList<User> listUser = new LinkedList<>();
        try {
            JSONObject jsonObject = new JSONObject(NetworkUtils.login(userName,passWord));
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            Log.d("TEST_LOG",jsonArray.toString());
            if(jsonArray != null && jsonArray.length() > 0){

                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonItem = jsonArray.getJSONObject(i);
                    String iUserName = jsonItem.getString("Usemember");
                    String iUserPass = jsonItem.getString("Password");
                    User member = new User(iUserName,iUserPass);
                    listUser.addLast(member);
                }
                return listUser;
            }
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
