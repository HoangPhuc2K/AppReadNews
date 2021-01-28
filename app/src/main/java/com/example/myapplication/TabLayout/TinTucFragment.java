package com.example.myapplication.TabLayout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapter.ListNewsAdapter;
import com.example.myapplication.AsyncTaskLoader.ListNewsLoader;
import com.example.myapplication.R;
import com.example.myapplication.data.model.News;

import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TinTucFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TinTucFragment extends Fragment implements LoaderManager.LoaderCallbacks<LinkedList<News>>, Loader.OnLoadCanceledListener<LinkedList<News>> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private final  int HotNews = 1000;
    private LoaderManager loaderManager;
    private RecyclerView mRecyclerView;
    private LinkedList<News> mListNews = new LinkedList<>();
    private ListNewsAdapter mAdapter;


    public TinTucFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TinTucFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TinTucFragment newInstance(String param1, String param2) {
        TinTucFragment fragment = new TinTucFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View item =  inflater.inflate(R.layout.fragment_tin_tuc, container, false);
        loaderManager = LoaderManager.getInstance(this);
        Loader<LinkedList<News>> loader =  loaderManager.getLoader(HotNews);
        if (loader == null) {
            loader = loaderManager.initLoader(HotNews, null, this);
        } else {
            loader = loaderManager.restartLoader(HotNews, null, this);
        }
        mRecyclerView = item.findViewById(R.id.recyclerView_News);
        // Inflate the layout for this fragment
        return item;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @NonNull
    @Override
    public Loader<LinkedList<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return new ListNewsLoader(getContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<LinkedList<News>> loader, LinkedList<News> data) {
        if(data != null){
            mListNews = data;
            Log.d("TEST_NewsNews", String.valueOf(data.get(0).getIdNews()));
            mAdapter = new ListNewsAdapter(getContext(),mListNews);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }


    @Override
    public void onLoaderReset(@NonNull Loader<LinkedList<News>> loader) {

    }

    @Override
    public void onLoadCanceled(@NonNull Loader<LinkedList<News>> loader) {

    }
}