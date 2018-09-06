package com.wsayan.huckster.core.ui.adapter;

import android.content.Context;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wsayan.huckster.core.R;

import java.lang.reflect.Array;

public class SelectListAdapter extends RecyclerView.Adapter<SelectListAdapter.ViewHolder>{
    private Context context;
    private String[] selectArray;

    public SelectListAdapter(Context context, String[] selectArray) {
        this.context = context;
        this.selectArray = selectArray;
    }

    @Override
    public SelectListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_select_item, parent, false);
        return new SelectListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SelectListAdapter.ViewHolder holder, int position) {
        holder.selectTextView.setText(selectArray[position]);
    }

    @Override
    public int getItemCount() {
        return selectArray.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RadioButton selectRadioButton;
        TextView selectTextView;

        ViewHolder(View itemView) {
            super(itemView);
            selectRadioButton = itemView.findViewById(R.id.selectRadioButton);
            selectTextView = itemView.findViewById(R.id.selectTextView);
        }
    }
}
