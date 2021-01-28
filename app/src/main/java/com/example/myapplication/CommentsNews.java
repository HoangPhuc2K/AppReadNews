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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.API.NetworkUtils;
import com.example.myapplication.Adapter.CommentAdapter;
import com.example.myapplication.Adapter.ListNewsAdapter;
import com.example.myapplication.AsyncTaskLoader.CommentLoader;
import com.example.myapplication.AsyncTaskLoader.ListNewsLoader;
import com.example.myapplication.data.model.Comment;
import com.example.myapplication.data.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class CommentsNews extends AppCompatActivity implements LoaderManager.LoaderCallbacks<LinkedList<Comment>>{
    public LinkedList<Comment> mComment = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;
    public CommentLoader CmtLoader;
    private LoaderManager loaderManager;
    private final int NEW_ID = 1111;
    public String id;
    public String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_news);
        Intent intent = getIntent();
        id = intent.getStringExtra(DetailNewsActivity.KeyID1);
        loaderManager = LoaderManager.getInstance(this);
        Loader<LinkedList<Comment>> loader =  loaderManager.getLoader(1111);
        if (loader == null) {
            loader = loaderManager.initLoader(NEW_ID, null, this);
        } else {
            loader = loaderManager.restartLoader(NEW_ID, null, this);
        }
    }

    @NonNull
    @Override
    public Loader<LinkedList<Comment>> onCreateLoader(int id, @Nullable Bundle args) {
        return new CommentLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<LinkedList<Comment>> loader, LinkedList<Comment> data) {


        for (int i =0; i < data.size(); i++)
        {
            if (data.get(i).getIdNews_FK() == Integer.parseInt(id))
            {
                mComment.addLast(data.get(i));
            }
        }
        mRecyclerView = findViewById(R.id.ListComment);
        mAdapter = new CommentAdapter(this, mComment);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<LinkedList<Comment>> loader) {

    }

    public void BinhLuan(View view) {
        EditText tv = findViewById(R.id.editTextTextPersonName);
        new CommentsNews.InsertCmt().execute();
        tv.setText("");
    }

    private class InsertCmt extends AsyncTask<Void, Void , String>
    {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("successfully"))
            {
                mRecyclerView = findViewById(R.id.ListComment);
                mAdapter = new CommentAdapter(CommentsNews.this, mComment);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(CommentsNews.this));
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Intent intent = getIntent();
                username = intent.getStringExtra(DetailNewsActivity.KeyID2);
                EditText tv = findViewById(R.id.editTextTextPersonName);
                String noidung = tv.getText().toString();
                String a = NetworkUtils.InsertComment(id, noidung, username);
                Comment cmt = new Comment(0 , 0, "" + username, "" + noidung);
                mComment.addLast(cmt);
                return "successfully";
            }
            catch (Exception e)
            {
                return "fail";

            }
        }
    }
}
