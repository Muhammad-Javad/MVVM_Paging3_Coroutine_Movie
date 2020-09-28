package com.javadsh98.movie.data.remote.paging

import androidx.paging.PagingSource
import com.javadsh98.movie.data.remote.api.Api
import com.javadsh98.movie.data.remote.model.TvSeries
import com.javadsh98.movie.utils.API_VALUE
import com.javadsh98.movie.utils.PAGE_START
import retrofit2.HttpException
import java.io.IOException

class SearchingSeriesPagingSource(
    val api: Api
    , val query: String
) : PagingSource<Int, TvSeries>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvSeries> {
        val page = params.key ?: PAGE_START
        return try {
            val response = api.searchingSeries(API_VALUE, query, page)
            val series = response.results
            LoadResult.Page(
                series
                , if(page == PAGE_START) null else page -1
                , if (series.isEmpty()) null else page +1
            )
        }catch (exception: HttpException){
            LoadResult.Error(exception)
        }catch (exception: IOException){
            LoadResult.Error(exception)
        }
    }

}