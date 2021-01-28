package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import com.example.myapplication.Adapter.ListSourceNewsAdapter;
import com.example.myapplication.Adapter.ListVideoAdapter;
import com.example.myapplication.data.model.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;


public class TempActivity extends AppCompatActivity {

    private LinkedList<News> mListNews = new LinkedList<>();
    private RecyclerView recyclerView;
    private ListSourceNewsAdapter mAdapter;
    private CalendarView calendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        Intent intent = getIntent();
        calendarView = findViewById(R.id.calendar_temp);
        if(intent.hasExtra("calendar")) {
            calendarView.setVisibility(View.VISIBLE);
            Log.d("lich",intent.getStringExtra("calendar"));
        }
        if(intent.hasExtra("nguoiduatin")) {
            recyclerView = findViewById(R.id.recyclerView_SourceNews);
            recyclerView.setVisibility(View.VISIBLE);
            calendarView.setVisibility(View.INVISIBLE);
            new ReadRSS().execute("https://www.nguoiduatin.vn/trang-chu.rss");
            while (!mListNews.isEmpty()){
                mListNews = new LinkedList<>();
                new ReadRSS().execute("https://www.nguoiduatin.vn/trang-chu.rss");
            }
        }
        if(intent.hasExtra("news24h")) {
            recyclerView = findViewById(R.id.recyclerView_SourceNews);
            recyclerView.setVisibility(View.VISIBLE);
            calendarView.setVisibility(View.INVISIBLE);
            new ReadRSS().execute("https://cdn.24h.com.vn/upload/rss/trangchu24h.rss");
            while (!mListNews.isEmpty()){
                mListNews = new LinkedList<>();
                new ReadRSS().execute("https://cdn.24h.com.vn/upload/rss/trangchu24h.rss");
            }
        }
        if(intent.hasExtra("thanhnien")) {
            recyclerView = findViewById(R.id.recyclerView_SourceNews);
            recyclerView.setVisibility(View.VISIBLE);
            calendarView.setVisibility(View.INVISIBLE);
            new ReadRSS().execute("https://thanhnien.vn/rss/home.rss");
            while (!mListNews.isEmpty()){
                mListNews = new LinkedList<>();
                new ReadRSS().execute("https://thanhnien.vn/rss/home.rss");
            }
        }
        if(intent.hasExtra("tuoitre")) {
            recyclerView = findViewById(R.id.recyclerView_SourceNews);
            recyclerView.setVisibility(View.VISIBLE);
            calendarView.setVisibility(View.INVISIBLE);
            new ReadRSS().execute("https://tuoitre.vn/rss/tin-moi-nhat.rss");
            while (!mListNews.isEmpty()){
                mListNews = new LinkedList<>();
                new ReadRSS().execute("https://tuoitre.vn/rss/tin-moi-nhat.rss");
            }
            Log.d("size", String.valueOf(mListNews.size()));
        }
    }
    private class ReadRSS extends AsyncTask<String,Void,String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mAdapter = new ListSourceNewsAdapter(TempActivity.this, mListNews);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(TempActivity.this));
        }

        public String extractImageUrl(String description) {
            Document document = Jsoup.parse(description);
            Elements imgs = document.select("img");

            for (Element img : imgs) {
                if (img.hasAttr("src")) {
                    return img.attr("src");
                }
            }

            // no image URL
            return "";
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Document document = Jsoup.connect(strings[0]).get();
                Elements item_eles = document.getElementsByTag("item");
                String title = "";
                String link = "";
                String img = "";
                String guid = "";
                String description_short = "";
                String pubDate = "";
                int count = 0;
                for (Element item_ele : item_eles) {
                    count++;
                    if (count > 10) break;

                    Element title_ele = item_ele.getElementsByTag("title").first();
                    Element link_ele = item_ele.getElementsByTag("link").first();
                    Element guid_ele = item_ele.getElementsByTag("guid").first();
                    Element pubDate_ele = item_ele.getElementsByTag("pubDate").first();
                    if (title_ele != null) {
                        title = title_ele.text().replace("&amp;#34;", "'\'").replaceAll("&#34;", "\'")
                                .replaceAll("&#39;", "\'");
                    }
                    if (link_ele != null) {
                        link = link_ele.text();
                    }
                    if (guid_ele != null) {
                        guid = guid_ele.text();
                    }
                    if (pubDate != null) {
                        pubDate = pubDate_ele.text();
                    }
                    Element elementDes = item_ele.getElementsByTag("description").first();
                    if (elementDes != null) {
                        img = extractImageUrl(elementDes.text());
                        description_short = Jsoup.parse(elementDes.text()).text().replace("&amp;#34;", "'").replaceAll("&#34;", "'")
                                .replaceAll("&#39;", "'");
                    }
                    News news = new News(title,description_short,img,pubDate,guid,link);
                    mListNews.addLast(news);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "successfully";
        }
    }
}