package com.javadsh98.movie.data.remote.model.response

import com.javadsh98.movie.data.remote.model.*

data class ResponseMovie(
    val page: Int
   , val total_results: Int
   , val total_pages: Int
   , val results: List<Movie>
)

data class ResponseMovieGenre(

    val genres: List<MovieGenres>

)

data class ResponseMovieReview(

    val id: Int
    , val page: Int
    , val results: List<MovieReview>
    , val total_pages: Int
    , val total_results: Int

)

data class ResponseMovieCredit(

    val id: Int
    , val cast: List<MovieCast>
    , val crew: List<MovieCrew>

)

data class ResponseMovieTrailer(
    val id: Int
    , val results: List<MovieTrailer>
)

data class ResponseSeries(
    val page: Int
    , val total_results: Int
    , val total_pages: Int
    , val results: List<TvSeries>
)

data class ResponseSeriesGenre(

    val genres: List<TvGenres>

)

data class ResponseSeriesReview(

    val id: Int
    , val page: Int
    , val results: List<TvReview>
    , val total_pages: Int
    , val total_results: Int

)

data class ResponseSeriesCredit(

    val id: Int
    , val cast: List<TvCast>
    , val crew: List<TvCrew>

)

data class ResponseSeriesTrailer(
    val id: Int
    , val results: List<TvTrailer>
)
