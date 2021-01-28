package com.example.myapplication.Bottom;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.ListNewsAdapter;
import com.example.myapplication.Adapter.ListVideoAdapter;
import com.example.myapplication.R;
import com.example.myapplication.data.model.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private final  int idVideo = 1000;
    private LoaderManager loaderManager;
    private RecyclerView mRecyclerView;
    private LinkedList<News> mListNews = new LinkedList<>();
    private ListVideoAdapter mAdapter;

    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        new ReadRSS().execute("https://www.nguoiduatin.vn/rss/video.rss");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View item = inflater.inflate(R.layout.fragment_video, container, false);
        mRecyclerView = item.findViewById(R.id.recyclerView_video);
        // Inflate the layout for this fragment
        return item;
    }


    private class ReadRSS extends AsyncTask<String,Void,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mAdapter = new ListVideoAdapter(getActivity(),mListNews);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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