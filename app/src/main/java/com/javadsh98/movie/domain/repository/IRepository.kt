package com.javadsh98.movie.domain.repository

import androidx.paging.PagingData
import com.javadsh98.movie.data.remote.Result
import com.javadsh98.movie.data.remote.model.*
import kotlinx.coroutines.flow.Flow

interface IRepository {

    fun getMovie(category: String, language: String): Flow<PagingData<Movie>>
    fun getSearchedMovie(query: String): Flow<PagingData<Movie>>

    fun getSeries(category: String, language: String): Flow<PagingData<TvSeries>>
    fun getSearchedSeries(query: String): Flow<PagingData<TvSeries>>

    suspend fun getMovieReview(movieId: Int): Result<List<MovieReview>>
    suspend fun getMovieTrailer(movieId: Int): Result<List<MovieTrailer>>
    suspend fun getMovieCast(movieId: Int): Result<List<MovieCast>>
    suspend fun getMovieGenres(): Result<List<MovieGenres>>

    suspend fun getSeriesReview(seriesId: Int): Result<List<TvReview>>
    suspend fun getSeriesTrailer(seriesId: Int): Result<List<TvTrailer>>
    suspend fun getSeriesCast(seriesId: Int): Result<List<TvCast>>
    suspend fun getSeriesGenres(): Result<List<TvGenres>>

}