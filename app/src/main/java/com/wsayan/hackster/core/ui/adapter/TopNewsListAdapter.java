package com.wsayan.hackster.core.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wsayan.hackster.core.R;
import com.wsayan.hackster.core.model.pojo.Article;
import com.wsayan.hackster.core.ui.activity.NewsActivity;
import com.wsayan.hackster.core.utility.GlobalConstants;

import java.util.List;

public class TopNewsListAdapter extends RecyclerView.Adapter<TopNewsListAdapter.ViewHolder> {
    private Context context;
    private List<Article> articles;

    public TopNewsListAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public TopNewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_top_news_item, parent, false);
        return new TopNewsListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopNewsListAdapter.ViewHolder holder, int position) {
        final String headLine = articles.get(position).getTitle();
        final String url = articles.get(position).getUrl();
        final String imageUrl = articles.get(position).getUrlToImage();
        final String description = articles.get(position).getDescription();
        final String author = articles.get(position).getAuthor();
        final String date = articles.get(position).getPublishedAt();
        final String name = articles.get(position).getTopNewsSource().getName();

        holder.headLineTextView.setText(headLine);
        holder.dateTextView.setText(date);

        loadImage(imageUrl, holder.newsImageView, holder.iconProgressBar);

        holder.topItemRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (url != null) {
                    Intent intent = new Intent(context, NewsActivity.class);
                    intent.putExtra(GlobalConstants.EXT_TAG_URL, url);
                    intent.putExtra(GlobalConstants.EXT_TAG_NAME, name);
                    context.startActivity(intent);
                }

            }
        });
    }

    private void loadImage(String url, ImageView imageView, final ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        Picasso picasso = Picasso.with(context);
        picasso.load(url).error(R.drawable.ic_home_black_24dp).fit().into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        int listSize = 0;
        if (articles != null) {
            listSize = articles.size();
        }
        return listSize;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView headLineTextView, dateTextView;
        ImageView newsImageView;
        RelativeLayout topItemRelativeLayout;
        ProgressBar iconProgressBar;

        ViewHolder(View itemView) {
            super(itemView);
            initializeWidgets(itemView);
        }

        private void initializeWidgets(View itemView) {
            headLineTextView = itemView.findViewById(R.id.top_news_item_headline_textView);
            dateTextView = itemView.findViewById(R.id.top_news_item_date_textView);
            newsImageView = itemView.findViewById(R.id.top_news_item_news_imageView);
            topItemRelativeLayout = itemView.findViewById(R.id.top_news_item_RelativeLayout);
            iconProgressBar = itemView.findViewById(R.id.top_news_item_icon_progress_bar);
        }
    }
}
