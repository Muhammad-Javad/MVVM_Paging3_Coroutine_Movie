package com.javadsh98.movie.presentation.main.fragment.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.javadsh98.movie.R
import com.javadsh98.movie.data.remote.model.Movie
import com.javadsh98.movie.utils.DATA_VIEW
import com.javadsh98.movie.utils.LOADING_VIEW
import kotlinx.android.synthetic.main.movie_item.view.*
import timber.log.Timber

typealias onMovieClicked = (Movie) -> Unit


class MovieAdapter(val listener: onMovieClicked)
    : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(diff){

    companion object{
        val diff = object:  DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == itemCount) LOADING_VIEW else DATA_VIEW
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder.create(parent)

    class MovieViewHolder(var view: View): RecyclerView.ViewHolder(view){

        fun bind(movie: Movie, listener: onMovieClicked) = with(itemView){
            movie_card.setOnClickListener {
                listener.invoke(movie)
            }
            movie_image.load(movie.fullPosterPath) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                transformations(RoundedCornersTransformation(3f, 3f, 3f, 3f))
            }

        }

        companion object{
            fun create(parent: ViewGroup): MovieViewHolder{
                return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false))
            }
        }

    }


}
