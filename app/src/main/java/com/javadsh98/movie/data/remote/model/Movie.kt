package com.javadsh98.movie.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(val popularity: Double
                 ,val vote_count: Int
                ,val video: Boolean
                ,val poster_path: String
                ,val id: Int
                ,val adult: Boolean
                ,val backdrop_path: String
                ,val original_language: String
                ,val original_title: String
                ,val genre_ids: List<Int>
                ,val title: String
                ,val vote_average: Double
                ,val overview: String
                ,val release_date: String
): Parcelable{

    val fullPosterPath
        get() = "http://image.tmdb.org/t/p/w342$poster_path"

    val fullBackdropPath
        get() = "http://image.tmdb.org/t/p/w780$backdrop_path"

}




