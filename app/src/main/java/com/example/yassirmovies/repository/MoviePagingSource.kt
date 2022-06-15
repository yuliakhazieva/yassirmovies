package com.example.yassirmovies.repository

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MoviePagingSource(private val api: MoviesAPI) :
    RxPagingSource<Int, MovieDetailsNO>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MovieDetailsNO>> {
        return api.getMovieList(page = params.key ?: 1)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it.results, params.key ?: 1) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: List<MovieDetailsNO>?, position: Int): LoadResult<Int, MovieDetailsNO> {
        return LoadResult.Page(
            data = data?: listOf(),
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == data?.size) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, MovieDetailsNO>): Int? {
        TODO("Not yet implemented")
    }
}