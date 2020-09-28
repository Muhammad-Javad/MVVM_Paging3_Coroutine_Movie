package com.javadsh98.movie.presentation.main.fragment.list.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javadsh98.movie.R
import com.javadsh98.movie.data.remote.model.MovieReview
import kotlinx.android.synthetic.main.movie_review_item.view.*

class MovieReviewViewHolder(val view: View) : RecyclerView.ViewHolder(view){

    fun onBind(movieReview: MovieReview) = with(itemView){
        review_name.text = movieReview.author
        review_text.text = movieReview.content
    }

    companion object{
        fun create(viewGroup: ViewGroup): MovieReviewViewHolder{
            return MovieReviewViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.movie_review_item, viewGroup, false)
            )
        }
    }

}