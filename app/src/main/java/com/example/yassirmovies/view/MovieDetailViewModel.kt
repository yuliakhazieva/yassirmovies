package com.example.yassirmovies.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yassirmovies.core.BaseViewModel
import com.example.yassirmovies.domain.IMoviesInteractor
import com.example.yassirmovies.domain.Movie
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDetailViewModel(
    private val moviesInteractor: IMoviesInteractor
) : BaseViewModel() {

    private val _movie: MutableLiveData<Movie> = MutableLiveData()
    private val _error: MutableLiveData<Boolean> = MutableLiveData()

    val movie: LiveData<Movie>
        get() = _movie
    val error: LiveData<Boolean>
        get() = _error

    fun getMovieDetails(id: Int) {
        compositeDisposable.addAll(
            moviesInteractor.getMovieDetails(id)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _movie.postValue(it)
                    _error.postValue(false)
                }, {
                    _error.postValue(true)
                })
        )
    }
}