package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.DetailNewsActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.model.News;

import java.util.LinkedList;

public class ListNewsSportAdapter extends RecyclerView.Adapter<ListNewsSportAdapter.NewHotViewHolder> {
    private final LinkedList<News> mListNewsHot;
    private LayoutInflater mInflater;
    private Context context;
    private String Url = "http://10.0.2.2:8000/storage/";

    public ListNewsSportAdapter(Context context, LinkedList<News> mListNewsHot) {
        this.mInflater = LayoutInflater.from(context);
        this.mListNewsHot = mListNewsHot;
        this.context = context;
    }

    @NonNull
    @Override
    public ListNewsSportAdapter.NewHotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.layout_item_news,parent,false);
        return new ListNewsSportAdapter.NewHotViewHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNewsSportAdapter.NewHotViewHolder holder, int position) {
        String IdNews = String.valueOf(mListNewsHot.get(position).getIdNews());
        String Title = mListNewsHot.get(position).getTitle();
        String Author= mListNewsHot.get(position).getAuthor();
        String Picture = mListNewsHot.get(position).getPicture();
        String Date = mListNewsHot.get(position).getPubDate();
        holder.News_id.setText(IdNews);
        holder.txt_News.setText(Author);
        holder.txt_NewsDec.setText(Title);
        holder.txt_date.setText(Date);
        Glide.with(context)
                .load(Url + Picture)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgNews);

    }

    @Override
    public int getItemCount() {
        return mListNewsHot.size();
    }

    public class NewHotViewHolder extends RecyclerView.ViewHolder{
        final ListNewsSportAdapter Adapter;
        public TextView txt_NewsDec;
        public TextView News_id;
        public TextView txt_News;
        public TextView txt_date;
        public ImageView imgNews;
        public ConstraintLayout constraintLayout;
        public static final String EXTRA_MESSAGE = "id";
        public NewHotViewHolder(@NonNull View itemView, ListNewsSportAdapter adapter) {
            super(itemView);
            this.Adapter = adapter;
            this.txt_NewsDec = itemView.findViewById(R.id.txt_NewsDec);
            this.txt_News = itemView.findViewById(R.id.txt_News);
            this.txt_date = itemView.findViewById(R.id.txt_NewsDate);
            this.News_id = itemView.findViewById(R.id.News_id);
            this.constraintLayout = itemView.findViewById(R.id.constraint);
            this.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailNewsActivity.class);
                    intent.putExtra(EXTRA_MESSAGE,News_id.getText().toString());
                    context.startActivity(intent);
                }
            });
            this.imgNews = itemView.findViewById(R.id.imgNews);
        }
    }
}
