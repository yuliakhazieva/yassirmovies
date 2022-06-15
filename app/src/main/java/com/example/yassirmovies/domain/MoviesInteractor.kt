package com.example.yassirmovies.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.example.yassirmovies.repository.IMoviesRepository
import com.example.yassirmovies.repository.MovieDetailsNO
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class MoviesInteractor(
    private val repository: IMoviesRepository
) : IMoviesInteractor {

    override fun getMovieList(): Flowable<PagingData<Movie>> {
        return repository.getMovieList().map {
            it.map { convertMovieToDomain(it) }
        }
    }

    override fun getMovieDetails(id: Int): Single<Movie> {
        return repository.getMovieDetails(id).map {
            convertMovieToDomain(it)
        }
    }

    private fun convertMovieToDomain(movieNO: MovieDetailsNO): Movie {
        return Movie(
            id = movieNO.id ?: -1,
            name = movieNO.title ?: "",
            description = movieNO.overview ?: "",
            image = IMAGE_BASE_URL + movieNO.poster_path,
            year = movieNO.release_date?.split("-")?.get(0) ?:""
        )
    }

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}