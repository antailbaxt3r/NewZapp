package com.antailbaxt3r.newzapp;

import com.antailbaxt3r.newzapp.models.NewsReponse;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

public interface APIInterface {

  @GET("top-headlines")
  Call<NewsReponse> getNews(@QueryMap Map<String, String> map);

}
