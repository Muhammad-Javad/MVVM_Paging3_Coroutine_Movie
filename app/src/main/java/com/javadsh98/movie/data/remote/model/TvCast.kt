package com.javadsh98.movie.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvCast(

    val character: String
    , val credit_id: String
    , val id: Int
    , val name: String
    , val gender: Int
    , val profile_path: String?
    , val order: Int

): Parcelable{

    val fullProfilePath
        get() = "http://image.tmdb.org/t/p/w342$profile_path"

}