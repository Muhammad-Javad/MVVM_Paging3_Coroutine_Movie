package com.javadsh98.movie.data.remote.api

import com.javadsh98.movie.data.remote.model.response.*
import com.javadsh98.movie.utils.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("movie/{$CATEGORY}")
    suspend fun getMovies(
        @Path(CATEGORY) category: String
        , @Query(API_KEY) key: String
        , @Query(LANGUAGE) language: String
        , @Query(PAGE) page: Int
    ): ResponseMovie

    @GET("genre/movie/list")
    suspend fun getMovieGenre(
        @Query(API_KEY) key: String
    ): ResponseMovieGenre

    @GET("movie/{$MOVIE_ID}/reviews")
    suspend fun getMovieReview(
        @Path(MOVIE_ID) movieId: Int,
        @Query(API_KEY) api: String
    ): ResponseMovieReview

    @GET("movie/{$MOVIE_ID}/credits")
    suspend fun getMovieCredits(
        @Path(MOVIE_ID) movieId: Int
        , @Query(API_KEY) key: String
    ): ResponseMovieCredit

    @GET("movie/{$MOVIE_ID}/videos")
    suspend fun getMovieTrailer(
         @Path("movie_id") movieId: Int
        , @Query(API_KEY) key: String
    ): ResponseMovieTrailer

    @GET("tv/{$CATEGORY}")
    suspend fun getSeries(
        @Path(CATEGORY) category: String
        , @Query(API_KEY) key: String
        , @Query(LANGUAGE) language: String
        , @Query(PAGE) page: Int
    ): ResponseSeries

    @GET("genre/tv/list")
    suspend fun getTvGenres(
        @Query(API_KEY) key: String
    ): ResponseSeriesGenre

    @GET("tv/{$TV_ID}/reviews")
    suspend fun getTvReviews(
        @Path(TV_ID) tvId: Int
        , @Query(API_KEY) key: String
    ): ResponseSeriesReview

    @GET("tv/{$TV_ID}/credits")
    suspend fun getTvCredits(
        @Path(TV_ID) tvId: Int
        , @Query(API_KEY) key: String
    ): ResponseSeriesCredit

    @GET("tv/{$TV_ID}/videos")
    suspend fun getTvTrailer(
         @Path(TV_ID) tvId: Int
        , @Query(API_KEY) key: String
    ): ResponseSeriesTrailer

    @GET("search/movie")
    suspend fun searchingMovie(
        @Query(API_KEY) key: String
        , @Query(QUERY) query: String
        , @Query(PAGE) page: Int
    ): ResponseMovie

    @GET("search/tv")
    suspend fun searchingSeries(
        @Query(API_KEY) key: String
        , @Query(QUERY) query: String
        , @Query(PAGE) page: Int
    ): ResponseSeries

}