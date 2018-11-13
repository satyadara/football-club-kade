package com.satyadara.football_club_dicoding.retrofit

import com.google.gson.GsonBuilder
import retrofit.GsonConverterFactory
import retrofit.RxJavaCallAdapterFactory
import retrofit.Retrofit

class RetrofitClient {
    companion object {
        val BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/"
        fun getClient() : Retrofit {
            return Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .baseUrl(BASE_URL)
                .build()
        }
    }
}