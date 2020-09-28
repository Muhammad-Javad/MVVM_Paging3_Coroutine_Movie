package com.javadsh98.movie.presentation.main.fragment.list.cast

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.javadsh98.movie.data.remote.model.MovieCast
import com.javadsh98.movie.data.remote.model.TvCast
import com.javadsh98.movie.utils.CAST_MOVIE
import com.javadsh98.movie.utils.CAST_TV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

class CastAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(diff) {

    val scope = CoroutineScope(Dispatchers.Default)

    companion object {
        val diff = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CAST_MOVIE -> MovieCastViewHolder.create(parent)
            CAST_TV -> SeriesCastViewHolder.create(parent)
            else -> throw ClassCastException("invalid class passed . ")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieCastViewHolder -> {
                val model = getItem(position) as DataItem.CastMovie
                holder.onBind( model.movieCast)
            }
            is SeriesCastViewHolder ->{
                val model = getItem(position) as DataItem.CastSeries
                holder.onBind(model.tvCast)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.CastMovie -> CAST_MOVIE
            is DataItem.CastSeries -> CAST_TV
        }
    }

    fun submitMovie(movieCast: List<MovieCast>){
        scope.launch {
            val mapped = movieCast.map {
                DataItem.CastMovie(it)
            }
            withContext(Dispatchers.Main){
                submitList(mapped)
            }
        }
    }

    fun submitSeries(tvCast: List<TvCast>){
        scope.launch {
            val mapped = tvCast.map {
                DataItem.CastSeries(it)
            }
            withContext(Dispatchers.Main){
                submitList(mapped)
            }
        }
    }

}


sealed class DataItem {

    data class CastMovie(val movieCast: MovieCast) : DataItem() {
        override val id: Int
            get() = movieCast.id
    }

    data class CastSeries(val tvCast: TvCast) : DataItem() {
        override val id: Int
            get() = tvCast.id

    }

    abstract val id: Int

}

