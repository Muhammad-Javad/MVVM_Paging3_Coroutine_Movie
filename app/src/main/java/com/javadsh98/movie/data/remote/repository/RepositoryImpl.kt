package com.javadsh98.movie.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.javadsh98.movie.data.remote.Result
import com.javadsh98.movie.data.remote.api.Api
import com.javadsh98.movie.data.remote.model.*
import com.javadsh98.movie.data.remote.paging.MoviePagingSource
import com.javadsh98.movie.data.remote.paging.SearchingMoviePagingSource
import com.javadsh98.movie.data.remote.paging.SearchingSeriesPagingSource
import com.javadsh98.movie.data.remote.paging.SeriesPagingSource
import com.javadsh98.movie.domain.repository.IRepository
import com.javadsh98.movie.utils.API_KEY
import com.javadsh98.movie.utils.API_VALUE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(val api: Api) : IRepository {

    override fun getMovie(category: String, language: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false), pagingSourceFactory = {
                MoviePagingSource(api, category, language)

            }
        ).flow
    }

    override fun getSearchedMovie(query: String): Flow<PagingData<Movie>> {
        return Pager(config = PagingConfig(
            pageSize = 20, enablePlaceholders = false
        ), pagingSourceFactory = {
            SearchingMoviePagingSource(api, query)
        }).flow
    }

    override fun getSeries(category: String, language: String): Flow<PagingData<TvSeries>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                SeriesPagingSource(api, category, language)
            }
        ).flow
    }

    override fun getSearchedSeries(query: String): Flow<PagingData<TvSeries>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                SearchingSeriesPagingSource(api, query)
            }
        ).flow
    }

    override suspend fun getMovieReview(movieId: Int): Result<List<MovieReview>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getMovieReview(movieId, API_VALUE)
                val review = response.results
                Result.Success(review)
            } catch (exception: IOException) {
                Result.Error(exception.message!!)
            } catch (exception: HttpException) {
                Result.Error(exception.message!!)
            }
        }
    }

    override suspend fun getMovieTrailer(movieId: Int): Result<List<MovieTrailer>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getMovieTrailer(movieId, API_VALUE)
                val trailer = response.results
                Result.Success(trailer)
            } catch (exception: Exception) {
                Result.Error(exception.message!!)
            } catch (exception: Exception) {
                Result.Error(exception.message!!)
            }

        }
    }

    override suspend fun getMovieCast(movieId: Int): Result<List<MovieCast>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getMovieCredits(movieId, API_VALUE)
                val cast = response.cast
                Result.Success(cast)
            } catch (exception: Exception) {
                Result.Error(exception.message!!)
            } catch (exception: Exception) {
                Result.Error(exception.message!!)
            }
        }
    }

    override suspend fun getMovieGenres(): Result<List<MovieGenres>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getMovieGenre(API_VALUE)
                val genres = response.genres
                Result.Success(genres)
            } catch (exception: Exception) {
                Result.Error(exception.message!!)
            } catch (exception: Exception) {
                Result.Error(exception.message!!)
            }
        }
    }

    override suspend fun getSeriesReview(seriesId: Int): Result<List<TvReview>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getTvReviews(seriesId, API_VALUE)
                val review = response.results
                Result.Success(review)
            } catch (exception: Exception) {
                Result.Error(exception.message!!)
            } catch (exception: Exception) {
                Result.Error(exception.message!!)
            }
        }
    }

    override suspend fun getSeriesTrailer(seriesId: Int): Result<List<TvTrailer>> {
        return withContext(
            Dispatchers.IO
        ) {
            try {
                val response = api.getTvTrailer(seriesId, API_VALUE)
                val trailer = response.results
                Result.Success(trailer)
            } catch (exception: Exception) {
                Result.Error(exception.message!!)
            } catch (exception: Exception) {
                Result.Error(exception.message!!)
            }
        }
    }

    override suspend fun getSeriesCast(seriesId: Int): Result<List<TvCast>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getTvCredits(seriesId, API_VALUE)
                val cast = response.cast
                Result.Success(cast)
            } catch (exception: Exception) {
                Result.Error(exception.message!!)
            } catch (exception: Exception) {
                Result.Error(exception.message!!)
            }
        }
    }

    override suspend fun getSeriesGenres(): Result<List<TvGenres>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getTvGenres(API_VALUE)
                val genres = response.genres
                Result.Success(genres)
            } catch (exception: Exception) {
                Result.Error(exception.message!!)
            } catch (exception: Exception) {
                Result.Error(exception.message!!)
            }
        }
    }

}