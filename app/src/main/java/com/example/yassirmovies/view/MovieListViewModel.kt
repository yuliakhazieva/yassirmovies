package com.example.yassirmovies.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.yassirmovies.core.BaseViewModel
import com.example.yassirmovies.domain.IMoviesInteractor
import com.example.yassirmovies.domain.Movie
import io.reactivex.rxjava3.core.Flowable

class MovieListViewModel(
    private val moviesInteractor: IMoviesInteractor
) : BaseViewModel() {

    private val _error: MutableLiveData<Boolean> = MutableLiveData()

    val error: LiveData<Boolean>
        get() = _error

    fun getMovieList(): Flowable<PagingData<Movie>> {
        return moviesInteractor.getMovieList()
    }
}