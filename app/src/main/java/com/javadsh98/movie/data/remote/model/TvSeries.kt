package com.javadsh98.movie.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvSeries(

    val original_name: String
    , val genre_ids: List<Int>
    , val name: String
    , val popularity: Double
    , val origin_country: List<String>
    , val vote_count: Int
    , val first_air_date: String
    , val backdrop_path: String?
    , val original_language: String
    , val id: Int
    , val vote_average: Double
    , val overview: String
    , val poster_path: String

): Parcelable{

    val fullPosterPath
        get() = "http://image.tmdb.org/t/p/w342$poster_path"

    val fullBackdropPath
        get() = "http://image.tmdb.org/t/p/w780$backdrop_path"
}