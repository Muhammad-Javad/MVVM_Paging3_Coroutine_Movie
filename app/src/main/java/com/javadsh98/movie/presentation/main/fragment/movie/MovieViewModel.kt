package com.javadsh98.movie.presentation.main.fragment.movie

import android.text.TextUtils
import android.view.textclassifier.TextLanguage
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.javadsh98.movie.data.remote.model.Movie
import com.javadsh98.movie.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

class MovieViewModel @ViewModelInject constructor(val iRepository: IRepository) : ViewModel(){

    var category: String? = null
    var query: String? = null

    var searchedMovies: Flow<PagingData<Movie>>? = null
    var movies: Flow<PagingData<Movie>>? = null


    fun getMovie(category: String, language: String): Flow<PagingData<Movie>>{

        //first time
        if(movies == null){

            movies = iRepository.getMovie(category, language)
                .cachedIn(viewModelScope)
            this.category = category

        }else if(movies != null && !TextUtils.equals(category, this.category)){

            movies = iRepository.getMovie(category, language)
                .cachedIn(viewModelScope)
            this.category = category

        }
        return movies!!
    }

    fun getSearchedMovie(query: String): Flow<PagingData<Movie>>{

        if(searchedMovies == null){
            searchedMovies = iRepository.getSearchedMovie(query)
                .cachedIn(viewModelScope)
            this.query = query

        }else if(searchedMovies != null && !TextUtils.equals(query, this.query)){
            searchedMovies = iRepository.getSearchedMovie(query)
                .cachedIn(viewModelScope)
            this.query = query

        }
        return searchedMovies!!
    }

}