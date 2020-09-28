package com.javadsh98.movie.presentation.main.fragment.series

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.javadsh98.movie.R
import com.javadsh98.movie.data.remote.model.TvSeries
import com.javadsh98.movie.utils.DATA_VIEW
import com.javadsh98.movie.utils.LOADING_VIEW
import kotlinx.android.synthetic.main.series_item.view.*

typealias itemClicked = (TvSeries) -> Unit
class SeriesAdapter(val itemClicked: itemClicked)
    : PagingDataAdapter<TvSeries, SeriesAdapter.SeriesViewHolder>(diff){

    companion object{
        val diff = object: DiffUtil.ItemCallback<TvSeries>(){
            override fun areItemsTheSame(oldItem: TvSeries, newItem: TvSeries): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvSeries, newItem: TvSeries): Boolean {
                return oldItem == newItem
            }

        }
    }

    class SeriesViewHolder(val view: View): RecyclerView.ViewHolder(view){

        fun onBind(tvSeries: TvSeries, itemClicked: itemClicked) = with(itemView){
            series_card.setOnClickListener {
                itemClicked.invoke(tvSeries)
            }

            series_image.load(tvSeries.fullPosterPath) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                transformations(RoundedCornersTransformation(3f, 3f, 3f, 3f))
            }

        }

        companion object{
            fun create(parent: ViewGroup): SeriesViewHolder {
                return SeriesViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.series_item, parent, false)
                )
            }

        }
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it, itemClicked)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        return SeriesViewHolder.create(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) LOADING_VIEW else DATA_VIEW
    }

}