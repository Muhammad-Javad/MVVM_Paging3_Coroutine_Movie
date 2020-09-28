package com.javadsh98.movie.presentation.main.fragment.moviedetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.javadsh98.movie.data.remote.Result
import com.javadsh98.movie.data.remote.model.*
import com.javadsh98.movie.domain.repository.IRepository
import com.javadsh98.movie.presentation.ext.SuperViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieDetailViewModel @ViewModelInject constructor(val iRepository: IRepository) :
    SuperViewModel() {

    private val _genres = MutableLiveData< List<MovieGenres>>()
    val genres: LiveData< List<MovieGenres>>
        get() = _genres

    init {

        //first fetch all kind of genres
        uiScope.launch {
            _genres.value = when(val response = iRepository.getMovieGenres()){
                is Result.Success -> {
                    response.data
                }
                is Result.Error -> {
                    null
                }
            }
        }
    }

    fun getMovieReview(movieId: Int): LiveData<List<MovieReview>>{
        val review = MutableLiveData<List<MovieReview>>()
        uiScope.launch {
            review.value = when(val response = iRepository.getMovieReview(movieId)){
                is Result.Success -> {
                    Timber.i("${response.data.size}")
                        response.data
                }
                is Result.Error -> {
                    Timber.i("${response.message}")
                    null
                }
            }
        }
        return review
    }

    fun getMovieTrailer(movieId: Int): LiveData<List<MovieTrailer>?>{
        val trailer = MutableLiveData<List<MovieTrailer>>()
        uiScope.launch {
            trailer.value = when(val response = iRepository.getMovieTrailer(movieId)){
                is Result.Success -> {
                    response.data
                }
                is Result.Error -> {
                    null
                }
            }
        }
        return trailer
    }

    fun getMovieCast(movieId: Int): LiveData<List<MovieCast>?>{
        val cast = MutableLiveData<List<MovieCast>>()
        uiScope.launch {
            cast.value = when(val response = iRepository.getMovieCast(movieId)){
                is Result.Success -> {
                    Timber.i("size = ${response.data.size}")
                    response.data
                }
                is Result.Error -> {
                    Timber.i("${response.message}")
                    null
                }
            }
        }
        return cast
    }

    fun getSeriesReview(movieId: Int): LiveData<List<TvReview>>{
        val review = MutableLiveData<List<TvReview>>()
        uiScope.launch {
            review.value = when(val response = iRepository.getSeriesReview(movieId)){
                is Result.Success -> response.data
                is Result.Error -> null
            }
        }
        return review
    }

    fun getSeriesTrailer(movieId: Int): LiveData<List<TvTrailer>>{
        val trailer = MutableLiveData<List<TvTrailer>>()
        uiScope.launch {
            trailer.value = when(val response = iRepository.getSeriesTrailer(movieId)){
                is Result.Success -> response.data
                is Result.Error -> null
            }
        }
        return trailer
    }

    fun getSeriesCast(movieId: Int): LiveData<List<TvCast>>{
        val cast = MutableLiveData<List<TvCast>>()
        uiScope.launch {
            cast.value = when(val response = iRepository.getSeriesCast(movieId)){
                is Result.Success -> response.data
                is Result.Error -> null
            }
        }
       return cast
    }

}