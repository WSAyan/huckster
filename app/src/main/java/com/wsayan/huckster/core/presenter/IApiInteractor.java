package com.wsayan.huckster.core.presenter;

import com.wsayan.huckster.core.model.pojo.Sources;
import com.wsayan.huckster.core.model.pojo.TopNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by wahid.sadique on 8/30/2017.
 */

public interface IApiInteractor {
    @GET("v2/sources")
    Call<Sources> getNewsSources(@Header("X-Api-Key") String apiKey, @Query("language") String language, @Query("country") String country);

    @GET("v2/top-headlines")
    Call<TopNews> getTopNews(@Header("X-Api-Key") String apiKey, @Query("language") String language, @Query("country") String country, @Query("category") String category);

    @GET("v2/everything")
    Call<TopNews> getNewsSearchResult(@Header("X-Api-Key") String apiKey, @Query("q") String query);
}
