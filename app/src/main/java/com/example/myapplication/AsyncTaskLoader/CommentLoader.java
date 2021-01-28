package com.example.myapplication.AsyncTaskLoader;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.myapplication.API.NetworkUtils;
import com.example.myapplication.data.model.Comment;
import com.example.myapplication.data.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class CommentLoader extends AsyncTaskLoader<LinkedList<Comment>> {
    public CommentLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public LinkedList<Comment> loadInBackground() {
        LinkedList<Comment> newlistComment = new LinkedList<>();
        try {
            JSONObject jsonObject = new JSONObject(NetworkUtils.loadListComment());
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            Log.d("loadComment",jsonObject.toString());
            if(jsonArray != null && jsonArray.length() > 0){
                for (int i = 0; i < jsonArray.length() ;i++){
                    JSONObject jsonItem = jsonArray.getJSONObject(i);
                    int IdCom = jsonItem.getInt("IdCom");
                    int IdNews_FK  = jsonItem.getInt("IdNews_FK");
                    String Usemember_FK = jsonItem.getString("Usemember_FK");
                    String Comment = jsonItem.getString("Context");
                    Comment cmt = new Comment(IdCom, IdNews_FK, Usemember_FK, Comment);
                    newlistComment.addLast(cmt);
                }
                return newlistComment;
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
