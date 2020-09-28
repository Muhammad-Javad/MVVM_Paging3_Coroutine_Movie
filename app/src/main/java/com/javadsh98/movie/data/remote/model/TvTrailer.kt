package com.javadsh98.movie.data.remote.model

data class TvTrailer(

    val id: String
    , val iso_639_1: String
    , val iso_3166_1: String
    , val key: String
    , val name: String
    , val site: String
    , val size: Int
    , val type: String

){

    val fullTrailerImagePath
        get() = "https://img.youtube.com/vi/$key/hqdefault.jpg"

}