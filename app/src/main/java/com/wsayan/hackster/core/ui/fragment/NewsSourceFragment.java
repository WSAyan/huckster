package com.wsayan.hackster.core.ui.fragment;


import android.app.ProgressDialog;
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

import com.wsayan.hackster.core.R;
import com.wsayan.hackster.core.model.pojo.Source;
import com.wsayan.hackster.core.model.pojo.Sources;
import com.wsayan.hackster.core.presenter.AppPresenter;
import com.wsayan.hackster.core.presenter.IApiInteractor;
import com.wsayan.hackster.core.presenter.IDbInteractor;
import com.wsayan.hackster.core.ui.adapter.NewsSourceListAdapter;
import com.wsayan.hackster.core.utility.HttpStatusCodes;
import com.wsayan.hackster.core.utility.SharedPrefUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsSourceFragment extends Fragment {
    private RecyclerView newsSourceRecyclerView;
    private Context context;
    private IDbInteractor dbInteractor;
    private IApiInteractor apiInteractor;
    private ProgressDialog progressDialog;
    private Call<Sources> sourcesCall;
    private List<Source> sources;
    private SharedPreferences sharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        initializeWidgets(view);
        initializeData();
        return view;
    }

    private void initializeWidgets(View view) {
        newsSourceRecyclerView = view.findViewById(R.id.source_list_recyclerView);
    }

    private void initializeData() {
        context = getActivity();
        dbInteractor = new AppPresenter().getDbInterface(context);
        apiInteractor = new AppPresenter().getApiInterface();
        sharedPref = new AppPresenter().getSharedPrefInterface(context);
        progressDialog = new ProgressDialog(context);
        downloadList();
    }

    private void createList(List<Source> sources) {
        NewsSourceListAdapter newsSourceListAdapter = new NewsSourceListAdapter(context, dbInteractor, sources);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        newsSourceRecyclerView.setLayoutManager(layoutManager);
        newsSourceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        newsSourceRecyclerView.setAdapter(newsSourceListAdapter);
        newsSourceListAdapter.notifyDataSetChanged();
    }

    private void downloadList() {
        progressDialog.setMessage(context.getString(R.string.progress_dialog_loading));
        progressDialog.show();
        sourcesCall = apiInteractor.getNewsSources(sharedPref.getString(SharedPrefUtils._API_KEY, null), sharedPref.getString(SharedPrefUtils._LANGUAGE, null));
        sourcesCall.enqueue(new Callback<Sources>() {
            @Override
            public void onResponse(Call<Sources> call, Response<Sources> response) {
                if (response.code() == HttpStatusCodes.OK) {
                    sources = response.body().getSources();
                    createList(sources);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Sources> call, Throwable t) {
                progressDialog.dismiss();
                sourcesCall.cancel();
            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && sources != null) {
            createList(sources);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        flush();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flush();

    }

    private void flush() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if (sourcesCall != null) {
            sourcesCall.cancel();
        }
    }

}
