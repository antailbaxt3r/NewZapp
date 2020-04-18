package com.antailbaxt3r.newzapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.antailbaxt3r.newzapp.models.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.NewsViewholder>{

    private List<Article> list;
    private Context context;

    public NewsRVAdapter(List<Article> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewholder holder, int position) {
        holder.populate(list.get(position), context);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class NewsViewholder extends RecyclerView.ViewHolder{

        private TextView title, desc, author;
        private ImageView image;
        private CardView item;

        public NewsViewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            image = itemView.findViewById(R.id.image);
            item = itemView.findViewById(R.id.item);
        }

        public void populate(Article article, Context context){
            title.setText(article.getTitle());
            author.setText(article.getAuthor());
            desc.setText(article.getDescription());
            Picasso.get().load(article.getUrlToImage()).into(image);
            item.setOnClickListener((v) -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });
        }
    }
}
