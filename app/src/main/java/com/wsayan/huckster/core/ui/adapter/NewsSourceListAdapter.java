package com.wsayan.huckster.core.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wsayan.huckster.core.R;
import com.wsayan.huckster.core.model.pojo.Source;
import com.wsayan.huckster.core.presenter.IDbInteractor;
import com.wsayan.huckster.core.ui.activity.NewsActivity;
import com.wsayan.huckster.core.utility.CommonOperations;
import com.wsayan.huckster.core.utility.GlobalConstants;
import com.wsayan.huckster.core.utility.WebUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wahid.sadique on 9/17/2017.
 */

public class NewsSourceListAdapter extends RecyclerView.Adapter<NewsSourceListAdapter.ViewHolder> {
    private Context context;
    private List<Source> sources;
    private IDbInteractor dbInteractor;

    public NewsSourceListAdapter(Context context, IDbInteractor dbInteractor, List<Source> sources) {
        this.context = context;
        this.sources = sources;
        this.dbInteractor = dbInteractor;
    }

    @Override
    public NewsSourceListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_source_item, parent, false);
        return new NewsSourceListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NewsSourceListAdapter.ViewHolder holder, int position) {
        final String id = sources.get(position).getId();
        final String name = sources.get(position).getName();
        final String description = sources.get(position).getDescription();
        final String url = sources.get(position).getUrl();

        final boolean isFavourite = dbInteractor.checkFavourites(id);

        holder.favImageView.setImageBitmap(getMarkerBitmap(isFavourite));
        holder.nameTextView.setText(name);
        holder.descriptionTextView.setText(description);
        holder.favImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavourite) {
                    dbInteractor.markFavourites(id, name, description, url);
                    notifyDataSetChanged();
                } else {
                    dbInteractor.removeFavourites(id);
                    notifyDataSetChanged();
                }
            }
        });

        holder.itemLinearLayout.setOnClickListener(new View.OnClickListener() {
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

        loadImage(CommonOperations.iconUrlMaker(url), holder.iconImageView, holder.iconProgressBar);
    }

    private Bitmap getMarkerBitmap(Boolean isFavourite) {
        if (isFavourite) {
            return BitmapFactory.decodeResource(context.getResources(), android.R.drawable.star_on);
        } else {
            return BitmapFactory.decodeResource(context.getResources(), android.R.drawable.star_off);
        }
    }

    private void loadImage(String url, ImageView imageView, final ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        Picasso picasso = Picasso.with(context);
        picasso.load(url).error(R.drawable.ic_folded_newspaper).into(imageView, new Callback() {
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
        return sources.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, descriptionTextView;
        ImageView favImageView, iconImageView;
        LinearLayout itemLinearLayout;
        ProgressBar iconProgressBar;

        ViewHolder(View itemView) {
            super(itemView);
            initializeWidgets(itemView);
        }

        private void initializeWidgets(View itemView) {
            nameTextView = itemView.findViewById(R.id.source_item_name_textView);
            descriptionTextView = itemView.findViewById(R.id.source_item_description_textView);
            favImageView = itemView.findViewById(R.id.source_item_fav_imageView);
            itemLinearLayout = itemView.findViewById(R.id.source_item_LinearLayout);
            iconImageView = itemView.findViewById(R.id.source_item_icon_imageView);
            iconProgressBar = itemView.findViewById(R.id.source_item_icon_progress_bar);
        }
    }
}
