package com.example.newsnow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private final List<NewsArticle> articles;
    private final Context context;

    public NewsAdapter(List<NewsArticle> articles, Context context) {
        this.articles = new ArrayList<>(articles);
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_article, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsArticle article = articles.get(position);
        holder.titleTextView.setText(article.getTitle());
        holder.sourceTextView.setText(article.getSource());
        holder.dateTextView.setText(article.getDate());
        holder.summaryTextView.setText(article.getSummary());

        holder.ratingBar.setOnRatingBarChangeListener(null);
        holder.ratingBar.setRating(article.getUserRating());
        holder.ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                article.setUserRating(rating);
            }
        });

        holder.readMoreButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsDetailActivity.class);
            intent.putExtra(NewsDetailActivity.EXTRA_ARTICLE_URL, article.getUrl());
            intent.putExtra(NewsDetailActivity.EXTRA_ARTICLE_TITLE, article.getTitle());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void updateData(List<NewsArticle> updatedArticles) {
        articles.clear();
        articles.addAll(updatedArticles);
        notifyDataSetChanged();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        final TextView titleTextView;
        final TextView sourceTextView;
        final TextView dateTextView;
        final TextView summaryTextView;
        final RatingBar ratingBar;
        final MaterialButton readMoreButton;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.articleTitleTextView);
            sourceTextView = itemView.findViewById(R.id.articleSourceTextView);
            dateTextView = itemView.findViewById(R.id.articleDateTextView);
            summaryTextView = itemView.findViewById(R.id.articleSummaryTextView);
            ratingBar = itemView.findViewById(R.id.articleRatingBar);
            readMoreButton = itemView.findViewById(R.id.readMoreButton);
        }
    }
}
