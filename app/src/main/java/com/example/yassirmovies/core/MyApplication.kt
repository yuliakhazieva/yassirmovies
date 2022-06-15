package com.example.yassirmovies.core

import android.app.Application
import com.example.yassirmovies.domain.IMoviesInteractor
import com.example.yassirmovies.domain.MoviesInteractor
import com.example.yassirmovies.repository.IMoviesRepository
import com.example.yassirmovies.repository.MoviePagingSource
import com.example.yassirmovies.repository.MoviesRepository
import com.example.yassirmovies.view.MovieDetailViewModel
import com.example.yassirmovies.view.MovieListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class MyApplication : Application() {

    private val appModule = module {
        single<IMoviesRepository> { MoviesRepository(get(), MoviePagingSource(get())) }
        single { API.get() }
        single<IMoviesInteractor> { MoviesInteractor(get()) }
        viewModel { MovieDetailViewModel(get()) }
        viewModel { MovieListViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}