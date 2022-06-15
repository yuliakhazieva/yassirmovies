package com.example.yassirmovies.domain

import androidx.paging.PagingData
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface IMoviesInteractor {

    fun getMovieList(): Flowable<PagingData<Movie>>

    fun getMovieDetails(id: Int): Single<Movie>
}