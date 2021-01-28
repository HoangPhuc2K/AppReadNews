package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class ListSourceNewsAdapter extends RecyclerView.Adapter<ListSourceNewsAdapter.NewHotViewHolder> {
    private final LinkedList<News> mListNews;
    private LayoutInflater mInflater;
    private Context context;

    public ListSourceNewsAdapter(Context context, LinkedList<News> mListNewsHot) {
        this.mInflater = LayoutInflater.from(context);
        this.mListNews = mListNewsHot;
        this.context = context;
    }

    @NonNull
    @Override
    public ListSourceNewsAdapter.NewHotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.layout_item_news,parent,false);
        return new ListSourceNewsAdapter.NewHotViewHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSourceNewsAdapter.NewHotViewHolder holder, int position) {
        String IdNews = String.valueOf(mListNews.get(position).getIdNews());
        String Title = mListNews.get(position).getTitle();
        String Author= mListNews.get(position).getAuthor();
        String Picture = mListNews.get(position).getPicture();
        String Date = mListNews.get(position).getPubDate();
        String Guid = mListNews.get(position).getGuid();
        holder.News_id.setText(IdNews);
        holder.txt_News.setText(Author);
        holder.txt_NewsDec.setText(Title);
        holder.txt_date.setText(Date);
        Log.d("LOG_PIC",Picture);
        Glide.with(context)
                .load(Picture)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgNews);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailNewsActivity.class);
                intent.putExtra("guid",Guid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListNews.size();
    }

    public class NewHotViewHolder extends RecyclerView.ViewHolder{
        final ListSourceNewsAdapter Adapter;
        public TextView txt_NewsDec;
        public TextView News_id;
        public TextView txt_News;
        public TextView txt_date;
        public ImageView imgNews;
        public ConstraintLayout constraintLayout;
        public static final String EXTRA_MESSAGE = "id";
        public NewHotViewHolder(@NonNull View itemView, ListSourceNewsAdapter adapter) {
            super(itemView);
            this.Adapter = adapter;
            this.txt_NewsDec = itemView.findViewById(R.id.txt_NewsDec);
            this.txt_News = itemView.findViewById(R.id.txt_News);
            this.txt_date = itemView.findViewById(R.id.txt_NewsDate);
            this.News_id = itemView.findViewById(R.id.News_id);
            this.constraintLayout = itemView.findViewById(R.id.constraint);
            this.imgNews = itemView.findViewById(R.id.imgNews);
        }
    }
}
