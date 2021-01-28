package com.example.myapplication.AsyncTaskLoader;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.myapplication.API.NetworkUtils;
import com.example.myapplication.data.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class NewsListLoaderNew extends AsyncTaskLoader<LinkedList<News>> {

    public NewsListLoaderNew(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public LinkedList<News> loadInBackground() {
        LinkedList<News> newLinkedList = new LinkedList<>();
        try {
            JSONObject jsonObject = new JSONObject(NetworkUtils.loadListNewsNew());
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if(jsonArray != null && jsonArray.length() > 0){
                for (int i = 0; i < jsonArray.length() ;i++){
                    JSONObject jsonItem = jsonArray.getJSONObject(i);
                    int IdNews = jsonItem.getInt("IdNews");
                    String Title = jsonItem.getString("Title");
                    String Author = jsonItem.getString("Author");
                    String Description = jsonItem.getString("Description");
                    String Picture = jsonItem.getString("Picture");
                    String Date = jsonItem.getString("created_at");
                    News news = new News(IdNews,Title,Description,Author,Picture,Date);
                    newLinkedList.addLast(news);
                }
                return newLinkedList;
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
