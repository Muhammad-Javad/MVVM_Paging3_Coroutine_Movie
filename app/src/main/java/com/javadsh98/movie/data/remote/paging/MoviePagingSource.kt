package com.javadsh98.movie.data.remote.paging

import androidx.paging.PagingSource
import com.javadsh98.movie.data.remote.api.Api
import com.javadsh98.movie.data.remote.model.Movie
import com.javadsh98.movie.data.remote.model.response.ResponseMovie
import com.javadsh98.movie.utils.API_KEY
import com.javadsh98.movie.utils.API_VALUE
import com.javadsh98.movie.utils.PAGE_START
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

class MoviePagingSource(
    val api: Api
    , val category: String
    , val language: String
) : PagingSource<Int, Movie>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val page = params.key ?: PAGE_START
        return try {
            val response = if(category != null || language != null){
                api.getMovies(category, API_VALUE, language, page)
            }else{
                throw InvalidObjectException("category and language should not be null")
            }
            val movie = response.results
            LoadResult.Page(movie
                , if (page == PAGE_START) null else page -1
                , if(movie.isEmpty()) null else page +1 )
        }catch (exception: HttpException){
            LoadResult.Error(exception)
        }catch (exception: IOException){
            LoadResult.Error(exception)
        }

    }

}