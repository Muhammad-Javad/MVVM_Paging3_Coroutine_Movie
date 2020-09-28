package com.javadsh98.movie.presentation.main.fragment.list.trailer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.javadsh98.movie.R
import com.javadsh98.movie.data.remote.model.MovieTrailer
import kotlinx.android.synthetic.main.movie_trailers_item.view.*

class MovieTrailerViewHolder(val view: View): RecyclerView.ViewHolder(view){

    fun onBind(trailer: MovieTrailer, clicked: clicked) = with(itemView) {
        movie_trailer_image.load(trailer.fullTrailerImagePath){
            placeholder(R.drawable.ic_launcher_background)
            crossfade(true)
            transformations(RoundedCornersTransformation(3f, 3f, 3f, 3f))
        }
        movie_trailer_text.text = trailer.name
        setOnClickListener {
            clicked.invoke(trailer)
        }
    }

    companion object{
        fun create(viewGroup: ViewGroup): MovieTrailerViewHolder{
            return MovieTrailerViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.movie_trailers_item, viewGroup, false)
            )
        }
    }

}