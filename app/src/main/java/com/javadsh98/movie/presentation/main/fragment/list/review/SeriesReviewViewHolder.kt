package com.javadsh98.movie.presentation.main.fragment.list.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javadsh98.movie.R
import com.javadsh98.movie.data.remote.model.TvReview
import com.javadsh98.movie.data.remote.model.TvSeries
import kotlinx.android.synthetic.main.movie_review_item.view.*

class SeriesReviewViewHolder(val view: View) : RecyclerView.ViewHolder(view){

    fun onBind(tvReview: TvReview) = with(itemView){
        review_name.text = tvReview.author
        review_text.text = tvReview.content
    }

    companion object{
        fun create(viewGroup: ViewGroup): SeriesReviewViewHolder{
            return SeriesReviewViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.series_review_item, viewGroup, false)
            )
        }
    }

}