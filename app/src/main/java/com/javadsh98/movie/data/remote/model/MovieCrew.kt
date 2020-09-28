package com.javadsh98.movie.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class MovieCrew(

    val credit_id: String
    , val department: String
    , val gender: Int
    , val id: Int
    , val job: String
    , val name: String
    , val profile_path: String
)