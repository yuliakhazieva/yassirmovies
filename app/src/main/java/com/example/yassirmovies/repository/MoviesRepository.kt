package com.example.yassirmovies.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class MoviesRepository(private val api: MoviesAPI, private val pagingSource: MoviePagingSource): IMoviesRepository {

    override fun getMovieList(): Flowable<PagingData<MovieDetailsNO>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40),
            pagingSourceFactory = { pagingSource }
        ).flowable
    }

    override fun getMovieDetails(id: Int): Single<MovieDetailsNO> {
        return api.getMovieDetails(id = id)
    }
}