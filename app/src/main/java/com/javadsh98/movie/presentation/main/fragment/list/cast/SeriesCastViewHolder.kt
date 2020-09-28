package com.javadsh98.movie.presentation.main.fragment.list.cast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.javadsh98.movie.R
import com.javadsh98.movie.data.remote.model.TvCast
import kotlinx.android.synthetic.main.series_cast_item.view.*

class SeriesCastViewHolder(val view: View) : RecyclerView.ViewHolder(view){

    fun onBind(tvCast: TvCast) = with(itemView){
        series_cast_name.text = tvCast.name
        series_cast_photo.load(tvCast.fullProfilePath){
            placeholder(R.drawable.ic_launcher_background)
            crossfade(true)
            transformations(RoundedCornersTransformation(3f, 3f, 3f, 3f))
        }
    }

    companion object{
        fun create(parent: ViewGroup): SeriesCastViewHolder {
            return SeriesCastViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.series_cast_item, parent, false)
            )
        }
    }

}