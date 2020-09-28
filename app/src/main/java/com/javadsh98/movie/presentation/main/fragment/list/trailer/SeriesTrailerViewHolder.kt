package com.javadsh98.movie.presentation.main.fragment.list.trailer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.javadsh98.movie.R
import com.javadsh98.movie.data.remote.model.TvTrailer
import kotlinx.android.synthetic.main.movie_trailers_item.view.*
import kotlinx.android.synthetic.main.series_trailers_item.view.*

class SeriesTrailerViewHolder(val view: View) : RecyclerView.ViewHolder(view){

    fun onBind(tvTrailer: TvTrailer, clicked: clicked) = with(itemView){
        series_trailer_image.load(tvTrailer.fullTrailerImagePath){
            placeholder(R.drawable.ic_launcher_background)
            crossfade(true)
            transformations(RoundedCornersTransformation(3f, 3f, 3f, 3f))
        }
        series_trailer_text.text = tvTrailer.name
        setOnClickListener {
            clicked.invoke(tvTrailer)
        }
    }

    companion object{
        fun create(viewGroup: ViewGroup): SeriesTrailerViewHolder{
            return SeriesTrailerViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.series_trailers_item, viewGroup, false)
            )
        }
    }

}