package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.ListNewsAdapter;
import com.example.myapplication.Adapter.ListNewsHotAdapter;
import com.example.myapplication.AsyncTaskLoader.SaveNewsLoader;
import com.example.myapplication.Bottom.UserFragment;
import com.example.myapplication.data.model.News;

import java.util.LinkedList;

public class DetailNewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, Loader.OnLoadCanceledListener<String> {

    private TextView txtid;
    private WebView detaiNews;
    private final String URLDETAIL = "http://10.0.2.2:8000/api/Detail/";
    private SharedPreferences sharedPreferences;
    LoaderManager loaderManager;
    private final int NEW_ID = 10000;
    private String id;
    public static final String KeyID1 = "KeyId";
    public static final String KeyID2 = "KeyId2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        detaiNews = findViewById(R.id.detai_News);
        loaderManager = LoaderManager.getInstance(this);
        Intent intent = getIntent();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(intent.hasExtra("guid")){
            id = intent.getStringExtra("guid");
            detaiNews.loadUrl(id);
        }
        if(intent.hasExtra(ListNewsAdapter.NewHotViewHolder.EXTRA_MESSAGE)){
            id = intent.getStringExtra(ListNewsAdapter.NewHotViewHolder.EXTRA_MESSAGE);
            detaiNews.loadUrl(URLDETAIL + id);
        }
    }

    public void sharedNews(View view) {

        String mimeType= "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Chia sẽ tin tức:")
                .setText(URLDETAIL + id)
                .startChooser();
    }

    public void backBtn(View view) {
        finish();
    }

    public void savebtn(View view) {
        if(sharedPreferences.getString(UserFragment.USER_NAME,"").length() > 0){
            String userName = sharedPreferences.getString(UserFragment.USER_NAME,"");
            Bundle bundle = new Bundle();
            bundle.putString("iduser",userName);
            bundle.putString("idnews",id);
            Loader<String> loader =  loaderManager.getLoader(NEW_ID);
            if (loader == null) {
                loader = loaderManager.initLoader(NEW_ID, bundle, this);
            } else {
                loader = loaderManager.restartLoader(NEW_ID, bundle, this);
            }
            loader.registerOnLoadCanceledListener(this);
        }else {
            Toast.makeText(this,"Vui lòng đăng nhập",Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String iduser = args.getString("iduser","");
        int idnews = Integer.parseInt(args.getString("idnews",""));
        return new SaveNewsLoader(this,iduser,idnews);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if(data != null && data.length() > 0){
            if(data.equals("success")){
                Toast.makeText(this,"Lưu thành công",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,data,Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    public void onLoadCanceled(@NonNull Loader<String> loader) {

    }

    public void BinhLuan(View view) {
        String UserName = sharedPreferences.getString(UserFragment.USER_NAME,"");
        Intent intent = new Intent(this, CommentsNews.class);
        intent.putExtra(KeyID1, id);
        intent.putExtra(KeyID2, UserName);
        startActivity(intent);
    }
}