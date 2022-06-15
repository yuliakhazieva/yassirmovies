package com.example.yassirmovies.core

import com.example.yassirmovies.repository.MoviesAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object API {

    private const val API_BASE_URL = "https://api.themoviedb.org/3/"

    private var httpClient = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }

    private val apiService = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(httpClient.build())
        .build()
        .create(MoviesAPI::class.java)

    fun get(): MoviesAPI = apiService

    class MyInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val originalURL = original.url()
            val url = originalURL.newBuilder()
                .addQueryParameter("api_key", "c9856d0cb57c3f14bf75bdc6c063b8f3")
                .build() //not a safe way to store keys in real scenario obviously
            val requestBuilder = original.newBuilder().url(url)
            return chain.proceed(requestBuilder.build())
        }
    }
}