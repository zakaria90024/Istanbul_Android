package com.copypasteit.istanbul.main.data.remote;



import com.copypasteit.istanbul.main.model.History;
import com.copypasteit.istanbul.main.model.Post;
import com.copypasteit.istanbul.main.model.Slider;
import com.copypasteit.istanbul.main.model.Tourist;

import java.util.List;
import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Query;


public interface APIService {
    @GET("/api/istanbul/7")
    Call<List<History>> getHistory();

    @GET("/api/istanbul/8")
    Call<List<Tourist>> getTouristPlace();

    @GET("/api/monako/post")
    Call<List<Post>> getpost();

    @GET("/api/monako/post")
    Call<List<Post>> getUsers(@Query("name") String keyword);

    @GET("/api/monako/slider")
    Call<List<Slider>> getSlider();
}
