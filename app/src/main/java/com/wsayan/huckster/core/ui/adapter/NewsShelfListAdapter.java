package com.wsayan.huckster.core.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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
import com.wsayan.huckster.core.model.binder.DataTable;
import com.wsayan.huckster.core.presenter.IDbInteractor;
import com.wsayan.huckster.core.ui.activity.NewsActivity;
import com.wsayan.huckster.core.utility.CommonOperations;
import com.wsayan.huckster.core.utility.GlobalConstants;

/**
 * Created by wahid.sadique on 9/14/2017.
 */

public class NewsShelfListAdapter extends RecyclerView.Adapter<NewsShelfListAdapter.ViewHolder> {
    private DataTable dataTable;
    private IDbInteractor dbInteractor;
    private Context context;

    public NewsShelfListAdapter(IDbInteractor dbInteractor) {
        this.dbInteractor = dbInteractor;
        this.dataTable = dbInteractor.getFavourites();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_shelf_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int currentPosition = position;

        final String id = dataTable.get(position).get("id").toString();
        final String name = dataTable.get(position).get("name").toString();
        final String description = dataTable.get(position).get("description").toString();
        final String url = dataTable.get(position).get("url").toString();

        holder.nameTextView.setText(name);
        holder.descriptionTextView.setText(description);
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status = dbInteractor.removeFavourites(id);
                if (status > 0) {
                    dataTable.remove(currentPosition);
                    notifyDataSetChanged();
                }
            }
        });
        holder.shelfItemLinearLayout.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public int getItemCount() {
        return dataTable.size();
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView nameTextView, descriptionTextView;
        protected ImageView deleteImageView, iconImageView;
        protected LinearLayout shelfItemLinearLayout;
        protected ProgressBar iconProgressBar;

        ViewHolder(View itemView) {
            super(itemView);
            initializeWidgets(itemView);
        }

        private void initializeWidgets(View itemView) {
            nameTextView = itemView.findViewById(R.id.shelf_item_name_textView);
            descriptionTextView = itemView.findViewById(R.id.shelf_item_description_textView);
            deleteImageView = itemView.findViewById(R.id.shelf_item_delete_imageView);
            shelfItemLinearLayout = itemView.findViewById(R.id.shelf_item_LinearLayout);
            iconImageView = itemView.findViewById(R.id.shelf_item_icon_imageView);
            iconProgressBar = itemView.findViewById(R.id.shelf_item_icon_progress_bar);
        }
    }
}
