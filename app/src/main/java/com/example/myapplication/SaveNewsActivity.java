package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.API.NetworkUtils;
import com.example.myapplication.Adapter.ListNewsAdapter;
import com.example.myapplication.Adapter.ListSourceNewsAdapter;
import com.example.myapplication.data.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;

public class SaveNewsActivity extends AppCompatActivity {

    private LinkedList<News> mListNews = new LinkedList<>();
    private RecyclerView recyclerView;
    private ListNewsAdapter mAdapter;
    private TextView txtsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_news);
        txtsave = findViewById(R.id.txt_save);
        recyclerView = findViewById(R.id.recyclerview_save);
        Intent intent = getIntent();
        if(intent.hasExtra("savenews")){
            String username = intent.getStringExtra("savenews");
            new Callsavenews().execute(username);
            while (!mListNews.isEmpty()){
                new Callsavenews().execute(username);
            }
        }
    }
    private class Callsavenews extends AsyncTask<String,Void,String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("successfully")) {
                txtsave.setVisibility(View.INVISIBLE);
                mAdapter = new ListNewsAdapter(SaveNewsActivity.this, mListNews);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(SaveNewsActivity.this));
            }else {
                txtsave.setVisibility(View.VISIBLE);
            }
        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject jsonObject = new JSONObject(NetworkUtils.loadSaveNews(strings[0]));
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                Log.d("save",jsonObject.toString());
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
                        mListNews.addLast(news);
                    }
                }
                else {
                    return "fail";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "successfully";
        }
    }
}