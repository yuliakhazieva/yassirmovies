package com.example.yassirmovies.repository

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {

    @GET("discover/movie")
    fun getMovieList(@Query("page") page: Int): Single<MovieListNO>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id: Int): Single<MovieDetailsNO>
}