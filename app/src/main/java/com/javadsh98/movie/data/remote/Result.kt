package com.javadsh98.movie.data.remote

sealed class Result<out T: Any>{
    data class Success<out T: Any>(val data: T): Result<T>()
    data class Error(val message: String): Result<Nothing>()
}