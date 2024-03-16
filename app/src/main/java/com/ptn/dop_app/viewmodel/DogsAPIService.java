package com.ptn.dop_app.viewmodel;

import com.ptn.dop_app.model.DogBreed;
import com.ptn.dop_app.model.DogsAPI;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogsAPIService {
    private static final String BASE_URL = "https://raw.githubusercontent.com/";
    private DogsAPI api;
    public DogsAPIService() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(DogsAPI.class);
    }

    public Single<List<DogBreed>> getDogs() {
        return api.getDogs();
    }

}
