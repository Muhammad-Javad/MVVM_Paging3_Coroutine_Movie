package com.javadsh98.movie.presentation.main.fragment.series

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.javadsh98.movie.data.remote.model.TvSeries
import com.javadsh98.movie.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow

class SeriesViewModel @ViewModelInject constructor(val iRepository: IRepository)
    : ViewModel(){

    var query: String? = null
    var category: String? = null

    var series: Flow<PagingData<TvSeries>>? = null
    var searchedSeries: Flow<PagingData<TvSeries>>? = null

    fun getSeries(category: String, language: String): Flow<PagingData<TvSeries>>?{

        if(!category.isNullOrEmpty()){
            //first time
            if(series == null){
                series = iRepository.getSeries(category, language)
                    .cachedIn(viewModelScope)
                this.category = category
                //dont reload if pervious category and current category is equal
            }else if(this.category != category && series != null){
                series = iRepository.getSeries(category, language).cachedIn(viewModelScope)
                this.category = category
            }
        }
        return series
    }

    fun getSearchedSeries(query: String): Flow<PagingData<TvSeries>>?{

        if(query != null){
            //first time
            if(searchedSeries == null){
                searchedSeries = iRepository.getSearchedSeries(query).cachedIn(viewModelScope)
                this.query = query
            }else if(searchedSeries != null && query != this.query){
                searchedSeries = iRepository.getSearchedSeries(query).cachedIn(viewModelScope)
                this.query = query
            }
        }

        return searchedSeries
    }

}