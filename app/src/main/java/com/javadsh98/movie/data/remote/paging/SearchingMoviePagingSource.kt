package com.javadsh98.movie.data.remote.paging

import androidx.paging.PagingSource
import com.javadsh98.movie.data.remote.api.Api
import com.javadsh98.movie.data.remote.model.Movie
import com.javadsh98.movie.utils.API_VALUE
import com.javadsh98.movie.utils.PAGE_START
import retrofit2.HttpException
import java.io.IOException

class SearchingMoviePagingSource(val api: Api, val query: String) : PagingSource<Int, Movie>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: PAGE_START
        return try{
            val response = api.searchingMovie(API_VALUE, query, page)
            val movie = response.results
            LoadResult.Page(movie
                , if(page == PAGE_START) null else page -1
                , if(movie.isEmpty()) null else page +1)
        }catch (exception: HttpException){
            LoadResult.Error(exception)
        }catch (exception: IOException){
            LoadResult.Error(exception)
        }
    }

}