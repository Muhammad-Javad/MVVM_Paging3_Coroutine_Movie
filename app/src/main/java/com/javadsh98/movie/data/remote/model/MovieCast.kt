package com.javadsh98.movie.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class MovieCast(
    val cast_id: Int
    , val character: String
    , val credit_id: String
    , val gender: Int
    , val id: Int
    , val name: String
    , val order: Int
    , val profile_path: String?
){

    val fullProfilePath
        get() = "http://image.tmdb.org/t/p/w342$profile_path"

}