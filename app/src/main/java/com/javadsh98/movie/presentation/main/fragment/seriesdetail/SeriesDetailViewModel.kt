package com.javadsh98.movie.presentation.main.fragment.seriesdetail

import android.support.v4.os.IResultReceiver
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.javadsh98.movie.data.remote.Result
import com.javadsh98.movie.data.remote.model.TvCast
import com.javadsh98.movie.data.remote.model.TvGenres
import com.javadsh98.movie.data.remote.model.TvReview
import com.javadsh98.movie.data.remote.model.TvTrailer
import com.javadsh98.movie.domain.repository.IRepository
import com.javadsh98.movie.presentation.ext.SuperViewModel
import kotlinx.coroutines.launch

class SeriesDetailViewModel @ViewModelInject constructor(val iRepository: IRepository) : SuperViewModel(){

    private val _genres = MutableLiveData<List<TvGenres>>()
    val genres: LiveData<List<TvGenres>>
        get() = _genres

    init {
        uiScope.launch {
            _genres.value = when(val response = iRepository.getSeriesGenres()){
                is Result.Success -> {
                     response.data
                }
                is Result.Error -> {
                    null
                }
            }
        }
    }

    fun getTrailer(tvid: Int): LiveData<List<TvTrailer>?>{
        val trailers = MutableLiveData<List<TvTrailer>>()
        uiScope.launch {
            trailers.value = when(val response = iRepository.getSeriesTrailer(tvid)){
                is Result.Success -> {
                    response.data
                }
                is Result.Error -> {
                    null
                }
            }
        }
        return trailers
    }

    fun getReviews(tvid: Int): LiveData<List<TvReview>?>{
        val review = MutableLiveData<List<TvReview>>()
        uiScope.launch {
            review.value = when(val response = iRepository.getSeriesReview(tvid)){
                is Result.Success -> response.data
                is Result.Error -> null
            }
        }
        return review
    }

    fun getCast(tvid: Int): LiveData<List<TvCast>?>{
        val cast = MutableLiveData<List<TvCast>>()
        uiScope.launch {
            cast.value = when(val response = iRepository.getSeriesCast(tvid)){
                is Result.Success -> response.data
                is Result.Error -> null
            }
        }
        return cast
    }

}