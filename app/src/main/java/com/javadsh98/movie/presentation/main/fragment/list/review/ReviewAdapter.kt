package com.javadsh98.movie.presentation.main.fragment.list.review

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.javadsh98.movie.data.remote.model.MovieReview
import com.javadsh98.movie.data.remote.model.TvReview
import com.javadsh98.movie.utils.REVIEW_MOVIE
import com.javadsh98.movie.utils.REVIEW_TV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

class ReviewAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(diff) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            REVIEW_TV -> {
                SeriesReviewViewHolder.create(parent)
            }
            REVIEW_MOVIE -> {
                MovieReviewViewHolder.create(parent)
            }
            else -> throw ClassCastException("invalid class found . ")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val model = getItem(position)) {
            is DataItem.MReview -> {
                holder as MovieReviewViewHolder
                holder.onBind(model.movieReview)
            }
            is DataItem.SReview -> {
                holder as SeriesReviewViewHolder
                holder.onBind(model.tvReview)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.MReview -> REVIEW_MOVIE
            is DataItem.SReview -> REVIEW_TV
        }
    }

    fun submitMovie(movieReview: List<MovieReview>) {
        scope.launch {
            val mapped = movieReview.map {
                DataItem.MReview(it)
            }
            withContext(Dispatchers.Main){
                submitList(mapped)
            }
        }
    }

    fun submitSeries(tvReview: List<TvReview>) {
        scope.launch {
            val mapped = tvReview.map {
                DataItem.SReview(it)
            }
            withContext(Dispatchers.Main){
                submitList(mapped)
            }
        }
    }

}

sealed class DataItem {
    data class MReview(val movieReview: MovieReview) : DataItem() {
        override val id: String
            get() = movieReview.id

    }

    data class SReview(val tvReview: TvReview) : DataItem() {
        override val id: String
            get() = tvReview.id

    }

    abstract val id: String

}