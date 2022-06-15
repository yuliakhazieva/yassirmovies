package com.example.yassirmovies.repository

import androidx.paging.PagingData
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface IMoviesRepository {

    fun getMovieList(): Flowable<PagingData<MovieDetailsNO>>

    fun getMovieDetails(id: Int): Single<MovieDetailsNO>
}