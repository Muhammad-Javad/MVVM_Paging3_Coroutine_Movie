package com.javadsh98.movie.presentation.main.fragment.list.trailer

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.annotations.Until
import com.javadsh98.movie.data.remote.model.MovieTrailer
import com.javadsh98.movie.data.remote.model.TvSeries
import com.javadsh98.movie.data.remote.model.TvTrailer
import com.javadsh98.movie.presentation.main.fragment.list.trailer.DataItem
import com.javadsh98.movie.utils.TRAILER_MOVIE
import com.javadsh98.movie.utils.TRAILER_TV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

typealias clicked = (Any) -> Unit

class TrailerAdapter(val clicked: clicked) : ListAdapter<DataItem, RecyclerView.ViewHolder>(diff){

    val scope = CoroutineScope(Dispatchers.Default)

    companion object{
        val diff = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id  == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TRAILER_TV -> SeriesTrailerViewHolder.create(parent)
            TRAILER_MOVIE -> MovieTrailerViewHolder.create(parent)
            else -> throw ClassCastException("invalid class found . ")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = getItem(position))
        {
            is DataItem.MTrailer -> {
                holder as MovieTrailerViewHolder
                holder.onBind(item.movieTrailer, clicked)
            }
            is DataItem.STrailer -> {
                holder  as SeriesTrailerViewHolder
                holder.onBind(item.tvTrailer, clicked)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is DataItem.MTrailer -> TRAILER_MOVIE
            is DataItem.STrailer -> TRAILER_TV
        }
    }

    fun movieSubmit(movieTrailer: List<MovieTrailer>){
        scope.launch {
            val mapped = movieTrailer.map {
                DataItem.MTrailer(it)
            }
            withContext(Dispatchers.Main){
                submitList(mapped)
            }
        }
    }

    fun seriesSubmit(series: List<TvTrailer>){
        scope.launch {
            val mapped = series.map {
                DataItem.STrailer(it)
            }
            withContext(Dispatchers.Main){
                submitList(mapped)
            }
        }
    }

}


sealed class DataItem{

    data class MTrailer(val movieTrailer: MovieTrailer): DataItem(){
        override val id: String
            get() = movieTrailer.id

    }

    data class STrailer(val tvTrailer: TvTrailer): DataItem(){
        override val id: String
            get() = tvTrailer.id

    }

    abstract val id: String

}