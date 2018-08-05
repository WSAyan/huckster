package com.wsayan.huckster.core.ui.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wsayan.huckster.core.R;
import com.wsayan.huckster.core.model.pojo.Article;
import com.wsayan.huckster.core.model.pojo.TopNews;
import com.wsayan.huckster.core.presenter.AppPresenter;
import com.wsayan.huckster.core.presenter.IApiInteractor;
import com.wsayan.huckster.core.ui.adapter.TopNewsListAdapter;
import com.wsayan.huckster.core.utility.HttpStatusCodes;
import com.wsayan.huckster.core.utility.SharedPrefUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopNewsFragment extends Fragment {
    private RecyclerView topNewsRecyclerView;
    private Context context;
    private IApiInteractor apiInteractor;
    private Call<TopNews> topNewsCall;
    private List<Article> articles;
    private SharedPreferences sharedPref;

    public TopNewsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_news, container, false);
        initializeWidgets(view);
        initializeData();
        return view;
    }

    private void initializeWidgets(View view) {
        topNewsRecyclerView = view.findViewById(R.id.top_news_list_recyclerView);
    }

    private void initializeData() {
        context = getActivity();
        apiInteractor = new AppPresenter().getApiInterface();
        sharedPref = new AppPresenter().getSharedPrefInterface(context);
        downLoadList();
    }

    private void downLoadList() {
        topNewsCall = apiInteractor.getTopNews(sharedPref.getString(SharedPrefUtils._API_KEY, null), sharedPref.getString(SharedPrefUtils._LANGUAGE, null), null, null);
        topNewsCall.enqueue(new Callback<TopNews>() {
            @Override
            public void onResponse(Call<TopNews> call, Response<TopNews> response) {
                if (response.code() == HttpStatusCodes.OK) {
                    articles = response.body().getArticles();
                    createList(articles);
                }
            }

            @Override
            public void onFailure(Call<TopNews> call, Throwable t) {

            }
        });
    }

    private void createList(List<Article> articles) {
        TopNewsListAdapter topNewsListAdapter = new TopNewsListAdapter(context, articles);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        topNewsRecyclerView.setLayoutManager(layoutManager);
        topNewsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        topNewsRecyclerView.setAdapter(topNewsListAdapter);
    }

}
