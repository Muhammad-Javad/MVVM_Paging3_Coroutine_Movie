package com.javadsh98.movie.presentation.main.fragment.list.cast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.javadsh98.movie.R
import com.javadsh98.movie.data.remote.model.MovieCast
import kotlinx.android.synthetic.main.movie_cast_item.view.*

class MovieCastViewHolder(val view: View) : RecyclerView.ViewHolder(view){

    fun onBind(movieCast: MovieCast) = with(itemView){

        movie_cast_name.text = movieCast.name

        movie_cast_photo.load(movieCast.fullProfilePath){
            placeholder(R.drawable.ic_launcher_background)
            crossfade(true)
            transformations(RoundedCornersTransformation(3f, 3f, 3f, 3f))

        }
    }

    companion object{
        fun create(viewGroup: ViewGroup): MovieCastViewHolder {
            return MovieCastViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.movie_cast_item, viewGroup, false)
            )
        }
    }

}