package com.example.myapplication.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.model.Comment;
import com.example.myapplication.data.model.News;

import java.util.LinkedList;

public class CommentAdapter  extends RecyclerView.Adapter<CommentAdapter.CommentViewHodel>{
    private final LinkedList<Comment> mComment;
    private LayoutInflater mInflater;
    private Context context;

    public CommentAdapter(Context context, LinkedList<Comment> mComment) {
        mInflater = LayoutInflater.from(context);
        this.mComment = mComment;
        this.context = context;
    }
    @NonNull
    @Override
    public CommentAdapter.CommentViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.layout_comment,
                parent, false);
        return new CommentViewHodel(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHodel holder, int position) {
        Comment mCurrent = mComment.get(position);
        holder.name.setText(mCurrent.getName());
        holder.comment.setText(mCurrent.getComment());
    }

    @Override
    public int getItemCount() {
        return mComment.size();
    }

    class CommentViewHodel extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView comment;
        final CommentAdapter mAdapter;


        public CommentViewHodel(View itemView, CommentAdapter adapter) {
            super(itemView);
            name = itemView.findViewById(R.id.Name);
            comment = itemView.findViewById(R.id.Comment);
            this.mAdapter = adapter;
        }
    }
}
